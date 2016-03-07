<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
     http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
     http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
     http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd"
	default-lazy-init="true">
	<!-- 参数载入 -->
	<bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="locations">
			<list>
				<value>classpath:application.properties</value>
			</list>
		</property>
	</bean>
	
	<!--spring容器负责所有其他非controller的Bean的注册 -->
	<context:component-scan base-package="com.springD">
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>

	<!-- 开启注解注入 
	<context:annotation-config />
	-->

	<!-- 数据源1 -->
	<bean id="dataSourceDruidOne" class="com.alibaba.druid.pool.xa.DruidXADataSource" destroy-method="close">
		<property name="filters" value="log4j,wall,mergeStat" />
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<!-- 配置额外的参数 分号隔开 -->
		<property name="connectionProperties" value="druid.stat.logSlowSql=true" />
	</bean>
	<!-- 数据源2 -->
	<bean id="dataSourceDruidTwo" class="com.alibaba.druid.pool.xa.DruidXADataSource" destroy-method="close">
		<property name="filters" value="log4j,wall,mergeStat" />
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="url" value="${jdbc2.url}" />
		<property name="username" value="${jdbc2.username}" />
		<property name="password" value="${jdbc2.password}" />
		<!-- 配置额外的参数 分号隔开 -->
		<property name="connectionProperties" value="druid.stat.logSlowSql=true" />
	</bean>

	 <!-- druid界面 spring监控标签配置开始-->
    <bean id="druid-stat-pointcut" class="org.springframework.aop.support.JdkRegexpMethodPointcut" scope="prototype">
        <property name="patterns">
            <list>
                <value>com.springD.application.system.service.*</value>
            </list>
        </property>
    </bean>
    <bean id="druid-stat-interceptor"
          class="com.alibaba.druid.support.spring.stat.DruidStatInterceptor">
    </bean>
    <aop:config>
        <aop:advisor advice-ref="druid-stat-interceptor"
                     pointcut-ref="druid-stat-pointcut" />
    </aop:config>
     <!-- druid界面 spring监控标签配置结束-->
     
     <!-- 使用atomikos管理数据源/事物 -->
   	<bean id="dataSourceOne" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
        <property name="uniqueResourceName" value="dataSourceOne"/>
        <property name="xaDataSource" ref="dataSourceDruidOne"/>
        <property name="xaDataSourceClassName" value="com.alibaba.druid.pool.xa.DruidXADataSource"/>
	</bean>
   	<bean id="dataSourceTwo" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
        <property name="uniqueResourceName" value="dataSourceTwo"/>
        <property name="xaDataSource" ref="dataSourceDruidTwo"/>
        <property name="xaDataSourceClassName" value="com.alibaba.druid.pool.xa.DruidXADataSource"/>
	</bean>

     <!-- 配置SqlSessionFactoryBean -->
	<bean id="sqlSessionFactoryOne" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSourceOne" />
		<property name="mapperLocations">
			<array>
				<value>classpath:com/springD/application/**/dao/*Mapper.xml</value>
			</array>
		</property>
		<property name="typeAliasesPackage" value="com.springD.application.**.entity"/>
		<property name="plugins">
			<array>
				<bean class="com.github.pagehelper.PageHelper">
					<!-- 这里的几个配置主要演示如何使用，如果不理解，一定要去掉下面的配置 -->
					<property name="properties">
						<value>
							dialect=mysql
						</value>
					</property>
				</bean>
			</array>
		</property>
	</bean>
	<bean id="sqlSessionFactoryTwo" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSourceTwo" />
		<property name="mapperLocations">
			<array>
				<value>classpath:com/springD/application/**/dao/*Mapper.xml</value>
			</array>
		</property>
		<property name="typeAliasesPackage" value="com.springD.application.**.entity"/>
		<property name="plugins">
			<array>
				<bean class="com.github.pagehelper.PageHelper">
					<!-- 这里的几个配置主要演示如何使用，如果不理解，一定要去掉下面的配置 -->
					<property name="properties">
						<value>
							dialect=mysql
						</value>
					</property>
				</bean>
			</array>
		</property>
	</bean>

	<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.springD.**.dao"/>
		<!-- 3.2.2版本新特性，markerInterface可以起到mappers配置的作用，详细情况需要看Marker接口类 -->
		<property name="markerInterface" value="com.springD.framework.mapper.MyMapper"/>
		<!-- 通用Mapper通过属性注入进行配置，默认不配置时会注册Mapper<T>接口
        <property name="properties">
            <value>
                mappers=tk.mybatis.mapper.common.Mapper
            </value>
        </property>
        -->
	</bean>

    <bean id="sqlSessionTemplate" class="com.springD.framework.datasource.CustomSqlSessionTemplate">
        <constructor-arg ref="sqlSessionFactoryOne" />
        <property name="targetSqlSessionFactorys">
            <map>
            	<!-- 为了适配原来的DatabaseContextHolder中已定义好的字符串 这里的key向DatabaseContextHolder看齐 -->
                <entry value-ref="sqlSessionFactoryOne" key="dataSourceOne"/>
                <entry value-ref="sqlSessionFactoryTwo" key="dataSourceTwo"/>
            </map>
        </property>
    </bean>
     
   	<bean id="atomikosUserTransaction" class="com.atomikos.icatch.jta.UserTransactionImp">
        <property name="transactionTimeout" value="300"/>
    </bean>
    
    <bean id="atomikosTransactionManager" class="com.atomikos.icatch.jta.UserTransactionManager" init-method="init" destroy-method="close">
        <property name="forceShutdown" value="true"/>
    </bean>
    <!-- 配置事务管理 -->
    <bean id="transactionManager"  class="org.springframework.transaction.jta.JtaTransactionManager">  
	    <property name="transactionManager" ref="atomikosTransactionManager" />  
	    <property name="userTransaction" ref="atomikosUserTransaction" />  
	    <property name="allowCustomIsolationLevels" value="true" />  
	</bean> 
    
     <!-- 配置事务传播特性 -->  
    <tx:advice id="transactionAdvice" transaction-manager="transactionManager">  
        <tx:attributes>  
            <tx:method name="insert*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="update*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="delete*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="add*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="save*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="replace*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="copy*" propagation="REQUIRED" rollback-for="Exception"/>  
            <tx:method name="*" propagation="SUPPORTS" read-only="true"/>  
        </tx:attributes>  
    </tx:advice>  
  
    <!-- 配置相关类的方法参与事务 使用|| 连接多个切点-->
    <aop:config>  
        <aop:pointcut id="findTrasactionPoint" expression="
        	execution(* com.springD.application.system.service.*.*(..)) || execution(* com.springD.application.front.service.*.*(..)) " />
        <aop:advisor pointcut-ref="findTrasactionPoint" advice-ref="transactionAdvice" />  
    </aop:config>
    
	 <!-- 配置注解实现管理事务 注意@Transactional(rollbackFor=Exception.class)-->
	<tx:annotation-driven transaction-manager="transactionManager" 	proxy-target-class="true"  />
     

	<!-- 配置 JSR303 Bean Validator 定义 -->
	<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean" />
	
	<import resource="classpath:applicationContext-cache.xml" />
	<import resource="classpath:applicationContext-shiro.xml" />
	<import resource="classpath:applicationContext-captcha.xml" />
	<!-- mq 本地若禁用  -->

</beans>