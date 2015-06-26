/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.financeloginfo.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.shiro.ShiroUser;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.financeloginfo.dao.FinanceFileLogInfoDAO;
import com.innshine.financeloginfo.entity.FinanceFileLogInfo;
import com.utils.DateUtils;
import com.utils.Exceptions;

@Service
@Transactional
public class FinanceFileLogInfoService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceFileLogInfoService.class);
	@Autowired
	private FinanceFileLogInfoDAO financeFileLogInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#get(java
	 * .lang.Long)
	 */

	public FinanceFileLogInfo get(Long id)
	{
		return financeFileLogInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#get(java
	 * .lang.Long)
	 */

	public void doTaskDeleteFileLogInfoAll()
	{
		LOGGER.info(" //=============================导入财务报表文件解析LOG日志清理开始 ==============================//");
		LOGGER.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		
		try
		{
			financeFileLogInfoDAO.deleteAll();
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		LOGGER.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOGGER.info(" //=========================== 导入财务文件解析LOG日志清理结束=================================//");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#get(java
	 * .lang.Long)
	 */

	public List<FinanceFileLogInfo> findBySessionIdAndUserInfo(String sessionId, ShiroUser shiroUser)
	{
		if (null != shiroUser)
		{
			return financeFileLogInfoDAO.findBySessionIdAndUserNameAndUserAddress(sessionId, shiroUser.getLoginName(),
					shiroUser.getIpAddress());
		}
		
		return null;
	}
	
	public List<FinanceFileLogInfo> findBySessionIdAndUserNameOrderByUpdateTime(String sessionId, ShiroUser shiroUser)
	{
		if (null != shiroUser)
		{
			return financeFileLogInfoDAO.findBySessionIdAndUserName(sessionId, shiroUser.getLoginName());
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#saveOrUpdate
	 * (com.innshine.financeloginfo.entity.FinanceFileLogInfo)
	 */

	public void saveOrUpdate(FinanceFileLogInfo financeFileLogInfo)
	{
		financeFileLogInfoDAO.save(financeFileLogInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#saveOrUpdate
	 * (com.innshine.financeloginfo.entity.FinanceFileLogInfo)
	 */

	public void saveOrUpdateAndFlush(FinanceFileLogInfo financeFileLogInfo)
	{
		try
		{
			financeFileLogInfoDAO.saveAndFlush(financeFileLogInfo);
			
			// financeFileLogInfoDAO.flush();
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#delete(
	 * java.lang.Long)
	 */

	public void delete(List<FinanceFileLogInfo> financeFileLogInfos)
	{
		if (CollectionUtils.isNotEmpty(financeFileLogInfos))
		{
			financeFileLogInfoDAO.delete(financeFileLogInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#delete(
	 * java.lang.Long)
	 */

	public void delete(Long id)
	{
		financeFileLogInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#delete(
	 * java.lang.Long)
	 */

	public void deleteAll()
	{
		financeFileLogInfoDAO.deleteAll();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#findAll
	 * (com.base.util.dwz.Page)
	 */

	public List<FinanceFileLogInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<FinanceFileLogInfo> springDataPage = financeFileLogInfoDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financeloginfo.service.FinanceFileLogInfoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<FinanceFileLogInfo> findByExample(Specification<FinanceFileLogInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<FinanceFileLogInfo> springDataPage = financeFileLogInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
