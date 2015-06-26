//前端判断采取的导出图策略类型
var strategyType = "";
var path = "";

//赋予一个js的全局变量
function getPath(urlpath) {
	path = urlpath;
	//  alert("路径是："+path);
}

//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return projectName;
}

//文件上传
function reportDataImport() {
	$.pdialog.open(getRootPath() + "/management/reportParse/upload",
			"finace_excel_report_import", "文件上传", {
				max : false,
				mask : true,
				resizable : true,
				drawable : true,
				fresh : true
			});
}

function reportSubmit() {
	// alert("开始导出图")
	var type = exportType();
	// alert("导出类型："+type);
	typeCheck(type);
}

//导出ppt
function reportPPT() {
	var imgName = $("#reportImgName").val();
	//	alert("imgName"+imgName);
	if (!imgName == "") {
		$('#formPPTId').submit();
	} else {
		alertMsg.warn("请先导图！")
	}
}

function exportType() {
	return $('input[name="type"]:checked').val();
}

function typeCheck(type) {
	var action = true;
	var item_num = itemCheck();
	var item_class_num = itemClassCheck();
	var typeImg = $('input[name="typeImg"]:checked').val();
	//	alert("item_num"+item_num);
	//	alert("item_class_num"+item_class_num);
	//	alert("type:"+type);
	switch (type) {
	case "1":
		action = tongbi(item_num, item_class_num, action, typeImg);//同比
		break;
	case "2":
		action = huangbi(item_num, item_class_num, action, typeImg); //环比
		break;
	case "3":
		action = zhanbi(item_num, item_class_num, action, typeImg);//占比
		break;
	case "4":
		action = areabi(item_num, item_class_num, action, typeImg); //地区比
		break;
	}
	if (action) {
		dowork();
	}
}

//同比条件判断(单项目多维度)（单维度多项目）
function tongbi(item_num, item_class_num, action, typeImg) {
	if (item_num == 1 && item_class_num > 0 && typeImg != 3) {
		strategyType = 1;
	} else if (item_num > 0 && item_class_num == 1 && typeImg != 3) {
		strategyType = 2;
	} else {
		action = false;
		alertMsg
				.warn("你所选择的是同比导出，目前支持：单项目多维度与单维度多项目，即：至多一个项目，至少一个分类或者至多一个分类，至少一个项目组合，且导出类型需为柱形图或折线图");
		//  message="你所选择的是同比导出，目前支持：单项目多维度与单维度多项目，即：至多一个项目，至少一个分类或者至多一个分类，至一个项目组合";
	}
	return action;
}

//环比条件判断(单项目多维度)（单维度多项目）
function huangbi(item_num, item_class_num, action, typeImg) {
	if (item_num == 1 && item_class_num > 0 && typeImg != 3) {
		strategyType = 3;
	} else if (item_num > 0 && item_class_num == 1 && typeImg != 3) {
		strategyType = 4;
	} else {
		action = false;
		alertMsg
				.warn("你所选择的是环比导出，目前支持：单项目多维度与单维度多项目，即：至多一个项目，至少一个分类或者至多一个分类，至少一个项目组合，且导出类型需为柱形图或折线图");
		//  message="你所选择的是环比导出，目前支持：单项目多维度与单维度多项目，即：至多一个项目，至少一个分类或者至多一个分类，至一个项目组合" ;
	}
	return action;
}

//占比条件判断(单项目多维度)
function zhanbi(item_num, item_class_num, action, typeImg) {
	if (item_num == 1 && item_class_num > 0 && typeImg == 3) {
		strategyType = 5;
	} else {
		action = false;
		alertMsg.warn("你所选择的是占比导出，目前支持：单项目多维度，即：至多一个项目，至少一个分类组合，且导出类型需为饼状图");
		//message="你所选择的是占比导出，目前支持：单项目多维度，即：至多一个项目，至少一个分类组合";
	}
	return action;
}

