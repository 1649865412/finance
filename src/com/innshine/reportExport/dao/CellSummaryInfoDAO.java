/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.reportExport.entity.CellSummaryInfo;

public interface CellSummaryInfoDAO extends JpaRepository<CellSummaryInfo, Long>,
		JpaSpecificationExecutor<CellSummaryInfo>
{
	List<CellSummaryInfo> findBySummaryTime(Date summaryTime);
}
