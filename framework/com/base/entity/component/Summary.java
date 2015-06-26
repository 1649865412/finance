package com.base.entity.component;

import java.io.Serializable;

public class Summary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getSuitNum() {
		return suitNum;
	}
	public void setSuitNum(String suitNum) {
		this.suitNum = suitNum;
	}
	public String getSubmitReportNum() {
		return submitReportNum;
	}
	public void setSubmitReportNum(String submitReportNum) {
		this.submitReportNum = submitReportNum;
	}
	public String getPlatformNum() {
		return platformNum;
	}
	public void setPlatformNum(String platformNum) {
		this.platformNum = platformNum;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	private String suitNum;
 
	 
	private String submitReportNum;
	 
	 
	
	private String platformNum;
	 
	 
	
	private String productNum;
	 
	
	private String sellNum;
	private String sellValue;
	
	private String organizationId;
	private String organizationName;
	 
	 
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getSellValue() {
		return sellValue;
	}
	public void setSellValue(String sellValue) {
		this.sellValue = sellValue;
	}
	 
	public String getProductNumConfigId() {
		return productNumConfigId;
	}
	public void setProductNumConfigId(String productNumConfigId) {
		this.productNumConfigId = productNumConfigId;
	}
	private String productNumConfigId;
	
}
