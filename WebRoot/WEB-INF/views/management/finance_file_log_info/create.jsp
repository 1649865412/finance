<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<div class="pageContent">
<form method="post" action="${contextPath }/management/financeFileLogInfo/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>sessionid：</label>
		<input type="text" name="sessionId" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>用户名：</label>
		<input type="text" name="userName" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>提示消息：</label>
		<input type="text" name="message" maxlength="255" class="input-medium"/>
	</p>	
	<p>
		<label>消息级别：</label>
		<input type="text" name="messageLevel" maxlength="25" class="input-medium"/>
	</p>	
	<p>
		<label>状态：</label>
		<input type="text" name="messageStatus" maxlength="0" class="input-medium"/>
	</p>	
	<p>
		<label>用户ID：</label>
		<input type="text" name="userId" maxlength="19" class="input-medium"/>
	</p>	
	<p>
		<label>用户IP：</label>
		<input type="text" name="userAddress" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>备注：</label>
		<input type="text" name="remark" maxlength="100" class="input-medium"/>
	</p>	
	<p>
		<label>数据更新时间：</label>
		<input type="text" name="updateTime" class="input-medium date" readonly="readonly" style="float:left;"/>
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