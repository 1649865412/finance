package com.base.service.component;

import java.io.IOException;
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class BusinessCache {
 
	private CacheManager manager = CacheManager.getInstance();
	public Object get(String cacheKey,String key )
	{ 
		Cache cache = getCache(cacheKey);
		if(cache != null){
			Element elem =  cache.get(key); 
			return elem.getObjectValue();
		}
		return null; 
	}
	
	 
	
	private Cache getCache(String cacheKey){   
		Cache cache = null;
		try { 
			cache = manager.getCache(cacheKey); 
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return cache;
	}

	public boolean hasCache(String key )
	{
		// FIXED Auto-generated method stub
		return false;
	}

	public void put(String cacheKey,String key,Object value )
	{
		Cache cache = getCache(cacheKey);
		if(cache != null){
			cache.put(new Element(key,value));
		}
		 
	}

	public void removeCache(String cacheKey,String key )
	{
		Cache cache = getCache(cacheKey);
		if(cache != null){
			cache.remove(key);
		} 
	}

	public void replaceCache(String cacheKey,String key,Object value )
	{
		Cache cache = getCache(cacheKey);
		if(cache != null){
			cache.remove(key);
			cache.put(new Element(key,value)); 
		} 
	}
}
