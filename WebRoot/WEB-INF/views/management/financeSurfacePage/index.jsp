<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>财务报表导出</title>
	</head>

<script src="${contextPath}/styles/finance/js/finance.js" type="text/javascript"/>
<link href="${contextPath}/styles/finance/css/finance.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
$(document).ready(function() {
	$("#circle").hide();	
	$("#topHideButton").toggle( 
		function () { 
			hideTop()
		  }, 
		function () { 
			  showTop();
			} 
		);
})
</script>
<body>
	<div class="pageContent">
		<div class="accountInfo" >
          <br>
          
          <button id="topHideButton">隐藏顶部</button> 
          
				<font  style="color: red;font-weight:bolder">项目：</font>
				
				<c:forEach var="brandsInfo"  items="${surFacePageEntity.brandsInfoList}">
				    <input type="checkbox" value="${brandsInfo.brandName}&${brandsInfo.id}" name="item" class="item">
				           ${brandsInfo.brandName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>    

		</div>
				
		<div class="pageFormContent"  style="height:700px;overflow: auto;">
			<div>
				<fieldset class="fieldset_left">
					<legend style="color: red">
						分类：
					</legend>

	<ul class="tree treeFolder expand">
				      <c:forEach var="classifyInfo" items="${surFacePageEntity.classifyInfoList}">
		               	   <li>
							<span><input type="checkbox" id=""  class="itemclass" name="itemClassThird" value="${classifyInfo.classifyType}&${classifyInfo.id}" />${classifyInfo.classifyType}</span>
							<ul>
							<c:forEach var="classifyInfoTwos" items="${classifyInfo.classifyInfoTwos}">
								<li>
									<label>
										<input type="checkbox" id="" class="itemclasstwo" name="itemClassFour" value="${classifyInfoTwos.classifyType}&${classifyInfoTwos.id}"/>
										${classifyInfoTwos.classifyType}
									</label>
								</li>
							</c:forEach>
							</ul>
						</li>
		           	</c:forEach>
	 </ul>
					
    <ul class="tree treeFolder expand">
			<c:forEach var="financeCostsType"
				items="${surFacePageEntity.financeCostsTypeList}">
				<li>
					<span><input type="checkbox" id="" class="itemclass"
							name="itemClassOne"
							value="${financeCostsType.costType}&${financeCostsType.id}" />${financeCostsType.costType}</span>
					<ul>
						<c:forEach var="financeCostsCategories"
							items="${financeCostsType.financeCostsCategories}">
							<li>
								<span><input type="checkbox" id="" class="itemclass"
										name="itemClassTwo"
										value="${financeCostsCategories.costType}&${financeCostsCategories.id}" />${financeCostsCategories.costType}</span>
								<ul>
									<c:forEach var="classifyInfo"
										items="${financeCostsCategories.classifyInfos}">
										<li>
											<span><input type="checkbox" id="" class="itemclass"
													name="itemClassThird"
													value="${classifyInfo.classifyType}&${classifyInfo.id}" />${classifyInfo.classifyType}</span>
											<ul>
												<c:forEach var="classifyInfoTwos"
													items="${classifyInfo.classifyInfoTwos}">
													<li>
														<label>
															<input type="checkbox" id="" class="itemclasstwo"
																name="itemClassFour" value="${classifyInfoTwos.classifyType}&${classifyInfoTwos.id}" />
														    	${classifyInfoTwos.classifyType}
														</label>
													</li>
												</c:forEach>
											</ul>
										</li>
									</c:forEach>
								</ul>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
				</fieldset>
			</div>

      <div> 
				<fieldset class="fieldset_right">
					<legend style="color: red">
						分类类型：
					</legend>
					<div style="float: left; width: 40%">
					<input type="radio" name="type" class="exportType" id=""value="1" onClick="tonbiShow()">
					同比&nbsp;&nbsp;&nbsp;
					
					<input type="radio" name="type" class="exportType" id="" value="2" onClick="tonbiShow()">
					环比&nbsp;&nbsp;&nbsp;
					<input type="radio" name="type" class="exportType" id="" value="3" onClick="tonbiShow()">
					占比&nbsp;&nbsp;&nbsp;
					<input type="radio" name="type" class="exportType"   checked="true"  id="" value="4" onClick="tonbiShow()">
					地区比&nbsp;&nbsp;&nbsp;	</div>
					<div style="float: right; width: 60%">
		
								<label style="color: red;width: 50px;">时间：</label>
								<span><input type="text" name="beginTime" class="date readonly" readonly="readonly" style="width: 80px;float: none;" value="${surFacePageEntity.beginTime}" dateFmt="yyyy-MM" />
								~
								<input type="text" name="endTime" class="date readonly" readonly="readonly" style="width: 80px;float: none;" value="${surFacePageEntity.endTime}" dateFmt="yyyy-MM" /></span>	
						&nbsp;
							<select name="circle" class="select" style="float: none;" id="circle" title="同比以结束时间为所在年的月份进行同比">
								<option value="1" >
									1年
								</option>
								<option value="2">
									2年
								</option>
								<option value="3">
									3年
								</option>
							</select>
						
				</div>
				</fieldset>

				</br>
				<fieldset class="fieldset_right">
					<legend style="color: red">
						图表类型：
					</legend>
					<input type="radio" name="typeImg" class="" id=""  value="1">
					折线图&nbsp;&nbsp;&nbsp;
					<input type="radio" name="typeImg" class="" id=""  value="2" checked="true">
					柱状图&nbsp;&nbsp;&nbsp;
					<input type="radio" name="typeImg" class="" id="" value="3" >
					饼状图&nbsp;&nbsp;&nbsp;
				</fieldset>

				<fieldset class="fieldset_right">
					<legend style="color: red">
						操作：
					</legend>
					<input type="button" value="导入基础数据" class="" id="" onClick="reportDataImport()">
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="导出图" class="" id="" onClick="reportSubmit()">
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="导出PPT" class="" id="" onClick="reportPPT()">
				</fieldset>
				</br>
              
				<fieldset class="fieldset_right fieldset_right_img">
					<legend style="color: red">
						图表列：
					</legend>
					<img height="350px" width="750px" id="reportImg" >
				</fieldset>
			</div>
		</div>
	  <form method="post" action="${contextPath}/management/surfaceReport/ExportPPT"
		id="formPPTId">
		  <input type="hidden" name="ImgName" value="" id="reportImgName" />
		</form>
	</div>
	</body>
</html>


