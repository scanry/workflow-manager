package com.sixliu.workflow.config.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.EventType;
import com.sixliu.workflow.config.repository.dao.EventModelDao;
import com.sixliu.workflow.config.repository.entity.EventModel;

/**    
 * @author: sixliu
 * @email:  359852326@qq.com
 * @date:   2018年9月6日 下午11:06:11   
 * @version V1.0 
 * @Description:TODO
 */
public class EventModelDaoTest extends BaseTest{

	@Autowired
	EventModelDao workflowEventModelDao;
	
	@Test
	public void testInsert() {
		EventModel workflowEventModel=new EventModel();
		workflowEventModel.setTaskId("c885bca1-b1e9-11e8-8bec-000c29851249");
		workflowEventModel.setType(EventType.CREATED);
		workflowEventModel.setScript("send msg");
		workflowEventModel.setCreateUserId("sixliu");
		workflowEventModel.setUpdateUserId("sixliu");
		int result=workflowEventModelDao.add(workflowEventModel);
		checkOk(result);
	}
	
	@Test
	public void testGet() {
		EventModel workflowEventModel=workflowEventModelDao.get("205dae84-b1ea-11e8-8bec-000c29851249");
		checkOk(workflowEventModel);
	}
	
	@Test
	public void testListByTaskId() {
		List<EventModel> workflowEventModels=workflowEventModelDao.listByTaskId("c885bca1-b1e9-11e8-8bec-000c29851249");
		checkOk(workflowEventModels);
	}
}
