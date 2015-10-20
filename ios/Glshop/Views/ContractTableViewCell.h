//
//  ContractTableViewCell.h
//  Glshop
//
//  Created by River on 15-1-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同待确认列表cell

#import <UIKit/UIKit.h>

@class ContractModel;
@interface ContractTableViewCell : UITableViewCell

@property (nonatomic, strong) ContractModel *contractModel;

@end
