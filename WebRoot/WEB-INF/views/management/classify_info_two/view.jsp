<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>一级分类名称：</label>
		<span class="unit">${classifyInfoTwo.classifyInfo.classifyType}</span>
	</p>
		<p>
		<label>二级分类名称：</label>
		<span class="unit">${classifyInfoTwo.classifyType}</span>
	</p>
	
	 <p>
		<label>项目编码：</label>
		<span class="unit">${classifyInfoTwo.classifyId}</span>
	</p> 
	<p>
		<label>备注：</label>
		<span class="unit">${classifyInfoTwo.remark}</span>
	</p>
	
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${classifyInfoTwo.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>