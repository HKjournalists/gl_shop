package com.glshop.net.logic.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.glshop.net.common.GlobalConstants.DataReqType;
import com.glshop.platform.api.buy.data.model.BuySummaryInfoModel;
import com.glshop.platform.api.buy.data.model.ForecastPriceModel;
import com.glshop.platform.api.buy.data.model.MyBuySummaryInfoModel;
import com.glshop.platform.api.buy.data.model.TodayPriceModel;
import com.glshop.platform.api.contract.data.model.ContractSummaryInfoModel;
import com.glshop.platform.api.contract.data.model.EvaluationInfoModel;
import com.glshop.platform.api.contract.data.model.ToPayContractInfoModel;
import com.glshop.platform.api.message.data.model.MessageInfoModel;
import com.glshop.platform.api.purse.data.model.DealSummaryInfoModel;
import com.glshop.platform.api.purse.data.model.PayeeInfoModel;
import com.glshop.platform.utils.BeanUtils;

/**
 * @Description : 全局数据中心
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 深圳市国立数码动画有限公司
 * @author      : 叶跃丰
 * @version     : 1.0
 * Create Date  : 2014-7-30 上午11:26:26
 */
public final class DataCenter {

	private static final String TAG = "DataCenter";

	private static DataCenter mInstance;

	/** 全局数据对象 */
	private Map<Integer, List> mDataMap = new HashMap<Integer, List>();

	/** （黄砂）今日价格数据列表 */
	private List<TodayPriceModel> mSandTodayPriceList = new ArrayList<TodayPriceModel>();

	/** （石子）今日价格数据列表 */
	private List<TodayPriceModel> mStoneTodayPriceList = new ArrayList<TodayPriceModel>();

	/** （黄砂）未来价格预测数据列表 */
	private List<ForecastPriceModel> mSandForecastPriceList = new ArrayList<ForecastPriceModel>();

	/** （石子）未来价格预测数据列表 */
	private List<ForecastPriceModel> mStoneForecastPriceList = new ArrayList<ForecastPriceModel>();

	/** （求购）找买找卖数据列表 */
	private List<BuySummaryInfoModel> mFindBuyerList = new ArrayList<BuySummaryInfoModel>();

	/** （出售）找买找卖数据列表  */
	private List<BuySummaryInfoModel> mFindSellerList = new ArrayList<BuySummaryInfoModel>();

	/** 我的供求数据列表  */
	private List<MyBuySummaryInfoModel> mMyBuyList = new ArrayList<MyBuySummaryInfoModel>();

	/** 待确认的合同列表 */
	private List<ContractSummaryInfoModel> mUfmContractList = new ArrayList<ContractSummaryInfoModel>();

	/** 进行中的合同列表 */
	private List<ContractSummaryInfoModel> mOngoingContractList = new ArrayList<ContractSummaryInfoModel>();

	/** 已结束的合同列表 */
	private List<ContractSummaryInfoModel> mEndedContractList = new ArrayList<ContractSummaryInfoModel>();

	/** 待付款的合同列表 */
	private List<ToPayContractInfoModel> mToPayContractList = new ArrayList<ToPayContractInfoModel>();

	/** 消息列表 */
	private List<MessageInfoModel> mMessageList = new ArrayList<MessageInfoModel>();

	/** 钱包流水列表  */
	private List<DealSummaryInfoModel> mDealList = new ArrayList<DealSummaryInfoModel>();

	/** 收款人选择列表  */
	private List<PayeeInfoModel> mPayeeSelectList = new ArrayList<PayeeInfoModel>();

	/** 收款人管理列表  */
	private List<PayeeInfoModel> mPayeeMgrList = new ArrayList<PayeeInfoModel>();

	/** 企业评价列表  */
	private List<EvaluationInfoModel> mEvaluationList = new ArrayList<EvaluationInfoModel>();

	/**
	 * 数据列表类型定义
	 */
	public interface DataType {

		/** （黄砂）今日价格数据列表 */
		public static final int SAND_TODAY_PRICE_LIST = 0;

		/** （石子）今日价格数据列表 */
		public static final int STONE_TODAY_PRICE_LIST = 1;

		/** （黄砂）未来价格预测数据列表  */
		public static final int SAND_FORECAST_PRICE_LIST = 2;

		/** （石子）未来价格预测数据列表 */
		public static final int STONE_FORECAST_PRICE_LIST = 3;

		/** （求购）找买找卖数据列表 */
		public static final int FINDBUY_BUYER_LIST = 4;

		/** （出售）找买找卖数据列表 */
		public static final int FINDBUY_SELLER_LIST = 5;

		/** 我的供求数据列表 */
		public static final int MY_BUY_LIST = 6;

		/** 待确认的合同列表 */
		public static final int UFM_CONTRACT_LIST = 7;

		/** 进行中的合同列表 */
		public static final int ONGOING_CONTRACT_LIST = 8;

