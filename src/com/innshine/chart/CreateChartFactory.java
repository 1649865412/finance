package com.innshine.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

/**
 * 图片创建工厂 <code>CreateCharFactory.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @author admin 时间 2014-5-23 上午10:20:22
 * @version 1.0 </br>最后修改人 无
 */
public class CreateChartFactory
{
	
	private CreateChartFactory()
	{
		
	}
	
	/**
	 * 创建柱状图 折线图
	 * <p>
	 * 
	 * @param chartTitle
	 *            图表标题
	 * @param xName
	 *            x轴显示信息
	 * @param yName
	 *            y轴显示信息
	 * @param charType
	 *            创建类型，支持3D、默认折线图 ，创建饼状图类型与ChartConstants常量对应
	 * @param xyDataset
	 *            填充数据集
	 * @return JFreeChart
	 * */
	public static JFreeChart createChartFactory(Dataset dataset, int charType, String xName, String yName,
			String chartTitle, boolean legend, boolean tooltips, boolean urls)
	{
		switch (charType)
		{
			case ChartConstants.BAR_CHART:
			case ChartConstants.BAR_3D_CHART:
				return CreateChartUtils.createBarChart(dataset, xName, yName, charType, chartTitle, legend, tooltips,
						urls);
			case ChartConstants.LINE_CHART:
			case ChartConstants.LINE_3D_CHART:

				return CreateChartUtils.createLineChar(chartTitle, xName, yName, charType, dataset, legend, tooltips,
						urls);
			default:
				return CreateChartUtils.createBarChart(dataset, xName, yName, charType, chartTitle, legend, tooltips,
						urls);
				
		}	
	}
	
	public static Dataset createDataSetFactory(DataParams parameter)
	{
		if (null != parameter)
		{
			switch (parameter.getChartType())
			{
				case ChartConstants.BAR_CHART:
				case ChartConstants.BAR_3D_CHART:
					return parameter.getDataSetBarData();
				case ChartConstants.LINE_CHART:
				case ChartConstants.LINE_3D_CHART:
					return parameter.getDataSetBarData();
				case ChartConstants.PIE_CHART:
				case ChartConstants.PIE_3D_CHART:
					return parameter.getDataPieSetByUtil();
				default:
					return parameter.getDataSetBarData();
			}
		}
		
		return null;
		
	}
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * 饼状图
	 * 
	 * @param dataset
	 *            数据集
	 * @param chartTitle
	 *            图标题
	 * 
	 * @param charType
	 *            创建饼状图类型与ChartConstants常量对应
	 * @param pieKeys
	 *            分饼的名字集
	 * @return
	 */
	
	public static JFreeChart createChartFactory(Dataset dataset, int charType, String[] pieKeys, String chartTitle,
			boolean legend, boolean tooltips, boolean urls)
	{
		switch (charType)
		{
			
			default:
				return CreateChartUtils.createPieChar(dataset, chartTitle, pieKeys, charType, legend, tooltips, urls);
				
		}
		
	}
}
