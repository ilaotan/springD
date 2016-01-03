package com.springD.application.system.dao;

import java.util.List;
import java.util.Map;

import com.springD.application.system.entity.Role;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface RoleMapper {

	public void save(Role role);
	
	public void update(Role role);
	
	public Role findById(String id);
	
	public List<Role> findAll(Map param);
	
	public int delete(String id);
	
	public void savePermission(Role role);
	
	public List<Role> findBeforeInsert(Role role);
}
