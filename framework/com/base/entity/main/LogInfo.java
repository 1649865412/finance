/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.entity.main.LogEntity.java
 * Class:			LogEntity
 * Date:			2013-5-3
 * Author:			Vigor
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.base.entity.Idable;
import com.base.log.LogLevel;

/** 
 * 	
 * @author 	Vigor
 * Version  2.1.0
 * @since   2013-5-3 下午4:43:44 
 */
@Entity
@Table(name="base_log_info")
public class LogInfo implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=32)
	private String username;

	@Column(length=256)
	private String message;
	
	@Column(length=16)
	private String ipAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;
	
	@Column(length=16)
	@Enumerated(EnumType.STRING)
	private LogLevel logLevel;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
