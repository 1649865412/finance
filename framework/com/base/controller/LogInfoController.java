/**
 *  code generation
 */
package com.base.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.entity.main.LogInfo;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.service.LogInfoService;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/management/security/logInfo")
public class LogInfoController {

	@Autowired
	private LogInfoService logInfoService;
	
	private static final String LIST = "management/security/logInfo/list";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@Log(message="删除了{0}条日志。")
	@RequiresPermissions("LogInfo:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		AjaxObject ajaxObject = new AjaxObject("删除日志成功！");
		for (Long id : ids) {
			logInfoService.delete(id);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{ids.length}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}

	@RequiresPermissions("LogInfo:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<LogInfo> specification = DynamicSpecifications.bySearchFilter(request, LogInfo.class);
		List<LogInfo> logInfos = logInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("logInfos", logInfos);

		return LIST;
	}
}
