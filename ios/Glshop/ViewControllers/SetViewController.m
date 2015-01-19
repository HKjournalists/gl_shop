//
//  SetViewController.m
//  Glshop
//
//  Created by River on 15-1-4.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "SetViewController.h"
#import "CopyRightInfoViewController.h"
#import "MangerAddressViewController.h"
#import "SettingPasswordViewController.h"

@interface SetViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation SetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 4 : 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    if (indexPath.section == 0) {
        NSArray *titles = @[@"联系人信息",@"交易地址",@"企业简介",@"企业照片",];
        cell.textLabel.text = titles[indexPath.row];
    }else if (indexPath.section == 1) {
        cell.textLabel.text = @"修改密码";
    }else {
        cell.textLabel.text = @"帮助";
    }
    
    return cell;
}


#define sectionHigh 5
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{

    return sectionHigh;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (section == 0) {
        return 30;
    }
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    if (section == 0) {
        return nil;
    }
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
    
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    if (section == 0) {
        return @"企业信息";
    }
    return nil;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            [self pushWithType:Add_Contact];
        }else if (indexPath.row == 1) {
            MangerAddressViewController *vc = [[MangerAddressViewController alloc] init];
            [self.navigationController pushViewController:vc animated:YES];
        }else if (indexPath.row == 2) {
            [self pushWithType:Fill_Brief];
        }else if (indexPath.row == 3) {
            [self pushWithType:Upload_Image];
        }
    }
    
    if (indexPath.section == 1) {
 
        SettingPasswordViewController *vc = [[SettingPasswordViewController alloc] init];
        UserInstance *user = [UserInstance sharedInstance];
        vc.phone = user.user.username;
        vc.title = @"修改密码";
        vc.operation = ModifySetPassword;
        [self.navigationController pushViewController:vc animated:YES];

    }
}

- (void)pushWithType:(Opention_type)type {
    CopyRightInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CopyRightInfoViewControllerId"];
    vc.opentionType = type;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
