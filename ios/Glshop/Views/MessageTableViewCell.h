//
//  MessageTableViewCell.h
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MessageModel;
@interface MessageTableViewCell : UITableViewCell

@property (nonatomic, strong) MessageModel *message;

@end
