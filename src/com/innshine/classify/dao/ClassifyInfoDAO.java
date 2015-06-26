/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.classify.entity.ClassifyInfo;

public interface ClassifyInfoDAO extends JpaRepository<ClassifyInfo, Long>, JpaSpecificationExecutor<ClassifyInfo>
{
	public List<ClassifyInfo> findByClassifyTypeIn(List<String>list);
}
