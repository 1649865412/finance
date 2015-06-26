package com.innshine.reportExport.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.base.dao.SqlDao;
import com.base.exception.ServiceException;
import com.innshine.areainfo.service.AreaInfoService;

/**
 * <code>StartupListener.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-20 下午05:04:10
 * @version 1.0 </br>最后修改人 无
 */
public class StartupListener extends ContextLoaderListener implements
		ServletContextListener
{

	public void contextInitialized(ServletContextEvent event)
	{
		// 获取spring上下文
		ServletContext scontext = event.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(scontext);
		try
		{	
			Constant.CONTENXT_BEAN=wac;
			Constant.AREAINFOSERVICE=(AreaInfoService) wac.getBean("areaInfoService");
			Constant.SqlDao=(SqlDao) wac.getBean("sqlDao");
			
		} catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}
}