package com.glshop.net.logic.model;

/**
 * @Description : 列表滚动位置信息
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-9-25 下午4:06:18
 */
public class ScrollPositionInfo {

	/**
	 * 列表第一条可见数据位置
	 */
	private int firstVisiblePosition;

	/**
	 * Y轴偏移量
	 */
	private int topDistance;

	public int getFirstVisiblePosition() {
		return firstVisiblePosition;
	}

	public void setFirstVisiblePosition(int firstVisiblePosition) {
		this.firstVisiblePosition = firstVisiblePosition;
	}

	public int getTopDistance() {
		return topDistance;
	}

	public void setTopDistance(int topDistance) {
		this.topDistance = topDistance;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ScrollPosition[");
		sb.append("firstVisiblePosition=" + firstVisiblePosition);
		sb.append(", topDistance=" + topDistance);
		sb.append("]");
		return sb.toString();
	}

}
