<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:context="http://www.springframework.org/schema/context" 
	xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.2.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.2.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.2.xsd
		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd"
		default-lazy-init="false">

	<description>Spring公共配置 </description>

	<!-- 使用annotation 自动注册bean, 并保证@Required、@Autowired的属性被注入 -->
	<context:component-scan base-package="com.ketayao,${packageName}">
		<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>
	
	<!-- Spring Data Jpa配置 , 扫描base-package下所有继承于Repository<T,ID>的接口-->
 	<jpa:repositories base-package="com.ketayao,${packageName}"  transaction-manager-ref="transactionManager" entity-manager-factory-ref="entityManagerFactory"/>

	<!-- Jpa Entity Manager 配置 -->
	<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" depends-on="dataSource"> 
		<property name="dataSource" ref="dataSource"/>
		<property name="jpaVendorAdapter" ref="hibernateJpaVendorAdapter"/>
		<property name="packagesToScan" value="com.ketayao,${packageName}"/>
		<property name="jpaProperties">
			<props>
				<prop key="hibernate.current_session_context_class">thread</prop>
				
				<!-- 抓取策略 -->
				<prop key="hibernate.max_fetch_depth">1</prop>
				<prop key="hibernate.default_batch_fetch_size">4</prop>
				<prop key="hibernate.jdbc.fetch_size">30</prop>
				<prop key="hibernate.jdbc.batch_size">50</prop>
				
				<!-- 缓存 -->
				<prop key="hibernate.cache.use_second_level_cache">true</prop>
				<prop key="hibernate.cache.use_query_cache">true</prop>
				<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
				<prop key="net.sf.ehcache.configurationResourceName">ehcache/ehcache-hibernate-local.xml</prop>
				
				<!-- 建表的命名规则 -->
				<prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
				
				<!-- 用于调试的属性 -->
				<!-- 
				<prop key="hibernate.hbm2ddl.auto">update</prop>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop> 
				<prop key="hibernate.generate_statistics">true</prop>
				<prop key="hibernate.use_sql_comments">true</prop>
				-->
				<!-- end 用于调试的属性 -->			
			</props>
		</property>
	</bean>

	<bean id="hibernateJpaVendorAdapter" class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
		<property name="databasePlatform" value="${r"${hibernate.dialect}"}"/>
	</bean>

	<!-- 事务管理器配置, Jpa单数据源事务 -->
	<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"/>
	</bean>

	<!-- 使用annotation定义事务 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
	<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"/>
	
	<!-- production环境 -->
 	<beans profile="production">
 		<context:property-placeholder ignore-unresolvable="true"
			location="classpath:/jdbc.properties" />	
		
		<!-- 数据源配置, 使用DBCP数据库连接池 -->
		<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close" >
			<!-- Connection Info -->
			<property name="driverClassName" value="${r"${jdbc.driver}"}" />
			<property name="url" value="${r"${jdbc.url}"}" />
			<property name="username" value="${r"${jdbc.username}"}" />
			<property name="password" value="${r"${jdbc.password}"}" />
		
			<!-- Connection Pooling Info -->
			<property name="maxActive" value="${r"${dbcp.maxActive}"}" />
			<property name="maxIdle" value="${r"${dbcp.maxIdle}"}" />
			<property name="defaultAutoCommit" value="false" />
			<!-- 连接Idle一个小时后超时 -->
			<property name="timeBetweenEvictionRunsMillis" value="3600000" />
			<property name="minEvictableIdleTimeMillis" value="3600000" />
		</bean>
	</beans>

</beans>