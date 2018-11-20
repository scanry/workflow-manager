package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.config.repository.dao.TaskModelDao;
import com.sixliu.workflow.config.repository.entity.TaskModel;
import com.sixliu.workflow.runtime.repository.dao.WorkerDao;
import com.sixliu.workflow.runtime.repository.entity.Worker;

/**
*@author:MG01867
*@date:2018年9月12日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class WorkerDaoTest extends BaseTest{

	@Autowired
	TaskModelDao workflowTaskModelDao;
	
	@Autowired
	WorkerDao workflowTaskWorkerDao;
	
	@Test
	public void testInsert() {
		TaskModel workflowTaskModel=workflowTaskModelDao.get("bed5fc39-b1cd-11e8-9e01-005056986f0b");
		Worker workflowTaskWorker=new Worker();
		workflowTaskWorker.setName("work");
		workflowTaskWorker.setCheckInterval(1000*60*5);
		workflowTaskWorker.setCreateUserId("sixliu");
		workflowTaskWorker.setUpdateUserId("sixliu");
		int result=workflowTaskWorkerDao.add(workflowTaskWorker);
		checkOk(result);
	}
	
	@Test
	public void testGet() {
		Worker workflowTaskWorker=workflowTaskWorkerDao.get("212597d3-b630-11e8-9e01-005056986f0b");
		checkOk(workflowTaskWorker);
	}
	
	@Test
	public void testGetByTaskId() {
		Worker workflowTaskWorker=workflowTaskWorkerDao.getByTaskId("bed5fc39-b1cd-11e8-9e01-005056986f0b");
		checkOk(workflowTaskWorker);
	}
	
	@Test
	public void testListAll() {
		List<Worker> workflowTaskWorkers=workflowTaskWorkerDao.listAll();
		checkOk(workflowTaskWorkers);
	}

}
