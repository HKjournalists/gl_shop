package com.appabc.cms.web;

import com.appabc.bean.bo.ClientBannerInfo;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.datas.cms.vo.Context;
import com.appabc.datas.cms.vo.Permission;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.UserPermission;
import com.appabc.datas.cms.service.UserService;
import com.appabc.datas.service.banner.IClientBannerService;
import com.appabc.datas.service.system.IUploadImagesService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	private IClientBannerService iClientBannerService;
	@Autowired
	private IUploadImagesService uploadImagesService;
	
	@RequestMapping("/accounts/{page}/")
	public String accounts(@PathVariable int page, ModelMap model) {
        Context<User> ctx = new Context<>();
        ctx.setStart(calculateStartRow(page, DEFAULT_PAGE_SIZE));
        ctx.setPageSize(DEFAULT_PAGE_SIZE);
        userService.querySystemUserList(ctx);
		model.addAttribute("users", ctx.getResult());
		calculatePagingData(model, "/manage/accounts/{}/", page, ctx.getTotalRows());
		return "manage/accounts";
	}

	@RequestMapping(value="/accounts/create/", method=RequestMethod.GET)
	public String createAccount(ModelMap model) {
		formModel(new User(), false, "create", model);
		return "manage/account_detail";
	}

	@RequestMapping(value="/accounts/create/", method=RequestMethod.POST)
	public String doCreateAccount(User user, Integer[] userPermissions, String mode, ModelMap model) {
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

	@RequestMapping(value="/accounts/detail/{userId}/edit/", method=RequestMethod.GET)
	public String editAccount(@PathVariable int userId, ModelMap model) {
		User user = userService.getUser(userId);
		formModel(user, false, "edit", model);
		return "manage/account_detail";
	}

	@RequestMapping(value="/accounts/detail/{userId}/edit/", method=RequestMethod.POST)
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
		return "redirect:/manage/accounts/detail/" + user.getId() + "/edit/";
	}

	@RequestMapping("/unresolved/")
	public String unresolved() {
		return "manage/unresolved";
	}
	
	@RequestMapping(value="/advertise/configure/{osType}/",method={RequestMethod.POST,RequestMethod.GET})
	public String configureAdvertisement (@PathVariable String osType,HttpServletRequest request,ModelMap model) {
		model.addAttribute("osType", osType);
		if(StringUtils.isEmpty(osType)){
			return null;
		}
		else {
			Map<String, Object> params = new HashMap<>();
		    params.put("btype", ClientType.valueOf(osType.toUpperCase()).getVal());	
		    List<ClientBannerInfo> clientBannerList=iClientBannerService.queryClientBannerInfoList(params);
	        model.addAttribute("clientBannerList", clientBannerList);
			return "manage/configureAdvertisement";
		}
	}
	
	@RequestMapping(value="/advertise/add/{osType}",method={RequestMethod.POST,RequestMethod.GET})
	public String addAdvertisement (@PathVariable String osType,HttpServletRequest request,ModelMap model) {		
		TClientBanner clientBanner=new TClientBanner();
		String auditor = getCurrentUser().getRealName();
		clientBanner.setCreater(auditor);
		int maxid=iClientBannerService.getMaxOrderno(ClientType.valueOf(osType.toUpperCase()));
		clientBanner.setOrderno(maxid+1);
		clientBanner.setCreatetime(Calendar.getInstance().getTime());
		clientBanner.setBtype(ClientType.valueOf(osType.toUpperCase()));
		iClientBannerService.add(clientBanner);
		return "redirect:/manage/advertise/configure/"+osType+"/";
	}
	
	@RequestMapping(value="/advertise/update/{osType}",method={RequestMethod.POST,RequestMethod.GET})
	public String updateAdvertisement (@PathVariable String osType,TClientBanner clientBanner) {
		clientBanner.setUpdater(getCurrentUser().getRealName());
		clientBanner.setUpdatetime(Calendar.getInstance().getTime());
		iClientBannerService.modify(clientBanner);		
		return "redirect:/manage/advertise/configure/"+osType+"/";
	}
	
	@RequestMapping(value="/advertise/delete/{osType}",method={RequestMethod.POST,RequestMethod.GET})
	public String deleteAdvertisement (@PathVariable String osType,TClientBanner clientBanner) {
		TUploadImages uploadImages=uploadImagesService.query(clientBanner.getSortimgid());
		uploadImagesService.delete(uploadImages);
		iClientBannerService.delete(clientBanner);		
		return "redirect:/manage/advertise/configure/"+osType+"/";
	}
	
	@RequestMapping(value="/advertise/updateFile/{osType}",method={RequestMethod.POST,RequestMethod.GET})
	public String updateFile (@PathVariable String osType,String newsortimgid,String newthumedid,TClientBanner clientBanner) {
		TUploadImages uploadImages=uploadImagesService.query(clientBanner.getSortimgid());
		uploadImagesService.delete(uploadImages);
		clientBanner.setUpdater(getCurrentUser().getRealName());
		clientBanner.setUpdatetime(Calendar.getInstance().getTime());
		clientBanner.setSortimgid(newsortimgid);
		iClientBannerService.modify(clientBanner);	
		return "redirect:/manage/advertise/configure/"+osType+"/";
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
