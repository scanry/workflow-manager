package com.sixliu.workflow.common.util;

import java.util.Date;

import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.model.repository.entity.JobModel;
import com.sixliu.workflow.model.repository.entity.TaskModel;
import com.sixliu.workflow.runtime.dto.FlowTask;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;

/**
 * @author:MG01867
 * @date:2018年7月23日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class FlowUtils{
	
	public static Job newWorkflowJob(JobModel workflowJobModel,String createUserId) {
		Job workflowJob = new Job();
		workflowJob.setName(workflowJobModel.getName());
		workflowJob.setModelId(workflowJobModel.getId());
		workflowJob.setStatus(JobStatus.NEW);
		workflowJob.setCreateUserId(createUserId);
		workflowJob.setUpdateUserId(createUserId);
		return workflowJob;
	}

	public static Task newWorkflowTask(TaskModel workflowTaskModel, String jobId,
			String userId) {
		Task workflowTask = new Task();
		workflowTask.setName(workflowTaskModel.getName());
		workflowTask.setJobId(jobId);
		workflowTask.setModelId(workflowTaskModel.getId());
		workflowTask.setPhase(workflowTaskModel.getPhase());
		workflowTask.setStatus(TaskStatus.POOLING);
		workflowTask.setType(workflowTaskModel.getType());
		workflowTask.setCreateUserId(userId);
		workflowTask.setUpdateUserId(userId);
		return workflowTask;
	}
	
	public static FlowTask copyFlowTask(FlowTask flowTask,String userId) {
		FlowTask newFlowTask = new FlowTask();
		newFlowTask.setFlowTaskModelId(flowTask.getFlowTaskModelId());
		newFlowTask.setFlowJobId(flowTask.getFlowJobId());
		newFlowTask.setPhase(flowTask.getPhase());
		newFlowTask.setStatus(TaskStatus.POOLING);
		newFlowTask.setType(flowTask.getType());
		newFlowTask.setWorker(flowTask.getWorker());
		Date nowDate = new Date();
		newFlowTask.setCreateDate(nowDate);
		newFlowTask.setUpdateDate(nowDate);
		newFlowTask.setCreateUserId(userId);
		return newFlowTask;
	}
}
