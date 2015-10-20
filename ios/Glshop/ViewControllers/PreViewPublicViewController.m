//
//  PreViewPublicViewController.m
//  Glshop
//
//  Created by River on 14-12-17.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PreViewPublicViewController.h"
#import "PublicGuideView.h"
#import "UIImageView+WebCache.h"
#import "ASINetworkQueue.h"
#import "AddressImgModel.h"
#import "CompanyAuthViewController.h"
#import "TipSuccessViewController.h"

@interface PreViewPublicViewController () <UITableViewDataSource,UITableViewDelegate,UploadImageDelete>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *sections;

@end

@implementation PreViewPublicViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    _publicModel.photoUploadView.delegate = nil;
    [_publicModel.photoUploadView.netQueue cancelAllOperations];
}

#pragma mark - Overidde
- (void)initDatas {
    if (_publicModel.addresstype.count == 0) {
//        _publicModel
    }
}

- (void)loadSubViews {
    // 引导图
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:3];
    UIView *header = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 70)];
    [header addSubview:guideView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+guideView.vheight;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.tableHeaderView = header;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIFactory createBtn:@"注册-正常状态" bTitle:@"立即发布" bframe:CGRectMake(10, self.tableView.vbottom+10, self.view.vwidth-20, 40)];
    [nexBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nexBtn];
}

