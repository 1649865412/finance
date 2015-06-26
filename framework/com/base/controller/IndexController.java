/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.controller.IndexController.java
 * Class:			IndexController
 * Date:			2012-8-2
 * Author:			Vigor
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.base.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.SecurityConstants;
import com.base.entity.main.Module;
import com.base.entity.main.Organization;
import com.base.entity.main.Permission;
import com.base.entity.main.User;
import com.base.exception.ServiceException;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.service.ModuleService;
import com.base.service.OrganizationService;
import com.base.service.SummaryCacheService;
import com.base.service.UserService;
import com.base.shiro.ShiroUser;
import com.base.util.dwz.AjaxObject;
import com.innshine.common.brand.BrandConfigResource;
import com.innshine.common.utils.BrandChangLocalThread;
import com.utils.Exceptions;
import com.utils.SecurityUtils;
import com.utils.SystemConst;

/**
 * 
 * @author Vigor Version 1.1.0
 * @since 2012-8-2 下午5:45:57
 */
@Controller
@RequestMapping("/management/index")
public class IndexController
{
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private SummaryCacheService summaryCacheService;
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final String INDEX = "management/index/index";
	private static final String SUBMENU = "management/index/subMenu";
	private static final String UPDATE_PASSWORD = "management/index/updatePwd";
	private static final String UPDATE_BASE = "management/index/updateBase";
	
	@Log(message = "{0}登录了系统。")
	@RequiresUser
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ServletRequest request, Map<String, Object> map)
	{
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		Module menuModule = getMenuModule(SecurityUtils.getSubject());
		User u = shiroUser.getUser();
		
		if (u == null)
		{
			return SystemConst.login;
		}
		
		Organization organization = u.getOrganization();
		String organizationId = null;
		if (organization == null)
		{
			return SystemConst.login;
		}else{
			//管理人员
			if(organization.getId() == 2 || organization.getId() == 9){
				organizationId = null;
			}else{
				organizationId = String.valueOf(organization.getId()); 
			} 
			map.put("organizationId", organizationId);
		}
		
		map.put(SecurityConstants.LOGIN_USER, u);
		map.put("menuModule", menuModule);
		List<Module> menuList = menuModule.getChildren();
		if (menuList.size() > 0)
		{
			map.put("firstMenuModule", menuList.get(0));
		}
		
		map.put("brandOrganizations", BrandConfigResource.getInstance().getConfig());
		 
		map.put("localThread_organizationId", organization.getId());
		map.put("curr_organizationId", organization.getSwitchId());
	//	map.put("summarys", summaryCacheService.getSummary());
		 
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { shiroUser.getLoginName() }));
		return INDEX;
	}
	
	private Module getMenuModule(Subject subject)
	{
		Module rootModule = moduleService.getTree();
		
		check(rootModule, subject);
		return rootModule;
	}
	
	private void check(Module module, Subject subject)
	{
		List<Module> list1 = new ArrayList<Module>();
		for (Module m1 : module.getChildren())
		{
			// 只加入拥有show权限的Module
			if (subject.isPermitted(m1.getSn() + ":" + Permission.PERMISSION_SHOW))
			{
				check(m1, subject);
				list1.add(m1);
			}
		}
		module.setChildren(list1);
	}
	
	@RequiresUser
	@RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
	public String preUpdatePassword()
	{
		return UPDATE_PASSWORD;
	}
	
	@Log(message = "{0}修改了密码。")
	@RequiresUser
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public @ResponseBody String updatePassword(ServletRequest request, String plainPassword, String newPassword,
			String rPassword)
	{
		User user = SecurityUtils.getLoginUser();
		
		if (newPassword != null && newPassword.equals(rPassword))
		{
			user.setPlainPassword(plainPassword);
			try
			{
				userService.updatePwd(user, newPassword);
			}
			catch (ServiceException e)
			{
				LogUitls.putArgs(LogMessageObject.newIgnore());// 忽略日志
				return AjaxObject.newError(e.getMessage()).setCallbackType("").toString();
			}
			LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { user.getUsername() }));
			return AjaxObject.newOk("修改密码成功！").toString();
		}
		
		return AjaxObject.newError("修改密码失败！").setCallbackType("").toString();
	}
	
	@RequiresUser
	@RequestMapping(value = "/updateBase", method = RequestMethod.GET)
	public String preUpdateBase(Map<String, Object> map)
	{
		map.put(SecurityConstants.LOGIN_USER, SecurityUtils.getLoginUser());
		return UPDATE_BASE;
	}
	
	@Log(message = "{0}修改了详细信息。")
	@RequiresUser
	@RequestMapping(value = "/updateBase", method = RequestMethod.POST)
	public @ResponseBody String updateBase(User user, ServletRequest request)
	{
		User loginUser = SecurityUtils.getLoginUser();
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());
		
		userService.saveOrUpdate(loginUser);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { user.getUsername() }));
		return AjaxObject.newOk("修改详细信息成功！").toString();
	}
	@RequiresUser
	@RequestMapping(value = "/retrievalSubMenu/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String retrievalSubMenu(@PathVariable Long id, Map<String, Object> map)
	{
		String subMenuKey = "subMenus";
		Module menuModule = getMenuModule(SecurityUtils.getSubject());
		List<Module> menus = menuModule.getChildren();
		for (Module m : menus)
		{
			if (m.getId().equals(id))
			{
				map.put(subMenuKey, m.getChildren());
			}
		}
		if (map.get(subMenuKey) == null)
		{
			return SystemConst.error404;
		}
		return SUBMENU;
	}
	
	@Log(message = "{0}切换品牌")
	@RequestMapping(value = "/changBrand", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody void changBrand(ServletRequest request, String organizationId)
	{
		// try
		// {
		// BrandChangLocalThread.removeOrganizationId();
		// BrandChangLocalThread.setOrganizationId(organizationId);
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		
		if (null != shiroUser)
		{
			User user = shiroUser.getUser();
			
			if (null != user)
			{
				Organization organization = user.getOrganization();
				
				if (null != organization)
				{
					if (StringUtils.isNotBlank(organizationId))
					{
						try
						{
							organization.setSwitchId(Long.parseLong(organizationId));
						}
						catch (Exception e)
						{
							log.error(Exceptions.getStackTraceAsString(e));
						}
					}
					else
					{
						organization.setSwitchId(null);
					}
				}
			}
		}
		
	}
}
