package com.base.dao.sql;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer; 

public class ResultToBean implements ResultTransformer {

	private Class entityClaz;

	public List transformList(List list) {
		return list;
	}
    
	private boolean checkGeneraleType(Object obj){
		if(obj == null){
			return false;
		}else if(obj instanceof String || obj instanceof Integer){
			return true;
		}else{
			return false;
		}
		
	}
	
	public Object transformTuple(Object[] values, String[] columns) {
		try {
			Object result = entityClaz.newInstance();
			boolean isGeneraleType = checkGeneraleType(result);
			for (int i = 0; i < columns.length; i++) {
				try {
					if(isGeneraleType && i == 0){
						return values[i];
					}
					String objectName = coloumnToObject(columns[i]);
					Object value = values[i];
					Field field = entityClaz.getDeclaredField(objectName);
					field.setAccessible(true);
					try {
						SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						field.set(result, sp.parse(value.toString()));
					} catch (Exception e) {
						field.set(result, ReflectionUtils.convertValue(value, field.getType()));
					}
				} catch (Exception e) {
					// fix
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String coloumnToObject(String column) {
		String[] columnWords = column.split("_");
		String result = "";
		for (int i = 0; i < columnWords.length; i++) {
			if (i == 0) {
				result += StringUtils.lowerCase(columnWords[i]);
			} else {
				result += StringUtils.capitalize(StringUtils.lowerCase(columnWords[i]));
			}
		}
		return result;
	}

	public static String toDBName(String objectName) {
		try {
			StringBuffer result = new StringBuffer();
			result.append(objectName.charAt(0));
			for (int i = 1; i < objectName.length(); i++) {
				char c = objectName.charAt(i);
				if (Character.isUpperCase(c)) {
					result.append("_");
				}
				result.append(objectName.charAt(i));
			}
			return result.toString().toUpperCase();
		} catch (Exception e) {
			return objectName;
		}
	}

	public ResultToBean as(Class claz) {
		entityClaz = claz;
		return this;
	}

}
