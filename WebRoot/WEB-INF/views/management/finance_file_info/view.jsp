<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>保存相对路径：</label>
		<span class="unit">${financeFileInfo.fileRelativePath}</span>
	</p>
	<p>
		<label>文件名：</label>
		<span class="unit">${financeFileInfo.fileName}</span>
	</p>
	<p>
		<label>用户名：</label>
		<span class="unit">${financeFileInfo.userName}</span>
	</p>
	<p>
		<label>用户ID：</label>
		<span class="unit">${financeFileInfo.userId}</span>
	</p>
	<p>
		<label>用户IP：</label>
		<span class="unit">${financeFileInfo.userAdderss}</span>
	</p>
	<p>
		<label>导入时间：</label>
		<span class="unit">${financeFileInfo.importTime}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${financeFileInfo.remark}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${financeFileInfo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>