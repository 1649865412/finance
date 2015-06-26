/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.exception.ExistedException.java
 * Class:			ExistedException
 * Date:			2012-8-13
 * Author:			Vigor
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.exception;

/** 
 * 	
 * @author 	Vigor
 * Version  1.1.0
 * @since   2012-8-13 上午10:54:15 
 */

public class ExistedException extends ServiceException {

	/** 描述  */
	private static final long serialVersionUID = -598071452360556064L;

	public ExistedException() {
		super();
	}

	public ExistedException(String message) {
		super(message);
	}

	public ExistedException(Throwable cause) {
		super(cause);
	}

	public ExistedException(String message, Throwable cause) {
		super(message, cause);
	}
}
