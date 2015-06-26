/**
 *  code generation
 */
package com.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.base.entity.main.User;

public interface UserDAO extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User getByUsername(String username);

	List<User> findByOrganizationId(Long id);
}