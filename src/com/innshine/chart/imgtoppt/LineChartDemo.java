package com.innshine.chart.imgtoppt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;



public class LineChartDemo 
{ 
	 public static void main(String[] args) throws IOException 
	 { 
		 JFreeChart chart = null;
		 // �����ͨ��ͼ
//		 JFreeChart chart = ChartFactory.createPieChart( 
//			"ͼ����ͳ��ͼ",  // ͼ�����
//			 getDataSet(), // ���
//			 true, // �Ƿ���ʾͼ��
//			 false, // �Ƿ���ʾ������ʾ
//			 false // �Ƿ���� URL 
//		 ); 
		 chart = ChartFactory.createTimeSeriesChart("month", 
				 "month", 
				 "problems", 
				 createTimeSeries(), 
				 true, 
				 false,
				 false);
		 
		 // ��������ͼ����⣬�ı�����
		 chart.setTitle(new TextTitle("month problem"
			 , new Font("宋体", Font.ITALIC , 22))); 
		 // ȡ��ͳ��ͼ��ĵ�һ��ͼ��
		 LegendTitle legend = chart.getLegend(0); 
		 // �޸�ͼ�������
		 legend.setItemFont(new Font("宋体", Font.BOLD, 14)); 
		 
		 //chart.setBackgroundImageAlignment(10);
		 
		 
		 
		 // ��ñ�ͼ�� Plot ����
		 XYPlot xyplot = chart.getXYPlot();
		 xyplot.setRangeGridlinesVisible(true);
		 xyplot.setDomainGridlinesVisible(true);
		 xyplot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		 xyplot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		 xyplot.setBackgroundPaint(new Color(255, 253, 246));
		 xyplot.setNoDataMessage("no data to display");//没有数据时显示的文字说明。
		 xyplot.setNoDataMessageFont(new Font("", Font.BOLD, 14));//字体的大小，粗体。
		 xyplot.setNoDataMessagePaint(new Color(87, 149, 117));//字体颜色
		 xyplot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 5d)); //
		 //xyplot.set
		 ValueAxis valueAxis = xyplot.getDomainAxis();//值域
		 //valueAxis.setUpperBound(maxpress);
		 valueAxis.setAutoRangeMinimumSize(1);
		 //valueAxis.setLowerBound(min);
		 valueAxis.setAutoRange(false);
		 valueAxis.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细
		 valueAxis.setAxisLinePaint(new Color(215, 215, 215)); // 坐标轴颜色
		 valueAxis.setLabelPaint(new Color(10, 10, 10)); // 坐标轴标题颜色
		 
		 XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
				 .getRenderer();
		 xylineandshaperenderer.setBaseItemLabelsVisible(true);
		 xylineandshaperenderer.setSeriesShapesVisible(0, true);
		 xylineandshaperenderer.setSeriesFillPaint(0, new Color(127, 128, 0));
		 xylineandshaperenderer.setSeriesPaint(0, new Color(127, 128, 0));

		 xylineandshaperenderer.setSeriesShapesVisible(1, true);
		 xylineandshaperenderer.setSeriesFillPaint(1, new Color(254, 103, 0));
		 xylineandshaperenderer.setSeriesPaint(1, new Color(254, 103, 0));

		 xylineandshaperenderer.setSeriesShapesVisible(2, true);
		 xylineandshaperenderer.setSeriesFillPaint(2, new Color(204, 0, 122));
		 xylineandshaperenderer.setSeriesPaint(2, new Color(204, 0, 122));
		 
		 xylineandshaperenderer.setSeriesShapesVisible(3, true);
		 xylineandshaperenderer.setSeriesFillPaint(3, new Color(228, 128, 100));
		 xylineandshaperenderer.setSeriesPaint(3, new Color(228, 128, 100));
		 
		 xylineandshaperenderer.setBaseLinesVisible(true);
		 
		 //xylineandshaperenderer.setBaseItemLabelsVisible(true);
		 xylineandshaperenderer
		 .setBasePositiveItemLabelPosition(new ItemLabelPosition(
		 ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		 xylineandshaperenderer
		 .setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		 xylineandshaperenderer.setBaseItemLabelPaint(new Color(102, 102, 102));// 显
		 
		 StandardXYToolTipGenerator xytool = new StandardXYToolTipGenerator();
		 xylineandshaperenderer.setBaseToolTipGenerator(xytool);
		 xylineandshaperenderer.setBaseStroke(new BasicStroke(1.5f));
		
//		 IntervalMarker intermarker = new IntervalMarker(100, 300);
//		 //intermarker.setPaint(Color.decode("#66FFCC"));// 域顏色
//		 intermarker.setLabelFont(new Font("SansSerif", 41, 14));
//		 intermarker.setLabelPaint(Color.RED);
//		 intermarker.setLabel("");
//		 xyplot.addRangeMarker(intermarker, Layer.BACKGROUND);
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		 format = new SimpleDateFormat("yyyy-MM");
		 dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1, format));
		 dateaxis.setVerticalTickLabels(false); // 设为true表示横坐标旋转到垂直。
		 dateaxis.setTickMarkPosition(DateTickMarkPosition.START);
