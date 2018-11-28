package com.sixliu.workflow.runtime.engine;

import com.sixliu.workflow.runtime.worker.ApprovalWorker;

/**
 * @author:MG01867
 * @date:2018年11月23日
 * @email:359852326@qq.com
 * @version:
 * @describe 自动审批worker管理组件
 */
public interface ApprovalEngine {

	void execute(String taskId,ApprovalWorker approvalWorker);
}
