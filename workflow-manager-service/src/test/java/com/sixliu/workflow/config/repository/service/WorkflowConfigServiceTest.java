package com.sixliu.workflow.config.repository.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.config.dto.JobModelDTO;
import com.sixliu.workflow.config.service.WorkflowConfigService;

/**
*@author:MG01867
*@date:2018年11月20日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class WorkflowConfigServiceTest extends BaseTest{

	@Autowired
	WorkflowConfigService workflowConfigService;
	
	@Test
	public void testAddJobModel() {
		JobModelDTO jobModel=new JobModelDTO();
		jobModel.setCode("xxxx");
		jobModel.setName("信用授信流程");
		jobModel.setRemark("信用授信流程");
		jobModel.setCreateUserId("sixliu");
		jobModel.setCreateRoleId("sixliu");
		workflowConfigService.addJobModel(jobModel);
	}
}
