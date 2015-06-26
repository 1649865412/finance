package com.utils.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.utils.excel.FieldUtils;

/**
 * 数据效验类,提供一些基本数据的效验
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ValidatorsDataUtils extends ValidatorsData
{
	
	// private static final Logger LOG =
	// LogFactory.getLog(ValidatorDataUtils.class);
	
	/**
	 * 验证唯一数据时 ，使用的方法名
	 */
	private static final String UNIQUE_METHOD = "findByUnique";
	
	private ValidatorsDataUtils()
	{
		
	}
	
	/**
	 * 判断数据是否唯一
	 * ，传入的服务类中必需有findByUnique方法,数据对象dataObj作为传入调用方法处理，没有则调用失败，无法查询数据，返回默认值
	 * false
	 * 
	 * @param dataObj
	 *            需要查询的数据对象
	 * @param servcieObj
	 *            服务层对象
	 * @return true 已存在 false 唯一
	 */
	public static boolean isUnique(Object dataObj, Object servcieObj)
	{
		return isUnique(dataObj, servcieObj, UNIQUE_METHOD);
	}
	
	/**
	 * 判断数据是否唯一
	 * ，传入的服务类中默认findByUnique方法,数据对象dataObj作为传入调用方法处理，没有则调用失败，无法查询数据，返回默认值 false
	 * 
	 * @param dataObj
	 *            需要查询的数据对象
	 * @param servcieObj
	 *            服务层对象
	 * @param methodName
	 *            调用的方法名
	 * @return true 唯一 false 已存在
	 */
	public static boolean isUnique(Object dataObj, Object servcieObj, String methodName)
	{
		boolean flag = true;
		if (null == dataObj || null == servcieObj)
		{
			return flag;
		}
		
		Class<?> classSerClass = servcieObj.getClass();
		Method[] methods = classSerClass.getDeclaredMethods();
		
		String tmpMethodName = StringUtils.isNotBlank(methodName) ? methodName : UNIQUE_METHOD;
		
		Method method1 = null;
		
		if (null != methods && methods.length > 0)
		{
			for (Method method : methods)
			{
				if (tmpMethodName.equalsIgnoreCase(method.getName()))
				{
					method1 = method;
					break;
				}
			}
		}
		
		if (null == method1)
		{
			return flag;
		}
		
		try
		{
			// 调用findByUnique方法
			Object obj = method1.invoke(servcieObj, dataObj);
			
			if (null != obj)
			{
				flag = false;
			}
			
			obj = null;
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 判断对象为空 或对象不为空，但内容为空的情况
	 * 
	 * @param dataObj
	 *            需要判断对象
	 * @return true 非空 false 空
	 */
	public static boolean isObjectNull(Object dataObj)
	{
		boolean flag = true;
		if (null == dataObj)
		{
			flag = false;
			return flag;
		}
		
		Map<String, Field> fieldMap = FieldUtils.getClazzFields(dataObj.getClass());
		
		if (null != fieldMap)
		{
			Iterator<Entry<String, Field>> it = fieldMap.entrySet().iterator();
			
			// 计数器，判断有多少不符合数据规范
			int count = 0;
			while (it.hasNext())
			{
				Entry<String, Field> entry = it.next();
				String fieldName = entry.getKey();
				Field field = entry.getValue();
				if (null != field)
				{
					try
					{
						Object obj = FieldUtils.findFieldValue(field.get(dataObj), field.getType());
						if (null == obj || StringUtils.isBlank(obj.toString()) || "0".equalsIgnoreCase(obj.toString())
								|| "0.0".equalsIgnoreCase(obj.toString()))
						{
							count++;
						}
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
				
				if (count >= fieldMap.size())
				{
					flag = false;
				}
			}
			
		}
		
		return flag;
	}
	
	/**
	 * 判断对象中一些字段是否满足条件，如非空、整型等于0等，会根据字段列表与对象属性对应。只要其中一个不满足，则认为整个数据条件不满足。 <br>
	 * 如果其字段无法从该对象属性中找到，则默认认为满足
	 * <p>
	 * 
	 * @param fields
	 *            字段列表
	 * @param dataObj
	 *            需要判断的对象
	 * @return true 满足 false 不满足
	 */
	public static boolean isFieldsRule(String[] fields, Object dataObj)
	{
		boolean flag = true;
		
		if (null == fields || fields.length <= 0)
		{
			return flag;
		}
		
		if (null == dataObj)
		{
			return flag;
		}
		
		Class<?> classObj = dataObj.getClass();
		
		for (String fieldName : fields)
		{
			
			try
			{
				Field field = FieldUtils.getDeclaredField(classObj, fieldName);
				if (null != field)
				{
					field.setAccessible(true);
					Object obj = FieldUtils.findFieldValue(field.get(dataObj), field.getType());
					
					if (null == obj || StringUtils.isBlank(obj.toString()))
					{
						flag = false;
						break;
					}
				}
			}
			catch (RuntimeException e)
			{
				e.printStackTrace();
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
			
		}
		
		return flag;
	}
	
	// public static void main(String[] args)
	// {
	// ProductInfo info = new ProductInfo();
	// info.setSku("ABCD");
	// info.setMarketPrice(1.1);
	//
	// System.out.println(isFieldsRule(new String[] { "sku11", "marketPrice" },
	// info));
	//
	// System.out.println(isObjectNull(info));
	//
	// }
}
