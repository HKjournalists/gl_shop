//
//  OppsiteProfileViewController.m
//  Glshop
//
//  Created by River on 15-2-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "OppsiteProfileViewController.h"
#import "CopyRightModel.h"
#import "LoadImageView.h"
#import "AddressImgModel.h"
#import "TPFloatRatingView.h"
#import "CommentViewController.h"
#import "AuthDetailViewController.h"

@interface OppsiteProfileViewController ()  <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;

/**
 *@brief 企业信息
 */
@property (nonatomic, strong) CopyRightModel *cModel;

@end

@implementation OppsiteProfileViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self requestNet];
}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}

- (void)initDatas {
    _dataSource = @[@3,@6,@4,@5,@2,@2,];
    self.title = @"对方资料";
    self.edgesForExtendedLayout = UIRectEdgeAll;
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    [self.view addSubview:_tableView];
    _tableView.hidden = YES;
    
    _tableView.tableHeaderView = [self loadHeaderView];
}

- (UIView *)loadHeaderView {
    UIImageView *header = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 170)];
    header.userInteractionEnabled = YES;
    header.image = [UIImage imageNamed:@"information_beijing"];
    //    [self.view addSubview:header];
    
    UIImageView *logoImage = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-33, 70, 66, 66)];
    logoImage.image = [UIImage imageNamed:@"information_touxiang"];
    [header addSubview:logoImage];
    
    UILabel *titleLabel = [UILabel labelWithTitle:@"长江电商"];
    titleLabel.textColor = [UIColor whiteColor];
    titleLabel.frame = CGRectMake(SCREEN_WIDTH/2-60, logoImage.vbottom, 120, 25);
    titleLabel.textAlignment = NSTextAlignmentCenter;
    [header addSubview:titleLabel];
    
    return header;
}

