package com.innshine.chart.service.util;

import java.util.List;



public class Constant
{

	
	/**
	 * 平价功能导出表头
	 */
	public static final String PLATE_PRICE_HEAD[]= new String[] { "条码","产品中文名称","市场价","是否上架","是否缺货","链接","售价","折扣"};
	
	
	/**
	 * 平价功能导出表头
	 */                                                                                                                     
	public static final String PLATE_PRICE_DISPLAY[]= new String[] { "upccode","productName","marketPrice","onSalesTime","outOfStock","link","platformSalesPrice","discount"};
	
	/**
	 * 导出提示语
	 * 
	 */
	public static String MARKED_WORD = "（此分析使用模糊数据，请注意）";

	/**
	 * 销售分析页面获取相关下拉属性
	 * 
	 */
	public static List ParamAll;


	/**
	 * 时间纵向销售分析报表
	 * 
	 */
	public static String TIME_ANALYSE = "时间纵向销售分析报表.xls";

	/**
	 * 欧来雅TOP20单装.xls
	 * 
	 */
	public static String O_TOP20_NAME = "TOP20单装.xls";

	/**
	 * 曼秀雷顿TOP20单装.xls;
	 * 
	 */
	public static String M_TOP20_NAME = "TOP20单装.xls";

	/**
	 * 曼秀雷顿TOP20套装.xls;
	 * 
	 */
	public static String M_TOP20_Z_NAME = "TOP20套装.xls";

	/**
	 * 欧莱雅TOP20套装.xls;
	 * 
	 */
	public static String O_TOP20_Z_NAME = "TOP20套装.xls";

	/**
	 * 欧莱雅全网月度报表.xls;
	 * 
	 */
	public static String O_Month_NAME = "全网月度报表.xls";

	/**
	 * 曼秀雷顿月度报表.xls;
	 * 
	 */
	public static String M_Month_NAME = "月度报表.xls";

	/**
	 * 欧莱雅销售综合分析报表.xls
	 * 
	 */
	public static String O_SHOPANALYSE_NAME = "销售综合分析报表.xls";

	/**
	 * 曼秀雷顿销售综合分析报表.xls
	 * 
	 */
	public static String M_SHOPANALYSE_NAME = "销售综合分析报表.xls";

	/**
	 * 欧莱雅套装销售综合分析报表.xls
	 * 
	 */
	public static String O_SHOPANALYSE_Z_NAME = "套装销售综合分析报表.xls";

	/**
	 * 曼秀雷顿套装销售综合分析报表.xls
	 * 
	 */
	public static String M_SHOPANALYSE_Z_NAME = "套装销售综合分析报表.xls";

	/**
	 * 时间维度查询 季度查询转成月
	 */
	public static final String[][] Z_MONTH = new String[][] {
			{ "01月", "02月", "03月" }, { "04月", "05月", "06月" },
			{ "07月", "08月", "09月" }, { "10月", "11月", "12月" } };

	/**
	 * 系统产品下拉属性(下拉属性类别编号，1：品类，2：系列，3：功效,4:产品类型，5：分销类型)，可放置内存
	 * 
	 */
	public static String PARAM_ARRAY[] = new String[] { "category", "series",
			"product_efficacy", "product_type", "distri_type" };

	/**
	 * 套装销售分析分类 如要增加新分类，加这里根据前台顺序加上对应数据库字段即可
	 */

	public static String GROUP_Z_NAME[] = new String[] { "平台", "品类", "系列", "功效" };
	public static String GROUP_Z_NAME_CHECK[] = new String[] { "gplate",
			"gcategory", "gseries", "gefficacy" };
	public static String GROUP_Z_NAME_SQL[] = new String[] { "a.platform_id",
			"a.suit_category", "a.suit_series", "a.suit_efficacy" };

	/**
	 * 销售分析分类 如要增加新分类，加这里根据前台顺序加上对应数据库字段即可
	 * 
	 */
	public static String GROUP_NAME[] = new String[] { "平台", "品类", "系列", "分销类型" };
	public static String GROUP_NAME_CHECK[] = new String[] { "plate",
			"category", "series", "distriType" };
	public static String GROUP_NAME_SQL[] = new String[] { "a.platform_id",
			"b.category", "b.series", "b.distri_type" };

	/**
	 * 销售分析套装默认导出列
	 * 
	 */
	public static String[] GROUP_LIST_ORIGN_Z_NAME = { "套装名称", "平台套装编码",
			"套装编码", "套装条形码", "套装售价", "套装品类", "套装系列", "套装功效分类", "平台名称",
			"平台产品ID", "单品Material", "单品名称", "销售数量", "销售金额(元)", "库存数量",
			"库存金额(元)" };

	/**
	 * 销售分析默认导出列
	 * 
	 */
	public static String[] GROUP_LIST_ORIGN_NAME = { "产品SKU详情", "产品名称", "平台名称",
			"品类", "销量", "销售金额(元)", "销售占比(%)" };

	/**
	 * 系列汇总导出列
	 * 
	 */
	public static String[] GROUP_LIST_SERIES_NAME = { "系列汇总", "销量", "销售金额(元)",
			"折扣金额(元)", "销售占比(%)" };

}
