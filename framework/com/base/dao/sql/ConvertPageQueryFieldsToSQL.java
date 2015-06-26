package com.base.dao.sql;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.base.util.persistence.SearchFilter;
import com.base.util.persistence.SearchFilter.Operator;

/**
 * 处理页面根据条件查询时，用于拼接查询条件工具类
 * 
 * <code>SplitPageQueryFields.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class ConvertPageQueryFieldsToSQL
{
	
	private static final String UNDER_LINE = "_";
	public static final String AND = " and ";
	public static final String BLANK = " ";
	public static final String WHERE_1_1 = " where 1=1 ";
	
	public static final String WHERE = "where";
	public static final String IN = "in";
	public static final String LTE = "<=";
	public static final String GTE = ">=";
	public static final String LT = "<";
	public static final String GT = ">";
	public static final String LIKE = "like";
	public static final String EQ = "=";
	
	private ConvertPageQueryFieldsToSQL()
	{
	}
	
	/**
	 * 处理 前端页面传入的字段列表，并转换为最终SQL查询条件并返回
	 * 
	 * @param filterSet
	 *            前端页面请求中获取的字段列表
	 * @param sql
	 *            需要拼接查询条件的SQL
	 * @return 最终拼接完成的SQL
	 */
	public static String getValueSQL(Set<SearchFilter> filterSet, String sql)
	{
		StringBuffer buffer = new StringBuffer(sql);
		if (null != filterSet && filterSet.size() > 0)
		{
			// 标致，判断在什么条件下拼接and
			for (SearchFilter filter : filterSet)
			{
				if (buffer.toString().toLowerCase().indexOf(WHERE) == -1)
				{
					buffer.append(WHERE_1_1).append(BLANK);
				}
				
				String fieldName = filter.getFieldName();
				Operator operator = filter.getOperator();
				Object value = filter.getValue();
				
				if (null != value)
				{
					buffer.append(AND);
					buffer.append(BLANK).append(fieldNameToColumName(fieldName)).append(BLANK);
					buffer.append(converCharacter(operator)).append(BLANK);
					buffer.append(converValue(operator, value)).append(BLANK);
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * 查询字符转换
	 * 
	 * @param operator
	 *            查询条件对应 的枚举类
	 * @return 返回最终数据库能识别的<= 、>、=、like等关键字
	 */
	private static String converCharacter(Operator operator)
	{
		
		switch (operator)
		{
			case EQ:
				return EQ;
			case LIKE:
				return LIKE;
			case GT:
				return GT;
			case LT:
				return LT;
			case GTE:
				return GTE;
			case LTE:
				return LTE;
			case IN:
				return IN;
			default:
				return EQ;
		}
		
	}
	
	/**
	 * 把根据"驼峰"规则作为pojo的变量，变量转为带 首字大写前面加一个"_"，转换为数据库对应字段
	 * 
	 * @param fieldName
	 *            字段名
	 * @return 返回数据库识别的列名
	 */
	public static String fieldNameToColumName(String fieldName)
	{
		if (StringUtils.isEmpty(fieldName))
		{
			return null;
		}
		char[] tmpChar = fieldName.toCharArray();
		StringBuffer buffer = new StringBuffer();
		if (ArrayUtils.isNotEmpty(tmpChar))
		{
			for (int i = 0; i < tmpChar.length; i++)
			{
				// 判断字母是否大写
				if (Character.isUpperCase(tmpChar[i]))
				{
					buffer.append(UNDER_LINE);
				}
				
				buffer.append(Character.toLowerCase(tmpChar[i]));
				
			}
		}
		
		return buffer.toString();
	}
	/****
	 * 将abc_bbc 转成 abcBbc;
	 * @param columnName
	 * @return
	 */
	public static String columnNameToFieldName(String str){
		String sb = columnNameToClassName(str); 
		if(sb.length() > 1){
			return sb.substring(0, 1).toLowerCase()+sb.substring(1);
		} 
		return sb.toString();
	}
	
	/****
	 * 将abc_bbc 转成AbcBbc;
	 * @param columnName
	 * @return
	 */
	public static String columnNameToClassName(String str){
		if (str == null || "".equals(str))
			return null;
		String[] arrs = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (String s : arrs) {
			sb.append(Character.toUpperCase(s.charAt(0))).append(
					s.substring(1).toLowerCase());
		} 
		return sb.toString();
	}
	
	private static Object converValue(Operator operator, Object obj)
	{
		switch (operator)
		{
			case LIKE:
				return "'%" + obj + "%'";
			case EQ:
			case GT:
			case LT:
			case GTE:
			case LTE:
				return converValue(obj);
			case IN:
				return "(" + obj + ")";
			default:
				return converValue(obj);
		}
		
	}
	
	private static Object converValue(Object obj)
	{
		
		Object tmpValue = "";
		if (null == obj)
		{
			return tmpValue;
		}
		
		if (obj instanceof String)
		{
			tmpValue = " '" + obj + "' ";
		}
		else
		{
			tmpValue = obj;
		}
		
		return tmpValue;
	}
	
}
