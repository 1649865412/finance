/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.controler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.innshine.reportExport.entity.FinanceFileInfo;
import com.innshine.reportExport.service.FinanceFileInfoService;
import com.innshine.reportExport.util.ExcelFileUtils;

@Controller
@RequestMapping("/management/financeFileInfo")
public class FinanceFileInfoController
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceFileInfoController.class);
	
	@Autowired
	private FinanceFileInfoService financeFileInfoService;
	
	private static final String CREATE = "management/finance_file_info/create";
	private static final String UPDATE = "management/finance_file_info/update";
	private static final String LIST = "management/finance_file_info/list";
	private static final String VIEW = "management/finance_file_info/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("FinanceFileInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}")
	@RequiresPermissions("FinanceFileInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid FinanceFileInfo financeFileInfo)
	{
		financeFileInfoService.saveOrUpdate(financeFileInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeFileInfo.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadFinanceFileInfo")
	public FinanceFileInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			FinanceFileInfo financeFileInfo = financeFileInfoService.get(id);
			return financeFileInfo;
		}
		return null;
	}
	
	@RequiresPermissions("FinanceFileInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceFileInfo financeFileInfo = financeFileInfoService.get(id);
		map.put("financeFileInfo", financeFileInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("FinanceFileInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadFinanceFileInfo") FinanceFileInfo financeFileInfo)
	{
		financeFileInfoService.saveOrUpdate(financeFileInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { financeFileInfo.getId() }));
		return AjaxObject.newOk("修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}")
	@RequiresPermissions("FinanceFileInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		financeFileInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("FinanceFileInfo删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}")
	@RequiresPermissions("FinanceFileInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			FinanceFileInfo financeFileInfo = financeFileInfoService.get(ids[i]);
			financeFileInfoService.delete(financeFileInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("FinanceFileInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<FinanceFileInfo> specification = DynamicSpecifications.bySearchFilter(request,
				FinanceFileInfo.class);
		List<FinanceFileInfo> financeFileInfos = financeFileInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("financeFileInfos", financeFileInfos);
		
		return LIST;
	}
	
	@RequiresPermissions("FinanceFileInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		FinanceFileInfo financeFileInfo = financeFileInfoService.get(id);
		map.put("financeFileInfo", financeFileInfo);
		return VIEW;
	}
	
	@RequestMapping(value = "/download/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public void download(@PathVariable Long id, Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		FinanceFileInfo financeFileInfo = financeFileInfoService.get(id);
		
		if (null != financeFileInfo)
		{
			String fileRelativePath = financeFileInfo.getFileRelativePath();
			if (StringUtils.isNotEmpty(fileRelativePath))
			{
				ExcelFileUtils.excelExport(request, response, fileRelativePath, null, false);
			}
		}
		
	}
}
