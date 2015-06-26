package com.innshine.chart.service.util;

import java.text.ParseException;

import org.springframework.web.bind.annotation.RequestParam;



public class ShopTime
{

	/**
	 * 功能Bean 销售分析模块(时间维度分析)(R日M月Z季Y年) selectTimeType 查询类型 year 年 firstTime
	 * 日开始时间 MonthBegin 月开始时间 QuarterfirstTime 季开始时间 circle 周期 platid 平台id Bean
	 */
	public String selectTimeType = "R";
	public int year = 2014;
	public String firstTime = "";
	public String endTime = "";
	public String MonthBegin = "1";
	public String MonthEnd = "12";
	public String QuarterfirstTime = "1";
	public String QuarterendTime = "4";
	public int circle = 1;
	public String shopPlatid = "jd";
	public String sku = "";
	public String category = "";
	public String series = "";
    
	public Long  organizationId;
	public ShopTime() throws ParseException
	{
		/**
		 *销售分析模块默认时间范围当前时间到往前推30天
		 */
		this.firstTime = DateUtil.getNewDay(-30, DateUtil.nowtimeString());
		this.endTime = DateUtil.nowtimeString();
		// this.firstTime= "2014-06-01";
		// this.endTime= "2015-01-01";
	}

	public Long getOrganizationId()
	{
		return organizationId;
	}

	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}

	public String getSelectTimeType()
	{
		return selectTimeType;
	}

	public void setSelectTimeType(String selectTimeType)
	{
		this.selectTimeType = selectTimeType;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getFirstTime()
	{
		return firstTime;
	}

	public void setFirstTime(String firstTime)
	{
		this.firstTime = firstTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getMonthBegin()
	{
		return MonthBegin;
	}

	public void setMonthBegin(String monthBegin)
	{
		MonthBegin = monthBegin;
	}

	public String getMonthEnd()
	{
		return MonthEnd;
	}

	public void setMonthEnd(String monthEnd)
	{
		MonthEnd = monthEnd;
	}

	public String getQuarterfirstTime()
	{
		return QuarterfirstTime;
	}

	public void setQuarterfirstTime(String quarterfirstTime)
	{
		QuarterfirstTime = quarterfirstTime;
	}

	public String getQuarterendTime()
	{
		return QuarterendTime;
	}

	public void setQuarterendTime(String quarterendTime)
	{
		QuarterendTime = quarterendTime;
	}

	public int getCircle()
	{
		return circle;
	}

	public void setCircle(int circle)
	{
		this.circle = circle;
	}

	public String getShopPlatid()
	{
		return shopPlatid;
	}

	public void setShopPlatid(String shopPlatid)
	{
		this.shopPlatid = shopPlatid;
	}

	public String getSku()
	{
		return sku;
	}

	public void setSku(String sku)
	{
		this.sku = sku;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

}
