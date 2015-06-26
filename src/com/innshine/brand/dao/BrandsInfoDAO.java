/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brand.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.brand.entity.BrandsInfo;

public interface BrandsInfoDAO extends JpaRepository<BrandsInfo, Long>, JpaSpecificationExecutor<BrandsInfo>
{
	
}
