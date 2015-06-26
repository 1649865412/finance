package com.innshine.reportExport.reportInterface;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.innshine.chart.entity.ImgPathLine;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;
import com.innshine.reportExport.reportService.ChartServiceImpl;
import com.innshine.reportExport.util.Constant;
import com.innshine.reportExport.util.DateUtil;
import com.innshine.reportExport.util.ImgTool;
import com.innshine.reportExport.util.ParamTool;
import com.innshine.reportExport.util.ReportTool;

/**
 * <code>ChainManyItemOneDimension.java 环比:单维度多项目 </code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:35:06
 * @version 1.0 </br>最后修改人 无
 */
public class ChainManyItemOneDimension extends Abstrategy
{

	public static String NOW_Month = "本月";
	public static String LAST_MONTH = "上月";

	public static String[] CHAIN_ARRAY = { "本月", "上月" };

	public static String yname = "费用（元）";

	@Override
	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{

		System.out.println("环比:单维度多项目策略");
		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();

		HttpServletRequest request = interfaceExportImpl.getRequest();
		String xname = "维度（" + ReportTool.getXname(conditionEntity) + "）:各项目环比";

		String[] rowKeys = getRowKeys(conditionEntity);// 图例[本月NB，上月NB，本月OS,上月OS]

		String[] columnKeys = null;// 横座标 [1，2，3，4，5]

		double[][] data = null;// 数据集

		try
		{
			columnKeys = ReportTool.getComparedColumnKeys(conditionEntity);
			data = getData(conditionEntity, columnKeys, rowKeys);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		String reportName = ImgTool.getReportName(conditionEntity, "");
		int imgtype = ImgTool.getChartImgType(conditionEntity);

		ImgPathLine imgPathLine = new ImgPathLine(data, rowKeys, columnKeys,
				xname, yname, reportName, imgtype);

		return new ChartServiceImpl().getImageHtml(imgPathLine, request);
	}

	/**
	 * 功能:四个级别的数据组装
	 * <p>作者 杨荣忠 2015-1-27 下午02:35:49
	 * @param conditionEntity
	 * @param columnKeys
	 * @param rowKeys
	 * @return
	 * @throws Exception
	 */
	public double[][] getData(ConditionEntity conditionEntity,
			String[] columnKeys, String[] rowKeys) throws Exception
	{
		double[][] data = null;

		// 判断维度是在哪个级别
		if (ImgTool.isSelectDimension(conditionEntity))
		{
			data = getDataOnetoTwo(conditionEntity, columnKeys, rowKeys);
		} else
		{
			data = getDataThridtoFour(conditionEntity, columnKeys, rowKeys);
		}
		return data;
	}

	/**
	 * 求环比图例（时间+项目）图例 [本月NB，上月NB，本月OS,上月OS]
	 * 
	 * @return
	 */
	public String[] getRowKeys(ConditionEntity conditionEntity)
	{
		String[] arrayItem = conditionEntity.getItem().split(",");// 图例
		String[] array = ReportTool.getArrayAdd(arrayItem, CHAIN_ARRAY);
		return array;
	}

	/**
	 * 求环比数据集，匹配年月，维度(一二级)
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataOnetoTwo(ConditionEntity conditionEntity,
			String[] columnKeys, String[] rowKeys) throws Exception
	{
		int type = Constant.PARAM_ONE_OR_TWO;
		/*
		 * int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
		 * CHAIN_ARRAY.length;
		 */

		double[][] data = new double[rowKeys.length][columnKeys.length];

		// 单维度
		// String dimension = ReportTool.getXname(conditionEntity);

		List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool
				.getCellMonthSummaryInfoResult(conditionEntity, type);

		for (int j = 0; j < rowKeys.length; j++)// 图例 [本月NB，上月NB，本月OS,上月OS]
		{
			for (int i = 0; i < columnKeys.length; i++)// x轴(月份) [2,3,4]
			{
				List<CellMonthSummaryInfo> result = new ArrayList();
				for (int k = 0; k < cellMonthSummaryInfoList.size(); k++)
				{// 值记录

					CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
							.get(k);

					String branName = cellMonthSummaryInfo.getBrandName();
					String month = cellMonthSummaryInfo.getMonthTime();

					String rowKeysValue = rowKeys[j].substring(0, 2);
					String brandNameValue = rowKeys[j].substring(2);
					String lastOrNowMonth = "";

					// 判断是不是要求的是上个月还是本月的环比数据（月份仅限今年）
					if (rowKeysValue.equals(NOW_Month))
					{
						lastOrNowMonth = columnKeys[i];
					} else
					{
						String monthValue = columnKeys[i];
						String year = cellMonthSummaryInfo.getYearTime();
						lastOrNowMonth = DateUtil
								.getLastMonth(monthValue, year);
					}

					if (month.equals(lastOrNowMonth)
							&& branName.equals(brandNameValue))// 判断是不是这个时间月份的+判断是不是这个品牌的
					{
						/*
						 * String classTypeName = ReportTool
						 * .getchangOneOrTwoValueName(changOneOrTwo, j,
						 * cellMonthSummaryInfo);
						 * 
						 * if (dimension.equals(classTypeName))// 判断是不是这个维度的 {
						 */
						result.add(cellMonthSummaryInfo);
						// }
					}
				}
				data[j][i] = ParamTool.getSumOneToTwo(result);
			}
		}
		return data;
	}

	/**
	 * 求环比数据集，匹配年月，维度(三四级)
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataThridtoFour(ConditionEntity conditionEntity,
			String[] columnKeys, String[] rowKeys) throws Exception
	{
		int type = Constant.PARAM_THIRD_OR_FOUR;

		double[][] data = new double[rowKeys.length][columnKeys.length];

		// 单维度
		// String dimension = ReportTool.getXname(conditionEntity);

		List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
				.getRowMonthSummaryInfoResult(conditionEntity, type);

		/*
		 * int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
		 * CHAIN_ARRAY.length;
		 */
		for (int j = 0; j < rowKeys.length; j++)// 图例 [本月NB，上月NB，本月OS,上月OS]
		{
			for (int i = 0; i < columnKeys.length; i++)// x轴(月份) [2,3,4]
			{
				List<RowMonthSummaryInfo> result = new ArrayList();
				for (int k = 0; k < rowMonthSummaryInfoList.size(); k++)
				{// 值记录

					RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
							.get(k);

					String branName = rowMonthSummaryInfo.getBrandName();
					String month = rowMonthSummaryInfo.getMonthTime();

					String rowKeysValue = rowKeys[j].substring(0, 2);
					String brandNameValue = rowKeys[j].substring(2);
					String lastOrNowMonth = "";

					// 判断是不是要求的是上个月还是本月的环比数据（月份仅限今年）
					if (rowKeysValue.equals(NOW_Month))
					{
						lastOrNowMonth = columnKeys[i];
					} else
					{
						String monthValue = columnKeys[i];
						String year = rowMonthSummaryInfo.getYearTime();
						lastOrNowMonth = DateUtil
								.getLastMonth(monthValue, year);
					}

					if (month.equals(lastOrNowMonth)
							&& branName.equals(brandNameValue))// 判断是不是这个时间月份的+判断是不是这个品牌的
					{
						/*
						 * String classTypeName = ReportTool
						 * .getchangThridOrFourValueName(changOneOrTwo, j,
						 * rowMonthSummaryInfo);
						 * 
						 * if (dimension.equals(classTypeName))// 判断是不是这个维度的 {
						 */
						result.add(rowMonthSummaryInfo);
						// }
					}
				}
				data[j][i] = ParamTool.getSumThirdToFour(result);
			}
		}
		return data;
	}

}
