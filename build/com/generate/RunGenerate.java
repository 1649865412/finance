package com.generate;

public class RunGenerate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/****
		 * 在生成代码前，请先确定 resources/template_settings.properties 的正确性
		 * 
		 */
		GenerateFactory factory = new GenerateFactory();  
		factory.generate();
	}

}
