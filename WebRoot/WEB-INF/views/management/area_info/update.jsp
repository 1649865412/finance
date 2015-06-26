<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/areaInfo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${areaInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>地区名称：</label>
		<input type="text" name="areaName" maxlength="100" value="${areaInfo.areaName}" class="input-medium"/>
	<input type="hidden" name="areaLevel" maxlength="19" class="input-medium" value="${areaInfo.areaLevel}"/>
		<input type="hidden" name="areaType" maxlength="25" class="input-medium" value="${areaInfo.areaLevel}"/>
	</p>
	<!-- 
	<p>
		<label>地区级别：</label>
		<input type="text" name="areaLevel" maxlength="19" value="${areaInfo.areaLevel}" class="input-medium"/>
	</p>
	<p>
		<label>地区类型：</label>
		<input type="text" name="areaType" maxlength="25" value="${areaInfo.areaType}" class="input-medium"/>
	</p>
	<p>
		<label>地区ID：</label>
		<input type="text" name="areaId" maxlength="19" value="${areaInfo.areaId}" class="input-medium"/>
	</p> -->
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${areaInfo.remark}" class="input-medium"/>
	</p>
	<!-- 
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" class="input-medium date" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${areaInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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