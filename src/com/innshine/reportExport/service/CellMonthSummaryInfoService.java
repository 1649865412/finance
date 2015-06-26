/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.reportExport.dao.CellMonthSummaryInfoDAO;
import com.innshine.reportExport.entity.CellMonthSummaryInfo;

@Service
@Transactional
public class CellMonthSummaryInfoService
{
	private static final Logger log = LoggerFactory.getLogger(CellMonthSummaryInfoService.class);
	
	@Autowired
	private CellMonthSummaryInfoDAO cellMonthSummaryInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_month_summary_info.service.CellMonthSummaryInfoService
	 * #get(java.lang.Long)
	 */

	public CellMonthSummaryInfo get(Long id)
	{
		return cellMonthSummaryInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_month_summary_info.service.CellMonthSummaryInfoService
	 * #saveOrUpdate
	 * (com.innshine.cell_month_summary_info.entity.CellMonthSummaryInfo)
	 */

	public void saveOrUpdate(CellMonthSummaryInfo cellMonthSummaryInfo)
	{
		cellMonthSummaryInfoDAO.save(cellMonthSummaryInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_month_summary_info.service.CellMonthSummaryInfoService
	 * #delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		cellMonthSummaryInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_month_summary_info.service.CellMonthSummaryInfoService
	 * #findAll(com.base.util.dwz.Page)
	 */

	public List<CellMonthSummaryInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<CellMonthSummaryInfo> springDataPage = cellMonthSummaryInfoDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_month_summary_info.service.CellMonthSummaryInfoService
	 * #findByExample(org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<CellMonthSummaryInfo> findByExample(Specification<CellMonthSummaryInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<CellMonthSummaryInfo> springDataPage = cellMonthSummaryInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
