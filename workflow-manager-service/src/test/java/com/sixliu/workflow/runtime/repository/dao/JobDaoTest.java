package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.runtime.repository.dao.JobDao;
import com.sixliu.workflow.runtime.repository.entity.Job;

/**
*@author:MG01867
*@date:2018年9月6日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class JobDaoTest extends BaseTest{

	@Autowired
	JobDao workflowJobDao;
	
	@Test
	public void testInsert() {
		Job workflowJob=new Job();
		workflowJob.setName("test");
		workflowJob.setModelId("workflowJobModelId");
		workflowJob.setStatus(JobStatus.NEW);
		workflowJob.setCreateUserId("sixliu");
		workflowJob.setUpdateUserId("sixliu");
		int result=workflowJobDao.add(workflowJob);
		checkOk(result);
	}
	
	@Test
	public void testGet() {
		Job workflowJob=workflowJobDao.get("186c08fa-b1c8-11e8-9e01-005056986f0b");
		checkOk(workflowJob);
	}
	
	@Test
	public void testListLockJobs() {
		List<Job> workflowJobs=workflowJobDao.listLockJobs();
		checkOk(workflowJobs);
	}
}