//地区比条件判断(单项目多维度)
function areabi(item_num, item_class_num, action, typeImg) {
	if (item_class_num > 0 && typeImg != 3) {
		strategyType = 6;
	} else {
		action = false;
		alertMsg.warn("你所选择的是地区比导出，即至少一个分类，且导出类型需为柱形图或折线图");
		//message="你所选择的是地区比导出，即至少一个分类";
	}
	return action;
}

//异步提交，获取图片，更新界面
function dowork() {
	//alert("进入IMG方法:"+valueArrayToStr($('input[name="itemClassTwo"]:checked')));
	$
			.post(
					getRootPath() + "/management/surfaceReport/ExportImg",
					{
						//导出项目
						item : valueArrayToStr($('input[name="item"]:checked')),
						//导出一级分类
						itemClassOne : valueArrayToStr($('input[name="itemClassOne"]:checked')),
						//导出二级分类
						itemClassTwo : valueArrayToStr($('input[name="itemClassTwo"]:checked')),
						//导出三级分类
						itemClassThird : valueArrayToStr($('input[name="itemClassThird"]:checked')),
						//导出四级分类
						itemClassFour : valueArrayToStr($('input[name="itemClassFour"]:checked')),
						//导出类型 
						type : $('input[name="type"]:checked').val(),
						//导出图片类型
						typeImg : $('input[name="typeImg"]:checked').val(),
						//同比周期
						circle : $('#circle').val(),
						//开始时间
						beginTime : $('input[name="beginTime"]').val(),
						//结束时间
						endTime : $('input[name="endTime"]').val(),
						//前端判断采取的导出图策略类型
						strategyType : strategyType,
						//占比，是否有求营收入的比
						checkProportionAll : zhanbiCheck()
					}, function(data, status) {
						//  alert("数据：" + data + "\n状态：" + status);
					if (status == "success") {
						//图片赋值
						$("#reportImg").attr("src", data);
						//图片隐藏域赋值，为方便导出ppt
						$("#reportImgName").val(getImgName(data));
						// alert("reportImgName:"+$("#reportImgName").val());
					}
				});
}

//返回选择的item个数
function itemCheck() {
	var item_num = $('input[name="item"]:checked').length;
	// alert("项目内容："+valueArrayToStr($('input[name="item"]:checked')));
	return item_num;
}

//返回选择的分类总个数
function itemClassCheck() {
	var item_class_num = $('input[name="itemClassOne"]:checked').length
			+ $('input[name="itemClassTwo"]:checked').length
			+ $('input[name="itemClassThird"]:checked').length
			+ $('input[name="itemClassFour"]:checked').length;
	return item_class_num;
}

//数组转字符串
function valueArrayToStr(arrayValue) {
	var result = new Array();
	var str = "";
	$.each(arrayValue, function(n, value) {
		result.push($(this).attr("value"));
	})
	str = result.join(",");
	return str;
}

//占比，是否有求营收入的比
function zhanbiCheck() {
	var str = valueArrayToStr($('input[name="itemClassThird"]:checked'));
	var flag = 0;
	if (str.indexOf("主营业务收入") >= 0) {
		flag = 1;
	}
	return flag;
}

//获取图片名字，为后面导出ppt做准备
function getImgName(imgUrl) {
	imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
	return imgName;
}

function hideTop() {
	$("#header", parent.document).hide();
	$("#leftside", parent.document).css("top", "0px");
	$("#container", parent.document).css("top", "0px");
	$("#navTab-panelID", parent.document).css("height", "530px");
	$("#topHideButton").html("显示顶部");

}

function showTop() {
	$("#leftside", parent.document).css("top", "140px");
	$("#container", parent.document).css("top", "140px");
	$("#header", parent.document).show();
	$("#navTab-panelID", parent.document).css("height", "500px");
	$("#topHideButton").html("隐藏顶部");
}

function tonbiShow() {
	var type = $('input[name="type"]:checked').val();
	if (type == 1) {
		$("#circle").show();
	} else {
		$("#circle").hide();
	}

}