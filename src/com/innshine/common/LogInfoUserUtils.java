package com.innshine.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.entity.main.Organization;
import com.base.entity.main.User;
import com.base.shiro.ShiroUser;
import com.utils.Exceptions;
import com.utils.SecurityUtils;

/**
 * 获取登陆用户信息 <code>LogInfoUserUtils.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @author admin 时间 2014年7月14日 下午4:28:32
 * @version 1.0 </br>最后修改人 无
 */
public class LogInfoUserUtils
{
	/**
	 * 日志对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(LogInfoUserUtils.class);
	
	private LogInfoUserUtils()
	{
		
	}
	
	/**
	 * 获取登陆对象所属组织对象
	 * 
	 * @param request
	 *            页面请求对象
	 * @return Organization
	 */
	public static Organization getOrganization()
	{
		
		try
		{
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			User user = null != shiroUser ? shiroUser.getUser() : null;
			if (null != user)
			{
				// 获取用户所属组织对象
				Organization organization = user.getOrganization();
				
				return organization;
				
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 获取登陆用户信息
	 * 
	 * @param request
	 *            页面请求对象
	 * @return Organization
	 */
	public static User User()
	{
		
		try
		{
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			
			return null != shiroUser ? shiroUser.getUser() : null;
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
}
