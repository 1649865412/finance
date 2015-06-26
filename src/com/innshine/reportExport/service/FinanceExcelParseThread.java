package com.innshine.reportExport.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.shiro.ShiroUser;
import com.utils.Exceptions;

/**
 * 解析文件线程 <code>FinanceExcelParseThread.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author admin 时间 2015-1-21 下午05:13:02
 * @version 1.0 </br>最后修改人 无
 */
public class FinanceExcelParseThread implements Runnable
{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExcelParseThread.class);
	/**
	 * 文件对象
	 */
	private File financeFile;
	
	/**
	 * sessionid
	 */
	private String jsessionId;
	
	/**
	 * 登陆用户对象
	 */
	private ShiroUser shiroUser;
	
	private FinanceExcelParseService financeExcelParseService;
	
	public FinanceExcelParseThread(File financeFile, String jsessionId, ShiroUser shiroUser,
			FinanceExcelParseService financeExcelParseService)
	{
		super();
		this.financeFile = financeFile;
		this.jsessionId = jsessionId;
		this.shiroUser = shiroUser;
		this.financeExcelParseService = financeExcelParseService;
	}
	
	public void run()
	{
		try
		{
			financeExcelParseService.parseFile(financeFile, shiroUser, jsessionId);
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
}
