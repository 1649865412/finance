package com.generate.vo;

public class Express {
	private Long  id;
	private String province;
	private String ems;
	private String yuantong;
	private String sf;
	private String zhongtong;
	public String getEms() {
		return ems;
	}
	public void setEms(String ems) {
		this.ems = ems;
	}
	public String getYuantong() {
		return yuantong;
	}
	public void setYuantong(String yuantong) {
		this.yuantong = yuantong;
	}
	public String getSf() {
		return sf;
	}
	public void setSf(String sf) {
		this.sf = sf;
	}
	public String getZhongtong() {
		return zhongtong;
	}
	public void setZhongtong(String zhongtong) {
		this.zhongtong = zhongtong;
	}
	private String express;
	private Double effective;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public Double getEffective() {
		return effective;
	}
	public void setEffective(Double effective) {
		this.effective = effective;
	}
}
