package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExportHelper {
	public static final String APPLICATION_X_MSDOWNLOAD = "application/x-msdownload;";
	/**
	 * User-Agent
	 */
	public static final String USER_AGENT = "User-Agent";
	/**
	 * 火狐浏览器标识
	 */
	public static final String FIREFOX = "firefox";

	/**
	 * 默认编码
	 */
	public static final String DEAULT_ENCODE_UTF_8 = "UTF-8";

	/**
	 * 编码
	 */
	public static final String ENCODE_ISO8859_1 = "ISO8859-1";

	/**
	 * 空格
	 */
	public static final String BLANK_SPACE_20 = "%20";

	/**
	 * 空格符
	 */
	public static final String BLANK_CHARACTER = " ";

	public static void excelExport(HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName)
			throws FileNotFoundException {

		File file = new File(filePath);
		if (file.exists()) {

			// 读到流中
			InputStream inStream = null;

			OutputStream outputStream = null;

			try {

				inStream = new FileInputStream(file);

				outputStream = response.getOutputStream();

				response.reset();
				response.setContentType(APPLICATION_X_MSDOWNLOAD);
				response.setCharacterEncoding(DEAULT_ENCODE_UTF_8);

				if (request.getHeader(USER_AGENT).toLowerCase()
						.indexOf(FIREFOX) > 0) {
					fileName = new String(fileName
							.getBytes(DEAULT_ENCODE_UTF_8), ENCODE_ISO8859_1);// firefox浏览器

				} else {
					// 处理文件名空格%20
					fileName = URLEncoder.encode(fileName, DEAULT_ENCODE_UTF_8)
							.replace(BLANK_SPACE_20, BLANK_CHARACTER);
				}

				response.addHeader("Content-Disposition",
						"attachment; filename=\"" + fileName + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len;

				while ((len = inStream.read(b)) > 0) {
					outputStream.write(b, 0, len);
				}

				outputStream.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != inStream) {
					try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (null != outputStream) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			throw new FileNotFoundException("对不起，导出文件失败，文件不存在！");

		}

	}
	
	public static void excelExport(HttpServletRequest request,
			HttpServletResponse response, String filePath)
			throws FileNotFoundException {
          String fileName = FileUtils.getFileName(filePath);
          ExportHelper.excelExport(request,response,filePath,fileName);
	}
}


