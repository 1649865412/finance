/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.reportExport.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.reportExport.dao.RowSummaryInfoDAO;
import com.innshine.reportExport.entity.RowSummaryInfo;

@Service
@Transactional
public class RowSummaryInfoService
{
	private static final Logger log = LoggerFactory.getLogger(RowSummaryInfoService.class);
	
	@Autowired
	private RowSummaryInfoDAO rowSummaryInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#get(java.
	 * lang.Long)
	 */

	public RowSummaryInfo get(Long id)
	{
		return rowSummaryInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#saveOrUpdate
	 * (com.innshine.row_summary_info.entity.RowSummaryInfo)
	 */

	public void saveOrUpdate(RowSummaryInfo rowSummaryInfo)
	{
		rowSummaryInfoDAO.save(rowSummaryInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#saveOrUpdate
	 * (com.innshine.row_summary_info.entity.RowSummaryInfo)
	 */

	public void saveOrUpdate(List<RowSummaryInfo> rowSummaryInfos)
	{
		if (CollectionUtils.isNotEmpty(rowSummaryInfos))
		{
			rowSummaryInfoDAO.save(rowSummaryInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(Long id)
	{
		rowSummaryInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(List<RowSummaryInfo> rowSummaryInfos)
	{
		if (CollectionUtils.isNotEmpty(rowSummaryInfos))
		{
			rowSummaryInfoDAO.delete(rowSummaryInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#findAll(com
	 * .base.util.dwz.Page)
	 */

	public List<RowSummaryInfo> findBySummaryTime(Date summaryTime)
	{
		if (null != summaryTime)
		{
			return rowSummaryInfoDAO.findBySummaryTime(summaryTime);
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#findAll(com
	 * .base.util.dwz.Page)
	 */

	public List<RowSummaryInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<RowSummaryInfo> springDataPage = rowSummaryInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<RowSummaryInfo> findByExample(Specification<RowSummaryInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<RowSummaryInfo> springDataPage = rowSummaryInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
