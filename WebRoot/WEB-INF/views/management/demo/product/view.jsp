<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<p>
		<label>产品名称：</label>
		<span class="unit">${product.name}</span>
	</p>
	<p>
		<label>产品描述：</label>
		<span class="unit">${product.description}</span>
	</p>
	<p>
		<label>产品等级：</label>
		<span class="unit">${product.level}</span>
	</p>
	<p>
		<label>创建人：</label>
		<span class="unit">${product.userId}</span>
	</p>
	<p>
		<label>创建时间：</label>
		<span class="unit">${product.createTime}</span>
	</p>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>