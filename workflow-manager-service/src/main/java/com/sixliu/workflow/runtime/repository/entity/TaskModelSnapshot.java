package com.sixliu.workflow.runtime.repository.entity;

import com.sixliu.workflow.model.repository.entity.TaskModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年11月16日
 * @email:359852326@qq.com
 * @version:
 * @describe 任务模型快照
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskModelSnapshot extends TaskModel {

	private String rawId;

	private int rawVersion;
}
