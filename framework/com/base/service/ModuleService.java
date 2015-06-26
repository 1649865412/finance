/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.Module;
import com.base.util.dwz.Page;

public interface ModuleService {
	Module get(Long id);

	void saveOrUpdate(Module module);

	void delete(Long id);
	
	List<Module> findAll(Page page);
	
	List<Module> findByExample(Specification<Module> specification, Page page);
	
	Module getTree();
	
	List<Module> findAll();
}
