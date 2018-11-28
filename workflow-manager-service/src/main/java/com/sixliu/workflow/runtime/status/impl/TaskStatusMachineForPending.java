package com.sixliu.workflow.runtime.status.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.util.FlowUtils;
import com.sixliu.workflow.model.repository.entity.TaskModel;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.status.AbstractTaskStatusMachine;

/**
 * @author:MG01867
 * @date:2018年9月7日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 任务状态机：待处理
 */
@Component("PENDING")
public class TaskStatusMachineForPending extends AbstractTaskStatusMachine {

	public TaskStatusMachineForPending() {
		super(TaskStatus.PENDING);
	}

	@Override
	protected Task doProcess(Job workflowJob, Task workflowTask,
			TaskProcessResult taskProcessResult) {
		TaskStatus next = taskProcessResult.getStatus();
		Task nextWorkflowTask = null;
		if (TaskStatus.PASS == next) {
			TaskModel nextWorkflowTaskModel = getTaskModelDao()
					.getByJobIdAndPhase(workflowJob.getModelId(), workflowTask.getPhase() + 1);
			if (null != nextWorkflowTaskModel) {
				nextWorkflowTask = FlowUtils.newWorkflowTask(nextWorkflowTaskModel, workflowTask.getJobId(),
						taskProcessResult.getUserId());
			} else {
				workflowJob.setStatus(JobStatus.PASSED);
				workflowJob.setUpdateDate(new Date());
			}
		} else if (TaskStatus.REJECT == next) {
			workflowJob.setStatus(JobStatus.PASSED);
			workflowJob.setUpdateDate(new Date());
		}else if (TaskStatus.OVERRULE == next) {
			TaskModel overruleWorkflowTaskModel = getTaskModelDao()
					.getByJobIdAndPhase(workflowJob.getModelId(), taskProcessResult.getOverrulePhase());
			if (null == overruleWorkflowTaskModel) {
				throw new IllegalArgumentException("the ");
			}
			if (overruleWorkflowTaskModel.getPhase() > workflowTask.getPhase()) {
				throw new IllegalArgumentException("the ");
			}
			nextWorkflowTask = FlowUtils.newWorkflowTask(overruleWorkflowTaskModel, workflowTask.getJobId(),
					taskProcessResult.getUserId());
		} else {
			throw new IllegalStateException(String.format(
					"This approvalResult's status[%s] of flowTask[%s][%s] is illegal", taskProcessResult.getStatus(),
					taskProcessResult.getTaskId(), taskProcessResult.getStatus()));
		}
		return nextWorkflowTask;
	}
}
