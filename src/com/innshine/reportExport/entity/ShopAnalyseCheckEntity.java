package com.innshine.reportExport.entity;

import java.text.ParseException;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.innshine.reportExport.util.DateUtil;


public class ShopAnalyseCheckEntity
{
	
	/**
	 * 前端到后台的实体bean 销售分析查询
	 * 
	 */
	private String beginTime;
	private String endTime;
	private String productPlatformId;
	private String quater;
	private String productLfPf;
	private String beginPrice;
	private String endPrice;
	//private Long organizationId;
	/**
	 * 品牌ID
	 */
	private Long brandId;
	private String materialNumber;
	private String monthlyFieldName;
	
	private int excelType = 0;
	private int img = -1;
	private String ShopOptions = "0";
	private String ShopOptionsName = "0";
	private String OrderList = "0";
	private String conditionText;
	
	// 用于存储所选列
	private String topColumn;
	private String daySalesColumn;
	
	public ShopAnalyseCheckEntity() throws ParseException
	{
		/**
		 * 默认时间范围当前月初1号到当前时间
		 */
		this.beginTime = DateUtil.nowmonthfirst();
		this.endTime = DateUtil.nowtimeString();
	}
	
	public String getTopColumn()
	{
		return topColumn;
	}
	
	public void setTopColumn(String topColumn)
	{
		this.topColumn = topColumn;
	}
	
	public String getDaySalesColumn()
	{
		return daySalesColumn;
	}
	
	public void setDaySalesColumn(String daySalesColumn)
	{
		this.daySalesColumn = daySalesColumn;
	}
	
	public String getBeginTime()
	{
		return beginTime;
	}
	
	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}
	
	public String getEndTime()
	{
		return endTime;
	}
	
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	
	public String getProductPlatformId()
	{
		return productPlatformId;
	}
	
	public void setProductPlatformId(String productPlatformId)
	{
		this.productPlatformId = productPlatformId;
	}
	
	public String getQuater()
	{
		return quater;
	}
	
	public void setQuater(String quater)
	{
		this.quater = quater;
	}
	
	public String getProductLfPf()
	{
		return productLfPf;
	}
	
	public void setProductLfPf(String productLfPf)
	{
		this.productLfPf = productLfPf;
	}
	
	public String getBeginPrice()
	{
		return beginPrice;
	}
	
	public void setBeginPrice(String beginPrice)
	{
		this.beginPrice = beginPrice;
	}
	
	public String getEndPrice()
	{
		return endPrice;
	}
	
	public void setEndPrice(String endPrice)
	{
		this.endPrice = endPrice;
	}
	
	public Long getBrandId()
	{
		return brandId;
	}

	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}

	/*	public Long getOrganizationId()
	{
		return organizationId;
	}
	
	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}
	*/
	public int getExcelType()
	{
		return excelType;
	}
	
	public void setExcelType(int excelType)
	{
		this.excelType = excelType;
	}
	
	public int getImg()
	{
		return img;
	}
	
	public void setImg(int img)
	{
		this.img = img;
	}
	
	public String getShopOptions()
	{
		return ShopOptions;
	}
	
	public void setShopOptions(String shopOptions)
	{
		ShopOptions = shopOptions;
	}
	
	public String getShopOptionsName()
	{
		return ShopOptionsName;
	}
	
	public void setShopOptionsName(String shopOptionsName)
	{
		ShopOptionsName = shopOptionsName;
	}
	
	public String getOrderList()
	{
		return OrderList;
	}
	
	public void setOrderList(String orderList)
	{
		OrderList = orderList;
	}
	
	public String getConditionText()
	{
		return conditionText;
	}
	
	public void setConditionText(String conditionText)
	{
		this.conditionText = conditionText;
	}
	
	public String getMaterialNumber()
	{
		return materialNumber;
	}
	
	public void setMaterialNumber(String materialNumber)
	{
		this.materialNumber = materialNumber;
	}
	
	public String getMonthlyFieldName()
	{
		return monthlyFieldName;
	}

	public void setMonthlyFieldName(String monthlyFieldName)
	{
		this.monthlyFieldName = monthlyFieldName;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
