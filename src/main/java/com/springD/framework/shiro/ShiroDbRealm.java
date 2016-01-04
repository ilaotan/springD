package com.springD.framework.shiro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import com.springD.framework.common.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springD.application.system.entity.User;
import com.springD.application.system.service.UserPermissionService;
import com.springD.framework.encoder.Md5PwdEncoder;
import com.springD.framework.utils.RegexValidateUtils;
import com.springD.framework.utils.StringUtils;


//@Component("shiroDbRealm")
/**   
 * @Description: 自定义shiroRAELM
 * @author Chenz
 * @date Nov 13, 2014 1:08:36 PM 
 * @version V1.0   
*/
public class ShiroDbRealm extends AuthorizingRealm{
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	UserPermissionService userPermissionService;
	
	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1;
	public static final int SALT_SIZE = 8;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principals);
			SecurityUtils.getSubject().logout();
			return null;
		}
		
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		if (null != shiroUser) {
			String uid = shiroUser.getId();
			String roleId = shiroUser.getRoleId();
			// 添加角色及权限信息
			SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
			try {
				if (StringUtils.isNotBlank(shiroUser.getCode())) {
					sazi.addRole(shiroUser.getCode());
				}
//				sazi.addRoles(userPermissionService.getRolesAsString(uid));//添加多个角色
				if(shiroUser.getCode().equals(Constants.SUPER_ADMIN_CODE)){
					Set<String> set = new HashSet<String>();
					set.add("*");
					sazi.addStringPermissions(set);
				}else{
					sazi.addStringPermissions(userPermissionService.getPermissionsAsString(uid,roleId));
				}
				logger.debug("当前用户的权限是{}",sazi.getStringPermissions());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return sazi;
		}else{
			return null;
		}
	}	

	/* 
	 * tanliansheng
	 * 2015年1月15日09:04:34
	 * 父类默认方法会拿null。覆盖一下父类方法 使用role的code当缓存的key
	 * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		return "autz-"+shiroUser.getCode();
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		User user = null;
		String username = token.getUsername();
		String loginType = "username";
		try{
			if(RegexValidateUtils.isEmail(username)){
				logger.error("有人用邮箱登陆啦      "+username);
				loginType = "email";
			}else if(RegexValidateUtils.isMobile(username)){
				logger.error("有人用手机号登陆啦    "+username);
				loginType = "mobile";
			}
//			loginType="username";
//			user = userPermissionService.findLoginUser(username, null, loginType);
			user = userPermissionService.findLoginUser(username, null, "username");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new AuthenticationException(e);
		}
		if("mobile".equals(loginType)){
			throw new DisabledAccountException("有人用手机号登陆啦");
		}
		if(null != user){
			if(!user.getStatus().equals("1")){//非正常状态账号 说明被锁定了
				throw new LockedAccountException("账户被锁定或未激活");
			}
			//md5(salt+md5(password))，处理传过来的密码
			String pwd = Md5PwdEncoder.encodePassword(String.valueOf(token.getPassword()), "");
////			String password = Md5PwdEncoder.encodePassword(pwd, user.getSalt());NH-BLHG4a9cc60d6224169fb6e08e1342dd055a3
			String password = user.getSalt() + pwd;
			token.setPassword(password.toCharArray());
			System.out.println( "最终密码是~~~~~~~~~~~~"+Md5PwdEncoder.encodePassword(password,""));
			AuthenticationInfo authinfo = new SimpleAuthenticationInfo(new ShiroUser(user), user.getPassword(), getName());
			return authinfo;
		}else{
			throw new UnknownAccountException("账户不存在");
		}
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
//	@PostConstruct
//	public void initCredentialsMatcher() {
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
//		matcher.setHashIterations(HASH_INTERATIONS);
//		setCredentialsMatcher(matcher);
//	}

}
