<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<p>
		<label>一级分类名称：</label>
		<span class="unit">${financeCostsCategories.financeCostsType.costType}</span>
	</p>
	<p>
		<label>分类名称：</label>
		<span class="unit">${financeCostsCategories.costType}</span>
	</p>
	<p>
		<label>备注：</label>
		<span class="unit">${financeCostsCategories.remark}</span>
	</p>
	<p>
		<label>数据更新时间：</label>
		<span class="unit">${financeCostsCategories.updateTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>