package com.springD.application.system.dao;

import java.util.List;

import com.springD.application.system.entity.Org;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrgMapper {

	void save(Org org);
	
	void update(Org org);

	Org findById(String id);
	
	List<Org> findByParentIdsLike(String str);
	
	List<Org> findAll();
	
	void delete(String id);
}
