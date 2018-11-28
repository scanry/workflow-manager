package com.sixliu.workflow.runtime.worker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sixliu.workflow.runtime.dto.TaskProcessResult;
import com.sixliu.workflow.runtime.repository.entity.Worker;
import com.sixliu.workflow.runtime.worker.ApprovalWorker;

/**
 * @author:MG01867
 * @date:2018年9月7日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class RemoteApprovalWorkerProxy implements ApprovalWorker{

	private RestTemplate restTemplate;

	private Worker workflowTaskWorker;

	public RemoteApprovalWorkerProxy(RestTemplate restTemplate,Worker workflowTaskWorker) {
		this.restTemplate = restTemplate;
		this.workflowTaskWorker = workflowTaskWorker;
	}

	@Override
	public TaskProcessResult process(String taskId) {
		ResponseEntity<TaskProcessResult> responseEntity = restTemplate.postForEntity(workflowTaskWorker.getId(),
				taskId, TaskProcessResult.class);
		return responseEntity.getBody();
	}

}
