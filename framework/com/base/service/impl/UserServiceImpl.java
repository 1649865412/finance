/**
 *  code generation
 */
package	com.base.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.UserDAO;
import com.base.entity.main.User;
import com.base.exception.ExistedException;
import com.base.exception.IncorrectPasswordException;
import com.base.exception.NotDeletedException;
import com.base.exception.ServiceException;
import com.base.service.UserService;
import com.base.shiro.ShiroDbRealm;
import com.base.shiro.ShiroDbRealm.HashPassword;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;
import com.utils.SecurityUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ShiroDbRealm shiroRealm;

	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public User get(Long id) {
		return userDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.base.service.UserService#saveOrUpdate(com.base.entity.main.User)  
	 */
	@Override
	public void saveOrUpdate(User user) {
		if (user.getId() == null) {
			if (userDAO.getByUsername(user.getUsername()) != null) {
				throw new ExistedException("登录名：" + user.getUsername() + "已存在。");
			}
			
			//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
				HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}
		}
		
		userDAO.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new NotDeletedException("不能删除超级管理员用户。");
		}
		User user = userDAO.findOne(id);
		userDAO.delete(user.getId());
		
		// TODO 从shiro中注销
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserService#findAll(com.base.util.dwz.Page)  
	 */
	@Override
	public List<User> findAll(Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.base.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.base.util.dwz.Page)	
	 */
	@Override
	public List<User> findByExample(
			Specification<User> specification, Page page) {
		org.springframework.data.domain.Page<User> springDataPage = userDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/* (non-Javadoc)
	 * @see com.base.service.UserService#updatePwd(com.base.entity.main.User, java.lang.String)
	 */
	@Override
	public void updatePwd(User user, String newPwd) throws ServiceException {
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
		if (isMatch) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
			
			userDAO.save(user);
			shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
			
			return; 
		}
		
		throw new IncorrectPasswordException("用户密码错误！");
	}

	/* (non-Javadoc)
	 * @see com.base.service.UserService#resetPwd(com.base.entity.main.User, java.lang.String)
	 */
	@Override
	public void resetPwd(User user, String newPwd) {
		if (newPwd == null) {
			newPwd = "123456";
		}
		
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		
		userDAO.save(user);
	}

	/* (non-Javadoc)
	 * @see com.base.service.UserService#getByUsername(java.lang.String)
	 */
	@Override
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}
}
