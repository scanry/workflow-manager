package com.sixliu.workflow.config.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.model.repository.dao.JobModelDao;
import com.sixliu.workflow.model.repository.entity.JobModel;

/**
*@author:MG01867
*@date:2018年9月6日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class JobModelDaoTest extends BaseTest{

	@Autowired
	JobModelDao workflowJobModelDao;
	
	@Test
	public void testInsert() {
		JobModel workflowJobModel=new JobModel();
		workflowJobModel.setName("test");
		workflowJobModel.setCreateRoleId("sixliu");
		workflowJobModel.setCreateUserId("sixliu");
		workflowJobModel.setUpdateUserId("sixliu");
		int result=workflowJobModelDao.add(workflowJobModel);
		checkOk(result);
	}
	
	@Test
	public void testGet() {
		JobModel workflowJobModel=workflowJobModelDao.get("940659f0-b1ca-11e8-9e01-005056986f0b");
		checkOk(workflowJobModel);
	}
}
