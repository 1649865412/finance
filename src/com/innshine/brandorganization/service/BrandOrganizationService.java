/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brandorganization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.brandorganization.entity.BrandOrganization;
import com.innshine.brandorganization.dao.BrandOrganizationDAO;

@Service
@Transactional
public class BrandOrganizationService
{
	private static final Logger log = LoggerFactory.getLogger(BrandOrganizationService.class);
	@Autowired
	private BrandOrganizationDAO brandOrganizationDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brandorganization.service.BrandOrganizationService#get(java
	 * .lang.Long)
	 */
	
	public BrandOrganization get(Long id)
	{
		return brandOrganizationDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brandorganization.service.BrandOrganizationService#saveOrUpdate
	 * (com.innshine.brandorganization.entity.BrandOrganization)
	 */
	
	public void saveOrUpdate(BrandOrganization brandOrganization)
	{
		brandOrganizationDAO.save(brandOrganization);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brandorganization.service.BrandOrganizationService#delete
	 * (java.lang.Long)
	 */
	
	public void delete(Long id)
	{
		brandOrganizationDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brandorganization.service.BrandOrganizationService#findAll
	 * (com.base.util.dwz.Page)
	 */
	
	public List<BrandOrganization> findAll(Page page)
	{
		org.springframework.data.domain.Page<BrandOrganization> springDataPage = brandOrganizationDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<BrandOrganization> findAll()
	{
		return brandOrganizationDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brandorganization.service.BrandOrganizationService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<BrandOrganization> findByExample(Specification<BrandOrganization> specification, Page page)
	{
		org.springframework.data.domain.Page<BrandOrganization> springDataPage = brandOrganizationDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
