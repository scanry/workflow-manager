package com.sixliu.workflow.runtime.component.worker;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.runtime.component.ApprovalWorker;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.dao.WorkerDao;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.repository.entity.Worker;
import com.sixliu.workflow.runtime.status.TaskStatusMachine;
import com.sixliu.workflow.runtime.status.TaskStatusMachineFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author:MG01867
 * @date:2018年7月18日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 自动审批handler管理
 */
@Component
@Slf4j
public class ApprovalWorkerMangaerImpl implements AutoApprovalWorkerManager {

	/** 工作线程名字索引 **/
	private final static AtomicInteger WORKER_THREAD_INDEX = new AtomicInteger(0);

	private final static String WORKER_THREAD_NAME_PRE = "Auto-Process-";

	/** 定时扫描任务-默认第一次扫描时间 **/
	private final static long DEFAULT_FIRST_INITIAL_DELAY = 1000 * 5;

	@Value("${app.autoProcessWorkerMangaer.workerThreads}")
	private int workerThreads;

	/** 定时扫描任务延迟时间-增量因子 **/
	@Value("${app.autoProcessWorkerMangaer.initialDelayIncFactor}")
	private long initialDelayIncFactor;

	/** 工作线程池 **/
	private ScheduledThreadPoolExecutor workerThreadPool;

	@Autowired
	private RemoteAutoProcessWorkerFactory remoteAutoProcessWorkerFactory;

	@Autowired
	private TaskDao workflowTaskDao;

	@Autowired
	private WorkerDao workflowTaskWorkerDao;

	@Autowired
	private TaskStatusMachineFactory taskStatusMachineFactory;

	/** 定时扫描任务延迟时间历史集合,用于打散定时扫描任务的时间分布 **/
	private TreeSet<Long> initialDelayHistory;


	@PostConstruct
	public void init() {
		this.initialDelayHistory = new TreeSet<>();
		this.workerThreadPool = new ScheduledThreadPoolExecutor(workerThreads, this::newWorkerThread);
		List<Worker> workflowTaskWorkers = workflowTaskWorkerDao.listAll();
		for (Worker workflowTaskWorker : workflowTaskWorkers) {
			long initialDelay = nextInitialDelay();
			workerThreadPool.scheduleAtFixedRate(new TimingScanAutoProcessWorkerProxy(workflowTaskWorker), initialDelay,
					workflowTaskWorker.getCheckInterval(), TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public void execute(String taskId, ApprovalWorker autoApprovalWorker) {
		TaskProcessResult taskProcessResult = autoApprovalWorker.process(taskId);
		TaskStatusMachine taskStatusMachine = taskStatusMachineFactory.get(taskProcessResult.getStatus());
		taskStatusMachine.process(taskProcessResult);
		noticeProcessNextTask(taskProcessResult.getJobId());
	}

	/**
	 * 根据jobid通知处理下个能处理的任务
	 * 
	 * @param jobId
	 */
	private void noticeProcessNextTask(String jobId) {
		workerThreadPool.execute(new AutoProcessWorkerBroadcaster(jobId));
	}

	private long nextInitialDelay() {
		if (initialDelayHistory.isEmpty()) {
			initialDelayHistory.add(DEFAULT_FIRST_INITIAL_DELAY);
		}
		long lastInitialDelay = initialDelayHistory.last();
		long initialDelay = lastInitialDelay + initialDelayIncFactor;
		initialDelayHistory.add(initialDelay);
		return initialDelay;
	}

	private Thread newWorkerThread(Runnable r) {
		String newName = WORKER_THREAD_NAME_PRE + WORKER_THREAD_INDEX.getAndIncrement();
		Thread newThread = new Thread(r);
		newThread.setDaemon(false);
		newThread.setName(newName);
		return newThread;
	}

	public class TimingScanAutoProcessWorkerProxy implements Runnable {

		private Worker workflowTaskWorker;

		protected TimingScanAutoProcessWorkerProxy(Worker workflowTaskWorker) {
			this.workflowTaskWorker = workflowTaskWorker;
		}

		public void process(Task task) {
			List<Task> pendingTasks = null;
			if (null == task) {
				pendingTasks = workflowTaskDao.listForTimingScan(workflowTaskWorker.getId(), "");
			} else {
				pendingTasks = Arrays.asList(task);
			}
			for (Task item : pendingTasks) {
				ApprovalWorker wutoProcessWorker = remoteAutoProcessWorkerFactory.getOrNew(workflowTaskWorker);
				execute(item.getId(), wutoProcessWorker);
			}
			log.info("AutoProcessWorkerProxy");
		}

		@Override
		public void run() {
			process(null);
		}
	}

	public class AutoProcessWorkerBroadcaster implements Runnable {

		private String jobId;

		protected AutoProcessWorkerBroadcaster(String jobId) {
			this.jobId = jobId;
		}

		@Override
		public void run() {
			Task workflowTask = workflowTaskDao.getByJobIdForPooling(jobId);
			if (null != workflowTask && TaskType.AUTO == workflowTask.getType()) {
				Worker workflowTaskWorker = workflowTaskWorkerDao.getByTaskId(workflowTask.getId());
				if (null == workflowTaskWorker) {
					log.error(String.format("The WorkflowTask[%s] has not AutoProcessWorkerConfig",
							workflowTask.getId()));
					return;
				}
				TimingScanAutoProcessWorkerProxy autoProcessWorkerProxy = new TimingScanAutoProcessWorkerProxy(
						workflowTaskWorker);
				autoProcessWorkerProxy.process(workflowTask);
			}
		}

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(ApprovalWorker autoApprovalWorker) {

	}

	@PreDestroy
	public void shutdown() {
		if (null != workerThreadPool) {
			workerThreadPool.shutdown();
		}
	}
}
