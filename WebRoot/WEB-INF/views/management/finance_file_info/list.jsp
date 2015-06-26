<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/financeFileInfo/list" page="${page }">
	<input type="hidden" name="search_LIKE_fileName" value="${param.search_LIKE_fileName}"/>
	<input type="hidden" name="search_LIKE_remark" value="${param.search_LIKE_remark}"/>
	<input type="hidden" name="search_LTE_importTime" value="${param.search_LTE_importTime}"/>
	<input type="hidden" name="search_GTE_importTime" value="${param.search_GTE_importTime}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/financeFileInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>文件名：</label>
					<input type="text" name="search_LIKE_fileName" value="${param.search_LIKE_fileName}"/>			
				</li>
				<li>
				<label>文件时间：</label>
					<input type="text" name="search_GTE_importTime"  value="${param.search_GTE_importTime}"  class="input-medium date textInput readonly" readonly="readonly" style="width: 80px">
					~<input type="text" name="search_LTE_importTime" value="${param.search_LTE_importTime}" class="input-medium date textInput readonly" readonly="readonly" style="width: 80px">
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
<form  id="downLoadFileId" name="downLoadFileId" method="post"  action="${contextPath }/management/financeFileInfo/download/" />

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="FinanceFileInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="FinanceFileInfo_view" href="${contextPath }/management/financeFileInfo/view/{slt_uid}"><span>查看文件</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="FinanceFileInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="FinanceFileInfo_save" href="${contextPath }/management/financeFileInfo/create"><span>添加文件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="FinanceFileInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="FinanceFileInfo_edit" href="${contextPath }/management/financeFileInfo/update/{slt_uid}"><span>编辑文件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="FinanceFileInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/financeFileInfo/delete" title="确认要删除选定?"><span>删除文件</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<!--  <th>保存相对路径</th>-->
				<th>文件名</th>
				<th>文件时间</th>
				<th>导入用户名</th>
				<th>导入用户IP</th>
				<th>备注</th>
				<th>数据更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${financeFileInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td><a href="javascript:void(0);"  onclick="downloadFinanceFile(${item.id});">${item.fileName}</a></td>
				<td><a href="javascript:void(0);"  onclick="downloadFinanceFile(${item.id});"><fmt:formatDate value="${item.importTime}" pattern="yyyy-MM"/></a></td>
				<td>${item.userName}</td>
				<td>${item.userAdderss}</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:void(0);"  onclick="downloadFinanceFile(${item.id});"  >下载</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>

<script type="text/javascript" > 
	function downloadFinanceFile(fileId)
	{
		$("#downLoadFileId").attr({action:"${contextPath }/management/financeFileInfo/download/"+fileId});
		$("#downLoadFileId").submit();
	}
</script>