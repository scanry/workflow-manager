package com.sixliu.workflow.common.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sixliu.workflow.ServiceName;

/**
 * @author: sixliu
 * @email: 359852326@qq.com
 * @date: 2018年9月7日 下午8:18:45
 * @version V1.0
 * @description:
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/system")
public interface SystemService {

	@RequestMapping(value = "getUser", method = RequestMethod.GET)
	@ResponseBody
	String getSytemUser();
}
