package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sixliu.workflow.common.constant.TaskStatus;
import com.sixliu.workflow.runtime.repository.entity.Task;

/**
 * @author:MG01867
 * @date:2018年8月13日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public interface TaskDao {

	int insert(Task workflowTask);

	Task get(String id);
	
	Task getByJobIdForPooling(String jobId);

	List<Task> listByRoleId(String roleId);
	
	List<Task> listByRoleIdAndStatus(@Param("roleId") String roleId,@Param("status") TaskStatus status);
	
	List<Task> listForTimingScan(@Param("taskModeId") String taskModeId);

	int update(Task workflowTask);
	
	int updateOwnerUserIdAndStatus(String id,String ownerUserId,TaskStatus status,String updateUserid,int version);
	
	int updateStatus(String id,TaskStatus status,String updateUserid,int version);
}
