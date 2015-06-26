package com.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static void saveCookie(String key, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60 * 60 * 24 * 365);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	public static void removeCookie(String key, HttpServletResponse response) {
		Cookie delCookie = new Cookie(key, "");
		delCookie.setMaxAge(0);
		delCookie.setPath("/");
		response.addCookie(delCookie);
	}

	public static String readCookie(String key, HttpServletRequest request) {
		String value = null;
		if(key==null || key.equals("")){
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase(key)) {
					value = c.getValue();
					return value;
				}
			}
		}
		return value;
	}

}
