package com.springD.application.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.springD.application.system.entity.Role;
import com.springD.application.system.entity.User;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface UserPermissionMapper {
	
	/**
	 * 查询用户角色
	 * @param uid
	 * @return
	 */
	public List<Role> findRoleByUid(String uid);
	
	/**
	 * 查找登录用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User findLoginUser(Map<String, Object> map);
	
	public String getRoleMenus(String id);
	
	public List<String> findMenusByIds(@Param(value="id")String id);
	
	public int saveUser(User user);
	
	public int updateUserByUid(Map map);
	
	public User getOneByMap(Map map);
	
	public List<User> getAllForPage(Map map);
	
	public int deletByUid(int uid);
	
	public List<User> findBeforeInsert(User user);
	
	public int addManagerKinderRelation(Map map);
	
	public List<User> findUserByKinderId(List list);
}
