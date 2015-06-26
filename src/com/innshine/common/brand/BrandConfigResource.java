package com.innshine.common.brand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.utils.Exceptions;
import com.utils.excel.FieldUtils;

public class BrandConfigResource
{
	private final static Logger LOGGER = LoggerFactory.getLogger(BrandConfigResource.class);
	/**
	 * 获取所有brand节点元素
	 */
	private static final String BRAND = "//brand[@*]";
	
	private static final String CLASSPATH = "classpath:";
	
	/**
	 * 品牌配置文件名称
	 */
	private static final String BRAND_RESOURCE = "brand-filter.xml";
	
	/**
	 * 配置 参数集合
	 */
	private static List<BrandConfigEntity> attrEntities = null;
	
	/**
	 * 内部实例
	 */
	private static BrandConfigResource brandConfigResource;
	
	/**
	 * 是否初始化标识
	 */
	private static boolean isInit = false;
	
	private BrandConfigResource()
	{
		
	}
	
	public synchronized static BrandConfigResource getInstance()
	{
		if (null == brandConfigResource)
		{
			brandConfigResource = new BrandConfigResource();
			
			try
			{
				attrEntities = initBrandFilterConfigFile();
				
				isInit = true;
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return brandConfigResource;
	}
	
	private static List<BrandConfigEntity> initBrandFilterConfigFile() throws ParserConfigurationException,
			FileNotFoundException, SAXException, IOException, XPathExpressionException, IllegalArgumentException,
			DOMException, IllegalAccessException
	{
		
		List<BrandConfigEntity> attrEntities = new ArrayList<BrandConfigEntity>();
		Document doc = getDocument();
		// 创建Xpath解析工厂
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xPath = xFactory.newXPath();
		
		// 获取所有brand元素
		NodeList nodes = (NodeList) xPath.evaluate(BRAND, doc, XPathConstants.NODESET);
		
		if (null != nodes)
		{
			for (int i = 0; i < nodes.getLength(); i++)
			{
				BrandConfigEntity attrEntity = new BrandConfigEntity();
				Field[] fields = attrEntity.getClass().getDeclaredFields();
				
				if (null != fields)
				{
					for (int j = 0; j < fields.length; j++)
					{
						Field tmpField = fields[j];
						if (null != tmpField)
						{
							Node tmpNod = nodes.item(i).getAttributes().getNamedItem(tmpField.getName());
							
							if (null != tmpNod)
							{
								tmpField.setAccessible(true);
								
								tmpField.set(attrEntity,
										FieldUtils.findFieldValue(tmpNod.getNodeValue(), fields[j].getType()));
								
							}
						}
					}
					
					attrEntities.add(attrEntity);
				}
			}
		}
		
		return attrEntities;
	}
	
	private static Document getDocument() throws ParserConfigurationException, SAXException, IOException,
			FileNotFoundException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		Document doc = documentBuilder.parse(new FileInputStream(ResourceUtils.getFile(CLASSPATH + BRAND_RESOURCE)));
		return doc;
	}
	
	public List<BrandConfigEntity> getConfig()
	{
		if (!isInit)
		{
			try
			{
				throw new Exception("can't init Brand-filter.xml ");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return attrEntities;
	}
	
	public static void main(String[] args) throws Exception
	{
		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setValidating(false);
		// DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		// Document doc = documentBuilder.parse(new
		// FileInputStream(ResourceUtils.getFile(CLASSPATH + BRAND_RESOURCE)));
		// // 创建Xpath解析工厂
		// XPathFactory xFactory = XPathFactory.newInstance();
		// XPath xPath = xFactory.newXPath();
		//
		// NodeList nodes = (NodeList) xPath.evaluate(BRAND, doc,
		// XPathConstants.NODESET);
		//
		// System.out.println(nodes.getLength());
		// for (int i = 0; i < nodes.getLength(); i++)
		// {
		// System.out.println(nodes.item(i).getAttributes().getNamedItem("brandName1").getNodeValue());
		// }
		
		List<BrandConfigEntity> attrEntities = BrandConfigResource.getInstance().getConfig();
		
		System.out.println(attrEntities);
	}
}
