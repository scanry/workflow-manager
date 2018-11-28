package com.sixliu.workflow.model.repository.dao;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.model.repository.entity.SubprocessModel;

/**
*@author:MG01867
*@date:2018年11月20日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public interface SubprocessModelDao extends BaseDao<SubprocessModel>{

	SubprocessModel getByTaskId(String taskId);
}
