package com.appabc.datas.system;


import com.appabc.datas.system.dao.UserDao;
import com.appabc.datas.system.dao.UserPermissionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Zou Xifeng
 * @version     : 1.0
 * Create Date  : Oct 15, 2014 3:57:58 PM
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPermissionDao userPermissionDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(int userId) {
    	User user = userDao.get(userId);
    	loadUserPermissions(user);
    	return user;
    }

    public boolean createUser(User user) {
        User u = userDao.findByUserName(user.getUserName());
        if (u != null) {
            if (logger.isInfoEnabled()) {
                logger.info("Username with [{}] is already existed.", user.getUserName());
            }
            return false;
        }

        user.setPassword(transformPassword(user.getPassword()));
        boolean result = userDao.create(user);

        if (!result) {
            if (logger.isDebugEnabled()) {
                logger.debug("Create system user result: [{}]", result);
            }
            return false;
        }

        List<UserPermission> permissions = user.getPermissions();
        if (!CollectionUtils.isEmpty(permissions)) {
        	for (UserPermission up : permissions) {
        		up.setUserId(user.getId());
        		userPermissionDao.create(up);
        	}
        }

        return result;
    }

    // TODO: Update SecurityContext after updating user permissions.
    public void updateUser(User user, List<Permission> permissions) {
        userDao.update(user);
        Set<Integer> currentPermIds = new HashSet<Integer>();
        if (!CollectionUtils.isEmpty(user.getPermissions())) {
            for (UserPermission up : user.getPermissions()) {
            	currentPermIds.add(up.getPermission().getId());
            }
        }
        Set<Integer> permIds = new HashSet<Integer>();
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission perm : permissions) {
            	permIds.add(perm.getId());
            }
        }
        Set<Integer> removePermId = new HashSet<Integer>(currentPermIds);
        removePermId.removeAll(permIds);
        Set<Integer> newPermId = new HashSet<Integer>(permIds);
        newPermId.removeAll(currentPermIds);
        for (Integer permId : removePermId) {
        	userPermissionDao.delete(user.getId(), permId);
        }
        for (Integer permId : newPermId) {
        	UserPermission up = new UserPermission();
        	up.setUserId(user.getId());
        	up.setPermission(permId);
        	userPermissionDao.create(up);
        }
    }

    private int countUser(Map<String, Object> params) {
        return userDao.countBy(params);
    }

    public Context<User> querySystemUserList(Context<User> ctx) {
        int count = countUser(ctx.getParameters());
        ctx.setTotalRows(count);
    	List<User> users = userDao.queryForList(ctx.getParameters(), ctx.getStart(), ctx.getPageSize(), new String[] {"create_time desc"});
    	for (User u : users) {
    		loadUserPermissions(u);
    	}
        ctx.setResult(users);
    	return ctx;
    }

    public void loadUserPermissions(User user) {
        List<UserPermission> perms = userPermissionDao.loadUserPermission(user.getId());
        user.setPermissions(perms);
    }

    public String transformPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