#pragma mark - Setter
- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    
    if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) { // 黄砂
        GoodChildModel *child = [SynacObject goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
        NSNumber *proCount = [NSNumber numberWithInteger:child.propretyArray.count];
        if ([_publicModel.type integerValue] == BussinessTypeSell && _publicModel.photoUploadView.imageArray.count) {
            _publicModel.photoUploadView.delegate = self;
            _sections = @[@{@0:@1},@{@1:@3},@{@2:@2},@{@3:proCount},@{@4:@2},@{@5:@1},@{@6:@2},@{@7:@2},@{@8:@5},@{@9:@2},@{@10:@2},];
        }else {
            _sections = @[@{@0:@1},@{@1:@3},@{@2:@2},@{@3:proCount},@{@4:@2},@{@5:@1},@{@6:@2},@{@7:@2},@{@8:@5},@{@10:@2},];
        }
        
    }else { // 石子
        GoodChildModel *child = [SynacObject goodsChildStone:_publicModel.pid];
        NSNumber *proCount = [NSNumber numberWithInteger:child.propretyArray.count];
        if ([_publicModel.type integerValue] == BussinessTypeSell && _publicModel.photoUploadView.imageArray.count) {
            _publicModel.photoUploadView.delegate = self;            
            _sections = @[@{@0:@1},@{@1:@2},@{@2:@2},@{@3:proCount},@{@4:@2},@{@5:@1},@{@6:@2},@{@7:@2},@{@8:@5},@{@9:@2},@{@10:@2},];
        }else {
            _sections = @[@{@0:@1},@{@1:@2},@{@2:@2},@{@3:proCount},@{@4:@2},@{@5:@1},@{@6:@2},@{@7:@2},@{@8:@5},@{@10:@2},];
        }
    }
    
    if (!_publicModel.premark.length) { // 如果没有商品备注
        NSMutableArray *array = [NSMutableArray arrayWithArray:_sections];
        [array removeObjectAtIndex:4];
        _sections = [NSArray arrayWithArray:array];
    }
    
    if (!_publicModel.remark.length) {  // 如果没有交易备注
        NSMutableArray *array = [NSMutableArray arrayWithArray:_sections];
        [array removeLastObject];
        _sections = [NSArray arrayWithArray:array];
    }
    
    if ([_publicModel.addresstype[DataValueKey] integerValue] == 1) {
        if (!_publicModel.addressImgModels.count) { // 如果己方指定卸货地址没有图片
            NSDictionary *dics = @{@8:@5};
            NSInteger index = [_sections indexOfObject:dics];
            NSMutableArray *array = [NSMutableArray arrayWithArray:_sections];
            [array replaceObjectAtIndex:index withObject:@{@8:@4}];
            _sections = [NSArray arrayWithArray:array];
        }
    }else {
        NSDictionary *dics = @{@8:@5};
        NSInteger index = [_sections indexOfObject:dics];
        NSMutableArray *array = [NSMutableArray arrayWithArray:_sections];
        if (index != NSNotFound) {
            [array removeObjectAtIndex:index];
        }
        _sections = [NSArray arrayWithArray:array];
    }

    

}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _sections.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//    return [_sections[section] integerValue];
    NSDictionary *dic = _sections[section];
    return [dic.allValues.firstObject integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {

      UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;

    SynacInstance *synac = [SynacInstance sharedInstance];
    
    cell.textLabel.textColor = C_GRAY;
    cell.textLabel.font = [UIFont systemFontOfSize:FONT_16];
    cell.detailTextLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_16];
    
    NSDictionary *dic = _sections[indexPath.section];
    NSInteger type = [dic.allKeys.firstObject integerValue];
    switch (type) {
        case 0:
        {
            cell.textLabel.text = @"发布信息类型";
            cell.detailTextLabel.text = [_publicModel.type integerValue] == 1 ? buyInfo : sellInfo;
        }
            break;
        case 1:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = glProduct_goods_info;
                GoodsModel *model = [synac goodsModelForPcode:_publicModel.pcode];
                cell.detailTextLabel.text = model.goodsName;
            }else if (indexPath.row == 1) {
                if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
                    cell.textLabel.text = glProduct_goods_category;
                    GoodsModel *goods = [synac goodsModelForPtype:_publicModel.ptype];
                    cell.detailTextLabel.text = goods.goodsName;
                }else {
                    cell.textLabel.text = glProduce_goods_stand;
                    GoodChildModel *model = [synac goodsChildStone:_publicModel.pid];
                    cell.detailTextLabel.text = model.combineNameWithUnit;
                }
            }else {
                cell.textLabel.text = glProduce_goods_stand;
                GoodChildModel *model = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                cell.detailTextLabel.text = model.combineNameWithUnit;
            }
        }
            break;
        case 2:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = glProduct_goods_color;
                cell.detailTextLabel.text = _publicModel.pcolor.length ? _publicModel.pcolor : @"未填写";
            }else {
                cell.textLabel.text = glProduct_goods_place;
                cell.detailTextLabel.text = _publicModel.paddress.length ? _publicModel.paddress : @"未填写";
            }
        }
            break;
        case 3:
        {
            NSArray *proArray;
            if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
                GoodChildModel *model = [SynacObject goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                proArray = model.propretyArray;
            }else {
                GoodChildModel *model = [SynacObject goodsChildStone:_publicModel.pid];
                proArray = model.propretyArray;
            }
            ProModel *promodel = [proArray safeObjAtIndex:indexPath.row];
            cell.textLabel.text = promodel.combinePnameWithUnit;
            cell.detailTextLabel.text = @"未填写";
            // 填充数据
            for (ProModel *aModel in _publicModel.proList) {
                for (ProModel *norModel in proArray) {
                    if ([norModel.proCode isEqualToString:aModel.proCode]) {
                        NSInteger index = [proArray indexOfObject:norModel];
                        if (index == indexPath.row) {
                            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[aModel.proContent floatValue]];
                        }
                    }
                }
            }
        }
            break;
        case 4:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"货物备注";
            }else {
                UILabel *label = [UILabel labelWithTitle:_publicModel.premark];
                label.frame = CGRectMake(15, 10, cell.vwidth-25, 0);
                label.numberOfLines = 0;
                [label sizeToFit];
                [cell.contentView addSubview:label];
            }
        }
            break;
        case 5:
        {
            NSString *selOrBuy = [_publicModel.type integerValue] == 1 ? @"购买量" : @"销售量";
            if ([_publicModel.unit[@"val"] isEqualToString:MathUnitTon]) {
                cell.textLabel.text = [NSString stringWithFormat:@"%@(单位:吨)",selOrBuy];
            }else {
                cell.textLabel.text = [NSString stringWithFormat:@"%@量(单位:立方)",selOrBuy];
            }
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_publicModel.totalnum floatValue]];
        }
            break;
        case 6:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"交易日期范围";
                UIFont *font = [UIFont systemFontOfSize:13.f];
                UILabel *label = [UILabel labelWithTitle:[_publicModel.starttime substringToIndex:10]];
                label.frame = CGRectMake(150, 0, 80, 44);
                label.font = font;
                [cell addSubview:label];
                
                UILabel *label1 = [UILabel labelWithTitle:@"至"];
                label1.frame = CGRectMake(label.vright, 0, 20, 44);
                label1.font = [UIFont systemFontOfSize:14.f];
                label1.textColor = [UIColor lightGrayColor];
                [cell addSubview:label1];
                
                UILabel *label2 = [UILabel labelWithTitle:[_publicModel.endtime substringToIndex:10 ]];
                label2.frame = CGRectMake(label1.vright, 0, label.vwidth, 44);
                label2.font = font;
                [cell addSubview:label2];
            }else {
                cell.textLabel.text = @"交易地域";
                cell.detailTextLabel.text = _publicModel.areaFullName;
            }
        }
            break;
        case 7:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"到港单价:(单位:元/吨)";
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_publicModel.price floatValue]];
            }else {
                cell.textLabel.text = @"交货地址指定方式";
                NSInteger type = [[_publicModel.addresstype objectForKey:@"val"] integerValue];
                NSString *s;
                if (type == 1) {
                    s = @"己方指定";
                }else {
                    s = @"对方指定";  
                }
                cell.detailTextLabel.text = s;
            }
        }
            break;
        case 8:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"详细交易地址";
            }else if (indexPath.row == 1) {
                cell.textLabel.textColor = [UIColor blackColor];
                cell.textLabel.text = FommatString(_publicModel.addrAreaFullName, _publicModel.address);
            }else if (indexPath.row == 2) {
                if (_publicModel.addressImgModels.count) {
                    int j = 0;
                    for (AddressImgModel *model in _publicModel.addressImgModels) {
                        UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80)];
                        [imgView sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] placeholderImage:nil];
                        j++;
                        [cell addSubview:imgView];
                    }
                }else {
                    cell.textLabel.text = cell_address_depth;
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }
              
            }else if (indexPath.row == 3) {
                if (_publicModel.addressImgModels.count) {
                    cell.textLabel.text = cell_address_depth;
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }else {
                    cell.textLabel.text = cell_boat_tun;
                    cell.detailTextLabel.text = [_publicModel.shippington stringValue];
                }
                
            }else if (indexPath.row == 4) {
                cell.textLabel.text = cell_boat_tun;
                cell.detailTextLabel.text = [_publicModel.shippington stringValue];
            }
        }
            break;
        case 9:
        {
            
                if (indexPath.row == 0) {
                    cell.textLabel.text = @"实物照片";
                }else {
                    cell.selectionStyle = UITableViewCellSelectionStyleNone;
                    int j = 0;
                    for (UIImage *image in _publicModel.photoUploadView.imageArray) {
                        UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:nil];
                        imageBtn.frame = CGRectMake(15+j*(260/3+15), 10, 260/3, 80);
                        [imageBtn setImage:image forState:UIControlStateNormal];
                        [cell addSubview:imageBtn];
                        j++;
                    }
                }
        }
            break;
        case 10:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = [_publicModel.type integerValue] == BussinessTypeSell ? @"销售备注" : @"购买备注";
            }else {
                UILabel *label = [UILabel labelWithTitle:_publicModel.remark];
                label.frame = CGRectMake(15, 10, cell.vwidth-25, 0);
                label.numberOfLines = 0;
                [label sizeToFit];
                [cell.contentView addSubview:label];
            }
        }
            break;
        default:
            break;
    }
    return cell;
}

