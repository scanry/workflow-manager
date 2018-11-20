package com.sixliu.workflow.runtime.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.runtime.dto.FlowTask;
import com.sixliu.workflow.runtime.service.WorkflowRuntimeService;

/**
*@author:MG01867
*@date:2018年9月12日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class WorkflowRuntimeServiceTest extends BaseTest {

	@Autowired
	private WorkflowRuntimeService workflowRuntimeService;
	
	
	@Test
	public void testCreateJob(){
		String jobId=workflowRuntimeService.createJob(null);
		checkOk(jobId);
	}
	
	@Test
	public void testListTaskByUserId(){
		List<FlowTask> tasks=workflowRuntimeService.listTaskByUserId("sixliu");
		checkOk(tasks);
	}
}
