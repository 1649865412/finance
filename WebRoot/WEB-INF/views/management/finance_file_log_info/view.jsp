<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>sessionid：</label>
		<span class="unit">${financeFileLogInfo.sessionId}</span>
	</p>
	<p>
		<label>用户名：</label>
		<span class="unit">${financeFileLogInfo.userName}</span>
	</p>
	<p>
		<label>提示消息：</label>
		<span class="unit">${financeFileLogInfo.message}</span>
	</p>
	<p>
		<label>消息级别：</label>
		<span class="unit">${financeFileLogInfo.messageLevel}</span>
	</p>
	<p>
		<label>状态：</label>
		<span class="unit">${financeFileLogInfo.messageStatus}</span>
	</p>
	<p>
		<label>用户ID：</label>
		<span class="unit">${financeFileLogInfo.userId}</span>
	</p>
	<p>
		<label>用户IP：</label>
		<span class="unit">${financeFileLogInfo.userAddress}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${financeFileLogInfo.remark}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${financeFileLogInfo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>