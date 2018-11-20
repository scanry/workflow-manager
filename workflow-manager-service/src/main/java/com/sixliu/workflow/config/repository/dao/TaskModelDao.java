package com.sixliu.workflow.config.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.config.repository.entity.TaskModel;

/**
*@author:MG01867
*@date:2018年8月13日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public interface TaskModelDao extends BaseDao<TaskModel>{

	TaskModel getByName(String name);
	
	List<TaskModel> listByJobId(String jobId);
	
	TaskModel getByJobIdAndPhase(@Param("jobId") String jobId,@Param("phase") int phase);
}
