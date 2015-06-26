/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brand.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;

@Entity
@Table(name = "brands_info")
public class BrandsInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 品牌名称
	 */
	@Column(length = 100)
	private String brandName;
	
	/**
	 * 地区编号
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId")
	private AreaInfo areaInfo;
	
	@OneToMany(mappedBy = "brandsInfo", cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<RowMonthSummaryInfo> monthSummaryInfos = new ArrayList<RowMonthSummaryInfo>();
	
	@OneToMany(mappedBy = "brandsInfo", cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellMonthSummaryInfo> cellMonthSummaryInfos = new ArrayList<CellMonthSummaryInfo>();
	
	public List<CellMonthSummaryInfo> getCellMonthSummaryInfos()
	{
		return cellMonthSummaryInfos;
	}
	
	public void setCellMonthSummaryInfos(List<CellMonthSummaryInfo> cellMonthSummaryInfos)
	{
		this.cellMonthSummaryInfos = cellMonthSummaryInfos;
	}
	
	public List<RowMonthSummaryInfo> getMonthSummaryInfos()
	{
		return monthSummaryInfos;
	}
	
	public void setMonthSummaryInfos(List<RowMonthSummaryInfo> monthSummaryInfos)
	{
		this.monthSummaryInfos = monthSummaryInfos;
	}
	
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
	 * @param brandName
	 *        the brandName to set
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
	
	public AreaInfo getAreaInfo()
	{
		return areaInfo;
	}
	
	public void setAreaInfo(AreaInfo areaInfo)
	{
		this.areaInfo = areaInfo;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
