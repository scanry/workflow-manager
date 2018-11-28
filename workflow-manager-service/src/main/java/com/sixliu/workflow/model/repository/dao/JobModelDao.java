package com.sixliu.workflow.model.repository.dao;

import java.util.List;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.model.repository.entity.JobModel;

/**
*@author:MG01867
*@date:2018年8月13日
*@E-mail:359852326@qq.com
*@version:
*@describe 流程作业模型数据访问dao
*/
public interface JobModelDao extends BaseDao<JobModel>{

	JobModel getByName(String name);
	
	List<JobModel> listAll();
}
