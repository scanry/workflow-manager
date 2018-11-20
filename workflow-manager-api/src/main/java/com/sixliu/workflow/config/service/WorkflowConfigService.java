package com.sixliu.workflow.config.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sixliu.workflow.ServiceName;
import com.sixliu.workflow.config.dto.JobModelDTO;
import com.sixliu.workflow.config.dto.TaskModelDTO;

/**
 * @author:MG01867
 * @date: 2018年9月6日
 * @email:359852326@qq.com
 * @version:
 * @describe 流程配置服务
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping(value = "/config")
public interface WorkflowConfigService {

	/**
	 * 新增job模型
	 * 
	 * @param jobModel
	 * @return
	 */
	@RequestMapping(value = "/addJobModel", method = RequestMethod.POST)
	@ResponseBody
	String addJobModel(@Valid @RequestBody JobModelDTO jobModel);

	/**
	 * 通过id获取job模型
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getJobModelById", method = RequestMethod.POST)
	@ResponseBody
	JobModelDTO getJobModelById(@NotBlank(message = "The id must be not blank") @RequestParam(name = "id") String id);

	/**
	 * 获取所有的job模型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listJobModel", method = RequestMethod.POST)
	@ResponseBody
	List<JobModelDTO> listJobModel();

	/**
	 * 新增task模型
	 * 
	 * @param workflowTaskModel
	 * @return
	 */
	@RequestMapping(value = "/addTaskModel", method = RequestMethod.POST)
	@ResponseBody
	String addTaskModel(TaskModelDTO taskModel);

	/**
	 * 通过job模型id获取它的task模型集合
	 * 
	 * @param jobId
	 * @return
	 */
	@RequestMapping(value = "/listTaskModelByJobId", method = RequestMethod.POST)
	@ResponseBody
	List<TaskModelDTO> listTaskModelByJobId(
			@NotBlank(message = "The jobId must be not blank") @RequestParam(name = "jobId") String jobId);
}
