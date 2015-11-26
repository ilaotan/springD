/**
 * Copyright (c) 2014-2015 爱维宝贝web端团队   All rights reserved
 *
 * Base on awframework,powered by aiwei web team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.springD.framework.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springD.framework.datasource.DatabaseContextHolder;
import com.springD.framework.spring.annotation.CustomDataSource;

/**   
 * @Description: 数据源切换的AOP处理类
 * @author Chenz
 * @date Nov 13, 2014 1:01:16 PM 
 * @version V1.0   
*/
public class DynamicDataSourceAspect {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	//前置通知
	public void beforeAdvice(JoinPoint pjp) throws Throwable{
		logger.debug("===============before advice");
		//注解解析
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		boolean flag = method.isAnnotationPresent(CustomDataSource.class);
		if(flag){
			CustomDataSource cds = method.getAnnotation(CustomDataSource.class);
			DatabaseContextHolder.setCustomerType(cds.value());
			logger.debug(cds.value());
		}
	}
	//后置最终通知
	public void afterFinallyAdvice(){
		logger.debug("===============after finally advice");
		//是否切换回原库，待处理
	}
}
