package com.sixliu.workflow.config.repository.entity;

import com.sixliu.workflow.common.constant.ScriptType;
import com.sixliu.workflow.common.constant.EventType;
import com.sixliu.workflow.common.repository.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年9月6日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 流程task事件模型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventModel extends AuditBaseEntity {
	
	/**流程任务模型id:VARCHAR(36)**/
	private String taskId;
	
	/**事件类型:VARCHAR(20)**/
	private EventType type;
	
	/**事件执行脚本类型:VARCHAR(20)**/
	private ScriptType scriptType;
	
	/**事件执行脚本:VARCHAR(200)**/
	private String script;
	
	/**备注:VARCHAR(100)**/
	private String remark;
}
