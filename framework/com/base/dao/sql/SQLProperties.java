package com.base.dao.sql;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.jdbc.core.SqlProvider;
import org.springframework.util.ResourceUtils;

public class SQLProperties {

	private static final String RESOURCE = "/sql";
	private static Properties prop = null;
	private static boolean isInit = false;

	private static SQLProperties sqlProperties = null;

	private SQLProperties() {
	}

	public synchronized static SQLProperties getInstance(){
		if (sqlProperties == null) {
			sqlProperties = new SQLProperties();			
			try {
				Properties prop = initProperties("classpath:" + RESOURCE);
				sqlProperties.setProp(prop);
				isInit = true;
				return sqlProperties;
			} catch (Exception e) {			
				try {
					Properties prop = initProperties(SQLProperties.class.getClass().getResource("/").getPath()+RESOURCE);
					sqlProperties.setProp(prop);
					isInit = true;
					return sqlProperties;	
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("can't init sql properties");
				}							
			}
		} else {
			return sqlProperties;
		}
		try {
			throw new Exception("can't init sql properties");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return sqlProperties;
		
	}

	public static Properties initProperties(String classPath) throws Exception {
		Properties prop = new Properties();
		File file = null;
		if (classPath.indexOf("classpath") > -1) {
			file = ResourceUtils.getFile("classpath:" + RESOURCE);
		} else {
			file = new File(classPath);
		}
		File[] files = file.listFiles();
		if (files != null) {
			for (File sqlFile : files) {
				if (sqlFile.isFile()) {
					try {
						InputStream sqlInputStream = new BufferedInputStream(new FileInputStream(sqlFile));
						prop.loadFromXML(sqlInputStream);
						if (sqlInputStream != null)
							sqlInputStream.close();
					} catch (Exception e) {
						System.out.println("error init "+sqlFile.getName());
						e.printStackTrace();
					}
					
				}
			}
		}
		return prop;
	}

	public String getSql(String name)  {
		if (!isInit) {
			try {
				throw new Exception("can't init sqlProperties");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		String result = prop.getProperty(name);
	
		if (result != null && result.length() > 0) {
			return result;
		}
		try {
			throw new Exception("couldn't found sql");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		SQLProperties.prop = prop;
	}

}
