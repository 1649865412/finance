/**
 *  code generation
 */
package	com.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.OrganizationRoleDAO;
import com.base.entity.main.OrganizationRole;
import com.base.service.OrganizationRoleService;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;

@Service
@Transactional
public class OrganizationRoleServiceImpl implements OrganizationRoleService {
	
	@Autowired
	private OrganizationRoleDAO organizationRoleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.base.service.OrganizationRoleService#get(java.lang.Long)  
	 */ 
	@Override
	public OrganizationRole get(Long id) {
		return organizationRoleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.base.service.OrganizationRoleService#saveOrUpdate(com.base.entity.main.OrganizationRole)  
	 */
	@Override
	public void saveOrUpdate(OrganizationRole organizationRole) {
		organizationRoleDAO.save(organizationRole);
	}

	/*
	 * (non-Javadoc)
	 * @see com.base.service.OrganizationRoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		organizationRoleDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.OrganizationRoleService#findAll(com.base.util.dwz.Page)  
	 */
	@Override
	public List<OrganizationRole> findAll(Page page) {
		org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.OrganizationRoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */
	@Override
	public List<OrganizationRole> findByExample(
			Specification<OrganizationRole> specification, Page page) {
		org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.base.service.OrganizationRoleService#findByOrganizationId(java.lang.Long)
	 */
	@Override
	public List<OrganizationRole> findByOrganizationId(Long organizationId) {
		return organizationRoleDAO.findByOrganizationId(organizationId);
	}
}
