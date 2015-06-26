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
import com.innshine.reportExport.dao.RowMonthSummaryInfoDAO;
import com.innshine.reportExport.entity.RowMonthSummaryInfo;

@Service
@Transactional
public class RowMonthSummaryInfoService
{
	private static final Logger log = LoggerFactory.getLogger(RowMonthSummaryInfoService.class);
	
	@Autowired
	private RowMonthSummaryInfoDAO rowMonthSummaryInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_month_summary_info.service.RowMonthSummaryInfoService
	 * #get(java.lang.Long)
	 */

	public RowMonthSummaryInfo get(Long id)
	{
		return rowMonthSummaryInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_month_summary_info.service.RowMonthSummaryInfoService
	 * #saveOrUpdate
	 * (com.innshine.row_month_summary_info.entity.RowMonthSummaryInfo)
	 */

	public void saveOrUpdate(RowMonthSummaryInfo rowMonthSummaryInfo)
	{
		rowMonthSummaryInfoDAO.save(rowMonthSummaryInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_month_summary_info.service.RowMonthSummaryInfoService
	 * #delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		rowMonthSummaryInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_month_summary_info.service.RowMonthSummaryInfoService
	 * #findAll(com.base.util.dwz.Page)
	 */

	public List<RowMonthSummaryInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<RowMonthSummaryInfo> springDataPage = rowMonthSummaryInfoDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_month_summary_info.service.RowMonthSummaryInfoService
	 * #findByExample(org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<RowMonthSummaryInfo> findByExample(Specification<RowMonthSummaryInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<RowMonthSummaryInfo> springDataPage = rowMonthSummaryInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
