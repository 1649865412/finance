package com.innshine.reportExport.util;

import java.util.List;

public class ExcelConstant {

	/**
	 * top10
	 */
	public static final String TOP_10 = "TOP10报表.xls";

	/**
	 *销售日报报表
	 */
	public static final String SHOP_DAY = "销售日报报表.xls";

	/**
	 * 月报报表
	 */
	public static final String SHOP_MONTH = "月报报表.xls";

	/**
	 * 月报统计报表
	 */

	public static final String SHOP_ANAYLSE_MONTH = "月度统计报表.xls";

	/**
	 * 销售分析报表
	 */

	public static final String SHOP_ANAYLSE = "销售分析报表.xls";
	
	/**
	 * 销售分析报表
	 */

	public static final String SHOP_TIME_ANAYLSE = "时间纵向分析往年同比报表.xls";

	/**
	 * 空格
	 */
	public static final String BLANK_SPACE_20 = "%20";
	/**
	 * 默认编码
	 */
	public static final String DEAULT_ENCODE_UTF_8 = "UTF-8";
	/**
	 * 空格符
	 */
	public static final String BLANK_CHARACTER = " ";
	/**
	 * User-Agent
	 */
	public static final String USER_AGENT = "User-Agent";

	/**
	 * 火狐浏览器标识
	 */
	public static final String FIREFOX = "firefox";

	/**
	 * 文件下载响应
	 */
	public static final String APPLICATION_X_MSDOWNLOAD = "application/x-msdownload;";

	/**
	 * 编码
	 */
	public static final String ENCODE_ISO8859_1 = "ISO8859-1";

	
	/**
	 * 时间纵向销售分析报表
	 * 
	 */
	public static String TIME_ANALYSE = "时间纵向销售分析报表.xls";
	
	/**
	 * top10表字段
	 */
	public static String TOP_10_HEAD[] = new String[] { "序号", "69码", "款号",
			"Lnline/2ndline", "LF/PF", "季节", "销售价", "销售金额", "销量", "销售占比" };

	/**
	 * top10表展示字段
	 */
	public static String DISPLAY_TOP_10[] = new String[] { "numIndex",
			"upccode", "materialNumber", "line", "productLfPf", "quater",
			"avgCurrentPrice", "salesSum", "salesNumber", "discount" };

	/**
	 * 销售日报报表字段
	 */
	public static String SHOP_DAY_HEAD[] = new String[] { "日期", "69码", "款号",
			"Lnline/2ndline", "LF/PF", "季节", "销售价(元)", "销售金额", "销量", "销售占比" };

	
	
	/**
	 * 销售日报展示报表字段
	 */
	public static String DISPLAY_SHOP_DAY[] = new String[] { "salesTime",
			"upccode", "materialNumber", "line", "productLfPf", "quater",
			"avgCurrentPrice", "salesSum", "salesNumber", "discount" };


	/**
	 * 销售分析种类，系列，line ，性别(导出字段)
	 * 
	 */
	public static String[] SHOP_ANALYSE_GROUP_OTHER_ONE_HEAD = { "分析类别", "销售数量",
			"销售占比", "付款金额", "付款金额占比", "吊牌金额", "吊牌金额占比" };
	
	
	
	/**
	 * 销售分析种类，系列，line ，性别(展示字段)
	 * 
	 */
	public static String[] DISPLAY_SHOP_ANALYSE_GROUP_OTHER_ONE = { "salesNumber",
			"salesNumberDiscount", "salesSum", "salesSumDiscount", "salesTagMoney", "salesTagDiscount" };

	/**
	 * 销售分析Q季(导出字段)
	 * 
	 */
	public static String[] SHOP_ANALYSE_QUARTER = { "季节", "本月销售", "本月销售占比(%)",
			"累计销售", "售罄率(%)" };
	
	/**
	 * 展示销售分析Q季(导出字段)
	 * 
	 */
	public static String[] DISPLAY_SHOP_ANALYSE_QUARTER = { "quater", "nowMonthSalesNumber", "nowMonthSalesNumberDiscount",
			"allSalesSum", "rateOut" };
	
	
	
	
	/**
	 * 销售分析分类(导出固定字段)
	 * 
	 */
	public static String[] SHOP_ANALYSE_GROUP = { "销量", "占比(%)" };
	
	
	/**
	 * 展示销售分析分类(导出固定字段)
	 * 
	 */
	public static String[] DISPLAY_SHOP_ANALYSE_GROUP = {"salesNumber", "discount"};
	
	
	
	/**
	 * 时间纵向销售分析分类(导出固定字段)
	 * 
	 */
	public static String[] SHOP_TIME_ANALYSE_GROUP = { "销量", "占比(%)" ,"库存量","增长率(%)"};

    /**
	 * 时间纵向销售分析分类同比本年字段
	 * 
	 */
	public static String[] SHOP_TIME_ANALYS= { "salesNumber", "discount","stockNumber" ,"raiseCount"};
   
	/**
	 * 时间纵向销售分析分类同比一年字段
	 * 
	 */
	public static String[] SHOP_TIME_ONE_YEAR_ANALYSE_GROUP = { "yearOneSalesNumber", "yearOneDiscount" ,"yearOneStockNumber","yearOneRaiseCount"};
	
	
	/**
	 * 时间纵向销售分析分类同比两年字段
	 * 
	 */
	public static String[] SHOP_TIME_TWO_YEAR_ANALYSE_GROUP = { "yearTwoSalesNumber", "yearTwoDiscount" ,"yearTwoStockNumber","yearTwoRaiseCount"};
	
	
	

}
