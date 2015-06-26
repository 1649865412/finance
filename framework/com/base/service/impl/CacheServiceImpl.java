/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012
 * Filename:		com.base.service.impl.ServiceUtil.java
 * Class:			ServiceUtil
 * Date:			2012-9-14
 * Author:			Vigor
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.base.service.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.base.service.CacheService;

/** 
 * 	
 * @author 	Vigor
 * Version  1.1.0
 * @since   2012-9-14 上午9:59:55 
 */
@Service
public class CacheServiceImpl implements CacheService {
	
     
	private EntityManager em;
	@Resource(name="entityManagerFactory")
	public void setEm(EntityManagerFactory defaultEm){
		 
		if(defaultEm != null){
			em = defaultEm.createEntityManager();
		}
		
	}
	
	/**
	 * @see com.base.service.CacheService#clearAllCache()
	 */
	public void clearAllCache() {
		em.getEntityManagerFactory().getCache().evictAll();
	}

}
