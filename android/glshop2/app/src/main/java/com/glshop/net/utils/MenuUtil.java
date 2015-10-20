package com.glshop.net.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.glshop.net.logic.model.MenuItemInfo;
import com.glshop.platform.api.syscfg.data.model.AreaInfoModel;
import com.glshop.platform.utils.StringUtils;

/**
 * 菜单工具类
 */
public class MenuUtil {
	
	/**
	 * 创建菜单列表
	 * @param menus
	 * @return
	 */
	public static List<MenuItemInfo> makeMenuAList(List<AreaInfoModel> areaList) {
		List<MenuItemInfo> itemList = new ArrayList<MenuItemInfo>();
		for (AreaInfoModel menu : areaList) {
			MenuItemInfo item = new MenuItemInfo(menu.name);
			itemList.add(item);
		}
		return itemList;
	}

	/**
	 * 创建菜单列表
	 * @param menus
	 * @return
	 */
	public static List<MenuItemInfo> makeMenuList(List<String> menus) {
		List<MenuItemInfo> itemList = new ArrayList<MenuItemInfo>();
		for (String menu : menus) {
			MenuItemInfo item = new MenuItemInfo(menu);
			itemList.add(item);
		}
		return itemList;
	}

	public static void sortMenuList(List<MenuItemInfo> menus) {
		Collections.sort(menus, new Comparator<MenuItemInfo>() {

			@Override
			public int compare(MenuItemInfo info1, MenuItemInfo info2) {
				if (StringUtils.isNotEmpty(info1.orderNo) && StringUtils.isNotEmpty(info2.orderNo)) {
					if (StringUtils.isDigital(info1.orderNo) && StringUtils.isDigital(info2.orderNo)) {
						int lhs = Integer.parseInt(info1.orderNo);
						int rhs = Integer.parseInt(info2.orderNo);
						return lhs - rhs;
					} else {
						return info1.orderNo.compareTo(info2.orderNo);
					}
				} else {
					return 0;
				}
			}

		});
	}

}