		/** 已结束的合同列表 */
		public static final int ENDED_CONTRACT_LIST = 9;

		/** 待付款的合同列表 */
		public static final int TO_PAY_CONTRACT_LIST = 10;

		/** 消息列表 */
		public static final int MESSAGE_LIST = 11;

		/** 钱包流水列表 */
		public static final int PURSE_DEAL_LIST = 12;

		/** 收款人选择列表 */
		public static final int PAYEE_SELECT_LIST = 13;

		/** 收款人管理列表 */
		public static final int PAYEE_MANAGER_LIST = 14;

		/** 企业评价列表 */
		public static final int EVALUATION_LIST = 15;

	}

	private DataCenter() {
		mDataMap.put(DataType.SAND_TODAY_PRICE_LIST, mSandTodayPriceList);
		mDataMap.put(DataType.STONE_TODAY_PRICE_LIST, mStoneTodayPriceList);
		mDataMap.put(DataType.SAND_FORECAST_PRICE_LIST, mSandForecastPriceList);
		mDataMap.put(DataType.STONE_FORECAST_PRICE_LIST, mStoneForecastPriceList);
		mDataMap.put(DataType.FINDBUY_BUYER_LIST, mFindBuyerList);
		mDataMap.put(DataType.FINDBUY_SELLER_LIST, mFindSellerList);
		mDataMap.put(DataType.MY_BUY_LIST, mMyBuyList);
		mDataMap.put(DataType.UFM_CONTRACT_LIST, mUfmContractList);
		mDataMap.put(DataType.ONGOING_CONTRACT_LIST, mOngoingContractList);
		mDataMap.put(DataType.ENDED_CONTRACT_LIST, mEndedContractList);
		mDataMap.put(DataType.TO_PAY_CONTRACT_LIST, mToPayContractList);
		mDataMap.put(DataType.MESSAGE_LIST, mMessageList);
		mDataMap.put(DataType.PURSE_DEAL_LIST, mDealList);
		mDataMap.put(DataType.PAYEE_SELECT_LIST, mPayeeSelectList);
		mDataMap.put(DataType.PAYEE_MANAGER_LIST, mPayeeMgrList);
		mDataMap.put(DataType.EVALUATION_LIST, mEvaluationList);
	}

	public static synchronized DataCenter getInstance() {
		if (mInstance == null) {
			mInstance = new DataCenter();
		}
		return mInstance;
	}

	/**
	 * 添加数据
	 * @param data
	 * @param dataType
	 * @param reqType
	 */
	public void addData(List data, int dataType, DataReqType reqType) {
		List list = getData(dataType);
		if (list != null) {
			switch (reqType) {
			case INIT:
				list.clear();
				if (BeanUtils.isNotEmpty(data)) {
					list.addAll(data);
				}
				break;
			case MORE:
				if (BeanUtils.isNotEmpty(data)) {
					list.addAll(data);
				}
				break;
			case REFRESH:
				if (isCoverData(dataType, reqType)) {
					list.clear();
				}
				if (BeanUtils.isNotEmpty(data)) {
					if (isCoverData(dataType, reqType)) {
						list.addAll(data);
					} else {
						for (Object o : data) {
							if (!list.contains(o)) {
								list.add(0, o);
							} else {
								int index = list.indexOf(o);
								list.set(index, o);
							}
						}
					}
				}
				break;
			}
		}
	}

	private boolean isCoverData(int dataType, DataReqType reqType) {
		boolean isCoverModel = false;
		if (reqType == DataReqType.REFRESH) {
			if (dataType == DataType.FINDBUY_BUYER_LIST || dataType == DataType.FINDBUY_SELLER_LIST || dataType == DataType.MY_BUY_LIST || dataType == DataType.UFM_CONTRACT_LIST
					|| dataType == DataType.ONGOING_CONTRACT_LIST || dataType == DataType.ENDED_CONTRACT_LIST) {
				isCoverModel = true;
			}
		}
		return isCoverModel;
	}

	/**
	 * 获取数据
	 * @param dataType
	 * @return
	 */
	public List getData(int dataType) {
		return mDataMap.get(dataType);
	}

	/**
	 * 重置内存中DataType类型数据
	 */
	public synchronized void cleanData(int... typeList) {
		if (BeanUtils.isNotEmpty(typeList)) {
			for (int type : typeList) {
				List data = getData(type);
				if (BeanUtils.isNotEmpty(data)) {
					data.clear();
				}
			}
		}
	}

	/**
	 * 重置内存中数据
	 */
	public synchronized void clean() {
		Iterator<Map.Entry<Integer, List>> it = (Iterator<Map.Entry<Integer, List>>) mDataMap.entrySet();
		while (it.hasNext()) {
			Entry entry = it.next();
			((List) entry.getValue()).clear();
		}
	}

}
