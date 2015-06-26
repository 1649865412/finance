package com.innshine.reportExport;

/**
 * 财务分析报表常量配置类 <code>Constants.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public interface Constants
{
	/**
	 * 营业收入类型(1:营业收入) 该记录被标，则用于计算总收入，故则相反
	 */
	int BUSINESS_INCOME_DEFAULT_TYPE = 1;
	
	/**
	 * 营业收入类型(2:其它) 该记录被标，则用于计算总收入，故则相反
	 */
	int BUSINESS_INCOME_OTHER_TYPE = 2;
	
	/**
	 * 总计类型(1:行总计) 用于需要导出原始数据时，所生成的行数据使用
	 */
	int SUMMARY_ROW_TYPE = 1;
	
	/**
	 * 总计类型( all：总计) 用于需要导出原始数据时，所生成的行数据使用
	 */
	int SUMMARY_ALL_TYPE = 2;
	
	/**
	 * 地区等级（城市：目前为广州，上海）
	 */
	long AREA_LEVEL = 2;
	
	/**
	 * 默认认排序字段
	 */
	String ORDER_FIELD_NAME = "updateTime";
	
	/**
	 * 顶部分类
	 */
	String  []TOP_CLASS_TYPE= {"主营业务收入","其他业务收入","营业外收入","其他业务成本","营业税金及附加"};
	
}
