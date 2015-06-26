package com.innshine.chart.imgtoppt;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;



public class PieChartDemo 
{ 
	 public static void main(String[] args) throws IOException 
	 { 
		 // �����ͨ��ͼ
		 JFreeChart chart = ChartFactory.createPieChart( 
			"ͼ������ͳ��ͼ",  // ͼ�����
			 getDataSet(), // ���
			 true, // �Ƿ���ʾͼ��
			 false, // �Ƿ���ʾ������ʾ
			 false // �Ƿ���� URL 
		 ); 
		 // ��������ͼ����⣬�ı�����
		 chart.setTitle(new TextTitle("ͼ������ͳ��ͼ"
			 , new Font("����", Font.ITALIC , 22))); 
		 // ȡ��ͳ��ͼ��ĵ�һ��ͼ��
		 LegendTitle legend = chart.getLegend(0); 
		 // �޸�ͼ�������
		 legend.setItemFont(new Font("����", Font.BOLD, 14)); 
		 // ��ñ�ͼ�� Plot ����
		 PiePlot plot = (PiePlot)chart.getPlot(); 
		 // ���ñ�ͼ�����ֵı�ǩ����
		 plot.setLabelFont(new Font("����", Font.BOLD, 18)); 
		 // �趨����͸���ȣ�0-1.0 ֮�䣩
		 plot.setBackgroundAlpha(0.9f); 
		 String fileName = "D:" + File.separator + "temp";
		 File dir = new File(fileName);
		 dir.mkdirs();
		 String fileNamebook = fileName + File.separator + "book.jpg";
		 File f = new File(fileNamebook);
		 f.createNewFile();
		 
		 
		 FileOutputStream fos = new FileOutputStream(f); 
		 ChartUtilities.writeChartAsJPEG( 
			 fos, // ������ĸ������
			 1, //JPEG ͼƬ��������0~1 ֮��
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
		 dataset.setValue("������ Java EE ��ҵʵս",38000); 
		 dataset.setValue("��� Ajax ����",31000); 
		 dataset.setValue("Struts 2 Ȩ��ָ��",29000); 
		 dataset.setValue("��� XML ����",25000); 
		 return dataset; 
	 } 
}