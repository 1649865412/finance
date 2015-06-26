package com.innshine.reportExport.reportService;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.data.general.Dataset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.DataParams;
import com.innshine.chart.entity.ImgPathLine;
import com.innshine.chart.entity.ImgPathPine;
import com.innshine.chart.service.AbsChartService;


/**
 * JfreeChart画图通用功能类，实现通用的柱状图、折线 、饼状图生成 业务类 <code>ChartServiceImpl.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ChartServiceImpl extends AbsChartService
{
	
	/**
	 * 生成饼状图
	 */
	public static String getImgPathPie(ImgPathPine imgPathPine)
	{   
		double[] data=imgPathPine.getData();
		String[] keys=imgPathPine.getKeys(); 
		String name=imgPathPine.getName();
		String path = "";
		ChartServiceImpl chartServiceImpl = new ChartServiceImpl();
		
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
	
	/**
	 * 生成柱状图,折线图
	 */
	public static String getImgPathLine(ImgPathLine imgPathLine)
	{
		String path = "";
		ChartServiceImpl chartServiceImpl = new ChartServiceImpl();
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
	
	/**
	 * 把图片拼接起来，以img标签的形式
	 * 
	 * @param imagetPaths
	 *        生成的图片完整路径名
	 * @param request
	 *        请求对象
	 * @return 拼接好的img标签，
	 */
	public static String getImageHtml(ImgPathLine imgPathLine, HttpServletRequest request)
	{      
		     String imgPath = getImgPathLine(imgPathLine);		
			// 获取web完整路径
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/excelImg/";
			
			StringBuffer buffer = new StringBuffer();
				try
					{
						File srcFile = new File(imgPath);
						String tmpPath = basePath + srcFile.getName();
					//	String image = "<img src='" + tmpPath + "' width='50%' height='400px' />";
						String image =  tmpPath ;
						buffer.append(image);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}	
			 return buffer.toString();
	}
	
	/**
	 * 把图片拼接起来，以img标签的形式
	 * 
	 * @param imagetPaths
	 *        生成的图片完整路径名
	 * @param request
	 *        请求对象
	 * @return 拼接好的img标签，
	 */
	public static String getImageHtml(String imagetPaths, HttpServletRequest request)
	{   
		    
			// 获取web完整路径
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/excelImg/";
			
			StringBuffer buffer = new StringBuffer();
				try
					{
						File srcFile = new File(imagetPaths);
						String tmpPath = basePath + srcFile.getName();
					//	String image = "<img src='" + tmpPath + "' width='50%' height='400px' />";
						String image =  tmpPath ;
						buffer.append(image);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}	
			 return buffer.toString();
	}
	
}
