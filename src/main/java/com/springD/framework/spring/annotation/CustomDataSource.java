package com.springD.framework.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**   
 * @Description: 数据源切换注解
 * @author Chenz
 * @date Nov 13, 2014 1:04:50 PM 
 * @version V1.0   
*/
@Target({ElementType.METHOD,ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface CustomDataSource {
	public String value() default "";
}
