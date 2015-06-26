/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.brand.service;

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
import com.innshine.brand.dao.BrandsInfoDAO;
import com.innshine.brand.entity.BrandsInfo;
import com.innshine.reportExport.Constants;

@Service
@Transactional
public class BrandsInfoService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandsInfoService.class);
	@Autowired
	private BrandsInfoDAO brandsInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.brand.service.BrandsInfoService#get(java.lang.Long)
	 */

	public BrandsInfo get(Long id)
	{
		return brandsInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brand.service.BrandsInfoService#saveOrUpdate(com.innshine
	 * .brand.entity.BrandsInfo)
	 */

	public void saveOrUpdate(BrandsInfo brandsInfo)
	{
		if (null != brandsInfo)
		{
			brandsInfo.setUpdateTime(new Date());
			brandsInfoDAO.save(brandsInfo);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.brand.service.BrandsInfoService#delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		brandsInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brand.service.BrandsInfoService#findAll(com.base.util.dwz
	 * .Page)
	 */

	public List<BrandsInfo> findAll(Page page)
	{
		setOrderBy(page);
		org.springframework.data.domain.Page<BrandsInfo> springDataPage = brandsInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public static void setOrderBy(Page page)
	{
		if (page != null)
		{
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
			page.setOrderField(Constants.ORDER_FIELD_NAME);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.brand.service.BrandsInfoService#findAll(com.base.util.dwz
	 * .Page)
	 */

	public List<BrandsInfo> findAll()
	{
		return brandsInfoDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.brand.service.BrandsInfoService#findByExample(org.
	 * springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<BrandsInfo> findByExample(Specification<BrandsInfo> specification, Page page)
	{
		setOrderBy(page);
		org.springframework.data.domain.Page<BrandsInfo> springDataPage = brandsInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
