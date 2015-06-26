/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.innshine.reportExport.entity.RowSummaryInfo;

public interface RowSummaryInfoDAO extends JpaRepository<RowSummaryInfo, Long>,
		JpaSpecificationExecutor<RowSummaryInfo>
{
	List<RowSummaryInfo> findBySummaryTime(Date summaryTime);
}
