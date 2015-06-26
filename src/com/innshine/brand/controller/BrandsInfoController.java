/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brand.controller;

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
import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.brand.service.BrandsInfoService;

@Controller
@RequestMapping("/management/brandInfo")
public class BrandsInfoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandsInfoController.class);
	@Autowired
	private BrandsInfoService brandsInfoService;
	
	@Autowired
	private AreaInfoService areaInfoService;
	
	private static final String CREATE = "management/brand_info/create";
	private static final String UPDATE = "management/brand_info/update";
	private static final String LIST = "management/brand_info/list";
	private static final String VIEW = "management/brand_info/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("BrandsInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		addResponseAreaInfos(map);
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("BrandsInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid BrandsInfo brandsInfo)
	{
		modifyAreaInfo(brandsInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { brandsInfo.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadBrandsInfo")
	public BrandsInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			BrandsInfo brandsInfo = brandsInfoService.get(id);
			return brandsInfo;
		}
		return null;
	}
	
	@RequiresPermissions("BrandsInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		BrandsInfo brandsInfo = brandsInfoService.get(id);
		addResponseAreaInfos(map);
		map.put("brandsInfo", brandsInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("BrandsInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(@Valid @ModelAttribute("preloadBrandsInfo") BrandsInfo brandsInfo)
	{
		modifyAreaInfo(brandsInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { brandsInfo.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}

	private void modifyAreaInfo(BrandsInfo brandsInfo)
	{
		if (null != brandsInfo)
		{	
			if(brandsInfo.getAreaId() != null)
			{
				brandsInfo.setAreaInfo(areaInfoService.get(brandsInfo.getAreaId()));
			}
			
			brandsInfoService.saveOrUpdate(brandsInfo);
		}
	}
	
	@Log(message = "删除了id={0}")
	@RequiresPermissions("BrandsInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id)
	{
		brandsInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}。")
	@RequiresPermissions("BrandsInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			BrandsInfo brandsInfo = brandsInfoService.get(ids[i]);
			brandsInfoService.delete(brandsInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("BrandsInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<BrandsInfo> specification = DynamicSpecifications.bySearchFilter(request, BrandsInfo.class);
		List<BrandsInfo> brandsInfos = brandsInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("brandsInfos", brandsInfos);
		addResponseAreaInfos(map);
		
		return LIST;
	}
	
	private void addResponseAreaInfos(Map<String, Object> map)
	{
		map.put("areaInfos", areaInfoService.findAll());
	}
	
	@RequiresPermissions("BrandsInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		BrandsInfo brandsInfo = brandsInfoService.get(id);
		map.put("brandsInfo", brandsInfo);
		addResponseAreaInfos(map);
		return VIEW;
	}
}
