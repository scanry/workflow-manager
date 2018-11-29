package com.sixliu.workflow.common.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sixliu
 * @email: 359852326@qq.com
 * @date: 2018年9月7日 下午8:21:22
 * @version V1.0
 * @Description:服务uuid
 */
@RestController
public class SystemServiceImpl implements SystemService {

	public final static String UUID = java.util.UUID.randomUUID().toString();
	
	@Value("${spring.cloud.client.ip-address}")
	private String ip;

	@Value("${server.port}")
	private int port;
	
	private String systemUser;
	
	@PostConstruct
	public void init() {
		this.systemUser=String.format("http://%s:%s/%s/%s",ip,port,UUID);
	}

	@Override
	public String getSytemUser() {
		return systemUser;
	}
}
