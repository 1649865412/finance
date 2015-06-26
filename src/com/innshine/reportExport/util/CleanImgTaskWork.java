package com.innshine.reportExport.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.DateUtils;

/**
 *  <code>CleanImgTaskWork.java</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-21 上午09:50:19	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class CleanImgTaskWork
{
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CleanImgTaskWork.class);

	public void doTask()
	{
		LOG.info(" //============================开始定时定期清理分析生成的图片与PPT工作=================================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		try
		  {  
			
			String url = this.getClass().getResource("").getPath().replaceAll(
					"%20", " ");
			String tempPath = url.substring(0, url.indexOf("WEB-INF")) + "excelImg";
			
			new FileOperate().del(tempPath);
			
			
		}
		
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info(" //=============================定时定期清理分析生成的图片与PPT工作 End=================================//");
		
	}
}
