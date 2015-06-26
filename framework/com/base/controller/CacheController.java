/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.controller.CacheManageController.java
 * Class:			CacheManageController
 * Date:			2012-9-14
 * Author:			Vigor
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.log.Log;
import com.base.service.CacheService;
import com.base.util.dwz.AjaxObject;

/** 
 * 	
 * @author 	Vigor
 * Version  1.1.0
 * @since   2012-9-14 上午11:08:15 
 */
@Controller
@RequestMapping("/management/security/cache")
public class CacheController {
	@Autowired
	private CacheService cacheService;
	
	private static final String INDEX = "management/security/cache/index";
	
	@RequiresPermissions("Cache:view")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return INDEX;
	}
	
	@Log(message="进行了缓存清理。")
	@RequiresPermissions(value={"Cache:edit", "Cache:delete"}, logical=Logical.OR)
	@RequestMapping(value="/clear", method=RequestMethod.POST)
	public @ResponseBody String clear() {
		cacheService.clearAllCache();
		
		return AjaxObject.newOk("清除缓存成功！").setCallbackType("").toString();
	}
}
