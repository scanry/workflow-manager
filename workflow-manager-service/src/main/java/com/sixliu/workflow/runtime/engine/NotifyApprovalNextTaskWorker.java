package com.sixliu.workflow.runtime.engine;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Task;

/**
*@author:MG01867
*@date:2018年11月28日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class NotifyApprovalNextTaskWorker implements Runnable {

	private String jobId;

	private TaskDao taskDao;

	private ApprovalEngine approvalEngine;

	protected NotifyApprovalNextTaskWorker(String jobId, TaskDao taskDao, ApprovalEngine approvalEngine) {
		this.jobId = jobId;
		this.taskDao = taskDao;
		this.approvalEngine = approvalEngine;
	}
	
	@Override
	public void run() {
		Task task = taskDao.getByJobIdForPooling(jobId);
		autoClaim(task);
	}

	private void autoClaim(Task task) {
		if (null != task && TaskType.AUTO == task.getType()) {
			TaskProcessResult taskProcessResult = new TaskProcessResult();
			taskProcessResult.setJobId(task.getJobId());
			taskProcessResult.setTaskId(task.getId());
			taskProcessResult.setStatus(TaskStatus.PENDING);
			taskProcessResult.setUserId("system");
			approvalEngine.execute(task.getId(), taskId -> {
				return taskProcessResult;
			});
		}
	}
}
