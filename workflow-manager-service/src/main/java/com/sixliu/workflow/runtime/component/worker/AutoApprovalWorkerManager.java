package com.sixliu.workflow.runtime.component.worker;

import com.sixliu.workflow.runtime.repository.entity.Task;

/**
 * @author:MG01867
 * @date:2018年11月23日
 * @email:359852326@qq.com
 * @version:
 * @describe 自动审批worker管理组件
 */
public interface AutoApprovalWorkerManager {

	void start();

	void register(AutoApprovalWorker autoApprovalWorker);

	void synProcess(Task task, AutoApprovalWorker autoApprovalWorker);

	void shutdown();
}
