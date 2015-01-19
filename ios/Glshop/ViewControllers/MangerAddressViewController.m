//
//  MangerAddressViewController.m
//  Glshop
//
//  Created by River on 14-12-22.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MangerAddressViewController.h"
#import "AddressPublicModel.h"
#import "UnLoadDetailViewController.h"

@interface MangerAddressViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *addressListView;

@end

@implementation MangerAddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)initDatas {
    self.title = @"管理交易地址";
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushAddressListNotification object:nil];
    self.shouldShowFailView = YES;
    [self requestNet];
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
    _addressListView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _addressListView.dataSource = self;
    _addressListView.delegate = self;
    [self.view addSubview:_addressListView];
    _addressListView.hidden = YES;
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_addressListView setTableFooterView:view];
}

#pragma mark - Net
- (void)requestNet {
    [super requestNet];
    
    UserInstance *user = [UserInstance sharedInstance];
    NSString *copyRightId = user.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:copyRightId,@"cid", nil];
    __block typeof(self) thisVC = self;
    [self requestWithURL:bGetUnloadAddressList params:params HTTPMethod:kHttpGetMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [thisVC handleNetData:responseData];
        
    } dataConflictBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
    } failedBlock:^(ASIHTTPRequest *req){
        DLog(@"failed");
    }];
}

- (void)handleNetData:(id)responseData {
    _addressListView.hidden = NO;
    
    NSArray *addressDics = [responseData objectForKey:ServiceDataKey];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in addressDics) {
        AddressPublicModel *model = [[AddressPublicModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    self.addressArray = [NSArray arrayWithArray:temp];
    [self.addressListView reloadData];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : self.addressArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (indexPath.section == 0) {
        UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
        cell.imageView.image = [UIImage imageNamed:@"information_icon_add"];
        cell.textLabel.text = @"新增交易地址";
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        return cell;
        
    }else {
        static NSString *identify = @"addressList";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identify];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identify];
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            cell.textLabel.font = [UIFont systemFontOfSize:13.5];
        }
        AddressPublicModel *model = self.addressArray[indexPath.row];
        if (indexPath.row == 0 && [[model.status objectForKey:@"val"] integerValue] == 1) {
//            cell.textLabel.text = FommatString(@"[默认]", model.address);
            cell.textLabel.text = [NSString stringWithFormat:@"[默认]%@%@",model.areaFullName,model.address];
//            UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
//            imageView.image = [UIImage imageNamed:@"address_icon_select"];
//            cell.accessoryView = imageView;
        }else {
            cell.textLabel.text = [NSString stringWithFormat:@"%@%@",model.areaFullName,model.address];
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
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        UnLoadDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnLoadDetailViewControllerId"];
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        UnLoadDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnLoadDetailViewControllerId"];
        vc.addressModel = self.addressArray[indexPath.row];
        vc.editorAddress = YES;
        [self.navigationController pushViewController:vc animated:YES];
    }


}


@end
