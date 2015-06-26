package com.innshine.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.base.entity.main.Organization;
import com.base.entity.main.User;
import com.base.util.persistence.DynamicSpecifications;
import com.utils.Exceptions;
import com.utils.SecurityUtils;

/**
 * 获取用户所属组织拦截器，并设置至用户对应属性中
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @author admin 时间 2014年7月14日 下午12:01:20
 * @version 1.0 </br>最后修改人 无
 */
public class LogInfoOrganizationInterceptor extends HandlerInterceptorAdapter
{
	
	private static final String COMMA_SYMBOL = ",";
	
	/**
	 * 添加、修改时使用的字段名
	 */
	private static final String SAVE_FIELD_NAME_ORGANIZATION_ID = "organizationId";
	
	/**
	 * 查询时使用的
	 */
	private static final String QUERY_FIELD_NAME_ORGANIZATION_ID = "search_EQ_organizationId";
	
	/**
	 * 需要拦截的请求链表，默认按"，"分割
	 */
	private String interceptorActions;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LogInfoOrganizationInterceptor.class);
	
	/**
	 * <p>
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 * </p>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		LOGGER.info("Need to intercept the request list ！ Values = [ " + interceptorActions + "]");
		if (checkCurrRequest(request))
		{
			request = setOrganizationId(request);
		}
		
		return true;
	}
	
	/**
	 * 检测当前请求是否需要设置组织ID
	 * <p>
	 * 
	 * @param request
	 *            请求对象
	 * @return true ： 需要 flase ： 不需要
	 */
	private boolean checkCurrRequest(HttpServletRequest request)
	{
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		LOGGER.info("Request URI =  " + requestUri + ", contextPath = " + contextPath);
		
		if (StringUtils.isNotBlank(requestUri) && StringUtils.isNotBlank(interceptorActions))
		{
			// requestUri = requestUri.indexOf(contextPath) != -1 ?
			// requestUri.substring(contextPath.length() + 1,
			// requestUri.length()) : requestUri;
			
			String[] actions = interceptorActions.split(COMMA_SYMBOL);
			
			if (ArrayUtils.isNotEmpty(actions))
			{
				for (String action : actions)
				{
					if (StringUtils.isNotBlank(action))
					{
						if (action.indexOf(requestUri) != -1 || requestUri.indexOf(action) != -1)
						{
							return true;
						}
					}
				}
				
			}
		}
		
		return false;
	}
	
	/**
	 * 设置所属组织参数
	 * 
	 * @param request
	 *            请求对象
	 * @param isQuery
	 *            true ： 查询请求 false : 添加、删除请求
	 */
	@SuppressWarnings("unchecked")
	private HttpServletRequest setOrganizationId(HttpServletRequest request)
	{
		try
		{
			User user = SecurityUtils.getShiroUser().getUser();
			if (null != user)
			{
				// 获取用户所属组织对象
				Organization organization = user.getOrganization();
				// Map map = new HashMap(request.getParameterMap());
				Map<String, String[]> map = new HashMap<String, String[]>(request.getParameterMap());
				
				map.putAll(request.getParameterMap());
				
				request = new ParameterRequestWrapper(request, map);
				
				if (null != organization && null != map)
				{
					// if (!isQuery)
					// {
					map.put(SAVE_FIELD_NAME_ORGANIZATION_ID, new String[] { String.valueOf(organization.getId()) });
					// }
					// else
					// {
					map.put(QUERY_FIELD_NAME_ORGANIZATION_ID, new String[] { String.valueOf(organization.getId()) });
					
					// }
					
				}
				
				DynamicSpecifications.removeRequest();
				DynamicSpecifications.putRequest(request);
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return request;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		DynamicSpecifications.removeRequest();
	}
	
	public String getInterceptorActions()
	{
		return interceptorActions;
	}
	
	public void setInterceptorActions(String interceptorActions)
	{
		this.interceptorActions = interceptorActions;
	}
	
}
