/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.financeloginfo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "finance_file_log_info")
public class FinanceFileLogInfo implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * sessionid
	 */
	@Column(length = 255)
	private String sessionId;
	
	/**
	 * 用户名
	 */
	@Column(length = 25)
	private String userName;
	
	/**
	 * 提示消息
	 */
	@Column(length = 255)
	private String message;
	
	/**
	 * 消息级别
	 */
	@Column(length = 25)
	private String messageLevel;
	
	/**
	 * 状态
	 */
	private boolean messageStatus;
	
	/**
	 * 用户ID
	 */
	@Column(length = 19)
	private Long userId;
	
	/**
	 * 用户IP
	 */
	@Column(length = 100)
	private String userAddress;
	
	/**
	 * 备注
	 */
	@Column(length = 100)
	private String remark;
	
	/**
	 * 数据更新时间
	 */
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
	 * @param sessionId
	 *        the sessionId to set
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
	/**
	 * @return the sessionId
	 */
	public String getSessionId()
	{
		return this.sessionId;
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
	 * @param message
	 *        the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	 * @param messageLevel
	 *        the messageLevel to set
	 */
	public void setMessageLevel(String messageLevel)
	{
		this.messageLevel = messageLevel;
	}
	
	/**
	 * @return the messageLevel
	 */
	public String getMessageLevel()
	{
		return this.messageLevel;
	}
	
	/**
	 * @param messageStatus
	 *        the messageStatus to set
	 */
	public void setMessageStatus(boolean messageStatus)
	{
		this.messageStatus = messageStatus;
	}
	
	/**
	 * @return the messageStatus
	 */
	public boolean isMessageStatus()
	{
		return this.messageStatus;
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
	 * @param userAddress
	 *        the userAddress to set
	 */
	public void setUserAddress(String userAddress)
	{
		this.userAddress = userAddress;
	}
	
	/**
	 * @return the userAddress
	 */
	public String getUserAddress()
	{
		return this.userAddress;
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
