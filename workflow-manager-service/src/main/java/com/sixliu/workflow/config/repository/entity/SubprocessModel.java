package com.sixliu.workflow.config.repository.entity;

import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
*@author:MG01867
*@date:2018年11月20日
*@email:359852326@qq.com
*@version:
*@describe 子处理模型
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubprocessModel extends AuditBaseEntity {

	/** 任务id:VARCHAR(36) **/
	private String taskId;
	
	/** 流程id:VARCHAR(36) **/
	private String jobId;
	
	/** 备注:VARCHAR(100) **/
	private String remark;
}
