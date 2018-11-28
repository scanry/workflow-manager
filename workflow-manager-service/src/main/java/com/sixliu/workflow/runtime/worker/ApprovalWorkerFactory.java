package com.sixliu.workflow.runtime.worker;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sixliu.workflow.common.constant.ScriptType;
import com.sixliu.workflow.runtime.repository.entity.Worker;
import com.sixliu.workflow.runtime.worker.ApprovalWorker;

/**
 * @author:MG01867
 * @date:2018年9月7日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Component
public class ApprovalWorkerFactory implements ApplicationContextAware {

	@Autowired
	private RestTemplate restTemplate;

	private ApplicationContext applicationContext;
	

	public ApprovalWorker getOrNew(Worker worker) {
		if (ScriptType.URL == worker.getScriptType()) {
			return new RemoteApprovalWorkerProxy(restTemplate, worker);
		} else if (ScriptType.JAVA_CLASS == worker.getScriptType()) {
			try {
				return applicationContext.getBean(worker.getScript(), ApprovalWorker.class);
			} catch (Exception exception) {
				throw new UnsupportedOperationException(
						String.format("The worker's scriptType[%s] is unsupported", worker.getScriptType()), exception);
			}
		} else {
			throw new UnsupportedOperationException(
					String.format("The worker's scriptType[%s] is unsupported", worker.getScriptType()));
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
