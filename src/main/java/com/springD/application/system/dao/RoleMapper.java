package com.springD.application.system.dao;

import com.springD.application.system.entity.Role;
import com.springD.framework.mapper.MyMapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper  extends MyMapper<Role> {

	int save(Role role);
	
	int update(Role role);
	
	Role findById(String id);
	
	List<Role> findAll(Map param);
	
	int delete(String id);
	
	int savePermission(Role role);
	
	List<Role> findBeforeInsert(Role role);
}
