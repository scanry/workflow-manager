package com.sixliu.workflow.runtime.component.status;

import org.springframework.stereotype.Component;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.status.AbstractTaskStatusMachine;

/**
 * @author:MG01867
 * @date:2018年9月7日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 任务状态机：任务池
 */
@Component("POOLING")
public class TaskStatusMachineForPooling extends AbstractTaskStatusMachine {

	public TaskStatusMachineForPooling() {
		super(TaskStatus.POOLING);
	}

	@Override
	protected Task doProcess(Job workflowJob, Task workflowTask, TaskProcessResult taskProcessResult) {
		if (TaskStatus.PENDING != taskProcessResult.getStatus()) {
			throw new IllegalStateException(String.format(
					"This approvalResult's status[%s] of flowTask[%s][%s] is illegal", taskProcessResult.getStatus(),
					taskProcessResult.getTaskId(), taskProcessResult.getStatus()));
		}
		workflowTask.setStatus(taskProcessResult.getStatus());
		workflowTask.setOwnerUserId(taskProcessResult.getUserId());
		workflowTask.setUpdateUserId(taskProcessResult.getUserId());
		return null;
	}

}
