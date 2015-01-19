//
//  ProfileViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ProfileViewController.h"
#import "SetViewController.h"
#import "UIImageView+WebCache.h"
#import "AddressImgModel.h"
#import "AuthViewController.h"
#import "AuthDetailViewController.h"

@interface ProfileViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;

@end

@implementation ProfileViewController

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    [self requestNet];
    

}

- (void)initDatas {
    _dataSource = @[@3,@4,@5,@2,@2,];
    self.title = @"我的资料";
    self.edgesForExtendedLayout = UIRectEdgeAll;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushCompanyInfoNotification object:nil];
    
    UIButton *setBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    setBtn.frame = CGRectMake(0, 0, 60, 40);
    [setBtn setTitle:@"设置" forState:UIControlStateNormal];
    setBtn.titleEdgeInsets = UIEdgeInsetsMake(0, 5, 0, 0);
    [setBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    [setBtn setImage:[UIImage imageNamed:@"information_icon_set"] forState:UIControlStateNormal];
    [setBtn addTarget:self action:@selector(pushToSetVC) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *bItem = [[UIBarButtonItem alloc] initWithCustomView:setBtn];
    self.navigationItem.rightBarButtonItem = bItem;
}

- (void)loadSubViews {

//    [self loadHeaderView];
    [self loadTableView];
    
    [self hideViewsWhenNoData];
}

- (void)hideViewsWhenNoData {
    for (UIView *view in self.view.subviews) {
        view.hidden = YES;
    }
}

- (void)showViewsWhenDataComing {
    for (UIView *view in self.view.subviews) {
        view.hidden = NO;
    }
}

- (UIView *)loadHeaderView {
    UIImageView *header = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 400/2)];
    header.userInteractionEnabled = YES;
    header.image = [UIImage imageNamed:@"information_beijing"];
//    [self.view addSubview:header];
    
    UIImageView *logoImage = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-33, 70, 66, 66)];
    logoImage.image = [UIImage imageNamed:@"information_touxiang"];
    [header addSubview:logoImage];
    
    UILabel *titleLabel = [UILabel labelWithTitle:@"腾讯QQ"];
    titleLabel.textColor = [UIColor whiteColor];
    titleLabel.frame = CGRectMake(SCREEN_WIDTH/2-60, logoImage.vbottom, 120, 25);
    titleLabel.textAlignment = NSTextAlignmentCenter;
    [header addSubview:titleLabel];
    
    UIImageView *bottomBar = [[UIImageView alloc] initWithFrame:CGRectMake(0, header.vbottom-40, SCREEN_WIDTH, 40)];
    bottomBar.userInteractionEnabled = YES;
    UIImage *bImage = [UIImage imageNamed:@"information_xia"];
    bImage = [bImage resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    bottomBar.image = bImage;
    [header addSubview:bottomBar];
    UserInstance *user = [UserInstance sharedInstance];
    NSString *userName = user.user.username;
    UILabel *userLabel = [UILabel labelWithTitle:[NSString stringWithFormat:@"登录账号:%@",userName]];
    userLabel.frame = CGRectMake(5, 0, 180, bottomBar.vheight);
    userLabel.textColor = [UIColor whiteColor];
    [bottomBar addSubview:userLabel];
    
    UIButton *logoutBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    logoutBtn.frame = CGRectMake(bottomBar.vright-90, 0, 80, 40);
    logoutBtn.titleLabel.font = [UIFont systemFontOfSize:16.f];
    [logoutBtn addTarget:self action:@selector(logout) forControlEvents:UIControlEventTouchUpInside];
    [logoutBtn setTitle:@"退出登录" forState:UIControlStateNormal];
    [bottomBar addSubview:logoutBtn];
    
    return header;
}

