<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/costsType/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${financeCostsType.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>分类名称：</label>
		<input type="text" name="costType" maxlength="255" value="${financeCostsType.costType}" class="input-medium"/>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${financeCostsType.remark}" class="input-medium"/>
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