<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/areaInfo/list" page="${page }">
	<input type="hidden" name="search_LIKE_areaName" value="${param.search_LIKE_areaName}"/>
	<input type="hidden" name="search_LIKE_remark" value="${param.search_LIKE_remark}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/areaInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>地区名称：</label>
					<input type="text" name="search_LIKE_areaName" value="${param.search_LIKE_areaName}" alt="支持模糊查询"/>			
				</li>
				<li>
				<label>备注：</label>
					<input type="text" name="search_LIKE_remark" value="${param.search_LIKE_remark}" alt="支持模糊查询"/>			
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="AreaInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="AreaInfo_view" href="${contextPath }/management/areaInfo/view/{slt_uid}"><span>查看地区</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="AreaInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="AreaInfo_save" href="${contextPath }/management/areaInfo/create"><span>添加地区</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="AreaInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="AreaInfo_edit" href="${contextPath }/management/areaInfo/update/{slt_uid}"><span>编辑地区</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="AreaInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/areaInfo/delete" title="确认要删除选定?"><span>删除地区</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>地区名称</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${areaInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.areaName}</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>