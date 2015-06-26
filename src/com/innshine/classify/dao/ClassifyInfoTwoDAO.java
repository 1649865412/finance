/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.classify.entity.ClassifyInfoTwo;

public interface ClassifyInfoTwoDAO extends JpaRepository<ClassifyInfoTwo, Long>,
		JpaSpecificationExecutor<ClassifyInfoTwo>
{
	
}
