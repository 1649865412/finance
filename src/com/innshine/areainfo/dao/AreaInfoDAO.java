/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.areainfo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.areainfo.entity.AreaInfo;

public interface AreaInfoDAO extends JpaRepository<AreaInfo, Long>, JpaSpecificationExecutor<AreaInfo>
{
	public List<AreaInfo> findByAreaLevel(long areaLevel);
}
