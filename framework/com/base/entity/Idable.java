/**
 * <pre>
 * Date:			2013年10月17日
 * Author:			Vigor
 * Description:
 * </pre>
 **/
 
package com.base.entity;

import java.io.Serializable;

/** 
 * 	
 * @author 	Vigor
 * @since   2013年10月17日 下午4:13:13 
 */

public interface Idable<T extends Serializable> {
	public T getId();

	public void setId(T id);
}
