package com.sixliu.workflow.runtime.repository.entity;


import com.sixliu.workflow.common.constant.JobStatus;
import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
*@author:MG01867
*@date:2018年9月6日
*@E-mail:359852326@qq.com
*@version:
*@describe 流程job
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Job extends AuditBaseEntity{

	/**流程作业名称**/
	private String name;
	
	/**流程作业id**/
	private String modelId;
	
	/**流程作业状态**/
	private JobStatus status;
	
	/**当前流程任务id**/
	private String currentTaskId;
	
	/**流程作业id**/
	private String currentPhase;
	
	/**保存锁着流程作业的服务url**/
	private String lockUrl;
}
