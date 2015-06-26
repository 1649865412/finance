package com.innshine.chart.service;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;

import com.innshine.chart.DataParams;

/**
 * JfreeChart画图通用功能服务接口
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public interface ChartService
{
	/**
	 * 设置通用的字体信息
	 * <p>
	 */
	void setFontInfo();
	
	/**
	 * 功能: 构造填充数据列表，根据传入的参数列表
	 * <p>
	 * 
	 * @param parameter
	 *            需要填充的数据列表
	 * @return CategoryDataset填充好的数据jfreechart数据列表
	 */
	Dataset consDataParamList(DataParams parameter) throws Exception;
	
	/**
	 * 根据传入的参数，创建图表对象
	 * <p>
	 * 
	 * @param categoryDataset
	 *            填充的数据对象set
	 * @param title
	 *            图表标题
	 * @param categoryAxisLabel
	 *            x轴的说明（如种类，时间等）
	 * @param valueAxisLabel
	 *            y轴的说明（如速度，时间等）
	 * @param chartType
	 *            图表类型 ： 2D柱状图、3D柱状图、折线图等类型
	 * @param orientation
	 *            boolean类型 图表水平或垂直
	 * @param legend
	 *            boolean 是否显示图例
	 * @param tooltips
	 *            配置图表生成工具提示
	 * @param urls
	 *            boolean 配置图表生成URL
	 * @param width
	 *            生成图片宽度
	 * @param hieght
	 *            生成图片高度
	 * @param fileName
	 *            生成图片文件名称
	 * @return 完整的文件名称 如(F:\\test.png)
	 */
	String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, int width,
			int hieght, String fileName);
	
	/**
	 * 根据传入的参数，创建图表对象, 不传入则设定默认宽度与高度
	 * <p>
	 * 
	 * @param categoryDataset
	 *            填充的数据对象set
	 * @param title
	 *            图表标题
	 * @param categoryAxisLabel
	 *            x轴的说明（如种类，时间等）
	 * @param valueAxisLabel
	 *            y轴的说明（如速度，时间等）
	 * @param chartType
	 *            图表类型 ： 2D柱状图、3D柱状图、折线图等类型
	 * @param orientation
	 *            boolean类型 图表水平或垂直
	 * @param legend
	 *            boolean 是否显示图例
	 * @param tooltips
	 *            配置图表生成工具提示
	 * @param urls
	 *            boolean 配置图表生成URL
	 * @param fileName
	 *            生成图片文件名称
	 * @return 完整的文件名称 如(F:\\test.png)
	 * */
	String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, String fileName);
	
	/**
	 * 根据传入的参数，创建图表对象。默认显示图例，不生成URL ，默认垂直柱状图
	 * <p>
	 * 
	 * @param categoryDataset
	 *            填充的数据对象set
	 * @param title
	 *            图表标题
	 * @param categoryAxisLabel
	 *            x轴的说明（如种类，时间等）
	 * @param valueAxisLabel
	 *            y轴的说明（如速度，时间等）
	 * 
	 * @return String 生成图片完整路径
	 */
	String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType);
	
	/**
	 * 根据传入的参数，创建图表对象。默认显示图例，不生成URL ，默认垂直柱状图
	 * <p>
	 * 
	 * @param categoryDataset
	 *            填充的数据对象set
	 * @param title
	 *            图表标题
	 * @param categoryAxisLabel
	 *            x轴的说明（如种类，时间等）
	 * @param valueAxisLabel
	 *            y轴的说明（如速度，时间等）
	 * @param imageName
	 *            生成图片名称
	 * @return String 生成图片完整路径
	 */
	String createChart(Dataset categoryDataset, String title, String categoryAxisLabel, String valueAxisLabel,
			int chartType, String imageName);
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * @param dataSet
	 *            填充数据集
	 * @param title
	 *            图表标题
	 * @param pieKeys
	 *            说明
	 * @param chartType
	 *            图表类型，2D,3D饼状图等
	 * @return 完整路径
	 */
	String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType);
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * @param dataSet
	 *            填充数据集
	 * @param title
	 *            图表标题
	 * @param pieKeys
	 *            说明
	 * @param chartType
	 *            图表类型，2D,3D饼状图等
	 * @param imageName
	 *            生成图片名称
	 * @return 完整路径
	 */
	String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, String imageName);
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * @param dataSet
	 *            填充数据集
	 * @param title
	 *            图表标题
	 * @param pieKeys
	 *            说明
	 * @param chartType
	 *            图表类型，2D,3D饼状图等
	 * @param width
	 *            生成图片宽度
	 * @param hieght
	 *            生成图片高度
	 * @param fileName
	 *            生成图片文件名称
	 * @return 完整路径
	 */
	String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, PlotOrientation orientation,
			boolean legend, boolean tooltips, boolean urls, int width, int hieght, String fileName);
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * @param dataSet
	 *            填充数据集
	 * @param title
	 *            图表标题
	 * @param pieKeys
	 *            说明
	 * @param chartType
	 *            图表类型，2D,3D饼状图等
	 * 
	 * 
	 * @param fileName
	 *            生成图片文件名称
	 * @return 完整路径
	 */
	String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, PlotOrientation orientation,
			boolean legend, boolean tooltips, boolean urls, String fileName);
	
	/**
	 * 创建饼状图
	 * <p>
	 * 
	 * @param dataSet
	 *            填充数据集
	 * @param title
	 *            图表标题
	 * @param pieKeys
	 *            说明
	 * @param chartType
	 *            图表类型，2D,3D饼状图等
	 * @return 完整路径
	 */
	String createPieChart(Dataset dataSet, String title, String[] pieKeys, int chartType, boolean legend,
			boolean tooltips, boolean urls);
	
	/**
	 * 设置图表框的属性
	 * <p>
	 */
	void setCategoryPlot(JFreeChart freeChart);
	
	/**
	 * 根据传入的图表对象 chart、保存路径、宽、高等 生成图片文件，并保存至指定目录中， 默认为PNG格式图片
	 * <p>
	 * 
	 * @param jFreeChart
	 *            设置的图表对象柱状图、折线图等对象
	 * @param saveFileName
	 *            图片名称
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @return String 图片保存的绝对路径
	 */
	String getCreateChartPath(JFreeChart jFreeChart, String saveFileName, int width, int height);
	
	/**
	 * 根据传入的图表对象 chart、保存路径、宽、高等 生成图片文件，并保存至指定目录中， 默认为PNG格式图片。该方法使用默认的高与宽
	 * <p>
	 * 
	 * @param jFreeChart
	 *            设置的图表对象柱状图、折线图等对象
	 * @param saveFileName
	 *            图片名称
	 * @return String 图片保存的绝对路径
	 */
	String getCreateChartPath(JFreeChart jFreeChart, String saveFileName);
	
	/**
	 * 创建图表图片
	 * <p>
	 * 
	 * 
	 * @param fileName
	 *            文件名字
	 * @param freeChart
	 *            图表对象
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @return 图片生成的完整路径
	 */
	String createFilePath(String fileName, JFreeChart freeChart, int width, int height);
	
}
