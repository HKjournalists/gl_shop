//
//  UnloadAddressViewController.m
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UnloadAddressViewController.h"
#import "AddressPublicModel.h"
#import "UnLoadDetailViewController.h"
#import "AddressViewController.h"
#import "MangerAddressViewController.h"
#import "AuthViewController.h"

@interface UnloadAddressViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *addressListView;
@property (nonatomic, strong) NSArray *addresses;
@property (nonatomic, assign) NSInteger lastSeleced;

@end

@implementation UnloadAddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    [self requestNet];
}

#pragma mark - Override
- (void)initDatas {
    self.title = @"选择交货地址";
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushAddressListNotification object:nil];
}

- (void)loadSubViews {
    
    _addressListView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _addressListView.dataSource = self;
    _addressListView.delegate = self;
    _addressListView.vheight -= kTopBarHeight;
    [self.view addSubview:_addressListView];
    _addressListView.hidden = YES;
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_addressListView setTableFooterView:view];
    
    _addressListView.hidden = YES;
    self.navigationItem.rightBarButtonItem = nil;
    
}

#pragma mark - UIActions
- (void)addAddress {
    UnLoadDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnLoadDetailViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 进入地址管理页面
 */
- (void)mangerAddress {
    MangerAddressViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"MangerAddressViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - Net
- (void)requestNet {
    [super requestNet];
    UserInstance *user = [UserInstance sharedInstance];
    DLog(@"fdsfdsfsd");
    NSString *copyRightId = user.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:copyRightId,@"cid", nil];
    __block typeof(self) thisSelf = self;
    [self requestWithURL:bGetUnloadAddressList params:params HTTPMethod:kHttpGetMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [thisSelf handleNetData:responseData];
        
    } dataConflictBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
    } failedBlock:^(ASIHTTPRequest *req){
        DLog(@"failed");
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *addressDics = [responseData objectForKey:ServiceDataKey];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in addressDics) {
        AddressPublicModel *model = [[AddressPublicModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    self.addresses = [NSArray arrayWithArray:temp];
    [self.addressListView reloadData];
    
    _addressListView.hidden = NO;
    UIBarButtonItem *barItem = [[UIBarButtonItem alloc] initWithTitle:@"管理" style:UIBarButtonItemStylePlain target:self action:@selector(mangerAddress)];
    self.navigationItem.rightBarButtonItem = barItem;
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : _addresses.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (indexPath.section == 0) {
        UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
        cell.imageView.image = [UIImage imageNamed:@"information_icon_add"];
        cell.textLabel.text = @"新增交易地址";
        cell.textLabel.textColor = C_BLACK;
        cell.textLabel.font = UFONT_16_B;
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        return cell;
        
    }else {
        static NSString *identify = @"addressList";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identify];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identify];
            cell.textLabel.font = [UIFont systemFontOfSize:FONT_14];
            cell.textLabel.textColor = C_BLACK;
        }
        
        AddressPublicModel *model = self.addresses[indexPath.row];
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
        imageView.image = [UIImage imageNamed:@"address_icon_select"];
        if (indexPath.row == 0 && [[model.status objectForKey:@"val"] integerValue] == 1) {
            cell.textLabel.text = [NSString stringWithFormat:@"[默认]%@%@",model.areaFullName,model.address];
            cell.accessoryView = imageView;
        }else {
            cell.textLabel.text = [NSString stringWithFormat:@"%@%@",model.areaFullName,model.address];
        }
        
        if (indexPath.row == _lastSeleced) {
            cell.accessoryView = imageView;
        }
        
        return cell;
    }
    
}

#define sectionHigh 8
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

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        UnLoadDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnLoadDetailViewControllerId"];
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        AddressViewController *vc = [self publicInfoVC];
        AddressPublicModel *model = self.addresses[indexPath.row];
        vc.publicModel.address = model.address;
        vc.publicModel.deep = model.deep;
        vc.publicModel.addressImgModels = model.addressImgModels;
        vc.publicModel.addrAreaFullName = model.areaFullName;
        vc.publicModel.shippington = model.shippington;
        vc.publicModel.addressModel = model;
        NSInteger addressRows = model.addressImgModels.count ? 6 : 5;
        vc.sections = @[@1,@2,@1,[NSNumber numberWithInteger:addressRows],@2,@1];
        
        if (_type == Address_Public) {
            [vc.tableView performSelectorOnMainThread:@selector(reloadData) withObject:nil waitUntilDone:NO];
        }else {
            AuthViewController *vc = (AuthViewController *)self.navigationController.viewControllers[2];
            vc.authModel.addrAreaFullName = model.areaFullName;
            vc.authModel.address = model.address;
            vc.authModel.addressid = model.id;
            [vc.tableView performSelectorOnMainThread:@selector(reloadData) withObject:nil waitUntilDone:NO];
        }
        
        UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
        imageView.image = [UIImage imageNamed:@"address_icon_select"];
        cell.accessoryView = imageView;
        if (indexPath.row != _lastSeleced) {
            UITableViewCell *prvCell = [tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:_lastSeleced inSection:0]];
            prvCell.accessoryType = UITableViewCellAccessoryNone;
        }
        _lastSeleced = indexPath.row;
        
        [self.navigationController popViewControllerAnimated:YES];
    }

}

#pragma mark - Private
- (AddressViewController *)publicInfoVC {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[AddressViewController class]]) {
            return (AddressViewController *)vc;
        }
    }
    return nil;
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
