package com.springD.application.system.dao;

import java.util.List;
import java.util.Map;

import com.springD.application.system.entity.Role;
import com.springD.application.system.entity.User;
import com.springD.framework.mapper.MyMapper;
import com.springD.framework.persistence.annotation.MyBatisDao;

public interface RoleMapper  extends MyMapper<Role> {

	int save(Role role);
	
	int update(Role role);
	
	Role findById(String id);
	
	List<Role> findAll(Map param);
	
	int delete(String id);
	
	int savePermission(Role role);
	
	List<Role> findBeforeInsert(Role role);
}
