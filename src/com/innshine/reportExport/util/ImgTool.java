package com.innshine.reportExport.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.ArrayUtil;
import org.springframework.web.context.WebApplicationContext;

import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.chart.ChartConstants;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.reportService.ChartServiceImpl;

/**
 * <code>ImgTool.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午03:40:28
 * @version 1.0 </br>最后修改人 无
 */
public class ImgTool
{
	public static String ALL_COST = "主营业务收入";
	public static String OTHER_ALL_COST = "其它业务收入";
	public static String OTHER = "其它";

	/**
	 * 功能:表名
	 * <p>
	 * 作者 杨荣忠 2015-1-21 下午01:18:35
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getReportName(ConditionEntity conditionEntity,
			String other)
	{
		StringBuffer name = new StringBuffer();
		String beginTime = conditionEntity.getBeginTime();
		String endTime = conditionEntity.getEndTime();
		name.append(other);
		name.append(beginTime + "至" + endTime);
		// name.append("("+getDimension(conditionEntity)+")");
		name.append(getImgType(conditionEntity));
		name.append(getExportType(conditionEntity));
		name.append("分析图");
		return name.toString();
	}

	/**
	 * 功能:获取总分类(从左到右先大分类再小分类)
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:51:47
	 * 
	 * @param conditionEntity
	 * @return
	 */
	/**
	 * 拼接维度信息，字符串以逗号“,”拼接，如果：1,2,3,4
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getDimensionAll(ConditionEntity conditionEntity)
	{
		String str = getDimensionAllStr(conditionEntity);
		return str.split(",");
	}

	public static String getDimensionAllStr(ConditionEntity conditionEntity)
	{
		StringBuffer buffer = new StringBuffer();
		if (null != conditionEntity)
		{
			String itemClassOne = conditionEntity.getItemClassOne(); // 一级维度名字
			String itemClassTwo = conditionEntity.getItemClassTwo(); // 二级维度名字
			String itemClassThird = conditionEntity.getItemClassThird(); // 三级维度名字
			String itemClassFour = conditionEntity.getItemClassFour();// 四级维度名字

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
	 * 功能:获取分类ID(从左到右)获取一二级的图例，或三四级的图例ID
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:51:47
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getDimensionAllID(ConditionEntity conditionEntity,
			int type)
	{
		StringBuffer dimension = new StringBuffer();
		String itemClassOneID = conditionEntity.getItemClassOneId(); // 一级id
		String itemClassTwoID = conditionEntity.getItemClassTwoId();// 二级id
		String itemClassThirdID = conditionEntity.getItemClassThirdId(); // 三级id
		String itemClassFourID = conditionEntity.getItemClassFourId();// 四级id
		if (type == Constant.PARAM_ONE_OR_TWO)
		{
			if (!StringUtils.isEmpty(itemClassOneID)&&!StringUtils.isEmpty(itemClassTwoID))
			{
               dimension.append(itemClassOneID).append(",").append(itemClassTwoID);
				
			}
			else if(!StringUtils.isEmpty(itemClassOneID)&&StringUtils.isEmpty(itemClassTwoID))
			{
				 dimension.append(itemClassOneID);
			}
			else{
				dimension.append(itemClassTwoID);
			}
		}
		else if (type == Constant.PARAM_THIRD_OR_FOUR)
		{
			if (!StringUtils.isEmpty(itemClassThirdID)&&!StringUtils.isEmpty(itemClassFourID))
			{
               dimension.append(itemClassThirdID).append(",").append(itemClassFourID);
				
			}
			else if(!StringUtils.isEmpty(itemClassThirdID)&&StringUtils.isEmpty(itemClassFourID))
			{
				 dimension.append(itemClassThirdID);
			}
			else{
				dimension.append(itemClassFourID);
			}

		}
		return StringUtils.isEmpty(dimension.toString())? null:dimension.toString().split(",");
	}
	

	/**
	 * 功能:获取分类ID(从左到右)获取一二级的图例，或三四级的图例（名字）
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:51:47
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getDimensionAllName(ConditionEntity conditionEntity,
			int type)
	{
		StringBuffer dimension = new StringBuffer();
		
		String itemClassOne = conditionEntity.getItemClassOne(); // 一级名字
		String itemClassTwo = conditionEntity.getItemClassTwo();// 二级名字
		String itemClassThird = conditionEntity.getItemClassThird(); // 三级名字
		String itemClassFour = conditionEntity.getItemClassFour();// 四级名字
		
		if (type == Constant.PARAM_ONE_OR_TWO)
		{
			if (!StringUtils.isEmpty(itemClassOne)&&!StringUtils.isEmpty(itemClassTwo))
			{
               dimension.append(itemClassOne).append(",").append(itemClassTwo);
				
			}
			else if(!StringUtils.isEmpty(itemClassOne)&&StringUtils.isEmpty(itemClassTwo))
			{
				 dimension.append(itemClassOne);
			}
			else{
				dimension.append(itemClassTwo);
			}
			
		}
		
		else if (type == Constant.PARAM_THIRD_OR_FOUR)
		{
			if (!StringUtils.isEmpty(itemClassThird)&&!StringUtils.isEmpty(itemClassFour))
			{
               dimension.append(itemClassThird).append(",").append(itemClassFour);
				
			}
			else if(!StringUtils.isEmpty(itemClassThird)&&StringUtils.isEmpty(itemClassFour))
			{
				 dimension.append(itemClassThird);
			}
			else{
				dimension.append(itemClassFour);
			}
		}
		return StringUtils.isEmpty(dimension.toString())? null:dimension.toString().split(",");
	}

	/**
	 * 功能:获取导出图类型
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:52:06
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getImgType(ConditionEntity conditionEntity)
	{
		int typeImg = conditionEntity.getTypeImg();
		String str = "";
		if (typeImg == 1)
		{
			str = "折线图";
		}
		if (typeImg == 2)
		{
			str = "柱形图";
		}
		if (typeImg == 3)
		{
			str = "饼状图";
		}
		return str;
	}

	/**
	 * 功能:获取导出图类型
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:52:06
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static int getChartImgType(ConditionEntity conditionEntity)
	{
		int typeImg = conditionEntity.getTypeImg();
		int str = 0;
		if (typeImg == 1)
		{
			str = ChartConstants.LINE_CHART;
		}
		if (typeImg == 2)
		{
			str = ChartConstants.BAR_CHART;
		}
		if (typeImg == 3)
		{
			str = ChartConstants.PIE_CHART;
		}
		return str;
	}

	/**
	 * 功能:获取导出分析类型
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:52:25
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String getExportType(ConditionEntity conditionEntity)
	{
		int type = conditionEntity.getType();
		String str = "";
		if (type == 1)
		{
			str = "同比";
		}
		if (type == 2)
		{
			str = "环比";
		}
		if (type == 3)
		{
			str = "占比";
		}
		if (type == 4)
		{
			str = "地区比";
		}
		return str;
	}

	/**
	 * 功能:图例（由前端过来的值：先大分类再小分类，从左到右 ,一，二，三，四级顺序）
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午03:56:37
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getRowKeys(ConditionEntity conditionEntity)
	{
		String[] rowKeys = getDimensionAll(conditionEntity);
		return rowKeys;
	}

	/**
	 * 功能:获取除去占比经营数据总收的其它图例
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午05:04:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getZhabiDetach(ConditionEntity conditionEntity)
	{
		String[] array = getDimensionAll(conditionEntity);
		String[] result = (String[]) ArrayUtils.removeElement(array, ALL_COST);
		result = (String[]) ArrayUtils.removeElement(result, OTHER_ALL_COST);
		String[] result_add = (String[]) ArrayUtils.add(result, OTHER);
		return result_add;
	}

	/**
	 * 功能:获取除去占比经营数据总收的其它图例Id(一二级别或，三四级别的ID)
	 * <p>
	 * 作者 杨荣忠 2015-1-19 下午05:04:21
	 * 
	 * @param conditionEntity
	 * @return
	 */
	public static String[] getZhabiDetachId(ConditionEntity conditionEntity,
			int type)
	{
		String[] array = ImgTool.getDimensionAllID(conditionEntity, type);
		
		String[] result_add = (String[]) ArrayUtils.remove(array, 0);// 除去主营图例ID
		return result_add;
	}

