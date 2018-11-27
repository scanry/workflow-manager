package com.sixliu.workflow.runtime.status;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.common.component.TransactionalHelper;
import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.service.HealthyService;
import com.sixliu.workflow.config.repository.dao.TaskModelDao;
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
	private JobDao workflowJobDao;

	@Autowired
	private TaskModelDao workflowTaskModelDao;

	@Autowired
	private TaskDao workflowTaskDao;

	@Autowired
	private TaskStatusMachineFactory taskStatusMachineFactory;

	@Autowired
	private TransactionalHelper transactionalHelper;
	
	@Autowired
	private HealthyService workflowService;

	public AbstractTaskStatusMachine(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	@PostConstruct
	public void init() {
		taskStatusMachineFactory.register(this);
	}

	@Override
	public final void process(TaskProcessResult taskProcessResult) {
		transactionalHelper.doSomething(() -> {
			processByTransactional(taskProcessResult);
			return null;
		});
	}

	private void processByTransactional(TaskProcessResult taskProcessResult) {
		Job workflowJob = workflowJobDao.get(taskProcessResult.getJobId());
		if (null == workflowJob) {
			throw new IllegalArgumentException(
					String.format("This Job[%s] is non-existent", taskProcessResult.getJobId()));
		}
		if (JobStatus.WORKING != workflowJob.getStatus()) {
			throw new UnsupportedOperationException(
					String.format("The job[%s] is ended[%s]", workflowJob.getId(), workflowJob.getStatus()));
		}
		if (null != workflowJob.getLockUrl()) {
			throw new IllegalArgumentException(
					String.format("The Job[%s] was be lock by target[%s]", workflowJob.getLockUrl()));
		}
		int locked = workflowJobDao.tryLock(workflowJob.getId(),workflowService.getUrl(), workflowJob.getVersion());
		if (1 == locked) {
			Task workflowTask = workflowTaskDao.get(taskProcessResult.getTaskId());
			try {
				TaskStatus oldStatus = workflowTask.getStatus();
				Task nextWorkflowTask = doProcess(workflowJob, workflowTask, taskProcessResult);
				TaskStatus newStatus = workflowTask.getStatus();
				if (oldStatus != newStatus) {
					workflowTaskDao.update(workflowTask);
				}
				if (StringUtils.isNotBlank(taskProcessResult.getInnerOpinion())
						|| StringUtils.isNotBlank(taskProcessResult.getOuterOpinion())) {
					workflowTask.setInnerOpinion(taskProcessResult.getInnerOpinion());
					workflowTask.setOuterOpinion(taskProcessResult.getOuterOpinion());
				}
				if (null != nextWorkflowTask) {
					workflowTaskDao.insert(nextWorkflowTask);
				}
			} catch (Exception exception) {
				log.error(String.format("The user[%s] process task[%s] of job[%s] err", taskProcessResult.getUserId(),
						workflowTask.getId(), workflowJob.getId()), exception);
			} finally {
				workflowJobDao.unlock(workflowJob.getId(), workflowJob.getVersion() + 1);
			}
		} else {
			log.warn(String.format("Try to lock job[%s] failed", workflowJob.getId()));
		}
	}

	protected abstract Task doProcess(Job workflowJob, Task workflowTask,
			TaskProcessResult taskProcessResult);

	@Override
	public final TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public final TaskModelDao getWorkflowTaskModelDao() {
		return workflowTaskModelDao;
	}

	public final TaskDao getWorkflowTaskDao() {
		return workflowTaskDao;
	}
}
