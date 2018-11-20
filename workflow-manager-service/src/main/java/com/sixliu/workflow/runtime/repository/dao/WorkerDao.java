package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.runtime.repository.entity.Worker;

/**
*@author:MG01867
*@date:2018年9月11日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public interface WorkerDao extends BaseDao<Worker>{

	Worker getByTaskId(String taskId);
	
	List<Worker> listAll();
}
