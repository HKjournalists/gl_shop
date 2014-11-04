package com.appabc.system.web.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appabc.system.User;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 29, 2014 10:42:44 PM
 */
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = -1316540233851521478L;

	private User user;
	private List<? extends GrantedAuthority> perms;
	
	public UserDetailsImpl(User user, List<? extends GrantedAuthority> perms) {
		this.user = user;
		this.perms = perms;
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()  
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perms;
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()  
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()  
	 */
	@Override
	public String getUsername() {
		return user.getUserName();
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()  
	 */
	@Override
	public boolean isAccountNonExpired() {
		return this.user.isEnabled();
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()  
	 */
	@Override
	public boolean isAccountNonLocked() {
		return user.isEnabled();
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()  
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return user.isEnabled();
	}

	/* (non-Javadoc)  
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()  
	 */
	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
