package com.sixliu.workflow.runtime.engine;

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

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.dao.WorkerDao;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.repository.entity.Worker;
import com.sixliu.workflow.runtime.status.TaskStatusMachine;
import com.sixliu.workflow.runtime.status.TaskStatusMachineFactory;
import com.sixliu.workflow.runtime.worker.ApprovalWorker;
import com.sixliu.workflow.runtime.worker.ApprovalWorkerFactory;


/**
 * @author:MG01867
 * @date:2018年7月18日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 自动审批handler管理
 */
@Component
public class ApprovalEngineImpl implements ApprovalEngine {

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
	private ApprovalWorkerFactory approvalWorkerFactory;

	@Autowired
	private TaskDao workflowTaskDao;

	@Autowired
	private WorkerDao workerDao;

	@Autowired
	private TaskStatusMachineFactory taskStatusMachineFactory;

	@PostConstruct
	public synchronized void init() {
		if (null == workerThreadPool) {
			this.workerThreadPool = new ScheduledThreadPoolExecutor(workerThreads, run -> {
				String newName = WORKER_THREAD_NAME_PRE + WORKER_THREAD_INDEX.getAndIncrement();
				Thread newThread = new Thread(run);
				newThread.setDaemon(false);
				newThread.setName(newName);
				return newThread;
			});
			List<Worker> workers = workerDao.listAll();
			/** 定时扫描任务延迟时间历史集合,用于打散定时扫描任务的时间分布 **/
			TreeSet<Long> initialDelayHistory = new TreeSet<>();
			initialDelayHistory.add(DEFAULT_FIRST_INITIAL_DELAY);
			for (Worker worker : workers) {
				long lastInitialDelay = initialDelayHistory.last();
				long initialDelay = lastInitialDelay + initialDelayIncFactor;
				initialDelayHistory.add(initialDelay);
				workerThreadPool.scheduleAtFixedRate(new TimingScanTaskApprovalWorker(worker), initialDelay,
						worker.getCheckInterval(), TimeUnit.MILLISECONDS);
			}
		}
	}

	@Override
	public void execute(String taskId, ApprovalWorker approvalWorker) {
		TaskProcessResult taskProcessResult = approvalWorker.process(taskId);
		TaskStatusMachine taskStatusMachine = taskStatusMachineFactory.get(taskProcessResult.getStatus());
		taskStatusMachine.process(taskProcessResult);
		workerThreadPool.execute(new ApprovalWorkerBroadcaster(taskProcessResult.getJobId()));
	}
	
	private void execute(String taskId) {
		Worker worker = workerDao.getByTaskId(taskId);
		ApprovalWorker approvalWorker = approvalWorkerFactory.getOrNew(worker);
		execute(taskId, approvalWorker);
	}

	public class TimingScanTaskApprovalWorker implements Runnable {

		private Worker worker;

		protected TimingScanTaskApprovalWorker(Worker worker) {
			this.worker = worker;
		}

		public void process(Task task) {
			List<Task> pendingTasks = null;
			if (null == task) {
				pendingTasks = workflowTaskDao.listForTimingScan(worker.getTaskId());
			} else {
				pendingTasks = Arrays.asList(task);
			}
			for (Task item : pendingTasks) {
				execute(item.getId());
			}
		}

		@Override
		public void run() {
			process(null);
		}
	}

	//autoClaim
	public class ApprovalWorkerBroadcaster implements Runnable {

		private String jobId;

		protected ApprovalWorkerBroadcaster(String jobId) {
			this.jobId = jobId;
		}

		@Override
		public void run() {
			Task task = workflowTaskDao.getByJobIdForPooling(jobId);
			if (null != task && TaskType.AUTO == task.getType()) {
				TaskStatusMachine taskStatusMachine = taskStatusMachineFactory.get(TaskStatus.POOLING);
				TaskProcessResult taskProcessResult = new TaskProcessResult();
				taskProcessResult.setJobId(task.getJobId());
				taskProcessResult.setTaskId(task.getId());
				taskProcessResult.setStatus(TaskStatus.PENDING);
				taskProcessResult.setUserId("system");
				taskStatusMachine.process(taskProcessResult);
				execute(task.getId());
			}
		}
	}
	
	
	@PreDestroy
	public void shutdown() {
		if (null != workerThreadPool) {
			workerThreadPool.shutdown();
		}
	}
}
