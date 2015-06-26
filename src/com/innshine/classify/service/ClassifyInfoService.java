/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.classify.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.innshine.brand.service.BrandsInfoService;
import com.innshine.classify.dao.ClassifyInfoDAO;
import com.innshine.classify.entity.ClassifyInfo;
import com.innshine.reportExport.Constants;

@Service
@Transactional
public class ClassifyInfoService
{
	private static final Logger log = LoggerFactory.getLogger(ClassifyInfoService.class);
	@Autowired
	private ClassifyInfoDAO classifyInfoDAO;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classify.service.ClassifyInfoService#get(java.lang.Long)
	 */

	public ClassifyInfo get(Long id)
	{
		return classifyInfoDAO.findOne(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classify.service.ClassifyInfoService#saveOrUpdate(com.innshine
	 * .classify.entity.ClassifyInfo)
	 */

	public void saveOrUpdate(ClassifyInfo classifyInfo)
	{
		if (null != classifyInfo)
		{
			classifyInfo.setUpdateTime(new Date());
		}
		classifyInfoDAO.save(classifyInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classify.service.ClassifyInfoService#delete(java.lang.Long)
	 */

	public void delete(Long id)
	{
		classifyInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classify.service.ClassifyInfoService#findAll(com.base.util
	 * .dwz.Page)
	 */

	public List<ClassifyInfo> findAll(Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<ClassifyInfo> springDataPage = classifyInfoDAO.findAll(PageUtils
				.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.innshine.classify.service.ClassifyInfoService#findAll(com.base.util
	 * .dwz.Page)
	 */

	public List<ClassifyInfo> findAll()
	{
		return classifyInfoDAO.findAll();
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.innshine.classify.service.ClassifyInfoService#findByExample(org.
	 * springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)
	 */
	public List<ClassifyInfo> findByExample(Specification<ClassifyInfo> specification, Page page)
	{
		BrandsInfoService.setOrderBy(page);
		org.springframework.data.domain.Page<ClassifyInfo> springDataPage = classifyInfoDAO.findAll(specification,
				PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	

	
	
	/**
	 * 功能:获取顶部分类
	 * <p>作者 杨荣忠 2015-1-26 下午01:22:20
	 * @return
	 * @throws Exception
	 */
	public List<ClassifyInfo> getZhuYinTopClass() throws Exception{
		List list = Arrays.asList(Constants.TOP_CLASS_TYPE);
		return classifyInfoDAO.findByClassifyTypeIn(list);
	}
}
