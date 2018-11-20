package com.sixliu.workflow.runtime.status;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;

/**
 * @author:MG01867
 * @date:2018年8月13日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 流程任务状态机
 */
public interface TaskStatusMachine {

	TaskStatus getTaskStatus();
	
	void process(TaskProcessResult taskProcessResult,CompleteCallback completeCallback);
	
	@FunctionalInterface
	public interface CompleteCallback{
		
		void complete(String jobId);
	}
}
