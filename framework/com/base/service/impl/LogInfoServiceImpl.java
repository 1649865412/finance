/**
 *  code generation
 */
package	com.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.LogInfoDAO;
import com.base.entity.main.LogInfo;
import com.base.service.LogInfoService;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;

@Service
@Transactional
public class LogInfoServiceImpl implements LogInfoService {
	
	@Autowired
	private LogInfoDAO logInfoDAO;

	/*
	 * (non-Javadoc)
	 * @see com.base.service.LogInfoService#get(java.lang.Long)  
	 */ 
	@Override
	public LogInfo get(Long id) {
		return logInfoDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.base.service.LogInfoService#saveOrUpdate(com.base.entity.main.LogInfo)  
	 */
	@Override
	public void saveOrUpdate(LogInfo logInfo) {
		logInfoDAO.save(logInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.base.service.LogInfoService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		logInfoDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.LogInfoService#findAll(com.base.util.dwz.Page)  
	 */
	@Override
	public List<LogInfo> findAll(Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.LogInfoService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */
	@Override
	public List<LogInfo> findByExample(
			Specification<LogInfo> specification, Page page) {
		org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDAO.findAll(specification, PageUtils.createPageable(page));
		//System.out.println("============"+springDataPage.getTotalElements());
	//	System.out.println("=============="+page.getTotalPage());
		page.setTotalCount(springDataPage.getTotalElements());
		//System.out.println("=============="+page.getTotalPage());
		return springDataPage.getContent();
	}
}
