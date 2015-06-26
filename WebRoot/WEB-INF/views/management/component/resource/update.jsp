<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/component/resource/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${resource.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>文件名称：</label>
		<input type="text" name="name" class="input-medium required" maxlength="32" value="${name }"/><span class="info">.${resource.type }</span>
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