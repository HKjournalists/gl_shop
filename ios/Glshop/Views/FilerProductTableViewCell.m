//
//  FilerProductTableViewCell.m
//  Glshop
//
//  Created by River on 15-3-17.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerProductTableViewCell.h"
#import "FilerProductView.h"

@implementation FilerProductTableViewCell

- (void)awakeFromNib {
    // Initialization code
    _box.boxImage = [UIImage imageNamed:@"check_unselected"];
    _box.selectImage = [UIImage imageNamed:@"dressing-by-screening_gouxuan"];
    __block typeof(self) this = self;
    _box.tapBlock = ^(BOOL selected) {
        if (selected) {
            [this handleSelect:YES];
        }else {
            [this handleSelect:NO];
        }
    };
}

- (void)handleSelect:(BOOL)isSelect {
    UITableView *tableView = [self findSuperViewOfClass:[UITableView class]];
    NSIndexPath *path = [tableView indexPathForCell:self];
    [tableView selectRowAtIndexPath:path animated:NO scrollPosition:UITableViewScrollPositionNone];
    FilerProductView *superView = [self findSuperViewOfClass:[FilerProductView class]];
    [superView tableView:tableView didSelectBoxRowAtIndexPath:path boxSelect:isSelect];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