//		 PiePlot plot = (PiePlot)chart.getPlot(); 
//		 // ���ñ�ͼ��ֵı�ǩ����
//		 plot.setLabelFont(new Font("e��", Font.BOLD, 18)); 
//		 // �趨����͸��ȣ�0-1.0 ֮�䣩
//		 plot.setBackgroundAlpha(0.9f); 
		 saveToDisk(chart);
	 } 
	 
	 private static void saveToDisk(JFreeChart chart) throws IOException{
		 String fileName = "D:" + File.separator + "temp";
		 File dir = new File(fileName);
		 dir.mkdirs();
		 String fileNamebook = fileName + File.separator + "book.jpg";
		 File f = new File(fileNamebook);
		 f.createNewFile();
		 
		 
		 FileOutputStream fos = new FileOutputStream(f); 
		 ChartUtilities.writeChartAsJPEG( 
			 fos, // ����ĸ������
			 1, //JPEG ͼƬ������0~1 ֮��
			 chart, // ͳ��ͼ�����
			 800, // ��
			 600,// ��
			 null //ChartRenderingInfo ��Ϣ
		 ); 
		 fos.close(); 
	 }
	 
	 private static DefaultPieDataset getDataSet() 
	 { 
		 // �ṩ��ɱ�ͼ�����
		 DefaultPieDataset dataset = new DefaultPieDataset(); 
		 dataset.setValue("��� Java ����",47000); 
		 dataset.setValue("���� Java EE ��ҵʵս",38000); 
		 dataset.setValue("��� Ajax ����",31000); 
		 dataset.setValue("Struts 2 Ȩ��ָ��",29000); 
		 dataset.setValue("��� XML ����",25000); 
		 
		 
		 return dataset; 
	 } 
	 
	 private static TimeSeriesCollection createTimeSeries(){
		 TimeSeriesCollection timeSeries = new TimeSeriesCollection();
//		 TimeSeries timeseries = new TimeSeries("SHOU", org.jfree.data.time.Month.class);
//		 TimeSeries timeseries1 = new TimeSeries("c1", org.jfree.data.time.Month.class);
//		 TimeSeries timeseriedia = new TimeSeries("SHOU", org.jfree.data.time.Month.class);
//		 TimeSeries timeseriedia1 = new TimeSeries("s", org.jfree.data.time.Month.class);
		 TimeSeries t1 = new TimeSeries("无线", "domain1", "100");
		 TimeSeries t2 = new TimeSeries("网络", "domain2", "200");
		 TimeSeries t3 = new TimeSeries("企业网", "domain1", "100");
		 TimeSeries t4 = new TimeSeries("软件", "domain1", "100");
		 
         
         t1.add(getMonth(2, 2013), 100);
         t1.add(getMonth(3, 2013), 160);
         t1.add(getMonth(4, 2013), 220);
         t1.add(getMonth(5, 2013), 200);
         t1.add(getMonth(6, 2013), 120);
         t1.add(getMonth(7, 2013), 100);
         t1.add(getMonth(8, 2013), 160);
         t1.add(getMonth(9, 2013), 220);
         t1.add(getMonth(10, 2013), 250);
         t1.add(getMonth(11, 2013), 210);
         t1.add(getMonth(12, 2013), 210);
         t1.add(getMonth(1, 2013), 220);
         
         
         t2.add(getMonth(2, 2013), 120);
         t2.add(getMonth(3, 2013), 110);
         t2.add(getMonth(4, 2013), 150);
         t2.add(getMonth(5, 2013), 100);
         t2.add(getMonth(6, 2013), 120);
         t2.add(getMonth(7, 2013), 110);
         t2.add(getMonth(8, 2013), 150);
         t2.add(getMonth(9, 2013), 130);
         t2.add(getMonth(10, 2013), 120);
         t2.add(getMonth(11, 2013), 140);
         t2.add(getMonth(12, 2013), 160);
         t2.add(getMonth(1, 2013), 100);
         
         
         t3.add(getMonth(2, 2013), 220);
         t3.add(getMonth(3, 2013), 190);
         t3.add(getMonth(4, 2013), 200);
         t3.add(getMonth(1, 2013), 90);
         

         t4.add(getMonth(3, 2013), 120);
         t4.add(getMonth(4, 2013), 160);
         t4.add(getMonth(5, 2013), 110);
         t4.add(getMonth(6, 2013), 130);
         t4.add(getMonth(7, 2013), 120);
         t4.add(getMonth(8, 2013), 190);
         t4.add(getMonth(9, 2013), 140);
         t4.add(getMonth(10, 2013), 130);
         t4.add(getMonth(11, 2013), 150);
         t4.add(getMonth(12, 2013), 180);
         t4.add(getMonth(1, 2013), 110);
         t4.add(getMonth(2, 2013), 130);
		 
		 timeSeries.addSeries(t1);
		 timeSeries.addSeries(t2);
		 timeSeries.addSeries(t3);
		 timeSeries.addSeries(t4);
		 return timeSeries;
	 }
	 
	 private static Month getMonth(int month,int year){
		 return new Month(month,year);
	 }
}