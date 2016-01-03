/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.springD.framework.utils;

import java.security.SecureRandom;
import java.util.UUID;

import com.springD.framework.encoder.Md5PwdEncoder;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author calvin
 * @version 2013-01-15
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月4日 上午9:34:21
	 * @方法描述： 盐 密码 2层加密---> md5(slat+md5(password))
	 * @修改记录：
	 * @since  1.0.0
	*/
	public static  String md5Password(String salt,String password){
		//密码 需要转码
		password = Md5PwdEncoder.encodePassword(String.valueOf(password), "");
		password = salt + password;
		password = Md5PwdEncoder.encodePassword(password,"");
		return password;
	}
	
	public static void main(String[] args){
		String id = Identities.uuid();
		String id2 = Identities.uuid2();
		System.out.println(id);
		System.out.println(id2);
		System.out.println(Identities.randomLong());
		System.out.println(Identities.randomBase62(8));
	}
}
