<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/classifyInfo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${classifyInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>一级分类名称：</label>
		<select  name="financeCostsCategoriesId" class="select input-medium required validate[required]"  >
			<option value="">请选择</option>
			<c:forEach items="${financeCostsCategoriess}" var="financeCostsType">
				<option value="${financeCostsType.id}" <c:if test="${classifyInfo.financeCostsCategoriesId eq financeCostsType.id}">selected="selected"</c:if>>${financeCostsType.costType}</option>
			</c:forEach>
		</select>
	</p>
	<p>
		<label>分类类型：</label>
		<input type="text" name="classifyType" maxlength="255" value="${classifyInfo.classifyType}" class="input-medium required validate[required]"/>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${classifyInfo.remark}" class="input-medium"/>
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