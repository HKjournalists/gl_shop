//
//  BankListViewController.m
//  Glshop
//
//  Created by River on 15-1-13.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BankListViewController.h"
#import "SecondGatherViewController.h"

@interface BankListViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *banks;
@property (nonatomic, assign) NSInteger lastSeleced;

@end

@implementation BankListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"选择银行";
    _banks = @[@"中国农业银行",@"中国工商银行",@"中国建设银行",@"中国邮政储蓄银行",@"中国银行",@"招商银行",@"交通银行",@"浦发银行",@"中国光大银行",@"中信银行",@"平安银行",@"中国民生银行",@"华夏银行",@"兴业银行",@"上海银行",@"南京银行",@"江苏银行",];
    _lastSeleced = 0;
    SecondGatherViewController *vc = [self secgaVC];
    if ([_banks indexOfObject:vc.selectBank] != NSNotFound) {
        _lastSeleced = [_banks indexOfObject:vc.selectBank];
    }
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    [self.view addSubview:_tableView];
    
    [_tableView makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view);
        make.width.mas_equalTo(self.view);
        make.top.mas_equalTo(self.view);
        make.height.mas_equalTo(self.view);
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _banks.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *cellId = @"bankIdName";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellId];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
        cell.textLabel.font = [UIFont boldSystemFontOfSize:16.f];
    }
    cell.textLabel.text = _banks[indexPath.row];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
    imageView.image = [UIImage imageNamed:@"address_icon_select"];
    if (indexPath.row == _lastSeleced) {
        cell.accessoryView = imageView;
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];

    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
    imageView.image = [UIImage imageNamed:@"address_icon_select"];
    cell.accessoryView = imageView;
    if (indexPath.row != _lastSeleced) {
        UITableViewCell *prvCell = [tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:_lastSeleced inSection:0]];
        prvCell.accessoryView = nil;
    }
    _lastSeleced = indexPath.row;
    
    SecondGatherViewController *vc = [self secgaVC];
    vc.selectBank = _banks[indexPath.row];
    [vc.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:1 inSection:0]] withRowAnimation:UITableViewRowAnimationAutomatic];
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - Private 
- (SecondGatherViewController *)secgaVC {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[SecondGatherViewController class]]) {
            return (SecondGatherViewController *)vc;
        }
    }
    return nil;
}

@end