- (void)loadTableView {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    
    _tableView.tableHeaderView = [self loadHeaderView];
    
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    CGRect rect = CGRectMake(0.0f, 0.0f, 320, 64.0f);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    CGContextSetFillColorWithColor(context, [[UIColor clearColor] CGColor]);
    CGContextFillRect(context, rect);
    
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    UIImage *bImg = [UIImage imageNamed:@"information_shang"];
    bImg = [bImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    
    UINavigationController *nav = self.navigationController;
    [nav.navigationBar setBackgroundImage:bImg forBarMetrics:UIBarMetricsDefault];
    [nav.navigationBar setShadowImage:image];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    UINavigationController *nav = self.navigationController;
    [nav.navigationBar setBackgroundImage:nil forBarMetrics:UIBarMetricsDefault];
    [nav.navigationBar setShadowImage:nil];
    
    [[UINavigationBar appearance] setBarTintColor:[Utilits colorWithHexString:@"#FF6700"]];
    [[UINavigationBar appearance] setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                                          [UIColor whiteColor], NSForegroundColorAttributeName,
                                                          [UIFont fontWithName:@"AmericanTypewriter-Bold" size:20], NSFontAttributeName,
                                                          nil]];
    [[UINavigationBar appearance] setTintColor:[UIColor whiteColor]];
}

- (void)requestNet {
    
    __block typeof(self) this = self;
    [self.view showWithTip:nil];
    [self requestWithURL:bCompanyAuthInfoPath params:nil HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *req){
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey];
    if (datas.count > 0) {
        NSDictionary *dic = datas.firstObject;
        self.cModel = [[CopyRightModel alloc] initWithDataDic:dic];
    }
    [_tableView reloadData];
}

#pragma mark - Setter
- (void)setCModel:(CopyRightModel *)cModel {
    _cModel = cModel;
    if (!_cModel.address.length) {
        _dataSource = @[@3,@4,@2,@2,@2,];
    }else {
        _dataSource = @[@3,@4,@5,@2,@2,];
    }
    
    if ([_cModel.ctype[DataValueKey] integerValue] == 2) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_dataSource];
        NSRange range = NSMakeRange(3, 2);
        [temp removeObjectsAtIndexes:[NSIndexSet indexSetWithIndexesInRange:range]];
        _dataSource = [NSArray arrayWithArray:temp];
    }
}

#pragma mark - Private
/**
 *@brief 计算文本高度
 */
- (float)heightOfLabelSize:(NSString *)text {
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 10, self.tableView.vwidth-30, 0)];
    label.numberOfLines = 0;
    label.text = text;
    [label sizeToFit];
    return label.vheight+35;
}

#pragma mark - UIActions
- (void)pushToSetVC {
    SetViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"SetViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)logout {
    
//    __block typeof(self) this = self;
//    [self showHUD:@"正在退出..." isDim:NO Yoffset:0];
//    [self requestWithURL:bUserLogoutPath params:nil HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
//        kASIResultLog;
//        [this logoutSuccess];
//    } failedBlock:^(ASIHTTPRequest *request) {
//        DLog(@"logout failed");
//    }];
    
    [self logoutSuccess];
}

