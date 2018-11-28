package com.sixliu.workflow.runtime.engine;

import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.worker.ApprovalWorker;

/**
 * @author:MG01867
 * @date:2018年11月28日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class AutpApprovalWorkerProxy implements ApprovalWorker {

	private String jobId;

	protected AutpApprovalWorkerProxy(String jobId) {
		this.jobId = jobId;
	}

	@Override
	public TaskProcessResult process(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

}