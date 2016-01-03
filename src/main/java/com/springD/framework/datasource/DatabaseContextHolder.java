package com.springD.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springD.framework.utils.StringUtils;

/**   
 * @Description: 多数据源上下文处理类
 * @author Chenz
 * @date Nov 13, 2014 1:03:21 PM 
 * @version V1.0   
*/
public class DatabaseContextHolder {
	protected static final Logger LOG = LoggerFactory.getLogger(DatabaseContextHolder.class);
	public static final String DATA_SOURCE_ONE_WEBPLATFORM = "dataSourceOne";
	public static final String DATA_SOURCE_TWO_BUSINESSPLATFORM = "dataSourceTwo";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
//	public static void setCustomerType(String customerType) {  
//        contextHolder.set(customerType);  
//    }
	
	public static void setCustomerType(String customerType) {
		LOG.debug("switch datasource:-------------------{}",customerType);
		String currentCustomerType = getCustomerType();
		LOG.debug("current datasource:-------------------{}",currentCustomerType);
		if(currentCustomerType == null ||StringUtils.isNotBlank(currentCustomerType) && !customerType.equals(currentCustomerType)){
			LOG.debug("had switch datasource!!!");
			contextHolder.set(customerType);
		}
    }  
  
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
  
    public static void clearCustomerType() {  
        contextHolder.remove();  
    } 
}