	/**
	 * 根据维度编号,判断当前选择维度，如果同时出现多个维度，有多个维度则按第一个不为空维度算，
	 * 用于单维度多项目，且只有一个维度
	 * @param conditionEntity
	 *            判断当前选择维度
	 * @return true:一级维度、二级维度    false:三级维度、四级维度
	 */
	public static boolean isSelectDimension(ConditionEntity conditionEntity)
	{
		int count =-1;
		if (null != conditionEntity)
		{
			String itemClassOneId = conditionEntity.getItemClassOneId();
			String itemClassTwoId = conditionEntity.getItemClassTwoId();
			String itemClassThirdId = conditionEntity.getItemClassThirdId();
			String itemClassFourId = conditionEntity.getItemClassFourId();

			if (StringUtils.isNotEmpty(itemClassOneId)
					|| StringUtils.isNotEmpty(itemClassTwoId))
			{
				count = 1;
			}
			if (StringUtils.isNotEmpty(itemClassThirdId)
					|| StringUtils.isNotEmpty(itemClassFourId))
			{
				count = 2;
			}

		}

		return count ==1 ?  true :false;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		ConditionEntity conditionEntity = new ConditionEntity();
		conditionEntity.setItemClassOneId("3");
		//conditionEntity.setItemClassThirdId("3");
		//System.out.println(ReportTool.getXname(conditionEntity));
		System.out.println(ImgTool.isSelectDimension(conditionEntity));
	}

}
