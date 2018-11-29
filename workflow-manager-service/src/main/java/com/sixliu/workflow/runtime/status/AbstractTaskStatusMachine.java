package com.sixliu.workflow.runtime.status;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.common.component.TransactionalHelper;
import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.service.SystemService;
import com.sixliu.workflow.model.repository.dao.TaskModelDao;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.dao.JobDao;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;

import lombok.extern.slf4j.Slf4j;

/**
 * @author:MG01867
 * @date:2018年9月7日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe
 */
@Slf4j
public abstract class AbstractTaskStatusMachine implements TaskStatusMachine {

	private TaskStatus taskStatus;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private TaskModelDao taskModelDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TaskStatusMachineFactory taskStatusMachineFactory;

	@Autowired
	private TransactionalHelper transactionalHelper;

	@Autowired
	private SystemService systemService;

	public AbstractTaskStatusMachine(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	@PostConstruct
	public void init() {
		taskStatusMachineFactory.register(this);
	}

	@Override
	public final boolean process(TaskProcessResult taskProcessResult) {
		return transactionalHelper.doSomething(() -> {
			return processByTransactional(taskProcessResult);
		});
	}

	private boolean processByTransactional(TaskProcessResult taskProcessResult) {
		Job job = jobDao.get(taskProcessResult.getJobId());
		if (null == job) {
			throw new IllegalArgumentException(
					String.format("This Job[%s] is non-existent", taskProcessResult.getJobId()));
		}
		if (JobStatus.WORKING != job.getStatus()) {
			throw new UnsupportedOperationException(
					String.format("The job[%s] is ended[%s]", job.getId(), job.getStatus()));
		}
		if (null != job.getLockUrl()) {
			throw new IllegalArgumentException(
					String.format("The Job[%s] was be lock by target[%s]", job.getLockUrl()));
		}
		int locked = jobDao.tryLock(job.getId(), systemService.getSytemUser(), job.getVersion());
		if (1 == locked) {
			Task workflowTask = taskDao.get(taskProcessResult.getTaskId());
			try {
				TaskStatus oldStatus = workflowTask.getStatus();
				Task nextWorkflowTask = doProcess(job, workflowTask, taskProcessResult);
				TaskStatus newStatus = workflowTask.getStatus();
				if (oldStatus != newStatus) {
					taskDao.update(workflowTask);
				}
				if (StringUtils.isNotBlank(taskProcessResult.getInnerOpinion())
						|| StringUtils.isNotBlank(taskProcessResult.getOuterOpinion())) {
					workflowTask.setInnerOpinion(taskProcessResult.getInnerOpinion());
					workflowTask.setOuterOpinion(taskProcessResult.getOuterOpinion());
				}
				if (null != nextWorkflowTask) {
					taskDao.insert(nextWorkflowTask);
				}
				return true;
			} catch (Exception exception) {
				log.error(String.format("The user[%s] process task[%s] of job[%s] err", taskProcessResult.getUserId(),
						workflowTask.getId(), job.getId()), exception);
			} finally {
				jobDao.unlock(job.getId(), job.getVersion() + 1);
			}
		} else {
			log.warn(String.format("Try to lock job[%s] failed", job.getId()));
		}
		return false;
	}

	protected abstract Task doProcess(Job workflowJob, Task workflowTask, TaskProcessResult taskProcessResult);

	@Override
	public final TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public final TaskModelDao getTaskModelDao() {
		return taskModelDao;
	}

	public final TaskDao getTaskDao() {
		return taskDao;
	}
}
