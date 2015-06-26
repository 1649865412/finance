package com.innshine.reportExport.entity;

import java.text.ParseException;
import java.util.List;

import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.coststype.entity.FinanceCostsType;
import com.innshine.reportExport.util.DateUtil;

/**
 * <code>SurFacePageEntity.java 页面封装Bean</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-21 上午10:57:53
 * @version 1.0 </br>最后修改人 无
 */
public class SurFacePageEntity
{
	private List<AreaInfo> areaInfoList;
	private List<ClassifyInfo> classifyInfoList;
	private List<BrandsInfo> brandsInfoList;
	private List<FinanceCostsType> financeCostsTypeList;
	private String beginTime ;
	private String endTime ;
	
	

	public SurFacePageEntity() 
	{	
	
	}

	public SurFacePageEntity(List<ClassifyInfo> classifyInfoList,
			List<BrandsInfo> brandsInfoList,List<FinanceCostsType> financeCostsTypeList)throws ParseException
	{
		super();
		this.classifyInfoList = classifyInfoList;
		this.brandsInfoList = brandsInfoList;
		this.financeCostsTypeList = financeCostsTypeList;
		this.beginTime = DateUtil.nowtimeString().substring(0,7) ;
		this.endTime = DateUtil.getNewDay(30, DateUtil.nowtimeString()).substring(0,7);
		
	}
	
	public List<FinanceCostsType> getFinanceCostsTypeList()
	{
		return financeCostsTypeList;
	}

	public void setFinanceCostsTypeList(List<FinanceCostsType> financeCostsTypeList)
	{
		this.financeCostsTypeList = financeCostsTypeList;
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

	public List<ClassifyInfo> getClassifyInfoList()
	{
		return classifyInfoList;
	}

	public void setClassifyInfoList(List<ClassifyInfo> classifyInfoList)
	{
		this.classifyInfoList = classifyInfoList;
	}

	public List<BrandsInfo> getBrandsInfoList()
	{
		return brandsInfoList;
	}

	public void setBrandsInfoList(List<BrandsInfo> brandsInfoList)
	{
		this.brandsInfoList = brandsInfoList;
	}

	public List<AreaInfo> getAreaInfoList()
	{
		return areaInfoList;
	}

	public void setAreaInfoList(List<AreaInfo> areaInfoList)
	{
		this.areaInfoList = areaInfoList;
	}

}
