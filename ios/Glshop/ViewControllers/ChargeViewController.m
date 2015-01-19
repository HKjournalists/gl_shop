//
//  ChargeViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ChargeViewController.h"

@interface ChargeViewController ()<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation ChargeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight = 200;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.scrollEnabled = NO;
    [self.view addSubview:_tableView];
    
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, _tableView.vbottom+10, self.view.vwidth-20, 100) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"交易保证金账号由上海浦东发展银行托管，直接汇入上海浦东发展银行指定账号，账号受上海浦东发展银行进行资金监控"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [self.view addSubview:tipView];
}

#pragma mark - UITableView DataSource/Delegate

#define sectionHigh 5
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return sectionHigh;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    if (indexPath.section == 0) {
        cell.textLabel.text = @"充值金额";
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%ld元",(long)_chartAmount];
        cell.detailTextLabel.textColor = [UIColor redColor];
    }else {
        NSArray *titles = @[@"在线充值",@"银行转账",@"线下支付"];
        NSArray *imgNames = @[@"wallet_icon_huokuan",@"wallet_icon_yinhang",@"wallet_icon_xianxia"];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        cell.textLabel.text = titles[indexPath.row];
        cell.imageView.image = [UIImage imageNamed:imgNames[indexPath.row]];
    }
    
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    if (section == 1) {
        return @"支付方式";
    }
    return nil;
}

@end
