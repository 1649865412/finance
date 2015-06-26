package com.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SqlDao;
import com.base.entity.component.Summary;
import com.base.service.component.BusinessCache;

@Service
public class SummaryCacheService {
	@Autowired
	private BusinessCache businessCache;
	
	private SqlDao sqlDao;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SummaryCacheService.class);

	private String summaryCacheKey = "summaryData";
	private String indexKey = "index";
	
	
	@Autowired
	public void setSqlDao(SqlDao sqlDao){
		this.sqlDao = sqlDao;
		//refresh();
	}
	
	public void refresh() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			List<Summary> list = sqlDao.queryListBySql(param,
					"index.querySummary", Summary.class);
		//	List<Summary> list =new ArrayList();
			businessCache.put(summaryCacheKey, indexKey, list);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Summary> getSummary() {
		List<Summary> list = (List<Summary>)businessCache.get(summaryCacheKey, indexKey);
		if(list == null){
			refresh();
			list = (List<Summary>)businessCache.get(summaryCacheKey, indexKey);
		}
		return list;
	}

}
