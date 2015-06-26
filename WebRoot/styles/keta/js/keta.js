function jumpClick($this, isChecked) {
	var $thisAllchk = $this.next("span[ref='allChk']");
	if (isChecked) {
		$this.attr("class", "button chk checkbox_true_full");
		$thisAllchk.attr("class", "button chk checkbox_false_full");
		$thisAllchk.click();
		$thisAllchk.attr("class", "button chk checkbox_true_full");
	} else {
		$this.attr("class", "button chk checkbox_false_full");
		$thisAllchk.attr("class", "button chk checkbox_true_full");
		$thisAllchk.click();
		$thisAllchk.attr("class", "button chk checkbox_false_full");
	}

	var $tr = $this.parent().parent();
	var id = $tr.attr("id");

	var $cTr = $("tr[pid='" + id + "']");
	var $cTreeChk = $("span[ref='treeChk']", $cTr);
	var $cAllChk = $("span[ref='allChk']", $cTr);

	$cTreeChk.each(function() {
		if (isChecked) {
			$(this).attr("class", "button chk checkbox_true_full");
		} else {
			$(this).attr("class", "button chk checkbox_false_full");
		}
	});

	$cAllChk.each(function() {
		if (isChecked) {
			$(this).attr("class", "button chk checkbox_false_full");
			$(this).click();
			$(this).attr("class", "button chk checkbox_true_full");
		} else {
			$(this).attr("class", "button chk checkbox_true_full");
			$(this).click();
			$(this).attr("class", "button chk checkbox_false_full");
		}
	});

	$cTr.each(function() {
		var $ccTr = $("tr[pid='" + $(this).attr("id") + "']");
		if ($ccTr.length > 0) {
			$ccTr.each(function() {
				var $c = $("span[ref='treeChk']", $ccTr);
				jumpClick($c, isChecked);
			});
		}
	});
}

