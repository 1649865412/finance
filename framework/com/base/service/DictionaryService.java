/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.Dictionary;
import com.base.util.dwz.Page;

public interface DictionaryService {
	Dictionary get(Long id);

	void saveOrUpdate(Dictionary dictionary);

	void delete(Long id);
	
	List<Dictionary> findAll(Page page);
	
	List<Dictionary> findByExample(Specification<Dictionary> specification, Page page);
	
	List<Dictionary> findByThemeName(String themeName, Page page);
}
