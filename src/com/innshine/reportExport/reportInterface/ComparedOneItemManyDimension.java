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
 * <code>ComparedOneItemManyDimension.java  同比:单项目多维度</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:37:33
 * @version 1.0 </br>最后修改人 无
 */
public class ComparedOneItemManyDimension extends Abstrategy
{
	public static String xname = "各同比时间维度";
	public static String yname = "费用（元）";
	public static int Compared_KEY = 0;

	@Override
	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{
		System.out.println("同比:单项目多维度策略");

		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();

		HttpServletRequest request = interfaceExportImpl.getRequest();

		String[] rowKeys = ReportTool.getComparedRowKeys(conditionEntity,
				Compared_KEY);// 图例 [2014维度A，2013维度A,2014维度B，2013维度B]

		String[] columnKeys = ReportTool.getComparedColumnKeys(conditionEntity);// 横座标
																				// [1，2，3，4，5]

		String xname = "项目（" + conditionEntity.getItem() + "）:各维度同比";

		double[][] data = null;// 数据集

		try
		{
			data = getData(conditionEntity, columnKeys);

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
	 * <p>
	 * 作者 杨荣忠 2015-1-26 下午08:03:18
	 * 
	 * @param conditionEntity
	 * @param columnKeys
	 *            横座标 [1，2，3，4，5]
	 * @return
	 * @throws Exception
	 */
	public double[][] getData(ConditionEntity conditionEntity,
			String[] columnKeys) throws Exception
	{
		double[][] data = ParamTool.getData(getDataOnetoTwo(conditionEntity,
				columnKeys), getDataThridtoFour(conditionEntity, columnKeys));
		return data;
	}

	/**
	 * 求同比数据集，匹配年月，维度（一二级） 备注：要增加品牌配对
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataOnetoTwo(ConditionEntity conditionEntity,
			String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_ONE_OR_TWO;
		String[] rowKeys = ReportTool.getComparedRowKeys(conditionEntity, type);// 图例

		if (rowKeys != null)
		{
			// 判断什么时候由一级分类匹配到二级分类切换
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
					* (1 + conditionEntity.getCircle());
			String itmeName = conditionEntity.getItem();
			double[][] data = new double[rowKeys.length][columnKeys.length];

			List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool
					.getComparedCellMonthSummaryInfoResult(conditionEntity,
							type);

			for (int j = 0; j < rowKeys.length; j++)// 图例
													// [2014维度A，2013维度A,2014维度B，2013维度B]
			{
				for (int i = 0; i < columnKeys.length; i++) // x轴(地区)
															// [1，2，3，4，5]
				{
					List<CellMonthSummaryInfo> result = new ArrayList();
					for (int k = 0; k < cellMonthSummaryInfoList.size(); k++)
					{
						// 值记录
						CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
								.get(k);
						String year = cellMonthSummaryInfo.getYearTime();
						String month = cellMonthSummaryInfo.getMonthTime();
						String brandName = cellMonthSummaryInfo.getBrandName();
						// 判断是不是同比的是这个年这个月的
						if (year.equals(rowKeys[j].substring(0, 4))
								&& month.equals(columnKeys[i]))
						{
							// 判断是不是从一级分类切换到二级分类进行匹配，即是不是这个分类
							String classTypeName = ReportTool
									.getchangOneOrTwoValueName(changOneOrTwo,
											j, cellMonthSummaryInfo);

							if (classTypeName.equals(rowKeys[j].substring(4)))
							{
								// 一二级需判断品牌名字是不是所选
								if (brandName.equals(itmeName))
								{
									result.add(cellMonthSummaryInfo);
								}

							}
						}
					}
					data[j][i] = ParamTool.getSumOneToTwo(result);
				}
			}
			return data;
		}
		return null;

	}

	/**
	 * 求同比数据集，匹配年月，维度(三四级)
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataThridtoFour(ConditionEntity conditionEntity,
			String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_THIRD_OR_FOUR;
		String[] rowKeys = ReportTool.getComparedRowKeys(conditionEntity, type);// 图例

		if (rowKeys != null)
		{
			// 判断什么时候由一级分类匹配到二级分类切换
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
					* (1 + conditionEntity.getCircle());
			double[][] data = new double[rowKeys.length][columnKeys.length];

			List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
					.getComparedMonthSummaryInfoResult(conditionEntity,
							Constant.PARAM_THIRD_OR_FOUR);

			for (int j = 0; j < rowKeys.length; j++)// 图例
													// [2014维度A，2013维度A,2014维度B，2013维度B]
			{
				for (int i = 0; i < columnKeys.length; i++) // x轴(地区)
															// [1，2，3，4，5]
				{
					List<RowMonthSummaryInfo> result = new ArrayList();
					for (int k = 0; k < rowMonthSummaryInfoList.size(); k++)
					{
						// 值记录
						RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
								.get(k);
						String year = rowMonthSummaryInfo.getYearTime();
						String month = rowMonthSummaryInfo.getMonthTime();

						// 判断是不是同比的是这个年这个月的
						if (year.equals(rowKeys[j].substring(0, 4))
								&& month.equals(columnKeys[i]))
						{
							// 从三级分类切换到四级分类进行匹配（ID）
							String classTypeName = ReportTool
									.getchangThridOrFourValueName(
											changOneOrTwo, j,
											rowMonthSummaryInfo);

							if (classTypeName.equals(rowKeys[j].substring(4)))
							{
								result.add(rowMonthSummaryInfo);
							}
						}
					}
					data[j][i] = ParamTool.getSumThirdToFour(result);
				}
			}
			return data;
		}
		return null;
	}
}
