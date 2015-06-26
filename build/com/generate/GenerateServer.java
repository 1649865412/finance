package com.generate;

import java.io.IOException;
import java.util.regex.Matcher;

import com.generate.util.FileUtils;
import com.generate.util.FreeMarkers;

import freemarker.template.Template;


/** 
 * @author 	Vigor
 * @since   2013年10月24日 上午9:55:58 
 */
public class GenerateServer extends AbstractGenerate {
	public GenerateServer() {
		super();
	}
	
	public void generate() throws IOException {
		String packageName = (String)model.get("packageName");
		packageName = packageName + ".test";
		model.put("packageName", packageName);
		
		Template template = cfg.getTemplate("server" + separator + "QuickStartServer.java.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		
		String packagePath = packageName.replaceAll("\\.", Matcher.quoteReplacement(separator));
		
		String filePath = testJavaPath + packagePath + separator + "QuickStartServer.java";
		FileUtils.writeFile(content, filePath);
		
		logger.info("QuickStartServer.java: {}", filePath);
	}
}
