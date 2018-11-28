package com.sixliu.workflow.model.repository.entity;

import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年9月6日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 流程job模型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JobModel extends AuditBaseEntity {

	/** 编码:VARCHAR(10) **/
	private String code;

	/** 名称:VARCHAR(20) **/
	private String name;

	/** 创建该流程作业角色id:VARCHAR(36) **/
	private String createRoleId;
	
	/** 备注:VARCHAR(100) **/
	private String remark;
}
