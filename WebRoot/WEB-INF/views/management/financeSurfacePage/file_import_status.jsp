<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>文件导入状态</title>
		
<script type="text/javascript">
var refreshParseStatusUrl = "${contextPath }/management/reportParse/refreshParseStatus?jsessionid=<%=session.getId()%>";
var refreshParseStatusIntervals  = [];
var tempRefreshParseStatusTime = 0 ;
var error_count = 0 ;

$(document).ready(function(){
    refreshParseStatus();

   var refreshParseStatusTime = setInterval(function(){
   	
   		if(typeof(tempRefreshParseStatusTime) == undefined || tempRefreshParseStatusTime <= 0)
   		{
   			refreshParseStatus();
   			tempRefreshParseStatusTime = 3;
   			if($("#refreshParseStatusTime_lable_id").val() == '')
   			{	
   				$("#refreshParseStatusTime_lable_id").html(tempRefreshParseStatusTime);
   			}
   			
   		}
   		else
   		{
   			tempRefreshParseStatusTime = tempRefreshParseStatusTime -1;
   		}
   		
   		$("#refreshParseStatusTimeId").empty();
   		$("#refreshParseStatusTimeId").html("<strong style='color:red'>"+tempRefreshParseStatusTime+"</strong>&nbsp;秒");
   		
   },1000);
   
  // var refreshParseStatusInterval = setInterval(refreshParseStatus,5000);
    
  // refreshParseStatusIntervals.push(refreshParseStatusInterval);
   
   refreshParseStatusIntervals.push(refreshParseStatusTime);
   
});

function clearIntervalRefreshParseStatus()
{
	if(typeof(refreshParseStatusIntervals) != undefined)
	{
		try
		{
			for(var i in refreshParseStatusIntervals)
			{
				clearInterval(refreshParseStatusIntervals[i]);
				
			}

			$("#refreshParseStatusTimeId").html("<strong style='color:red'>本次解析已结束</strong>");
			//alertMsg.confirm("本次解析已经结束，请选择是否关闭窗口！" ,{okCall:function()
        	//{
        	//	$.pdialog.closeCurrent(); 
        	//}});
		}catch(e)
		{
			$.error = e;
		}
	}
}




function getRemark(remark)
{
	if(remark != undefined)
	{
		try
		{
			var jsonRemark = $.parseJSON(remark);
			if(jsonRemark.status =='DELETE')
			{
				var url = "${contextPath}/management/reportParse/deleteBySummaryTime/"+jsonRemark.params;
				var title = "确认删除" + jsonRemark.params + "数据吗?";
				var deleteHtml = "<a  iconclass=\"user_add\" class=\"delete\"";
				deleteHtml +=" src='javascript:#' onclick=\"deletDataBySummaryTime('"+url+"','"+title+"')\"";
				deleteHtml +=" title="+title+" style='cursor:pointer'>";
				deleteHtml += "<img src=\"/finance/styles/dwz/themes/css/images/icons/cross.png\" style=\"position: relative; top:3px;\">";
				deleteHtml +="<span>删除数据</span></a>";
				return deleteHtml;
			}
		}
		catch(e)
		{
			$.error = e;
		}
		
	   return remark;
	}
}

function refreshParseStatus()
{
	  $.ajax(
            {url:refreshParseStatusUrl,
        		type: 'post',
        		dataType :'json',
        		success	: function(data, textStatus){
        				if(typeof(data) != undefined)
        				{
	        				var html = "";
	        				for(var i in data)
							{
								html+="<tr>";
								html+="<td width='10.1%'>";
								if(data[i].messageLevel ==  'INFO')
								{
									html+="<img src='${contextPath}/styles/dwz/themes/css/images/icons/accept.png' style='position: relative; top:3px;' />&nbsp;正常";
								}
								if(data[i].messageLevel ==  'ERROR')
								{
									html+="<img src='${contextPath}/styles/dwz/themes/css/images/icons/cross.png' style='position: relative; top:3px;' /><span style='color: red'>&nbsp;错误</span>";
								}
								html+="</td>";
								html+="<td width='80%' title="+data[i].message+">"+data[i].message+"</td>";
								html+="<td width='10.3%'>"+getRemark(data[i].remark)+"</td>";
								html+="</tr>";
								// 如果到达最后一条，则清空定时器
								if(data[i].messageStatus || data[i].messageLevel == undefined)
								{
									if(data[i].messageLevel == undefined)
									{
										$("#tbody_page_content_id").empty();
									}
									
									clearIntervalRefreshParseStatus();
									break;
								}
							}
							
							$("#tbody_page_content_id").empty();
							$("#tbody_page_content_id").html(html);
						}
        				
        			},
        		error:function (XMLHttpRequest, textStatus, errorThrown) {
        			
        			//  连续错误5次，认为请求后台失败，清除定时器
        			if(error_count>=5)
        			{
        				clearIntervalRefreshParseStatus();
        				
        				$("#refreshParseStatusTimeId").html("<strong style='color:red'>本次解析已结束，错误次数已达"+error_count+"次，错误信息="+textStatus+"</strong>");	
        			}
        			
        			error_count++;
        			//alertMsg.error(XMLHttpRequest.responseText);
				}
        	});
}

function deletDataBySummaryTime(deletDataUrl,title)
{
	if(deletDataUrl !=undefined )
	{
		alertMsg.confirm(title ,{okCall:function()
        {		
		 	$.ajax(
            {url:deletDataUrl,
        		type: 'post',
        		dataType :'json',
        		success	: function(data, textStatus){
        				if(textStatus == 'success')
        				{
        					alertMsg.correct(data.message);
        				}
        			},
        		error:function (XMLHttpRequest, textStatus, errorThrown) {
        			alertMsg.error(XMLHttpRequest.responseText);
				}
        	});
        }});
	}
}



</script>
	</head>
<div>
		<ul>
			<li>
				<label style="padding-left: 10px">文件导入状态提示信息，页面每<strong><span id="refreshParseStatusTime_lable_id">3</span></strong>秒更新一次状态，离下次更新还剩：<span id="refreshParseStatusTimeId"></span></label>
			</li>
		</ul>
	</div>
<br/>
<div class="pageContent">
	<table class="table" layoutH="100" width="100%">
		<thead>
			<tr>
				<th width="10%">状态</th>
				<th width="79%">内容</th>
				<th  width="10%">操作</th>
			</tr>
		</thead>
		<tbody id="tbody_page_content_id" >
		<!-- 
			<c:forEach var="item" items="${financeFileLogInfos}">
			<tr valign="middle">
				<td>
					<c:if test="${item.messageLevel eq 'INFO'}"><img src="${contextPath}/styles/dwz/themes/css/images/icons/accept.png" style="position: relative; top:3px;" />&nbsp;正常</c:if>
					<c:if test="${item.messageLevel eq 'ERROR'}"><img src="${contextPath}/styles/dwz/themes/css/images/icons/cross.png" style="position: relative; top:3px;"/><span style="color: red">&nbsp;错误</span></c:if>
				</td>
				<td title="${item.message}">${item.message}</td>
				<td title="${item.remark}">${item.remark}</td>
			</tr>
			</c:forEach> -->
		</tbody>
	</table>
</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" style="width: 100px;">关闭</button></div></div></li>
		</ul>
	</div>
</html>