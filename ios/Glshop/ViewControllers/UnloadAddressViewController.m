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

@interface UnloadAddressViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *addressListView;
@property (nonatomic, strong) NSArray *addresses;

@end

@implementation UnloadAddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestNet];
}

- (void)initDatas {
    self.title = @"选择交易地址";
}

-(void)viewDidLayoutSubviews
{
    if ([self.addressListView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.addressListView setSeparatorInset:UIEdgeInsetsMake(0,0,0,0)];
    }
    
    if ([self.addressListView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.addressListView setLayoutMargins:UIEdgeInsetsMake(0,0,0,0)];
    }
}

- (void)loadSubViews {
    
    _addressListView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _addressListView.dataSource = self;
    _addressListView.delegate = self;
    _addressListView.vheight -= kTopBarHeight+50;
    [self.view addSubview:_addressListView];
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_addressListView setTableFooterView:view];
    
    UIBarButtonItem *barItem = [[UIBarButtonItem alloc] initWithTitle:@"管理" style:UIBarButtonItemStylePlain target:self action:@selector(mangerAddress)];
    self.navigationItem.rightBarButtonItem = barItem;
    
    UIImageView *bottomView = [[UIImageView alloc] initWithFrame:CGRectMake(0, _addressListView.vbottom, self.view.vwidth, 50)];
    UIImage *image = [UIImage imageNamed:@"address_add_beijing"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    bottomView.image = image;
    bottomView.userInteractionEnabled = YES;
    [self.view addSubview:bottomView];
    
    UIButton *button = [UIButton buttonWithTip:@"添加新交易地址" target:self selector:@selector(addAddress)];
    button.frame = CGRectMake(bottomView.vwidth/2-45, 10, 110, 30);
    UIImage *btnImage = [UIImage imageNamed:@"address_add"];
    btnImage = [btnImage resizableImageWithCapInsets:UIEdgeInsetsMake(5, 50, 5, 50) resizingMode:UIImageResizingModeStretch];
    button.titleLabel.font = [UIFont systemFontOfSize:13.f];
    [button setBackgroundImage:btnImage forState:UIControlStateNormal];
    [bottomView addSubview:button];
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
    vc.addressArray = self.addresses;
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - Net
- (void)requestNet {
    UserInstance *user = [UserInstance sharedInstance];
    NSString *copyRightId = user.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:copyRightId,@"cid", nil];
    [self requestWithURL:bGetUnloadAddressList params:params HTTPMethod:kHttpGetMethod shouldCache:YES needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        NSArray *addressDics = [responseData objectForKey:@"DATA"];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in addressDics) {
            AddressPublicModel *model = [[AddressPublicModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        self.addresses = [NSArray arrayWithArray:temp];
        [self.addressListView reloadData];
        
    } dataConflictBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
    } failedBlock:^{
        DLog(@"failed");
    }];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.addresses.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identify = @"addressList";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identify];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identify];
    }
    AddressPublicModel *model = self.addresses[indexPath.row];
    cell.textLabel.text = model.address;
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

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {    
    AddressViewController *vc = [self publicInfoVC];
    vc.publicModel.addressModel = self.addresses[indexPath.row];
    [vc.tableView performSelectorOnMainThread:@selector(reloadData) withObject:nil waitUntilDone:NO];
    [self.navigationController popViewControllerAnimated:YES];
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

@end
