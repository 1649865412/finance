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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.CellSummaryInfo;

@Entity
@Table(name = "finance_costs_categories")
public class FinanceCostsCategories implements Idable<Long>
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
	 * 大分类ID
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long financeCostsId;
	
	/**
	 * 数据更新时间
	 */
	@Column(length = 0)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@OneToMany(mappedBy = "financeCostsCategories", cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellMonthSummaryInfo> cellMonthSummaryInfos;
	
	@OneToMany(mappedBy = "financeCostsCategories", cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellSummaryInfo> cellSummaryInfos;
	
	@OneToMany(mappedBy = "financeCostsCategories", cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<ClassifyInfo> classifyInfos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsId")
	private FinanceCostsType financeCostsType;
	
	public List<ClassifyInfo> getClassifyInfos()
	{
		return classifyInfos;
	}
	
	public void setClassifyInfos(List<ClassifyInfo> classifyInfos)
	{
		this.classifyInfos = classifyInfos;
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
	 * @param financeCostsId
	 *        the financeCostsId to set
	 */
	public void setFinanceCostsId(Long financeCostsId)
	{
		this.financeCostsId = financeCostsId;
	}
	
	/**
	 * @return the financeCostsId
	 */
	public Long getFinanceCostsId()
	{
		return this.financeCostsId;
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
	
	public FinanceCostsType getFinanceCostsType()
	{
		return financeCostsType;
	}
	
	public void setFinanceCostsType(FinanceCostsType financeCostsType)
	{
		this.financeCostsType = financeCostsType;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
