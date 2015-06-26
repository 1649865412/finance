package com.utils;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AjaxHelper {
	public static String toJSON(Object o){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", o);
		return jsonObject.toString();
	}
	
	public static String toJSON(Collection col){
		JSONArray jsonArray = JSONArray.fromObject(col); 
		return jsonArray.toString();
	}
}
