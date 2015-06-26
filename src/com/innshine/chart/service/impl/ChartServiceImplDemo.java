package com.innshine.chart.service.impl;

import org.jfree.data.general.Dataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.DataParams;
import com.innshine.chart.entity.ImgPathLine;
import com.innshine.chart.entity.ImgPathPine;
import com.innshine.chart.service.AbsChartService;


/**
 * JfreeChart画图通用功能类，实现通用的柱状图、折线 、饼状图生成 实现类 <code>ChartServiceImpl.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class ChartServiceImplDemo extends AbsChartService
{
	public static void main(String[] args)
	{
		//makeLineAndShapeChart();
		
		 makePieChart();
	}
	
	public static void makeLineAndShapeChart()
	{
		
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
		double[][] data = new double[][] { { 672, 766, 223, 540, 126 }, { 325, 521, 210, 340, 106 },
				{ 332, 256, 523, 240, 526 } };
		String[] rowKeys = { "苹果", "梨子", "葡萄" };
		String[] columnKeys = { "北京", "上海", "广州", "成都", "深圳" };
		
		// DataParams p = new DataParams(data, rowKeys, columnKeys,
		// ChartConstants.BAR_CHART);
		DataParams p = new DataParams(data, rowKeys, columnKeys,5);
		try
		{
			Dataset dataset = chartServiceImpl.consDataParamList(p);
			String path = chartServiceImpl.createChart(dataset, "测试图", "xx", "yy", p.getChartType(), "test11");
			
			System.err.println(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 生成饼状图
	 */
	public static void makePieChart()
	{
		String path = "";
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
		double[] data = { 9, 91, 20 };
		String[] keys = { "失败率", "成功率", "asdf" };
		DataParams p = new DataParams(data, keys, ChartConstants.PIE_CHART);
		try
		{
			path = chartServiceImpl
					.createPieChart(chartServiceImpl.consDataParamList(p), "饼状图", keys, p.getChartType());
			System.out.println("=========="+path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成饼状图
	 */
	public static String getImgPathPie(double[] data, String[] keys, String name)
	{
		String path = "";
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
		// double[] data = { 30, 91,200};值
		// String[] keys = { "失败率", "成功率" ,"asdf"};图例
		DataParams p = new DataParams(data, keys, ChartConstants.PIE_CHART);
		try
		{
			path = chartServiceImpl.createPieChart(chartServiceImpl.consDataParamList(p), name, keys, p.getChartType());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}
	
	
	/**
	 * 生成饼状图
	 */
	public static String getImgPathPie(ImgPathPine imgPathPine)
	{   
		double[] data=imgPathPine.getData();
		String[] keys=imgPathPine.getKeys(); 
		String name=imgPathPine.getName();
		String path = "";
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
		
		DataParams p = new DataParams(data, keys, ChartConstants.PIE_CHART);
		try
		{
			path = chartServiceImpl.createPieChart(chartServiceImpl.consDataParamList(p), name, keys, p.getChartType());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("生成饼状图的路径是："+path);
		return path;
	}
	
	public static String getImgPathLine(ImgPathLine imgPathLine)
	{
		String path = "";
		// int a[2][3]={{1,2,3},{4，5，6}};
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
	//	getTimeMillis
		/*
		 * //图例个数+平台个数 double[][] data = new double[][] { { 672, 766, 223, 540,
		 * 126 }, { 325, 521, 210, 340, 106 }, { 332, 256, 523, 240, 526 } };
		 * String[] rowKeys = { "苹果", "梨子", "葡萄" };//图例（平台） String[] columnKeys
		 * = { "北京", "上海", "广州", "成都", "深圳" };//横轴
		 */
		 double[][] data=imgPathLine.getData();
		 String[] rowKeys=imgPathLine.getRowKeys();
		 String[] columnKeys=imgPathLine.getColumnKeys();
		 String xname=imgPathLine.getXname();
		 String yname=imgPathLine.getYname();
		 String reportName=imgPathLine.getReportName();
		 int imgtype=imgPathLine.getImgtype();
		
		DataParams p = new DataParams(data, rowKeys, columnKeys, imgtype);
		
		try
		{
			Dataset dataset = chartServiceImpl.consDataParamList(p);
			path = chartServiceImpl.createChart(dataset, reportName, xname, yname, p.getChartType(), "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("生成折线图或者柱状图的路径是："+path);
		return path;
	}
	
	public static String getImgPathLine(double[][] data, String[] rowKeys, String[] columnKeys, String xname,
			String yname, String reportName, int imgtype)
	{
		String path = "";
		// int a[2][3]={{1,2,3},{4，5，6}};
		ChartServiceImplDemo chartServiceImpl = new ChartServiceImplDemo();
	//	getTimeMillis
		/*
		 * //图例个数+平台个数 double[][] data = new double[][] { { 672, 766, 223, 540,
		 * 126 }, { 325, 521, 210, 340, 106 }, { 332, 256, 523, 240, 526 } };
		 * String[] rowKeys = { "苹果", "梨子", "葡萄" };//图例（平台）
		 * String[] columnKeys = { "北京", "上海", "广州", "成都", "深圳" };//横轴
		 */
		DataParams p = new DataParams(data, rowKeys, columnKeys, imgtype);
		
		try
		{
			Dataset dataset = chartServiceImpl.consDataParamList(p);
			path = chartServiceImpl.createChart(dataset, reportName, xname, yname, p.getChartType(), reportName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return path;
	}
	
}
