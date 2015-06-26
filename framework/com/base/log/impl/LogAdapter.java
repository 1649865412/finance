/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.log.LogAdapter.java
 * Class:			LogTemplateAdapter
 * Date:			2013-5-3
 * Author:			Vigor
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.log.impl;

import java.util.HashMap;
import java.util.Map;

import com.base.log.LogAPI;
import com.base.log.LogLevel;

/** 
 * 	
 * @author 	Vigor
 * Version  2.1.0
 * @since   2013-5-3 下午5:21:07 
 */

public class LogAdapter implements LogAPI {

	/**   
	 * @param message
	 * @param logLevel  
	 * @see com.base.log.LogAPI#log(java.lang.String, com.base.log.LogLevel)  
	 */
	@Override
	public void log(String message, LogLevel logLevel) {
		log(message, null, logLevel);
	}

	/**   
	 * @param message
	 * @param objects
	 * @param logLevel  
	 * @see com.base.log.LogAPI#log(java.lang.String, java.lang.Object[], com.base.log.LogLevel)  
	 */
	@Override
	public void log(String message, Object[] objects, LogLevel logLevel) {
		
	}
	
	/**   
	 * @return  
	 * @see com.base.log.LogAPI#getRootLogLevel()  
	 */
	@Override
	public LogLevel getRootLogLevel() {
		return LogLevel.ERROR;
	}

	/**   
	 * @return  
	 * @see com.base.log.LogAPI#getCustomLogLevel()  
	 */
	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return new HashMap<String, LogLevel>();
	}
}
