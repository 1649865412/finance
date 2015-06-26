/*package com.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;





public class HttpClientUtils {

	public static String get(String url) {
		return send("get", url);
	}

	public static String post(String url) {
		return send("post", url);
	}
	
	public static String send(String method, String url) {
		String body = "error";
		HttpMethod httpMethod = null;
		if (method.equalsIgnoreCase("post")) {
			httpMethod = new PostMethod(url); // 输入网址
		} else {
			httpMethod = new GetMethod(url); // 输入网址
		}
		HttpClient client = new HttpClient();
		try {
			client.getParams().setContentCharset("UTF-8"); // 处理中午字符串
			client.executeMethod(httpMethod);
			body = httpMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	public static void main(String[] args) {
		 
	}
}
*/