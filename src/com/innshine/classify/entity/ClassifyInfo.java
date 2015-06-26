/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.entity;

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
import com.innshine.reportExport.entity.RowMonthSummaryInfo;
import com.innshine.reportExport.entity.RowSummaryInfo;

@Entity
@Table(name = "classify_info")
public class ClassifyInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 分类类型
	 */
	@Column(length = 255)
	private String classifyType;
	
	/**
	 * 财务费用小类别
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long financeCostsCategoriesId;
	
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
	
	@OneToMany(mappedBy = "classifyInfo", cascade = { CascadeType.REFRESH, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<ClassifyInfoTwo> classifyInfoTwos;
	
	@OneToMany(mappedBy = "classifyInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<RowSummaryInfo> rowSummaryInfos;
	
	@OneToMany(mappedBy = "classifyInfo", cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	private List<RowMonthSummaryInfo> rowMonthSummaryInfos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsCategoriesId")
	private FinanceCostsCategories financeCostsCategories;
	
	public Long getFinanceCostsCategoriesId()
	{
		return financeCostsCategoriesId;
	}
	
	public void setFinanceCostsCategoriesId(Long financeCostsCategoriesId)
	{
		this.financeCostsCategoriesId = financeCostsCategoriesId;
	}
	
	public FinanceCostsCategories getFinanceCostsCategories()
	{
		return financeCostsCategories;
	}
	
	public void setFinanceCostsCategories(FinanceCostsCategories financeCostsCategories)
	{
		this.financeCostsCategories = financeCostsCategories;
	}
	
	public List<RowSummaryInfo> getRowSummaryInfos()
	{
		return rowSummaryInfos;
	}
	
	public void setRowSummaryInfos(List<RowSummaryInfo> rowSummaryInfos)
	{
		this.rowSummaryInfos = rowSummaryInfos;
	}
	
	public List<RowMonthSummaryInfo> getRowMonthSummaryInfos()
	{
		return rowMonthSummaryInfos;
	}
	
	public void setRowMonthSummaryInfos(List<RowMonthSummaryInfo> rowMonthSummaryInfos)
	{
		this.rowMonthSummaryInfos = rowMonthSummaryInfos;
	}
	
	public List<ClassifyInfoTwo> getClassifyInfoTwos()
	{
		return classifyInfoTwos;
	}
	
	public void setClassifyInfoTwos(List<ClassifyInfoTwo> classifyInfoTwos)
	{
		this.classifyInfoTwos = classifyInfoTwos;
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
	 * @param classifyType
	 *        the classifyType to set
	 */
	public void setClassifyType(String classifyType)
	{
		this.classifyType = classifyType;
	}
	
	/**
	 * @return the classifyType
	 */
	public String getClassifyType()
	{
		return this.classifyType;
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
