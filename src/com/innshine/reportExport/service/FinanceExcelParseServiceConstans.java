package com.innshine.reportExport.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 财务报表Excel文件解析常量类<code>FinanceExcelParseServiceConstans.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author auto 时间 2015-1-19 下午03:39:31
 * @version 1.0 </br>最后修改人 无
 */
public interface FinanceExcelParseServiceConstans
{
	/**
	 * 公用身体部分
	 */
	String[] COMMON_BODY_FIELD = new String[] { "classifyName", "classifyInfoTwoName" };
	
	/**
	 * 公用头部分
	 */
	String[] COMMON_HEAD_FIELD = new String[] { "projectId" };
	
	/**
	 * 品牌金额列
	 */
	String COMMON_BRAND_AMOUNT_FIELD = "monthSummaryAmount";
	
	/**
	 * 公用底部分
	 */
	String[] COMMON_STERM_FIELD = new String[] { "projectSubtotal", "comprehensiveCost", "currentSummary",
			"preSummary", "chain", "preYearAgo", "yearRate" };
	/**
	 * 费用部分头部分
	 */
	String[] FINANCE_HEAD_FIELD = new String[] { "financeCostsTypeName", "financeCostsCategoriesName" };
	
	/**
	 * Excel中底部部分解析头
	 */
	String[] FINANCE_STERM_HEAD_FIELD = new String[] { "financeCostsTypeName" };
	
	/**
	 * 创建一个可重用固定线程数的线程池
	 */
	ExecutorService DEFAULT_EXECUTOR_POOL = Executors.newFixedThreadPool(20);
	
	/**
	 * 日期格式
	 */
	String FORMAT_YYYY_MM = "yyyy-MM";
	
}
