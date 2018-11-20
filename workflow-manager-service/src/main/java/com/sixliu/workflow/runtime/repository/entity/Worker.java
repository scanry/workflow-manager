package com.sixliu.workflow.runtime.repository.entity;

import com.sixliu.workflow.config.repository.entity.WorkerModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年9月11日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Worker extends WorkerModel {

	/** 原始数据id:VARCHAR(36) **/
	private String rawId;

	/** 原始数据版本:INT(11) **/
	private int rawVersion;
}