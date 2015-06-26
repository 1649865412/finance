<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/financeFileLogInfo/list" page="${page }">
	<input type="hidden" name="search_LIKE_id" value="${param.search_LIKE_id}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/financeFileLogInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<input type="text" name="search_LIKE_id" value="${param.search_LIKE_id}"/>			
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
			<shiro:hasPermission name="FinanceFileLogInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="FinanceFileLogInfo_view" href="${contextPath }/management/financeFileLogInfo/view/{slt_uid}"><span>查看FinanceFileLogInfo</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="FinanceFileLogInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="FinanceFileLogInfo_save" href="${contextPath }/management/financeFileLogInfo/create"><span>添加FinanceFileLogInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="FinanceFileLogInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="FinanceFileLogInfo_edit" href="${contextPath }/management/financeFileLogInfo/update/{slt_uid}"><span>编辑FinanceFileLogInfo</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="FinanceFileLogInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/financeFileLogInfo/delete" title="确认要删除选定FinanceFileLogInfo?"><span>删除FinanceFileLogInfo</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>sessionid</th>
				<th>用户名</th>
				<th>提示消息</th>
				<th>消息级别</th>
				<th>状态</th>
				<th>用户ID</th>
				<th>用户IP</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${financeFileLogInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.sessionId}</td>
				<td>${item.userName}</td>
				<td>${item.message}</td>
				<td>${item.messageLevel}</td>
				<td>${item.messageStatus}</td>
				<td>${item.userId}</td>
				<td>${item.userAddress}</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>