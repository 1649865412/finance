/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.entity;

import java.math.BigDecimal;
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
import com.innshine.coststype.entity.FinanceCostsCategories;
import com.innshine.coststype.entity.FinanceCostsType;

@Entity
@Table(name = "cell_summary_info")
public class CellSummaryInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 财务费用大类别
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long financeCostsTypeId;
	
	/**
	 * 财务费用大类别名称
	 */
	@Column(length = 100)
	private String financeCostsTypeName;
	
	/**
	 * 财务费用小类别
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long financeCostsCategoriesId;
	
	/**
	 * 财务费用小类别名称
	 */
	@Column(length = 100)
	private String financeCostsCategoriesName;
	
	/**
	 * 总计类型(1:行总计2: all总计)
	 */
	@Column(length = 10)
	private Integer summaryType;
	
	/**
	 * 项目小计
	 */
	@Column(length = 20)
	private BigDecimal projectSubtotal;
	
	/**
	 * 综合费用
	 */
	@Column(length = 20)
	private BigDecimal comprehensiveCost;
	
	/**
	 * 本期合计
	 */
	@Column(length = 20)
	private BigDecimal currentSummary;
	
	/**
	 * 环比
	 */
	@Column(length = 20)
	private BigDecimal chain;
	
	/**
	 * 上期合计
	 */
	@Column(length = 20)
	private BigDecimal preSummary;
	
	/**
	 * 上年同期
	 */
	@Column(length = 20)
	private BigDecimal preYearAgo;
	
	/**
	 * 同比
	 */
	@Column(length = 20)
	private BigDecimal yearRate;
	
	/**
	 * 汇总时间
	 */
	@Column(length = 0)
	@Temporal(TemporalType.TIMESTAMP)
	private Date summaryTime;
	
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
	
	@OneToMany(mappedBy = "cellSummaryInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<CellMonthSummaryInfo> cellMonthSummaryInfos;
	
	@OneToMany(mappedBy = "cellSummaryInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<RowSummaryInfo> rowSummaryInfos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsTypeId")
	private FinanceCostsType financeCostsType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsCategoriesId")
	private FinanceCostsCategories financeCostsCategories;
	
	public List<RowSummaryInfo> getRowSummaryInfos()
	{
		return rowSummaryInfos;
	}
	
	public void setRowSummaryInfos(List<RowSummaryInfo> rowSummaryInfos)
	{
		this.rowSummaryInfos = rowSummaryInfos;
	}
	
	public List<CellMonthSummaryInfo> getCellMonthSummaryInfos()
	{
		return cellMonthSummaryInfos;
	}
	
	public void setCellMonthSummaryInfos(List<CellMonthSummaryInfo> cellMonthSummaryInfos)
	{
		this.cellMonthSummaryInfos = cellMonthSummaryInfos;
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
	 * @param financeCostsTypeId
	 *        the financeCostsTypeId to set
	 */
	public void setFinanceCostsTypeId(Long financeCostsTypeId)
	{
		this.financeCostsTypeId = financeCostsTypeId;
	}
	
	/**
	 * @return the financeCostsTypeId
	 */
	public Long getFinanceCostsTypeId()
	{
		return this.financeCostsTypeId;
	}
	
	/**
	 * @param financeCostsCategoriesId
	 *        the financeCostsCategoriesId to set
	 */
	public void setFinanceCostsCategoriesId(Long financeCostsCategoriesId)
	{
		this.financeCostsCategoriesId = financeCostsCategoriesId;
	}
	
	/**
	 * @return the financeCostsCategoriesId
	 */
	public Long getFinanceCostsCategoriesId()
	{
		return this.financeCostsCategoriesId;
	}
	
	/**
	 * @param summaryType
	 *        the summaryType to set
	 */
	public void setSummaryType(Integer summaryType)
	{
		this.summaryType = summaryType;
	}
	
	/**
	 * @return the summaryType
	 */
	public Integer getSummaryType()
	{
		return this.summaryType;
	}
	
	/**
	 * @param projectSubtotal
	 *        the projectSubtotal to set
	 */
	public void setProjectSubtotal(BigDecimal projectSubtotal)
	{
		this.projectSubtotal = projectSubtotal;
	}
	
	/**
	 * @return the projectSubtotal
	 */
	public BigDecimal getProjectSubtotal()
	{
		return this.projectSubtotal;
	}
	
	/**
	 * @param comprehensiveCost
	 *        the comprehensiveCost to set
	 */
	public void setComprehensiveCost(BigDecimal comprehensiveCost)
	{
		this.comprehensiveCost = comprehensiveCost;
	}
	
	/**
	 * @return the comprehensiveCost
	 */
	public BigDecimal getComprehensiveCost()
	{
		return this.comprehensiveCost;
	}
	
	/**
	 * @param currentSummary
	 *        the currentSummary to set
	 */
	public void setCurrentSummary(BigDecimal currentSummary)
	{
		this.currentSummary = currentSummary;
	}
	
	/**
	 * @return the currentSummary
	 */
	public BigDecimal getCurrentSummary()
	{
		return this.currentSummary;
	}
	
	/**
	 * @param chain
	 *        the chain to set
	 */
	public void setChain(BigDecimal chain)
	{
		this.chain = chain;
	}
	
	/**
	 * @return the chain
	 */
	public BigDecimal getChain()
	{
		return this.chain;
	}
	
	/**
	 * @param preSummary
	 *        the preSummary to set
	 */
	public void setPreSummary(BigDecimal preSummary)
	{
		this.preSummary = preSummary;
	}
	
	/**
	 * @return the preSummary
	 */
	public BigDecimal getPreSummary()
	{
		return this.preSummary;
	}
	
	/**
	 * @param preYearAgo
	 *        the preYearAgo to set
	 */
	public void setPreYearAgo(BigDecimal preYearAgo)
	{
		this.preYearAgo = preYearAgo;
	}
	
	/**
	 * @return the preYearAgo
	 */
	public BigDecimal getPreYearAgo()
	{
		return this.preYearAgo;
	}
	
	/**
	 * @param yearRate
	 *        the yearRate to set
	 */
	public void setYearRate(BigDecimal yearRate)
	{
		this.yearRate = yearRate;
	}
	
	/**
	 * @return the yearRate
	 */
	public BigDecimal getYearRate()
	{
		return this.yearRate;
	}
	
	/**
	 * @param summaryTime
	 *        the summaryTime to set
	 */
	public void setSummaryTime(Date summaryTime)
	{
		this.summaryTime = summaryTime;
	}
	
	/**
	 * @return the summaryTime
	 */
	public Date getSummaryTime()
	{
		return this.summaryTime;
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
	
	public String getFinanceCostsTypeName()
	{
		return financeCostsTypeName;
	}
	
	public void setFinanceCostsTypeName(String financeCostsTypeName)
	{
		this.financeCostsTypeName = financeCostsTypeName;
	}
	
	public String getFinanceCostsCategoriesName()
	{
		return financeCostsCategoriesName;
	}
	
	public void setFinanceCostsCategoriesName(String financeCostsCategoriesName)
	{
		this.financeCostsCategoriesName = financeCostsCategoriesName;
	}
	
	public FinanceCostsType getFinanceCostsType()
	{
		return financeCostsType;
	}
	
	public void setFinanceCostsType(FinanceCostsType financeCostsType)
	{
		this.financeCostsType = financeCostsType;
	}
	
	public FinanceCostsCategories getFinanceCostsCategories()
	{
		return financeCostsCategories;
	}
	
	public void setFinanceCostsCategories(FinanceCostsCategories financeCostsCategories)
	{
		this.financeCostsCategories = financeCostsCategories;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
