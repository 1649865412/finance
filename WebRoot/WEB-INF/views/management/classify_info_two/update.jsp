<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/classifyInfoTwo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${classifyInfoTwo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>一级分类名称：</label>
		<select  name="areaId" class="select input-medium required validate[required]" >
			<option value="">请选择</option>
					<c:forEach items="${classifyInfos}" var="classifyInfo">
						<option value="${classifyInfo.id}" <c:if test="${classifyInfoTwo.classifyInfoId eq classifyInfo.id}">selected="selected"</c:if>>${classifyInfo.classifyType}</option>
					</c:forEach>
		</select>
		<!-- <input type="text" name="classifyInfoId" maxlength="19" value="${classifyInfoTwo.classifyInfoId}" class="input-medium"/> -->
	</p>
	<p>
		<label>二级分类名称：</label>
		<input type="text" name="classifyType" maxlength="255" value="${classifyInfoTwo.classifyType}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>项目编码：</label>
		<input type="text" name="classifyId" maxlength="100" value="${classifyInfoTwo.classifyId}" class="input-medium"/><span class="info">即：6001.0100</span>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${classifyInfoTwo.remark}" class="input-medium"/>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>