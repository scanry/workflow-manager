package com.sixliu.workflow.model.repository.entity;

import com.sixliu.workflow.common.constant.ScriptType;
import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年9月11日
 * @email:359852326@qq.com
 * @version:
 * @describe 流程task自动处理worker模型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WorkerModel extends AuditBaseEntity {

	/** 名称:VARCHAR(20) **/
	private String name;

	/** 任务模型id:VARCHAR(36) **/
	private String taskId;
	
	/** 自动处理worker扫描任务间隔时间:INT(11) **/
	private long checkInterval;

	/** 脚本类型:VARCHAR(20) **/
	private ScriptType scriptType;

	/** 脚本:VARCHAR(200) **/
	private String script;
	
	/** 备注:VARCHAR(100) **/
	private String remark;
}