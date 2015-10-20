package com.appabc.cms.web.security;

import com.appabc.datas.cms.vo.Permission;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by zouxifeng on 11/8/14.
 */
public class UserPermissionAuthority implements GrantedAuthority {

    private final static long serialVersionUID = 602664457427556559L;

    private final static String ROLE_PREFIX = "ROLE_";

    private Permission permission;

    public UserPermissionAuthority(Permission perm) {
       this.permission = perm;
    }

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + permission.getName();
    }

    public Permission getPermission() {
        return permission;
    }
}
