package com.innshine.reportExport.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.base.dao.SqlDao;
import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;

/**
 * <code>ParamTool.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-21 下午01:25:55
 * @version 1.0 </br>最后修改人 无
 */
public class ReportTool
{
	public static String[] CHAIN_ARRAY = { "本月", "上月" };

	public static String Param_ONE_OR_TWO = "Param_ONE_OR_TWO.all";

	public static String Param_THIRD_OR_FOUR = "Param_THIRD_OR_FOUR.all";

	/**
	 * 功能:求占比与地区比数据（一二级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<CellMonthSummaryInfo> getCellMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;
		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);
		List<CellMonthSummaryInfo> cellMonthSummaryInfo = sqlDao
				.queryListBySql(param, Param_ONE_OR_TWO,
						CellMonthSummaryInfo.class);
		return cellMonthSummaryInfo;
	}

	/**
	 * 功能:求占比与地区比数据（三四级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<RowMonthSummaryInfo> getRowMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;
		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);
		List<RowMonthSummaryInfo> rowMonthSummaryInfoList = sqlDao
				.queryListBySql(param, Param_THIRD_OR_FOUR,
						RowMonthSummaryInfo.class);
		return rowMonthSummaryInfoList;
	}

	/**
	 * 功能:环比数据（一二级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<CellMonthSummaryInfo> getChainCellMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;
		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);

		param.put("beginTime", DateUtil.lastMonth(conditionEntity
				.getBeginTime()));// 环比，需取开始时间再上一个月
		param.put("endTime", conditionEntity.getEndTime());

		List<CellMonthSummaryInfo> cellMonthSummaryInfoList = sqlDao
				.queryListBySql(param, Param_ONE_OR_TWO,
						CellMonthSummaryInfo.class);
		return cellMonthSummaryInfoList;
	}

	/**
	 * 功能:环比数据（三四级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<RowMonthSummaryInfo> getChainMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;
		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);

		param.put("beginTime", DateUtil.lastMonth(conditionEntity
				.getBeginTime()));// 环比，需取开始时间再上一个月
		param.put("endTime", conditionEntity.getEndTime());

		List<RowMonthSummaryInfo> rowMonthSummaryInfoList = sqlDao
				.queryListBySql(param, Param_THIRD_OR_FOUR,
						RowMonthSummaryInfo.class);
		return rowMonthSummaryInfoList;
	}

	/**
	 * 功能:同比数据（一二级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<CellMonthSummaryInfo> getComparedCellMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;

		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);
		param.put("beginTime", DateUtil.comparYearTime(conditionEntity
				.getBeginTime(), -conditionEntity.getCircle()));// 取最大时间范围
		param.put("endTime", conditionEntity.getEndTime());

		List<CellMonthSummaryInfo> cellMonthSummaryInfoList = sqlDao
				.queryListBySql(param, Param_ONE_OR_TWO,
						CellMonthSummaryInfo.class);
		return cellMonthSummaryInfoList;
	}

	/**
	 * 功能:同比数据（三四级数据）
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:13:06
	 * 
	 * @param conditionEntity
	 * @return
	 * @throws Exception
	 */
	public static List<RowMonthSummaryInfo> getComparedMonthSummaryInfoResult(
			ConditionEntity conditionEntity, int type) throws Exception
	{
		SqlDao sqlDao = Constant.SqlDao;

		Map<String, Object> param = ParamTool.getParam(new HashMap(),
				conditionEntity, type);
		param.put("beginTime", DateUtil.comparYearTime(conditionEntity
				.getBeginTime(), -conditionEntity.getCircle()));// 取最大时间范围
		param.put("endTime", conditionEntity.getEndTime());
		List<RowMonthSummaryInfo> rowMonthSummaryInfoList = sqlDao
				.queryListBySql(param, Param_THIRD_OR_FOUR,
						RowMonthSummaryInfo.class);
		return rowMonthSummaryInfoList;
	}

	/**
	 * 功能:获取同比环比单维多项目的x轴标题
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午05:46:21
	 */
	public static String getXname(ConditionEntity conditionEntity)
	{
		String x_name = ImgTool.getDimensionAllStr(conditionEntity);
		return x_name;
	}

	/**
	 * 功能: 从一级分类切换到二级分类进行匹配，或者三级到四级分类的切换的索引位置
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:49:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static int getChangIndex(ConditionEntity conditionEntity, int type)
	{
		int changOneOrTwo = 0;
		String itemClassOne = conditionEntity.getItemClassOne(); // 一级维度
		String itemClassThird = conditionEntity.getItemClassThird(); // 三级维度

		if (type == Constant.PARAM_ONE_OR_TWO)
		{

			changOneOrTwo = StringUtils.isEmpty(itemClassOne) ? 0
					: itemClassOne.split(",").length;// 为后面循环到哪个索引的时候，从一级分类切换到二级分类进行匹配

		} else if (type == Constant.PARAM_THIRD_OR_FOUR)
		{

			changOneOrTwo = StringUtils.isEmpty(itemClassThird) ? 0
					: itemClassThird.split(",").length;// 为后面循环到哪个索引的时候，从三级分类切换到四级分类进行匹配

		}
		return changOneOrTwo;

	}

	/**
	 * 功能: 从一级分类切换到二级分类进行匹配（ID）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:49:21
	 * 
	 * @param index
	 *            图例索引
	 * @param conditionEntity
	 * @return
	 */
	public static String getchangOneOrTwoValue(int changOneOrTwo, int index,
			CellMonthSummaryInfo cellMonthSummaryInfo)
	{
		String classTypeId = "";
		if (index < changOneOrTwo)
		{ // 采用一级分类
			classTypeId = cellMonthSummaryInfo.getFinanceCostsTypeId()
					.toString();
		} else
		{ // 采用二级分类
			classTypeId = cellMonthSummaryInfo.getFinanceCostsCategoriesId()
					.toString();
		}

		return classTypeId;
	}

