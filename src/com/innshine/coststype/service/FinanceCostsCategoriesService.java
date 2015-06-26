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
import com.innshine.coststype.dao.FinanceCostsCategoriesDAO;
import com.innshine.coststype.entity.FinanceCostsCategories;

@Service
@Transactional
public class FinanceCostsCategoriesService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCostsCategoriesService.class);
	@Autowired
	private FinanceCostsCategoriesDAO financeCostsCategoriesDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #get(java.lang.Long)
	 */

	public FinanceCostsCategories get(Long id)
	{
		return financeCostsCategoriesDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #saveOrUpdate(com.innshine.finance_costs_categories.entity.
	 * FinanceCostsCategories)
	 */

	public void saveOrUpdate(FinanceCostsCategories financeCostsCategories)
	{
		if (null != financeCostsCategories)
		{
			financeCostsCategories.setUpdateTime(new Date());
			financeCostsCategoriesDAO.save(financeCostsCategories);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		financeCostsCategoriesDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #findAll(com.base.util.dwz.Page)
	 */

	public List<FinanceCostsCategories> findAll(Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<FinanceCostsCategories> springDataPage = financeCostsCategoriesDAO
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #findAll(com.base.util.dwz.Page)
	 */

	public List<FinanceCostsCategories> findAll()
	{
		return financeCostsCategoriesDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.finance_costs_categories.service.FinanceCostsCategoriesService
	 * #findByExample(org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<FinanceCostsCategories> findByExample(Specification<FinanceCostsCategories> specification, Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<FinanceCostsCategories> springDataPage = financeCostsCategoriesDAO
				.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
