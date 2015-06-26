/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brandorganization.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.entity.Idable;

@Entity
@Table(name = "brand_organization")
public class BrandOrganization implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 品牌编号
	 */
	@Column(length = 50)
	private String brandId;
	
	/**
	 * 品牌名称
	 */
	@Column(length = 100)
	private String brandName;
	
	/**
	 * 所属组织
	 */
	@Column(length = 10)
	private Long organizationId;
	
	/**
	 * 所属组织名称
	 */
	@Column(length = 100)
	private String organizationName;
	
	/**
	 * 备注
	 */
	@Column(length = 1000)
	private String remarks;
	
	/**
	 * 数据更新时间
	 */
	@Column(length = 100)
	private String updatetime;
	
	/**
	 * 预留字段1
	 */
	@Column(length = 100)
	private String reserve1;
	
	/**
	 * 预留字段2
	 */
	@Column(length = 100)
	private String reserve2;
	
	/**
	 * 预留字段3
	 */
	@Column(length = 100)
	private String reserve3;
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId)
	{
		this.brandId = brandId;
	}
	
	/**
	 * @return the brandId
	 */
	public String getBrandId()
	{
		return this.brandId;
	}
	
	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	
	/**
	 * @return the brandName
	 */
	public String getBrandName()
	{
		return this.brandName;
	}
	
	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(Long organizationId)
	{
		this.organizationId = organizationId;
	}
	
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId()
	{
		return this.organizationId;
	}
	
	/**
	 * @param organizationName
	 *            the organizationName to set
	 */
	public void setOrganizationName(String organizationName)
	{
		this.organizationName = organizationName;
	}
	
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName()
	{
		return this.organizationName;
	}
	
	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}
	
	/**
	 * @return the remarks
	 */
	public String getRemarks()
	{
		return this.remarks;
	}
	
	/**
	 * @param updatetime
	 *            the updatetime to set
	 */
	public void setUpdatetime(String updatetime)
	{
		this.updatetime = updatetime;
	}
	
	/**
	 * @return the updatetime
	 */
	public String getUpdatetime()
	{
		return this.updatetime;
	}
	
	/**
	 * @param reserve1
	 *            the reserve1 to set
	 */
	public void setReserve1(String reserve1)
	{
		this.reserve1 = reserve1;
	}
	
	/**
	 * @return the reserve1
	 */
	public String getReserve1()
	{
		return this.reserve1;
	}
	
	/**
	 * @param reserve2
	 *            the reserve2 to set
	 */
	public void setReserve2(String reserve2)
	{
		this.reserve2 = reserve2;
	}
	
	/**
	 * @return the reserve2
	 */
	public String getReserve2()
	{
		return this.reserve2;
	}
	
	/**
	 * @param reserve3
	 *            the reserve3 to set
	 */
	public void setReserve3(String reserve3)
	{
		this.reserve3 = reserve3;
	}
	
	/**
	 * @return the reserve3
	 */
	public String getReserve3()
	{
		return this.reserve3;
	}
}
