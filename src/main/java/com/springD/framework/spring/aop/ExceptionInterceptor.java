/**
 * Copyright (c) 2014-2015 爱维宝贝web端团队   All rights reserved
 *
 * Base on awframework,powered by aiwei web team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.springD.framework.spring.aop;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springD.framework.shiro.ShiroUser;
import com.springD.framework.utils.BrowserUtils;
import com.springD.framework.utils.UserUtils;


/** 
 * 类描述： 当service处理数据时,抛出异常时,本AOP会拦截到出异常的方法中传入的异常参数(1个 or 多个),并输出,供开发者分析
 * <p> 
 * <p>
 * <p>
 * <p>
 * @创建人：tanliansheng
 * @创建时间： 2015年6月25日 下午6:40:58
 * @version 1.0
 */
@Component
@Aspect
public class ExceptionInterceptor {

	protected static final Logger LOG = LoggerFactory.getLogger(ExceptionInterceptor.class);

	@AfterThrowing(value="execution (* com.tan.application.system.service..*.*(..))",throwing="error")
	public void doGetErrorInfo(JoinPoint jp,Throwable error){
		ShiroUser user =UserUtils.getShiroUser();
		//获得request 从request里拿相关内容
		ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();
		HttpServletRequest req = (HttpServletRequest) request;
		String broswer = BrowserUtils.checkBrowse(req);
		try {
			Object[] args = jp.getArgs(); 
			Object functionName3 =  jp.getSignature().toShortString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
