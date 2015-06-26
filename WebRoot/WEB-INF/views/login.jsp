<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>汇尚电子商务商品管理系统</title>
		<link rel="icon" href="${contextPath }/public/favicon.ico" mce_href="${contextPath }/public/favicon.ico" type="image/x-icon" >
		<link rel="shortcut icon" href="${contextPath }/public/favicon.ico"/>
		<link href="${contextPath }/styles/login/system.css" rel="stylesheet"
			type="text/css" />
		<!-- form验证 -->
		<link rel="stylesheet"
			href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css"
			type="text/css" />
		<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js"
			type="text/javascript">
</script>
		<script
			src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js"
			type="text/javascript" charset="utf-8">
</script>
		<script
			src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js"
			type="text/javascript" charset="utf-8">
</script>

		<script>
jQuery(document).ready(function() {
	jQuery("#formID").validationEngine();
});
jQuery(document).ready(function() {
	$("#captcha").click(function() {
		$(this).attr("src", "${contextPath}/Captcha.jpg?time=" + new Date());
		return false;
	});
});
</script>
<style type="text/css">
	.login_center{position: relative; top: auto; left: auto;}
</style>
	</head>
	<body style="text-align: center;">  
		<div class="logintopbg"></div>

		<div class="login login_center">
			<div class="leftbar fl"></div>
			<div class="rightbar fr" >
				<h2></h2>
				<form method="post" action="${contextPath}/login" id="formID">
					<c:if test="${msg!=null }">
						<p style="color: red; margin-left: 10px;">
							${msg }
						</p>
					</c:if>
					<span class="inputtxt fl">用户名</span>
					<input class="inputbg w180 fl validate[required]" name="username" id="username" value="${username }" type="text" />
					<!-- <span class="alert_right">填写正确出现提醒</span> -->
					<div class="clearb"></div>
					<span class="inputtxt fl">密&nbsp;&nbsp;码</span>
					<input class="inputbg w180 fl validate[required]" type="password" id="password"  name="password"/>
					<!--<span class="alert_wrong">填写错误要提醒</span>-->
					<div class="clearb"></div>
					<span class="inputtxt fl">验证码</span>
					<input type="text" id="captcha_key" style="width: 70px;float:left;" name="captcha_key" class="inputbg fl validate[required,maxSize[4]]" size="6" />
					<span class="fl">&nbsp;&nbsp;<img src="${contextPath}/Captcha.jpg" alt="点击刷新验证码" width="75" height="24" id="captcha"/></span> 
					<!--<span class="alert_wrong">填写错误要提醒</span>-->
					<div class="clearb"></div>
					<span class="inputtxt fl"></span>
					<input name="" type="submit" class="btn fl" value="登录" />
					<!--<input name="" type="button" class="btn fl" value="注册" />-->
					 <span class="alert_jg fl"><a href="javascript:toggleBox('forgotPwd')">忘记密码？</a>
					</span>
					<div class="clearb"></div>
					<span class="inputtxt2 fl" style="width: 50px;">注意：</span><span
						class="inputtxt2 w280 fl" style="text-align: left;">1、不要在公共场合保存登录信息。<br />2、尽量避免多人使用同一帐号。<br />3、为保证您的帐号安全，退出系统时请退出登录。</span>
				</form>
			</div>
			<div class="clearb"></div>

		</div>

		<div class="loginfooter">
			Copyright&copy;2013 版权所有 汇尚电子商务有限公司
		</div>

	</body>
</html>
