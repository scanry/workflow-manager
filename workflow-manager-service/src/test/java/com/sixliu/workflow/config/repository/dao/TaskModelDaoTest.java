package com.sixliu.workflow.config.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.model.repository.dao.TaskModelDao;
import com.sixliu.workflow.model.repository.entity.TaskModel;

/**
*@author:MG01867
*@date:2018年9月6日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class TaskModelDaoTest  extends BaseTest{

	@Autowired
	TaskModelDao workflowTaskModelDao;
	
	@Test
	public void testInsert() {
		for(int i=0;i<5;i++) {
			TaskModel workflowTaskModel=new TaskModel();
			workflowTaskModel.setName("test"+i);
			workflowTaskModel.setJobId("a50d9f82-b1e9-11e8-8bec-000c29851249");
			workflowTaskModel.setPhase(i);
			workflowTaskModel.setType(TaskType.MANUAL);
			workflowTaskModel.setCreateUserId("sixliu");
			workflowTaskModel.setUpdateUserId("sixliu");
			workflowTaskModelDao.add(workflowTaskModel);
		}
		checkOk(null);
	}

}
