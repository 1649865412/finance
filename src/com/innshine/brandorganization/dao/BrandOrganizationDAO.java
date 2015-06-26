/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brandorganization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.brandorganization.entity.BrandOrganization;

public interface BrandOrganizationDAO extends JpaRepository<BrandOrganization, Long>, JpaSpecificationExecutor<BrandOrganization> {

}