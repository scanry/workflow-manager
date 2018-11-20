package com.sixliu.workflow.history.service;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.sixliu.workflow.history.dto.JobRecordDTO;
import com.sixliu.workflow.history.service.WorkflowHistoryService;


/**
*@author:MG01867
*@date:2018年9月6日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
@RestController
public class WorkflowHistoryServiceImpl implements WorkflowHistoryService{

	@Override
	public List<JobRecordDTO> listJobByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
