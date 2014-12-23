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
    [self.view addSubview:_addressListView];
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_addressListView setTableFooterView:view];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.addressArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identify = @"addressList";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identify];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identify];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    AddressPublicModel *model = self.addressArray[indexPath.row];
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
    UnLoadDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnLoadDetailViewControllerId"];
    vc.addressModel = self.addressArray[indexPath.row];
    [self.navigationController pushViewController:vc animated:YES];

}


@end
