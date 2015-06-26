/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.sample.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.sample.entity.Product;
import com.sample.service.ProductService;

@Controller
@RequestMapping("/management/demo/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	private static final String CREATE = "management/demo/product/create";
	private static final String UPDATE = "management/demo/product/update";
	private static final String LIST = "management/demo/product/list";
	private static final String VIEW = "management/demo/product/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("Product:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}产品。")
	@RequiresPermissions("Product:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Product product) {
		productService.saveOrUpdate(product);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{product.getId()}));
		return AjaxObject.newOk("产品添加成功！").toString();
	}
	
	@ModelAttribute("preloadProduct")
	public Product preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Product product = productService.get(id);
			return product;
		}
		return null;
	}
	
	@RequiresPermissions("Product:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Product product = productService.get(id);
		map.put("product", product);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}产品的信息。")
	@RequiresPermissions("Product:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadProduct")Product product) {
		productService.saveOrUpdate(product);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{product.getId()}));
		return AjaxObject.newOk("产品修改成功！").toString();
	}

	@Log(message="删除了id={0}产品。")
	@RequiresPermissions("Product:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		productService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("产品删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}产品。")
	@RequiresPermissions("Product:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Product product = productService.get(ids[i]);
			productService.delete(product.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("产品删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Product:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Product> specification = DynamicSpecifications.bySearchFilter(request, Product.class);
		List<Product> products = productService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("products", products);

		return LIST;
	}
	
	@RequiresPermissions("Product:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Product product = productService.get(id);
		map.put("product", product);
		return VIEW;
	}
}
