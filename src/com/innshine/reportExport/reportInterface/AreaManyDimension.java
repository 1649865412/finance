package com.innshine.reportExport.reportInterface;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.chart.entity.ImgPathLine;
import com.innshine.reportExport.Constants;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;
import com.innshine.reportExport.reportService.ChartServiceImpl;
import com.innshine.reportExport.util.Constant;
import com.innshine.reportExport.util.ImgTool;
import com.innshine.reportExport.util.ParamTool;
import com.innshine.reportExport.util.ReportTool;

/**
 * <code>AreaManyDimension.java 地区比:多维度比</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 下午02:10:03
 * @version 1.0 </br>最后修改人 无 多地区多维度： 1）x轴：多地区 y轴：多维分类 标题：时间+地区+维度+图表+多地区 图例：多维分类
 *          支持: 折线图，柱形图 特点：至少一个维度，求时间段的汇总
 */
@Component
public class AreaManyDimension extends Abstrategy
{

	public static String yname = "费用（元）";

	public String reportImg(InterfaceExportImpl interfaceExportImpl)
	{
		System.out.println("进入地区比方法");

		ConditionEntity conditionEntity = interfaceExportImpl
				.getConditionEntity();

		AreaInfoService areaInfoService = Constant.AREAINFOSERVICE;

		HttpServletRequest request = interfaceExportImpl.getRequest();

		List<AreaInfo> areaList = areaInfoService
				.getAreaList(Constants.AREA_LEVEL); // 求当前公司的地区

		String xname = "维度（" + ReportTool.getXname(conditionEntity) + "）:各地区比"; // x轴标题

		String[] rowKeys = ImgTool.getRowKeys(conditionEntity);// 图例 [维度A，维度B]

		String[] columnKeys = ReportTool.getColumnKeys(conditionEntity,
				areaList);// 横座标[广州，上海]

		double[][] data = null;

		try
		{
			data = getAreaData(conditionEntity, areaList);

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
	 * 功能:求地区数据(四个级别的数据组装)
	 * <p>
	 * 作者 杨荣忠 2015-1-27 上午10:48:47
	 * 
	 * @param conditionEntity
	 * @param areaList
	 * @return
	 * @throws Exception
	 */
	public double[][] getAreaData(ConditionEntity conditionEntity,
			List<AreaInfo> areaList) throws Exception
	{
		double[][] data = ParamTool.getData(getDataOnetoTwo(conditionEntity,
				areaList), getDataThridtoFour(conditionEntity, areaList));

		return data;
	}

	/**
	 * 功能:求一级二级的数据
	 * <p>
	 * 作者 杨荣忠 2015-1-26 下午03:00:17
	 * 
	 * @param value
	 * @param rowKeysId
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataOnetoTwo(ConditionEntity conditionEntity,
			List<AreaInfo> areaList) throws Exception
	{

		int type = Constant.PARAM_ONE_OR_TWO;

		String[] rowKeysId = ImgTool.getDimensionAllID(conditionEntity, type);// 图例
		if (null != rowKeysId)
		{
			// 判断什么时候一级分类切换到二级分类进行匹配;
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type);

			double[][] data = new double[rowKeysId.length][areaList.size()];

			List<CellMonthSummaryInfo> cellMonthSummaryInfoList = ReportTool
					.getCellMonthSummaryInfoResult(conditionEntity, type);

			for (int j = 0; j < rowKeysId.length; j++)
			{// 图例
				for (int i = 0; i < areaList.size(); i++)
				{// x轴(地区)
					AreaInfo areaInfo = areaList.get(i);
					List<CellMonthSummaryInfo> result = new ArrayList();

					for (int k = 0; k < cellMonthSummaryInfoList.size(); k++)
					{// 值记录
						CellMonthSummaryInfo cellMonthSummaryInfo = cellMonthSummaryInfoList
								.get(k);
						String branID = cellMonthSummaryInfo.getBrandId()
								.toString();
						if (ParamTool.checkAreaBranId(ReportTool
								.getColumnKeys(areaInfo.getBrandsInfos()),
								branID))
						{// 判断是不是属于这个地区的

							String classTypeId = ReportTool
									.getchangOneOrTwoValue(changOneOrTwo, j,
											cellMonthSummaryInfo);

							if (classTypeId.equals(rowKeysId[j]))
							{// 判断是不是属于这个维度的
								result.add(cellMonthSummaryInfo);
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
	 * 功能:求三级四级的数据
	 * <p>
	 * 作者 杨荣忠 2015-1-26 下午03:00:33
	 * 
	 * @param conditionEntity
	 * @param areaList
	 * @param value
	 * @param rowKeysId
	 * @param changOneOrTwo
	 * @return
	 * @throws Exception
	 */
	public double[][] getDataThridtoFour(ConditionEntity conditionEntity,
			List<AreaInfo> areaList) throws Exception
	{
		int type = Constant.PARAM_THIRD_OR_FOUR;
		String[] rowKeysId = ImgTool.getDimensionAllID(conditionEntity, type);// 图例

		if (rowKeysId != null)
		{
			// 判断什么时候由三级分类切换到四级;
			int changOneOrTwo = ReportTool.getChangIndex(conditionEntity, type);

			double[][] data = new double[rowKeysId.length][areaList.size()];

			List<RowMonthSummaryInfo> rowMonthSummaryInfoList = ReportTool
					.getRowMonthSummaryInfoResult(conditionEntity, type);

			for (int j = 0; j < rowKeysId.length; j++)
			{// 图例
				for (int i = 0; i < areaList.size(); i++)
				{// x轴(地区)
					AreaInfo areaInfo = areaList.get(i);
					List<RowMonthSummaryInfo> result = new ArrayList();

					for (int k = 0; k < rowMonthSummaryInfoList.size(); k++)
					{// 值记录
						RowMonthSummaryInfo rowMonthSummaryInfo = rowMonthSummaryInfoList
								.get(k);
						String branID = rowMonthSummaryInfo.getBrandId()
								.toString();

						if (ParamTool.checkAreaBranId(ReportTool
								.getColumnKeys(areaInfo.getBrandsInfos()),
								branID))
						{// 判断是不是属于这个地区的

							String classTypeId = ReportTool
									.getchangThridOrFourValue(changOneOrTwo, j,
											rowMonthSummaryInfo);

							if (classTypeId.equals(rowKeysId[j]))
							{// 判断是不是属于这个维度的
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
