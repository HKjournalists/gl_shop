//
//  RECustomViewCell.m
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RECustomViewCell.h"
#import "RETableViewManager.h"

@implementation RECustomViewCell

- (void)cellDidLoad {
    [super cellDidLoad];
    
    [self.contentView addSubview:self.item.customView];
}

- (void)layoutSubviews
{
    [super layoutSubviews];

    [self.contentView addSubview:self.item.customView];
    
    if ([self.tableViewManager.delegate respondsToSelector:@selector(tableView:willLayoutCellSubviews:forRowAtIndexPath:)])
        [self.tableViewManager.delegate tableView:self.tableViewManager.tableView willLayoutCellSubviews:self forRowAtIndexPath:[self.tableViewManager.tableView indexPathForCell:self]];
}

+ (CGFloat)heightWithItem:(RECustomViewItem *)item tableViewManager:(RETableViewManager *)tableViewManager {
    return CGRectGetHeight(item.customView.bounds);
}

@end
