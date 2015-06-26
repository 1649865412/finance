/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.coststype.entity.FinanceCostsCategories;
import com.innshine.coststype.entity.FinanceCostsType;

@Entity
@Table(name = "cell_month_summary_info")
public class CellMonthSummaryInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 销售单列记录合计表ID
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long salesCellSummaryId;
	
	/**
	 * 品牌编号
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long brandId;
	
	/**
	 * 品牌名字
	 */
	@Column(length = 25)
	private String brandName;
	
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
	 * 月汇总金额
	 */
	@Column(length = 20)
	private BigDecimal monthSummaryAmount;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId")
	private BrandsInfo brandsInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesCellSummaryId")
	private CellSummaryInfo cellSummaryInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsTypeId")
	private FinanceCostsType financeCostsType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financeCostsCategoriesId")
	private FinanceCostsCategories financeCostsCategories;
	
	@Transient
	private String yearTime;
	
	@Transient
	private String monthTime;
	
	
	public String getYearTime()
	{
		return yearTime;
	}

	public void setYearTime(String yearTime)
	{
		this.yearTime = yearTime;
	}

	public String getMonthTime()
	{
		return monthTime;
	}

	public void setMonthTime(String monthTime)
	{
		this.monthTime = monthTime;
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
	
	public CellSummaryInfo getCellSummaryInfo()
	{
		return cellSummaryInfo;
	}
	
	public void setCellSummaryInfo(CellSummaryInfo cellSummaryInfo)
	{
		this.cellSummaryInfo = cellSummaryInfo;
	}
	
	public BrandsInfo getBrandsInfo()
	{
		return brandsInfo;
	}
	
	public void setBrandsInfo(BrandsInfo brandsInfo)
	{
		this.brandsInfo = brandsInfo;
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
	 * @param brandId
	 *        the brandId to set
	 */
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	/**
	 * @return the brandId
	 */
	public Long getBrandId()
	{
		return this.brandId;
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
	 * @param financeCostsTypeName
	 *        the financeCostsTypeName to set
	 */
	public void setFinanceCostsTypeName(String financeCostsTypeName)
	{
		this.financeCostsTypeName = financeCostsTypeName;
	}
	
	/**
	 * @return the financeCostsTypeName
	 */
	public String getFinanceCostsTypeName()
	{
		return this.financeCostsTypeName;
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
	 * @param financeCostsCategoriesName
	 *        the financeCostsCategoriesName to set
	 */
	public void setFinanceCostsCategoriesName(String financeCostsCategoriesName)
	{
		this.financeCostsCategoriesName = financeCostsCategoriesName;
	}
	
	/**
	 * @return the financeCostsCategoriesName
	 */
	public String getFinanceCostsCategoriesName()
	{
		return this.financeCostsCategoriesName;
	}
	
	/**
	 * @param monthSummaryAmount
	 *        the monthSummaryAmount to set
	 */
	public void setMonthSummaryAmount(BigDecimal monthSummaryAmount)
	{
		this.monthSummaryAmount = monthSummaryAmount;
	}
	
	/**
	 * @return the monthSummaryAmount
	 */
	public BigDecimal getMonthSummaryAmount()
	{
		return this.monthSummaryAmount;
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
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
