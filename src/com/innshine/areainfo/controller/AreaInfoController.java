/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.areainfo.controller;

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
import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.areainfo.service.AreaInfoService;

@Controller
@RequestMapping("/management/areaInfo")
public class AreaInfoController
{
	private static final Logger log = LoggerFactory.getLogger(AreaInfoController.class);
	
	@Autowired
	private AreaInfoService areaInfoService;
	
	private static final String CREATE = "management/area_info/create";
	private static final String UPDATE = "management/area_info/update";
	private static final String LIST = "management/area_info/list";
	private static final String VIEW = "management/area_info/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("AreaInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("AreaInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid AreaInfo areaInfo)
	{
		areaInfoService.saveOrUpdate(areaInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { areaInfo.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadAreaInfo")
	public AreaInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			AreaInfo areaInfo = areaInfoService.get(id);
			return areaInfo;
		}
		return null;
	}
	
	@RequiresPermissions("AreaInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		AreaInfo areaInfo = areaInfoService.get(id);
		map.put("areaInfo", areaInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("AreaInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@Valid @ModelAttribute("preloadAreaInfo") AreaInfo areaInfo)
	{
		areaInfoService.saveOrUpdate(areaInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { areaInfo.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}。")
	@RequiresPermissions("AreaInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id)
	{
		areaInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}。")
	@RequiresPermissions("AreaInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			AreaInfo areaInfo = areaInfoService.get(ids[i]);
			areaInfoService.delete(areaInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("AreaInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<AreaInfo> specification = DynamicSpecifications.bySearchFilter(request, AreaInfo.class);
		List<AreaInfo> areaInfos = areaInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("areaInfos", areaInfos);
		
		return LIST;
	}
	
	@RequiresPermissions("AreaInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		AreaInfo areaInfo = areaInfoService.get(id);
		map.put("areaInfo", areaInfo);
		return VIEW;
	}
}
