<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>地区名称：</label>
		<span class="unit">${areaInfo.areaName}</span>
	</p>
	<!-- 
	<p>
		<label>地区级别：</label>
		<span class="unit">${areaInfo.areaLevel}</span>
	</p>
	<p>
		<label>地区类型：</label>
		<span class="unit">${areaInfo.areaType}</span>
	</p> 
	<p>
		<label>地区ID：</label>
		<span class="unit">${areaInfo.areaId}</span>
	</p>-->
	<p>
		<label>备注：</label>
		<span class="unit">${areaInfo.remark}</span>
	</p>
	 
	<p>
		<label>数据更新时间：</label>
		<span class="unit"><fmt:formatDate value="${areaInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>