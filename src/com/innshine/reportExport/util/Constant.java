package com.innshine.reportExport.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import com.base.dao.SqlDao;
import com.innshine.areainfo.service.AreaInfoService;

/**
 * <code>Constant.java,组合模块实现类名常量</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 上午11:57:17
 * @version 1.0 </br>最后修改人 无
 */

/*
 * PROPORTION COMPARED CHAIN AREA ITEM DIMENSION
 */

public class Constant
{
	/**
	 * 同比:单项目多维度
	 */
	public static String COMPARED_ONE_ITEM_MANY_DIMENSION = "com.innshine.reportExport.reportInterface.ComparedOneItemManyDimension";

	/**
	 * 同比:单维度多项目
	 */
	public static String COMPARED_MANY_ITEM_ONE_DIMENSION = "com.innshine.reportExport.reportInterface.ComparedManyItemOneDimension";

	/**
	 * 环比:单项目多维度
	 */
	public static String CHAIN_ONE_ITEM_MANY_DIMENSION = "com.innshine.reportExport.reportInterface.ChainOneItmeManyDimension";

	/**
	 * 环比:单维度多项目
	 */
	public static String CHAIN_MANY_ITEM_ONE_DIMENSION = "com.innshine.reportExport.reportInterface.ChainManyItemOneDimension";

	/**
	 * 占比:单项目多维度
	 */
	public static String PROPORTION_ONE_ITEM_MANY_DIMENSION = "com.innshine.reportExport.reportInterface.ProportionOneItmeManyDimension";

	/**
	 * 地区:多维度比
	 */
	public static String AREA_MANY_DIMENSION = "com.innshine.reportExport.reportInterface.AreaManyDimension";
	
	public static WebApplicationContext CONTENXT_BEAN =null;
	
	public static AreaInfoService AREAINFOSERVICE =null;
	
	public static SqlDao SqlDao=null;
	
	public static String  FILE_FORMAT="导出PPT下载.ppt";
	
	public static int  PARAM_ONE_OR_TWO=1;
	
	public static int  PARAM_THIRD_OR_FOUR=2;
	
	


}
