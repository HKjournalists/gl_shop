package com.appabc.cms.web.security;

import com.appabc.datas.cms.vo.UserPermission;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.dao.UserDao;
import com.appabc.datas.cms.dao.UserPermissionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 30, 2014 10:21:01 AM
 */
public class UserDetailsManager implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(UserDetailsManager.class);

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
		List<GrantedAuthority> authorities = new LinkedList<>();

		for (UserPermission up : userPermissions) {
			authorities.add(new UserPermissionAuthority(up.getPermission()));
            if (logger.isDebugEnabled()) {
                logger.debug("{} has authority: {} {}", userName, up.getPermission() != null ? up.getPermission().getName() : "", up.getPermission() != null ? up.getPermission().getDisplayName() : "");
            }
		}

		return new UserDetailsImpl(user, authorities);
	}

}
