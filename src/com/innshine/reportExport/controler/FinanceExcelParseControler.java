package com.innshine.reportExport.controler;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.shiro.ShiroUser;
import com.base.util.dwz.AjaxObject;
import com.innshine.financeloginfo.entity.FinanceFileLogInfo;
import com.innshine.financeloginfo.service.FinanceFileLogInfoService;
import com.innshine.reportExport.entity.CellSummaryInfo;
import com.innshine.reportExport.entity.RowSummaryInfo;
import com.innshine.reportExport.service.CellSummaryInfoService;
import com.innshine.reportExport.service.FinanceExcelParseService;
import com.innshine.reportExport.service.FinanceExcelParseServiceConstans;
import com.innshine.reportExport.service.FinanceExcelParseThread;
import com.innshine.reportExport.service.RowSummaryInfoService;
import com.innshine.reportExport.util.ExcelFileUtils;
import com.innshine.reportExport.util.LogInfoUserUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 财务报表Excel文件导入Action <code>FinanceExcelParseControler.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

@Controller
@RequestMapping("/management/reportParse")
public class FinanceExcelParseControler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExcelParseControler.class);
	
	/**
	 * 文件上传页面
	 */
	private static final String UPLOAD = "management/financeSurfacePage/upload";
	
	/**
	 * 文件解析状态页面
	 */
	private static final String PARSE_STATUS = "management/financeSurfacePage/file_import_status";
	
	@Autowired
	private FinanceExcelParseService financeExcelParseService;
	
	@Autowired
	private FinanceFileLogInfoService financeFileLogInfoService;
	
	@Autowired
	private CellSummaryInfoService cellSummaryInfoService;
	
	@Autowired
	private RowSummaryInfoService rowSummaryInfoService;
	
	/**
	 * 导入文件之前打开上传页面
	 * <p>
	 * 
	 * @return UPLOAD
	 */
	@RequiresPermissions("surfaceReport:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload()
	{
		return UPLOAD;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("surfaceReport:upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile[] files, String sessionId)
	{
		ResponseEntity<String> responseEntity = ExcelFileUtils.fileExcelUpload(files);
		
		return responseEntity;
	}
	
	@Log(message = "上传了{0}文件成功。")
	@RequiresPermissions("surfaceReport:upload")
	@RequestMapping(value = "/parseData", method = RequestMethod.POST)
	public ResponseEntity<String> parseFileData(@RequestParam String fileName, @RequestParam String jsessionid)
	{
		String uploadPath = ExcelFileUtils.FILE_TEMP_DIR + ExcelFileUtils.UPLOAD
				+ ExcelFileUtils.decodeFileName(fileName);
		// 获取文件路径
		File file = new File(uploadPath);
		
		if (!file.exists())
		{
			return new ResponseEntity<String>("上传文件不存在！!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Map<String, String> failMap = null;
		try
		{
			ShiroUser shiroUser = LogInfoUserUtils.getShiroUser();
			
			// 首先先清空所有日志
			deleteFainanceExcelParseLogInfo(jsessionid, shiroUser);
			
			// 启动线程开始解析
			FinanceExcelParseServiceConstans.DEFAULT_EXECUTOR_POOL.execute(new FinanceExcelParseThread(file,
					jsessionid, shiroUser, financeExcelParseService));
		}
		catch (Exception e)
		{
			
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			return new ResponseEntity<String>("对不起，文档内容为空或解析文件失败！!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * 上传文件成功 并准备解析之前，先删除之前所有解析日志记录。
	 * 
	 * @param shiroUser
	 * @param jsessionid
	 */
	private void deleteFainanceExcelParseLogInfo(String jsessionid, ShiroUser shiroUser)
	{
		try
		{
			financeFileLogInfoService.delete(financeFileLogInfoService
					.findBySessionIdAndUserInfo(jsessionid, shiroUser));
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	@RequiresPermissions("surfaceReport:upload")
	@RequestMapping(value = "/refreshParseStatus", method = RequestMethod.GET)
	public String preRefreshFileParseStatus(HttpServletRequest request, String jsessionid, Map<String, Object> map)
	{
		try
		{
			List<FinanceFileLogInfo> financeFileLogInfos = financeFileLogInfoService.findBySessionIdAndUserInfo(
					jsessionid, LogInfoUserUtils.getShiroUser());
			
			if (CollectionUtils.isNotEmpty(financeFileLogInfos))
			{
				map.put("financeFileLogInfos", JSONArray.fromObject(financeFileLogInfos));
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return PARSE_STATUS;
	}
	
	@RequestMapping(value = "/deleteBySummaryTime/{summaryTime}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable String summaryTime)
	{
		if (StringUtils.isNotEmpty(summaryTime))
		{
			try
			{
				List<CellSummaryInfo> cellSummaryInfos = cellSummaryInfoService.findBySummaryTime(DateUtils.toDate(
						summaryTime, FinanceExcelParseServiceConstans.FORMAT_YYYY_MM));
				cellSummaryInfoService.delete(cellSummaryInfos);
				
				List<RowSummaryInfo> rowSummaryInfos = rowSummaryInfoService.findBySummaryTime(DateUtils.toDate(
						summaryTime, FinanceExcelParseServiceConstans.FORMAT_YYYY_MM));
				rowSummaryInfoService.delete(rowSummaryInfos);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				return AjaxObject.newError("删除失败，出现错误." + Exceptions.getStackTraceAsString(e)).setCallbackType("")
						.toString();
			}
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { summaryTime }));
		return AjaxObject.newOk("删除数据成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("surfaceReport:upload")
	@RequestMapping(value = "/refreshParseStatus", method = RequestMethod.POST)
	public ResponseEntity<String> refreshFileParseStatus(@RequestParam String jsessionid)
	{
		String json = null;
		try
		{
			List<FinanceFileLogInfo> financeFileLogInfos = financeFileLogInfoService.findBySessionIdAndUserInfo(
					jsessionid, LogInfoUserUtils.getShiroUser());
			
			if (CollectionUtils.isNotEmpty(financeFileLogInfos))
			{
				JSONArray jsonArray = JSONArray.fromObject(financeFileLogInfos);
				
				json = jsonArray.toString();
				
				// 检测该文件解析状态是否到最后一条，
				checkLastMessage(financeFileLogInfos);
				
			}
		}
		catch (Exception e)
		{
			
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	/**
	 * 根据标识字段检测，如果最后一条标识表示该解析流程已结束，则从数据库中删除该记录所属所有数据。
	 * 
	 * @param financeFileLogInfos
	 */
	private void checkLastMessage(List<FinanceFileLogInfo> financeFileLogInfos)
	{
		if (CollectionUtils.isNotEmpty(financeFileLogInfos))
		{
			FinanceFileLogInfo financeFileLogInfo = financeFileLogInfos.get(financeFileLogInfos.size() - 1);
			try
			{
				if (null != financeFileLogInfo && financeFileLogInfo.isMessageStatus())
				{
					financeFileLogInfoService.delete(financeFileLogInfos);
				}
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
}
