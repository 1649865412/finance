/**
 * 
 */
package com.utils;

import org.apache.shiro.subject.Subject;

import com.base.entity.main.User;
import com.base.shiro.ShiroUser;

/**
 * @author KETAYAO
 *
 */
public abstract class SecurityUtils {
	public static User getLoginUser() {
		return getShiroUser().getUser();
	}
	
	public static ShiroUser getShiroUser() {
		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		
		return shiroUser;
	}

	public static Subject getSubject() {
		return org.apache.shiro.SecurityUtils.getSubject();
	}
}
