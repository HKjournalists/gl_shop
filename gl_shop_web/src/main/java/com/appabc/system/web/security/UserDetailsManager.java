package com.appabc.system.web.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.appabc.system.User;
import com.appabc.system.UserDao;
import com.appabc.system.UserPermission;
import com.appabc.system.UserPermissionDao;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 30, 2014 10:21:01 AM
 */
public class UserDetailsManager implements UserDetailsService {
	
	private final static String ROLE_PREFIX = "ROLE_";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserPermissionDao userPermissionDao;

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)  
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);

		List<UserPermission> userPermissions = userPermissionDao.loadUserPermission(user.getId());
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		
		for (UserPermission up : userPermissions) {
			SimpleGrantedAuthority auth = new SimpleGrantedAuthority(ROLE_PREFIX + up.getPermission().getName());
			authorities.add(auth);
		}

		return new UserDetailsImpl(userDao.findByUserName(userName), authorities);
	}

}
