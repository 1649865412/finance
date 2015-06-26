/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.controller;

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
import com.innshine.classify.entity.ClassifyInfoTwo;
import com.innshine.classify.service.ClassifyInfoService;
import com.innshine.classify.service.ClassifyInfoTwoService;

@Controller
@RequestMapping("/management/classifyInfoTwo")
public class ClassifyInfoTwoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassifyInfoTwoController.class);
	
	@Autowired
	private ClassifyInfoTwoService classifyInfoTwoService;
	
	@Autowired
	private ClassifyInfoService classifyInfoService ;
	
	private static final String CREATE = "management/classify_info_two/create";
	private static final String UPDATE = "management/classify_info_two/update";
	private static final String LIST = "management/classify_info_two/list";
	private static final String VIEW = "management/classify_info_two/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("ClassifyInfoTwo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		map.put("classifyInfos", classifyInfoService.findAll());
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("ClassifyInfoTwo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid ClassifyInfoTwo classifyInfoTwo)
	{
		modifyifClassifyInfo(classifyInfoTwo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { classifyInfoTwo.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadClassifyInfoTwo")
	public ClassifyInfoTwo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			ClassifyInfoTwo classifyInfoTwo = classifyInfoTwoService.get(id);
			return classifyInfoTwo;
		}
		return null;
	}
	
	@RequiresPermissions("ClassifyInfoTwo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		ClassifyInfoTwo classifyInfoTwo = classifyInfoTwoService.get(id);
		map.put("classifyInfoTwo", classifyInfoTwo);
		map.put("classifyInfos", classifyInfoService.findAll());
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("ClassifyInfoTwo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadClassifyInfoTwo") ClassifyInfoTwo classifyInfoTwo)
	{
		modifyifClassifyInfo(classifyInfoTwo);
		
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { classifyInfoTwo.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}

	private void modifyifClassifyInfo(ClassifyInfoTwo classifyInfoTwo)
	{
		if(null != classifyInfoTwo)
		{
			if(classifyInfoTwo.getClassifyInfoId() != null)
			{
				classifyInfoTwo.setClassifyInfo(classifyInfoService.get(classifyInfoTwo.getClassifyInfoId()));
			}
			classifyInfoTwoService.saveOrUpdate(classifyInfoTwo);
		}
	}
	
	@Log(message = "删除了id={0}。")
	@RequiresPermissions("ClassifyInfoTwo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		classifyInfoTwoService.delete(id);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}。")
	@RequiresPermissions("ClassifyInfoTwo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			ClassifyInfoTwo classifyInfoTwo = classifyInfoTwoService.get(ids[i]);
			classifyInfoTwoService.delete(classifyInfoTwo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("ClassifyInfoTwo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<ClassifyInfoTwo> specification = DynamicSpecifications.bySearchFilter(request,
				ClassifyInfoTwo.class);
		List<ClassifyInfoTwo> classifyInfoTwos = classifyInfoTwoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("classifyInfoTwos", classifyInfoTwos);
		map.put("classifyInfos", classifyInfoService.findAll());
		
		return LIST;
	}
	
	@RequiresPermissions("ClassifyInfoTwo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		ClassifyInfoTwo classifyInfoTwo = classifyInfoTwoService.get(id);
		map.put("classifyInfoTwo", classifyInfoTwo);
		return VIEW;
	}
}
