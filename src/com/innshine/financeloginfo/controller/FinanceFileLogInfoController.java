/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.financeloginfo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.innshine.financeloginfo.entity.FinanceFileLogInfo;
import com.innshine.financeloginfo.service.FinanceFileLogInfoService;

@Controller
@RequestMapping("/management/financeFileLogInfo")
public class FinanceFileLogInfoController {
    private static final Logger log = LoggerFactory.getLogger(FinanceFileLogInfoController.class);
    
	@Autowired
	private FinanceFileLogInfoService financeFileLogInfoService;
	
	private static final String CREATE = "management/financeFileLogInfo/create";
	private static final String UPDATE = "management/financeFileLogInfo/update";
	private static final String LIST = "management/financeFileLogInfo/list";
	private static final String VIEW = "management/financeFileLogInfo/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("FinanceFileLogInfo:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}FinanceFileLogInfo。")
	@RequiresPermissions("FinanceFileLogInfo:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid FinanceFileLogInfo financeFileLogInfo) {
		financeFileLogInfoService.saveOrUpdate(financeFileLogInfo);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{financeFileLogInfo.getId()}));
		return AjaxObject.newOk("FinanceFileLogInfo添加成功！").toString();
	}
	
	@ModelAttribute("preloadFinanceFileLogInfo")
	public FinanceFileLogInfo preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			FinanceFileLogInfo financeFileLogInfo = financeFileLogInfoService.get(id);
			return financeFileLogInfo;
		}
		return null;
	}
	
	@RequiresPermissions("FinanceFileLogInfo:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		FinanceFileLogInfo financeFileLogInfo = financeFileLogInfoService.get(id);
		map.put("financeFileLogInfo", financeFileLogInfo);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}FinanceFileLogInfo的信息。")
	@RequiresPermissions("FinanceFileLogInfo:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadFinanceFileLogInfo")FinanceFileLogInfo financeFileLogInfo) {
		financeFileLogInfoService.saveOrUpdate(financeFileLogInfo);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{financeFileLogInfo.getId()}));
		return AjaxObject.newOk("FinanceFileLogInfo修改成功！").toString();
	}

	@Log(message="删除了id={0}FinanceFileLogInfo。")
	@RequiresPermissions("FinanceFileLogInfo:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		financeFileLogInfoService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("FinanceFileLogInfo删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}FinanceFileLogInfo。")
	@RequiresPermissions("FinanceFileLogInfo:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			FinanceFileLogInfo financeFileLogInfo = financeFileLogInfoService.get(ids[i]);
			financeFileLogInfoService.delete(financeFileLogInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("FinanceFileLogInfo删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("FinanceFileLogInfo:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<FinanceFileLogInfo> specification = DynamicSpecifications.bySearchFilter(request, FinanceFileLogInfo.class);
		List<FinanceFileLogInfo> financeFileLogInfos = financeFileLogInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("financeFileLogInfos", financeFileLogInfos);

		return LIST;
	}
	
	@RequiresPermissions("FinanceFileLogInfo:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		FinanceFileLogInfo financeFileLogInfo = financeFileLogInfoService.get(id);
		map.put("financeFileLogInfo", financeFileLogInfo);
		return VIEW;
	}
}