/**
 *@brief 计算文本高度
 */
- (float)heightOfLabelSize:(NSString *)text {
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 10, self.tableView.vwidth-25, 0)];
    label.numberOfLines = 0;
    label.text = text;
    [label sizeToFit];
    return label.vheight+35;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSDictionary *dic = _sections[indexPath.section];
    NSInteger type = [dic.allKeys.firstObject integerValue];
    
    if (type == 4) { // 货物备注
        if (indexPath.row == 1) {
            return [self heightOfLabelSize:_publicModel.premark];
        }
    }
    
    if (type == 8 && [dic.allValues.firstObject integerValue] == 5) {
        if (indexPath.row == 2) {
            return 100;
        }
    }
    
    if (type == 9) {
        if (indexPath.row == 1) {
            return 100;
        }
    }
    
    if (type == 10) {
        if (indexPath.row == 1) {
            return [self heightOfLabelSize:_publicModel.remark];
        }
    }

    return 44;
}

#pragma mark - Private 

/**
 *@brief 生成发布信息参数
 */
- (NSMutableDictionary *)createPostDictionary {
    NSMutableDictionary *params = [NSMutableDictionary dictionary];

    // 买家/卖家
    [params setObject:_publicModel.type forKey:@"typeValue"];
    
    // 企业id
    UserInstance *user = [UserInstance sharedInstance];
    NSString *cid = user.user.cid;
    [params addString:cid forKey:@"cid"];
    
    // 卸货地址指定方式
    NSNumber *number = [_publicModel.addresstype objectForKey:@"val"];
    [params addNumber:number forKey:@"addresstypeValue"];

    // 单价
    [params addNumber:_publicModel.price forKey:@"price"];
    
    // 发布地域
    [params addString:_publicModel.area forKey:@"area"];
    
    // 总量
    [params addNumber:_publicModel.totalnum forKey:@"totalnum"];
    
    // 交易范围
#warning 与服务器约定，时间只带年月日，后面加上 00:00:00
    [params addString:[NSString stringWithFormat:@"%@ 00:00:00",_publicModel.starttime] forKey:@"starttime"];
    [params addString:[NSString stringWithFormat:@"%@ 00:00:00",_publicModel.endtime] forKey:@"endtime"];
    
    // 是否多地域
    [params setObject:@1 forKey:@"moreareaValue"];
    
    // 卸货地址ID
    if (_publicModel.addressModel && _publicModel.addressModel.id) {
        [params addString:_publicModel.addressModel.id forKey:@"addressid"];
    }
    
    // 产品id
    [params addString:_publicModel.pid forKey:@"pid"];
    
    // 产品颜色
    [params addString:_publicModel.pcolor forKey:@"pcolor"];
    
    // 产品产地
    [params addString:_publicModel.paddress forKey:@"paddress"];
    
    // 单位
    [params addString:_publicModel.unit[@"val"] forKey:@"unit"];
    
    // 属性jsonString
    if (_publicModel.productPropertys.length) {
        [params addString:_publicModel.productPropertys forKey:@"productPropertys"];
    }
    
    // 产品备注
    if (_publicModel.premark) {
        [params addString:_publicModel.premark forKey:@"premark"];
    }
    
    // 发布备注
    if (_publicModel.remark) {
        [params addString:_publicModel.remark forKey:@"remark"];
    }
    
    return params;
}

