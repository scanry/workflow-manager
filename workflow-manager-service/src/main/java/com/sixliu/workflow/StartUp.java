package com.sixliu.workflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sixliu.workflow.config.repository.dao.JobModelDao;
import com.sixliu.workflow.history.repository.dao.JobRecordDao;
import com.sixliu.workflow.runtime.repository.dao.JobDao;


/**
 * @author:MG01867
 * @date:2018年6月15日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 服务启动类 
 */
@SpringCloudApplication
@EnableFeignClients(basePackageClasses = { com.sixliu.app.ServiceName.class})
@EnableTransactionManagement 
@MapperScan(basePackageClasses= {JobModelDao.class,JobDao.class,JobRecordDao.class})
public class StartUp implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StartUp.class,args);
	}
}