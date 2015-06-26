package com.innshine.reportExport.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.log.LogLevel;
import com.base.shiro.ShiroUser;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.brand.service.BrandsInfoService;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.classify.entity.ClassifyInfoTwo;
import com.innshine.classify.service.ClassifyInfoService;
import com.innshine.coststype.entity.FinanceCostsCategories;
import com.innshine.coststype.entity.FinanceCostsType;
import com.innshine.coststype.service.FinanceCostsTypeService;
import com.innshine.financeloginfo.entity.FinanceFileLogInfo;
import com.innshine.financeloginfo.service.FinanceFileLogInfoService;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;
import com.innshine.reportExport.entity.CellSummaryInfo;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;
import com.innshine.reportExport.entity.RowSummaryInfo;
import com.innshine.reportExport.util.ExcelFileUtils;
import com.innshine.reportExport.util.FinanceExcelParseExcelUtils;
import com.utils.DateUtils;
import com.utils.Exceptions;
import com.utils.excel.FieldUtils;
import com.utils.reflection.ReflectionUtils;

/**
 * 
 * 解析财务报表Excel服务类 <code>FinanceParseService.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class FinanceExcelParseService
{
	/**
	 * 底部数据开始列
	 */
	private static final int DEFAULT_BOTTOM_START_CELL = 2;
	
	/**
	 * 忽略后7行
	 */
	private static final int NEGLECTFUL_STERN_CELL_COUNT = 7;
	
	/**
	 * 忽略前3行
	 */
	private static final int NEGLECTFUL_HEAD_CELL_COUNT = 3;
	
	/**
	 * 店铺销售金额行行号
	 */
	private static final int SHOP_SALES_AMOUT_ROW_INDEX = 1;
	
	@Autowired
	private BrandsInfoService brandsInfoService;
	
	@Autowired
	private ClassifyInfoService classifyInfoService;
	
	@Autowired
	private FinanceCostsTypeService financeCostsTypeService;
	
	@Autowired
	private RowSummaryInfoService rowSummaryInfoService;
	
	@Autowired
	private CellSummaryInfoService cellSummaryInfoService;
	
	@Autowired
	private FinanceFileLogInfoService financeFileLogInfoService;
	
	@Autowired
	private FinanceFileInfoService financeFileInfoService;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExcelParseService.class);
	
	/**
	 * 解析上传的Excel文件，并返回失败错误数据与提示信息 <br>
	 * Map<RowSummaryInfo,String >
	 * key:导入的原始数据com.innshine.reportExport.entity.RowSummaryInfo <br>
	 * value：错误提示信息
	 * 
	 * @param shiroUser
	 *        用户登陆信息
	 * @param jsessionid
	 *        用户session ID
	 * 
	 * @param filePath
	 *        上传文件完整存储路径（包含文件名）
	 * @return Map<RowSummaryInfo,String >
	 *         key:导入的原始数据com.innshine.reportExport.entity .RowSummaryInfo <br>
	 *         value：错误提示信息
	 */
	public Map<String, String> parseFile(File financeFile, ShiroUser shiroUser, String sessionId)
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("filePath = [" + (null != financeFile ? financeFile.getPath() : "") + "],financeFile = ["
					+ financeFile + "]");
		}
		
		addFileUploadLogInfo(shiroUser, sessionId, "文件解析开始......", LogLevel.INFO);
		
		// 获取excel对象
		Workbook workbook = FinanceExcelParseExcelUtils.getCommonWorkbook(financeFile);
		
		// 根据sheet名，生成汇总时间
		Date summaryDate = getSummaryDate(workbook);
		if (null != summaryDate)
		{
			String time = DateUtils.format(summaryDate, FinanceExcelParseServiceConstans.FORMAT_YYYY_MM);
			
			// 效验汇总时间，是否已导入过数据
			if (checkSummaryTime(summaryDate))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "文件解析失败，该时间已有数据！ [ 时间 ：" + time + "]", LogLevel.ERROR,

				getDeleteRemark(time), true);
				
				return null;
			}
			
			addFileUploadLogInfo(shiroUser, sessionId, "时间解析成功！  [ 时间 ：" + time + "]", LogLevel.INFO);
			
			addFileUploadLogInfo(shiroUser, sessionId, "开始解析品牌列表", LogLevel.INFO);
			
			// 获取当前导入Excel中的品牌对象
			List<String> brandsInfos = getExcelBrandsInfo(workbook);
			
			addFileUploadLogInfo(shiroUser, sessionId, "品牌列表解析成功，详情数据 =" + brandsInfos, LogLevel.INFO);
			
			// 算定义品牌Map对象, key：品牌名称 value:系统内置品牌对象
			Map<String, BrandsInfo> customBrandMap = getCustomBrandMap(brandsInfos, sessionId, shiroUser);
			
			// 获取整个表格记录
			Map<String, String> failedMap = parseRowSummaryInfo(workbook, summaryDate, brandsInfos, customBrandMap,
					shiroUser, sessionId);
			
			addFileUploadLogInfo(shiroUser, sessionId, "文件解析结束......", LogLevel.INFO, true);
			
			// 保存文件并对文件信入库
			financeFileInfoService.saveReportExcelFile(financeFile, summaryDate, shiroUser);
			
			return failedMap;
			
		}
		else
		{
			addFileUploadLogInfo(shiroUser, sessionId,
					"根据工作表名称，解析汇总时间失败。请检测汇总时间是否正确！工作表命令规则：1501项目汇总分析表、2015-01、2015.01", LogLevel.ERROR, true);
			
			ExcelFileUtils.deleteFile(financeFile);
		}
		
		return null;
	}
	
	/**
	 * 生成删除标签
	 * 
	 * @param time
	 * @return
	 */
	private String getDeleteRemark(String time)
	{
		if (StringUtils.isNotEmpty(time))
		{
			StringBuffer buffer = new StringBuffer();
			// <a class="delete" target="selectedTodo"
			// rel="ids" href="${contextPath }/management/productInfo/delete"
			// title="确认要删除选定?"><span>删除产品信息</span></a>
			// buffer.append("<a class=\"delete\" target=\"ajaxTodo\"");
			// buffer.append(" src=\"${contextPath}/management/reportParse/deleteBySummaryTime/"
			// + time + "\"");
			// buffer.append(" title=\"确定清除" + time + "数据吗？ \">");
			// buffer.append("<span>清除已存在数据</span></a>");
			
			buffer.append("{\"status\":\"DELETE\",\"params\":\"" + time + "\"}");
			
			return buffer.toString();
		}
		
		return null;
	}
	
	/**
	 * 检测当前导入的文件汇总时间是否存在
	 * 
	 * @param summaryDate
	 *        汇总时间
	 * @return
	 */
	private boolean checkSummaryTime(Date summaryDate)
	{
		if (null != summaryDate)
		{
			try
			{
				List<RowSummaryInfo> rowSummaryInfos = rowSummaryInfoService.findBySummaryTime(summaryDate);
				if (CollectionUtils.isNotEmpty(rowSummaryInfos))
				{
					rowSummaryInfos.clear();
					rowSummaryInfos = null;
					return true;
				}
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * 把从EXCEL中获取的品牌通过比对，转换为与数据库存中对应的品牌数据
	 * 
	 * @param brandsInfos
	 *        excel中存放的品牌列表
	 * @param shiroUser
	 * @param sessionId
	 * @return Map< String, BrandsInfo > key:品牌名 value:数据库中存放的品牌对象
	 */
	private Map<String, BrandsInfo> getCustomBrandMap(List<String> brandsInfos, String sessionId, ShiroUser shiroUser)
	{
		Map<String, BrandsInfo> customMap = new HashMap<String, BrandsInfo>();
		
		if (CollectionUtils.isNotEmpty(brandsInfos))
		{
			List<BrandsInfo> infos = brandsInfoService.findAll();
			
			if (CollectionUtils.isNotEmpty(infos))
			{
				for (BrandsInfo brandsInfo : infos)
				{
					if (null != brandsInfo)
					{
						String brandName = brandsInfo.getBrandName();
						
						if (null != brandName && brandsInfos.contains(brandName.trim()))
						{
							customMap.put(brandName, brandsInfo);
						}
						
					}
				}
				
				checkBrandNameisExits(brandsInfos, sessionId, shiroUser, customMap);
			}
		}
		
		return customMap;
	}
	
	/**
	 * 检测品牌是否存在
	 * 
	 * @param brandsInfos
	 * @param sessionId
	 * @param shiroUser
	 * @param customMap
	 */
	private void checkBrandNameisExits(List<String> brandsInfos, String sessionId, ShiroUser shiroUser,
			Map<String, BrandsInfo> customMap)
	{
		if (CollectionUtils.isNotEmpty(brandsInfos) && null != customMap)
		{
			for (String brandName : brandsInfos)
			{
				if (StringUtils.isNotEmpty(brandName))
				{
					BrandsInfo brandsInfo = customMap.get(brandName.trim());
					
					if (null == brandsInfo)
					{
						savebrandNameNotExitsLogInfo(sessionId, shiroUser, brandName);
					}
					
				}
			}
		}
	}
	
	/**
	 * 获取Excel中导入的品牌列表
	 * 
	 * @param workbook
	 * @return
	 */
	private List<String> getExcelBrandsInfo(Workbook workbook)
	{
		List<String> brandList = new ArrayList<String>();
		if (null != workbook)
		{
			Sheet sheet = FinanceExcelParseExcelUtils.getSheet(workbook);
			
			if (sheet != null)
			{
				Row row = sheet.getRow(0);
				
				if (null != row)
				{
					Short lastCellNum = row.getLastCellNum();
					Short firstCellNum = row.getFirstCellNum();
					
					// 忽略前三行、后七期行，从中间开始认为是品牌列表
					for (int i = firstCellNum + NEGLECTFUL_HEAD_CELL_COUNT; i < lastCellNum
							- NEGLECTFUL_STERN_CELL_COUNT; i++)
					{
						Cell cell = row.getCell(i);
						if (null != cell)
						{
							String cellValue = cell.getStringCellValue();
							if (null != cellValue)
							{
								brandList.add(cellValue.trim());
							}
						}
					}
				}
			}
		}
		
		return brandList;
	}
	
	/**
	 * 根据sheet名称，生成汇总时间
	 * 
	 * @param workbook
	 *        excel对象
	 * @return java.util.Date
	 */
	private Date getSummaryDate(Workbook workbook)
	{
		String sheetName = FinanceExcelParseExcelUtils.getSheetName(workbook);
		
		if (StringUtils.isNotEmpty(sheetName))
		{
			if (sheetName.indexOf("-") != -1)
			{
				return com.utils.DateUtils.toDate(sheetName, "yyyy-MM");
			}
			else if (sheetName.indexOf(".") != -1)
			{
				return com.utils.DateUtils.toDate(sheetName, "yyyy.MM");
			}
			else
			{
				if (sheetName.length() >= 4)
				{
					String subDate = sheetName.trim().substring(0, 4);
					
					// 判断是否数据
					if (NumberUtils.isDigits(subDate))
					{
						return com.utils.DateUtils.toDate(subDate, "yyMM");
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取行汇总数据，如项目小计、综合费用、本期合计等
	 * 
	 * @param workbook
	 *        excel对象
	 * @param summaryDate
	 *        本次excel汇总时间
	 * @param brandsInfos
	 *        获取当前导入Excel中的品牌对象
	 * @param customBrandMap
	 *        自定义map对象，key:品牌名 value
	 * @param sessionId
	 * @param shiroUser
	 * @return
	 */
	public Map<String, String> parseRowSummaryInfo(Workbook workbook, Date summaryDate, List<String> brandsInfos,
			Map<String, BrandsInfo> customBrandMap, ShiroUser shiroUser, String sessionId)
	{
		// 记录整个Excel表数据
		List<RowSummaryInfo> rowSummaryInfos = new ArrayList<RowSummaryInfo>();
		
		Sheet sheet = FinanceExcelParseExcelUtils.getSheet(workbook);
		
		// 获取第一行销售列数据
		getSalesAmountRowValue(sheet, summaryDate, brandsInfos, customBrandMap, rowSummaryInfos, workbook);
		
		addFileUploadLogInfo(shiroUser, sessionId, "获取第一行[店铺销售额-淘宝店铺销售额]数据成功。 ", LogLevel.INFO);
		
		// 继续向下获取向下取列数据
		Map<String, String> map = getReportValue(sheet, summaryDate, brandsInfos, customBrandMap, rowSummaryInfos,
				workbook, shiroUser, sessionId);
		
		return map;
	}
	
	/**
	 * 获取项目编号、销售业绩情况，等数据
	 * 
	 * @param sheet
	 *        工作薄对象
	 * @param summaryDate
	 *        本次excel汇总时间
	 * @param brandsInfos
	 *        获取当前导入Excel中的品牌对象
	 * @param customBrandMap
	 *        自定义map对象，key:品牌名 value：数据库中对应的品牌对象
	 * @param rowSummaryInfos
	 *        存放当前解析的行对象
	 * @param workbook
	 * @param sessionId
	 * @param shiroUser
	 */
	private Map<String, String> getReportValue(Sheet sheet, Date summaryDate, List<String> brandsInfos,
			Map<String, BrandsInfo> customBrandMap, List<RowSummaryInfo> rowSummaryInfos, Workbook workbook,
			ShiroUser shiroUser, String sessionId)
	{
		if (null != sheet)
		{
			// 获取得行数
			int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
			
			addFileUploadLogInfo(shiroUser, sessionId, "文档总行数  ，数量 =" + rowNum, LogLevel.INFO);
			
			// 6001.0200 销售折扣与折让 0.00 0.00 #DIV/0! 0.00 #DIV/0!
			String[] rowSummaryFields = getRowSummaryFields();
			
			// NB 微热山丘 曼秀雷敦 雅芳婷 欧莱雅 欧莱雅专营 OT ASICS 美宝莲微信 美宝莲(通用字段名)
			String rowMonthSummaryField = FinanceExcelParseServiceConstans.COMMON_BRAND_AMOUNT_FIELD;
			
			// 直接费用 直接材料成本+支付宝直接费用
			String[] costSummaryFields = getFinanceCostsTypeFields(FinanceExcelParseServiceConstans.FINANCE_HEAD_FIELD);
			
			// 直接费用 直接材料成本+支付宝直接费用
			String[] costBottomSummaryFields = getFinanceCostsTypeFields(FinanceExcelParseServiceConstans.FINANCE_STERM_HEAD_FIELD);
			
			// 获取所有合并单元格
			List<CellRangeAddress> cellRangeAddresses = FinanceExcelParseExcelUtils.getCombineCell(sheet);
			
			// 记录行汇总值
			List<RowSummaryInfo> summaryInfos = new ArrayList<RowSummaryInfo>();
			
			// 记录列汇总值
			List<CellSummaryInfo> cellSummaryTakeDetailsInfos = new ArrayList<CellSummaryInfo>();
			
			// 记录列汇总值
			List<CellSummaryInfo> cellSummaryInfos = new ArrayList<CellSummaryInfo>();
			
			// 汇总列数据对象，未解析到底部行的数据(直接费用 直接材料成本+支付宝直接费用)，用之于行数据详情(如：6001.0200
			// 销售折扣与折让 )关联
			int preCellSummaryCount = 0;
			
			// 标识是否有列汇总数据
			boolean isCellSummaryData = false;
			
			// 记录行汇总值
			List<RowSummaryInfo> cellRowSummaryDetailsInfos = new ArrayList<RowSummaryInfo>();
			
			for (int i = SHOP_SALES_AMOUT_ROW_INDEX + 1; i < rowNum; i++)
			{
				Row row = sheet.getRow(i);
				
				if (null != row)
				{
					// 检测当前行中是否有合并列，有合并列则认为是列汇总数据，即如：直接费用 直接材料成本+支付宝直接费用
					if (isColspan(cellRangeAddresses, row, sheet))
					{
						CellSummaryInfo cellSummaryInfo = parseCostSummaryData(row, summaryDate, costSummaryFields,
								rowMonthSummaryField, workbook, (null != row ? row.getFirstCellNum() : 0), true,
								brandsInfos);
						
						addList(cellSummaryTakeDetailsInfos, cellSummaryInfo);
						
						// 列汇总计数器
						if (cellSummaryInfo != null)
						{
							isCellSummaryData = true;
							preCellSummaryCount++;
						}
						
						// 设置列汇总数据中行数据
						setCellRowSummaryDetailsInfos(cellSummaryTakeDetailsInfos, preCellSummaryCount,
								cellRowSummaryDetailsInfos);
						
					}
					
					// 检测前两行如果都为空，则认为取到了最后底部汇总部分，即：项目毛利 项目毛利率 综合费用等
					else if (isPreTwoRowValue(row))
					{
						// 检测该行，第三列单元格值不为空
						if (is3RowValueNotEmpty(row, DEFAULT_BOTTOM_START_CELL))
						{
							CellSummaryInfo summaryInfo = parseCostSummaryData(row, summaryDate,
									costBottomSummaryFields, rowMonthSummaryField, workbook, (null != row ? row
											.getFirstCellNum()
											+ DEFAULT_BOTTOM_START_CELL : 0), false, brandsInfos);
							
							addList(cellSummaryInfos, summaryInfo);
						}
					}
					
					// 正常数据 6602.1003.0100 广告宣传费 展览费
					else
					{
						// 获取解析的列对象集合
						CellSummaryInfo cellSummaryInfo = (preCellSummaryCount > 0 ? cellSummaryTakeDetailsInfos
								.get(preCellSummaryCount - 1) : null);
						// 解析行
						RowSummaryInfo rowSummaryInfo = parseRowAndBrandsData(row, summaryDate, rowSummaryFields,
								rowMonthSummaryField, workbook, cellSummaryInfo, brandsInfos);
						
						addList(summaryInfos, rowSummaryInfo);
						
						// 添加行详情数据至集合中
						addCellRowSummaryDetailsInfos(isCellSummaryData, rowSummaryInfo, cellRowSummaryDetailsInfos);
					}
				}
			}
			
			addFileUploadLogInfo(shiroUser, sessionId, "数据解析成功，数据清洗中......！", LogLevel.INFO);
			
			// 添加尾部分列汇总详情到，灰色背景部分的列汇总数据处理，并清空cellSummaryInfos集合
			addToAll(cellSummaryTakeDetailsInfos, cellSummaryInfos);
			
			// 添加尾部分列汇总详情到，灰色背景部分的列汇总数据处理，并清空cellSummaryInfos集合
			addToAll(rowSummaryInfos, summaryInfos);
			
			// 更新本次集合中的品牌、分类等属性
			modifySummaryInfos(rowSummaryInfos, customBrandMap, sessionId, shiroUser);
			
			// 更新本次集合中的品牌、财务等属性
			modifyCellSummaryInfos(cellSummaryTakeDetailsInfos, customBrandMap, sessionId, shiroUser);
			
			addFileUploadLogInfo(shiroUser, sessionId, "数据清洗成功......！", LogLevel.INFO);
			
			// 记录解析集合日志
			// loginfo(summaryInfos, cellSummaryTakeDetailsInfos);
			
			// 数据入库操作
			dataSaveOrUpdate(rowSummaryInfos, shiroUser, sessionId, cellSummaryTakeDetailsInfos);
			
		}
		
		return null;
	}
	
	/**
	 * 数据入库
	 * 
	 * @param rowSummaryInfos
	 * @param shiroUser
	 * @param sessionId
	 * @param cellSummaryTakeDetailsInfos
	 */
	private void dataSaveOrUpdate(List<RowSummaryInfo> rowSummaryInfos, ShiroUser shiroUser, String sessionId,
			List<CellSummaryInfo> cellSummaryTakeDetailsInfos)
	{
		try
		{
			addFileUploadLogInfo(shiroUser, sessionId, "正在入库！", LogLevel.INFO);
			
			cellSummaryInfoService.saveOrUpdate(cellSummaryTakeDetailsInfos);
			
			// 入库
			rowSummaryInfoService.saveOrUpdate(rowSummaryInfos);
			
			if (CollectionUtils.isNotEmpty(cellSummaryTakeDetailsInfos) && CollectionUtils.isNotEmpty(rowSummaryInfos))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "入库总数量 = "
						+ (cellSummaryTakeDetailsInfos.size() + rowSummaryInfos.size()) + "", LogLevel.INFO);
			}
			
			addFileUploadLogInfo(shiroUser, sessionId, "入库成功！", LogLevel.INFO);
		}
		catch (Exception e)
		{
			addFileUploadLogInfo(shiroUser, sessionId, "入库失败，系统异常！", LogLevel.ERROR, true);
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 检测该单元格值是否为空
	 * 
	 * @param row
	 * @param i
	 * @return
	 */
	private boolean is3RowValueNotEmpty(Row row, int cellIndex)
	{
		try
		{
			if (null != row)
			{
				Cell cell = row.getCell(cellIndex);
				
				if (null != cell)
				{
					// 忽略数据类型，都当作String获取
					cell.setCellType(Cell.CELL_TYPE_STRING);
					
					if (StringUtils.isNotEmpty(cell.getStringCellValue()))
					{
						return true;
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return false;
	}
	
	/**
	 * 设置列汇总数据中行数据详情，即List
	 * 
	 * @param cellSummaryTakeDetailsInfos
	 * @param preCellSummaryCount
	 *        cellSummaryTakeDetailsInfos该
	 * @param cellRowSummaryDetailsInfos
	 */
	private void setCellRowSummaryDetailsInfos(List<CellSummaryInfo> cellSummaryTakeDetailsInfos,
			int preCellSummaryCount, List<RowSummaryInfo> cellRowSummaryDetailsInfos)
	{
		try
		{
			if (preCellSummaryCount > 0 && CollectionUtils.isNotEmpty(cellSummaryTakeDetailsInfos))
			{
				CellSummaryInfo cellSummaryInfo = cellSummaryTakeDetailsInfos.get(preCellSummaryCount - 1);
				
				if (null != cellSummaryInfo)
				{
					List<RowSummaryInfo> tmpList = new ArrayList<RowSummaryInfo>();
					if (null != cellRowSummaryDetailsInfos)
					{
						tmpList.addAll(cellRowSummaryDetailsInfos);
						
						// 清空上次集合
						cellRowSummaryDetailsInfos.clear();
						
					}
					cellSummaryInfo.setRowSummaryInfos(tmpList);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 添加行详情数据至集合中，用于列汇总数据与行汇总数据one-many关联起来
	 * 
	 * @param isCellSummaryData
	 *        true:有列汇总数据
	 * @param rowSummaryInfo
	 * @param cellRowSummaryDetailsInfos
	 */
	private void addCellRowSummaryDetailsInfos(boolean isCellSummaryData, RowSummaryInfo rowSummaryInfo,
			List<RowSummaryInfo> cellRowSummaryDetailsInfos)
	{
		if (isCellSummaryData && null != rowSummaryInfo && null != cellRowSummaryDetailsInfos)
		{
			addList(cellRowSummaryDetailsInfos, rowSummaryInfo);
		}
	}
	
	/**
	 * 更新本次集合中的品牌、财务等属性转换为对应ID,如分类名称->分类ID 、品牌-->品牌ID等
	 * 
	 * @param cellSummaryTakeDetailsInfos
	 * 
	 * @param customBrandMap
	 * @param shiroUser
	 * @param sessionId
	 */
	private void modifyCellSummaryInfos(List<CellSummaryInfo> cellSummaryTakeDetailsInfos,
			Map<String, BrandsInfo> customBrandMap, String sessionId, ShiroUser shiroUser)
	{
		// 财务分类信息
		List<FinanceCostsType> financeCostsTypes = financeCostsTypeService.findAll();
		if (CollectionUtils.isNotEmpty(cellSummaryTakeDetailsInfos) && MapUtils.isNotEmpty(customBrandMap)
				&& CollectionUtils.isNotEmpty(financeCostsTypes))
		{
			for (CellSummaryInfo cellSummaryInfo : cellSummaryTakeDetailsInfos)
			{
				if (cellSummaryInfo != null)
				{
					// 更新费用编号、子费用编号编号
					modifyCostsTypeInfo(cellSummaryInfo, financeCostsTypes);
					// 检测分类、二级分类是否存在，不存在则记录日志
					checkCostsTypeOrCostsTypeTwoInfoExist(cellSummaryInfo, sessionId, shiroUser);
					// 更新对应子列、品牌
					modifyCellMonthSummaryInfosClassifyInfo(cellSummaryInfo, customBrandMap);
				}
			}
		}
	}
	
	/**
	 * 检测分类、二级分类是否存在，不存在则记录日志
	 * 
	 * @param cellSummaryInfo
	 * @param sessionId
	 * @param shiroUser
	 */
	private void checkCostsTypeOrCostsTypeTwoInfoExist(CellSummaryInfo cellSummaryInfo, String sessionId,
			ShiroUser shiroUser)
	{
		if (null != cellSummaryInfo)
		{
			if (cellSummaryInfo.getFinanceCostsType() == null
					&& StringUtils.isNotEmpty(cellSummaryInfo.getFinanceCostsTypeName()))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "一级分类清洗失败，请检查一级分类名称是否存在! ["
						+ cellSummaryInfo.getFinanceCostsTypeName() + "]", LogLevel.ERROR);
			}
			
			if (cellSummaryInfo.getFinanceCostsCategories() == null
					&& StringUtils.isNotEmpty(cellSummaryInfo.getFinanceCostsCategoriesName()))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "二级分类清洗失败，请检查二级分类名称是否存在! ["
						+ (null == cellSummaryInfo.getFinanceCostsType() ? "" : cellSummaryInfo.getFinanceCostsType())
						+ "--" + cellSummaryInfo.getFinanceCostsCategoriesName() + "]", LogLevel.ERROR);
			}
			
		}
	}
	
	/**
	 * 
	 * 
	 * @param cellSummaryInfo
	 * @param customBrandMap
	 */
	private void modifyCellMonthSummaryInfosClassifyInfo(CellSummaryInfo cellSummaryInfo,
			Map<String, BrandsInfo> customBrandMap)
	{
		
		if (cellSummaryInfo != null && MapUtils.isNotEmpty(customBrandMap))
		{
			List<CellMonthSummaryInfo> monthSummaryInfos = cellSummaryInfo.getCellMonthSummaryInfos();
			
			if (CollectionUtils.isNotEmpty(monthSummaryInfos))
			{
				for (CellMonthSummaryInfo cellMonthSummaryInfo : monthSummaryInfos)
				{
					if (cellMonthSummaryInfo != null)
					{
						cellMonthSummaryInfo.setFinanceCostsTypeId(cellSummaryInfo.getFinanceCostsTypeId());
						cellMonthSummaryInfo.setFinanceCostsTypeName(cellSummaryInfo.getFinanceCostsTypeName());
						cellMonthSummaryInfo.setFinanceCostsType(cellSummaryInfo.getFinanceCostsType());
						cellMonthSummaryInfo.setFinanceCostsCategories(cellSummaryInfo.getFinanceCostsCategories());
						cellMonthSummaryInfo.setFinanceCostsCategoriesId(cellSummaryInfo.getFinanceCostsCategoriesId());
						cellMonthSummaryInfo.setFinanceCostsCategoriesName(cellSummaryInfo
								.getFinanceCostsCategoriesName());
						cellMonthSummaryInfo.setSummaryTime(cellSummaryInfo.getSummaryTime());
						cellMonthSummaryInfo.setUpdateTime(cellSummaryInfo.getUpdateTime());
						BrandsInfo brandsInfo = customBrandMap.get(cellMonthSummaryInfo.getBrandName());
						
						if (null != brandsInfo)
						{
							cellMonthSummaryInfo.setBrandId(brandsInfo.getId());
							cellMonthSummaryInfo.setBrandsInfo(brandsInfo);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 更新本次集合中的品牌、财务等属性转换为对应ID,如费用名称->费用ID 、品牌-->品牌ID等
	 * 
	 * @param cellSummaryInfo
	 * @param financeCostsTypes
	 */
	private void modifyCostsTypeInfo(CellSummaryInfo cellSummaryInfo, List<FinanceCostsType> financeCostsTypes)
	{
		if (CollectionUtils.isNotEmpty(financeCostsTypes) && null != cellSummaryInfo)
		{
			String financeCostsTypeName = cellSummaryInfo.getFinanceCostsTypeName();
			String financeCostsCategoriesName = cellSummaryInfo.getFinanceCostsCategoriesName();
			
			// 处理一级费用名称相同，但二级费用名不一样的数据比对
			FinanceCostsType financeCostsType = getCostsAndCategories(financeCostsTypeName, financeCostsCategoriesName,
					financeCostsTypes);
			
			if (null != financeCostsType)
			{
				cellSummaryInfo.setFinanceCostsTypeId(financeCostsType.getId());
				cellSummaryInfo.setFinanceCostsType(financeCostsType);
				
				// 更新子类别
				modifyCostsCategoriesInfo(financeCostsCategoriesName, financeCostsType.getFinanceCostsCategories(),
						cellSummaryInfo);
			}
		}
		
	}
	
	/**
	 * 比较一级分类名与二级分类名，获取一级分类名一样，但二级分类名不一样的分类对象<br>
	 * 
	 * 
	 * @param financeCostsTypeName
	 *        一级分类名
	 * @param financeCostsCategoriesName
	 *        二级分类
	 * @param financeCostsTypes
	 *        一级分类数据对象，即用来循环比较
	 * @return {@link com.innshine.coststype.entity. FinanceCostsType}
	 */
	private FinanceCostsType getCostsAndCategories(String financeCostsTypeName, String financeCostsCategoriesName,
			List<FinanceCostsType> financeCostsTypes)
	{
		if (StringUtils.isNotEmpty(financeCostsTypeName))
		{
			for (FinanceCostsType financeCostsType : financeCostsTypes)
			{
				// 一级分类
				String oneCosttyp = financeCostsType.getCostType();
				List<FinanceCostsCategories> financeCostsCategories = financeCostsType.getFinanceCostsCategories();
				
				if (StringUtils.isNotEmpty(oneCosttyp) && oneCosttyp.trim().equalsIgnoreCase(financeCostsTypeName))
				{
					if (CollectionUtils.isNotEmpty(financeCostsCategories)
							&& StringUtils.isNotEmpty(financeCostsCategoriesName))
					{
						for (FinanceCostsCategories financeCostsCategoriesInfo : financeCostsCategories)
						{
							if (null != financeCostsCategoriesInfo)
							{
								String costsCategoriesType = financeCostsCategoriesInfo.getCostType();
								
								if (StringUtils.isNotEmpty(costsCategoriesType)
										&& costsCategoriesType.trim().equalsIgnoreCase(
												financeCostsCategoriesName.trim()))
								{
									return financeCostsType;
								}
							}
						}
					}
					
					else
					{
						return financeCostsType;
					}
				}
				
			}
			
		}
		
		return null;
	}
	
	/**
	 * @param financeCostsCategoriesName
	 * @param financeCostsCategories
	 * @param cellSummaryInfo
	 */
	private void modifyCostsCategoriesInfo(String financeCostsCategoriesName,
			List<FinanceCostsCategories> financeCostsCategories, CellSummaryInfo cellSummaryInfo)
	{
		if (null != cellSummaryInfo && CollectionUtils.isNotEmpty(financeCostsCategories))
		{
			for (FinanceCostsCategories financeCostsCategorie : financeCostsCategories)
			{
				if (financeCostsCategorie != null)
				{
					String costType = financeCostsCategorie.getCostType();
					
					if (StringUtils.isNotEmpty(financeCostsCategoriesName) && StringUtils.isNotEmpty(costType))
					{
						if (costType.trim().equalsIgnoreCase(financeCostsCategoriesName.trim()))
						{
							cellSummaryInfo.setFinanceCostsCategoriesId(financeCostsCategorie.getId());
							cellSummaryInfo.setFinanceCostsCategories(financeCostsCategorie);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 更新基础数据，如分类名称->分类ID 、品牌-->品牌ID等
	 * 
	 * @param summaryInfos
	 *        基础数据
	 * @param customBrandMap
	 *        品牌map,key:品牌名称 value:品牌对象
	 * @param shiroUser
	 * @param sessionId
	 */
	private void modifySummaryInfos(List<RowSummaryInfo> summaryInfos, Map<String, BrandsInfo> customBrandMap,
			String sessionId, ShiroUser shiroUser)
	{
		// 组装数据，主要重新组装有合并行数据
		assemblesData(summaryInfos);
		
		// 清洗Excel数据为数据库对应数据
		eltExcelToDataBase(summaryInfos, customBrandMap, sessionId, shiroUser);
	}
	
	/**
	 * 把名称转ID处理，如品牌名-id ,分类ID-ID
	 * 
	 * @param summaryInfos
	 * @param customBrandMap
	 * @param shiroUser
	 * @param sessionId
	 */
	private void eltExcelToDataBase(List<RowSummaryInfo> summaryInfos, Map<String, BrandsInfo> customBrandMap,
			String sessionId, ShiroUser shiroUser)
	{
		
		// 获取分类信息
		List<ClassifyInfo> classifyInfos = classifyInfoService.findAll();
		
		if (CollectionUtils.isNotEmpty(summaryInfos) && MapUtils.isNotEmpty(customBrandMap)
				&& CollectionUtils.isNotEmpty(classifyInfos))
		{
			for (RowSummaryInfo rowSummaryInfo : summaryInfos)
			{
				if (rowSummaryInfo != null)
				{
					// 更新分类编号
					modifyClassifyInfo(rowSummaryInfo, classifyInfos);
					
					// 检测分类、二级分类是否存在，不存在则记录日志
					checkClassifyOrClassifyTwoInfoExist(rowSummaryInfo, sessionId, shiroUser);
					
					// 更新对应子列
					modifyRowMonthSummaryInfosClassifyInfo(rowSummaryInfo, customBrandMap, sessionId, shiroUser);
				}
			}
		}
	}
	
	/**
	 * 更新分类与子分类信息
	 * 
	 * @param rowSummaryInfo
	 * @param customBrandMap
	 * @param shiroUser
	 * @param sessionId
	 */
	private void modifyRowMonthSummaryInfosClassifyInfo(RowSummaryInfo rowSummaryInfo,
			Map<String, BrandsInfo> customBrandMap, String sessionId, ShiroUser shiroUser)
	{
		if (rowSummaryInfo != null && MapUtils.isNotEmpty(customBrandMap))
		{
			List<RowMonthSummaryInfo> monthSummaryInfos = rowSummaryInfo.getRowMonthSummaryInfos();
			
			if (CollectionUtils.isNotEmpty(monthSummaryInfos))
			{
				for (RowMonthSummaryInfo rowMonthSummaryInfo : monthSummaryInfos)
				{
					if (rowMonthSummaryInfo != null)
					{
						rowMonthSummaryInfo.setSummaryTime(rowSummaryInfo.getSummaryTime());
						rowMonthSummaryInfo.setUpdateTime(rowSummaryInfo.getUpdateTime());
						rowMonthSummaryInfo.setClassifyId(rowSummaryInfo.getClassifyId());
						rowMonthSummaryInfo.setClassifyName(rowSummaryInfo.getClassifyName());
						
						rowMonthSummaryInfo.setClassifyInfo(rowSummaryInfo.getClassifyInfo());
						
						rowMonthSummaryInfo.setClassifyInfoTwoId(rowSummaryInfo.getClassifyInfoTwoId());
						rowMonthSummaryInfo.setClassifyInfoTwoName(rowSummaryInfo.getClassifyInfoTwoName());
						
						rowMonthSummaryInfo.setClassifyInfoTwo(rowSummaryInfo.getClassifyInfoTwo());
						
						BrandsInfo brandsInfo = customBrandMap.get(rowMonthSummaryInfo.getBrandName());
						if (null != brandsInfo)
						{
							rowMonthSummaryInfo.setBrandId(brandsInfo.getId());
							rowMonthSummaryInfo.setBrandsInfo(brandsInfo);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 记录品牌不存在日志
	 * 
	 * @param sessionId
	 * @param shiroUser
	 * @param brandName
	 */
	private void savebrandNameNotExitsLogInfo(String sessionId, ShiroUser shiroUser, String brandName)
	{
		addFileUploadLogInfo(shiroUser, sessionId, "品牌清洗失败，请检查品牌名称是否存在.[" + brandName + "]", LogLevel.ERROR);
	}
	
	/**
	 * 更新分类与子分类信息
	 * 
	 * @param rowSummaryInfo
	 * @param classifyInfos
	 */
	private void modifyClassifyInfo(RowSummaryInfo rowSummaryInfo, List<ClassifyInfo> classifyInfos)
	{
		if (CollectionUtils.isNotEmpty(classifyInfos) && null != rowSummaryInfo)
		{
			String classifyName = rowSummaryInfo.getClassifyName();
			String classifyInfoTwoName = rowSummaryInfo.getClassifyInfoTwoName();
			String projectId = rowSummaryInfo.getProjectId();
			
			// 检测项目编号是否为
			ClassifyInfo tmpClassifyInfo = compareProjectId(classifyInfos, projectId);
			
			// 如果不存在项目编号，则认为是没有，继续比对名称
			if (null == tmpClassifyInfo)
			{
				tmpClassifyInfo = compareClassify(classifyInfos, classifyName);
			}
			
			if (null != tmpClassifyInfo)
			{
				rowSummaryInfo.setClassifyId(tmpClassifyInfo.getId());
				rowSummaryInfo.setClassifyInfo(tmpClassifyInfo);
				
				// 更新子类别
				modifyClassifyTwoInfo(classifyInfoTwoName, tmpClassifyInfo.getClassifyInfoTwos(), rowSummaryInfo);
				
			}
		}
	}
	
	/**
	 * 比对项目名称是否存在
	 * 
	 * @param classifyInfos
	 * @param projectId
	 * @return
	 */
	private ClassifyInfo compareProjectId(List<ClassifyInfo> classifyInfos, String projectId)
	{
		if (CollectionUtils.isNotEmpty(classifyInfos))
		{
			for (ClassifyInfo classifyInfo : classifyInfos)
			{
				if (classifyInfo != null)
				{
					List<ClassifyInfoTwo> classifyInfoTwos = classifyInfo.getClassifyInfoTwos();
					
					// 根据项目编号或分类名称，清洗分类至数据库
					ClassifyInfoTwo classifyInfoTwo = checkProjectId(classifyInfoTwos, projectId);
					
					if (null != classifyInfoTwo)
					{
						return classifyInfoTwo.getClassifyInfo();
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 根据项目编号(如：6403.0500)或分类名称，从Excel中清洗分类名称至数据库中。
	 * 
	 * @param classifyInfos
	 *        excel中存储的分类名称
	 * @param classifyTypeName
	 *        分类名称
	 * @return com.innshine.classify.entity.ClassifyInfo
	 */
	private ClassifyInfo compareClassify(List<ClassifyInfo> classifyInfos, String classifyTypeName)
	{
		
		if (StringUtils.isNotEmpty(classifyTypeName) && CollectionUtils.isNotEmpty(classifyInfos))
		{
			for (ClassifyInfo classifyInfo : classifyInfos)
			{
				if (null != classifyInfo)
				{
					String classifyType = classifyInfo.getClassifyType();
					if (StringUtils.isNotEmpty(classifyType)
							&& classifyTypeName.trim().equalsIgnoreCase(classifyType.trim()))
					{
						return classifyInfo;
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 检测项目编号是否一致，
	 * 
	 * @param classifyInfoTwos
	 *        二级子类列表
	 * @param projectId
	 *        项目编号
	 * @return ClassifyInfoTwo
	 */
	private ClassifyInfoTwo checkProjectId(List<ClassifyInfoTwo> classifyInfoTwos, String projectId)
	{
		if (CollectionUtils.isNotEmpty(classifyInfoTwos) && StringUtils.isNotEmpty(projectId))
		{
			for (ClassifyInfoTwo classifyInfoTwo : classifyInfoTwos)
			{
				if (classifyInfoTwo != null)
				{
					// 比较两个项目编号是否一致
					if (projectId.trim().equalsIgnoreCase(classifyInfoTwo.getClassifyId()))
					{
						return classifyInfoTwo;
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 检测分类、二级分类是否存在，不存在则记录日志
	 * 
	 * @param rowSummaryInfo
	 * @param sessionId
	 * @param shiroUser
	 */
	private void checkClassifyOrClassifyTwoInfoExist(RowSummaryInfo rowSummaryInfo, String sessionId,
			ShiroUser shiroUser)
	{
		if (null != rowSummaryInfo)
		{
			if (rowSummaryInfo.getClassifyInfo() == null && StringUtils.isNotEmpty(rowSummaryInfo.getClassifyName()))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "一级分类清洗失败，请检查一级分类名称是否存在! ["
						+ rowSummaryInfo.getClassifyName() + "]", LogLevel.ERROR);
			}
			
			if (rowSummaryInfo.getClassifyInfoTwo() == null
					&& StringUtils.isNotEmpty(rowSummaryInfo.getClassifyInfoTwoName()))
			{
				addFileUploadLogInfo(shiroUser, sessionId, "二级分类清洗失败，请检查二级分类名称是否存在! ["
						+ (null == rowSummaryInfo.getClassifyName() ? "" : rowSummaryInfo.getClassifyName()) + "--"
						+ rowSummaryInfo.getClassifyInfoTwoName() + "]", LogLevel.ERROR);
			}
			
		}
	}
	
	/**
	 * 清洗子类别ID，从子类别名称转换为ID
	 * 
	 * @param classifyInfoTwoName
	 *        子类别名称
	 * @param classifyInfoTwos
	 *        子类别列表
	 * @param rowSummaryInfo
	 *        原始数据对象
	 */
	private void modifyClassifyTwoInfo(String classifyInfoTwoName, List<ClassifyInfoTwo> classifyInfoTwos,
			RowSummaryInfo rowSummaryInfo)
	{
		if (null != rowSummaryInfo && CollectionUtils.isNotEmpty(classifyInfoTwos))
		{
			for (ClassifyInfoTwo classifyInfoTwo : classifyInfoTwos)
			{
				if (classifyInfoTwo != null)
				{
					String classifyType = classifyInfoTwo.getClassifyType();
					
					if (StringUtils.isNotEmpty(classifyInfoTwoName) && StringUtils.isNotBlank(classifyType))
					{
						if (classifyType.trim().equalsIgnoreCase(classifyInfoTwoName.trim()))
						{
							rowSummaryInfo.setClassifyInfoTwoId(classifyInfoTwo.getId());
							rowSummaryInfo.setClassifyInfoTwo(classifyInfoTwo);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 正在组装数据方法，组装格式如：1-2 ,1-3
	 * <p>
	 * 
	 * @param summaryInfos
	 */
	private void assemblesData(List<RowSummaryInfo> summaryInfos)
	{
		if (CollectionUtils.isNotEmpty(summaryInfos))
		{
			
			for (int i = 0; i < summaryInfos.size(); i++)
			{
				RowSummaryInfo rowSummaryInfo = summaryInfos.get(i);
				
				if (null != rowSummaryInfo && StringUtils.isNotEmpty(rowSummaryInfo.getClassifyName()))
				{
					// 更新对应品牌金额详情分类信息
					modifyRowMonthSummaryInfos(rowSummaryInfo.getRowMonthSummaryInfos(), rowSummaryInfo);
					
					for (int j = i + 1; j < summaryInfos.size(); j++)
					{
						RowSummaryInfo summaryInfo = summaryInfos.get(j);
						
						// 如果下一个分类名称为空，则认为上一次组装成功，跳出上次组装流程，继续下次组装
						if (null != summaryInfo && StringUtils.isNotEmpty(summaryInfo.getClassifyName()))
						{
							break;
						}
						
						// 更新 分类名称
						summaryInfo.setClassifyName(rowSummaryInfo.getClassifyName());
						
						// 更新对应品牌金额详情分类信息
						modifyRowMonthSummaryInfos(summaryInfo.getRowMonthSummaryInfos(), summaryInfo);
					}
				}
			}
		}
	}
	
	/**
	 * 更新对应品牌金额详情分类信息
	 * 
	 * @param rowMonthSummaryInfos
	 * @param summaryInfo
	 */
	private void modifyRowMonthSummaryInfos(List<RowMonthSummaryInfo> rowMonthSummaryInfos, RowSummaryInfo summaryInfo)
	{
		if (null != rowMonthSummaryInfos && null != summaryInfo)
		{
			for (RowMonthSummaryInfo rowMonthSummaryInfo : rowMonthSummaryInfos)
			{
				if (rowMonthSummaryInfo != null)
				{
					rowMonthSummaryInfo.setClassifyName(summaryInfo.getClassifyName());
					rowMonthSummaryInfo.setClassifyInfoTwoName(summaryInfo.getClassifyInfoTwoName());
				}
			}
		}
	}
	
	/**
	 * 记录解析EXCEL数据结果日志
	 * 
	 * @param summaryInfos
	 * @param cellSummaryTakeDetailsInfos
	 * @param cellSummaryInfos
	 */
	// private void loginfo(List<RowSummaryInfo> summaryInfos,
	// List<CellSummaryInfo> cellSummaryTakeDetailsInfos)
	// {
	// if (LOGGER.isDebugEnabled())
	// {
	// LOGGER.debug("RowSummaryInfo Result = " + summaryInfos + "");
	// LOGGER.debug("CellSummaryInfo Result = " + cellSummaryTakeDetailsInfos +
	// "");
	// }
	// }
	
	/**
	 * 添加尾部分列汇总详情到，灰色背景部分的列汇总数据处理，并清空cellSummaryInfos集合
	 * 
	 * @param t2
	 */
	private <T> void addToAll(List<T> t1, List<T> t2)
	{
		try
		{
			if (null != t1 && null != t2)
			{
				t1.addAll(t2);
				t2.clear();
				t2 = null;
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 添加对像至List集合中
	 * 
	 * @param <T>
	 * @param list
	 * @param t
	 */
	public <T> void addList(List<T> list, T t)
	{
		if (null != t && null != list)
		{
			list.add(t);
		}
	}
	
	/**
	 * 费用类型表头
	 * 
	 * @param financeHeadField
	 *        字段类型
	 * @return
	 */
	private String[] getFinanceCostsTypeFields(String[] financeHeadField)
	{
		String[] array = new String[] {};
		array = ArrayUtils.addAll(array, financeHeadField);
		array = ArrayUtils.addAll(array, FinanceExcelParseServiceConstans.COMMON_STERM_FIELD);
		return array;
	}
	
	/**
	 * 解析单行数据，带灰色背景。数据格式为： 直接费用 直接材料成本+支付宝直接费用
	 * 
	 * @param row
	 *        行
	 * @param summaryDate
	 *        汇总日期
	 * @param costSummaryFields
	 *        字段列表
	 * @param rowMonthSummaryField
	 *        品牌金额
	 * @param workbook
	 *        excel对象
	 * @param isProcessColspan
	 *        是否需要处理前3行存在合并列，true:处理 false:不处理
	 * @param startCellIndex
	 *        单元格开始下标
	 * @param brandsInfos
	 * @return CellSummaryInfo
	 */
	private CellSummaryInfo parseCostSummaryData(Row row, Date summaryDate, String[] costSummaryFields,
			String rowMonthSummaryField, Workbook workbook, int startCellIndex, boolean isProcessColspan,
			List<String> brandsInfos)
	{
		if (null != row)
		{
			CellSummaryInfo cellSummaryInfo = cosCellSummaryInfo(summaryDate);
			List<CellMonthSummaryInfo> cellMonthSummaryInfos = new ArrayList<CellMonthSummaryInfo>();
			
			short lastCellNum = row.getLastCellNum();
			int index = 0;
			int brandIndex = 0;
			for (int i = startCellIndex; i < lastCellNum; i++)
			{
				Cell cell = row.getCell(i);
				Object obj = FinanceExcelParseExcelUtils.getCellValue(cell, workbook);
				
				// 处理结果为空且为前3行，为空则调出本次循环
				if (isProcessColspan && preCell3ValueEmpty(obj, i))
				{
					continue;
				}
				
				if (i < NEGLECTFUL_HEAD_CELL_COUNT || i >= lastCellNum - NEGLECTFUL_STERN_CELL_COUNT)
				{
					setFieldValue(cellSummaryInfo, obj, costSummaryFields[index]);
					index++;
				}
				else
				{
					CellMonthSummaryInfo cellMonthSummaryInfo = consCellMonthSummaryInfo(cellSummaryInfo, brandIndex,
							brandsInfos);
					setFieldValue(cellMonthSummaryInfo, obj, rowMonthSummaryField);
					cellMonthSummaryInfos.add(cellMonthSummaryInfo);
					brandIndex++;
				}
			}
			
			cellSummaryInfo.setCellMonthSummaryInfos(cellMonthSummaryInfos);
			
			return cellSummaryInfo;
		}
		
		return null;
	}
	
	/**
	 * 处理结果为空且为前3行，则返回true
	 * 
	 * @param obj
	 *        值
	 * @param i
	 *        列下标
	 * @return
	 */
	
	private boolean preCell3ValueEmpty(Object obj, int i)
	{
		if (null != obj && StringUtils.isEmpty(obj.toString()) && i < NEGLECTFUL_HEAD_CELL_COUNT)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param cellSummaryInfo
	 * @param brandsInfos
	 * @param brandIndex
	 * @return
	 */
	private CellMonthSummaryInfo consCellMonthSummaryInfo(CellSummaryInfo cellSummaryInfo, int brandIndex,
			List<String> brandsInfos)
	{
		CellMonthSummaryInfo monthSummaryInfo = new CellMonthSummaryInfo();
		monthSummaryInfo.setCellSummaryInfo(cellSummaryInfo);
		monthSummaryInfo.setUpdateTime(cellSummaryInfo.getUpdateTime());
		try
		{
			monthSummaryInfo.setBrandName(brandsInfos.get(brandIndex));
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		return monthSummaryInfo;
	}
	
	/**
	 * 实例化对象
	 * 
	 * @param summaryDate
	 *        汇总时间
	 * @return com.innshine.reportExport.entity.CellSummaryInfo
	 */
	private CellSummaryInfo cosCellSummaryInfo(Date summaryDate)
	{
		CellSummaryInfo cellSummaryInfo = new CellSummaryInfo();
		cellSummaryInfo.setSummaryTime(summaryDate);
		cellSummaryInfo.setUpdateTime(new Date());
		return cellSummaryInfo;
	}
	
	/**
	 * 
	 * 解析单行数据，数据格式为： 6602.1003.0100 广告宣传费 展览费
	 * 
	 * @param row
	 *        行对象
	 * @param summaryDate
	 *        汇总时间
	 * @param rowSummaryFields
	 *        行汇总字段列表
	 * @param rowMonthSummaryField
	 *        品牌字段
	 * @param workbook
	 * @param cellSummaryInfo
	 * @param brandsInfos
	 * @return
	 */
	private RowSummaryInfo parseRowAndBrandsData(Row row, Date summaryDate, String[] rowSummaryFields,
			String rowMonthSummaryField, Workbook workbook, CellSummaryInfo cellSummaryInfo, List<String> brandsInfos)
	{
		List<RowMonthSummaryInfo> rowMonthSummaryInfos = new ArrayList<RowMonthSummaryInfo>();
		
		if (null != row)
		{
			RowSummaryInfo rowSummaryInfo = cosRowSummaryInfo(summaryDate);
			rowSummaryInfo.setCellSummaryInfo(cellSummaryInfo);
			
			int index = 0;
			int brandIndex = 0;
			short lastCellNum = row.getLastCellNum();
			for (int i = row.getFirstCellNum(); i < lastCellNum; i++)
			{
				Cell cell = row.getCell(i);
				if (i < NEGLECTFUL_HEAD_CELL_COUNT || i >= lastCellNum - NEGLECTFUL_STERN_CELL_COUNT)
				{
					setFieldValue(rowSummaryInfo, FinanceExcelParseExcelUtils.getCellValue(cell, workbook),
							rowSummaryFields[index]);
					index++;
				}
				else
				{
					RowMonthSummaryInfo monthSummaryInfo = consRowMonthSummaryInfo(rowSummaryInfo, brandIndex,
							brandsInfos);
					setFieldValue(monthSummaryInfo, FinanceExcelParseExcelUtils.getCellValue(cell, workbook),
							rowMonthSummaryField);
					rowMonthSummaryInfos.add(monthSummaryInfo);
					brandIndex++;
				}
			}
			
			rowSummaryInfo.setRowMonthSummaryInfos(rowMonthSummaryInfos);
			return rowSummaryInfo;
		}
		
		return null;
	}
	
	/**
	 * 检测前两行如果都为空，则认为取到了最后底部汇总部分，即：项目毛利 项目毛利率 综合费用等
	 * 
	 * @param row
	 *        行对像
	 * @return true : 已到达底部 ,false :相反
	 */
	private boolean isPreTwoRowValue(Row row)
	{
		if (null != row)
		{
			// 前两行数据为空记数器
			int count = 0;
			for (int i = row.getFirstCellNum(); i < row.getFirstCellNum() + DEFAULT_BOTTOM_START_CELL; i++)
			{
				try
				{
					Cell cell = row.getCell(i);
					
					// 如果其中有合并的列，则认为是费用类型，即：直接费用 直接材料成本+支付宝直接费用
					if (null != cell && StringUtils.isEmpty(cell.getStringCellValue()))
					{
						count++;
					}
				}
				catch (Exception e)
				{
					LOGGER.error(Exceptions.getStackTraceAsString(e));
				}
			}
			
			if (count >= 2)
			{
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 判断当前行中是否存在合并列的行
	 * 
	 * @param cellRangeAddresses
	 *        Excel中所有的合并单元格
	 * @param row
	 *        行对象
	 * @param sheet
	 * @return true:存在 false:不存在
	 */
	private boolean isColspan(List<CellRangeAddress> cellRangeAddresses, Row row, Sheet sheet)
	{
		if (null != row && CollectionUtils.isNotEmpty(cellRangeAddresses))
		{
			for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
			{
				Cell cell = row.getCell(i);
				
				// 如果其中有合并的列，则认为是费用类型，即：直接费用 直接材料成本+支付宝直接费用
				if (null != cell && FinanceExcelParseExcelUtils.isCombineListCell(cellRangeAddresses, cell, sheet))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 获取通用行字段列表
	 * 
	 * @return
	 */
	private String[] getRowSummaryFields()
	{
		String[] arry = new String[] {};
		arry = ArrayUtils.addAll(arry, FinanceExcelParseServiceConstans.COMMON_HEAD_FIELD);
		arry = ArrayUtils.addAll(arry, FinanceExcelParseServiceConstans.COMMON_BODY_FIELD);
		arry = ArrayUtils.addAll(arry, FinanceExcelParseServiceConstans.COMMON_STERM_FIELD);
		return arry;
	}
	
	/**
	 * 获取第一行销售列数据
	 * 
	 * @param sheet
	 *        工作薄对象
	 * @param summaryDate
	 *        本次excel汇总时间
	 * @param brandsInfos
	 *        获取当前导入Excel中的品牌对象
	 * @param customBrandMap
	 *        自定义map对象，key:品牌名 value：数据库中对应的品牌对象
	 * @param rowSummaryInfos
	 *        存放当前解析的行对象
	 * @param workbook
	 * @return
	 */
	
	private void getSalesAmountRowValue(Sheet sheet, Date summaryDate, List<String> brandsInfos,
			Map<String, BrandsInfo> customBrandMap, List<RowSummaryInfo> rowSummaryInfos, Workbook workbook)
	{
		if (null != sheet)
		{
			Row row = sheet.getRow(SHOP_SALES_AMOUT_ROW_INDEX);
			if (row != null)
			{
				// 记录行数
				int summaryIndex = 0;
				
				// 首列位置
				short firstCellNum = row.getFirstCellNum();
				
				// 尾列位置
				short lastCellNum = row.getLastCellNum();
				
				// 获取属性字段列表
				String[] summaryFields = getShopSalesAmountField();
				
				// 获取所有合并单元格
				List<CellRangeAddress> cellRangeAddresses = FinanceExcelParseExcelUtils.getCombineCell(sheet);
				
				// 解析第一行店铺销售金额
				getOneRowValue(sheet, rowSummaryInfos, workbook, row, summaryIndex, firstCellNum, lastCellNum,
						summaryFields, cellRangeAddresses, summaryDate, brandsInfos);
			}
		}
	}
	
	/**
	 * 解析第1行值
	 * 
	 * @param sheet
	 * @param rowSummaryInfos
	 * @param workbook
	 * @param row
	 * @param summaryIndex
	 * @param firstCellNum
	 * @param lastCellNum
	 * @param summaryFields
	 * @param cellRangeAddresses
	 * @param summaryDate
	 *        当前汇总日期
	 * @param brandsInfos
	 */
	private void getOneRowValue(Sheet sheet, List<RowSummaryInfo> rowSummaryInfos, Workbook workbook, Row row,
			int summaryIndex, short firstCellNum, short lastCellNum, String[] summaryFields,
			List<CellRangeAddress> cellRangeAddresses, Date summaryDate, List<String> brandsInfos)
	{
		// 构造合计对象
		RowSummaryInfo rowSummaryInfo = cosRowSummaryInfo(summaryDate);
		
		// 记录本列对应品牌详情
		List<RowMonthSummaryInfo> monthSummaryInfos = new ArrayList<RowMonthSummaryInfo>();
		
		int brandIndex = 0;
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
		{
			try
			{
				Object o = null;
				Cell cell = row.getCell(i);
				
				if (cell != null)
				{
					Object obj = FinanceExcelParseExcelUtils.getCellValue(cell, workbook);
					
					String fieldName = null;
					
					// 处理合计部分，取前3行与后七行
					if ((cell.getColumnIndex() < firstCellNum + NEGLECTFUL_HEAD_CELL_COUNT || cell.getColumnIndex() >= lastCellNum
							- NEGLECTFUL_STERN_CELL_COUNT))
					{
						// 合并的列大于2，则认为该列值为空，不继续向下，跳出本次循环
						if (preCell3ValueEmpty(obj, i))
						{
							continue;
						}
						
						o = rowSummaryInfo;
						fieldName = summaryFields[summaryIndex];
						summaryIndex++;
					}
					else
					{
						o = consRowMonthSummaryInfo(rowSummaryInfo, brandIndex, brandsInfos);
						fieldName = FinanceExcelParseServiceConstans.COMMON_BRAND_AMOUNT_FIELD;
						monthSummaryInfos.add((RowMonthSummaryInfo) o);
						brandIndex++;
					}
					
					setFieldValue(o, obj, fieldName);
					
				}
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		rowSummaryInfo.setRowMonthSummaryInfos(monthSummaryInfos);
		rowSummaryInfos.add(rowSummaryInfo);
	}
	
	/**
	 * 构造行统计品牌销售详情
	 * 
	 * @param rowSummaryInfo
	 * @param brandsInfos
	 * @param brandIndex
	 * @return
	 */
	private RowMonthSummaryInfo consRowMonthSummaryInfo(RowSummaryInfo rowSummaryInfo, int brandIndex,
			List<String> brandsInfos)
	{
		RowMonthSummaryInfo monthSummaryInfo = new RowMonthSummaryInfo();
		monthSummaryInfo.setRowSummaryInfo(rowSummaryInfo);
		monthSummaryInfo.setUpdateTime(rowSummaryInfo.getUpdateTime());
		try
		{
			monthSummaryInfo.setBrandName(brandsInfos.get(brandIndex));
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		return monthSummaryInfo;
	}
	
	/**
	 * 构造行统计对象
	 * 
	 * @param summaryDate
	 *        文件汇总时间
	 * @return RowSummaryInfo
	 */
	private RowSummaryInfo cosRowSummaryInfo(Date summaryDate)
	{
		RowSummaryInfo rowSummaryInfo = new RowSummaryInfo();
		rowSummaryInfo.setSummaryTime(summaryDate);
		rowSummaryInfo.setUpdateTime(new Date());
		return rowSummaryInfo;
	}
	
	/**
	 * 设置字段值
	 * 
	 * @param rowSummaryInfo
	 * @param obj
	 * @param fieldName
	 */
	private void setFieldValue(Object rowSummaryInfo, Object obj, String fieldName)
	{
		try
		{
			if (null != obj)
			{
				String str = obj.toString();
				if (StringUtils.isNotEmpty(str))
				{
					ReflectionUtils.setFieldValue(rowSummaryInfo, fieldName, FieldUtils.findFieldValue(obj, FieldUtils
							.getDeclaredField(rowSummaryInfo.getClass(), fieldName).getType()));
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	// private String[] getBrandFields()
	// {
	//		
	// String[] brandsArray = new String[] {};
	//		
	// // ArrayUtils.add(brandsArray,
	// // FinanceExcelParseServiceConstans.COMMON_BRAND_AMOUNT_FIELD);
	//		
	// return brandsArray;
	// }
	
	/**
	 * 获取第一行销售列数据，汇总部分属性字段名称
	 * <p>
	 * 
	 * @return
	 */
	private String[] getShopSalesAmountField()
	{
		String[] array = new String[] {};
		
		array = ArrayUtils.addAll(array, FinanceExcelParseServiceConstans.COMMON_BODY_FIELD);
		
		array = ArrayUtils.addAll(array, FinanceExcelParseServiceConstans.COMMON_STERM_FIELD);
		
		return array;
	}
	
	/**
	 * 记录文件导入的每步步骤日志
	 * 
	 * @param shiroUser
	 *        当前登陆用户对象
	 * @param sessionId
	 *        sessionid
	 * @param message
	 *        提示信息
	 * @param logLevel
	 *        信息级别
	 * @param remark
	 *        备注
	 */
	public void addFileUploadLogInfo(ShiroUser shiroUser, String sessionId, String message, LogLevel logLevel,
			String remark, boolean messageStatus)
	{
		if (null != shiroUser && StringUtils.isNotEmpty(message) && null != logLevel)
		{
			try
			{
				FinanceFileLogInfo financeFileLogInfo = new FinanceFileLogInfo();
				financeFileLogInfo.setUpdateTime(new Date());
				financeFileLogInfo.setRemark(remark);
				financeFileLogInfo.setMessage(message);
				financeFileLogInfo.setMessageLevel(logLevel.value());
				financeFileLogInfo.setUserAddress(shiroUser.getIpAddress());
				financeFileLogInfo.setUserName(shiroUser.getLoginName());
				financeFileLogInfo.setMessageStatus(messageStatus);
				financeFileLogInfo.setSessionId(sessionId);
				financeFileLogInfo.setUserId((shiroUser.getUser() != null ? shiroUser.getUser().getId() : null));
				financeFileLogInfoService.saveOrUpdateAndFlush(financeFileLogInfo);
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
	}
	
	/**
	 * 记录文件导入的每步步骤日志
	 * 
	 * @param shiroUser
	 *        当前登陆用户对象
	 * @param sessionId
	 *        sessionid
	 * @param message
	 *        提示信息
	 * @param logLevel
	 *        信息级别
	 */
	public void addFileUploadLogInfo(ShiroUser shiroUser, String sessionId, String message, LogLevel logLevel)
	{
		addFileUploadLogInfo(shiroUser, sessionId, message, logLevel, null, false);
	}
	
	/**
	 * 记录文件导入的每步步骤日志
	 * 
	 * @param shiroUser
	 *        当前登陆用户对象
	 * @param sessionId
	 *        sessionid
	 * @param message
	 *        提示信息
	 * @param logLevel
	 *        信息级别
	 * @param messageStatus
	 *        true:标识该记录结束，flase:相反
	 */
	public void addFileUploadLogInfo(ShiroUser shiroUser, String sessionId, String message, LogLevel logLevel,
			boolean messageStatus)
	{
		addFileUploadLogInfo(shiroUser, sessionId, message, logLevel, null, messageStatus);
	}
}
