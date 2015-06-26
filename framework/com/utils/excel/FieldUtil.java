package com.utils.excel;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FieldUtil {

   /**
    * 根据字段名和类原型，取出对相应用字段
    * @param clazz
    * @param fieldName
    * @return
    */
	public static  Field getDeclaredField(final Class clazz, final String fieldName) {  
	        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {   
	            try { 
	            	Field[] fields = superClass.getDeclaredFields();
	            	for(Field field : fields){
	            		if(fieldName.equals(field.getName())){
	            			return field;
	            		}
	            	}
	               
	            } catch (Exception  e) {   
	                 throw new RuntimeException(e); 
	            }   
	        }   
	        return null;   
	 }
	public static  Map<String,Field> getFields(final Class clazz) {  
		Map<String,Field> map = new HashMap<String, Field>();
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {   
            try { 
            	Field[] fields = superClass.getDeclaredFields();
            	for(Field field : fields){
            		map.put(field.getName(), field);
            	}
               
            } catch (Exception  e) {   
                 throw new RuntimeException(e); 
            }   
        }   
        return map;   
 }
	//解析字段类型并返回解析的值
	public static Object findFieldValue(Object value,Class fieldType){
		Object  result = null;
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
		if (fieldType == Date.class){
			try {
				result = dateValue(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public static  boolean booleanValue(Object value) {
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

	public static  long longValue(Object value) throws NumberFormatException {
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

	public static  double doubleValue(Object value) throws NumberFormatException {
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

	public static  BigInteger bigIntValue(Object value) throws NumberFormatException {
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

	public static  BigDecimal bigDecValue(Object value) throws NumberFormatException {
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
    
	public static  Date dateValue(Object value) throws ParseException{
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat(
			"yyyy-MM-dd");
		 String date = String.valueOf(value);
		 if (date == null || date.equals("null") || date.trim().equals("")){
				return null;
			}else if(date.length()<=10){
				return DATE_FORMAT_SHORT.parse(date);
			}else{
				return DATE_FORMAT.parse(date);
			} 
	}
	public static  String stringValue(Object value, boolean trim) {
		String result;

		if (value == null) {
			result = "";
		} else {
			result = value.toString();
			//System.out.println("--================="+result);
			if (trim) {
				result = result.trim();
			}
		}
		return result;
	}

	public static  String stringValue(Object value) {
		return stringValue(value, false);
	}
}
