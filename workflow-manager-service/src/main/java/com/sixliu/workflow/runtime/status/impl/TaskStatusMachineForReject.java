package com.sixliu.workflow.runtime.status.impl;

import org.springframework.stereotype.Component;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.status.AbstractTaskStatusMachine;

/**
*@author:MG01867
*@date:2018年9月7日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
@Component("REJECT")
public class TaskStatusMachineForReject extends AbstractTaskStatusMachine {

	public TaskStatusMachineForReject() {
		super(TaskStatus.REJECT);
	}

	@Override
	protected Task doProcess(Job workflowJob, Task workflowTask,
			TaskProcessResult taskProcessResult) {
		throw new UnsupportedOperationException(
				String.format("No operation is supported when the flowTask[%s]'s status[%s] of flowJob[%s]",
						workflowTask.getId(), TaskStatus.REJECT, workflowJob.getId()));
	}

}