	/**
	 * 功能: 从三级分类切换到四级分类进行匹配（ID）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:49:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getchangThridOrFourValue(int changOneOrTwo, int index,
			RowMonthSummaryInfo rowMonthSummaryInfo)
	{
		String classTypeId = "";
		if (index < changOneOrTwo)
		{ // 采用三级分类
			classTypeId = rowMonthSummaryInfo.getClassifyId().toString();
		} else
		{ // 采用四级分类
			classTypeId = rowMonthSummaryInfo.getClassifyInfoTwoId().toString();
		}
		return classTypeId;
	}

	/**
	 * 功能: 从一级分类切换到二级分类进行匹配(名字)
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:49:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getchangOneOrTwoValueName(int changOneOrTwo,
			int index, CellMonthSummaryInfo cellMonthSummaryInfo)
	{
		String classTypeName = "";
		if (index < changOneOrTwo)
		{ // 采用一级分类
			classTypeName = cellMonthSummaryInfo.getFinanceCostsTypeName()
					.toString();
		} else
		{ // 采用二级分类
			classTypeName = cellMonthSummaryInfo
					.getFinanceCostsCategoriesName().toString();
		}

		return classTypeName;
	}

	/**
	 * 功能: 从三级分类切换到四级分类进行匹配（名字）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:49:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getchangThridOrFourValueName(int changOneOrTwo,
			int index, RowMonthSummaryInfo rowMonthSummaryInfo)
	{
		String classTypeName = "";
		if (index < changOneOrTwo)
		{ // 采用三级分类
			classTypeName = rowMonthSummaryInfo.getClassifyName().toString();
		} else
		{ // 采用四级分类
			classTypeName = rowMonthSummaryInfo.getClassifyInfoTwoName()
					.toString();
		}
		return classTypeName;
	}

	/**
	 * 求环比图例（单项目多维度） [本月A，上月A，本月B,上月B] type:0 求全部
	 * 
	 * @return
	 */
	public static String[] getRowKeys(ConditionEntity conditionEntity, int type)
	{
		String[] arrayItem = null;
		String[] array = null;
		if (type == 0)
		{
			arrayItem = ImgTool.getRowKeys(conditionEntity);// 图例
		} else
		{
			arrayItem = ImgTool.getDimensionAllName(conditionEntity, type);// 图例
		}

		if (arrayItem != null)
		{
			array = ReportTool.getArrayAdd(arrayItem, CHAIN_ARRAY);
			return array;
		}
		return null;
	}

	/**
	 * 求同比图例(单项目多维度)[2014维度A，2013维度A,2014维度B，2013维度B] type:0 求全部
	 * 
	 * @return
	 */
	public static String[] getComparedRowKeys(ConditionEntity conditionEntity,
			int type)// 图例
	{

		String[] arrayItem = null;
		String[] array = null;
		String[] arrayYear = DateUtil.getYearTlist(conditionEntity);

		if (type == 0)
		{
			arrayItem = ImgTool.getDimensionAll(conditionEntity);// 图例
		} else
		{
			arrayItem = ImgTool.getDimensionAllName(conditionEntity, type);// 图例
		}
		if (arrayItem != null)
		{
			array = ReportTool.getArrayAdd(arrayItem, arrayYear);
			return array;
		}
		return null;
	}

	/**
	 * 功能:求出该地区品牌id数组
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午03:29:36
	 * 
	 * @param brandsInfos
	 * @return
	 */
	public static String[] getColumnKeys(List<BrandsInfo> brandsInfos)
	{
		String[] columnKeys = new String[brandsInfos.size()];
		int count = 0;
		for (BrandsInfo area : brandsInfos)
		{
			columnKeys[count] = area.getId().toString();
			count++;
		}
		return columnKeys;
	}

	/**
	 * 功能:获取地区横座标
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:30:09
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getColumnKeys(ConditionEntity conditionEntity,
			List<AreaInfo> areaList)
	{
		String[] columnKeys = new String[areaList.size()];
		int count = 0;
		for (AreaInfo area : areaList)
		{
			columnKeys[count] = area.getAreaName();
			count++;
		}
		return columnKeys;
	}

	/**
	 * 功能:求两数组的组装，用于同比、环比图例
	 * <p>
	 * 作者 杨荣忠 2015-1-22 下午02:03:35
	 * 
	 * @return
	 */
	public static String[] getArrayAdd(String[] first, String[] second)
	{
		int Item_length = first.length;
		int timeType_length = second.length;
		String[] array = null;
		for (int i = 0; i < Item_length; i++)
		{
			for (int j = 0; j < timeType_length; j++)
			{
				array = (String[]) ArrayUtils.add(array, second[j] + first[i]);// [本月NB，上月NB，本月OS,上月OS]
			}
		}
		return array;
	}

	/**
	 * 求同比，环比横座标
	 * 
	 * @return
	 */
	public static String[] getComparedColumnKeys(ConditionEntity conditionEntity)
	{
		List monthList = DateUtil.getMonthList(conditionEntity.getBeginTime(),
				conditionEntity.getEndTime());
		return (String[]) monthList.toArray(new String[monthList.size()]);
	}

}
