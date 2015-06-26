package com.innshine.reportExport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.Exceptions;

/**
 * 解析财务报表excel工作类 <code>FinanceExcelParseExcelUtils.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @author auto 时间 2015-1-19 下午01:50:33
 * @version 1.0 </br>最后修改人 无
 */
public class FinanceExcelParseExcelUtils
{
	/**
	 * 默认第一个sheet
	 */
	private static final int DEFAULT_SHEETAT_INDEX = 0;
	
	/**
	 * excel文件后辍
	 */
	private static final String XLSX = ".xlsx";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceExcelParseExcelUtils.class);
	
	/**
	 * 功能: 创建操作excel对应版本对象 ，支持2003、2007、2010
	 * <p>
	 * 
	 * @return Workbook 有可能返回null ,需要对null进行处理
	 */
	public static Workbook getCommonWorkbook(File file)
	{
		if (null == file)
		{
			return null;
		}
		
		try
		{
			InputStream is = new FileInputStream(file);
			
			if (file.getPath().indexOf(XLSX) > -1)
			{
				return new XSSFWorkbook(is);
			}
			else
			{
				return new HSSFWorkbook(is);
			}
			
		}
		catch (FileNotFoundException e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IOException e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	/**
	 * 获取sheet名称
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheetAt
	 *        工作薄下标，默认为0
	 * @return 工作薄名称
	 */
	public static String getSheetName(Workbook workbook, int sheetAt)
	{
		if (workbook != null)
		{
			Sheet sheet = getSheet(workbook, sheetAt);
			
			if (null != sheet)
			{
				return sheet.getSheetName();
			}
		}
		
		return null;
	}
	
	/**
	 * 获取sheet名称
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheetAt
	 *        工作薄下标，默认为0
	 * @return 工作薄名称
	 */
	public static Sheet getSheet(Workbook workbook, int sheetAt)
	{
		if (workbook != null)
		{
			return workbook.getSheetAt(sheetAt);
		}
		
		return null;
	}
	
	/**
	 * 获取sheet名称
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param sheetAt
	 *        工作薄下标，默认为0
	 * @return 工作薄名称
	 */
	public static Sheet getSheet(Workbook workbook)
	{
		if (workbook != null)
		{
			return workbook.getSheetAt(DEFAULT_SHEETAT_INDEX);
		}
		
		return null;
	}
	
	/**
	 * 获取sheet名称
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @return 工作薄名称
	 */
	public static String getSheetName(Workbook workbook)
	{
		return getSheetName(workbook, DEFAULT_SHEETAT_INDEX);
	}
	
	/**
	 * 获取该sheet中所有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	public static List<CellRangeAddress> getCombineCell(Sheet sheet)
	{
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		if (null != sheet)
		{
			// 获得一个 sheet 中合并单元格的数量
			int sheetmergerCount = sheet.getNumMergedRegions();
			
			// 遍历合并单元格
			for (int i = 0; i < sheetmergerCount; i++)
			{
				// 获得合并单元格加入list中
				CellRangeAddress ca = (CellRangeAddress) sheet.getMergedRegion(i);
				list.add(ca);
			}
		}
		
		return list;
	}
	
	/**
	 * 判断单元格是否为合并单元格
	 * 
	 * @param listCombineCell
	 *        存放合并单元格的list
	 * @param cell
	 *        需要判断的单元格
	 * @param sheet
	 *        sheet
	 * @return
	 */
	public static boolean isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
	{
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : listCombineCell)
		{
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC)
			{
				if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 判断单元格是否为合并列单元格
	 * 
	 * @param listCombineCell
	 *        存放合并单元格的list
	 * @param cell
	 *        需要判断的单元格
	 * @param sheet
	 *        sheet
	 * @return
	 */
	public static boolean isCombineListCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
	{
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : listCombineCell)
		{
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC)
			{
				if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR)
				{
					if (lastC - firstC >= 1)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 处理Excel 单元格字段值
	 */
	public static Object getCellValue(Cell cell, Workbook workbook)
	{
		if (cell != null)
		{
			
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				String dataStr = getDateValue(cell);
				if (StringUtils.isNotBlank(dataStr))
				{
					return dataStr;
				}
				
				// 格式化double数据，以防止长整型数据变成科学计数法形式，对Double本身或int类型不影响。
				// 如：694E+537059828
				DecimalFormat df = new DecimalFormat("#.##");
				try
				{
					dataStr = df.format(cell.getNumericCellValue());
				}
				catch (ArithmeticException e)
				{
					e.printStackTrace();
				}
				
				return dataStr;
			}
			else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN)
			{
				return cell.getBooleanCellValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				RichTextString hr = cell.getRichStringCellValue();
				return hr.toString();
			}
			else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				return "";
			}
			else if (cellType == HSSFCell.CELL_TYPE_ERROR)
			{
				return cell.getErrorCellValue();
			}
			else if (cellType == HSSFCell.CELL_TYPE_FORMULA)
			{
				return formulaGetValue(cell, workbook);
				// return cell.getCellFormula();
			}
			else
			{
				return null;
			}
			
		}
		else
		{
			return null;
		}
	}
	
	public static String getDateValue(Cell cell)
	{
		if (HSSFDateUtil.isCellDateFormatted(cell))
		{
			SimpleDateFormat sdf = null;
			short format = cell.getCellStyle().getDataFormat();
			if (format == 14 || format == 31 || format == 57 || format == 58)
			{
				// 日期 yyyy-MM-dd HH:mm:ss
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			else if (format == 20 || format == 32)
			{
				// 日期格式
				sdf = new SimpleDateFormat("HH:mm");
			}
			
			Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			
			return null == sdf ? null : sdf.format(date);
			
		}
		
		return null;
	}
	
	/**
	 * 执行单元格公式
	 * 
	 * @param cell
	 *        单元格
	 * @param workbook
	 *        工作薄对象
	 * @return Object 单元格值
	 */
	private static Object formulaGetValue(Cell cell, Workbook workbook)
	{
		FormulaEvaluator eval = null;
		
		if (workbook instanceof HSSFWorkbook)
		{
			eval = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}
		
		else if (workbook instanceof XSSFWorkbook)
		{
			eval = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}
		
		Object objValue = null;
		if (null != eval)
		{
			try
			{
				// evaluateInCell(Cell cell)
				// 方法是计算公式，并将原公式替换为计算结果，
				// 也就是说该单元格的类型不在是Cell.CELL_TYPE_FORMULA而是Cell.CELL_TYPE_NUMBERIC
				objValue = eval.evaluateInCell(cell).getNumericCellValue();
				
				// evaluateFormulaCell(Cell cell)方法，计算公式保存结果，但不改变公式
				// objValue = eval.evaluateFormulaCell(cell);
			}
			catch (Exception e)
			{
				// cell.setCellType(Cell.CELL_TYPE_STRING);
				objValue = null;
				LOGGER.error(Exceptions.getStackTraceAsString(e));
				// System.out.println(objValue);
			}
		}
		
		return objValue;
	}
}
