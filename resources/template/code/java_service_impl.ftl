/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package	${pknService};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import ${pknEntity}.${className};
import ${pknDAO}.${className}DAO; 

@Service
@Transactional
public class ${className}Service  {
	private static final Logger log = LoggerFactory.getLogger(${className}Service.class);
	@Autowired
	private ${className}DAO ${instanceName}DAO;

	/*
	 * (non-Javadoc)
	 * @see ${pknService}.${className}Service#get(java.lang.Long)  
	 */ 
	 
	public ${className} get(Long id) {
		return ${instanceName}DAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see ${pknService}.${className}Service#saveOrUpdate(${pknEntity}.${className})  
	 */
 
	public void saveOrUpdate(${className} ${instanceName}) {
		${instanceName}DAO.save(${instanceName});
	}

	/*
	 * (non-Javadoc)
	 * @see ${pknService}.${className}Service#delete(java.lang.Long)  
	 */
 
	public void delete(Long id) {
		${instanceName}DAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ${pknService}.${className}Service#findAll(com.base.util.dwz.Page)  
	 */
	 
	public List<${className}> findAll(Page page) {
		org.springframework.data.domain.Page<${className}> springDataPage = ${instanceName}DAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	  
	 * (non-Javadoc)
	 * @see ${pknService}.${className}Service#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */ 
	public List<${className}> findByExample(
			Specification<${className}> specification, Page page) {
		org.springframework.data.domain.Page<${className}> springDataPage = ${instanceName}DAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
}
