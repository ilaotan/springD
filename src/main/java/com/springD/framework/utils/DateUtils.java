/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.springD.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.springD.framework.common.Constants;
import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
    
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static int getAgeByBrithday(String brithday) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatDate = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);
			String currentTime = formatDate.format(calendar.getTime());
			Date today = formatDate.parse(currentTime);
			Date brithDay = formatDate.parse(brithday);
			return today.getYear() - brithDay.getYear();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 与当前日期比较大小，是今天以前日期小于0，以后日期大于0
	 * @param target
	 * @return
	 */
	public static int compareToToday(Date target) {
		
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatDate = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);
			String currentTime = formatDate.format(calendar.getTime());
			Date today = formatDate.parse(currentTime);
			return target.compareTo(today);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 按星期比较日期大小，是今天以前日期小于0，以后日期大于0
	 * @param target
	 * @return
	 */
	public static int compareToByWeek(Date target) {
		
		try {
			
			int returnInt = 0;
			Calendar calendar = Calendar.getInstance();
			Calendar targetCal = Calendar.getInstance();
			targetCal.setTime(target);

			calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK) + 2);

			calendar.get(Calendar.DAY_OF_YEAR);
			targetCal.get(Calendar.DAY_OF_YEAR);
			calendar.get(Calendar.YEAR);
			targetCal.get(Calendar.YEAR);
			if (targetCal.get(Calendar.YEAR) < calendar.get(Calendar.YEAR)) {
				returnInt = -1;
			} else if (calendar.get(Calendar.YEAR) < targetCal.get(Calendar.YEAR)) {
				returnInt = 1;
			} else {
				if (targetCal.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)) {
					returnInt = -1;
				} else if (calendar.get(Calendar.DAY_OF_YEAR) < targetCal.get(Calendar.DAY_OF_YEAR)) {
					returnInt = 1;
				} else {
					returnInt = 0;
				}
			}

			return returnInt;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}
}
