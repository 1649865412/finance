/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.dao.component.ResourceDAO.java
 * Class:			ResourceDAO
 * Date:			2013-6-28
 * Author:			Vigor
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.dao.component;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.component.Resource;

/** 
 * 	
 * @author 	Vigor
 * Version  3.1.0
 * @since   2013-6-28 上午10:18:10 
 */

public interface ResourceDAO extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource>{
	Resource getByUuid(String uuid);
}
