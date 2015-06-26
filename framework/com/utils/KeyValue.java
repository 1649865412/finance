package com.utils;
/****
 * 键值对对象
 * 用于保存和转换对象
 * @author vigor
 *
 */
public class KeyValue {
	public String key;
	public String value;
	public KeyValue(String key, String value) { 
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
