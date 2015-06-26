/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.coststype.entity;

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
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.CellSummaryInfo;

@Entity
@Table(name = "finance_costs_type")
public class FinanceCostsType implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 费用类型
	 */
	@Column(length = 255)
	private String costType;
	
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
	
	@OneToMany(mappedBy = "financeCostsType", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellMonthSummaryInfo> cellMonthSummaryInfos;
	
	@OneToMany(mappedBy = "financeCostsType", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellSummaryInfo> cellSummaryInfos;
	
	@OneToMany(mappedBy = "financeCostsType", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<FinanceCostsCategories> financeCostsCategories;
	
	public List<FinanceCostsCategories> getFinanceCostsCategories()
	{
		return financeCostsCategories;
	}
	
	public void setFinanceCostsCategories(List<FinanceCostsCategories> financeCostsCategories)
	{
		this.financeCostsCategories = financeCostsCategories;
	}
	
	public List<CellMonthSummaryInfo> getCellMonthSummaryInfos()
	{
		return cellMonthSummaryInfos;
	}
	
	public void setCellMonthSummaryInfos(List<CellMonthSummaryInfo> cellMonthSummaryInfos)
	{
		this.cellMonthSummaryInfos = cellMonthSummaryInfos;
	}
	
	public List<CellSummaryInfo> getCellSummaryInfos()
	{
		return cellSummaryInfos;
	}
	
	public void setCellSummaryInfos(List<CellSummaryInfo> cellSummaryInfos)
	{
		this.cellSummaryInfos = cellSummaryInfos;
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
	 * @param costType
	 *        the costType to set
	 */
	public void setCostType(String costType)
	{
		this.costType = costType;
	}
	
	/**
	 * @return the costType
	 */
	public String getCostType()
	{
		return this.costType;
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
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
