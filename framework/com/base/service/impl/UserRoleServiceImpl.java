/**
 *  code generation
 */
package	com.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.UserRoleDAO;
import com.base.entity.main.UserRole;
import com.base.service.UserRoleService;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	private UserRoleDAO userRoleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserRoleService#get(java.lang.Long)  
	 */ 
	@Override
	public UserRole get(Long id) {
		return userRoleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.base.service.UserRoleService#saveOrUpdate(com.base.entity.main.UserRole)  
	 */
	@Override
	public void saveOrUpdate(UserRole userRole) {
		userRoleDAO.save(userRole);
	}

	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserRoleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		userRoleDAO.delete(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserRoleService#findAll(com.base.util.dwz.Page)  
	 */
	@Override
	public List<UserRole> findAll(Page page) {
		org.springframework.data.domain.Page<UserRole> springDataPage = userRoleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserRoleService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */
	@Override
	public List<UserRole> findByExample(
			Specification<UserRole> specification, Page page) {
		org.springframework.data.domain.Page<UserRole> springDataPage = userRoleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.base.service.UserRoleService#findByUserId(java.lang.Long)
	 */
	@Override
	public List<UserRole> findByUserId(Long userId) {
		return userRoleDAO.findByUserId(userId);
	}
	
}
