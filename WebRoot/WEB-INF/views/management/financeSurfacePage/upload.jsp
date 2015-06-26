<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script type="text/javascript">

</script>
<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>

<div class="pageContent" layoutH="0">
<div class="pageFormContent">
<input id="file_upload" type="file"
	uploaderOption="{
        'auto':false,
        'successTimeout':300,
        'swf':'${contextPath}/styles/uploadify/scripts/uploadify.swf',
        'overrideEvents' : ['onDialogClose'],
        'queueID':'fileQueue',
        'fileObjName':'files',
        'uploader':'${contextPath}/management/reportParse/upload;jsessionid=<%=session.getId()%>',
        'buttonImage':'${contextPath}/styles/uploadify/img/add.jpg',
		'buttonClass':'my-uploadify-button',
        'width':'102',
        'height':'28',
        'removeComplete': false,
        'fileTypeDesc':'支持的格式：xls,xlsx',
        'fileTypeExts':'*.xlsx;*.xls',
        'fileSizeLimit':'20MB',
        'queueSizeLimit' : 1,
        'onSelectError':function(file, errorCode, errorMsg){
            switch(errorCode) {
                case -100:
                    alertMsg.error('上传的文件数量已经超出系统限制的'+$('#file_upload').uploadify('settings','queueSizeLimit')+'个文件！');
                    break;
                case -110:
                    alertMsg.error('文件 ['+file.name+'] 大小超出系统限制的'+$('#file_upload').uploadify('settings','fileSizeLimit')+'大小！');
                    break;
                case -120:
                    alertMsg.error('文件 ['+file.name+'] 大小异常！');
                    break;
                case -130:
                    alertMsg.error('文件 ['+file.name+'] 类型不正确！支持的格式：xls,xlsx');
                    break;
            }
        },
        'onFallback':function(){
            alertMsg.error('您未安装FLASH控件，无法上传！请安装FLASH控件后再试。');
        },
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
        	if (errorCode == 403) {
        		forbidden();
        	}
           alertMsg.error(file.name + '上传失败: ' + errorMsg + errorString);
        },
         onUploadStart:function(file){
           //	alert('文件开始上传： ' + file.name)
         },
        onUploadSuccess : function(file, data, response) {
		$.pdialog.closeCurrent();
        $.ajax(
            {url:'${contextPath}/management/reportParse/parseData?fileName='+encodeURIComponent(encodeURIComponent(file.name))+'&jsessionid=<%=session.getId()%>',
        		type: 'post',
        		dataType :'text',
        		cache:false, 
       			async:true, 
        		success	: function(data, textStatus){
        			//alertMsg.confirm('导入完成，请选择是否关闭当前窗口！ ' ,{okCall:function()
        			//{
        			//		$.pdialog.closeCurrent(); 
        			//}});
        			//alertMsg.correct('文件上传成功，准备开始解析.' );
					$.pdialog.open('${contextPath }/management/reportParse/refreshParseStatus?jsessionid=<%=session.getId()%>', 'finace_excel_report_import_status', '上传文件解析状态',{width:800,height:450,max:true,mask:true,resizable:true,drawable:true,fresh:true});
        			},
        		error:function (XMLHttpRequest, textStatus, errorThrown) {
        			alertMsg.error(XMLHttpRequest.responseText);
				}
        	});
        }
	}"
/>



<div id="fileQueue" class="fileQueue"></div> 
<input  type="image" src="${contextPath}/styles/uploadify/img/upload.jpg" onclick="$('#file_upload').uploadify('upload', '*');"/>
<input type="image" src="${contextPath}/styles/uploadify/img/cancel.jpg" onclick="$('#file_upload').uploadify('cancel', '*');"/>
</div>
</div>
