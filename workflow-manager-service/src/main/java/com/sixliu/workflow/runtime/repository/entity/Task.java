package com.sixliu.workflow.runtime.repository.entity;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年9月6日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends AuditBaseEntity {
	
	/** jobId:VARCHAR(36) **/
	private String jobId;
	
	/** 流程作业任务名称 **/
	private String name;
	
	/** 流程作业任务模型 id **/
	private String modelId;

	/** 流程作业任务所处阶段 **/
	private int phase;
	
	/** 流程作业任务所处状态 **/
	private TaskStatus status;

	/** 流程作业任务处理类型 **/
	private TaskType type;

	/** 流程作业任务处理worker或者流程作业任务所属角色 **/
	private String roleId;
	
	/** 流程作业任务处理worker或者流程作业任务所属角色 **/
	private String worker;

	/** 流程作业任务所属用户 **/
	private String ownerUserId;
	
	/**流程任务内部意见**/
	private String innerOpinion;
	
	/**流程任务外部意见**/
	private String outerOpinion;
}
