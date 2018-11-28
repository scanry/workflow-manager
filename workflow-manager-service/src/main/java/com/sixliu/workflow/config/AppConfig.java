package com.sixliu.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
*@author:MG01867
*@date:2018年11月28日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@Configuration
public class AppConfig {

	@Bean
	public RestTemplate customRestTemplate() {
		return new RestTemplate();
	}
}
