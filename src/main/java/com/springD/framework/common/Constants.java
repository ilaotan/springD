package com.springD.framework.common;


/**   
 * @Description: 静态常量类
 * @author Chenz
 * @date Nov 13, 2014 1:21:25 PM 
 * @version V1.0   
*/
public class Constants {
	
	/**
	 * 系统后台登录地址
	 */
	public static final String ADMIN_LOGIN_URL = "/system/login";
	/**
	 * 系统后台前缀
	 */
	public static final String ADMIN_PREFIX = "/system";
	/**
	 * 系统后台首页地址
	 */
	public static final String ADMIN_INDEX = "/system/admin";
	
	/**
	 * 验证码名称
	 */
	public static final String CAPTCHA_PARAM = "captcha";
	
	//超级管理员的角色code
	public static final String SUPER_ADMIN_CODE = "superadmin";
	
	/**
	 * 登录失败显示验证码的次数
	 */
	public static final Integer LOGIN_TRY_TIME = 3;
	
	/**
	 * 表单日期格式
	 */
	public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String EXCEL_DATE_FORMAT = "yyyy/MM/dd";
	
	
	public static final String BUTTON_TEXT_SUBMIT = "保存";
	
	public static final String BUTTON_TEXT_SAVE = "提交";
	
	public static final String BUTTON_TEXT_DELETE = "删除";
	
	public static final String BUTTON_TEXT_ADD = "保存";
	
	public static final String MESSAGE_TYPE_SUCCESS = "info";
	
	public static final String MESSAGE_TYPE_ERROR = "danger";
	
	public static final String CONTRACTOR_USER_PREFIX = "g_";
	
	/**
	 * 在 @com.springD.framework.shiro.SystemAuthenticationFilter.java的onLoginSuccess方法里使用
	 * 往session里塞当前登录的用户名 
	 */
	public static final String LOGIN_USERNAME="username";
	
}
