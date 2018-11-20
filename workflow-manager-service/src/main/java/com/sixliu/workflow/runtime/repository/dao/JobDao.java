package com.sixliu.workflow.runtime.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sixliu.workflow.common.repository.dao.BaseDao;
import com.sixliu.workflow.runtime.repository.entity.Job;

/**
 * @author:MG01867
 * @date:2018年8月13日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 流程job数据访问接口
 */
public interface JobDao extends BaseDao<Job> {

	List<Job> listLockJobs();

	int tryLock(@Param("id") String id, @Param("lockUrl") String lockUrl, @Param("version") int version);

	int unlock(@Param("id") String id, @Param("version") int version);
}
