//
//  AssistViewController.m
//  Glshop
//
//  Created by River on 15-2-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AssistViewController.h"
#import "PlatformViewController.h"
#import "AboutViewController.h"
#import "ServicePhoneViewController.h"
#import "CommenQuestionViewController.h"
#import "UpdateModel.h"

static NSString *glAppNameAndVersion = @"长江电商v1.3";


@interface AssistViewController () <UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, strong) UpdateModel *updateModel;

@end

@implementation AssistViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.title = @"帮助";
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return kAppStoreVersion ? 4 : 5;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    
    NSArray *imgNames = @[@"help_icon_guanyu",@"help_icon_pingtaii",@"help_icon_wenti",@"help_icon_kefu",@"help_icon_gengxin",];
    NSArray *texts = @[@"关于我们",@"平台介绍",@"常见问题",@"客服电话",@"版本更新",];
    cell.imageView.image = [UIImage imageNamed:imgNames[indexPath.row]];
    cell.textLabel.text = texts[indexPath.row];
    cell.textLabel.textColor = C_BLACK;
    cell.textLabel.font = UFONT_16_B;
    
    return cell;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 80)];
    UIImage *image = [UIImage imageNamed:@"help_icon"];
    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-58/2, 10, 58, 44)];
    imgView.image = image;
    [view addSubview:imgView];
    
    UILabel *label = [UILabel labelWithTitle:glAppNameAndVersion];
    if (kAppStoreVersion) {
        label.text = @"长江电商v1.3";
    }
    label.frame = CGRectMake(SCREEN_WIDTH/2-100, imgView.vbottom, 200, 20);
    label.textColor = [UIColor blackColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont systemFontOfSize:14.f];
    label.textColor = RGB(100, 100, 100, 1);
    [view addSubview:label];
    
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 80;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.row == 0) {
        AboutViewController *vc = [[AboutViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else if (indexPath.row == 1) {
        PlatformViewController *vc = [[PlatformViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else if (indexPath.row == 2) {
        CommenQuestionViewController *vc = [[CommenQuestionViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else if (indexPath.row == 3) {
        ServicePhoneViewController *vc = [[ServicePhoneViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else if (indexPath.row == 4) {
        [self checkUpdate];
    }
}

- (void)checkUpdate {
    [self showHUD];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:@"3",@"devices",version, @"lastNo", nil];
    [self requestWithURL:bcheckVersion params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [self handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    _updateModel = [[UpdateModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    if (_updateModel.downurl) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"新版本" message:_updateModel.mark delegate:self cancelButtonTitle:@"暂不更新"  otherButtonTitles:@"我要更新", nil];
        [alert show];
    }else {
        [self showTip:@"当前已经是最新版本！"];
    }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:_updateModel.downurl]];
        exit(0);
    }
}

@end