- (void)requestNet {
    [super requestNet];
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_cid forKey:path_key_companyId];
    __block typeof(self) this = self;
    [self requestWithURL:bUserBaseInfoPath params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
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
    
    if (!_cModel.address.length) {
        _dataSource = @[@3,@6,@4,@2,@2,@2,];
    }
    
    _tableView.hidden = NO;
    [_tableView reloadData];
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
    if (indexPath.section==1&&indexPath.row == 5) {
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
    }else {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    switch (indexPath.section) {
        case 0:
        {
            if (indexPath.row == 0) {
                cell.detailTextLabel.textColor = [UIColor grayColor];
                if ([self.cModel.ctype[DataTextKey] length]) {
                    cell.textLabel.text = [NSString stringWithFormat:@"用户身份:%@",self.cModel.ctype[DataTextKey]];
                }else {
                    cell.textLabel.text = @"用户身份:未认证";
                }
                
            }else if (indexPath.row == 1) {
                if (_cModel.isUserAuthed) {
                    cell.imageView.image = [UIImage imageNamed:@"icon_renzheng"];
                }else {
                    cell.imageView.image = [UIImage imageNamed:@"icon_rz"];
                }
                cell.textLabel.text = @"平台认证";
                cell.detailTextLabel.text = _cModel.authstatus[DataTextKey];
                if (_cModel.isUserAuthed) {
                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                }
            }else {
                if (_cModel.isPaymentMargin) {
                    cell.imageView.image = [UIImage imageNamed:@"icon_bao"];
                }else {
                    cell.imageView.image = [UIImage imageNamed:@"icon_b"];
                }
                cell.textLabel.text = @"交易保证金";
                cell.detailTextLabel.text = _cModel.bailstatus[DataTextKey];
            }
        }
            break;
        case 1:
        {
            NSArray *data = @[@"评价信息:",@"成功交易笔数",@"交易成功率",@"交易满意度",@"交易诚信度",@"交易历史评价",];
            cell.textLabel.text = data[indexPath.row];
            cell.detailTextLabel.textColor = [UIColor orangeColor];
            if (indexPath.row == 1) {
                cell.detailTextLabel.text = [_cModel.evaluatModel.transactionSuccessNum stringValue];
            }else if (indexPath.row == 2) {
                float successRate = [_cModel.evaluatModel.transactionSuccessRate floatValue]*100;
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f%%",successRate];
            }else if (indexPath.row == 3) {
                TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.view.vwidth-130, 8.5, 120, 30)];
                ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
                ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
                ratingView.rating = [_cModel.evaluatModel.averageEvaluation floatValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 4) {
                TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.view.vwidth-130, 8.5, 120, 30)];
                ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
                ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
                ratingView.rating = [_cModel.evaluatModel.averageCredit floatValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 5) {
                cell.detailTextLabel.text = @"查看";
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }
        }
            break;
        case 2:
        {
            NSArray *texts = @[@"联系人信息",@"姓名",@"手机",@"电话",];
            cell.textLabel.text = texts[indexPath.row];
            if (indexPath.row == 0) {
                
            }else if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_namexx"];
                cell.detailTextLabel.text = _cModel.contact.length ? _cModel.contact : cell_temp_none;
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_cellphone"];
                cell.detailTextLabel.text = _cModel.cphone.length ? _cModel.cphone : cell_temp_none;
            }else if (indexPath.row == 3) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_phone"];
                cell.detailTextLabel.text = _cModel.tel.length ? _cModel.tel : cell_temp_none;
            }
        }
            break;
        case 3:
        {
            if ([_dataSource[indexPath.section] integerValue] == 5) {
                if (indexPath.row == 0) {
                    cell.textLabel.textColor = [UIColor grayColor];
                    cell.textLabel.text = @"默认交易地址";
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = [NSString stringWithFormat:@"%@%@",_cModel.addrAreaFullName,_cModel.address];
                }else if (indexPath.row == 2) {
                    if (_cModel.addressImgList.count) {
                        int j = 0;
                        for (AddressImgModel *imgModel in _cModel.addressImgList) {
                            CGRect rect = CGRectMake(15+j*(260/3+15), 10, 260/3, 80);
                            LoadImageView *imageView = [[LoadImageView alloc] initWithFrame:rect bigImageUrl:imgModel.url];
                            [imageView sd_setImageWithURL:[NSURL URLWithString:imgModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                            [cell addSubview:imageView];
                            j++;
                            
                        }
                    }else {
                        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(15, 10, 260/3, 80)];
                        imageView.image = [UIImage imageNamed:@"shangchuanzhong"];
                        [cell addSubview:imageView];
                    }
                    
                }else if (indexPath.row == 3) {
                    cell.textLabel.text = cell_address_depth;
                    cell.detailTextLabel.text = [NSString stringWithFormat:@"%.1f",[_cModel.deep floatValue]];
                }else if (indexPath.row == 4) {
                    cell.textLabel.text = cell_boat_tun;
                    cell.detailTextLabel.text = [NSString stringWithFormat:@"%.1f",[_cModel.shippington floatValue]];
                }
            }else {
                if (indexPath.row == 0) {
                    cell.textLabel.textColor = [UIColor grayColor];
                    cell.textLabel.text = cell_business_addressInfo;
                    //                    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = cell_NoaddressInfo;
                }
            }
            
            
        }
            break;
        case 4:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = cell_companyIndrouce;
            }else {
                NSString *targetStr = _cModel.mark ? _cModel.mark : cell_noIndrouce;
                UILabel *label = [UILabel labelWithTitle:targetStr];
                label.frame = CGRectMake(15, 10, cell.vwidth-30, 0);
                label.numberOfLines = 0;
                [label sizeToFit];
                [cell.contentView addSubview:label];
            }
        }
            break;
        case 5:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"用户照片";
            }else {
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
                if (_cModel.companyImgList.count) {
                    int j = 0;
                    for (AddressImgModel *imgModel in _cModel.companyImgList) {
                        LoadImageView *imageView = [[LoadImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80) bigImageUrl:imgModel.url];
                        [imageView sd_setImageWithURL:[NSURL URLWithString:imgModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                        [cell addSubview:imageView];
                        j++;
                    }
                }else {
                    UIImageView *imageView = [[UIImageView alloc] init];
                    imageView.frame = CGRectMake(15, 10, 260/3, 80);
                    imageView.image = [UIImage imageNamed:@"shangchuanzhong"];
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
    if (indexPath.section == 3) {
        if (indexPath.row == 2) {
            return 100;
        }
    }
    
    if (indexPath.section == 4) {
        if (indexPath.row == 1) {
            return _cModel.mark.length ? [self heightOfLabelSize:_cModel.mark] : 44;
        }
    }
    
    if (indexPath.section == 5) {
        if (indexPath.row == 1) {
            return 100;
        }
    }
    
    return 44;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 1 && indexPath.row == 5) {
        CommentViewController *vc = [[CommentViewController alloc] init];
        vc.checkStyle = copyrightCheck;
        vc.cid = _cModel.companyId;
        [self.navigationController pushViewController:vc animated:YES];
    }
    
    if (indexPath.section == 0 && indexPath.row == 1) {
        AuthDetailViewController *vc = [[AuthDetailViewController alloc] init];
        vc.cjCopyModel = _cModel;
        vc.userType = [_cModel.ctype[DataValueKey] integerValue];
        [self.navigationController pushViewController:vc animated:YES];
    }
}


@end
