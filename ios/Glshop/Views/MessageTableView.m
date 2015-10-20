//
//  MessageTableView.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MessageTableView.h"
#import "MessageTableViewCell.h"

static NSString *messageCellIdentify = @"messageCellIdentify";

@implementation MessageTableView

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    MessageTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:messageCellIdentify];
    if (!cell) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"MessageTableViewCell" owner:self options:nil]lastObject];
    }
    cell.message = self.dataArray[indexPath.row];
    DebugLog(@"build:%ld",(long)indexPath.row);
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 58;
}

@end
