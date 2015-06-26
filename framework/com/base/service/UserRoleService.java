/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.UserRole;
import com.base.util.dwz.Page;

public interface UserRoleService {
	UserRole get(Long id);

	void saveOrUpdate(UserRole userRole);

	void delete(Long id);
	
	List<UserRole> findAll(Page page);
	
	List<UserRole> findByExample(Specification<UserRole> specification, Page page);
	
	List<UserRole> findByUserId(Long userId);
}
