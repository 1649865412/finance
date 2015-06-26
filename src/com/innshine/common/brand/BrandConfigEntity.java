package com.innshine.common.brand;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 根据登陆用户不同，区分不同数据的配置文件属性实体bean
 * 
 * 
 * Copyright 2014 All right reserved.
 * 
 * @author admin 时间 2014年7月18日 下午2:29:54
 * @version 1.0 </br>最后修改人 无
 */
public class BrandConfigEntity
{
	/**
	 * 数据清洗模版对应品牌ID
	 */
	private String id;
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 所属组织编号
	 */
	private String organizationId;
	
	/**
	 * 所属组织名称
	 */
	private String organizationName;
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getBrandName()
	{
		return brandName;
	}
	
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	
	public String getOrganizationId()
	{
		return organizationId;
	}
	
	public void setOrganizationId(String organizationId)
	{
		this.organizationId = organizationId;
	}
	
	public String getOrganizationName()
	{
		return organizationName;
	}
	
	public void setOrganizationName(String organizationName)
	{
		this.organizationName = organizationName;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
