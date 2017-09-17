package com.reger.datasource.core;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 重试已经失效的数据源 
 * @author leige 
 *
 */
public class DataSourceInvalidRetry implements ApplicationContextAware {
	
	ApplicationContext applicationContext;
	Map<String, DynamicDataSource> dataSources;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	@Scheduled(initialDelay=10*60*1000,fixedDelay=60*1000) void runTest(){
		 dataSources = dataSources!=null?dataSources:applicationContext.getBeansOfType(DynamicDataSource.class);
		 dataSources.forEach((k,d)->d.retryFailureSlavesDataSource());
	}
}
