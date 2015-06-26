package com.innshine.chart.entity;

/**
 *  <code>ImgPathPine.java;JFreeChart饼状图封装</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-16 下午03:50:36	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class ImgPathPine
{
	// double[] data = { 30, 91};值
	// String[] keys = { "失败率", "成功率" };图例
	//  name标题 
	public double[] data; 
	public String[] keys; 
	public String name;
	
	
	
	public ImgPathPine(double[] data, String[] keys, String name)
	{
		super();
		this.data = data;
		this.keys = keys;
		this.name = name;
	}
	public double[] getData()
	{
		return data;
	}
	public void setData(double[] data)
	{
		this.data = data;
	}
	public String[] getKeys()
	{
		return keys;
	}
	public void setKeys(String[] keys)
	{
		this.keys = keys;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	
}
