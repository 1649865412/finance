package com.innshine.chart.entity;

/**
 * 
 *  <code>ImgPathLine.java，JFreeChart柱状图与拆线图封装</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-16 下午03:49:34	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class ImgPathLine {
	
	
	
	/*
	 * 图例个数+平台个数 double[][] data = new double[][] { { 672, 766}, { 325, 521} };
	 * 图例（平台） String[] columnKeys：String[] rowKeys = { "苹果", "梨子" };
	 * 横轴 {"上海", "广州" };
	 */
	
	
	/**
	 * 报表组件线状图与柱状图bean
	 */
	private double[][] data;
	private String[] rowKeys;
	private String[] columnKeys;
	private String xname;
	private String yname;
	private String reportName;
	private int imgtype;
	
	
	public ImgPathLine(){};
	

	
	public ImgPathLine(double[][] data, String[] rowKeys, String[] columnKeys,
			String xname, String yname, String reportName, int imgtype) {
		super();
		this.data = data;
		this.rowKeys = rowKeys;
		this.columnKeys = columnKeys;
		this.xname = xname;
		this.yname = yname;
		this.reportName = reportName;
		this.imgtype = imgtype;
	}



	public double[][] getData() {
		return data;
	}
	public void setData(double[][] data) {
		this.data = data;
	}
	public String[] getRowKeys() {
		return rowKeys;
	}
	public void setRowKeys(String[] rowKeys) {
		this.rowKeys = rowKeys;
	}
	public String[] getColumnKeys() {
		return columnKeys;
	}
	public void setColumnKeys(String[] columnKeys) {
		this.columnKeys = columnKeys;
	}
	public String getXname() {
		return xname;
	}
	public void setXname(String xname) {
		this.xname = xname;
	}
	public String getYname() {
		return yname;
	}
	public void setYname(String yname) {
		this.yname = yname;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public int getImgtype() {
		return imgtype;
	}
	public void setImgtype(int imgtype) {
		this.imgtype = imgtype;
	}
	
	
}
