package com.innshine.chart;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * 处理构造图表填充数据参数类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class DataParams
{
	/**
	 * 二维数据列表，折线图、柱状图使用
	 */
	private double[][] barData;

	/**
	 * 数据列表
	 */
	private double[] pieData;

	/**
	 * X轴对应显示数值数组
	 */
	private String[] rowKeys;

	/**
	 * X轴对应显示数值数组
	 */
	private String[] columnKeys;

	/**
	 * 饼状图对象显示说明
	 */
	private String[] dataDescription;

	/**
	 * 创建图表类型
	 */
	private int chartType;

	public DataParams()
	{
		super();
	}

	/**
	 * 折线图、柱状图使用参数构造
	 * 
	 * @param barData
	 *            double[][] 填充数据值二维数组
	 * @param rowKeys
	 *            X轴展示数值(如时间、速度等)
	 * @param columnKeys
	 *            Y轴展示数值(如时间、速度等)
	 * @param chartType
	 *            图表类型(如：折线2D、3D ，柱状图2D、3D)，会根据该类型组装填充对象DataSet
	 */
	public DataParams(double[][] barData, String[] rowKeys, String[] columnKeys, int chartType)
	{
		super();
		this.barData = barData;
		this.rowKeys = rowKeys;
		this.columnKeys = columnKeys;
		this.chartType = chartType;
	}

	/**
	 * 饼状图 数据集构造
	 * 
	 * @param double[][] data 填充数据值数组
	 * @param datadescription
	 *            [] 说明
	 * @param chartType
	 *            图表类型(如：折线2D、3D ，柱状图2D、3D)，会根据该类型组装填充对象DataSet
	 */
	public DataParams(double[] pieData, String[] dataDescription, int chartType)
	{
		super();
		this.pieData = pieData;
		this.dataDescription = dataDescription;
		this.chartType = chartType;
	}

	/**
	 * 柱状图,折线图 数据集
	 * 
	 * @param double[][] data 填充数据值二维数组
	 * @param rowKeys
	 *            X轴展示数值(如时间、速度等)
	 * @param columnKeys
	 *            Y轴展示数值(如时间、速度等)
	 * @return CategoryDataset 图表数据填充对象
	 */
	public CategoryDataset getDataSetBarData()
	{
		if (ArrayUtils.isNotEmpty(this.columnKeys) && ArrayUtils.isNotEmpty(this.barData)
				&& ArrayUtils.isNotEmpty(rowKeys))
		{
			return DatasetUtilities.createCategoryDataset(this.rowKeys, this.columnKeys, this.barData);
		}

		return null;

	}

	/**
	 * 饼状图 数据集
	 * <p>
	 * 
	 * @param double[][] data 填充数据值二维数组
	 * @param dataDescription
	 *            [] 说明
	 * @return CategoryDataset 图表数据填充对象
	 */
	public PieDataset getDataPieSetByUtil()
	{

		if (this.pieData != null && this.dataDescription != null)
		{
			if (this.pieData.length == this.dataDescription.length)
			{
				DefaultPieDataset dataset = new DefaultPieDataset();
				for (int i = 0; i < this.pieData.length; i++)
				{
					dataset.setValue(this.dataDescription[i], this.pieData[i]);
				}

				return dataset;
			}

		}

		return null;
	}

	public String[] getRowKeys()
	{
		return rowKeys;
	}

	public void setRowKeys(String[] rowKeys)
	{
		this.rowKeys = rowKeys;
	}

	public String[] getColumnKeys()
	{
		return columnKeys;
	}

	public void setColumnKeys(String[] columnKeys)
	{
		this.columnKeys = columnKeys;
	}

	public String[] getDataDescription()
	{
		return dataDescription;
	}

	public void setDataDescription(String[] dataDescription)
	{
		this.dataDescription = dataDescription;
	}

	public int getChartType()
	{
		return chartType;
	}

	public void setChartType(int chartType)
	{
		this.chartType = chartType;
	}

	public double[][] getBarData()
	{
		return barData;
	}

	public void setBarData(double[][] barData)
	{
		this.barData = barData;
	}

	public double[] getPieData()
	{
		return pieData;
	}

	public void setPieData(double[] pieData)
	{
		this.pieData = pieData;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

}
