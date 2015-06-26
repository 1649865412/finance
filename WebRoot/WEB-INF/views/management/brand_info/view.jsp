<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>品牌名称：</label>
		<span class="unit">${brandsInfo.brandName}</span>
	</p>
	<p>
		<label>地区：</label>
		<span class="unit">
		
		<c:if test="${not empty areaInfos}">
					<c:forEach items="${areaInfos}" var="areaInfo">
						<c:if test="${areaInfo.id eq brandsInfo.areaId}">${areaInfo.areaName}</c:if>
					</c:forEach>
				</c:if>
			<c:if test="${empty areaInfos}">
					${brandsInfo.areaId}
			</c:if>
		</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${brandsInfo.remark}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${brandsInfo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>