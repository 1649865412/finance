/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.shiro.ShiroUser;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.reportExport.dao.FinanceFileInfoDAO;
import com.innshine.reportExport.entity.FinanceFileInfo;
import com.innshine.reportExport.util.ExcelFileUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;

@Service
@Transactional
public class FinanceFileInfoService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceFileInfoService.class);
	private static final String YYYY_MM = "yyyy-MM";
	
	/**
	 * 文件上传临时目录
	 */
	public static final String FILE_TEMP_DIR = System.getProperty("user.dir") + "/finance_report/";
	
	@Autowired
	private FinanceFileInfoDAO financeFileInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#get(java.
	 * lang.Long)
	 */

	public FinanceFileInfo get(Long id)
	{
		return financeFileInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#saveOrUpdate
	 * (com.innshine.financefileinfo.entity.FinanceFileInfo)
	 */

	public void saveOrUpdate(FinanceFileInfo financeFileInfo)
	{
		financeFileInfoDAO.save(financeFileInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(Long id)
	{
		financeFileInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(List<FinanceFileInfo> financeFileInfos)
	{
		if (CollectionUtils.isNotEmpty(financeFileInfos))
		{
			financeFileInfoDAO.delete(financeFileInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#delete(java
	 * .lang.Long)
	 */

	public List<FinanceFileInfo> findByImportTime(Date importTime)
	{
		if (null != importTime)
		{
			return financeFileInfoDAO.findByImportTime(importTime);
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#findAll(com
	 * .base.util.dwz.Page)
	 */

	public List<FinanceFileInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<FinanceFileInfo> springDataPage = financeFileInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.financefileinfo.service.FinanceFileInfoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<FinanceFileInfo> findByExample(Specification<FinanceFileInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<FinanceFileInfo> springDataPage = financeFileInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 保存报表文件
	 * 
	 * @param file
	 *        文件对象
	 * @param summaryDate
	 *        日志汇总时间
	 * @return
	 */
	public boolean saveReportExcelFile(File file, Date summaryDate, ShiroUser shiroUser)
	{
		if (null != file)
		{
			try
			{
				File destFile = getDestFile(file, summaryDate);
				if (moveFile(file, destFile))
				{
					ExcelFileUtils.deleteFile(file);
					
					FinanceFileInfo datObj = getImportTimeFileInfo(summaryDate);
					
					FinanceFileInfo financeFileInfo = datObj == null ? new FinanceFileInfo() : datObj;
					financeFileInfo.setFileName(com.utils.FileUtils.getRealName(destFile.getName()));
					financeFileInfo.setFileRelativePath(destFile.getPath());
					financeFileInfo.setImportTime(summaryDate);
					financeFileInfo.setUpdateTime(new Date());
					
					if (null != shiroUser)
					{
						financeFileInfo.setUserName(shiroUser.getLoginName());
						financeFileInfo.setUserAdderss(shiroUser.getIpAddress());
						financeFileInfo.setUserId(shiroUser.getUser() != null ? shiroUser.getUser().getId() : null);
						
					}
					
					saveOrUpdate(financeFileInfo);
					
					return true;
				}
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return false;
	}
	
	private FinanceFileInfo getImportTimeFileInfo(Date summaryDate)
	{
		if (null != summaryDate)
		{
			List<FinanceFileInfo> financeFileInfos = findByImportTime(summaryDate);
			
			if (CollectionUtils.isNotEmpty(financeFileInfos))
			{
				if (financeFileInfos.size() >= 1 && financeFileInfos.size() < 2)
				{
					return financeFileInfos.get(0);
				}
				else
				{
					delete(financeFileInfos);
				}
				
			}
		}
		
		return null;
	}
	
	/**
	 * 移动文件，
	 * <p>
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	private boolean moveFile(File srcFile, File destFile)
	{
		try
		{
			if (null != srcFile && null != destFile)
			{
				ExcelFileUtils.deleteFile(destFile);
				FileUtils.moveFile(srcFile, destFile);
			}
		}
		catch (IOException e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param file
	 * @param summaryDate
	 * @return
	 */
	private File getDestFile(File file, Date summaryDate)
	{
		if (null != file)
		{
			String folderName = null != summaryDate ? DateUtils.format(summaryDate, YYYY_MM) : DateUtils.format(
					new Date(), YYYY_MM);
			
			String path = FILE_TEMP_DIR + File.separator + folderName;
			
			if (ExcelFileUtils.createDir(path))
			{
				return new File(path + File.separator + file.getName());
			}
		}
		return null;
	}
}
