package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.model.repository.dao.TaskModelDao;
import com.sixliu.workflow.model.repository.entity.TaskModel;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Task;

/**    
 * @author: sixliu
 * @email:  359852326@qq.com
 * @date:   2018年9月6日 下午11:39:26   
 * @version V1.0 
 * @description:
 */
public class TaskDaoTest extends BaseTest{
	
	@Autowired
	TaskDao workflowTaskDao;
	
	@Autowired
	TaskModelDao workflowTaskModelDao;

	
	@Test
	public void testInsert() {
		List<TaskModel> workflowTaskModels=workflowTaskModelDao.listByJobId("940659f0-b1ca-11e8-9e01-005056986f0b");
		for(TaskModel workflowTaskModel:workflowTaskModels) {
			Task workflowTask=new Task();
			workflowTask.setJobId("f93c67ec-b1ea-11e8-8bec-000c29851249");
			workflowTask.setModelId(workflowTaskModel.getId());
			workflowTask.setName(workflowTaskModel.getName());
			workflowTask.setPhase(workflowTaskModel.getPhase());
			workflowTask.setType(workflowTaskModel.getType());
			workflowTask.setStatus(TaskStatus.POOLING);
			workflowTask.setWorker("sixliu");
			workflowTask.setCreateUserId("sixliu");
			workflowTask.setUpdateUserId("sixliu");
			workflowTaskDao.insert(workflowTask);
		}
		checkOk(null);

	}

}
