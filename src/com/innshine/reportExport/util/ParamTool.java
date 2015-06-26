package com.innshine.reportExport.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.base.dao.SqlDao;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.reportExport.Constants;
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
public class ParamTool
{
	public static int ITEM_CLASS_NAME = 0;

	public static int ITEM_CLASS_ID_NAME = 1;

	/**
	 * 功能:判断是示一二级数据还是三级四级数据的SQL方法 数据集获取 type=1：求一组二级参数，type=2,求三级四级参数
	 * <p>
	 * 作者 杨荣忠 2015-1-26 下午01:49:21
	 * 
	 * @param type
	 * @return
	 */
	public static String getSQLName(int type)
	{
		String sqlName = "";

		if (type == Constant.PARAM_ONE_OR_TWO)
		{

			sqlName = "Param_ONE_OR_TWO.all";

		} else if (type == Constant.PARAM_THIRD_OR_FOUR)
		{

			sqlName = "Param_THIRD_OR_FOUR.all";

		}
		return sqlName;
	}

	/**
	 * 功能:判断采用哪种策略进行导出
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午01:38:53
	 */
	public static String checkType(int type)
	{
		String strategy_name = "";

		switch (type)
		{
		case 1:
			/**
			 * 同比:单项目多维度
			 */
			strategy_name = Constant.COMPARED_ONE_ITEM_MANY_DIMENSION;
			break;
		case 2:
			/**
			 * 同比:单维度多项目
			 */
			strategy_name = Constant.COMPARED_MANY_ITEM_ONE_DIMENSION;
			break;
		case 3:
			/**
			 * 环比:单项目多维度
			 */
			strategy_name = Constant.CHAIN_ONE_ITEM_MANY_DIMENSION;
			break;
		case 4:
			/**
			 * 环比:单维度多项目
			 */
			strategy_name = Constant.CHAIN_MANY_ITEM_ONE_DIMENSION;
			break;

		case 5:
			/**
			 * 占比:单项目多维度
			 */
			strategy_name = Constant.PROPORTION_ONE_ITEM_MANY_DIMENSION;
			break;
		case 6:
			/**
			 * 地区:多维度比
			 */
			strategy_name = Constant.AREA_MANY_DIMENSION;
			break;
		}
		return strategy_name;
	}

	/**
	 * 功能:判断是不是这个地区的品牌
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午03:46:52
	 * 
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean checkAreaBranId(String[] arr, String targetValue)
	{
		return Arrays.asList(arr).contains(targetValue);
	}



	/**
	 * 拼接维度信息，字符串以逗号“,”拼接，如果：1,2,3,4
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getDimensionAll(ConditionEntity conditionEntity)
	{
		StringBuffer buffer = new StringBuffer();
		if (null != conditionEntity)
		{
			String itemClassOne = conditionEntity.getItemClassOne(); // 一级维度名字
			String itemClassTwo = conditionEntity.getItemClassTwo(); // 二级维度名字
			String itemClassThird = conditionEntity.getItemClassThird(); // 三级维度名字
			String itemClassFour = conditionEntity.getItemClassFour();// 三级维度名字

			if (null != itemClassOne
					&& StringUtils.isNotEmpty(itemClassOne.trim()))
			{
				buffer.append(itemClassOne.trim());
			}

			if (null != itemClassTwo
					&& StringUtils.isNotEmpty(itemClassTwo.trim()))
			{
				appendCommaSymbol(buffer);

				buffer.append(itemClassTwo.trim());
			}

			if (null != itemClassThird
					&& StringUtils.isNotEmpty(itemClassThird.trim()))
			{
				appendCommaSymbol(buffer);
				buffer.append(itemClassThird.trim());
			}

			if (null != itemClassFour
					&& StringUtils.isNotEmpty(itemClassFour.trim()))
			{
				appendCommaSymbol(buffer);
				buffer.append(itemClassFour.trim());
			}

		}
		return buffer.toString();
	}

	private static void appendCommaSymbol(StringBuffer buffer)
	{
		if (StringUtils.isNotEmpty(buffer.toString()))
		{
			buffer.append(",");
		}
	}

	/**
	 * 功能:求维度的在时间范围内的总销售额（一二级）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午04:14:13
	 * 
	 * @param result
	 * @return
	 */
	public static Double getSumOneToTwo(List<CellMonthSummaryInfo> result)
	{
		double value = 0;
		if (!CollectionUtils.isEmpty(result))
		{
			for (int i = 0; i < result.size(); i++)
			{
				try
				{
					value += result.get(i).getMonthSummaryAmount()
							.doubleValue();
				} catch (Exception e)
				{
					value += 0;
				}
			}
		}
		return value;
	}
	
