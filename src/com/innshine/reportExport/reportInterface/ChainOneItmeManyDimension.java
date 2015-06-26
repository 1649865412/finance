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
 * <code>ChainOneItmeManyDimension.java  环比:单项目多维度</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:36:05
 * @version 1.0 </br>最后修改人 无
 */
public class ChainOneItmeManyDimension extends Abstrategy
{
	public static String NOW_Month = "本月";
	public static String LAST_MONTH = "上月";
	public static String[] CHAIN_ARRAY = { "本月", "上月" };
	public static int Chain_KEY = 0;

	public static String yname = "费用（元）";

	@Override
	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{
		System.out.println("环比:单项目多维度策略");

		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();

		HttpServletRequest request = interfaceExportImpl.getRequest();

		String xname = "项目（" + conditionEntity.getItem() + "）:各维度环比";

		String[] rowKeys = ReportTool.getRowKeys(conditionEntity, Chain_KEY);// 图例
																				// [本月A，上月A，本月B,上月B]
		String[] columnKeys = null;// 横座标 x轴(月份) [2,3,4]

		double[][] data = null;// 数据集

		try
		{
			columnKeys = ReportTool.getComparedColumnKeys(conditionEntity);
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
	 * <p>作者 杨荣忠 2015-1-27 下午02:35:57
	 * @param conditionEntity
	 * @param columnKeys
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
	 * 求环比数据集，匹配年月，维度(一级二级) 备注：要增加品牌配对
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataOnetoTwo(ConditionEntity conditionEntity,
			String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_ONE_OR_TWO;
		String[] rowKeys = ReportTool.getRowKeys(conditionEntity, type);// 图例

		if (rowKeys != null)
		{
			String itmeName = conditionEntity.getItem();
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
					* CHAIN_ARRAY.length;
			double[][] data = new double[rowKeys.length][columnKeys.length];
			List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool
					.getChainCellMonthSummaryInfoResult(conditionEntity, type);

			for (int j = 0; j < rowKeys.length; j++)
			{// 图例 [本月A，上月A，本月B,上月B]
				for (int i = 0; i < columnKeys.length; i++)
				{// x轴(月份) [2,3,4]
					List<CellMonthSummaryInfo> result = new ArrayList();
					for (int k = 0; k < cellMonthSummaryInfoList.size(); k++)
					{// 值记录

						CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
								.get(k);

						String month = cellMonthSummaryInfo.getMonthTime();

						String classTypeName = ReportTool
								.getchangOneOrTwoValueName(changOneOrTwo, j,
										cellMonthSummaryInfo);
						String brandName = cellMonthSummaryInfo.getBrandName();
						String rowKeysValue = rowKeys[j].substring(0, 2);
						String classTypeValue = rowKeys[j].substring(2);
						String lastOrNowMonth = "";

						// 判断是不是要求的是上个月还是本月的环比数据（月份仅限今年）
						if (rowKeysValue.equals(NOW_Month))
						{
							lastOrNowMonth = columnKeys[i];
						} else
						{
							String monthValue = columnKeys[i];
							String year = cellMonthSummaryInfo.getYearTime();
							lastOrNowMonth = DateUtil.getLastMonth(monthValue,
									year);
						}
						// 判断是不是这个月的
						if (month.equals(lastOrNowMonth))
						{
							// 判断是不是这个维度的
							if (classTypeName.equals(classTypeValue))
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
	 * 求环比数据集，匹配年月，维度（三级，四级）
	 * 
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataThridtoFour(ConditionEntity conditionEntity,
			String[] columnKeys) throws Exception
	{
		int type = Constant.PARAM_THIRD_OR_FOUR;
		String[] rowKeys = ReportTool.getRowKeys(conditionEntity, type);// 图例

		if (rowKeys != null)
		{
			double[][] data = new double[rowKeys.length][columnKeys.length];
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type)
					* CHAIN_ARRAY.length;
			List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
					.getChainMonthSummaryInfoResult(conditionEntity, type);

			for (int j = 0; j < rowKeys.length; j++)
			{// 图例 [本月A，上月A，本月B,上月B]
				for (int i = 0; i < columnKeys.length; i++)
				{// x轴(月份) [2,3,4]
					List<RowMonthSummaryInfo> result = new ArrayList();
					for (int k = 0; k < rowMonthSummaryInfoList.size(); k++)
					{// 值记录

						RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
								.get(k);
						String month = rowMonthSummaryInfo.getMonthTime();

						String classTypeName = ReportTool
								.getchangThridOrFourValueName(changOneOrTwo, j,
										rowMonthSummaryInfo);

						String rowKeysValue = rowKeys[j].substring(0, 2);
						String classTypeValue = rowKeys[j].substring(2);

						String lastOrNowMonth = "";
						// 判断是不是要求的是上个月还是本月的环比数据（月份仅限今年）
						if (rowKeysValue.equals(NOW_Month))
						{
							lastOrNowMonth = columnKeys[i];
						} else
						{
							String monthValue = columnKeys[i];
							String year = rowMonthSummaryInfo.getYearTime();
							lastOrNowMonth = DateUtil.getLastMonth(monthValue,
									year);
						}
						if (month.equals(lastOrNowMonth))
						{// 判断是不是这个月的
							if (classTypeName.equals(classTypeValue))
							{// 判断是不是这个维度的
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
