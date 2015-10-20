//
//  ForcastTableView.m
//  Glshop
//
//  Created by River on 14-11-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ForcastTableView.h"
#import "WeekForcastTableViewCell.h"

static NSString *cellIdentify = @"forcastTableCell";

@implementation ForcastTableView

#pragma mark -
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.dataArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    NSDictionary *dataDic = self.dataArray[section];
    return [dataDic.allValues.firstObject count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    WeekForcastTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (!cell) {
        cell = [[WeekForcastTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
    }
    NSDictionary *dic = self.dataArray[indexPath.section];
    NSArray *dataArray = dic.allValues.firstObject;
    cell.weekModel = dataArray[indexPath.row];
    
//    if (indexPath.row%2 == 1) {
//        cell.backgroundColor = RGB(247, 247, 247, 1);
//    }else {
//        cell.backgroundColor = [UIColor whiteColor];
//    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 30;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView* customView = [[UIView alloc] initWithFrame:CGRectMake(8, 0.0, 300.0, 30.0)];
    [customView setBackgroundColor:ColorWithHex(@"EDEDED")];
    
    UILabel * headerLabel = [[UILabel alloc] initWithFrame:CGRectZero];
    headerLabel.textColor = ColorWithHex(@"333333");
    headerLabel.font = [UIFont boldSystemFontOfSize:FONT_16];
    headerLabel.frame = CGRectMake(8, 0, 300.0, 30.0);
    
    NSDictionary *dic = self.dataArray[section];
    NSString *names = dic.allKeys.firstObject;
    headerLabel.text = names;
    [customView addSubview:headerLabel];
    return customView;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    NSDictionary *dic = self.dataArray[section];
    return dic.allKeys.firstObject;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsZero];
    }
    
    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsZero];
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if ([self.eventDelegate respondsToSelector:@selector(tableView:didSelectRowAtIndexPath:)]) {
        [self.eventDelegate tableView:self didSelectRowAtIndexPath:indexPath];
    }
}

@end
