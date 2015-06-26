<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }//management/demo/product/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>产品名称：</label>
		<input type="text" name="name" maxlength="20" class="input-medium required validate[required]"/>
	</p>	
	<p>
		<label>产品描述：</label>
		<input type="text" name="description" maxlength="500" class="input-medium"/>
	</p>	
	<p>
		<label>产品等级：</label>
		<input type="text" name="level" maxlength="10" class="input-medium required validate[required]"/>
	</p>	
	<p>
		<label>创建人：</label>
		<input type="text" name="userId" maxlength="19" class="input-medium required validate[required]"/>
	</p>	
	<p>
		<label>创建时间：</label>
		<input type="text" name="createTime" class="input-medium date" readonly="readonly" style="float:left;"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
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