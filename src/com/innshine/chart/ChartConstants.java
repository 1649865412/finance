package com.innshine.chart;

/**
 * 使用JfreeChart中需要使用的常量参数类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @author 时间 2014-5-22 下午03:15:40
 * @version 1.0 </br>最后修改人 无
 */
public interface ChartConstants
{
    
	/** =====================================定义图表对象=========================== */

	/**
	 * 
	 * createRingChart
	 * 
	 * 
	 * 
	 * createAreaChart createStackedAreaChart
	 * 
	 * createLineChart
	 * 
	 * createGanttChart createWaterfallChart createPolarChart createScatterPlot
	 * 
	 * createXYBarChart createXYAreaChart createStackedXYAreaChart
	 * createXYLineChart createXYStepChart createXYStepAreaChart
	 * 
	 * createTimeSeriesChart createCandlestickChart createHighLowChart
	 * createBubbleChart createHistogram createBoxAndWhiskerChart createWindPlot
	 * createWaferMapChart
	 */
	/**
	 * createPieChart饼图
	 */
	static final int PIE_CHART = 0;

	/**
	 * createMultiplePieChart 多重饼状图
	 */

	static final int MULTIPLE_PIE_CHART = 1;

	/**
	 * createPieChart3D 3D饼图
	 */
	static final int PIE_3D_CHART = 2;

	/**
	 * createMultiplePieChart3D 多重饼状 3D图
	 */
	static final int MULTIPLE_PIE_3D_CHART = 3;

	/**
	 * createBarChart 柱状图
	 */
	static final int BAR_CHART = 4;

	/**
	 * createBarChart3D 3D柱状图
	 */

	static final int BAR_3D_CHART = 5;

	/**
	 * createStackedBarChart 多重柱状图
	 */

	static final int STACKED_BAR_CHART = 6;

	/**
	 * createStackedBarChart3D 多重柱状图
	 */

	static final int STACKED_BAR_3D_CHART = 7;

	/**
	 * createLineChart 折线图
	 */

	static final int LINE_CHART = 8;

	/**
	 * createLineChart3D 3D 折线图
	 */

	static final int LINE_3D_CHART = 9;

	/** ======================================================================== */

	/**
	 * 图片生成宽度 600PX
	 */
	static final int WIDTH = 680;

	/**
	 * 图片生成高度 400PX
	 */
	static final int HEIGTH = 320;

	/**
	 * 图片默认生成路径
	 */
	static final String CHART_PATH = System.getProperty("user.dir") + "/report_images_temp/";
	
	

	/**
	 * png图片格式 ,jxl Excel插入图片 ，目前只支持png格式
	 */
	static final String PNG = ".png";

}
