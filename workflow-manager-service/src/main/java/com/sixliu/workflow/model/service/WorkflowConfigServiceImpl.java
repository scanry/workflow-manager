package com.sixliu.workflow.model.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sixliu.workflow.common.exception.IllegalArgumentAppException;
import com.sixliu.workflow.common.util.Convertor;
import com.sixliu.workflow.model.dto.JobModelDTO;
import com.sixliu.workflow.model.dto.TaskModelDTO;
import com.sixliu.workflow.model.repository.dao.JobModelDao;
import com.sixliu.workflow.model.repository.dao.TaskModelDao;
import com.sixliu.workflow.model.repository.entity.JobModel;
import com.sixliu.workflow.model.repository.entity.TaskModel;
import com.sixliu.workflow.model.service.WorkflowConfigService;

/**
 * @author:MG01867
 * @date:2018年9月6日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe
 */
@RestController
public class WorkflowConfigServiceImpl implements WorkflowConfigService {

	@Autowired
	private JobModelDao jobModelDao;

	@Autowired
	private TaskModelDao workflowTaskModelDao;

	@Override
	public String addJobModel(JobModelDTO jobModel) {
		if (null != jobModelDao.getByName(jobModel.getName())) {
			throw new IllegalArgumentAppException(
					String.format("The WorkflowJobModel[%s] already be  exists", jobModel.getName()));
		}
		JobModel workflowJobModel = new JobModel();
		BeanUtils.copyProperties(jobModel, workflowJobModel);
		workflowJobModel.setId(null);
		jobModelDao.add(workflowJobModel);
		return workflowJobModel.getId();
	}

	@Override
	public JobModelDTO getJobModelById(String id) {
		return Convertor.convert(jobModelDao.get(id), () -> new JobModelDTO());
	}

	@Override
	public List<JobModelDTO> listJobModel() {
		return Convertor.convert(jobModelDao.listAll(), () -> new JobModelDTO());
	}

	@Override
	public String addTaskModel(TaskModelDTO workflowTaskModelDTO) {
		if (null != workflowTaskModelDao.getByName(workflowTaskModelDTO.getName())) {
			throw new IllegalArgumentAppException(
					String.format("The WorkflowTaskModel[%s] of WorkflowJobModel[%s]  already be exists",
							workflowTaskModelDTO.getName(), workflowTaskModelDTO.getJobId()));
		}
		TaskModel workflowTaskModel = new TaskModel();
		BeanUtils.copyProperties(workflowTaskModelDTO, workflowTaskModel);
		workflowTaskModel.setId(null);
		workflowTaskModelDao.add(workflowTaskModel);
		return workflowTaskModel.getId();
	}

	@Override
	public List<TaskModelDTO> listTaskModelByJobId(String jobId) {
		List<TaskModel> workflowTaskModels = workflowTaskModelDao.listByJobId(jobId);
		return Convertor.convert(workflowTaskModels, () -> new TaskModelDTO());
	}
}
