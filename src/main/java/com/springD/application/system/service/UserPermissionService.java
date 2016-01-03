package com.springD.application.system.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springD.framework.datasource.DatabaseContextHolder;

import com.springD.application.system.dao.UserPermissionMapper;
import com.springD.application.system.entity.Role;
import com.springD.application.system.entity.User;

/**
 * 类描述：
 * <p> 
 * @创建人：tanliansheng
 * @创建时间： 2014年11月7日 上午11:41:01
 * @version 1.0
 */
@Service
public class UserPermissionService {
	@Autowired
	private UserPermissionMapper userPermissionMapper;

	/**
	 * 获取用户所有角色
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public List<Role> findRoleByUserId(String uid) throws Exception {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		if (StringUtils.isBlank(uid)) {
			return null;
		}
		List<Role> list = userPermissionMapper.findRoleByUid(uid);
		return list;
	}

	/**
	 * 获取用户角色set
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Set<String> getRolesAsString(String uid) throws Exception {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		List<Role> list = findRoleByUserId(uid);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		for(Role r:list){
			set.add(r.getCode());
		}
		return set;
	}

	/**
	 * 获取用户的所有权限
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public  Set<String> getPermissionsAsString(String uid, String roleId) throws Exception{
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		String menuIds = userPermissionMapper.getRoleMenus(roleId);
		List<String> urlList = userPermissionMapper.findMenusByIds(menuIds);

		Set<String> set=new HashSet<String>();
		for(String u:urlList){
			set.add(u);
		}

		return set;
	}

	/**
	 * 根据用户名和密码查找登录的用户
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User findLoginUser(String username, String password, String type){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		if (StringUtils.isBlank(username)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(type)){
			map.put(type, username);
		}else{
			map.put("username", username);
		}
		map.put("password", password);
		return userPermissionMapper.findLoginUser(map);
	}

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月3日 下午8:04:39
	 * @方法描述： 创建用户(用户名 密码 )
	 * @修改记录：
	 * @since  1.0.0
	 */
	public int saveUser(User user){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.saveUser(user);
	}

	/**
	 * 修改用户信息
	 * @Description: 修改用户信息（主要目的是修改密码）
	 * @param map
	 * @return
	 */
	public int updateUserByUid(Map map){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.updateUserByUid(map);
	}

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月7日 上午11:41:19
	 * @方法描述：密码找回时,用的方法 Map里可传 email username uid
	 * @修改记录：
	 * @since  1.0.0
	 */
	public User getOneByMap(Map map){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.getOneByMap(map);
	};

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月10日 上午11:08:50
	 * @方法描述：分页 获取所有用户信息
	 * @修改记录：
	 * @since  1.0.0
	 */
	public List<User> getAllForPage(Map map){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.getAllForPage(map);
	}

	/**
	 * @创建人：tanliansheng
	 * @创建时间： 2014年11月10日 下午2:47:46
	 * @方法描述：根据id 删除用户
	 * @修改记录：
	 * @since  1.0.0
	 */
	public int deletByUid(int uid){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.deletByUid(uid);
	}

	/**
	 * 查找重复数据
	 * @Description: 添加用户前查找重复数据
	 * @param user : 用户信息
	 * @return
	 */
	public List<User> findBeforeInsert(User user) {
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.findBeforeInsert(user);
	}

	/** 根据list里的幼儿园id 拿对应的用户信息
	 * @param list
	 * @return
	 */
	public List<User> findUserByKinderId(List list){
		DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DATA_SOURCE_ONE_WEBPLATFORM);
		return userPermissionMapper.findUserByKinderId(list);
	}
}