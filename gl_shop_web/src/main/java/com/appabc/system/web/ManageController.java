package com.appabc.system.web;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appabc.system.Permission;
import com.appabc.system.User;
import com.appabc.system.UserPermission;
import com.appabc.system.UserService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : zouxifeng
 * @version     : 1.0
 * Create Date  : Oct 24, 2014 11:33:56 AM
 */
@Controller
@RequestMapping("/manage")
public class ManageController extends AbstractListBaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/accounts/{page}/")
	public String accounts(@PathVariable int page, ModelMap model) {
		int start = (page - 1) * DEFAULT_PAGE_SIZE;
		model.addAttribute("users", userService.querySystemUserList(null, start, DEFAULT_PAGE_SIZE));
		int rowCount = userService.countUser(null);
		calculatePagingData(model, "/manage/accounts/{}/", page, rowCount);
		return "manage/accounts";
	}
	
	@RequestMapping(value="/accounts/create/", method=RequestMethod.GET)
	public String createAccount(ModelMap model) {
		formModel(new User(), false, "create", model);
		return "manage/account_detail";
	}
	
	@RequestMapping(value="/accounts/create", method=RequestMethod.POST)
	public String createAccount(User user, Integer[] userPermissions, String mode, ModelMap model) {
		model.addAttribute("type", "create");
		List<UserPermission> permissions = loadPermissions(userPermissions);
		
		user.setPermissions(permissions);
		user.setCreateTime(new Date());
		boolean result = userService.createUser(user);
		if (!result) {
			formModel(user, true, "create", model);
            return "manage/account_detail";
		}
		
		if ("save".equals(mode)) {
			return "redirect:/manage/accounts/1/";
		} else {
            return "redirect:/manage/accounts/create/";
		}
		
	}


	@RequestMapping(value="/accounts/{userId}/edit", method=RequestMethod.GET)
	public String editAccount(@PathVariable int userId, ModelMap model) {
		User user = userService.getUser(userId); 
		formModel(user, false, "edit", model);
		return "manage/account_detail";
	}
	

	@RequestMapping(value="/accounts/{userId}/edit", method=RequestMethod.POST)
	public String editAccount(@PathVariable int userId, User formUser, Integer[] userPermissions, ModelMap model) {
		User user = userService.getUser(userId);
		user.setRealName(formUser.getRealName());
		List<Permission> perms = new LinkedList<Permission>();
		if (userPermissions != null) {
			for (Integer perm : userPermissions) {
                perms.add(Permission.valueOf(perm));
			}
		}
		userService.updateUser(user, perms);
		formModel(user, false, "edit", model);
		return "manage/account_detail";
	}

	@RequestMapping(value="/accounts/")
	public String saveAccount() {
		return null;
	}

	@RequestMapping("/unresolved/")
	public String unresolved() {
		return "manage/unresolved";
	}
	
	private void formModel(User user, boolean error, String type, ModelMap model) {
		model.addAttribute("user", user);
		Set<Integer> permSet = new HashSet<Integer>();
		List<UserPermission> permissions = user.getPermissions();
		if (permissions != null) {
            for (UserPermission up : user.getPermissions()) {
            	permSet.add(up.getPermission().getId());
            }
		}
		model.addAttribute("permissions", permSet);
		model.addAttribute("error", error);
		model.addAttribute("type", type);
	}
	
	private List<UserPermission> loadPermissions(Integer[] userPermissions) {
		List<UserPermission> permissions = new LinkedList<UserPermission>();
		if (userPermissions != null) {
			for (Integer p : userPermissions) {
				UserPermission up = new UserPermission();
				up.setPermission(p);
				permissions.add(up);
			}
		}
		return permissions;
	}
	
}
