/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.coststype.controller;

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
import com.innshine.coststype.entity.FinanceCostsType;
import com.innshine.coststype.service.FinanceCostsTypeService;

@Controller
@RequestMapping("/management/costsType")
public class FinanceCostsTypeController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCostsTypeController.class);
	@Autowired
	private FinanceCostsTypeService financeCostsTypeService;
	
	private static final String CREATE = "management/finance_costs_type/create";
	private static final String UPDATE = "management/finance_costs_type/update";
	private static final String LIST = "management/finance_costs_type/list";
	private static final String VIEW = "management/finance_costs_type/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("FinanceCostsType:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("FinanceCostsType:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid FinanceCostsType financeCostsType)
	{
		financeCostsTypeService.saveOrUpdate(financeCostsType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeCostsType.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadFinanceCostsType")
	public FinanceCostsType preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			FinanceCostsType financeCostsType = financeCostsTypeService.get(id);
			return financeCostsType;
		}
		return null;
	}
	
	@RequiresPermissions("FinanceCostsType:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceCostsType financeCostsType = financeCostsTypeService.get(id);
		map.put("financeCostsType", financeCostsType);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("FinanceCostsType:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadFinanceCostsType") FinanceCostsType financeCostsType)
	{
		financeCostsTypeService.saveOrUpdate(financeCostsType);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeCostsType.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}。")
	@RequiresPermissions("FinanceCostsType:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		financeCostsTypeService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}")
	@RequiresPermissions("FinanceCostsType:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			FinanceCostsType financeCostsType = financeCostsTypeService.get(ids[i]);
			financeCostsTypeService.delete(financeCostsType.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("FinanceCostsType:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<FinanceCostsType> specification = DynamicSpecifications.bySearchFilter(request,
				FinanceCostsType.class);
		List<FinanceCostsType> financeCostsTypes = financeCostsTypeService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("financeCostsTypes", financeCostsTypes);
		
		return LIST;
	}
	
	@RequiresPermissions("FinanceCostsType:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceCostsType financeCostsType = financeCostsTypeService.get(id);
		map.put("financeCostsType", financeCostsType);
		return VIEW;
	}
}
