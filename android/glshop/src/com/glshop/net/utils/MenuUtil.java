package com.glshop.net.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.glshop.net.logic.model.MenuItemInfo;

/**
 * 菜单工具类
 */
public class MenuUtil {

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
				return info1.orderNo.compareTo(info2.orderNo);
			}

		});
	}

}
