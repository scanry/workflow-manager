package com.sixliu.workflow.runtime.engine;

import java.util.List;

import com.sixliu.workflow.common.service.SystemService;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.repository.entity.Worker;
import com.sixliu.workflow.runtime.worker.ApprovalWorker;
import com.sixliu.workflow.runtime.worker.ApprovalWorkerFactory;

/**
 * @author:MG01867
 * @date:2018年11月28日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class TimingScanPendingAutoApprovalTask implements Runnable {

	private Worker worker;
	private TaskDao taskDao;
	private SystemService systemService;
	private ApprovalWorkerFactory approvalWorkerFactory;
	private ApprovalEngine approvalEngine;

	protected TimingScanPendingAutoApprovalTask(Worker worker, TaskDao taskDao, SystemService systemService,
			ApprovalEngine approvalEngine,ApprovalWorkerFactory approvalWorkerFactory) {
		this.worker = worker;
		this.taskDao = taskDao;
		this.systemService = systemService;
		this.approvalEngine = approvalEngine;
		this.approvalWorkerFactory = approvalWorkerFactory;
	}

	@Override
	public void run() {
		List<Task> pendingTasks = taskDao.listForTimingScan(worker.getTaskId(), systemService.getSytemUser());
		for (Task item : pendingTasks) {
			ApprovalWorker approvalWorker = approvalWorkerFactory.getOrNew(worker);
			approvalEngine.execute(item.getId(), approvalWorker);
		}
	}
}