package com.utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtils {

	public static Configuration getConfiguration(String templateDir)
			throws IOException {
		Configuration cfg = new Configuration();
		File templateDirFile = new File(templateDir);
		cfg.setDirectoryForTemplateLoading(templateDirFile);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}
	
	public static String compile(String tplConfigPath,String tplName,Map data)throws Exception{
		Configuration configuration=getConfiguration(tplConfigPath);
		Template t = configuration.getTemplate(tplName);
		String content=FreeMarkerTemplateUtils.processTemplateIntoString(t, data);
		return content;
	}
	
}
