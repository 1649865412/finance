package com.innshine.reportExport.reportInterface;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.util.Constant;

/**
 * <code>InterfaceExportImpl.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-19 上午11:53:39
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class InterfaceExportImpl
{
	private Abstrategy strategy;
	private ConditionEntity conditionEntity;
	private HttpServletRequest request;

	
   
	public InterfaceExportImpl(){
		
	}
	
	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public Abstrategy getStrategy()
	{
		return strategy;
	}

	public void setStrategy(Abstrategy strategy)
	{
		this.strategy = strategy;
	}

	public InterfaceExportImpl(Abstrategy strategy,ConditionEntity conditionEntity,HttpServletRequest request)
	{
		super();
		this.strategy = strategy;
		this.conditionEntity = conditionEntity;
		this.request=request;
	}

	public Abstrategy getInterfaceExport()
	{
		return strategy;
	}

	public void setInterfaceExport(Abstrategy strategy)
	{
		this.strategy = strategy;
	}

	public ConditionEntity getConditionEntity()
	{
		return conditionEntity;
	}

	public void setConditionEntity(ConditionEntity conditionEntity)
	{
		this.conditionEntity = conditionEntity;
	}

	
	public String reportImg()
	{
		return this.strategy.reportImg(this);
	}
	
	


}
