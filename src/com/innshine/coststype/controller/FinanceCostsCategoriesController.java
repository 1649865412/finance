/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.coststype.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.innshine.coststype.entity.FinanceCostsCategories;
import com.innshine.coststype.service.FinanceCostsCategoriesService;
import com.innshine.coststype.service.FinanceCostsTypeService;

@Controller
@RequestMapping("/management/costsTypeCategories")
public class FinanceCostsCategoriesController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCostsCategoriesController.class);
	
	@Autowired
	private FinanceCostsCategoriesService financeCostsCategoriesService;
	
	@Autowired
	private FinanceCostsTypeService financeCostsTypeService;
	
	private static final String CREATE = "management/finance_costs_categories/create";
	private static final String UPDATE = "management/finance_costs_categories/update";
	private static final String LIST = "management/finance_costs_categories/list";
	private static final String VIEW = "management/finance_costs_categories/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("FinanceCostsCategories:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		mapPutCostsTypes(map);
		return CREATE;
	}
	
	@Log(message = "添加了id={0}")
	@RequiresPermissions("FinanceCostsCategories:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid FinanceCostsCategories financeCostsCategories)
	{
		if(null != financeCostsCategories)	
		{
			financeCostsCategories.setFinanceCostsType(financeCostsTypeService.get(financeCostsCategories.getFinanceCostsId()));
			financeCostsCategoriesService.saveOrUpdate(financeCostsCategories);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeCostsCategories.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadFinanceCostsCategories")
	public FinanceCostsCategories preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			FinanceCostsCategories financeCostsCategories = financeCostsCategoriesService.get(id);
			return financeCostsCategories;
		}
		return null;
	}
	
	@RequiresPermissions("FinanceCostsCategories:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceCostsCategories financeCostsCategories = financeCostsCategoriesService.get(id);
		map.put("financeCostsCategories", financeCostsCategories);
		mapPutCostsTypes(map);
		return UPDATE;
	}

	private void mapPutCostsTypes(Map<String, Object> map)
	{
		map.put("financeCostsTypes", financeCostsTypeService.findAll());
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("FinanceCostsCategories:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadFinanceCostsCategories") FinanceCostsCategories financeCostsCategories)
	{
		if(null != financeCostsCategories)	
		{
			financeCostsCategories.setFinanceCostsType(financeCostsTypeService.get(financeCostsCategories.getFinanceCostsId()));
			financeCostsCategoriesService.saveOrUpdate(financeCostsCategories);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeCostsCategories.getId() }));
		return AjaxObject.newOk("FinanceCostsCategories修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}")
	@RequiresPermissions("FinanceCostsCategories:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		financeCostsCategoriesService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("FinanceCostsCategories删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}")
	@RequiresPermissions("FinanceCostsCategories:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			FinanceCostsCategories financeCostsCategories = financeCostsCategoriesService.get(ids[i]);
			financeCostsCategoriesService.delete(financeCostsCategories.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("FinanceCostsCategories:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<FinanceCostsCategories> specification = DynamicSpecifications.bySearchFilter(request,
				FinanceCostsCategories.class);
		List<FinanceCostsCategories> financeCostsCategoriess = financeCostsCategoriesService.findByExample(
				specification, page);
		
		map.put("page", page);
		map.put("financeCostsCategoriess", financeCostsCategoriess);
		mapPutCostsTypes(map);
		return LIST;
	}
	
	@RequiresPermissions("FinanceCostsCategories:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceCostsCategories financeCostsCategories = financeCostsCategoriesService.get(id);
		map.put("financeCostsCategories", financeCostsCategories);
		mapPutCostsTypes(map);
		return VIEW;
	}
}
