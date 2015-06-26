<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>    
<div class="accordion" fillSpace="sideBar"> 
	<div class="accordionContent">
		<ul class="tree treeFolder expand">
		 <c:forEach var="level2" items="${subMenus}">
			<li>
				<dwz:menuAccordion child="${level2 }" urlPrefix="${contextPath }"/>
			</li>
		</c:forEach> 
		</ul>
	</div>
	 
</div>
