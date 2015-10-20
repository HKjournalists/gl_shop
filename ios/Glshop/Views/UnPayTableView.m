//
//  UnPayTableView.m
//  Glshop
//
//  Created by River on 15-3-2.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UnPayTableView.h"
#import "UnPayTableViewCell.h"

@implementation UnPayTableView

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.dataArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UnPayTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"unPayCellIdentify"];
    if (!cell) {
        cell = [[[NSBundle mainBundle] loadNibNamed:@"UnPayTableViewCell" owner:self options:nil]lastObject];
    }
    cell.contractModel = self.dataArray[indexPath.section];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 138;
}

@end
