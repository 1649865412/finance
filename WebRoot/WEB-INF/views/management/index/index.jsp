<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>汇尚电子商务商品管理系统</title>
		<link rel="icon" href="${contextPath }/public/favicon.ico" mce_href="${contextPath }/public/favicon.ico" type="image/x-icon" >
		<link rel="shortcut icon" href="${contextPath }/public/favicon.ico"/>
		<link href="${contextPath}/styles/dwz/themes/default/style.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/core.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/icons.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/dwz/themes/css/print.css"
			rel="stylesheet" type="text/css" media="print" />
		<link
			href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/ztree/css/zTreeStyle.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link href="${contextPath}/styles/uploadify/css/uploadify.css"
			rel="stylesheet" type="text/css" media="screen" />
		<link
			href="${contextPath}/styles/treeTable/themes/default/treeTable.css"
			rel="stylesheet" type="text/css" />
		<link href="${contextPath}/styles/keta/css/keta.css" rel="stylesheet"
			type="text/css" />
		<!--[if IE]>
<link href="${contextPath}/styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
		<!--[if lte IE 9]>
<script src="${contextPath}/styles/dwz/js/speedup.js" type="text/javascript"></script>
<![endif]-->

		<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/jquery.bgiframe.js"
			type="text/javascript">
</script>

		<script src="${contextPath}/styles/treeTable/jquery.treeTable.min.js"
			type="text/javascript">
</script>
		<%-- form验证 --%>
		<script
			src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js"
			type="text/javascript" charset="utf-8">
</script>
		<script
			src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js"
			type="text/javascript" charset="utf-8">
</script>
		<script src="${contextPath}/styles/keta/js/json2.js"
			type="text/javascript" charset="utf-8">
</script>



		<%--
<script src="${contextPath}/styles/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/dwz.print.js" type="text/javascript"></script>
--%>

		<script src="${contextPath}/styles/dwz/js/dwz.min.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/dwz.switchEnv.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js"
			type="text/javascript">
</script>
		<%-- 自定义JS --%>
		<script src="${contextPath}/styles/dwz/js/customer.js"
			type="text/javascript">
</script>
		<script src="${contextPath}/styles/keta/js/keta.js"
			type="text/javascript">
</script>
		<%-- upload --%>
		<script
			src="${contextPath}/styles/uploadify/scripts/jquery.uploadify.min.js"
			type="text/javascript">
</script>
		<%-- zTree --%>
		<script
			src="${contextPath}/styles/ztree/js/jquery.ztree.all-3.5.min.js"
			type="text/javascript">
</script>

		<script type="text/javascript">
$(function() {
	DWZ.init("${contextPath}/styles/dwz/dwz.frag.xml", {
		loginUrl : "${contextPath}/login/timeout",
		loginTitle : "登录", // 弹出登录对话框
		debug : false, // 调试模式 【true|false】
		callback : function() {
			initEnv();
			$("#themeList").theme( {
				themeBase : "${contextPath}/styles/dwz/themes"
			});
		}
	});
});

