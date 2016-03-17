package com.springD.framework.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 *  自定义接口的实现
 * @author tanliansheng
 *
 */
public class MyProvider extends MapperTemplate {
	
	public MyProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
	
	/**
    * 拼sql语句 select xx from xx where id=?
    * @param ms
    * @return
    */
	public String getList(MappedStatement ms) {
	   
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
	}
	
}
