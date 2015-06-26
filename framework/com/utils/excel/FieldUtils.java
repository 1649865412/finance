package com.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 解析xls 和 txt文件 字段处理类 <code>FieldUtil.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class FieldUtils
{
	
	public static Map<String, Field> getClazzFields(Class<?> clazz)
	{
		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
		{
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields)
			{
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
		}
		return fieldMap;
	}
	
	public static Map<String, String> getValues(Class<?> clazz, Object obj) throws IllegalArgumentException,
			IllegalAccessException
	{
		Map<String, Field> fields = getClazzFields(clazz);
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		Set<String> sets = fields.keySet();
		for (String key : sets)
		{
			Field field = fields.get(key);
			Object value = findFieldValue(field.get(obj), field.getType());
			if (value == null)
			{
				value = "";
			}
			valueMap.put(key, String.valueOf(value));
		}
		return valueMap;
	}
	
	/**
	 * 根据字段名和类原型，取出对相应用字段
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(final Class clazz, final String fieldName)
	{
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
		{
			try
			{
				Field[] fields = superClass.getDeclaredFields();
				for (Field field : fields)
				{
					if (fieldName.equals(field.getName()))
					{
						return field;
					}
				}
				
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	/**
	 * 根据字段名和类原型，取出对相应用方法
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Method getDeclaredMethod(final Class clazz, final String fieldName)
	{
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
		{
			try
			{
				Method[] methods = superClass.getDeclaredMethods();
				for (Method method : methods)
				{
					if (method.getName().toUpperCase().endsWith(fieldName.toUpperCase()))
					{
						return method;
					}
				}
				
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	// 解析字段类型并返回解析的值
	public static Object findFieldValue(Object value, Class fieldType)
	{
		Object result = null;
		if ((fieldType == Integer.class) || (fieldType == Integer.TYPE))
			result = Integer.valueOf((int) longValue(value));
		if ((fieldType == Double.class) || (fieldType == Double.TYPE))
			result = new Double(doubleValue(value));
		if ((fieldType == Boolean.class) || (fieldType == Boolean.TYPE))
			result = booleanValue(value) ? Boolean.TRUE : Boolean.FALSE;
		if ((fieldType == Byte.class) || (fieldType == Byte.TYPE))
			result = Byte.valueOf((byte) longValue(value));
		if ((fieldType == Character.class) || (fieldType == Character.TYPE))
			result = new Character((char) longValue(value));
		if ((fieldType == Short.class) || (fieldType == Short.TYPE))
			result = Short.valueOf((short) longValue(value));
		if ((fieldType == Long.class) || (fieldType == Long.TYPE))
			result = Long.valueOf(longValue(value));
		if ((fieldType == Float.class) || (fieldType == Float.TYPE))
			result = new Float(doubleValue(value));
		if (fieldType == BigInteger.class)
			result = bigIntValue(value);
		if (fieldType == BigDecimal.class)
			result = bigDecValue(value);
		if (fieldType == String.class)
			result = stringValue(value);
		if (fieldType == Date.class)
		{
			try
			{
				result = dateValue(value);
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static boolean booleanValue(Object value)
	{
		if (value == null)
			return false;
		Class c = value.getClass();
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue();
		// if ( c == String.class )
		// return ((String)value).length() > 0;
		if (c == Character.class)
			return ((Character) value).charValue() != 0;
		if (value instanceof Number)
			return ((Number) value).doubleValue() != 0;
		return true; // non-null
	}
	
	public static Enum<?> enumValue(Class toClass, Object o)
	{
		Enum<?> result = null;
		if (o == null)
		{
			result = null;
		}
		else if (o instanceof String[])
		{
			result = Enum.valueOf(toClass, ((String[]) o)[0]);
		}
		else if (o instanceof String)
		{
			result = Enum.valueOf(toClass, (String) o);
		}
		return result;
	}
	
	public static long longValue(Object value) throws NumberFormatException
	{
		if (value == null)
			return 0L;
		Class c = value.getClass();
		if (c.getSuperclass() == Number.class)
			return ((Number) value).longValue();
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue() ? 1 : 0;
		if (c == Character.class)
			return ((Character) value).charValue();
		return Long.valueOf(stringValue(value, true));
	}
	
	public static double doubleValue(Object value) throws NumberFormatException
	{
		if (value == null)
			return 0.0;
		Class c = value.getClass();
		if (c.getSuperclass() == Number.class)
			return ((Number) value).doubleValue();
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue() ? 1 : 0;
		if (c == Character.class)
			return ((Character) value).charValue();
		String s = stringValue(value, true);
		
		return (s.length() == 0) ? 0.0 : Double.parseDouble(s);
	}
	
	public static BigInteger bigIntValue(Object value) throws NumberFormatException
	{
		if (value == null)
			return BigInteger.valueOf(0L);
		Class c = value.getClass();
		if (c == BigInteger.class)
			return (BigInteger) value;
		if (c == BigDecimal.class)
			return ((BigDecimal) value).toBigInteger();
		if (c.getSuperclass() == Number.class)
			return BigInteger.valueOf(((Number) value).longValue());
		if (c == Boolean.class)
			return BigInteger.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
		if (c == Character.class)
			return BigInteger.valueOf(((Character) value).charValue());
		return new BigInteger(stringValue(value, true));
	}
	
	public static BigDecimal bigDecValue(Object value) throws NumberFormatException
	{
		if (value == null)
			return BigDecimal.valueOf(0L);
		Class c = value.getClass();
		if (c == BigDecimal.class)
			return (BigDecimal) value;
		if (c == BigInteger.class)
			return new BigDecimal((BigInteger) value);
		if (c.getSuperclass() == Number.class)
			return new BigDecimal(((Number) value).doubleValue());
		if (c == Boolean.class)
			return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
		if (c == Character.class)
			return BigDecimal.valueOf(((Character) value).charValue());
		return new BigDecimal(stringValue(value, true));
	}
	
	public static Date dateValue(Object value) throws ParseException
	{
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
		String date = String.valueOf(value);
		if (date == null || date.equals("null") || date.trim().equals(""))
		{
			return null;
		}
		else if (date.length() <= 10)
		{
			return DATE_FORMAT_SHORT.parse(date);
		}
		else
		{
			return DATE_FORMAT.parse(date);
		}
	}
	
	public static String stringValue(Object value, boolean trim)
	{
		String result;
		
		if (value == null)
		{
			result = "";
		}
		else
		{
			result = value.toString();
			// System.out.println("--================="+result);
			if (trim)
			{
				result = result.trim();
			}
		}
		return result;
	}
	
	public static String stringValue(Object value)
	{
		return stringValue(value, false);
	}
	
	/**
	 * Excel表行转列
	 * 
	 * @param locateRowGetLen
	 *            定位几行的长度为列长,首行为0
	 */
	public static File excelRowToCol(File file, int locateRowGetLen)
	{
		HSSFWorkbook newWorkBook = new HSSFWorkbook();
		HSSFSheet newSheet = newWorkBook.createSheet();
		InputStream in = null;
		OutputStream out = null;
		try
		{
			in = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			// 仅处理第一张表格
			HSSFSheet sheet1 = workbook.getSheetAt(0);
			// 行数
			int rowNum = sheet1.getLastRowNum() - sheet1.getFirstRowNum() + 1;
			// 列数
			int colNum = sheet1.getRow(locateRowGetLen).getLastCellNum() + 1;
			
			// 先循环数据源列 当作 新表行索引，新建行
			for (int i = 0; i < colNum; i++)
			{
				// 创建每一行
				newSheet.createRow(i);
			}
			// 循环数据源取单元格的值赋值到新表
			for (int i = 0; i < rowNum; i++)
			{
				for (int j = 0; j < colNum; j++)
				{
					// 数据源的列即为新表的行
					HSSFRow newRow = newSheet.getRow(j);
					// 取当前单元格的值
					HSSFCell oldTableCell = sheet1.getRow(i).getCell(j);
					// 设置相应位置单元格
					HSSFCell newTableCell = newRow.createCell(i);
					setCellValue(oldTableCell, newTableCell);
				}
			}
			out = new FileOutputStream(file);
			newWorkBook.write(out);
			return file;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/** 将旧单元格的值赋值到新单元格 */
	private static void setCellValue(HSSFCell oldTableCell, HSSFCell newTableCell)
	{
		if (oldTableCell != null && newTableCell != null)
		{
			int cellType = oldTableCell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				newTableCell.setCellValue(oldTableCell.getNumericCellValue());
			}
			else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN)
			{
				newTableCell.setCellValue(oldTableCell.getBooleanCellValue());
			}
			else if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				newTableCell.setCellValue(oldTableCell.getRichStringCellValue());
			}
			else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				newTableCell.setCellValue("");
			}
			else if (cellType == HSSFCell.CELL_TYPE_ERROR)
			{
				newTableCell.setCellValue(oldTableCell.getErrorCellValue());
			}
			else if (cellType == HSSFCell.CELL_TYPE_FORMULA)
			{
				newTableCell.setCellValue(oldTableCell.getCellFormula());
			}
			
		}
		
	}
}
