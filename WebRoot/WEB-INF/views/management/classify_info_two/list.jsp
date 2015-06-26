<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/classifyInfoTwo/list" page="${page }">
	<input type="hidden" name="search_LIKE_classifyType" value="${param.search_LIKE_classifyType}"/>
	<input type="hidden" name="search_EQ_classifyInfoId" value="${param.search_EQ_classifyInfoId}"/>
	<input type="hidden" name="search_LIKE_remark" value="${param.search_LIKE_remark}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/classifyInfoTwo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>一分类名称</label>
					<select  name="search_EQ_classifyInfoId" class="select input-medium " >
						<option value="">请选择</option>
								<c:forEach items="${classifyInfos}" var="classifyInfo">
									<option value="${classifyInfo.id}" <c:if test="${param.search_EQ_classifyInfoId eq classifyInfo.id}">selected="selected"</c:if>>${classifyInfo.classifyType}</option>
								</c:forEach>
					</select>
				</li>
				<li>
					<label>二分类名称</label>
					<input type="text" name="search_LIKE_classifyType" value="${param.search_LIKE_classifyType}" alt="支持模糊查询"/>			
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
			<shiro:hasPermission name="ClassifyInfoTwo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="ClassifyInfoTwo_view" href="${contextPath }/management/classifyInfoTwo/view/{slt_uid}"><span>查看分类</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="ClassifyInfoTwo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="ClassifyInfoTwo_save" href="${contextPath }/management/classifyInfoTwo/create"><span>添加分类</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ClassifyInfoTwo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="ClassifyInfoTwo_edit" href="${contextPath }/management/classifyInfoTwo/update/{slt_uid}"><span>编辑分类</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ClassifyInfoTwo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/classifyInfoTwo/delete" title="确认要删除选定? 注意会删除分类对应所有数据"><span>删除分类</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>一分类名称</th>
				<th>二级分类名称</th>
				<th>项目编号</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${classifyInfoTwos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.classifyInfo.classifyType}</td>
				<td>${item.classifyType}</td>
				<td>${item.classifyId}</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>