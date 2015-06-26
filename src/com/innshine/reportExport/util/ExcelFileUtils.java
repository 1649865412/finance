package com.innshine.reportExport.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.base.dao.sql.ReflectionUtils;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.utils.Exceptions;
import com.utils.FileUtils;
import com.utils.excel.ExcelToBeanUtils;
import com.utils.excel.FieldUtils;

/**
 * Excel文件导入、导出工具类 <code>FileUtils.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ExcelFileUtils
{
	/**
	 * 2007版文件后缀
	 */
	private static final String EXCLE_SUFFIX_XLSX = ".xlsx";
	
	/**
	 * 2003版文件后缀
	 */
	private static final String EXCLE_SUFFIX_XLS = ".xls";
	
	public static final String DOUBLE_ZERO = "0.0";
	
	public static final String INTEGER_ZERO = "0";
	
	/**
	 * 空格符
	 */
	public static final String BLANK_CHARACTER = " ";
	
	/**
	 * 默认最大支持100M文件导入 (100 * 1024 * 1024 * 1024 )
	 */
	public static final long FILE_SIZE = 104857600L;
	
	/**
	 * 文件上传临时目录
	 */
	public static final String FILE_TEMP_DIR = System.getProperty("user.dir") + "/tmp/";
	
	/**
	 * 支持的excel类型
	 */
	public static final String[] FILE_TYPE = new String[] { "xls", "xlsx" };
	
	/**
	 * WEB-INF
	 */
	public static final String WEB_INF = "WEB-INF";
	
	/**
	 * 空格
	 */
	public static final String BLANK_SPACE_20 = "%20";
	
	/**
	 * 文件下载响应
	 */
	public static final String APPLICATION_X_MSDOWNLOAD = "application/x-msdownload;";
	
	/**
	 * 编码
	 */
	public static final String ENCODE_ISO8859_1 = "ISO8859-1";
	
	/**
	 * 火狐浏览器标识
	 */
	public static final String FIREFOX = "firefox";
	
	/**
	 * User-Agent
	 */
	public static final String USER_AGENT = "User-Agent";
	
	/**
	 * 默认编码
	 */
	public static final String DEAULT_ENCODE_UTF_8 = "UTF-8";
	
	/**
	 * 导出临时目录
	 */
	public static final String EXPORT = "export/";
	
	/**
	 * 上传临时目录
	 */
	public static final String UPLOAD = "upload/";
	
	/**
	 * 下划线
	 */
	public static final String UNDER_LINE = "_";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ExcelFileUtils.class);
	
	/**
	 * 处理Excel文件上传
	 * <p>
	 * 
	 * @param files
	 *        文件列表
	 * @return ResponseEntity<String>
	 */
	public static ResponseEntity<String> fileExcelUpload(MultipartFile[] files)
	{
		String uploadPath = ExcelFileUtils.FILE_TEMP_DIR + UPLOAD;
		
		if (!createDir(uploadPath))
		{
			return new ResponseEntity<String>("创建文件上传路径失败，" + uploadPath, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for (MultipartFile file : files)
		{
			String fileExt = FileUtils.getFileExt(file.getOriginalFilename());
			if (!ArrayUtils.contains(ExcelFileUtils.FILE_TYPE, fileExt))
			{
				return new ResponseEntity<String>("只允许上传" + ArrayUtils.toString(ExcelFileUtils.FILE_TYPE)
						+ "格式的Excel文件！", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if (file.getSize() > ExcelFileUtils.FILE_SIZE)
			{
				return new ResponseEntity<String>("只允许上传100M以内的图标！", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			File uploadedFile = new File(uploadPath, file.getOriginalFilename());
			if (uploadedFile.exists())
			{
				uploadedFile.delete();
			}
			
			try
			{
				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { file.getOriginalFilename() }));
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * 关闭资源
	 * <p>
	 * 
	 * @param closeable
	 *        需要关闭的对象 列表
	 */
	public static void closeable(Closeable... closeable)
	{
		if (null != closeable && ArrayUtils.isNotEmpty(closeable))

		{
			for (Closeable closeableObj : closeable)
			{
				if (null != closeableObj)
				{
					try
					{
						closeableObj.close();
					}
					catch (IOException e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
					catch (Exception e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
				}
			}
		}
	}
	
	/**
	 * 获取模版路径
	 * 
	 * @param modelFilePath
	 *        模版文件路径
	 * @return 返回完整路径
	 */
	public static String getExcelModelPath(String modelFilePath)

	{
		String classPath = ExcelFileUtils.class.getResource("").getPath();
		
		String tempPath = null;
		
		if (StringUtils.isNotBlank(classPath))
		{
			String tmp = classPath.substring(0, classPath.indexOf(WEB_INF));
			
			tempPath = tmp + modelFilePath;
		}
		
		return tempPath;
	}
	
	/**
	 * 清除上传的Excel文件
	 * <p>
	 * 
	 * @param file
	 *        上传的Excel文件
	 */
	public static void deleteFile(File file)
	{
		try
		{
			if (null != file && file.exists())
			{
				file.delete();
			}
		}
		catch (SecurityException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 根据模版导出Excel列表，默认忽略表头。只生成1张表工作表
	 * <p>
	 * 
	 * @param templateFile
	 *        模版文件
	 * @param dataList
	 *        数据列表
	 * @param sheetName
	 *        工作蒲名称
	 * @return 生成后的Excel文件，完整路径
	 */
	public static <T> String exportByTemplateToExcel(File templateFile, List<T> dataList, Field[] fields)
	{
		if (null == templateFile || null == dataList || ArrayUtils.isEmpty(fields))
		{
			return null;
			
		}
		
		File targerFile = new File(FILE_TEMP_DIR + EXPORT + (new Date().getTime()) + "_" + templateFile.getName());
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try
		{
			
			// 目录是否存在
			if (!createDir(targerFile.getParent()))
			{
				return null;
			}
			
			// 创建模版文件
			if (!createModelFile(templateFile, targerFile))
			{
				return null;
			}
			
			inputStream = new FileInputStream(targerFile);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
			
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			
			T obj = null;
			
			for (int i = 0; i < dataList.size(); i++)
			{
				obj = dataList.get(i);
				
				if (null != obj)
				{
					
					// 创建行对象
					XSSFRow xssfRow = xssfSheet.createRow((i + 1));
					for (int j = 0; j < fields.length; j++)
					{
						Field field = fields[j];
						
						XSSFCell xssfCell = xssfRow.createCell(j);
						if (null != field)
						{
							field.setAccessible(true);
							Object objValue = FieldUtils.findFieldValue(field.get(obj), field.getType());
							String tmpValue = String.valueOf(objValue);
							// 过滤0 与0.0的数值
							if (null != objValue
									&& (INTEGER_ZERO.equalsIgnoreCase(tmpValue) || DOUBLE_ZERO
											.equalsIgnoreCase(tmpValue)))
							{
								tmpValue = "";
							}
							xssfCell.setCellValue(tmpValue);
						}
						else
						{
							xssfCell.setCellValue("");
						}
						
						xssfCell.setCellStyle(getXssfCellStyle(xssfWorkbook));
						
					}
					
				}
			}
			
			outputStream = new FileOutputStream(targerFile);
			
			// 写入数据
			xssfWorkbook.write(outputStream);
			
		}
		catch (IOException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IllegalArgumentException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IllegalAccessException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		finally
		{
			closeable(new Closeable[] { inputStream, outputStream });
		}
		
		return targerFile.getPath();
	}
	
	/**
	 * 根据模版导出Excel列表，默认忽略表头。只生成1张表工作表
	 * <p>
	 * 
	 * @param dataList
	 *        数据列表
	 * @param isImage
	 *        是否需要导入图片 true：有图片导入
	 * @param fieldNames
	 *        哪些字段为存储图片路径
	 * @param display
	 *        显示的字段列表
	 * @return 生成后的Excel文件，完整路径
	 */
	public static <T> Workbook exportByTemplateToExcel(List<T> dataList, Vector<String> display, Vector<String> header)
	{
		if (null == dataList || CollectionUtils.isEmpty(display))
		{
			return null;
			
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try
		{
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			
			XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet1");
			
			XSSFCellStyle xssfCellStyle = ExcelFileUtils.getXssfCellStyle(xssfWorkbook);
			
			// 写入标题行
			writeSheetTitle(xssfSheet, header, 0, xssfCellStyle);
			
			T obj = null;
			
			for (int i = 0; i < dataList.size(); i++)
			{
				obj = dataList.get(i);
				
				if (null != obj)
				{
					
					int startRowIndex = i + 1;
					// 创建行对象
					XSSFRow xssfRow = xssfSheet.createRow((startRowIndex));
					xssfRow.setHeight((short) 600);
					
					for (int j = 0; j < display.size(); j++)
					{
						String displayFieldName = display.get(j);
						Object objValue = ReflectionUtils.getFieldValue(obj, displayFieldName);
						
						XSSFCell xssfCell = xssfRow.createCell(j);
						if (null != objValue)
						{
							String tmpValue = String.valueOf(objValue);
							
							// 过滤0 与0.0的数值
							if (null != objValue
									&& (ExcelFileUtils.INTEGER_ZERO.equalsIgnoreCase(tmpValue) || ExcelFileUtils.DOUBLE_ZERO
											.equalsIgnoreCase(tmpValue)))
							{
								tmpValue = "";
							}
							
							xssfCell.setCellValue(tmpValue);
							
						}
						else
						{
							xssfCell.setCellValue("");
						}
						
						xssfCell.setCellStyle(ExcelFileUtils.getXssfCellStyle(xssfWorkbook));
						
					}
					
				}
			}
			// 写入数据
			return xssfWorkbook;
			
		}
		catch (IllegalArgumentException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		finally
		{
			ExcelFileUtils.closeable(new Closeable[] { inputStream, outputStream });
		}
		
		return null;
	}
	
	/**
	 * 写入标题行
	 * 
	 * @param xssfSheet
	 *        工作表对象
	 * @param header
	 *        表头
	 * @param startRow
	 *        开始行
	 * @param xssfCellStyle
	 */
	private static void writeSheetTitle(XSSFSheet xssfSheet, Vector<String> header, int startRow,
			XSSFCellStyle xssfCellStyle)
	{
		if (CollectionUtils.isNotEmpty(header))
		{
			XSSFRow xssfRow = xssfSheet.createRow(startRow);
			for (int i = 0; i < header.size(); i++)
			{
				XSSFCell xssfCell = xssfRow.createCell(i);
				xssfCell.setCellValue(header.get(i));
				xssfCell.setCellStyle(xssfCellStyle);
			}
		}
	}
	
	/**
	 * 设置公共样式
	 * <p>
	 * 
	 * @param xssfWorkbook
	 *        工作薄对象
	 * @return XSSFCellStyle 返回列样式
	 */
	public static XSSFCellStyle getXssfCellStyle(XSSFWorkbook xssfWorkbook)
	{
		XSSFCellStyle xssfCellStyle = xssfWorkbook.createCellStyle();
		xssfCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
		return xssfCellStyle;
	}
	
	/**
	 * 创建导出Excel，根据模版导出，适用于简单单行导出
	 * 
	 * @param objects
	 *        需要导出的数据列表
	 * @param modelClass
	 *        对应模版
	 * @param beanClass
	 *        数据列表对应实体bea n
	 * @param modelFilePath
	 *        导出模版路径
	 * @return 导出成功后的完整Excel文件路径
	 */
	public static <T, M> String createExportExcel(List<T> objects, Class<M> modelClass, Class<T> beanClass,
			String modelFilePath)
	{
		
		String tmpPath = null;
		
		if (null == modelClass || null == beanClass || StringUtils.isBlank(modelFilePath))
		{
			return tmpPath;
		}
		
		List<M> models = reflectSetValue(objects, modelClass, beanClass);
		
		tmpPath = ExcelFileUtils.exportByTemplateToExcel(new File(ExcelFileUtils.getExcelModelPath(modelFilePath)),
				models, modelClass.getDeclaredFields());
		
		return tmpPath;
	}
	
	/**
	 * 
	 * 通过反射设置属性值，最终返回模版列表
	 * 
	 * @param objects
	 *        集合对象
	 * @param modelClass
	 *        模版class
	 * @param beanClass
	 *        实体bean
	 * @return List< Object> 模版列表
	 */
	public static <T, M> List<M> reflectSetValue(List<T> objects, Class<M> modelClass, Class<T> beanClass)
	{
		
		Vector<String> fieldNames = new Vector<String>();
		
		// 反射获取实体bean ，所有定义属性列表
		ExcelToBeanUtils.refSupperFieldsName(fieldNames, beanClass);
		
		List<M> models = new ArrayList<M>();
		
		for (Object obj : objects)

		{
			M objData = null;
			try
			{
				objData = modelClass.newInstance();
			}
			catch (InstantiationException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			catch (IllegalAccessException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			
			if (null != objData)
			{
				// 给模型类通过反射赋值
				ExcelToBeanUtils.reflectSetValue(fieldNames, obj, objData);
				
				// 增加至模型导出列表
				models.add(objData);
			}
		}
		return models;
	}
	
	/**
	 * Excel文件导出,destFileName 为空，则按原始文件导出
	 * <p>
	 * 
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param filePath
	 *        文件路径
	 * @param destFileName
	 *        重命令文件名
	 * @param isDeleteFile
	 *        true:删除原始文件 false:不删除
	 * @throws IOException 
	 */
	public static void excelExport(HttpServletRequest request, HttpServletResponse response, String filePath,
			String destFileName, boolean isDeleteFile) throws IOException
	{
		File file = null;
		File destFile = null;
		if (StringUtils.isNotEmpty(filePath))
		{
			file = new File(filePath);
			if (StringUtils.isNotBlank(destFileName))
			{
				destFile = new File(file.getParent() + destFileName + FileUtils.getFileExt(file.getName()));
			}
			else
			{
				destFile = file;
			}
			
			// 读到流中
			InputStream inStream = null;
			
			OutputStream outputStream = null;
			
			try
			{
				if (StringUtils.isNotEmpty(destFileName))
				{
					// 给文件重名名
					org.apache.commons.io.FileUtils.moveFile(file, destFile);
				}
				
				inStream = new FileInputStream(destFile);
				
				outputStream = response.getOutputStream();
				
				response.reset();
				response.setContentType(APPLICATION_X_MSDOWNLOAD);
				response.setCharacterEncoding(ExcelFileUtils.DEAULT_ENCODE_UTF_8);
				
				String fileName = destFile.getName();
				
				if (request.getHeader(USER_AGENT).toLowerCase().indexOf(FIREFOX) > 0)
				{
					fileName = new String(fileName.getBytes(ExcelFileUtils.DEAULT_ENCODE_UTF_8), ENCODE_ISO8859_1);// firefox浏览器
					
				}
				else
				{
					// 处理文件名空格%20
					fileName = URLEncoder.encode(fileName, ExcelFileUtils.DEAULT_ENCODE_UTF_8).replace(BLANK_SPACE_20,
							BLANK_CHARACTER);
				}
				
				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len;
				
				while ((len = inStream.read(b)) > 0)
				{
					outputStream.write(b, 0, len);
				}
				
				outputStream.flush();
			}
			catch (FileNotFoundException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			catch (IOException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
				throw e;
			}
			finally
			{
				if (null != inStream)
				{
					try
					{
						inStream.close();
					}
					catch (IOException e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
				}
				
				if (null != outputStream)
				{
					try
					{
						outputStream.close();
					}
					catch (IOException e)
					{
						LOG.error(Exceptions.getStackTraceAsString(e));
					}
				}
			}
			
			if (isDeleteFile)
			{
				deleteFile(file);
				deleteFile(destFile);
			};
			
		}
		else
		{
			throw new FileNotFoundException("对不起，文件不存在！");
			
		}
		
	}
	
	/**
	 * 导文件
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param fileName
	 *        文件名带后辍
	 * @throws IOException
	 */
	// public static void excelExport(Workbook workbook, HttpServletRequest
	// request, HttpServletResponse response,
	// String fileName) throws IOException
	// {
	// ExcelModelTool.setResponses(request, response,
	// StringUtils.isEmpty(fileName) ? (new Date().getTime() +
	// getSuffix(workbook)) : fileName);
	//		
	// OutputStream out = response.getOutputStream();
	// workbook.write(out);
	// }
	
	/**
	 * 根据工作薄对象，获取相应文件后辍
	 * <p>
	 * 
	 * 
	 * @param workbook
	 *        工作薄对象
	 * @return 返回.xls、.xlsx后辍
	 */
	public static String getSuffix(Workbook workbook)
	{
		if (workbook instanceof HSSFWorkbook)
		{
			return EXCLE_SUFFIX_XLS;
		}
		
		return EXCLE_SUFFIX_XLSX;
	}
	
	/**
	 * 创建模版文件
	 * <p>
	 * 
	 * @param templateFile
	 *        模版文件
	 * @param targerFile
	 *        目标文件
	 */
	public static boolean createModelFile(File templateFile, File targerFile)
	{
		boolean flag = false;
		if (null != templateFile && null != targerFile)
		{
			try
			{
				deleteFile(targerFile);
				
				org.apache.commons.io.FileUtils.copyFile(templateFile, targerFile);
				
				flag = true;
			}
			catch (IOException e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return flag;
	}
	
	/**
	 * Excel文件导出
	 * <p>
	 * 
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param filePath
	 *        文件路径
	 * @throws IOException 
	 */
	public static void excelExport(HttpServletRequest request, HttpServletResponse response, String filePath)
			throws IOException
	{
		excelExport(request, response, filePath, null, true);
	}
	
	/**
	 * Excel文件导出
	 * <p>
	 * 
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param filePath
	 *        文件路径
	 * @param fileName
	 *        文件名
	 * @throws IOException 
	 */
	public static void excelExport(HttpServletRequest request, HttpServletResponse response, String filePath,
			String fileName) throws IOException
	{
		excelExport(request, response, filePath, fileName, true);
	}
	
	/**
	 *文件导出， 默认不会删除原始文件
	 * <p>
	 * 
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param filePath
	 *        文件路径
	 * @param fileName
	 *        文件名
	 * @throws IOException 
	 */
	public static void fileExport(HttpServletRequest request, HttpServletResponse response, String filePath,
			String fileName) throws IOException
	{
		excelExport(request, response, filePath, fileName, false);
	}
	
	/**
	 *文件导出， 默认不会删除原始文件
	 * <p>
	 * 
	 * @param request
	 *        请求对象
	 * @param response
	 *        响应对象
	 * @param filePath
	 *        文件路径 文件名
	 * @throws IOException 
	 */
	public static void fileExport(HttpServletRequest request, HttpServletResponse response, String filePath)
			throws IOException
	{
		excelExport(request, response, filePath, null, false);
	}
	
	/**
	 * 解码文件名
	 * <p>
	 * 
	 * @param fileName
	 *        文件名
	 * @return 返回解码后的文件名
	 */
	public static String decodeFileName(String fileName)
	{
		String tmpFileName = null;
		try
		{
			if (StringUtils.isNotBlank(fileName))
			{
				tmpFileName = URLDecoder.decode(fileName, DEAULT_ENCODE_UTF_8);
			}
		}
		catch (UnsupportedEncodingException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return tmpFileName;
	}
	
	/**
	 * 创建文件，如存在则不创建，返回默认值 已创建true
	 * <p>
	 * 
	 * @param directoryPath
	 *        目录路径完整路径
	 * @return true已存在 false : 创建失败
	 */
	public static boolean createDir(String directoryPath)
	{
		boolean flag = true;
		if (StringUtils.isNotBlank(directoryPath))
		{
			
			File file = new File(directoryPath);
			if (!file.exists())
			{
				try
				{
					flag = file.mkdirs();
				}
				catch (SecurityException e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
				catch (Exception e)
				{
					LOG.error(Exceptions.getStackTraceAsString(e));
				}
				
			}
		}
		
		return flag;
		
	}
	
	/**
	 * 设置合并后行与列的样式
	 * 
	 * @param startRow
	 *        开始行
	 * @param rowSpan
	 *        结束行
	 * @param columnCount
	 *        行数
	 * @param xssfWorkbook
	 *        工作薄对象
	 * @param xssfSheet
	 *        表对象
	 */
	public static void setMergedRegionCellStyle(int startRow, int rowSpan, int columnCount, XSSFWorkbook xssfWorkbook,
			XSSFSheet xssfSheet)
	{
		for (int i = startRow; i <= rowSpan; i++)
		{
			XSSFRow xssfRow = xssfSheet.getRow(i);
			
			if (xssfRow != null)
			{
				for (int j = 0; j <= columnCount; j++)
				{
					XSSFCell xssfCell = xssfRow.getCell(j);
					
					if (xssfCell == null)
					{
						xssfCell = xssfRow.createCell(j);
					}
					
					if (null != xssfCell)
					{
						xssfCell.setCellStyle(ExcelFileUtils.getXssfCellStyle(xssfWorkbook));
					}
					
				}
			}
		}
	}
	
}
