package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@author yixibo
 *@time Jul 29, 2013 4:27:59 PM
 */
public class StringHelper
{

	public static String lowFirstCase(String pojoName){
		if(pojoName != null){
			String last = "";
			if(pojoName.length() > 1){
		        last = pojoName.substring(1,pojoName.length());
			}   
		    String pf = pojoName.charAt(0)+"";
		    return pf.toLowerCase()+last;
		}
		return "";
	}
	
	public static String bigFirstCase(String str){
		if(str != null){
			String last = "";
			if(str.length() > 1){
		        last = str.substring(1,str.length());
			}   
		    String pf = str.charAt(0)+"";
		    return pf.toUpperCase()+last;
		}
		return "";
	}
	
	public  static Integer backInt(String str){
		if(str == null){
			return 0;
		}
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String[] strarray = str.split("\\.");
			return Integer.valueOf(strarray[0]);
		}
		return 0;
	}
	
	public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
           if (c >= 0 && c <= 255){sb.append(c);}
           else{
               byte[] b;
               try { b = Character.toString(c).getBytes("utf-8");}
               catch (Exception ex) {
                   System.out.println(ex);
                   b = new byte[0];
               }
               for (int j = 0; j < b.length; j++) {
                   int k = b[j];
                   if (k < 0) k += 256;
                   sb.append("%" + Integer.toHexString(k).toUpperCase());
               }
           }
       }
       return sb.toString();
}
	/**
	 * @author yixibo
	 * @time Jul 29, 2013 4:27:59 PM 
	 * @param args
	 */
	public static void main(String[] args )
	{
		// FIXED Auto-generated method stub
		System.out.println(StringHelper.backInt("0.01"));
		System.out.println(StringHelper.backInt("11.01"));
		System.out.println(StringHelper.backInt("12"));
	}
}
