package com.utils;

import java.io.File;

public class ResourceUtils {
	
	public static String getClassPath(){
		return ResourceUtils.class.getResource("/").getPath();
	}
	
	public static String getWebappPath(){
		String clsPath = getClassPath();
		File file = new File(clsPath);
		if(file.exists()){
			return file.getParentFile().getParentFile().getPath();
		}
		return ""; 
	}
		
	public static String packageToPath(String packageStr){
		return packageStr.replaceAll("\\.","/");
	}
	public static String pathToPackage(String pathStr){
		return pathStr.replaceAll("\\/",".");
	}
	
	public static void main(String[] args) {
		System.out.println(ResourceUtils.getClassPath());
		System.out.println(ResourceUtils.packageToPath("cn.test.module"));
		System.out.println(ResourceUtils.pathToPackage("cn/test/module"));
	}

}
