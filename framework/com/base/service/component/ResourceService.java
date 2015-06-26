/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.service.component.ResourceService.java
 * Class:			ResourceService
 * Date:			2013-6-28
 * Author:			Vigor
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.service.component;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.component.Resource;
import com.base.util.dwz.Page;

/** 
 * 	
 * @author 	Vigor
 * Version  3.1.0
 * @since   2013-6-28 上午10:19:01 
 */

public interface ResourceService {
	Resource get(Long id);

	void saveOrUpdate(Resource resource);

	void delete(Long id);
	
	List<Resource> findAll(Page page);
	
	List<Resource> findByExample(Specification<Resource> specification, Page page);
	
	Resource getByUuid(String uuid);
}
