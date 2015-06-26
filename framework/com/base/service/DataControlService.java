/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.DataControl;
import com.base.util.dwz.Page;

public interface DataControlService {
	DataControl get(Long id);

	void saveOrUpdate(DataControl dataControl);

	void delete(Long id);
	
	List<DataControl> findAll(Page page);
	
	List<DataControl> findByExample(Specification<DataControl> specification, Page page);
}