function initRolePage() {
	// 给全选加入事件
	$(".setAll").click(function() {
		var isChecked = null;

		var chkClass = $(this).attr("class").split(" ")[2];
		if (chkClass.indexOf("true") > -1) {
			isChecked = false;

			$(this).attr("class", "button chk checkbox_false_full setAll");
		} else {
			isChecked = true;

			$(this).attr("class", "button chk checkbox_true_full setAll");
		}

		var $td = $(this).parent().nextAll("td");
		var $inputSpan = $(".inputValueRole", $td);
		$("input[type=checkbox]", $inputSpan).each(function() {
			if (isChecked == true) {
				$(this).attr("checked", "checked");
			} else {
				$(this).removeAttr("checked");
			}
		});
	});
	// 初始化permission
	$(".inputValueRole input[type=checkbox]").click(function() {
		var isChecked = $(this).is(":checked");
		var $td = $(this).parent().parent().prevAll("td");
		var $chk = $(".setAll", $td);
		if (isChecked == false) {
			$chk.attr("class", "button chk checkbox_false_full setAll");
		}
	});
	//初始化treeTable
	var option = {
		theme : 'default',
		expandLevel : 3
	};
	$('.treeTable').treeTable(option);

	//初始化checkbox_
	$("span[class*='checkbox_']")
			.each(
					function() {
						$(this).mouseover(
								function() {
									var chkClass = $(this).attr("class").split(
											" ")[2];
									if (chkClass.indexOf("_focus") < 0) {
										if ($(this).attr("class").indexOf(
												"setAll") > -1) {
											$(this).attr(
													"class",
													"button chk " + chkClass
															+ "_focus"
															+ " setAll");
										} else {
											$(this).attr(
													"class",
													"button chk " + chkClass
															+ "_focus");
										}
									}
								});

						$(this)
								.mouseout(
										function() {
											var chkClass = $(this)
													.attr("class").split(" ")[2];
											if (chkClass.indexOf("_focus") > -1) {
												if ($(this).attr("class")
														.indexOf("setAll") > -1) {
													$(this)
															.attr(
																	"class",
																	"button chk "
																			+ chkClass
																					.substring(
																							0,
																							chkClass.length - 6)
																			+ " setAll");
												} else {
													$(this)
															.attr(
																	"class",
																	"button chk "
																			+ chkClass
																					.substring(
																							0,
																							chkClass.length - 6));
												}
											}
										});

						// 给treeTable的chk添加click事件
						if ($(this).attr("class").indexOf("setAll") < 0) {
							$(this)
									.click(
											function() {
												var isChecked = null;
												var chkClass = $(this).attr(
														"class").split(" ")[2];
												if (chkClass.indexOf("true") > -1) {
													isChecked = false;
													$(this)
															.attr("class",
																	"button chk checkbox_false_full");
												} else {
													isChecked = true;
													$(this)
															.attr("class",
																	"button chk checkbox_true_full");
												}

												var $thisAllchk = $(this).next(
														"span[ref='allChk']");
												if (isChecked) {
													$thisAllchk
															.attr("class",
																	"button chk checkbox_false_full");
													$thisAllchk.click();
													$thisAllchk
															.attr("class",
																	"button chk checkbox_true_full");
												} else {
													$thisAllchk
															.attr("class",
																	"button chk checkbox_true_full");
													$thisAllchk.click();
													$thisAllchk
															.attr("class",
																	"button chk checkbox_false_full");
												}

												jumpClick($(this), isChecked);
											});
						}
					});
}
$.fn.fillform = function(data, moduleName) {
	var tmpObj = {};
	if (data != null) {
		for ( var o in data) {
			if (moduleName == null) {
				tmpObj[o] = data[o];
			} else {
				tmpObj[moduleName + "." + o] = data[o];
			}
		}
		this.find("input,textarea,select").each(function() {
			var type = $(this).attr("type");
			if (type != "button") {
				var tmpname = $(this).attr("name");
				if (type == "radio" || type == "checkbox") {
					var v = $(this).val();
					if (v == tmpObj[tmpname]) {
						$(this).attr("checked", true);
					} else {
						$(this).removeAttr("checked");
					}
				} else {
					if (tmpObj[tmpname] != null) {
						$(this).val(tmpObj[tmpname]);
					}
				}
			}
		});
	}
};
$.fn.fetchData = function() {
	var dataobj = {};
	this
			.find("input,textarea,select")
			.each(
					function() {
						var type = $(this).attr("type");
						if (type != "button" && type != "image"
								&& type != "submit" && type != "reset") {
							var tmpname = $(this).attr("name");
							var v = $(this).val();
							if (tmpname != null && tmpname != undefined
									&& v != "" && $.trim(v).length > 0) {
								dataobj["check_value"] = 1;
								if (type == "radio") {
									if ($(this).attr("checked") == "checked") {
										dataobj[tmpname] = v;
									}
								} else {
									if (type == "checkbox") {
										if ($(this).attr("checked") == "checked") {
											if (dataobj[tmpname] != null
													&& dataobj[tmpname] != "undefined") {
												dataobj[tmpname] = dataobj[tmpname]
														+ "," + v;
											} else {
												dataobj[tmpname] = v;
											}
										}
									} else {
										if (dataobj[tmpname] != null
												&& dataobj[tmpname] != "undefined") {
											dataobj[tmpname] = dataobj[tmpname]
													+ "," + v;
										} else {
											dataobj[tmpname] = v;
										}
									}
								}
							}
						}
					});
	return dataobj;
};
$.fn.resetform = function() {
	this.find("input,textarea").each(function() {
		var type = $(this).attr("type");
		if (type == "radio" || type == "checkbox") {
			$(this).removeAttr("checked");
		} else {
			if (type != "button") {
				$(this).val("");
			}
		}
	});
	this.find("select").each(function() {
		$(this).get(0).selectedIndex = 1;
	});
};
/**
 * 打开窗口
 */
function openWin(url, winname, w, h) {
	if (w == null || h == null) {
		h = jQuery(window).height();
		w = jQuery(window).width();
	}
	if (winname == null) {
		winname = "newname";
	}
	var retv = window
			.open(
					url,
					winname,
					'height='
							+ h
							+ ', width='
							+ w
							+ ', top=0,left=0, toolbar=no, menubar=no, scrollbars=no, location=no, status=no');
	return retv;
}
function closeWin() {

	window.close();
}

/*******************************************************************************
 * string 方法 start
 */

