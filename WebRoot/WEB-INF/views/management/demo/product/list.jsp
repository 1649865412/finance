<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }//management/demo/product/list" page="${page }">
	<input type="hidden" name="search_LIKE_titles" value="${param.search_LIKE_titles}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }//management/demo/product/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<input type="text" name="search_LIKE_titles" value="${param.search_LIKE_titles}"/>			
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="Product:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="Product_view" href="${contextPath }//management/demo/product/view/{slt_uid}"><span>查看产品</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="Product:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="Product_save" href="${contextPath }//management/demo/product/create"><span>添加产品</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Product:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="Product_edit" href="${contextPath }//management/demo/product/update/{slt_uid}"><span>编辑产品</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Product:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }//management/demo/product/delete" title="确认要删除选定产品?"><span>删除产品</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>产品名称</th>
				<th>产品描述</th>
				<th>产品等级</th>
				<th>创建人</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${products}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.name}</td>
				<td>${item.description}</td>
				<td>${item.level}</td>
				<td>${item.userId}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>