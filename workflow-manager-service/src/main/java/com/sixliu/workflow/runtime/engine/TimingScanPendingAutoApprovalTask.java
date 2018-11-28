package com.sixliu.workflow.runtime.engine;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.repository.entity.Worker;

/**
*@author:MG01867
*@date:2018年11月28日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class TimingScanPendingAutoApprovalTask implements Runnable {

	private Worker worker;
	private TaskDao taskDao;

	protected TimingScanPendingAutoApprovalTask(Worker worker,TaskDao taskDao) {
		this.worker = worker;
		this.taskDao = taskDao;
	}

	public void process(Task task) {
		List<Task> pendingTasks = taskDao.listForTimingScan(worker.getTaskId());
		for (Task item : pendingTasks) {
			
		}
	}

	@Override
	public void run() {
		process(null);
	}
}