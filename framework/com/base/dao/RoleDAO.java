/**
 *  code generation
 */
package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.Role;

public interface RoleDAO extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}