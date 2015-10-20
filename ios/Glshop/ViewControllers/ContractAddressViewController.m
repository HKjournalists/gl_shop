//
//  ContractAddressViewController.m
//  Glshop
//
//  Created by River on 15-2-2.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同交易地址

#import "ContractAddressViewController.h"
#import "AddressPublicModel.h"
#import "LoadImageView.h"

@interface ContractAddressViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) AddressPublicModel *address;

@end

@implementation ContractAddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = vc_title_contract_address;
    self.shouldShowFailView = YES;
    [self requestNet];
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.contentInset = UIEdgeInsetsMake(-15, 0, 0, 0);
    _tableView.vheight -= kTopBarHeight;
    _tableView.hidden = YES;
    [self.view addSubview:_tableView];
}

- (void)requestNet {
    [super requestNet];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_fid forKey:path_key_fid];
    __block typeof(self) this = self;
    [self requestWithURL:bordergetAddress params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    _tableView.hidden = NO;
    _address = [[AddressPublicModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    [self.tableView reloadData];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _address.addressImgModels.count ? 5 : 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    BaseTableViewCell *cell = [[BaseTableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    BOOL hasAddressImg = _address.addressImgModels.count;
    if (indexPath.row == 0) {
        cell.textLabel.text = _address.areaFullName;
    }else if (indexPath.row == 1) {
        cell.textLabel.text = _address.address;
    }else if (indexPath.row == 2) {
        if (hasAddressImg) {
            int j = 0;
            for (AddressImgModel *imageModel in _address.addressImgModels) {
                LoadImageView *imageView = [[LoadImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80) bigImageUrl:imageModel.url];
                [imageView sd_setImageWithURL:[NSURL URLWithString:imageModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                [cell addSubview:imageView];
                j++;
            }
        }else {
            cell.textLabel.text = cell_address_depth;
            cell.detailTextLabel.text = [_address.deep stringValue];
        }
    }else if (indexPath.row == 3) {
        if (hasAddressImg) {
            cell.textLabel.text = cell_address_depth;
            cell.detailTextLabel.text = [_address.deep stringValue];
        }else {
            cell.textLabel.text = cell_boat_tun;
            cell.detailTextLabel.text = [_address.shippington stringValue];
        }
        
    }else if (indexPath.row == 4) {
        cell.textLabel.text = cell_boat_tun;
        cell.detailTextLabel.text = [_address.shippington stringValue];
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 2 && _address.addressImgModels.count) {
        return 100;
    }
    return 44;
}

@end
