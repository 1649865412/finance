<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${contextPath }/management/financeFileInfo/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${financeFileInfo.id}"/>
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>保存相对路径：</label>
		<input type="text" name="fileRelativePath" maxlength="1000" value="${financeFileInfo.fileRelativePath}" class="input-medium"/>
	</p>
	<p>
		<label>文件名：</label>
		<input type="text" name="fileName" maxlength="255" value="${financeFileInfo.fileName}" class="input-medium"/>
	</p>
	<p>
		<label>用户名：</label>
		<input type="text" name="userName" maxlength="25" value="${financeFileInfo.userName}" class="input-medium"/>
	</p>
	<p>
		<label>用户ID：</label>
		<input type="text" name="userId" maxlength="19" value="${financeFileInfo.userId}" class="input-medium"/>
	</p>
	<p>
		<label>用户IP：</label>
		<input type="text" name="userAdderss" maxlength="100" value="${financeFileInfo.userAdderss}" class="input-medium"/>
	</p>
	<p>
		<label>导入时间：</label>
		<input type="text" name="importTime" class="input-medium date" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${financeFileInfo.importTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		<a class="inputDateButton" href="javascript:;" style="float:left;">选择</a>
	</p>
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" value="${financeFileInfo.remark}" class="input-medium"/>
	</p>
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" class="input-medium date" readonly="readonly" style="float:left;" value="<fmt:formatDate value="${financeFileInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
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