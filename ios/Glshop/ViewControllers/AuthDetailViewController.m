//
//  AuthDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AuthDetailViewController.h"
#import "CopyRightModel.h"
#import "AuthViewController.h"

@interface AuthDetailViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *keys;
@property (nonatomic, strong) NSMutableArray *values;

@end

@implementation AuthDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"认证信息";
    self.edgesForExtendedLayout = UIRectEdgeAll;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
//    CGRect rect = CGRectMake(0.0f, 0.0f, 320, 64.0f);
//    UIGraphicsBeginImageContext(rect.size);
//    CGContextRef context = UIGraphicsGetCurrentContext();
//    
//    CGContextSetFillColorWithColor(context, [[UIColor clearColor] CGColor]);
//    CGContextFillRect(context, rect);
//    
//    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
//    UIGraphicsEndImageContext();
//    
//    UIImage *bImg = [UIImage imageNamed:@"information_shang"];
//    bImg = [bImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
//    
//    UINavigationController *nav = self.navigationController;
//    [nav.navigationBar setBackgroundImage:bImg forBarMetrics:UIBarMetricsDefault];
//    [nav.navigationBar setShadowImage:image];
}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,0,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,0,0,0)];
    }
}

#pragma mark - UI
- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    UIEdgeInsets insets = UIEdgeInsetsZero;
    UserInstance *userInstance = [UserInstance sharedInstance];
    if (_userType == user_personal && [userInstance.user.cid isEqualToString:_cjCopyModel.companyId]) {
        insets = UIEdgeInsetsMake(0, 0, 60, 0);
        
        UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:@"重新认证" bframe:CGRectZero];
        [btn addTarget:self action:@selector(reAuth) forControlEvents:UIControlEventTouchUpInside];
        [self.view addSubview:btn];
        [btn makeConstraints:^(MASConstraintMaker *make) {
            make.leading.mas_equalTo(self.view).offset(10);
            make.right.mas_equalTo(self.view).offset(-10);
            make.bottom.mas_equalTo(self.view).offset(-10);
            make.height.mas_equalTo(40);
        }];
    }
    [_tableView makeConstraints:^(MASConstraintMaker *make) {
        make.edges.mas_equalTo(self.view).insets(insets);
    }];
    
    UIView *view = UIView.new;
    view.backgroundColor = [UIColor clearColor];
    _tableView.tableFooterView = view;
    _tableView.tableHeaderView = [self loadHeaderView];
}

- (UIView *)loadHeaderView {
    UIImageView *header = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 150)];
    header.userInteractionEnabled = YES;
    header.image = [UIImage imageNamed:@"information_beijing"];
    //    [self.view addSubview:header];
    
    UIImageView *logoImage = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-33, 67, 50, 50)];
    logoImage.image = [UIImage imageNamed:@"information_icon_attestation"];
    [header addSubview:logoImage];
    
    UILabel *titleLabel = [UILabel labelWithTitle:@"用户已通过平台认证"];
    titleLabel.textColor = [UIColor whiteColor];
    titleLabel.frame = CGRectMake(SCREEN_WIDTH/2-80, logoImage.vbottom, 160, 25);
    titleLabel.textAlignment = NSTextAlignmentCenter;
    [header addSubview:titleLabel];

    return header;
}

#pragma mark - UIAction
- (void)reAuth {
    AuthViewController *authVC = [[AuthViewController alloc] init];
    [self.navigationController pushViewController:authVC animated:YES];
}

#pragma mark - Stter
- (void)setUserType:(UserType)userType {
    _userType = userType;
    
    if (userType == user_company) {
        _keys = @[@"公司名称",@"注册地址",@"成立时间",@"注册号",@"法定代表人姓名",@"登记机关",@"企业类型"];
        _values = [NSMutableArray array];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.cname];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.address];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.rdate];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.regno];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.lperson];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.orgid];
        [_values addSafeObject:_cjCopyModel.authCompanyModel.ctype];
    }else if (userType == user_ship) {
        _keys = @[@"船舶名称",@"船籍港",@"船舶登记号",@"船舶检验机构",@"船舶所有人",@"船舶经营人",@"船舶种类",@"船舶建成日期",@"总吨位(吨)",@"载重(吨)",@"船长(米)",@"船宽(米)",@"型深(米)",@"满载吃水(米)",@"船体材料",];
        _values = [NSMutableArray array];
        [_values addSafeObject:_cjCopyModel.authShipModel.sname];
        [_values addSafeObject:_cjCopyModel.authShipModel.pregistry];
        [_values addSafeObject:_cjCopyModel.authShipModel.sno];
        [_values addSafeObject:_cjCopyModel.authShipModel.sorg];
        [_values addSafeObject:_cjCopyModel.authShipModel.sowner];
        [_values addSafeObject:_cjCopyModel.authShipModel.sbusinesser];
        [_values addSafeObject:_cjCopyModel.authShipModel.stype];
        [_values addSafeObject:_cjCopyModel.authShipModel.screatetime];
        [_values addSafeObject:[_cjCopyModel.authShipModel.stotal stringValue]];
        [_values addSafeObject:[_cjCopyModel.authShipModel.sload stringValue]];
        [_values addSafeObject:[_cjCopyModel.authShipModel.slength stringValue]];
        [_values addSafeObject:[_cjCopyModel.authShipModel.swidth stringValue]];
        [_values addSafeObject:[_cjCopyModel.authShipModel.sdeep stringValue]];
        [_values addSafeObject:[_cjCopyModel.authShipModel.sover stringValue]];
        [_values addSafeObject:_cjCopyModel.authShipModel.smateriall];
    }else if (userType == user_personal) {
        _keys = @[@"姓名",@"性别",@"出生日期",@"住址",@"身份证号",@"签发机关",@"有效期限",];
        _values = [NSMutableArray array];
        [_values addSafeObject:_cjCopyModel.authPsModel.cpname];
        [_values addSafeObject:_cjCopyModel.authPsModel.sex[DataTextKey]];
        [_values addSafeObject:_cjCopyModel.authPsModel.birthday];
        [_values addSafeObject:_cjCopyModel.authPsModel.origo];
        [_values addSafeObject:_cjCopyModel.authPsModel.identification];
        [_values addSafeObject:_cjCopyModel.authPsModel.issuingauth];
        [_values addSafeObject:_cjCopyModel.authPsModel.effendtime];
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return  _keys.count+1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *reuseId = @"authInfoId";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuseId];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:reuseId];
        cell.detailTextLabel.font = [UIFont systemFontOfSize:15.f];
        cell.textLabel.font = [UIFont systemFontOfSize:16.f];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    if (indexPath.row) {
        cell.textLabel.text = _keys[indexPath.row-1];
        [cell.detailTextLabel setSafeText:_values[indexPath.row-1]];
        cell.backgroundColor = [UIColor whiteColor];
    }else {
        cell.backgroundColor = self.view.backgroundColor;
        cell.textLabel.text = [NSString stringWithFormat:@"用户身份：%@",_cjCopyModel.ctype[DataTextKey]];
        cell.detailTextLabel.text = nil;
    }
    
    return cell;
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

@end
