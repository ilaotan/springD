package com.springD.framework.config;


/**   
 * @Description: 静态常量类
*/
public class StaticConstants {
	
	/**
	 * 系统后台登录地址
	 */
	public static final String ADMIN_LOGIN_URL = "/system/login.do";
	/**
	 * 系统后台前缀
	 */
	public static final String ADMIN_PREFIX = "/system";
	/**
	 * 系统后台首页地址
	 */
	public static final String ADMIN_INDEX = "/system/admin.do";
	
	/**
	 * 幼儿园后台登录地址
	 */
	public static final String KINDER_LOGIN_URL = "/kindergarten/login.do";
	/**
	 * 幼儿园后台首页地址
	 */
	public static final String KINDER_INDEX = "/kindergarten/admin.do";
	
	/**
	 * 工程商地址前缀
	 */
	public static final String CONTRACTOR_PREFIX = "/contractor";
	
	/**
	 * 系统后台登录地址
	 */
	public static final String CONTRACTOR_LOGIN_URL = "/contractor/login.do";
	
	/**
	 * 工程商
	 */
	public static final String CONTRACTOR_INDEX = "/contractor/admin.do";
	
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
	 * 设备管理 添加时使用的超级幼儿园的id 默认是1
	 */
	public static final int kid = 1;
	
	/**
	 * 匿名访问使用的幼儿园
	 */
	public static final int kid2 = 26;
	
	/**
	 * (新闻页面使用的七牛公有仓库的名字,特殊名字(名字以/开头的)请自行加@符号 比如 名字为/img.jpg的图片 
	 * 	http://7te8dq.com1.z0.glb.clouddn.com/@/img.jpg )
	 */
	public static final String qiniuImgUrl="http://7te8dq.com1.z0.glb.clouddn.com/";
	
	/**
	 * 在 @com.basep.framework.shiro.SystemAuthenticationFilter.java的onLoginSuccess方法里使用 
	 * 往session里塞当前登录的用户名 
	 */
	public static final String LOGIN_USERNAME="username";
	
	/**
	 * 配置在七牛上的图片缩放规则 
	 * 修改为固定字符串 从数据库拿字段时替换成别的.
	 */
	public static final String mobileCopress="";
	
	/**
	 * 在线浏览的缩略图缩放规则
	 */
	public static final String imageOnlineCompress="-v001";
}
