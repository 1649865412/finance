package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	
	public static Properties load(String path) throws IOException{
		Properties properties=new Properties();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(ResourceUtils.getClassPath()+"/"+path);
			properties.load(fis);
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

}
