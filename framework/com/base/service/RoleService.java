/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.Role;
import com.base.util.dwz.Page;

public interface RoleService {
	Role get(Long id);

	void saveOrUpdate(Role role);

	void delete(Long id);
	
	List<Role> findAll(Page page);
	
	List<Role> findByExample(Specification<Role> specification, Page page);
}
