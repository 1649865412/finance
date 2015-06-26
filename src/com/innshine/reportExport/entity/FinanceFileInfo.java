/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.base.entity.Idable;

@Entity
@Table(name = "finance_file_info")
public class FinanceFileInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 保存相对路径
	 */
	@Column(length = 1000)
	private String fileRelativePath;
	
	/**
	 * 文件名
	 */
	@Column(length = 255)
	private String fileName;
	
	/**
	 * 用户名
	 */
	@Column(length = 25)
	private String userName;
	
	/**
	 * 用户ID
	 */
	@Column(length = 19)
	private Long userId;
	
	/**
	 * 用户IP
	 */
	@Column(length = 100)
	private String userAdderss;
	
	/**
	 * 导入时间
	 */
	@Column(length = 0)
	@Temporal(TemporalType.DATE)
	private Date importTime;
	
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
	 * @param fileRelativePath
	 *        the fileRelativePath to set
	 */
	public void setFileRelativePath(String fileRelativePath)
	{
		this.fileRelativePath = fileRelativePath;
	}
	
	/**
	 * @return the fileRelativePath
	 */
	public String getFileRelativePath()
	{
		return this.fileRelativePath;
	}
	
	/**
	 * @param fileName
	 *        the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return this.fileName;
	}
	
	/**
	 * @param userName
	 *        the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return this.userName;
	}
	
	/**
	 * @param userId
	 *        the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return this.userId;
	}
	
	/**
	 * @param userAdderss
	 *        the userAdderss to set
	 */
	public void setUserAdderss(String userAdderss)
	{
		this.userAdderss = userAdderss;
	}
	
	/**
	 * @return the userAdderss
	 */
	public String getUserAdderss()
	{
		return this.userAdderss;
	}
	
	/**
	 * @param importTime
	 *        the importTime to set
	 */
	public void setImportTime(Date importTime)
	{
		this.importTime = importTime;
	}
	
	/**
	 * @return the importTime
	 */
	public Date getImportTime()
	{
		return this.importTime;
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
}
