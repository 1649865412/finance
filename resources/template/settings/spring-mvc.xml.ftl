<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd">

	<!-- 自动扫描且只扫描@Controller -->
	<!-- 加入定制化包实体路径com.sample -->
	<context:component-scan base-package="com.ketayao,${packageName}" use-default-filters="false">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
		<context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
	</context:component-scan>
	
	<!-- 系统日志跟踪功能 -->
	<bean id="log4JDBCImpl" class="com.ketayao.ketacustom.log.impl.Log4JDBCImpl" >
		<property name="logInfoService" ref="logInfoServiceImpl"/>
		<property name="rootLogLevel" value="ERROR"/>
		<property name="customLogLevel">
           <map>
              <entry key="com.ketayao.ketacustom" value="TRACE" />
              <entry key="${packageName}" value="TRACE" />
           </map>
       </property>
	</bean>
	
	<mvc:interceptors>
		<!-- 监控运行时间 -->
		<!-- 
		<mvc:interceptor>
			<mvc:mapping path="/**" />
			<bean class="com.ketayao.ketacustom.spring.ExecuteTimeInterceptor" />
		</mvc:interceptor>
		 -->	
		<!-- 日志拦截记录 -->	
		<mvc:interceptor>
			<mvc:mapping path="/management/**" />
			<mvc:mapping path="/login/timeout/success"/>
			<bean class="com.ketayao.ketacustom.log.spring.LogInterceptor" >
				<property name="logAPI" ref="log4JDBCImpl"/>
			</bean>
		</mvc:interceptor>
		<!-- 数据权限验证 -->
		<mvc:interceptor>
			<mvc:mapping path="/**" />
			<bean class="com.ketayao.ketacustom.spring.DataControlInterceptor" />
		</mvc:interceptor>	
	</mvc:interceptors>
	
	<mvc:annotation-driven>
		<mvc:message-converters register-defaults="true">
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
				<constructor-arg value="UTF-8" />
			</bean>
		</mvc:message-converters>	
	</mvc:annotation-driven>

	<!-- 容器默认的DefaultServletHandler处理 所有静态内容与无RequestMapping处理的URL-->
	<mvc:default-servlet-handler/>
	
	<bean id="multipartResolver"  
	    class="org.springframework.web.multipart.commons.CommonsMultipartResolver">  
	    <property name="defaultEncoding">  
	        <value>UTF-8</value>  
	    </property>  
	    <property name="maxUploadSize">  
	        <value>1048576</value><!-- 上传文件大小限制为1M，1*1024*1024 -->  
	    </property>  
	    <property name="maxInMemorySize">  
	        <value>4096</value>  
	    </property>  
	</bean>
	
	<!-- 定义JSP文件的位置 --> 
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"/>
		<property name="suffix" value=".jsp"/>
	</bean>	
	
	<!-- 定义无Controller的path<->view直接映射 -->
	<mvc:view-controller path="/" view-name="redirect:/management/index"/>
	
	 <!-- 全局异常配置 start -->  
     <bean id="exceptionResolver" class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">  
         <property name="exceptionMappings">  
             <props>
				 <prop key="org.apache.shiro.authz.UnauthorizedException">error/403</prop>      
                 <prop key="java.lang.Throwable">error/500</prop>
             </props>  
         </property>  
         <property name="statusCodes">  
             <props>  
                 <prop key="500">500</prop>  
                 <prop key="404">404</prop>
                 <prop key="403">403</prop>  
             </props>  
         </property>  
         <!-- 设置日志输出级别，不定义则默认不输出警告等错误日志信息 -->  
         <property name="warnLogCategory" value="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver"/>  
         <!-- 默认错误页面，当找不到上面mappings中指定的异常对应视图时，使用本默认配置 -->  
         <property name="defaultErrorView" value="error/500"/>  
         <!-- 默认HTTP状态码 ，返回错误默认设置的状态码-->  
         <property name="defaultStatusCode" value="200"/>  
     </bean>  
     <!-- 全局异常配置 end -->

	<!-- 支持 Shiro对Controller的方法级AOP安全控制 begin-->
	<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator" depends-on="lifecycleBeanPostProcessor">
		<property name="proxyTargetClass" value="true" />
	</bean>
	
	<bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
    	<property name="securityManager" ref="securityManager"/>
	</bean>
	<!-- 支持 Shiro对Controller的方法级AOP安全控制 end  -->       	     
</beans>
