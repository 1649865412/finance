/**
 *  code generation
 */
package com.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.RolePermission;

public interface RolePermissionDAO extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {

	/**
	 * @param id
	 * @return
	 */
	List<RolePermission> findByRoleId(Long roleId);

}