package com.sixliu.workflow.dao;

import java.util.List;

import com.sixliu.workflow.entity.WorkflowEvent;

/**    
 * @author: sixliu
 * @email:  359852326@qq.com
 * @date:   2018年9月6日 下午11:03:10   
 * @version V1.0 
 * @Description:TODO
 */
public interface WorkflowEventDao {

	WorkflowEvent get(String id);
	
	int insert(WorkflowEvent workflowEvent);
	
	List<WorkflowEvent> listByTaskId(String taskId);
}
