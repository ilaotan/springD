package com.springD.application.system.dao;

import com.springD.application.system.entity.Role;
import com.springD.application.system.entity.User;
import com.springD.framework.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserPermissionMapper extends MyMapper<User> {
	
	/**
	 * 查询用户角色
	 * @param uid
	 * @return
	 */
	List<Role> findRoleByUid(String uid);
	
	/**
	 * 查找登录用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findLoginUser(Map<String, Object> map);
	
	String getRoleMenus(String id);
	
	List<String> findMenusByIds(@Param(value="id")String id);
	
	int saveUser(User user);
	
	int updateUserByUid(Map map);
	
	User getOneByMap(Map map);
	
	List<User> getAllForPage(Map map);
	
	int deletByUid(int uid);
	
	List<User> findBeforeInsert(User user);
	
	int addManagerKinderRelation(Map map);
	
	List<User> findUserByKinderId(List list);
}
