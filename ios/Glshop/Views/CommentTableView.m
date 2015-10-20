//
//  CommentTableView.m
//  Glshop
//
//  Created by River on 15-1-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CommentTableView.h"
#import "CommentModel.h"
#import "TPFloatRatingView.h"

@implementation CommentTableView

static NSString *cellIdentify1 = @"commentCellIdentify1";
static NSString *cellIdentify2 = @"commentCellIdentify2";
static NSString *cellIdentify3 = @"commentCellIdentify3";
static NSString *cellIdentify4 = @"commentCellIdentify4";

#pragma mark -
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.dataArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 4;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    CommentModel *model = self.dataArray[indexPath.section];
    if (indexPath.row == 3) {
        float height = [self heightOfLabelSize:model.evaluation];
        return height+55;
    }
    return 44;
}

/**
 *@brief 计算文本高度
 */
- (float)heightOfLabelSize:(NSString *)text {
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, SCREEN_WIDTH-30, 0)];
    label.numberOfLines = 0;
    label.font = [UIFont systemFontOfSize:14.f];
    label.text = text;
    [label sizeToFit];
    return label.vheight;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell;
    
    CommentModel *model = self.dataArray[indexPath.section];
    
    if (indexPath.row == 0) {
        cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify1];
    }else if (indexPath.row == 1) {
        cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify2];
    }else if (indexPath.row == 2) {
        cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify3];
    }else if (indexPath.row == 3) {
        cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify4];
    }
    
    if (!cell) {
        if (indexPath.row == 0) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentify1];
            cell.imageView.image = [UIImage imageNamed:@"appraise_niming"];
            UILabel *nameLab = [UILabel labelWithTitle:@""];
            nameLab.frame = CGRectMake(50, 2, 280, 20);
            nameLab.font = [UIFont systemFontOfSize:FONT_14];
            nameLab.textColor = C_BLACK;
            nameLab.tag = 100;
            [cell.contentView addSubview:nameLab];
            
            UILabel *timeLab = [UILabel labelWithTitle:@"详细时间:2014-09-08"];
            timeLab.tag = 101;
            timeLab.frame = CGRectMake(nameLab.vleft, nameLab.vbottom, 260, 20);
            timeLab.font = [UIFont systemFontOfSize:FONT_12];
            timeLab.textColor = C_GRAY;
            [cell.contentView addSubview:timeLab];
        }else if (indexPath.row == 1) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentify2];
            cell.imageView.image = [UIImage imageNamed:@"appraise_chengxin"];
            cell.textLabel.font = UFONT_16;
            cell.textLabel.textColor = C_BLACK;
            cell.textLabel.text = evaBusinessSatified;
            
            TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.vwidth-130, 8.5, 120, 30)];
            ratingView.tag = 102;
            ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
            ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
            [cell.contentView addSubview:ratingView];
        }else if (indexPath.row == 2) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentify3];
            cell.imageView.image = [UIImage imageNamed:@"appraise_mangyi"];
            cell.textLabel.text = evaBusinessInterd;
            cell.textLabel.font = UFONT_16;
            cell.textLabel.textColor = C_BLACK;
            
            TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.vwidth-130, 8.5, 120, 30)];
            ratingView.tag = 103;
            ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
            ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
            [cell.contentView addSubview:ratingView];
        }else if (indexPath.row == 3) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentify4];
            cell.imageView.image = nil;
            UILabel *tipLabel = [UILabel labelWithTitle:@"详细评价"];
            tipLabel.frame = CGRectMake(15, 10, 120, 20);
            tipLabel.font = [UIFont systemFontOfSize:FONT_14];
            tipLabel.textColor = RGB(100, 100, 100, 1);
            [cell.contentView addSubview:tipLabel];
            
            UILabel *detailLabel = [UILabel labelWithTitle:nil];
            detailLabel.font = [UIFont systemFontOfSize:14.f];
            detailLabel.tag = 200;
            detailLabel.frame = CGRectMake(tipLabel.vleft, tipLabel.vbottom+5, self.vwidth-30, 0);
            [cell.contentView addSubview:detailLabel];
        }
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    UILabel *nameLa = (UILabel *)[cell viewWithTag:100];
    nameLa.text = model.cname;
    
    UILabel *timeLa = (UILabel *)[cell viewWithTag:101];
    timeLa.text = [NSString stringWithFormat:@"详细时间:%@",model.cratedate];
    
    TPFloatRatingView *rating1 = (TPFloatRatingView *)[cell viewWithTag:102];
    TPFloatRatingView *rating2 = (TPFloatRatingView *)[cell viewWithTag:103];
    rating1.rating = [model.satisfaction floatValue];
    rating2.rating = [model.credit floatValue];
    
    UILabel *evaLab = (UILabel *)[cell viewWithTag:200];
    evaLab.text = model.evaluation ? model.evaluation : @"暂无详细评价";
    evaLab.numberOfLines = 0;
    evaLab.frame = CGRectMake(15, 35, SCREEN_WIDTH-30, 0);
    evaLab.textColor = C_BLACK;
    evaLab.font = UFONT_14;
    [evaLab sizeToFit];


    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if ([self.eventDelegate respondsToSelector:@selector(tableView:didSelectRowAtIndexPath:)]) {
        [self.eventDelegate tableView:self didSelectRowAtIndexPath:indexPath];
    }
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsMake(0, 15, 0, 0)];
    }
    
    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsMake(0, 15, 0, 0)];
    }
}

#pragma mark - UIAction

@end
