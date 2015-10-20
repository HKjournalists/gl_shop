//
//  BrowseViewController.h
//  Glshop
//
//  Created by River on 14-12-25.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  找买找卖或我的供求详细页

#import "BaseViewController.h"
#import "PublicInfoModel.h"

typedef NS_ENUM(NSInteger, OrderStatus)
{
    /**
     *@brief 有效，已发布
     */
    OrderStatus_YES,
    /**
     *@brief 无效，审核不通过
     */
    OrderStatus_NO,
    /**
     *@brief 无效，已产生合同
     */
    OrderStatus_CLOSE,
    /**
     *@brief 无效，销售光，销售量为0
     */
    OrderStatus_ZERO,
    /**
     *@brief 无效，到期失效
     */
    OrderStatus_FAILURE,
    /**
     *@brief 无效，已取消
     */
    OrderStatus_CANCEL,
};

@interface BrowseViewController : BaseViewController

/**
 *@brief 订单id
 *@discussion 根据此id查询详细信息
 */
@property (nonatomic, copy) NSString *orderId;
@property (nonatomic, strong,readonly) PublicInfoModel *publicModel;
/**
 *@brief 订单状态
 *@discussion 过期、无效交易已完成、无效用户已取消的订单显示删除按钮
 */
@property (nonatomic, assign) OrderStatus orderStatus;

/**
 *@brief 0 代表不是 1代表是从我的供求进入
 *@discussion 从我的供求查看详细不显示交易询盘，显示取消发布、修改信息、删除等按钮
 */
@property (nonatomic, assign) NSInteger fromMySupply; //

@end
