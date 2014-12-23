//
//  RECustomViewCell.h
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RETableViewCell.h"
#import "RECustomViewItem.h"

@interface RECustomViewCell : RETableViewCell

@property (strong, readwrite, nonatomic) RECustomViewItem *item;

@end
