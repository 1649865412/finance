/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.areainfo.service;

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
import com.innshine.areainfo.dao.AreaInfoDAO;
import com.innshine.areainfo.entity.AreaInfo;
import com.innshine.brand.service.BrandsInfoService;

@Service(value = "areaInfoService")
@Transactional
public class AreaInfoService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AreaInfoService.class);
	
	@Autowired
	private AreaInfoDAO areaInfoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.areainfo.service.AreaInfoService#get(java.lang.Long)
	 */

	public AreaInfo get(Long id)
	{
		return areaInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.areainfo.service.AreaInfoService#saveOrUpdate(com.innshine
	 * .areainfo.entity.AreaInfo)
	 */

	public void saveOrUpdate(AreaInfo areaInfo)
	{
		if (null != areaInfo)
		{
			areaInfo.setUpdateTime(new Date());
			areaInfoDAO.save(areaInfo);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.innshine.areainfo.service.AreaInfoService#delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		areaInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.areainfo.service.AreaInfoService#findAll(com.base.util.dwz
	 * .Page)
	 */

	public List<AreaInfo> findAll(Page page)
	{
		BrandsInfoService.setOrderBy(page);
		
		org.springframework.data.domain.Page<AreaInfo> springDataPage = areaInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.areainfo.service.AreaInfoService#findAll(com.base.util.dwz
	 * .Page)
	 */

	public List<AreaInfo> findAll()
	{
		return areaInfoDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.areainfo.service.AreaInfoService#findByExample(org.
	 * springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<AreaInfo> findByExample(Specification<AreaInfo> specification, Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<AreaInfo> springDataPage = areaInfoDAO.findAll(specification, PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<AreaInfo> getAreaList(long leve)
	{
		return areaInfoDAO.findByAreaLevel(leve);
	}
	
}