function changeBran(obj) {
	var changObj = $.trim($(obj).val());
	$.ajax( {
		url : '${contextPath }/management/index/changBrand?organizationId='
				+ changObj + "&rand=" + (new Date().getTime()),
		type : 'post',
		dataType : 'json',
		success : function(data, textStatus) {
			alertMsg.correct('切换成功 ！');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});

}
</script>
	</head>
	<body scroll="no">
		<div id="layout">
			<div id="header">
				<div class="headerNav">
					<a class="logo" href="${contextPath}/management/index">Logo</a>
					<ul class="nav">
						<li>
							<a href="${contextPath}/management/index">&nbsp;&nbsp;主页</a>
						</li>
						<li>
							<a href="${contextPath}/management/index/updateBase"
								target="dialog" mask="true" width="550" height="250">修改用户信息</a>
						</li>
						<li>
							<a href="${contextPath}/management/index/updatePwd"
								target="dialog" mask="true" width="500" height="200">修改密码</a>
						</li>
						<li>
							<a href="${contextPath}/logout">退出</a>
						</li>
					</ul>
					<br />
					<p
						style="margin-top: 45px; margin-right: 20px; font-size: 14px; float: right">
						您好 ${login_user.username }，欢迎进入商品管理系统！
					</p>
					<%-- 
			<ul class="themeList" id="themeList">
				<li theme="default"><div class="selected">blue</div></li>
				<li theme="green"><div>green</div></li>
				<li theme="purple"><div>purple</div></li>
				<li theme="silver"><div>silver</div></li>
				<li theme="azure"><div>天蓝</div></li>
			</ul>
			--%>
				</div>

				<div id="navMenu">
					<ul>
						<c:forEach var="level1" items="${menuModule.children }"
							varStatus="menuStatus">
							<c:if
								test="${level1.id!=10&&level1.id!=12&&level1.id!=15&&level1.id!=27}">
								<li
									<c:if test="${menuStatus.index == 0}">class="selected"</c:if>>
									<a
										href="${contextPath}/management/index/retrievalSubMenu/${level1.id }"><span>${level1.name
											}</span>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse">
							<div></div>
						</div>
					</div>
				</div>
				
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>
							&nbsp;
						</h2>
						<div>
							collapse
						</div>
					</div>
					
					<div class="accordion" fillSpace="sideBar">
						<c:if test="${firstMenuModule != null}">
							<div class="accordionContent" id="accordionContentID">
								<ul class="tree treeFolder expand">
									<c:forEach var="level2" items="${firstMenuModule.children}">
										<c:if test="${level2.id!=5}">
											<li>
												<dwz:menuAccordion child="${level2 }"
													urlPrefix="${contextPath }" />
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
								<li tabid="main" class="main">
									<a href="javascript:void(0)"><span><span
											class="home_icon">&nbsp;&nbsp;&nbsp;主页</span>
									</span>
									</a>
								</li>
							</ul>
						</div>
						<div class="tabsLeft">
							left
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">
							right
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">
							more
						</div>
					</div>
					<ul class="tabsMoreList">
						<li>
							<a href="javascript:void(0)">&nbsp;&nbsp;主页</a>
						</li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox" id="navTab-panelID" >
						<div class="page unitBox" id="index_page">
							<div class="accountInfo" >
							 
								<div class="right">
									<p>
										<fmt:formatDate value="<%=new Date() %>"
											pattern="yyyy-MM-dd EEEE" />
									</p>
								</div>
								<p>
									<span>系统概要汇总 </span>
								</p>
							</div>
							<div class="pageFormContent" layouth="80">
								<c:if test="${summarys != null}">
								  <c:forEach items="${summarys}" var="su">
								    <c:if test="${organizationId== null || organizationId == su.organizationId}">
									 
									<fieldset>
						                <legend><b>${su.organizationName}</b></legend>
									<p>  
											<a rel="SuitInfo_47" target="navTab" title="套装信息管理" href="${contextPath}/management/suitinfo/list">套装数量：${su.suitNum}</a>
									</p>
									 <c:if test="${'7' == su.organizationId}">
									<p>
											<a rel="PlatformManager_43" title="平台管理" target="navTab" href="${contextPath}/management/generalsettings/platformManager/list">平台数量：${su.platformNum}</a>
									</p>
								     </c:if>
								     <c:if test="${'8' == su.organizationId}">
									<p>
										 
											<a rel="Productinfo_22" target="navTab" title="产品信息管理" href="${contextPath}/management/productInfo/list">UPC数量：${su.productNum}</a>
										 
									</p>
									<p>
									 
											<a rel="export_28" target="navTab" title="销售信息管理" href="${contextPath}/management/shopAnalyse/list">销售数量：${su.sellNum}</a>
									 
									</p>
									<p> 
											<a rel="export_28" target="navTab" title="销售信息管理" href="${contextPath}/management/salesDetails/list">销售金额：${su.sellValue}</a>
										 
									</p>
									</c:if>
									 <c:if test="${'7' == su.organizationId}">
									<p>
											<a rel="OlyProductInfo_52" target="navTab" title="欧莱雅-产品信息管理" href="${contextPath}/management/olyProductManager/list">UPC数量：${su.productNum}</a>
										 
									</p>
									<p>
											<a rel="OlySalesDetails_53" target="navTab" title="欧莱雅-销售信息管理" href="${contextPath}/management/olySalesDetails/list">销售数量：${su.sellNum}</a>
									 
									</p>
									<p> 
											<a rel="OlySalesDetails_53" target="navTab" title="欧莱雅-销售信息管理" href="${contextPath}/management/olySalesDetails/list">销售金额：${su.sellValue}</a>
										 
									</p>
									</c:if>
									</fieldset>
									</c:if>
								  </c:forEach>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="footer">
			Copyright&copy; 版权所有 汇尚电子商务有限公司
		</div>
	</body>
</html>