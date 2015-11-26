package com.springD.application.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springD.application.system.entity.Menu;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MenuMapper {

	public void save(Menu menu);
	
	public void update(Menu menu);
	
	public Menu findById(String id);
	
	public List<Menu> findAll();
	
	public List<Menu> findAll4Tree();
	
	public int delete(String id);
	
	public int deleteByPid(@Param(value="pid")String pid);
	
	public List<Menu> findByParentIdsLike(@Param(value="str")String str);
	
	public List<Menu> findBeforeInsert(Menu menu);
}
