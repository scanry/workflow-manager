package com.sixliu.workflow.config.repository.dao;

import java.util.List;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.config.repository.entity.EventModel;

/**    
 * @author: sixliu
 * @email:  359852326@qq.com
 * @date:   2018年9月6日 下午11:04:44   
 * @version V1.0 
 * @Description:TODO
 */
public interface EventModelDao extends BaseDao<EventModel>{

	List<EventModel> listByTaskId(String taskId);
}
