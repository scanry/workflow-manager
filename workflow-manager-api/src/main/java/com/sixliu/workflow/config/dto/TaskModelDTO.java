package com.sixliu.workflow.config.dto;

import com.sixliu.workflow.common.constant.PhaseType;
import com.sixliu.workflow.common.constant.TaskType;
import com.sixliu.workflow.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**    
 * @author: sixliu
 * @email:  359852326@qq.com
 * @date:   2018年9月10日 下午10:44:22   
 * @version V1.0 
 * @Description:TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskModelDTO extends BaseDTO{

	/** 流程作业模型id:VARCHAR(36) **/
	private String jobId;
	
	/** 流程作业任务名称:VARCHAR(20) **/
	private String name;

	/** 阶段类型 :VARCHAR(20)**/
	private PhaseType phaseType;

	/** 流程作业任务所处阶段:INT(4) **/
	private int phase;
	
	/** 流程作业任务处理类型:VARCHAR(20) **/
	private TaskType type;
	
	/** 授权角色id:VARCHAR(36) **/
	private String grantRoleId;
	
	/** 跳过脚本:VARCHAR(200) **/
	private String skipScript;
	
	/** 备注:VARCHAR(100) **/
	private String remark;
}
