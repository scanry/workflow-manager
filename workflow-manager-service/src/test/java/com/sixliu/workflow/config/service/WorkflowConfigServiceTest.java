package com.sixliu.workflow.config.service;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sixliu.workflow.BaseTest;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.model.dto.JobModelDTO;
import com.sixliu.workflow.model.dto.TaskModelDTO;
import com.sixliu.workflow.model.service.WorkflowConfigService;

/**
 * @author: sixliu
 * @email: 359852326@qq.com
 * @date: 2018年9月4日 下午10:33:22
 * @version V1.0
 * @Description:TODO
 */
public class WorkflowConfigServiceTest extends BaseTest {

	@Autowired
	WorkflowConfigService workflowConfigService;

	@Test
	public void testAddWorkflowJobModel() {
		JobModelDTO workflowJobModel = new JobModelDTO();
		workflowJobModel.setName("信贷产品授信流程");
		workflowJobModel.setCreateRoleId("sixliu");
		workflowJobModel.setRemark("信贷产品授信流程");
		workflowJobModel.setCreateUserId("sixliu");
		String jobId = workflowConfigService.addJobModel(workflowJobModel);
		checkOk(jobId);
	}

	@Test
	public void testAddWorkflowTaskModel() {
		String jobModelId = "b1e700c2-b6f9-11e8-9e01-005056986f0b";
		int phase = 1;
		TaskModelDTO workflowTaskModel1 = new TaskModelDTO();
		workflowTaskModel1.setName("初审");
		workflowTaskModel1.setJobId(jobModelId);
		workflowTaskModel1.setPhase(phase++);
		workflowTaskModel1.setType(TaskType.MANUAL);
		workflowTaskModel1.setGrantRoleId("sixliu");
		workflowTaskModel1.setRemark("信贷产品授信流程");
		workflowTaskModel1.setCreateUserId("sixliu");
		String taskId = workflowConfigService.addTaskModel(workflowTaskModel1);
		checkOk(taskId);

		TaskModelDTO workflowTaskModel2 = new TaskModelDTO();
		workflowTaskModel2.setName("复审");
		workflowTaskModel2.setJobId(jobModelId);
		workflowTaskModel2.setPhase(phase++);
		workflowTaskModel2.setType(TaskType.MANUAL);
		workflowTaskModel2.setGrantRoleId("sixliu");
		workflowTaskModel2.setRemark("信贷产品授信流程");
		workflowTaskModel2.setCreateUserId("sixliu");
		taskId = workflowConfigService.addTaskModel(workflowTaskModel2);
		checkOk(taskId);

		TaskModelDTO workflowTaskModel3 = new TaskModelDTO();
		workflowTaskModel3.setName("终审");
		workflowTaskModel3.setJobId(jobModelId);
		workflowTaskModel3.setPhase(phase++);
		workflowTaskModel3.setType(TaskType.MANUAL);
		workflowTaskModel3.setGrantRoleId("sixliu");
		workflowTaskModel3.setRemark("信贷产品授信流程");
		workflowTaskModel3.setCreateUserId("sixliu");
		taskId = workflowConfigService.addTaskModel(workflowTaskModel3);
		checkOk(taskId);

		TaskModelDTO workflowTaskModel4 = new TaskModelDTO();
		workflowTaskModel4.setName("自动风控审批");
		workflowTaskModel4.setJobId(jobModelId);
		workflowTaskModel4.setPhase(phase++);
		workflowTaskModel4.setType(TaskType.AUTO);
		workflowTaskModel4.setGrantRoleId("sixliu");
		workflowTaskModel4.setRemark("信贷产品授信流程");
		workflowTaskModel4.setCreateUserId("sixliu");
		taskId = workflowConfigService.addTaskModel(workflowTaskModel4);
		checkOk(taskId);
	}

	@Test
	public void testListWorkflowJobModels() {
		List<JobModelDTO> result = workflowConfigService.listJobModel();
		checkOk(result);
	}

	@Test
	public void testListWorkflowTaskModelsByJobId() {
		List<TaskModelDTO> result = workflowConfigService
				.listTaskModelByJobId("b1e700c2-b6f9-11e8-9e01-005056986f0b");
		checkOk(result);
	}
}
