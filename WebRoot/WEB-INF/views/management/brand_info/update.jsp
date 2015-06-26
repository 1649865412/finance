<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/brandInfo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${brandsInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>品牌名称：</label>
		<input type="text" name="brandName" maxlength="100" value="${brandsInfo.brandName}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>地区：</label>
		<select  name="areaId" class="select input-medium required validate[required]" >
			<option value="">请选择</option>
					<c:forEach items="${areaInfos}" var="areaInfo">
						<option value="${areaInfo.id}" <c:if test="${brandsInfo.areaId eq  areaInfo.id}">selected="selected"</c:if>>${areaInfo.areaName}</option>
					</c:forEach>
		</select>
		<!-- 
		<input type="text" name="areaId" maxlength="19" value="${brandsInfo.areaId}" class="input-medium"/> -->
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${brandsInfo.remark}" class="input-medium"/>
	</p>
	<!-- 
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" class="input-medium date" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${brandsInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p> -->
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>