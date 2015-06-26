/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.areainfo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.brand.entity.BrandsInfo;

@Entity
@Table(name = "area_info")
public class AreaInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 地区名称
	 */
	@Column(length = 100)
	private String areaName;
	
	/**
	 * 地区级别
	 */
	@Column(length = 19)
	private Long areaLevel;
	
	/**
	 * 地区类型
	 */
	@Column(length = 25)
	private String areaType;
	
	/**
	 * 地区ID
	 */
	@Column(length = 19)
	private Long areaId;
	
	/**
	 * 备注
	 */
	@Column(length = 100)
	private String remark;
	
	/**
	 * 数据更新时间
	 */
	@Column(length = 0)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@OneToMany(mappedBy = "areaInfo", cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<BrandsInfo> brandsInfos = new ArrayList<BrandsInfo>();
	
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param id
	 *        the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * @param areaName
	 *        the areaName to set
	 */
	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}
	
	/**
	 * @return the areaName
	 */
	public String getAreaName()
	{
		return this.areaName;
	}
	
	/**
	 * @param areaLevel
	 *        the areaLevel to set
	 */
	public void setAreaLevel(Long areaLevel)
	{
		this.areaLevel = areaLevel;
	}
	
	/**
	 * @return the areaLevel
	 */
	public Long getAreaLevel()
	{
		return this.areaLevel;
	}
	
	/**
	 * @param areaType
	 *        the areaType to set
	 */
	public void setAreaType(String areaType)
	{
		this.areaType = areaType;
	}
	
	/**
	 * @return the areaType
	 */
	public String getAreaType()
	{
		return this.areaType;
	}
	
	/**
	 * @param areaId
	 *        the areaId to set
	 */
	public void setAreaId(Long areaId)
	{
		this.areaId = areaId;
	}
	
	/**
	 * @return the areaId
	 */
	public Long getAreaId()
	{
		return this.areaId;
	}
	
	/**
	 * @param remark
	 *        the remark to set
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	/**
	 * @return the remark
	 */
	public String getRemark()
	{
		return this.remark;
	}
	
	/**
	 * @param updateTime
	 *        the updateTime to set
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime()
	{
		return this.updateTime;
	}
	
	public List<BrandsInfo> getBrandsInfos()
	{
		return brandsInfos;
	}
	
	public void setBrandsInfos(List<BrandsInfo> brandsInfos)
	{
		this.brandsInfos = brandsInfos;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
