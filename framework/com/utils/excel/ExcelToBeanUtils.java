package com.utils.excel;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.Exceptions;

/**
 * 处理Excel解析，解析完成后对解析结果通过反射绑定至实体bean中
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ExcelToBeanUtils
{
	/**
	 * 默认调用方法前辍
	 */
	private static final String GET = "get";
	/**
	 * 日志对象
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(ExcelToBeanUtils.class);
	
	/**
	 * 解析excel文件 解析完成后，根据参数isReturnModelList，返回对应解析结果列表。 *
	 * <p>
	 * 
	 * @param file
	 *            excel文件
	 * @param objModel
	 *            解析Excel、Txt 、CVS对应的模版
	 * @param classBean
	 *            数据入库对应的实体bean完整类路径
	 * @param type
	 *            文件格式，暂仅支持txt 和excel 文件.
	 *            与FileExcelUtils.TEXT_TYPE/EXCEL_TYPE属性对应
	 * @param isReturnModelList
	 *            true 解析当前Excel并返回解析后的模型数据列表， false ：解析并返回最终实体bean对象列表
	 * @return List集合列表，存放实体bean
	 * @throws Exception
	 *             返回错误
	 */
	@SuppressWarnings("unchecked")
	public static List<? extends Object> readFilelModel(File file, Object objModel, Class<?> classBean, String type,
			boolean isReturnModelList) throws Exception
	{
		List<Object> bList = new ArrayList<Object>();
		
		if (null == file || null == objModel)
		{
			return bList;
		}
		
		try
		{
			
			// 如果需要设置实体bean对象，则检测实体bean对象class是否为空
			if (!isReturnModelList)
			{
				if (null == classBean)
				{
					return bList;
				}
				
			}
			
			Vector<String> salesVector = new Vector<String>();
			
			Class<?> objClass = objModel.getClass();
			
			// 获取excel模型中与解析模型对应的属性名称
			refSupperFieldsName(salesVector, objClass);
			
			// 构造解析参数
			FileExcelUtils<? extends Object> fileBean = FileExcelUtils.parseData(salesVector, type,
					objModel.getClass(), file);
			
			// 开始解析并返回解析结果
			List<? extends Object> salesList = fileBean.fileDataList();
			
			// 如果只需要返回模型列表，则该参数为true
			
			if (null != salesList && salesList.size() > 0)
			{
				if (isReturnModelList)
				{
					return salesList;
				}
				
				for (Object sales : salesList)
				{
					Object bean = null;
					try
					{
						bean = classBean.newInstance();
					}
					catch (InstantiationException e)
					{
						LOGGER.error(Exceptions.getStackTraceAsString(e));
					}
					catch (IllegalAccessException e)
					{
						LOGGER.error(Exceptions.getStackTraceAsString(e));
					}
					
					if (null != bean)
					{
						reflectSetValue(salesVector, sales, bean);
						bList.add(bean);
					}
					
				}
			}
			else
			{
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			throw e;
		}
		
		return bList;
	}
	
	/**
	 * 解析excel文件 ，解析完成后保存为对应实体bean集合
	 * <p>
	 * 
	 * @param file
	 *            excel文件
	 * @param objModel
	 *            解析Excel、Txt 、CVS对应的模版
	 * @param classBean
	 *            数据入库对应的实体bean完整类路径
	 * @param type
	 *            文件格式，暂仅支持txt 和excel 文件.
	 *            与FileExcelUtils.TEXT_TYPE/EXCEL_TYPE属性对应
	 * @throws Exception
	 *             返回错误
	 * */
	public static List<? extends Object> readFilelModel(File file, Object objModel, Class<?> classBean, String type)
			throws Exception
	{
		return readFilelModel(file, objModel, classBean, type, false);
	}
	
	/**
	 * 解析excel文件 解析完成后,返回对应模型列表
	 * <p>
	 * 
	 * @param file
	 *            excel文件
	 * @param objModel
	 *            解析Excel、Txt 、CVS对应的模版
	 * @param type
	 *            文件格式，暂仅支持txt 和excel 文件.
	 *            与FileExcelUtils.TEXT_TYPE/EXCEL_TYPE属性对应
	 * @throws Exception
	 *             返回未知错误
	 * */
	public static List<? extends Object> readFilelModel(File file, Object objModel) throws Exception
	{
		return readFilelModel(file, objModel, null, FileExcelUtils.EXCEL_TYPE, true);
	}
	
	/**
	 * 解析excel文件 解析完成后,返回对应模型列表
	 * <p>
	 * 
	 * @param file
	 *            excel文件
	 * @param objModel
	 *            解析Excel、Txt 、CVS对应的模版
	 * @param type
	 *            文件格式，暂仅支持txt 和excel 文件.
	 *            与FileExcelUtils.TEXT_TYPE/EXCEL_TYPE属性对应
	 * @return List集合列表，存放实体bean
	 * @throws Exception
	 *             返回未知错误
	 */
	public static List<? extends Object> readFilelModel(File file, Object objModel, String type) throws Exception
	{
		return readFilelModel(file, objModel, null, type);
	}
	
	/**
	 * 解析excel文件 ，解析完成后保存为对应实体bean集合 默认解析类型为Ｅxcel
	 * <p>
	 * 
	 * @param file
	 *            excel文件
	 * @param objModel
	 *            解析Excel、Txt 、CVS对应的模版
	 * @param bean
	 *            数据入库对应的实体bean 对应
	 * @return List集合列表，存放实体bean
	 * @throws Exception
	 *             返回未知错误
	 */
	public static List<? extends Object> readFilelModel(File file, Object objModel, Class<?> bean) throws Exception
	{
		return readFilelModel(file, objModel, bean, FileExcelUtils.EXCEL_TYPE);
	}
	
	/**
	 * 获取解析模型所有属性字段，从顶层父类开始解析，依次住下获取
	 * <p>
	 * 
	 * @param fieldNames
	 *            记录所有模型实体bean对应属性名
	 * @param classObj
	 *            需要反射的模型bean对象Class
	 */
	public static void refSupperFieldsName(Vector<String> fieldNames, Class<? extends Object> classObj)
	{
		List<Class<? extends Object>> classList = new ArrayList<Class<? extends Object>>();
		
		// 递归获取子类所有父类Class 包含子类本身
		for (Class<? extends Object> superClass = classObj; superClass != Object.class; superClass = superClass
				.getSuperclass())
		{
			classList.add(superClass);
		}
		
		if (null != classList && classList.size() > 0)
		{
			for (int i = classList.size() - 1; i >= 0; i--)
			{
				Class<? extends Object> objectClass = classList.get(i);
				if (null != objectClass)
				{
					Field[] fields = objectClass.getDeclaredFields();
					
					if (null != fields && fields.length > 0)
					{
						for (Field field : fields)
						{
							fieldNames.add(field.getName());
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 获取解析模型所有属性字段，从顶层父类开始解析，依次住下获取
	 * <p>
	 * 
	 * @param fieldNames
	 *            记录所有模型实体bean对应属性名
	 * @param classObj
	 *            需要反射的模型bean对象Class
	 */
	public static Vector<String> refSupperFieldsName(Class<? extends Object> classObj)
	{
		Vector<String> fieldNames = new Vector<String>();
		refSupperFieldsName(fieldNames, classObj);
		
		return fieldNames;
		
	}
	
	/**
	 * 通过反射，获取两个对象包含的所有属性，如果属性名完全一样，则进行反射赋值
	 * <p>
	 * 
	 * @param fieldNameList
	 *            反射字段模型列表
	 * @param sourceObj
	 *            原始模型对应pojo
	 * @param destObj
	 *            目标数据库对应pojo
	 */
	public static void reflectSetValue(Vector<String> fieldNameList, Object sourceObj, Object destObj)
	{
		if (null != sourceObj && destObj != null)
		{
			Class<? extends Object> destClass = destObj.getClass();
			Field[] fields = destClass.getDeclaredFields();
			Class<? extends Object> sourceClass = sourceObj.getClass();
			
			if (null != fields && fields.length > 0)
			{
				for (Field field : fields)
				{
					if (null != fieldNameList && fieldNameList.contains(field.getName()))
					{
						
						// 获取对应字段
						Field sourceField = FieldUtils.getDeclaredField(sourceClass, field.getName());
						
						if (null == sourceField)
						{
							continue;
						}
						
						// 设置private 属性可以访问
						sourceField.setAccessible(true);
						field.setAccessible(true);
						
						try
						{
							if (field.getType() == List.class)
							{
								try
								{
									field.set(destObj, FieldUtils.getDeclaredMethod(sourceClass, GET + field.getName())
											.invoke(sourceObj));
								}
								catch (SecurityException e)
								{
									LOGGER.error(Exceptions.getStackTraceAsString(e));
								}
								catch (InvocationTargetException e)
								{
									
									e.printStackTrace();
									LOGGER.error(Exceptions.getStackTraceAsString(e));
								}
								
							}
							else
							{
								// 赋值
								field.set(destObj,
										FieldUtils.findFieldValue(sourceField.get(sourceObj), field.getType()));
								
							}
						}
						catch (IllegalArgumentException e)
						{
							LOGGER.error(e.getMessage(), e);
							e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							LOGGER.error(e.getMessage(), e);
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
}
