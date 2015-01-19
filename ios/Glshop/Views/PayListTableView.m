//
//  PayListTableView.m
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListTableView.h"
#import "PayListTableViewCell.h"


static NSString *payListIdentify = @"payListCellIdentify";

@implementation PayListTableView

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    PayListTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:payListIdentify];
    if (!cell) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"PayListTableViewCell" owner:self options:nil]lastObject];
    }
    cell.listModel = self.dataArray[indexPath.row];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 63;
}


@end