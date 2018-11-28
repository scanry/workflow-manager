package com.sixliu.workflow.config.repository.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.PhaseType;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.model.dto.JobModelDTO;
import com.sixliu.workflow.model.dto.TaskModelDTO;
import com.sixliu.workflow.model.service.WorkflowConfigService;

/**
 * @author:MG01867
 * @date:2018年11月20日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class WorkflowConfigServiceTest extends BaseTest {

	@Autowired
	WorkflowConfigService workflowConfigService;

	@Test
	public void testAddJobModel() {
		JobModelDTO jobModel = new JobModelDTO();
		jobModel.setCode("xxxx");
		jobModel.setName("信用授信流程");
		jobModel.setRemark("信用授信流程");
		jobModel.setCreateUserId("sixliu");
		jobModel.setCreateRoleId("sixliu");
		workflowConfigService.addJobModel(jobModel);
	}

	@Test
	public void testGetJobModelById() {
		JobModelDTO jobModel = workflowConfigService.getJobModelById("b1e700c2-b6f9-11e8-9e01-005056986f0b");
		checkOk(jobModel);
	}

	@Test
	public void testListJobModel() {
		List<JobModelDTO> jobModels = workflowConfigService.listJobModel();
		checkOk(jobModels);
	}

	@Test
	public void testAddTaskModel() {
		String jobId="b1e700c2-b6f9-11e8-9e01-005056986f0b";
		int phase=0;
		TaskModelDTO taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("创建授信申请");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.MANUAL);
		taskModel.setPhaseType(PhaseType.START);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		String taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
		
		taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("授信申请提交");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.MANUAL);
		taskModel.setPhaseType(PhaseType.MIDWAY);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
		
		taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("风控自动审批");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.AUTO);
		taskModel.setPhaseType(PhaseType.MIDWAY);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
		
		taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("初审");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.MANUAL);
		taskModel.setPhaseType(PhaseType.MIDWAY);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
		
		taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("复审");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.MANUAL);
		taskModel.setPhaseType(PhaseType.MIDWAY);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
		
		taskModel = new TaskModelDTO();
		taskModel.setJobId(jobId);
		taskModel.setName("终审");
		taskModel.setPhase(phase++);
		taskModel.setType(TaskType.MANUAL);
		taskModel.setPhaseType(PhaseType.MIDWAY);
		taskModel.setGrantRoleId("sixliu");
		taskModel.setSkipScript("xxx<=500");
		taskModel.setRemark("创建授信申请");
		taskModel.setCreateUserId("sixliu");
		taskId=workflowConfigService.addTaskModel(taskModel);
		checkOk(taskId);
	}
}
