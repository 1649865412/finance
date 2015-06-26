package com.innshine.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * jfreeChart图表对象创建类 <code>CreateCharUtils.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class CreateChartUtils
{
	/**
	 * 柱状图
	 * 
	 * @param dataset
	 *            数据集
	 * @param xName
	 *            x轴的说明（如种类，时间等）
	 * @param yName
	 *            y轴的说明（如速度，时间等）
	 * @param charType
	 * @param chartTitle
	 *            图标题
	 * @return
	 */
	public static JFreeChart createBarChart(CategoryDataset dataset, String xName, String yName, int charType,
			String chartTitle, boolean legend, boolean tooltips, boolean urls, PlotOrientation orientation)
	{
		JFreeChart chart = null;
		
		if (ChartConstants.BAR_3D_CHART == charType)
		{
			chart = ChartFactory.createBarChart3D(chartTitle, // 图表标题
					xName, // 目录轴的显示标签
					yName, // 数值轴的显示标签
					dataset, // 数据集
					orientation, // 图表方向：水平、垂直
					legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls); // 是否生成URL链接
		}
		else
		{
			chart = ChartFactory.createBarChart(chartTitle, // 图表标题
					xName, // 目录轴的显示标签
					yName, // 数值轴的显示标签
					dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls); // 是否生成URL链接
		}
		
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		/*
		 * VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭,
		 * 使用的关闭抗锯齿后，字体尽量选择12到14号的隶书字,这样文字最清晰好看
		 */
		//
		
		// chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.white);
		// create plot
		CategoryPlot plot = chart.getCategoryPlot();
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// vn.setAutoRangeIncludesZero(true);
		DecimalFormat df = new DecimalFormat("#0.00");
		vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
		// x轴设置
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		
		// Lable（Math.PI/3.0）度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));
		
		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable
		// 是否完整显示
		
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.1);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.1);
		// 设置 columnKey 是否间隔显示
		// domainAxis.setSkipCategoryLabelsToFit(true);
		
		plot.setNoDataMessage("No Data.");
		// 设置无数据时的信息显示颜色
		plot.setNoDataMessagePaint(Color.red);
		plot.setNoDataMessageFont(new Font("隶书", Font.BOLD, 14));// 字体的大小，
		
		plot.setDomainAxis(domainAxis);
		// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
		plot.setBackgroundPaint(new Color(255, 255, 204));
		
		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);
		
		BarRenderer renderer = new BarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.2);
		// 设置柱子边框颜色
		renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱子边框可见
		renderer.setDrawBarOutline(true);
		
		// // 设置柱的颜色
		// renderer.setSeriesPaint(0, new Color(204, 255, 255));
		// renderer.setSeriesPaint(1, new Color(153, 204, 255));
		// renderer.setSeriesPaint(2, new Color(51, 204, 204));
		
		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.0);
		
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		
		plot.setRenderer(renderer);
		// 设置柱的透明度
		plot.setForegroundAlpha(1.0f);
		
		return chart;
	}
	
	/**
	 * 横向图
	 * 
	 * @param dataset
	 *            数据集
	 * @param xName
	 *            x轴的说明（如种类，时间等）
	 * @param yName
	 *            y轴的说明（如速度，时间等）
	 * @param chartTitle
	 *            图标题
	 * @param charName
	 *            生成图片的名字
	 * @return
	 */
	public static JFreeChart createBarChart(Dataset dataset, String xName, String yName, int charType,
			String chartTitle, boolean legend, boolean tooltips, boolean urls)
	{
		return createBarChart((CategoryDataset) dataset, xName, yName, charType, chartTitle, legend, tooltips, urls,
				PlotOrientation.VERTICAL);
	}
	
	/**
	 * 水平柱状图
	 * 
	 * @param dataset
	 *            数据集
	 * @param xName
	 *            x轴的说明（如种类，时间等）
	 * @param yName
	 *            y轴的说明（如速度，时间等）
	 * @param chartTitle
	 *            图标题
	 * @param charName
	 *            生成图片的名字
	 * @return
	 */
	public static JFreeChart createHorizontalBarChart(CategoryDataset dataset, String xName, String yName,
			int charType, String chartTitle, boolean legend, boolean tooltips, boolean urls)
	{
		return createBarChart(dataset, xName, yName, charType, chartTitle, legend, tooltips, urls,
				PlotOrientation.HORIZONTAL);
	}
	
	/**
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
	public static JFreeChart createPieChar(Dataset dataset, String chartTitle, String[] pieKeys, int charType,
			boolean legend, boolean tooltips, boolean urls)
	{
		JFreeChart chart = null;
		if (ChartConstants.PIE_CHART == charType)
		{
			chart = ChartFactory.createPieChart(chartTitle, // chart
					// title
					(PieDataset) dataset,// data
					legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls); // 是否生成URL链接
		}
		else
		{
			chart = ChartFactory.createPieChart3D(chartTitle, // chart
					// title
					(PieDataset) dataset,// data
					legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls); // 是否生成URL链接
		}
		
		// 使下说明标签字体清晰,去锯齿类似于的效果
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setTextAntiAlias(false);
		// 图片背景色
		chart.setBackgroundPaint(Color.WHITE);
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		
		PiePlot plot = (PiePlot) chart.getPlot();
		// 图片中显示百分比:默认方式
		
		// 设置鼠标悬停提示
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0} ({2} percent)"));
		
		// 指定饼图轮廓线的颜色
		plot.setBaseSectionOutlinePaint(Color.GRAY);
		
		// 饼状图背景色
		plot.setBackgroundPaint(new Color(255, 255, 204));
		
		// 饼图标签背景色
		plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		
		// 设置无数据时的信息
		plot.setNoDataMessage("No Data.");
		// 设置无数据时的信息显示颜色
		plot.setNoDataMessageFont(new Font("隶书", Font.BOLD, 14));// 字体的大小，
		plot.setNoDataMessagePaint(Color.red);
		plot.setNoDataMessageFont(new Font("隶书", Font.BOLD, 14));// 字体的大小，
		
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
		
		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));
		
		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.65f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(false, true);
		
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(90);
		
		// // 设置分饼颜色
		// plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
		// plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));
		
		return chart;
	}
	
	/**
	 * 判断文件夹是否存在，如果不存在则新建
	 * 
	 * @param chartPath
	 */
	public static void isChartPathExist(String chartPath)
	{
		File file = new File(chartPath);
		if (!file.exists())
		{
			file.mkdirs();
		}
	}
	
	/**
	 * 折线图
	 * 
	 * @param chartTitle
	 *            图表标题
	 * @param x
	 *            x轴显示信息
	 * @param y
	 *            y轴显示信息
	 * @param charType
	 *            创建类型，支持3D、默认折线图
	 * @param xyDataset
	 *            填充数据集
	 * @return JFreeChart
	 */
	public static JFreeChart createLineChar(String chartTitle, String x, String y, int charType, Dataset xyDataset,
			boolean legend, boolean tooltips, boolean urls)
	{
		
		JFreeChart chart = null;
		if (ChartConstants.LINE_3D_CHART == charType)
		{
			chart = ChartFactory.createLineChart3D(chartTitle, x, y, (CategoryDataset) xyDataset,
					PlotOrientation.VERTICAL, legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls);// 是否生成URL链接
		}
		else
		{
			chart = ChartFactory.createLineChart(chartTitle, x, y, (CategoryDataset) xyDataset,
					PlotOrientation.VERTICAL, legend, // 是否显示图例(对于简单的柱状图必须是false)
					tooltips, // 是否生成工具
					urls);// 是否生成URL链接
		}
		
		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.WHITE);
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 13);
		
		// chart.setBackgroundPaint(Color.WHITE);
		
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		
		categoryplot.setRangeGridlinePaint(Color.GRAY);// 虚线色彩
		
		categoryplot.setDomainGridlinePaint(Color.GRAY);// 虚线色彩
		
		categoryplot.setBackgroundPaint(new Color(255, 255, 204));
		// categoryplot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细
		
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setDomainGridlinesVisible(true);
		
		categoryplot.setNoDataMessage("No Data.");
		categoryplot.setNoDataMessagePaint(Color.red);
		categoryplot.setNoDataMessageFont(new Font("隶书", Font.BOLD, 14));// 字体的大小，
		categoryplot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 5d)); //
		
		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		
		domainAxis.setLabelFont(labelFont);// 轴标题
		
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的
		// Lable
		// 45度倾斜
		// 设置距离图片左端距离
		
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);
		
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		
		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
		
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
		
		lineandshaperenderer.setBaseItemLabelsVisible(true); // 设置曲线显示各数据点的值
		
		// 下面三句是对设置折线图数据标示的关键代码，设置点数据可见
		lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelPaint(new Color(102, 102, 102));
		lineandshaperenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.BASELINE_CENTER));
		
		// 设置折线粗线
		lineandshaperenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
		lineandshaperenderer.setStroke(new BasicStroke(2.5f));
		
		return chart;
	}
	
	/**
	 * 堆栈柱状图
	 * 
	 * @param dataset
	 * @param xName
	 * @param yName
	 * @param chartTitle
	 * @param charName
	 * @return
	 */
	public static JFreeChart createStackedBarChart(CategoryDataset dataset, String xName, String yName,
			String chartTitle, String charName, boolean legend, boolean tooltips, boolean urls)
	{
		// 1:得到 CategoryDataset
		
		// 2:JFreeChart对象
		JFreeChart chart = ChartFactory.createStackedBarChart(chartTitle, // 图表标题
				xName, // 目录轴的显示标签
				yName, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				legend, // 是否显示图例(对于简单的柱状图必须是false)
				tooltips, // 是否生成工具
				urls // 是否生成URL链接
				);
		// 图例字体清晰
		chart.setTextAntiAlias(false);
		
		chart.setBackgroundPaint(Color.WHITE);
		
		// 2 ．2 主标题对象 主标题对象是 TextTitle 类型
		chart.setTitle(new TextTitle(chartTitle, new Font("隶书", Font.BOLD, 25)));
		// 2 ．2.1:设置中文
		// x,y轴坐标字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		
		// 2 ．3 Plot 对象 Plot 对象是图形的绘制结构对象
		CategoryPlot plot = chart.getCategoryPlot();
		
		// 设置横虚线可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.gray);
		
		// 数据轴精度
		NumberAxis vn = (NumberAxis) plot.getRangeAxis();
		// 设置最大值是1
		vn.setUpperBound(1);
		// 设置数据轴坐标从0开始
		// vn.setAutoRangeIncludesZero(true);
		// 数据显示格式是百分比
		DecimalFormat df = new DecimalFormat("0.00%");
		vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
		// DomainAxis （区域轴，相当于 x 轴）， RangeAxis （范围轴，相当于 y 轴）
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		
		domainAxis.setLabelFont(labelFont);// 轴标题
		
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		
		// x轴坐标太长，建议设置倾斜，如下两种方式选其一，两种效果相同
		// 倾斜（1）横轴上的 Lable 45度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 倾斜（2）Lable（Math.PI 3.0）度倾斜
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions
		// .createUpRotationLabelPositions(Math.PI / 3.0));
		
		domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示
		
		plot.setDomainAxis(domainAxis);
		
		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(labelFont);
		rangeAxis.setTickLabelFont(labelFont);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.15);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);
		
		// Renderer 对象是图形的绘制单元
		StackedBarRenderer renderer = new StackedBarRenderer();
		// 设置柱子宽度
		renderer.setMaximumBarWidth(0.05);
		// 设置柱子高度
		renderer.setMinimumBarLength(0.1);
		// 设置柱的边框颜色
		renderer.setBaseOutlinePaint(Color.BLACK);
		// 设置柱的边框可见
		renderer.setDrawBarOutline(true);
		
		// // 设置柱的颜色(可设定也可默认)
		// renderer.setSeriesPaint(0, new Color(204, 255, 204));
		// renderer.setSeriesPaint(1, new Color(255, 204, 153));
		
		// 设置每个地区所包含的平行柱的之间距离
		renderer.setItemMargin(0.4);
		
		plot.setRenderer(renderer);
		
		return chart;
	}
	
}
