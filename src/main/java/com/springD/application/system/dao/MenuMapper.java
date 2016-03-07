package com.springD.application.system.dao;

import com.springD.application.system.entity.Menu;
import com.springD.framework.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends MyMapper<Menu> {

	void save(Menu menu);
	
	void update(Menu menu);
	
	Menu findById(String id);
	
	List<Menu> findAll();
	
	List<Menu> findAll4Tree();
	
	int deleteById(String id);

	int deleteByPid(@Param(value="pid")String pid);
	
	List<Menu> findByParentIdsLike(@Param(value="str")String str);
	
	List<Menu> findBeforeInsert(Menu menu);
}