- (void)logoutSuccess {
    UserInstance *userInstance = [UserInstance sharedInstance];
    userInstance.user = nil;
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLogoutNotification object:nil];
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _dataSource.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_dataSource[section] integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    if (indexPath.section) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    switch (indexPath.section) {
        case 0:
        {
            if (indexPath.row == 0) {
//                cell.textLabel.text = @"用户身份:企业";
                UserInstance *userInstance = [UserInstance sharedInstance];
                cell.detailTextLabel.textColor = [UIColor grayColor];
                cell.textLabel.text = [NSString stringWithFormat:@"用户身份:%@",userInstance.user.ctype[DataTextKey]];
                
            }else if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"information_icon_attestation"];
                cell.textLabel.text = @"平台认证";
                cell.detailTextLabel.text = _cModel.authstatus[DataTextKey];
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }else {
                cell.imageView.image = [UIImage imageNamed:@"information_icon_jiaona"];
                cell.textLabel.text = @"交易保证金";
                cell.detailTextLabel.text = _cModel.bailstatus[DataTextKey];
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }
        }
            break;
        case 1:
        {
            NSArray *texts = @[@"企业联系信息",@"姓名",@"手机",@"电话",];
            cell.textLabel.text = texts[indexPath.row];
            if (indexPath.row == 0) {
                
            }else if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_name"];
                cell.detailTextLabel.text = _cModel.contact.length ? _cModel.contact : @"暂无";
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_cellphone"];
                cell.detailTextLabel.text = _cModel.cphone.length ? _cModel.cphone : @"暂无";
            }else if (indexPath.row == 3) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_phone"];
                cell.detailTextLabel.text = _cModel.tel.length ? _cModel.tel : @"暂无";
            }
        }
            break;
        case 2:
        {
            if ([_dataSource[indexPath.section] integerValue] == 5) {
                if (indexPath.row == 0) {
                    cell.textLabel.textColor = [UIColor grayColor];
                    cell.textLabel.text = @"交易地址信息";
//                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = [NSString stringWithFormat:@"%@%@",_cModel.addrAreaFullName,_cModel.address];
                }else if (indexPath.row == 2) {
                    int j = 0;
                    for (AddressImgModel *imgModel in _cModel.addressImgList) {
                        UIImageView *imageView = [[UIImageView alloc] init];
                        imageView.frame = CGRectMake(15+j*(260/3+15), 10, 260/3, 80);
                        [imageView sd_setImageWithURL:[NSURL URLWithString:imgModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                        [cell addSubview:imageView];
                        j++;
                    }
                }else if (indexPath.row == 3) {
                    cell.textLabel.text = @"卸货码头水深度(单位:/米)";
                    cell.detailTextLabel.text = [NSString stringWithFormat:@"%.1f",[_cModel.deep floatValue]];
                }else if (indexPath.row == 4) {
                    cell.textLabel.text = @"可停泊载重船吨位(单位:/吨)";
                    cell.detailTextLabel.text = [NSString stringWithFormat:@"%.1f",[_cModel.shippington floatValue]];
                }
            }else {
                if (indexPath.row == 0) {
                    cell.textLabel.textColor = [UIColor grayColor];
                    cell.textLabel.text = @"交易地址信息";
//                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = @"暂无地址信息";
                }
            }
            

        }
            break;
        case 3:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"企业简介";
            }else {
                NSString *targetStr = _cModel.mark ? _cModel.mark : @"暂无简介";
                UILabel *label = [UILabel labelWithTitle:targetStr];
                label.frame = CGRectMake(15, 10, cell.vwidth-30, 0);
                label.numberOfLines = 0;
                [label sizeToFit];
                [cell.contentView addSubview:label];
            }
        }
            break;
        case 4:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"企业照片";
            }else {
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
                if (_cModel.companyImgList.count) {
                    int j = 0;
                    for (AddressImgModel *imgModel in _cModel.companyImgList) {
                        UIImageView *imageView = [[UIImageView alloc] init];
                        imageView.frame = CGRectMake(15+j*(260/3+15), 10, 260/3, 80);
                        [imageView sd_setImageWithURL:[NSURL URLWithString:imgModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                        [cell addSubview:imageView];
                        j++;
                    }
                }else {
                    UIImageView *imageView = [[UIImageView alloc] init];
                    imageView.frame = CGRectMake(15, 10, 260/3, 80);
                    imageView.image = [UIImage imageNamed:PlaceHodelImageName];
                    [cell addSubview:imageView];
                }
                
            }

        }
            break;
            
        default:
            break;
    }
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 2) {
        if (indexPath.row == 2) {
            return 100;
        }
    }
    
    if (indexPath.section == 3) {
        if (indexPath.row == 1) {
            return _cModel.mark.length ? [self heightOfLabelSize:_cModel.mark] : 44;
        }
    }
    
    if (indexPath.section == 4) {
        if (indexPath.row == 1) {
            return 100;
        }
    }
    
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    if (indexPath.section == 0 && indexPath.row == 1) {
        if ([_cModel.authstatus[DataValueKey] integerValue] != 1) { // 没有认证通过或正在认证中
            AuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"AuthViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
        }else { // 查看认证资料
            AuthDetailViewController *vc = [[AuthDetailViewController alloc] init];
            vc.cjCopyModel = _cModel;
            vc.userType = [_cModel.ctype[DataValueKey] integerValue];
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
}

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


@end
