/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.service;

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
import com.innshine.classify.dao.ClassifyInfoTwoDAO;
import com.innshine.classify.entity.ClassifyInfoTwo;

@Service
@Transactional
public class ClassifyInfoTwoService
{
	private static final Logger log = LoggerFactory.getLogger(ClassifyInfoTwoService.class);
	
	@Autowired
	private ClassifyInfoTwoDAO classifyInfoTwoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classifytwo.service.ClassifyInfoTwoService#get(java.lang
	 * .Long)
	 */

	public ClassifyInfoTwo get(Long id)
	{
		return classifyInfoTwoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classifytwo.service.ClassifyInfoTwoService#saveOrUpdate(
	 * com.innshine.classifytwo.entity.ClassifyInfoTwo)
	 */

	public void saveOrUpdate(ClassifyInfoTwo classifyInfoTwo)
	{
		if (null != classifyInfoTwo)
		{
			classifyInfoTwo.setUpdateTime(new Date());
			classifyInfoTwoDAO.save(classifyInfoTwo);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classifytwo.service.ClassifyInfoTwoService#delete(java.lang
	 * .Long)
	 */

	public void delete(Long id)
	{
		classifyInfoTwoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classifytwo.service.ClassifyInfoTwoService#findAll(com.base
	 * .util.dwz.Page)
	 */

	public List<ClassifyInfoTwo> findAll(Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<ClassifyInfoTwo> springDataPage = classifyInfoTwoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classifytwo.service.ClassifyInfoTwoService#findByExample
	 * (org.springframework.data.jpa.domain.Specification,
	 * com.base.util.dwz.Page)
	 */
	public List<ClassifyInfoTwo> findByExample(Specification<ClassifyInfoTwo> specification, Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<ClassifyInfoTwo> springDataPage = classifyInfoTwoDAO.findAll(
				specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
