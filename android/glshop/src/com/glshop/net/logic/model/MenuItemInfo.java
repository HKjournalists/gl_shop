package com.glshop.net.logic.model;

/**
 * @Description : 菜单项信息实体
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class MenuItemInfo {

	/**
	 * 菜单项文本
	 */
	public String menuText;

	/**
	 * 菜单项编码
	 */
	public String menuCode;

	/**
	 * 菜单项ID
	 */
	public String menuID;
	
	/**
	 * 菜单项排序
	 */
	public String orderNo;

	public MenuItemInfo() {

	}

	public MenuItemInfo(String text) {
		this(text, null, null);
	}

	public MenuItemInfo(String text, String code) {
		this(text, code, null);
	}

	public MenuItemInfo(String text, String code, String no) {
		this.menuText = text;
		this.menuCode = code;
		this.menuID = no;
	}
	
	public MenuItemInfo(String text, String code, String id, String no) {
		this.menuText = text;
		this.menuCode = code;
		this.menuID = id;
		this.orderNo = no;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (o instanceof MenuItemInfo) {
			MenuItemInfo other = (MenuItemInfo) o;
			if (this.menuText == null || other.menuText == null || this.menuCode == null || other.menuCode == null) {
				return false;
			} else {
				return this.menuText.equals(other.menuText) && this.menuCode.equals(other.menuCode);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MenuItemInfo[");
		sb.append("menuText=" + menuText);
		sb.append(", menuCode=" + menuCode);
		sb.append(", orderNo=" + orderNo);
		sb.append("]");
		return sb.toString();
	}

}
