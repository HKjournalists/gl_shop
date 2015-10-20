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
#import "AssistViewController.h"

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
    
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 90)];
    UIButton *logoutBtn = [UIFactory createBtn:YelloCommnBtnImgName bTitle:@"退出登录" bframe:CGRectZero];
    logoutBtn.frame = CGRectMake(10, 25, SCREEN_WIDTH-20, 40);
    logoutBtn.titleLabel.font = UFONT_16;
    [logoutBtn addTarget:self action:@selector(logoutAlert) forControlEvents:UIControlEventTouchUpInside];
    [logoutBtn setTitle:btntitle_logout forState:UIControlStateNormal];
    [bgView addSubview:logoutBtn];
    _tableView.tableFooterView = bgView;
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
    cell.textLabel.font = UFONT_16;
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    if (indexPath.section == 0) {
        NSArray *titles = @[@"联系人信息",@"交易地址",@"用户简介",@"用户照片",];
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
        UIView* customView = [[UIView alloc] initWithFrame:CGRectMake(8, 0.0, 300.0, 44.0)];
        //[customView setBackgroundColor:ColorWithHex(@"EDEDED")];
        
        UILabel * headerLabel = [[UILabel alloc] initWithFrame:CGRectZero];
        headerLabel.textColor = C_GRAY;
        headerLabel.font = [UIFont boldSystemFontOfSize:FONT_14];
        headerLabel.frame = CGRectMake(15, -5, 300.0, 44.0);
        
        headerLabel.text = @"用户信息";
        [customView addSubview:headerLabel];
        return customView;
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
    }else if (indexPath.section == 1) {
        SettingPasswordViewController *vc = [[SettingPasswordViewController alloc] init];
        UserInstance *user = [UserInstance sharedInstance];
        vc.phone = user.user.username;
        vc.title = @"修改密码";
        vc.operation = ModifySetPassword;
        [self.navigationController pushViewController:vc animated:YES];

    }else {
        AssistViewController *vc = [[AssistViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }
    
    
}

- (void)pushWithType:(Opention_type)type {
    CopyRightInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CopyRightInfoViewControllerId"];
    vc.opentionType = type;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)logout {
    
    __block typeof(self) this = self;
    [self showHUD];
    UserInstance *uIns = [UserInstance sharedInstance];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:uIns.user.username forKey:@"userName"];
    [self requestWithURL:bUserLogoutPath params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this logoutSuccess];
    } failedBlock:^(ASIHTTPRequest *request) {
        DLog(@"logout failed");
    }];
}

- (void)logoutSuccess {
    UserInstance *userInstance = [UserInstance sharedInstance];
    userInstance.user = nil;
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLogoutNotification object:nil];
    [self.navigationController popToRootViewControllerAnimated:NO];
}

#pragma mark - UIAction
- (void)logoutAlert {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"退出登录" message:@"您确定要退出系统吗？" delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alert.tag = 1001;
    [alert show];
}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 1001) {
        if (buttonIndex) { // 退出登录
            [self logout];
        }
    }
}

@end
