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
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.classify.entity.ClassifyInfoTwo;

@Entity
@Table(name = "row_month_summary_info")
public class RowMonthSummaryInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 品牌编号
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long brandId;
	
	/**
	 * 汇总行_id
	 */
	@Column(length = 19, nullable = false, insertable = false, updatable = false)
	private Long rowSummaryInfoId;
	
	/**
	 * 品牌名字
	 */
	@Column(length = 25)
	private String brandName;
	
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
	 * 营业收入类型(1:营业收入,2:其它)
	 */
	@Column(length = 10)
	private Integer businessIncomeType;
	
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
	@JoinColumn(name = "rowSummaryInfoId")
	private RowSummaryInfo rowSummaryInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifyInfoTwoId")
	private ClassifyInfoTwo classifyInfoTwo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classifyId")
	private ClassifyInfo classifyInfo;
	
	
	@Transient
	private String yearTime;
	
	@Transient
	private String monthTime;
	
	
	
	public String getYearTime() {
		return yearTime;
	}

	public void setYearTime(String yearTime) {
		this.yearTime = yearTime;
	}

	public String getMonthTime() {
		return monthTime;
	}

	public void setMonthTime(String monthTime) {
		this.monthTime = monthTime;
	}

	public ClassifyInfo getClassifyInfo()
	{
		return classifyInfo;
	}
	
	public void setClassifyInfo(ClassifyInfo classifyInfo)
	{
		this.classifyInfo = classifyInfo;
	}
	
	public ClassifyInfoTwo getClassifyInfoTwo()
	{
		return classifyInfoTwo;
	}
	
	public void setClassifyInfoTwo(ClassifyInfoTwo classifyInfoTwo)
	{
		this.classifyInfoTwo = classifyInfoTwo;
	}
	
	public RowSummaryInfo getRowSummaryInfo()
	{
		return rowSummaryInfo;
	}
	
	public void setRowSummaryInfo(RowSummaryInfo rowSummaryInfo)
	{
		this.rowSummaryInfo = rowSummaryInfo;
	}
	
	public BrandsInfo getBrandsInfo()
	{
		return brandsInfo;
	}
	
	public void setBrandsInfo(BrandsInfo brandsInfo)
	{
		this.brandsInfo = brandsInfo;
	}
	
	public Long getRowSummaryInfoId()
	{
		return rowSummaryInfoId;
	}
	
	public void setRowSummaryInfoId(Long rowSummaryInfoId)
	{
		this.rowSummaryInfoId = rowSummaryInfoId;
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
	 * @param classifyName
	 *        the classifyName to set
	 */
	public void setClassifyName(String classifyName)
	{
		this.classifyName = classifyName;
	}
	
	/**
	 * @return the classifyName
	 */
	public String getClassifyName()
	{
		return this.classifyName;
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
	 * @param classifyInfoTwoName
	 *        the classifyInfoTwoName to set
	 */
	public void setClassifyInfoTwoName(String classifyInfoTwoName)
	{
		this.classifyInfoTwoName = classifyInfoTwoName;
	}
	
	/**
	 * @return the classifyInfoTwoName
	 */
	public String getClassifyInfoTwoName()
	{
		return this.classifyInfoTwoName;
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
	 * @param businessIncomeType
	 *        the businessIncomeType to set
	 */
	public void setBusinessIncomeType(Integer businessIncomeType)
	{
		this.businessIncomeType = businessIncomeType;
	}
	
	/**
	 * @return the businessIncomeType
	 */
	public Integer getBusinessIncomeType()
	{
		return this.businessIncomeType;
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
