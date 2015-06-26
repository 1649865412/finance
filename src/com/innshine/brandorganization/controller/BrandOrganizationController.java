/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brandorganization.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
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
import com.innshine.brandorganization.entity.BrandOrganization;
import com.innshine.brandorganization.service.BrandOrganizationService;

@Controller
@RequestMapping("/management/brand_organization")
public class BrandOrganizationController
{
	private static final Logger log = LoggerFactory.getLogger(BrandOrganizationController.class);
	@Autowired
	private BrandOrganizationService brandOrganizationService;
	
	private static final String CREATE = "management/brand_organization/create";
	private static final String UPDATE = "management/brand_organization/update";
	private static final String LIST = "management/brand_organization/list";
	private static final String VIEW = "management/brand_organization/view";
	
	@RequiresPermissions("BrandOrganization:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}BrandOrganization。")
	@RequiresPermissions("BrandOrganization:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid BrandOrganization brandOrganization)
	{
		brandOrganizationService.saveOrUpdate(brandOrganization);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { brandOrganization.getId() }));
		return AjaxObject.newOk("BrandOrganization添加成功！").toString();
	}
	
	@ModelAttribute("preloadBrandOrganization")
	public BrandOrganization preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			BrandOrganization brandOrganization = brandOrganizationService.get(id);
			return brandOrganization;
		}
		return null;
	}
	
	@RequiresPermissions("BrandOrganization:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		BrandOrganization brandOrganization = brandOrganizationService.get(id);
		map.put("brandOrganization", brandOrganization);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}BrandOrganization的信息。")
	@RequiresPermissions("BrandOrganization:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(
			@Valid @ModelAttribute("preloadBrandOrganization") BrandOrganization brandOrganization)
	{
		brandOrganizationService.saveOrUpdate(brandOrganization);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { brandOrganization.getId() }));
		return AjaxObject.newOk("BrandOrganization修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}BrandOrganization。")
	@RequiresPermissions("BrandOrganization:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		brandOrganizationService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("BrandOrganization删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}BrandOrganization。")
	@RequiresPermissions("BrandOrganization:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			BrandOrganization brandOrganization = brandOrganizationService.get(ids[i]);
			brandOrganizationService.delete(brandOrganization.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("BrandOrganization删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("BrandOrganization:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<BrandOrganization> specification = DynamicSpecifications.bySearchFilter(request,
				BrandOrganization.class);
		List<BrandOrganization> brandOrganizations = brandOrganizationService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("brandOrganizations", brandOrganizations);
		
		return LIST;
	}
	
	@RequiresPermissions("BrandOrganization:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		BrandOrganization brandOrganization = brandOrganizationService.get(id);
		map.put("brandOrganization", brandOrganization);
		return VIEW;
	}
}
