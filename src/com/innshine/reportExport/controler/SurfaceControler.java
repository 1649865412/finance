package com.innshine.reportExport.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.log.Log;
import com.base.util.dwz.Page;
import com.innshine.areainfo.service.AreaInfoService;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.brand.service.BrandsInfoService;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.classify.service.ClassifyInfoService;
import com.innshine.coststype.entity.FinanceCostsType;
import com.innshine.coststype.service.FinanceCostsTypeService;
import com.innshine.reportExport.Constants;
import com.innshine.reportExport.entity.ConditionEntity;
import com.innshine.reportExport.entity.SurFacePageEntity;
import com.innshine.reportExport.reportInterface.Abstrategy;
import com.innshine.reportExport.reportInterface.InterfaceExportImpl;
import com.innshine.reportExport.reportService.PPTServiceImpl;
import com.innshine.reportExport.util.ParamTool;

/**
 * <code>SurfaceControler.java  财务报表导出界面</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author 杨荣忠 时间 2015-1-16 下午04:21:26
 * @version 1.0 </br>最后修改人 无
 */

@Controller
@RequestMapping("/management/surfaceReport")
public class SurfaceControler  
{
	
	private static final Logger log = LoggerFactory
			.getLogger(SurfaceControler.class);

	@Autowired
	AreaInfoService  areaInfoService;
	
	@Autowired
	FinanceCostsTypeService financeCostsTypeService;
	
	@Autowired
	ClassifyInfoService  classifyInfoService;
	
	@Autowired
	BrandsInfoService  brandsInfoService;
	
	private static final String INDEX = "management/financeSurfacePage/index";
	private static final String INDEX2 = "management/financeSurfacePage/MyJsp";

	
	
	/**
	 * 财务报表导出页面
	 * 
	 */
	@Log(message = "进入财务报表导出页面")
	@RequestMapping(value = "/index")
	public String list(HttpServletRequest request, Page page,
			Map<String, Object> map) throws Exception
	{  
		
	//	System.out.println("进入财务报表导出页面");
		List<BrandsInfo> brandsInfoList =brandsInfoService.findAll();
		List<ClassifyInfo>  classifyInfoList=classifyInfoService.getZhuYinTopClass();//顶部分类
        List<FinanceCostsType> financeCostsTypeList= financeCostsTypeService.findAll();//底部分类
		SurFacePageEntity surFacePageEntity =new SurFacePageEntity(classifyInfoList,brandsInfoList,financeCostsTypeList);
		map.put("surFacePageEntity", surFacePageEntity);
		return INDEX;
	}
	

	/**
	 * 财务报表导出img
	 * 
	 */
	@Log(message = "财务报表导出")
	@RequestMapping(value = "/ExportImg")
	public @ResponseBody
	String exportImg(HttpServletRequest request, Page page,
			Map<String, Object> map, @Valid ConditionEntity conditionEntity)
			throws Exception
	{
		//System.out.println("财务报表导出");
		conditionEntity=ParamTool.chageConditionEntity(conditionEntity);
		String className = ParamTool.checkType(conditionEntity.getStrategyType());
		Abstrategy strategy = (Abstrategy) Class.forName(className)
				.newInstance();
		InterfaceExportImpl interfaceExportImpl = new InterfaceExportImpl(
				strategy, conditionEntity,request);
		String img_path = interfaceExportImpl.reportImg();
		return img_path;
	}
	



	/**
	 * 财务报表导出ppt
	 * 
	 
	@Log(message = "财务报表PPT导出")
	@RequestMapping(value = "/ExportPPT")
	public @ResponseBody
	void exportPPT(HttpServletRequest request,HttpServletResponse response, Page page,
			Map<String, Object> map, String ImgName)
			throws Exception
	{
		System.out.println("财务报表PPT导出");
		new PPTServiceImpl().exportPPTFile(ImgName,response,request);
	}
	*/
	

	/**
	 * 财务报表导出ppt
	 * 
	 */
	@Log(message = "财务报表PPT导出")
	@RequestMapping(value = "/ExportPPT")
	public void exportPPT(HttpServletRequest request,HttpServletResponse response, Page page,
			Map<String, Object> map, String ImgName)
			throws Exception
	{
		//System.out.println("财务报表PPT导出");
		new PPTServiceImpl().exportPPTFile(ImgName,response,request);
	}

}
