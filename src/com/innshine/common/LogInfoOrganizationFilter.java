package com.innshine.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.entity.main.Organization;
import com.innshine.common.brand.BrandConfigEntity;
import com.innshine.common.brand.BrandConfigResource;
import com.utils.Exceptions;

public class LogInfoOrganizationFilter implements Filter
{
	private static final String ORGANIZATION_IDS_FIELD_NAME = "organizationIds";
	
	private static final String COMMA_SYMBOL = ",";
	
	/**
	 * 添加、修改时使用的字段名
	 */
	public static final String SAVE_FIELD_NAME_ORGANIZATION_ID = "organizationId";
	
	/**
	 * 查询时使用的
	 */
	public static final String QUERY_FIELD_NAME_ORGANIZATION_ID = "search_EQ_organizationId";
	
	/**
	 * 需要拦截的请求链表，默认按"，"分割
	 */
	private String interceptorActions;
	
	/**
	 * 不需要过滤的路径
	 */
	private String needToFilterActions;
	
	/**
	 * 所属部门编号列表
	 */
	private String organizationIds;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LogInfoOrganizationInterceptor.class);
	
	@Override
	public void destroy()
	{
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		// LOGGER.info("Need to intercept the request list ！ Values = [ " +
		// interceptorActions + "]");
		if (checkCurrRequest((HttpServletRequest) request))
		{
			request = setOrganizationId((HttpServletRequest) request);
		}
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.interceptorActions = filterConfig.getInitParameter("interceptorActions");
		this.organizationIds = getOrganizationIds(filterConfig);
		this.needToFilterActions = filterConfig.getInitParameter("needToFilterActions");
	}
	
	/**
	 * 获取品牌列表
	 * <p>
	 * 
	 * @param filterConfig
	 * @return
	 */
	private String getOrganizationIds(FilterConfig filterConfig)
	{
		String tmpString = "";
		
		try
		{
			List<BrandConfigEntity> configAttrEntities = BrandConfigResource.getInstance().getConfig();
			
			StringBuffer buffer = new StringBuffer();
			if (null != configAttrEntities)
			{
				for (int i = 0; i < configAttrEntities.size(); i++)
				{
					BrandConfigEntity brandConfigEntity = configAttrEntities.get(i);
					if (brandConfigEntity != null && StringUtils.isNotBlank(brandConfigEntity.getOrganizationId()))
					{
						buffer.append(brandConfigEntity.getOrganizationId()).append(COMMA_SYMBOL);
					}
				}
				
				if (StringUtils.isNotBlank(buffer.toString()))
				{
					tmpString = buffer.lastIndexOf(COMMA_SYMBOL) != -1 ? buffer.substring(0,
							buffer.lastIndexOf(COMMA_SYMBOL)) : buffer.toString();
				}
				else
				{
					tmpString = filterConfig.getInitParameter(ORGANIZATION_IDS_FIELD_NAME);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			tmpString = filterConfig.getInitParameter(ORGANIZATION_IDS_FIELD_NAME);
		}
		
		return tmpString;
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
		// String contextPath = request.getContextPath();
		// LOGGER.info("Request URI =  " + requestUri + ", contextPath = " +
		// contextPath);
		
		if (StringUtils.isNotBlank(requestUri))
		{
			String[] actions = StringUtils.isNotBlank(interceptorActions) ? interceptorActions.split(COMMA_SYMBOL)
					: null;
			
			if (ArrayUtils.isNotEmpty(actions))
			{
				return checkURL(requestUri, actions);
				
			}
			else
			{
				// 不需要添加所属部门编号的公用地址
				String[] tmpNeedToFilterActions = StringUtils.isNotBlank(needToFilterActions) ? needToFilterActions
						.split(COMMA_SYMBOL) : null;
				if (ArrayUtils.isNotEmpty(tmpNeedToFilterActions))
				{
					return checkURL(requestUri, tmpNeedToFilterActions) ? false : true;
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 检测URL是否需要过滤，不需要过滤的则直接返回false ，故则相反
	 * 
	 * @param requestUri
	 *            请求路径
	 * @param actions
	 *            过滤URL列表
	 * @param isFlag
	 *            true 找到需要过滤返回true false:找到不需要过滤false
	 * @return
	 */
	public boolean checkURL(String requestUri, String[] actions)
	{
		for (String action : actions)
		{
			if (StringUtils.isNotBlank(action))
			{
				if (action.toUpperCase().indexOf(requestUri.toUpperCase()) != -1
						|| requestUri.toUpperCase().indexOf(action.toUpperCase()) != -1)
				{
					
					return true;
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
	private HttpServletRequest setOrganizationId(HttpServletRequest request)
	{
		try
		{
			
			// 获取用户所属组织对象
			Organization organization = LogInfoUserUtils.getOrganization();
			
			if (null == organization)
			{
				return request;
			}
			
			String tmpId = String.valueOf(organization.getSwitchId() != null ? organization.getSwitchId()
					: organization.getId());
			
			boolean isReturnRequest = false;
			// 忽略不需要过滤的所属组织
			if (StringUtils.isNotBlank(organizationIds))
			{
				if (!ArrayUtils.contains(organizationIds.split(COMMA_SYMBOL), tmpId))
				{
					isReturnRequest = true;
				}
			}
			
			// 如果没有找到所属组织编号，则认为是超级管理员或其它有权限用户登陆
			// if (isReturnRequest)
			// {
			// Object obj = BrandChangLocalThread.getOrganizationId();
			// if (null != obj)
			// {
			// tmpId = String.valueOf(obj);
			// // 超级管理员选择了查看品牌，则切换为相应品牌
			// isReturnRequest = false;
			//
			// // 给当前Thread.currentThread()设线程局面变量
			// // if
			// // (!obj.equals(BrandChangLocalThread.getOrganizationId()))
			// // {
			//
			// // BrandChangLocalThread.removeOrganizationId();
			// // BrandChangLocalThread.setOrganizationId(obj);
			// // }
			// }
			// }
			
			// System.out.println("Thread.currentThread() Id = " +
			// Thread.currentThread().getId() + ", Name = "
			// + Thread.currentThread().getName() +
			// ",BrandChangLocalThread.OrganizationId = "
			// + BrandChangLocalThread.getOrganizationId());
			
			if (isReturnRequest)
			{
				return request;
			}
			
			request = setParamter(request, tmpId);
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return request;
	}
	
	@SuppressWarnings("unchecked")
	public HttpServletRequest setParamter(HttpServletRequest request, String tmpId)
	{
		Map<String, String[]> map = new HashMap<String, String[]>(request.getParameterMap());
		
		map.putAll(request.getParameterMap());
		
		request = new ParameterRequestWrapper(request, map);
		
		if (null != map)
		{
			map.put(SAVE_FIELD_NAME_ORGANIZATION_ID, new String[] { tmpId });
			
			map.put(QUERY_FIELD_NAME_ORGANIZATION_ID, new String[] { tmpId });
		}
		return request;
	}
}
