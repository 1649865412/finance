/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.entity;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

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
import com.innshine.classify.entity.ClassifyInfoTwo;

@Entity
@Table(name = "row_summary_info")
public class RowSummaryInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 分类大类别
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long classifyId;
	
	/**
	 * 分类大类别名称
	 */
	@Column(length = 100)
	private String classifyName;
	
	/**
	 * 分类小类别
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long classifyInfoTwoId;
	
	/**
	 * 分类小类别名称
	 */
	@Column(length = 100)
	private String classifyInfoTwoName;
	
	/**
	 * 项目编号
	 */
	@Column(length = 100)
	private String projectId;
	
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
	 * 列汇总表id
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long salesCellSummaryId;
	
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@OneToMany(mappedBy = "rowSummaryInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<RowMonthSummaryInfo> rowMonthSummaryInfos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifyInfoTwoId")
	private ClassifyInfoTwo classifyInfoTwo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifyId")
	private ClassifyInfo classifyInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesCellSummaryId")
	private CellSummaryInfo cellSummaryInfo;
	
	public ClassifyInfoTwo getClassifyInfoTwo()
	{
		return classifyInfoTwo;
	}
	
	public void setClassifyInfoTwo(ClassifyInfoTwo classifyInfoTwo)
	{
		this.classifyInfoTwo = classifyInfoTwo;
	}
	
	public CellSummaryInfo getCellSummaryInfo()
	{
		return cellSummaryInfo;
	}
	
	public void setCellSummaryInfo(CellSummaryInfo cellSummaryInfo)
	{
		this.cellSummaryInfo = cellSummaryInfo;
	}
	
	public List<RowMonthSummaryInfo> getRowMonthSummaryInfos()
	{
		return rowMonthSummaryInfos;
	}
	
	public void setRowMonthSummaryInfos(List<RowMonthSummaryInfo> rowMonthSummaryInfos)
	{
		this.rowMonthSummaryInfos = rowMonthSummaryInfos;
	}
	
	public ClassifyInfo getClassifyInfo()
	{
		return classifyInfo;
	}
	
	public void setClassifyInfo(ClassifyInfo classifyInfo)
	{
		this.classifyInfo = classifyInfo;
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
	 * @param classifyId
	 *        the classifyId to set
	 */
	public void setClassifyId(Long classifyId)
	{
		this.classifyId = classifyId;
	}
	
	/**
	 * @return the classifyId
	 */
	public Long getClassifyId()
	{
		return this.classifyId;
	}
	
	/**
	 * @param classifyInfoTwoId
	 *        the classifyInfoTwoId to set
	 */
	public void setClassifyInfoTwoId(Long classifyInfoTwoId)
	{
		this.classifyInfoTwoId = classifyInfoTwoId;
	}
	
	/**
	 * @return the classifyInfoTwoId
	 */
	public Long getClassifyInfoTwoId()
	{
		return this.classifyInfoTwoId;
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
	 * @param salesCellSummaryId
	 *        the salesCellSummaryId to set
	 */
	public void setSalesCellSummaryId(Long salesCellSummaryId)
	{
		this.salesCellSummaryId = salesCellSummaryId;
	}
	
	/**
	 * @return the salesCellSummaryId
	 */
	public Long getSalesCellSummaryId()
	{
		return this.salesCellSummaryId;
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
	
	public String getClassifyName()
	{
		return classifyName;
	}
	
	public void setClassifyName(String classifyName)
	{
		this.classifyName = classifyName;
	}
	
	public String getClassifyInfoTwoName()
	{
		return classifyInfoTwoName;
	}
	
	public void setClassifyInfoTwoName(String classifyInfoTwoName)
	{
		this.classifyInfoTwoName = classifyInfoTwoName;
	}
	
	public String getProjectId()
	{
		return projectId;
	}
	
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
