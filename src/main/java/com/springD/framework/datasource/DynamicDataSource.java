package com.springD.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**   
 * @Description: 动态数据源路由实现类
 * @author Chenz
 * @date Nov 13, 2014 1:04:12 PM 
 * @version V1.0   
*/
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getCustomerType();
	}

}
