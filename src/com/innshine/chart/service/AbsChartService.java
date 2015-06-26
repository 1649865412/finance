package com.innshine.chart.service;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;

import com.innshine.chart.ChartConstants;
import com.innshine.chart.CreateChartFactory;
import com.innshine.chart.CreateChartUtils;
import com.innshine.chart.DataParams;
import com.innshine.chart.service.util.DateUtil;


/**
 * JfreeChart画图通用功能抽象类，实现通用的柱状图、折线 、饼状图生成
 * 
 * 
 * <code>AbsChartService.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class AbsChartService implements ChartService
{
	
	@Override
	public Dataset consDataParamList(DataParams parameter) throws Exception
	{
		return CreateChartFactory.createDataSetFactory(parameter);
	}
	
	@Override
	public String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, int width,
			int hieght, String fileName)
	{
		setFontInfo();
		JFreeChart freeChart = CreateChartFactory.createChartFactory(categoryDataset, chartType, categoryAxisLabel,
				valueAxisLabel, title, legend, tooltips, urls);
		
		return createFilePath(fileName, freeChart, width, hieght);
	}
	
	// 关键方法
	@Override
	public String createFilePath(String fileName, JFreeChart freeChart, int width, int height)
	{
		String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
		String tempPath = url.substring(0, url.indexOf("WEB-INF")) + "excelImg/";
		// String tempPath = ChartConstants.CHART_PATH + (new Date().getTime())
		// + ChartConstants.PNG;
		// System.out.println("========="+tempPath);
		
		if (createDir(tempPath))
		{
			if (StringUtils.isEmpty(fileName))
			{
			//	fileName = String.valueOf(new Date().getTime());
				fileName = "";
			}
			
		    tempPath = tempPath +DateUtil.getTimeMillis()+fileName + ChartConstants.PNG;
		//	tempPath = tempPath +DateUtil.getTimeMillis()+fileName + ChartConstants.PNG;
		}
		
		return width > 0 && height > 0 ? getCreateChartPath(freeChart, tempPath, width, height) : getCreateChartPath(
				freeChart, tempPath);
	}
	
	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，或目录已创建返回true ，否则返回false
	 */
	public boolean createDir(String destDirName)
	{
		File dir = new File(destDirName);
		if (dir.exists())
		{
			// System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
			return true;
		}
		// endsWith(String suffix)测试此字符串是否以指定的后缀结束
		// public static final String separator 与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
		// 此字符串只包含一个字符，即 separatorChar。即为"\"后缀结尾。
		if (!destDirName.endsWith(File.separator))
		{
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs())
		{
			// System.out.println("创建父目录和子目录" + destDirName + "成功！");
			return true;
		}
		else
		{
			// System.out.println("创建指定父目录中的子目录" + destDirName + "失败！");
			return false;
		}
	}
	
	@Override
	public String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType)
	{
		
		return createChart(categoryDataset, title, categoryAxisLabel, valueAxisLabel, chartType,
				PlotOrientation.VERTICAL, true, false, false, ChartConstants.WIDTH, ChartConstants.HEIGTH, null);
	}
	
	@Override
	public synchronized String getCreateChartPath(JFreeChart jFreeChart, String saveFileName, int width, int height)
	{
		File file = null;
		try
		{
			CreateChartUtils.isChartPathExist(ChartConstants.CHART_PATH);
			file = new File(saveFileName);
			
			setFontInfo();
			ChartUtilities.saveChartAsPNG(file, jFreeChart, width, height);
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String str = null != file ? file.getPath() : null;
		// file.delete();
		return str;
	}
	
	@Override
	public String getCreateChartPath(JFreeChart jFreeChart, String saveFileName)
	{
		
		return getCreateChartPath(jFreeChart, saveFileName, ChartConstants.WIDTH, ChartConstants.HEIGTH);
	}
	
	@Override
	public void setCategoryPlot(JFreeChart chart)
	{
		
	}
	
	@Override
	public void setFontInfo()
	{
		// 解决中文乱码问题
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 14));
		mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 14));
		mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 14));
		ChartFactory.setChartTheme(mChartTheme);
	}
	
	@Override
	public String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType)
	{
		return createPieChart(dataSet, title, pieKeys, chartType, true, false, false);
	}
	
	@Override
	public String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType,
			PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, int width, int hieght,
			String fileName)
	{
		
		setFontInfo();
		JFreeChart freeChart = CreateChartFactory.createChartFactory(dataSet, chartType, pieKeys, title, legend,
				tooltips, urls);
		CreateChartUtils.isChartPathExist(ChartConstants.CHART_PATH);
		
		return createFilePath(fileName, freeChart, width, hieght);
	}
	
	@Override
	public String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, boolean legend,
			boolean tooltips, boolean urls)
	{
		
		return createPieChart(dataSet, title, pieKeys, chartType, PlotOrientation.VERTICAL, true, false, false,
				ChartConstants.WIDTH, ChartConstants.HEIGTH, null);
	}
	
	@Override
	public String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, String fileName)
	{
		return createChart(categoryDataset, title, categoryAxisLabel, valueAxisLabel, chartType, orientation, legend,
				tooltips, urls, ChartConstants.WIDTH, ChartConstants.HEIGTH, fileName);
		
	}
	
	@Override
	public String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType,
			PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, String fileName)
	{
		return createPieChart(dataSet, title, pieKeys, chartType, orientation, legend, tooltips, urls,
				ChartConstants.WIDTH, ChartConstants.HEIGTH, fileName);
	}
	
	@Override
	public String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, String imageName)
	{
		return createChart(categoryDataset, title, categoryAxisLabel, valueAxisLabel, chartType,
				PlotOrientation.VERTICAL, true, false, false, ChartConstants.WIDTH, ChartConstants.HEIGTH, imageName);
		
	}
	
	@Override
	public String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, String imageName)
	{
		return createPieChart(dataSet, title, pieKeys, chartType, PlotOrientation.VERTICAL, true, false, false,
				ChartConstants.WIDTH, ChartConstants.HEIGTH, imageName);
	}
	
}
