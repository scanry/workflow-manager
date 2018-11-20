package com.sixliu.workflow.runtime.repository.entity;

import com.sixliu.workflow.common.constant.EventType;
import com.sixliu.workflow.common.repository.entity.BaseEntity;

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
public class WorkflowEvent extends BaseEntity {
	
	/**流程任务id**/
	private String taskId;
	
	/**事件类型**/
	private EventType type;
	
	/**事件执行脚本**/
	private String executeScript;
}