	/**
	 * 功能:求维度的在时间范围内的总销售额（三四级）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午04:14:13
	 * 
	 * @param result
	 * @return
	 */
	public static Double getSumThirdToFour(List<RowMonthSummaryInfo> result)
	{
		double value = 0;
		if (!CollectionUtils.isEmpty(result))
		{
			for (int i = 0; i < result.size(); i++)
			{
				try
				{
					value += result.get(i).getMonthSummaryAmount()
							.doubleValue();
				} catch (Exception e)
				{
					value += 0;
				}
			}
		}
		return value;
	}

	/**
	 * 功能:通用SQL参数设置 
	 * type=1：设置一级二级参数，
	 * type=2, 设置三级四级参数
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:36:39
	 * 
	 * @param param
	 * @param conditionEntity
	 * @return
	 */
	public static Map<String, Object> getParam(Map<String, Object> param,
			ConditionEntity conditionEntity, int type)
	{
		if (StringUtils.isNotEmpty(conditionEntity.getBeginTime()))
		{
			param.put("beginTime", conditionEntity.getBeginTime());
		}
		if (StringUtils.isNotEmpty(conditionEntity.getEndTime()))
		{
			param.put("endTime", conditionEntity.getEndTime());
		}
		if (StringUtils.isNotEmpty(conditionEntity.getItemId()))
		{
			param.put("brandId", conditionEntity.getItemId());
		}

		if (type == Constant.PARAM_ONE_OR_TWO)
		{
			param = classParamOneOrTwo(param, conditionEntity);
		} else if (type == Constant.PARAM_THIRD_OR_FOUR)
		{
			param = classParamThirdOrThird(param, conditionEntity);
		}

		return param;
	}

	/**
	 * 功能:处理前端过来的分类（一级二级在SQL里的参数配置）
	 * <p>
	 * 作者 杨荣忠 2015-1-23 上午09:31:53
	 * 
	 * @param param
	 * @param conditionEntity
	 * @return
	 */
	public static Map<String, Object> classParamOneOrTwo(
			Map<String, Object> param, ConditionEntity conditionEntity)
	{
		String itemClass = "";
		String itemClassOneId = conditionEntity.getItemClassOneId().trim();
		String itemClassTwoId = conditionEntity.getItemClassTwoId().trim();

		// 一级二级都有
		if (!itemClassOneId.equals("") && !itemClassTwoId.equals(""))
		{
			String value = "AND  ( a.finance_costs_type_id  IN("
					+ itemClassOneId
					+ ") OR a.finance_costs_categories_id IN ("
					+ itemClassTwoId + ") )";
			param.put("itemClass", value);
		}

		// 一级有，二级分类没有
		else if (!itemClassOneId.equals("") && itemClassTwoId.equals(""))
		{
			String value = "AND   a.finance_costs_type_id  IN("
					+ itemClassOneId + ")";
			param.put("itemClass", value);
		}

		// 一级分类没有，二级有
		else if (itemClassOneId.equals("") && !itemClassTwoId.equals(""))
		{
			String value = "AND  a.finance_costs_categories_id IN ("
					+ itemClassTwoId + ")";
			param.put("itemClass", value);
		}
		return param;

	}

	/**
	 * 功能:处理前端过来的分类（三级四级在SQL里的参数配置）
	 * <p>
	 * 作者 杨荣忠 2015-1-23 上午09:31:53
	 * 
	 * @param param
	 * @param conditionEntity
	 * @return
	 */
	public static Map<String, Object> classParamThirdOrThird(
			Map<String, Object> param, ConditionEntity conditionEntity)
	{
		String itemClass = "";

		String itemClassThirdId = conditionEntity.getItemClassThirdId().trim();
		String itemClassFourId = conditionEntity.getItemClassFourId().trim();

		// 三级四级都有
		if (!itemClassThirdId.equals("") && !itemClassFourId.equals(""))
		{
			String value = "AND  ( a.classify_id IN(" + itemClassThirdId
					+ ") OR a.classify_info_two_id IN (" + itemClassFourId
					+ ") )";
			param.put("itemClass", value);
		}
		// 三级有，四级分类没有
		else if (!itemClassThirdId.equals("") && itemClassFourId.equals(""))
		{
			String value = "AND   a.classify_id IN(" + itemClassThirdId + ")";
			param.put("itemClass", value);
		}
		// 三级分类没有，四级有
		else if (itemClassThirdId.equals("") && !itemClassFourId.equals(""))
		{
			String value = "AND  a.classify_info_two_id IN ("
					+ itemClassFourId + ")";
			param.put("itemClass", value);
		}
		return param;

	}

