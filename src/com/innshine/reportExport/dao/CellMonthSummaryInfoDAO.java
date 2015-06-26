/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.reportExport.entity.CellMonthSummaryInfo;

public interface CellMonthSummaryInfoDAO extends JpaRepository<CellMonthSummaryInfo, Long>,
		JpaSpecificationExecutor<CellMonthSummaryInfo>
{
	
}