String.prototype.isNum = function() {
	var reg = /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/g;
	return reg.test(this);
};
String.prototype.isIntNum = function() {
	var reg = /^[1-9]\d*$/g;
	return reg.test(this);
};
String.prototype.isCheckNum = function() {
	var reg = /^(0|[1-9]\d*)$/g;
	return reg.test(this);
};
String.prototype.isPrice = function() {
	var reg = /^[0-9]\d*(\.\d*)?$/g;
	return reg.test(this);
};
String.prototype.isPhone = function() {
	var reg = /^(((0[0-9]{2,3})([2-9][0-9]{6,7})+(\-[0-9]{1,4})?)|(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8}))$/i;
	return reg.test(this);
};
String.prototype.trim = function() {
	var reg = /\s*/g;
	return this.replace(reg, "");
};
String.prototype.toJson = function() {
	return eval("(" + this + ")");
};
String.prototype.notBlank = function() {
	var timpStr = this.trim();
	return (timpStr.length > 0);
};
String.prototype.blank = function() {
	var timpStr = this.trim();
	return (timpStr.length == 0);
};
String.prototype.toInt = function() {
	var timpStr = this.trim();
	return parseInt(timpStr, 10);
};
String.prototype.toFloat = function() {
	var timpStr = this.trim();
	return parseFloat(timpStr, 10).toFixed(2);
};

// 将字符串转成字节
String.prototype.getBytesValue = function() {
	var codes = 0;
	for (i = 0; i < this.length; i++) {
		iCode = this.charCodeAt(i);
		codes += iCode;
	}
	return codes;
};
String.prototype.getColor = function() {
	if (this.blank()) {
		return "fff";
	}
	var codes = this.getBytesValue();
	codes = codes.toString(16);
	if (codes.length > 6) {
		return codes.substring(0, 6);
	} else {
		var append = "";
		var appendLength = 6 - codes.length;
		for ( var i = 0; i < appendLength; i++) {
			append += "f";
		}
		return codes + append;
	}
};

/*******************************************************************************
 * string 方法 end
 */
/**
 * 将参数对象转化为参数字符串 a=c&s=c
 * 
 * @param {}
 *            param ｛a:c,s:c｝
 * @return {String}
 */
function objToParamstr(param) {
	if (param != null && typeof (param) == 'object') {
		var paramStr = "";
		for ( var o in param) {

			if (o != null
					&& typeof (o) == 'string'
					&& (typeof (param[o]) == 'string' || typeof (param[o]) == 'number')) {
				paramStr += o + "=" + param[o] + "&";
			}
		}
		if (paramStr.length > 1) {
			return paramStr.substring(0, paramStr.length - 1);
		}
	}
	return "";
}

/**
 * 将地址后面的参数“” 取掉
 */
function urlshort(url) {
	if (url != null && url.length > 0) {
		var paramm = url.split("&");
		var tmpstr = "";
		for ( var i = 0; i < paramm.length; i++) {
			var ppparam = paramm[i];
			var pps = ppparam.split("=");
			if (pps.length > 1 && pps[1] != "") {
				tmpstr += pps[0] + "=" + pps[1] + "&";
			}
		}
		if (tmpstr.length > 1) {
			return tmpstr.substring(0, tmpstr.length - 1);
		}
		return "";
	}
}

/**
 * 左填充
 * 
 * @param str
 *            需填充的字符串
 * @param len
 *            填充的长度
 * @param repchar
 *            空处填充的字符
 */
function leftpad(str, len, repchar) {
	str = str + "";
	if (str && str.length < len) {
		var relen = len - str.length;
		var tmp = "";
		for ( var i = 0; i < relen; i++) {
			tmp += repchar;
		}
		return tmp + str;
	}
	return str;
}

/**
 * 生成随机字符串，长度传参所得
 * 
 * @return {}
 */
function randomChar(len) {
	var x = "0123456789qwertyuioplkjhgfdsazxcvbnm";
	var tmp = "";
	var leng = len || 5
	for ( var i = 0; i < leng; i++) {
		tmp += x.charAt(Math.ceil(Math.random() * 100000000) % x.length);
	}
	return tmp;
}
// html字符串转义
function htmlEscape(htmlString) {
	htmlString = htmlString.replace(/&/g, '&amp;');
	htmlString = htmlString.replace(/</g, '&lt;');
	htmlString = htmlString.replace(/>/g, '&gt;');
	htmlString = htmlString.replace(/'/g, '&acute;');
	htmlString = htmlString.replace(/"/g, '&quot;');
	htmlString = htmlString.replace(/\|/g, '&brvbar;');
	return htmlString;
}

// 设置Cookie
function setCookie(name, value) {
	var expires = (arguments.length > 2) ? arguments[2] : null;
	document.cookie = name + "=" + encodeURIComponent(value)
			+ ((expires == null) ? "" : ("; expires=" + expires.toGMTString()))
			+ ";path=" + shopxx.base;
}

// 获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name
			+ "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
	} else {
		return null;
	}
}

// 删除cookie
function deleteCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1000 * 60);
	setCookie(name, "", expires);
}

 