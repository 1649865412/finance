/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.controller;

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
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.classify.service.ClassifyInfoService;
import com.innshine.coststype.service.FinanceCostsCategoriesService;

@Controller
@RequestMapping("/management/classifyInfo")
public class ClassifyInfoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyInfoController.class);
	@Autowired
	private ClassifyInfoService classifyInfoService;
	
	@Autowired
	private FinanceCostsCategoriesService financeCostsCategoriesService;
	
	private static final String CREATE = "management/classify_info/create";
	private static final String UPDATE = "management/classify_info/update";
	private static final String LIST = "management/classify_info/list";
	private static final String VIEW = "management/classify_info/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("ClassifyInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		mapPutCostsCategoriess(map);
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("ClassifyInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid ClassifyInfo classifyInfo)
	{
		if(null != classifyInfo)
		{
			classifyInfo.setFinanceCostsCategories(financeCostsCategoriesService.get(classifyInfo.getFinanceCostsCategoriesId()));
		}
		
		classifyInfoService.saveOrUpdate(classifyInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { classifyInfo.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadClassifyInfo")
	public ClassifyInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			ClassifyInfo classifyInfo = classifyInfoService.get(id);
			return classifyInfo;
		}
		return null;
	}
	
	@RequiresPermissions("ClassifyInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		ClassifyInfo classifyInfo = classifyInfoService.get(id);
		map.put("classifyInfo", classifyInfo);
		mapPutCostsCategoriess(map);
		return UPDATE;
	}

	private void mapPutCostsCategoriess(Map<String, Object> map)
	{
		map.put("financeCostsCategoriess", financeCostsCategoriesService.findAll());
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("ClassifyInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadClassifyInfo") ClassifyInfo classifyInfo)
	{
		if(null != classifyInfo)
		{
			classifyInfo.setFinanceCostsCategories(financeCostsCategoriesService.get(classifyInfo.getFinanceCostsCategoriesId()));
		}
		
		classifyInfoService.saveOrUpdate(classifyInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { classifyInfo.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}。")
	@RequiresPermissions("ClassifyInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		classifyInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}。")
	@RequiresPermissions("ClassifyInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			ClassifyInfo classifyInfo = classifyInfoService.get(ids[i]);
			classifyInfoService.delete(classifyInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ClassifyInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<ClassifyInfo> specification = DynamicSpecifications.bySearchFilter(request, ClassifyInfo.class);
		List<ClassifyInfo> classifyInfos = classifyInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("classifyInfos", classifyInfos);
		mapPutCostsCategoriess(map);
		return LIST;
	}
	
	@RequiresPermissions("ClassifyInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		ClassifyInfo classifyInfo = classifyInfoService.get(id);
		map.put("classifyInfo", classifyInfo);
		return VIEW;
	}
}