#pragma mark - UIActions
/**
 *@brief 点击上传图片并发布（求购/出售）信息
 */
- (void)publicInfo:(UIButton *)button {
    
    if (_publicModel.photoUploadView.imageArray.count > 0) { // 如果有实物图片，需要先上传实物图片
        [self showHUD:@"正在发布..." isDim:NO Yoffset:0];
        [_publicModel.photoUploadView uploadImage];
    }else {
        [self hideHUD];
        [self public:nil];
    }
    
}

/**
 *@brief 发布信息
 */
- (void)public:(NSMutableDictionary *)params {
    [self showHUD:@"正在发布..." isDim:NO Yoffset:0];
    NSMutableDictionary *param = params ? params : [self createPostDictionary];
    CompanyAuthViewController *vc = [self findDesignatedViewController:[CompanyAuthViewController class]];
    NSString *serPath = vc.publicInfoType == public_Modify ? bModifyMyProduct : bPublishOrderInfo;
    if (vc.publicInfoType == public_Modify) {
        [param addString:_publicModel.id forKey:@"id"];
    }
    if (vc.publicInfoType == public_Reset) {
        [param addString:_publicModel.id forKey:@"originalFid"];
    }
    __block typeof(self) this = self;
    [self requestWithURL:serPath params:param HTTPMethod:kHttpPostMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMySupplyNotification object:nil];
        TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
        vc.operationType = tip_public_success;
        [this.navigationController pushViewController:vc animated:YES];
    } failedBlock:^(ASIHTTPRequest *req){
        
    }];
}

#pragma mark - UploadImage Delegate
- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView{
    [self hideHUD];
    NSMutableDictionary *params = [self createPostDictionary];
    [params addString:imgsId forKey:@"productImgIds"];
    [self public:params];
}

- (void)uploadImageFaile:(PhotoUploadView *)uploadView {
    [self hideHUD];
    [self public:nil];
    
}

@end
