<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${contextPath }/management/brandInfo/list" page="${page }">
	<input type="hidden" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}"/>
	<input type="hidden" name="search_LIKE_remark" value="${param.search_LIKE_remark}"/>
	<input type="hidden" name="search_EQ_areaId" value="${param.search_EQ_areaId}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/brandInfo/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				<label>品牌名称：</label>
					<input type="text" name="search_LIKE_brandName" value="${param.search_LIKE_brandName}" alt="支持模糊查询"/>			
				</li>
				<li>
				　	<label>地区：</label>
					<select  name="search_EQ_areaId" class="select input-medium" >
						<option value="">请选择</option>
						<c:forEach items="${areaInfos}" var="areaInfo">
							<option value="${areaInfo.id}" <c:if test="${param.search_EQ_areaId eq areaInfo.id}">selected="selected"</c:if> >${areaInfo.areaName}</option>
						</c:forEach>
					</select>
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
			<shiro:hasPermission name="BrandsInfo:view">
				<li><a iconClass="magnifier" target="dialog" mask="true" width="530" height="250" rel="BrandsInfo_view" href="${contextPath }/management/brandInfo/view/{slt_uid}"><span>查看品牌</span></a></li>
			</shiro:hasPermission>		
			<shiro:hasPermission name="BrandsInfo:save">
				<li><a class="add" target="dialog" mask="true" width="530" height="250" rel="BrandsInfo_save" href="${contextPath }/management/brandInfo/create"><span>添加品牌</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BrandsInfo:edit">
				<li><a class="edit" target="dialog" mask="true" width="530" height="250" rel="BrandsInfo_edit" href="${contextPath }/management/brandInfo/update/{slt_uid}"><span>编辑品牌</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="BrandsInfo:delete">
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/management/brandInfo/delete" title="确认要删除选定？ 注意会删除品牌对应所有数据"><span>删除品牌</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>品牌名称</th>
				<th>地区</th>
				<th>备注</th>
				<th>数据更新时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${brandsInfos}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.brandName}</td>
				<td>
				<c:if test="${not empty areaInfos}">
					<c:forEach items="${areaInfos}" var="areaInfo">
						<c:if test="${areaInfo.id eq item.areaId}">${areaInfo.areaName}</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty areaInfos}">
					${item.areaId}
				</c:if>
				</td>
				<td>${item.remark}</td>
				<td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>