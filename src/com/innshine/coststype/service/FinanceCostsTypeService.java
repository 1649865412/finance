/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.coststype.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.brand.service.BrandsInfoService;
import com.innshine.coststype.dao.FinanceCostsTypeDAO;
import com.innshine.coststype.entity.FinanceCostsType;

@Service
@Transactional
public class FinanceCostsTypeService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCostsTypeService.class);
	
	@Autowired
	private FinanceCostsTypeDAO financeCostsTypeDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#get(java.lang.
	 * Long)
	 */

	public FinanceCostsType get(Long id)
	{
		return financeCostsTypeDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#saveOrUpdate(com
	 * .innshine.coststype.entity.FinanceCostsType)
	 */

	public void saveOrUpdate(FinanceCostsType financeCostsType)
	{
		if (null != financeCostsType)
		{
			financeCostsType.setUpdateTime(new Date());
			financeCostsTypeDAO.save(financeCostsType);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#delete(java.lang
	 * .Long)
	 */

	public void delete(Long id)
	{
		financeCostsTypeDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#findAll(com.base
	 * .util.dwz.Page)
	 */

	public List<FinanceCostsType> findAll(Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<FinanceCostsType> springDataPage = financeCostsTypeDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#findAll(com.base
	 * .util.dwz.Page)
	 */

	public List<FinanceCostsType> findAll()
	{
		return financeCostsTypeDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.coststype.service.FinanceCostsTypeService#findByExample(
	 * org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<FinanceCostsType> findByExample(Specification<FinanceCostsType> specification, Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<FinanceCostsType> springDataPage = financeCostsTypeDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
