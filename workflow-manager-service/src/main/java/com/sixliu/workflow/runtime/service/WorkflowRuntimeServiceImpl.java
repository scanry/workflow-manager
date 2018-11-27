package com.sixliu.workflow.runtime.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.sixliu.app.user.dto.UserRoleDTO;
import com.sixliu.app.user.service.UserRoleService;
import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.util.FlowUtils;
import com.sixliu.workflow.config.repository.dao.JobModelDao;
import com.sixliu.workflow.config.repository.dao.TaskModelDao;
import com.sixliu.workflow.config.repository.entity.JobModel;
import com.sixliu.workflow.config.repository.entity.TaskModel;
import com.sixliu.workflow.runtime.component.worker.ApprovalWorkerMangaerImpl;
import com.sixliu.workflow.runtime.dto.CreateJobDTO;
import com.sixliu.workflow.runtime.dto.FlowTask;
import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.dao.JobDao;
import com.sixliu.workflow.runtime.repository.dao.TaskDao;
import com.sixliu.workflow.runtime.repository.entity.Job;
import com.sixliu.workflow.runtime.repository.entity.Task;
import com.sixliu.workflow.runtime.service.WorkflowRuntimeService;

/**
 * @author:MG01867
 * @date:2018年7月23日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 流程管理-运行时服务接口实现
 */
@RestController
public class WorkflowRuntimeServiceImpl implements WorkflowRuntimeService {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private JobModelDao workflowJobModelDao;

	@Autowired
	private TaskModelDao workflowTaskModelDao;

	@Autowired
	private JobDao workflowJobDao;

	@Autowired
	private TaskDao workflowTaskDao;

	@Autowired
	private ApprovalWorkerMangaerImpl autoProcessWorkerMangaer;
	

	@Transactional
	@Override
	public String createJob(CreateJobDTO createJobDTO) {
		JobModel workflowJobModel = workflowJobModelDao.get(createJobDTO.getModelId());
		if (null == workflowJobModel) {
			throw new IllegalArgumentException(
					String.format("The flowJobClass[%s] is non-existent", createJobDTO.getModelId()));
		}
		UserRoleDTO user = userRoleService.getByUserIdAndRoleId(createJobDTO.getCreateUserId(),
				workflowJobModel.getCreateRoleId());
		if (null == user) {
			throw new IllegalArgumentException(
					String.format("The user[%s] create flowJob of flowJobClass[%s] Permission denied",
							createJobDTO.getCreateUserId(), createJobDTO.getModelId()));
		}
		Job flowJob = FlowUtils.newWorkflowJob(workflowJobModel, user.getId());
		workflowJobDao.add(flowJob);
		TaskModel workflowTaskModel = workflowTaskModelDao.getByJobIdAndPhase(workflowJobModel.getId(), 0);
		if (null == workflowTaskModel) {
			throw new IllegalArgumentException(
					String.format("The flowJobClass[%s] configure empty flowTaskModel", workflowJobModel.getId()));
		}
		Task workflowTask = FlowUtils.newWorkflowTask(workflowTaskModel, flowJob.getId(), user.getId());
		workflowTaskDao.insert(workflowTask);
		return flowJob.getId();
	}

	@Override
	public List<FlowTask> listTaskByUserId(String userId) {
		UserRoleDTO user = getAndCheckUser(userId);
		List<Task> workflowTasks = workflowTaskDao.listByRoleId(user.getRoleId());
		List<FlowTask> result = new ArrayList<>(workflowTasks.size());
		for (Task item : workflowTasks) {
			FlowTask flowTask = new FlowTask();
			BeanUtils.copyProperties(item, flowTask);
			result.add(flowTask);
		}
		return result;
	}

	@Override
	public List<FlowTask> listTaskByUserIdAndTaskStatus(String userId, TaskStatus status) {
		UserRoleDTO user = getAndCheckUser(userId);
		workflowTaskDao.listByRoleIdAndStatus(user.getRoleId(), status);
		return null;
	}

	@Override
	public String autoClaimTask(String userId) {
		UserRoleDTO user = getAndCheckUser(userId);
		Task claimWorkflowTask = randomWorkflowTask(user.getId());
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setJobId(claimWorkflowTask.getId());
		taskProcessResult.setTaskId(claimWorkflowTask.getId());
		taskProcessResult.setStatus(TaskStatus.PENDING);
		taskProcessResult.setUserId(userId);
		submitTaskProcessResult(taskProcessResult);
		return claimWorkflowTask.getId();
	}

	@Override
	public void submitTaskProcessResult(TaskProcessResult taskProcessResult) {
		autoProcessWorkerMangaer.execute(taskProcessResult.getTaskId(),taskId->{
			return taskProcessResult;
		});
	}

	@Override
	public void cancelJob(String jobId, String userId) {
		Job job = workflowJobDao.get(jobId);
		if (null == job) {
			throw new IllegalArgumentException(String.format("This flowTask[%s] is non-existent", jobId));
		}
		if (!StringUtils.equals(userId, job.getCreateUserId())) {
			throw new IllegalArgumentException(
					String.format("The user[%s] cancelFlowJob flowJob[%s] Permission denied", userId, userId, jobId));
		}
		job.setStatus(JobStatus.CANCELED);
		job.setUpdateDate(new Date());
	}

	private Task randomWorkflowTask(String userId) {
		return null;
	}

	private UserRoleDTO getAndCheckUser(String userId) {
//		UserDTO user = userManagerService.get(userId);
//		if (null == user) {
//			throw new IllegalArgumentException(String.format("The user[%s] is non-existent", userId));
//		}
		UserRoleDTO user = new UserRoleDTO();
		user.setUserId(userId);
		user.setRoleId(userId);
		return user;
	}
}
