/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ReflectionUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.base.dao.sql;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.utils.DateUtils;

/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * 
 * @author calvin
 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	private static Map<Class, Object> primitiveDefaults;

	static {
		Map<Class, Object> map = new HashMap<Class, Object>();
		map.put(Boolean.TYPE, Boolean.FALSE);
		map.put(Byte.TYPE, Byte.valueOf((byte) 0));
		map.put(Short.TYPE, Short.valueOf((short) 0));
		map.put(Character.TYPE, new Character((char) 0));
		map.put(Integer.TYPE, Integer.valueOf(0));
		map.put(Long.TYPE, Long.valueOf(0L));
		map.put(Float.TYPE, new Float(0.0f));
		map.put(Double.TYPE, new Double(0.0));
		map.put(BigInteger.class, new BigInteger("0"));
		map.put(BigDecimal.class, new BigDecimal(0.0));
		primitiveDefaults = Collections.unmodifiableMap(map);
	}
	
	
	public static Object convertValue(Object value, Class toType) {
		Object result = null;
		if (value != null) {
			if (value.getClass().isArray() && toType.isArray()) {
				Class componentType = toType.getComponentType();
				result = Array.newInstance(componentType, Array.getLength(value));
				for (int i = 0, icount = Array.getLength(value); i < icount; i++) {
					Array.set(result, i, convertValue(Array.get(value, i), componentType));
				}
			} else {
				if ((toType == Integer.class) || (toType == Integer.TYPE))
					result = Integer.valueOf((int) longValue(value));
				if ((toType == Double.class) || (toType == Double.TYPE))
					result = new Double(doubleValue(value));
				if ((toType == Boolean.class) || (toType == Boolean.TYPE))
					result = booleanValue(value) ? Boolean.TRUE : Boolean.FALSE;
				if ((toType == Byte.class) || (toType == Byte.TYPE))
					result = Byte.valueOf((byte) longValue(value));
				if ((toType == Character.class) || (toType == Character.TYPE))
					result = new Character((char) longValue(value));
				if ((toType == Short.class) || (toType == Short.TYPE))
					result = Short.valueOf((short) longValue(value));
				if ((toType == Long.class) || (toType == Long.TYPE))
					result = Long.valueOf(longValue(value));
				if ((toType == Float.class) || (toType == Float.TYPE))
					result = new Float(doubleValue(value));
				if (toType == BigInteger.class)
					result = bigIntValue(value);
				if (toType == BigDecimal.class)
					result = bigDecValue(value);
				if (toType == String.class)
					result = stringValue(value);
				if (toType == Date.class){
					result = DateUtils.toDate(stringValue(value));
				}
				if (Enum.class.isAssignableFrom(toType))
					result = enumValue((Class<Enum>) toType, value);
			}
		} else {
			if (toType.isPrimitive()) {
				result = primitiveDefaults.get(toType);
			}
		}
		return result;
	}

	public static boolean booleanValue(Object value) {
		if (value == null)
			return false;
		Class c = value.getClass();
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue();
		if (c == Character.class)
			return ((Character) value).charValue() != 0;
		if (value instanceof Number)
			return ((Number) value).doubleValue() != 0;
		return true; // non-null
	}

	public static Enum<?> enumValue(Class toClass, Object o) {
		Enum<?> result = null;
		if (o == null) {
			result = null;
		} else if (o instanceof String[]) {
			result = Enum.valueOf(toClass, ((String[]) o)[0]);
		} else if (o instanceof String) {
			result = Enum.valueOf(toClass, (String) o);
		}
		return result;
	}

	public static long longValue(Object value) throws NumberFormatException {
		if (value == null)
			return 0L;
		Class c = value.getClass();
		if (c.getSuperclass() == Number.class)
			return ((Number) value).longValue();
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue() ? 1 : 0;
		if (c == Character.class)
			return ((Character) value).charValue();
		return Long.parseLong(stringValue(value, true));
	}
	
	private static double processDecimal(Object value){
		String objs = String.valueOf(value);
		double dbvalue= Double.valueOf(objs);
		BigDecimal bg = new BigDecimal(dbvalue);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	public static double doubleValue(Object value) throws NumberFormatException {
		if (value == null)
			return 0.0;
		Class c = value.getClass();
		if (c.getSuperclass() == Number.class){
	        double f1 = processDecimal(value);
			return f1;
		}
		if (c == Boolean.class)
			return ((Boolean) value).booleanValue() ? 1 : 0;
		if (c == Character.class)
			return ((Character) value).charValue();
		String s = stringValue(value, true);

		return (s.length() == 0) ? 0.0 : Double.parseDouble(s);
	}

	public static BigInteger bigIntValue(Object value) throws NumberFormatException {
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

	public static BigDecimal bigDecValue(Object value) throws NumberFormatException {
		if (value == null)
			return BigDecimal.valueOf(0L);
		Class c = value.getClass();
		if (c == BigDecimal.class)
			return (BigDecimal) value;
		if (c == BigInteger.class)
			return new BigDecimal((BigInteger) value);
		if (c.getSuperclass() == Number.class) 
		    return BigDecimal.valueOf(processDecimal(value));
			 
		if (c == Boolean.class)
			return BigDecimal.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
		if (c == Character.class)
			return BigDecimal.valueOf(((Character) value).charValue());
		return new BigDecimal(stringValue(value, true));
	}

	public static String stringValue(Object value, boolean trim) {
		String result;

		if (value == null) {
			result = null;
		} else {
			result = value.toString();
			if (trim) {
				result = result.trim();
			}
		}
		return result;
	}

	public static String stringValue(Object value) {
		return stringValue(value, false);
	}
	
	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField,	 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Assert.notNull(obj, "object不能为空");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
