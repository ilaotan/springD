package com.springD.framework.mapper;

import com.springD.framework.mapper.provider.MyProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;



public interface getListMapper<T> {
	
	//如果非select 需要替换成对应的注解
    @SelectProvider(type = MyProvider.class, method = "dynamicSQL")
    List<T> getList(List<T> list);
}