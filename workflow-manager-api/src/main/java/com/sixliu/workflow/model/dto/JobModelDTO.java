package com.sixliu.workflow.model.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sixliu.workflow.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
*@author:MG01867
*@date:2018年7月6日
*@E-mail:359852326@qq.com
*@version:
*@describe 流程模型
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JobModelDTO extends BaseDTO{


	/** 编码**/
	@NotBlank(message="The code must be not blank")
	@Size(min=1,max=10)
	private String code;
	
	/** 流程作业模型名称 **/
	@NotBlank(message="The name must be not blank")
	@Size(min=1,max=20)
	private String name;
	
	/**允许创建该流程作业角色id**/
	//@RoleValidation(message="The createUser is illegal")
	private String createRoleId;
	
	/**数据备注:VARCHAR(100)**/
	private String remark;
}
