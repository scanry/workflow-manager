package com.sixliu.workflow.runtime.component.worker;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sixliu.workflow.runtime.component.AutoProcessWorker;
import com.sixliu.workflow.runtime.repository.entity.Worker;

/**
*@author:MG01867
*@date:2018年9月7日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
@Component
public class RemoteAutoProcessWorkerFactory {

	private RestTemplate  restTemplate;
	
	public AutoProcessWorker getOrNew(Worker workflowTaskWorker) {
		return new RemoteAutoProcessWorker(restTemplate, workflowTaskWorker);
	}
}
