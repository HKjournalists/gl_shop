//
//  FilerAreasView.h
//  FilerView
//
//  Created by River on 15-3-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FilerTitleView.h"

@interface FilerAreasView : UIView <UITableViewDataSource,UITableViewDelegate,FilerSelectAllDelegate>
{
@private
    FilerTitleView *_filerTitleView;
    UITableView *_tableView1;
    UITableView *_tableView2;
    UITableView *_tableView3;
    NSInteger _table1SelectRow;
    NSInteger _table2SelectRow;
}

@property (nonatomic, strong) NSMutableArray *provinces;
@property (nonatomic, strong) NSMutableArray *citys;
@property (nonatomic, strong) NSMutableArray *regions;

- (void)resetFilerAddressData;

- (void)tableView:(UITableView *)tableView didSelectBoxRowAtIndexPath:(NSIndexPath *)indexPath boxSelect:(BOOL)isSelect;

@end
