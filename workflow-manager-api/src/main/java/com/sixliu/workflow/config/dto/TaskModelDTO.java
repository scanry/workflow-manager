package com.sixliu.workflow.config.dto;

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

	/** 流程作业任务名称 **/
	private String name;

	/** 流程作业模型id **/
	private String jobId;

	/** 流程作业任务所处阶段 **/
	private int phase;

	/** 流程作业任务处理类型 **/
	private TaskType type;

	/** 流程作业任务处理worker **/
	private String worker;
	
	/**数据备注:VARCHAR(100)**/
	private String remark;
}