	/**
	 * 功能:前端过来的bean加工（很重要，例如分类value处理，时间等统一处理）
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:36:59
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static ConditionEntity chageConditionEntity(
			ConditionEntity conditionEntity)
	{
		String beginTime = conditionEntity.getBeginTime().trim();
		String endTime = conditionEntity.getEndTime().trim();

		// 处理成2014-01-01的格式
		if (!beginTime.equals(""))
		{
			conditionEntity.setBeginTime(beginTime + "-01");
		}
		if (!endTime.equals(""))
		{
			conditionEntity.setEndTime(endTime + "-01");
		}

		String itemClassOne = conditionEntity.getItemClassOne(); // 一级维度
		String itemClassTwo = conditionEntity.getItemClassTwo();// 二级维度
		String itemClassThird = conditionEntity.getItemClassThird(); // 三级维度
		String itemClassFour = conditionEntity.getItemClassFour();// 四级维度

		String item = conditionEntity.getItem();
		conditionEntity.setItemId(getStringValuesStr(item, ITEM_CLASS_ID_NAME));
		conditionEntity.setItem(getStringValuesStr(item, ITEM_CLASS_NAME));

		conditionEntity.setItemClassOneId(getStringValuesStr(itemClassOne,
				ITEM_CLASS_ID_NAME));
		conditionEntity.setItemClassTwoId(getStringValuesStr(itemClassTwo,
				ITEM_CLASS_ID_NAME));
		conditionEntity.setItemClassThirdId(getStringValuesStr(itemClassThird,
				ITEM_CLASS_ID_NAME));
		conditionEntity.setItemClassFourId(getStringValuesStr(itemClassFour,
				ITEM_CLASS_ID_NAME));

		conditionEntity.setItemClassOne(getStringValuesStr(itemClassOne,
				ITEM_CLASS_NAME));
		conditionEntity.setItemClassTwo(getStringValuesStr(itemClassTwo,
				ITEM_CLASS_NAME));
		conditionEntity.setItemClassThird(getStringValuesStr(itemClassThird,
				ITEM_CLASS_NAME));
		conditionEntity.setItemClassFour(getStringValuesStr(itemClassFour,
				ITEM_CLASS_NAME));

		return conditionEntity;
	}



	/**
	 * 功能:bean的一级分类与二级分类 加工方法：name&ID (valueOrId为0的时候，取name,1的时候取ID)
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午05:37:23
	 * 
	 * @param valueString
	 * @param valueOrId
	 * @return
	 */
	public static String getStringValuesStr(String valueString, int valueOrId)
	{
		StringBuffer valueArray = new StringBuffer();
		if (valueString.trim() != "")
		{
			String[] str = valueString.split(",");
			for (int i = 0; i < str.length; i++)
			{
				String[] a = str[i].split("&");
				valueArray.append(a[valueOrId]);
				if (i < str.length - 1)
				{
					valueArray.append(",");
				}
			}
		}
		return valueArray.toString();
	}
	
	
	
	/**
	 * 功能:一二级与三四级合作
	 * <p>作者 杨荣忠 2015-1-27 上午10:47:07
	 * @param dataOnetoTwo
	 * @param dataThridtoFour
	 * @return
	 */
	public static double[][] getData(double[][] dataOnetoTwo, double[][] dataThridtoFour ){
		double[][] data = null;
		if(dataOnetoTwo!=null&&dataThridtoFour!=null){
			data = (double[][]) ArrayUtils.addAll(dataOnetoTwo, dataThridtoFour);
		}
		else if(dataOnetoTwo!=null&&dataThridtoFour==null){
			data =dataOnetoTwo;
		}else if(dataOnetoTwo==null&&dataThridtoFour!=null){
			data =dataThridtoFour;
		}
		return data;
	} 
	
	/**
	 * 功能:一二级与三四级合作
	 * <p>作者 杨荣忠 2015-1-27 上午10:47:07
	 * @param dataOnetoTwo
	 * @param dataThridtoFour
	 * @return
	 */
	public static double[] getData(double[] dataOnetoTwo, double[] dataThridtoFour ){
		double[] data = null;
		if(dataOnetoTwo!=null&&dataThridtoFour!=null){
			data = (double[]) ArrayUtils.addAll(dataOnetoTwo, dataThridtoFour);
		}
		else if(dataOnetoTwo!=null&&dataThridtoFour==null){
			data =dataOnetoTwo;
		}else if(dataOnetoTwo==null&&dataThridtoFour!=null){
			data =dataThridtoFour;
		}
		return data;
	} 
	

	// 测试main
	public static void main(String[] args) throws Exception
	{
		String a[] = { "2", "3", "5" };
		System.out.println(checkAreaBranId(a, "9"));
		// System.out.println(getStringValuesStr("s&1,3&3",0));

	}
}
