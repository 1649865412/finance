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
 * <code>ComparedManyItemOneDimension.java  同比:单维度多项目</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:38:45
 * @version 1.0 </br>最后修改人 无
 */
public class ComparedManyItemOneDimension extends Abstrategy
{
	public static String yname = "费用（元）";

	@Override
	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{
		System.out.println("同比:单维度多项目策略");
		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();

		HttpServletRequest request = interfaceExportImpl.getRequest();

		String[] rowKeys = getRowKeys(conditionEntity);// 图例
														// [2014Nb,2013NB，2014OS,2013OS]

		String[] columnKeys = ReportTool.getComparedColumnKeys(conditionEntity);// 横座标
																				// [1，2，3，4，5]

		String xname = "维度（" + ReportTool.getXname(conditionEntity) + "）:各项目同比";

		double[][] data = null;// 数据集

		try
		{
			data = getData(conditionEntity, rowKeys, columnKeys);
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
	 * 求同比图例（时间+项目）[2014Nb,2013NB，2014OS,2013OS]
	 * 
	 * @return
	 */
	public String[] getRowKeys(ConditionEntity conditionEntity)
	{
		String[] arrayYear = DateUtil.getYearTlist(conditionEntity);
		String[] arrayItem = conditionEntity.getItem().split(",");
		String[] array = ReportTool.getArrayAdd(arrayItem, arrayYear);
		return array;
	}

	/**
	 * 功能:四个级别的数据组装
	 * <p>
	 * 作者 杨荣忠 2015-1-26 下午08:09:42
	 * 
	 * @param conditionEntity
	 * @param rowKeys
	 * @param columnKeys
	 * @return
	 * @throws Exception
	 */
	public double[][] getData(ConditionEntity conditionEntity,
			String[] rowKeys, String[] columnKeys) throws Exception
	{
		double[][] data = null;

		// 判断维度是在哪个级别
		if (ImgTool.isSelectDimension(conditionEntity))
		{
			data = getDataOnetoTwo(conditionEntity, rowKeys, columnKeys);
		} else
		{
			data = getDataThridtoFour(conditionEntity, rowKeys, columnKeys);
		}
		return data;
	}

	/**
	 * 求同比数据集，匹配年月，维度(一二级)
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataOnetoTwo(ConditionEntity conditionEntity,
			String[] rowKeys, String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_ONE_OR_TWO;

		double[][] data = new double[rowKeys.length][columnKeys.length];

		// String dimension=ReportTool.getXname(conditionEntity);

		List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool
				.getComparedCellMonthSummaryInfoResult(conditionEntity, type);

		// 判断什么时候由一级分类到二级分类切换
		// int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type) *
		// (1 + conditionEntity.getCircle());

		for (int j = 0; j < rowKeys.length; j++)// 图例
												// [2014Nb,2013NB，2014OS,2013OS]
		{
			for (int i = 0; i < columnKeys.length; i++)// x轴(地区) 横座标 [1，2，3，4，5]
			{
				List<CellMonthSummaryInfo> result = new ArrayList();
				for (int k = 0; k < cellMonthSummaryInfoList.size(); k++)
				{
					// 值记录
					CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
							.get(k);
					String brandName = cellMonthSummaryInfo.getBrandName();// 求品牌名字
					String year = cellMonthSummaryInfo.getYearTime();
					String month = cellMonthSummaryInfo.getMonthTime();

					if (year.equals(rowKeys[j].substring(0, 4))
							&& month.equals(columnKeys[i]))// 判断是不是同比的是这个年这个月的
					{
						if (brandName.equals(rowKeys[j].substring(4)))// 判断是不是这个品牌的
						{
							/*
							 * String classTypeName =
							 * ReportTool.getchangOneOrTwoValueName
							 * (changOneOrTwo, j, cellMonthSummaryInfo); if
							 * (dimension.equals(classTypeName))// 判断是不是这个维度的 {
							 */
							result.add(cellMonthSummaryInfo);
							// }

						}
					}
				}
				data[j][i] = ParamTool.getSumOneToTwo(result);
			}
		}
		return data;
	}

	/**
	 * 求同比数据集，匹配年月，维度(三四级)
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataThridtoFour(ConditionEntity conditionEntity,
			String[] rowKeys, String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_THIRD_OR_FOUR;

		double[][] data = new double[rowKeys.length][columnKeys.length];

		// 判断什么时候由三级分类到四级分类切换
		// int changOneOrTwo = ReportTool.getChangIndex(conditionEntity,type) *
		// (1 + conditionEntity.getCircle());

		// String dimension=ReportTool.getXname(conditionEntity);

		List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
				.getComparedMonthSummaryInfoResult(conditionEntity, type);
		for (int j = 0; j < rowKeys.length; j++)// 图例
												// [2014Nb,2013NB，2014OS,2013OS]
		{
			for (int i = 0; i < columnKeys.length; i++)// x轴(地区) 横座标 [1，2，3，4，5]
			{
				List<RowMonthSummaryInfo> result = new ArrayList();
				for (int k = 0; k < rowMonthSummaryInfoList.size(); k++)
				{
					// 值记录
					RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
							.get(k);
					String brandName = rowMonthSummaryInfo.getBrandName();// 求品牌名字
					String year = rowMonthSummaryInfo.getYearTime();
					String month = rowMonthSummaryInfo.getMonthTime();
					if (year.equals(rowKeys[j].substring(0, 4))
							&& month.equals(columnKeys[i]))// 判断是不是同比的是这个年这个月的
					{
						if (brandName.equals(rowKeys[j].substring(4)))// 判断是不是这个品牌的
						{
							/*
							 * String classTypeName =
							 * ReportTool.getchangThridOrFourValue
							 * (changOneOrTwo, j, rowMonthSummaryInfo); if
							 * (dimension.equals(classTypeName))// 判断是不是这个维度的 {
							 */
							result.add(rowMonthSummaryInfo);
							// }

						}
					}
				}
				data[j][i] = ParamTool.getSumThirdToFour(result);
			}
		}
		return data;
	}
}
