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
import com.innshine.reportExport.dao.CellSummaryInfoDAO;
import com.innshine.reportExport.entity.CellSummaryInfo;

@Service
@Transactional
public class CellSummaryInfoService
{
	private static final Logger log = LoggerFactory.getLogger(CellSummaryInfoService.class);
	@Autowired
	private CellSummaryInfoDAO cellSummaryInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#get(java
	 * .lang.Long)
	 */

	public CellSummaryInfo get(Long id)
	{
		return cellSummaryInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#saveOrUpdate
	 * (com.innshine.cell_summary_info.entity.CellSummaryInfo)
	 */

	public void saveOrUpdate(CellSummaryInfo cellSummaryInfo)
	{
		cellSummaryInfoDAO.save(cellSummaryInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#saveOrUpdate
	 * (com.innshine.cell_summary_info.entity.CellSummaryInfo)
	 */

	public void saveOrUpdate(List<CellSummaryInfo> cellSummaryInfos)
	{
		if (CollectionUtils.isNotEmpty(cellSummaryInfos))
		{
			cellSummaryInfoDAO.save(cellSummaryInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#delete(
	 * java.lang.Long)
	 */

	public void delete(Long id)
	{
		cellSummaryInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#findAll
	 * (com.base.util.dwz.Page)
	 */

	public List<CellSummaryInfo> findAll(Page page)
	{
		org.springframework.data.domain.Page<CellSummaryInfo> springDataPage = cellSummaryInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.row_summary_info.service.RowSummaryInfoService#delete(java
	 * .lang.Long)
	 */

	public void delete(List<CellSummaryInfo> cellSummaryInfos)
	{
		if (CollectionUtils.isNotEmpty(cellSummaryInfos))
		{
			cellSummaryInfoDAO.delete(cellSummaryInfos);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#findAll
	 * (com.base.util.dwz.Page)
	 */

	public List<CellSummaryInfo> findBySummaryTime(Date summaryTime)
	{
		if (null != summaryTime)
		{
			return cellSummaryInfoDAO.findBySummaryTime(summaryTime);
		}
		
		return null;
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.cell_summary_info.service.CellSummaryInfoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<CellSummaryInfo> findByExample(Specification<CellSummaryInfo> specification, Page page)
	{
		org.springframework.data.domain.Page<CellSummaryInfo> springDataPage = cellSummaryInfoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
