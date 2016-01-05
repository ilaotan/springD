package com.springD.application.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springD.application.system.entity.Menu;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MenuMapper {

	void save(Menu menu);
	
	void update(Menu menu);
	
	Menu findById(String id);
	
	List<Menu> findAll();
	
	List<Menu> findAll4Tree();
	
	int delete(String id);
	
	int deleteByPid(@Param(value="pid")String pid);
	
	List<Menu> findByParentIdsLike(@Param(value="str")String str);
	
	List<Menu> findBeforeInsert(Menu menu);
}
