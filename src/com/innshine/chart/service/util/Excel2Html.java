package com.innshine.chart.service.util;

/**
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author: LOEI
 * @since: May 3, 2013
 * @modified: May 3, 2013
 * @version:
 */
public class Excel2Html
{

	public static void main(String argv[])
	{
		try
		{
			convert2Html("e://a.xls");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String ExcelToHtml(String path) throws TransformerException,
			IOException, ParserConfigurationException, SAXException
	{
		return convert2Html(path);
	}

	public static String convert2Html(String path) throws TransformerException,
			IOException, ParserConfigurationException, SAXException
	{
		HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(new File(path));
		ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument());
		excelToHtmlConverter.processWorkbook(workbook);
		Document htmlDocument = excelToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();

		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os.startsWith("Win"))
		{
			serializer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
		} else
		{
			serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		}
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();

		byte[] lens = out.toByteArray();
		String content = new String(lens);

		return ReplaceContent(content);
	}

	public static String ReplaceContent(String content)
	{

		content = content.replaceAll(".b1\\{[\\w*-:]*;}",

		".b1{text-align:center;white-space-collapsing:preserve;}");

		content = content.replaceAll("<th>[A-Z]</th>", "<th></th>");

		content = content.replaceAll("<th class=\"rownumber\">\\d*</th>",
				"<th class=\"rownumber\"></th>");

		// 去掉标题
		String strA = content.substring(0, content.indexOf("<h2>"));
		// System.out.println(strA);
		String strB = content.substring(content.indexOf("</h2>") + 5);
		// System.out.println(strB);
		content = strA + strB;
		content = content.replaceAll("GB2312", "UTF-8");
		return content;
	}

}