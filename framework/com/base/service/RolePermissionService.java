/**
 *  code generation
 */
package com.base.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.base.entity.main.RolePermission;
import com.base.util.dwz.Page;

public interface RolePermissionService {
	RolePermission get(Long id);

	void saveOrUpdate(RolePermission rolePermission);

	void delete(Long id);
	
	List<RolePermission> findAll(Page page);
	
	List<RolePermission> findByExample(Specification<RolePermission> specification, Page page);

	/**
	 * @param id
	 * @return
	 */
	List<RolePermission> findByRoleId(Long id);

	/**
	 * @param newRList
	 */
	void save(List<RolePermission> newRList);
	
	void delete(List<RolePermission> delRList);
}
