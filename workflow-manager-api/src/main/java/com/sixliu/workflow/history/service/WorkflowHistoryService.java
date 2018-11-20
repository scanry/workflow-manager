package com.sixliu.workflow.history.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sixliu.app.user.validation.CurrentUserValidation;
import com.sixliu.workflow.ServiceName;
import com.sixliu.workflow.history.dto.JobRecordDTO;


/**
 * @author:MG01867
 * @date: 2018年7月6日
 * @email:359852326@qq.com
 * @version:
 * @describe 历史记录服务接口
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/history")
public interface WorkflowHistoryService {

    @RequestMapping(value = "/listJobByUserId", method = RequestMethod.POST)
    List<JobRecordDTO> listJobByUserId(@CurrentUserValidation String userId);
}
