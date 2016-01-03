package com.springD.application.system.dao;

import java.util.List;

import com.springD.application.system.entity.Org;
import com.springD.framework.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface OrgMapper {

	public void save(Org org);
	
	public void update(Org org);
	
	public Org findById(String id);
	
	public List<Org> findByParentIdsLike(String str);
	
	public List<Org> findAll();
	
	public void delete(String id);
}
