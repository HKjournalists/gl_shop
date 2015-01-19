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

- (void)initDatas {
    if (_publicModel.addresstype.count == 0) {
//        _publicModel
    }
}

- (void)loadSubViews {
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:3];
    [self.view addSubview:guideView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vtop = guideView.vbottom;
    _tableView.vheight -= kTopBarHeight+guideView.vheight+50;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIFactory createBtn:@"注册-正常状态" bTitle:@"立即发布" bframe:CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40)];
    [nexBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nexBtn];
}

#pragma mark - Setter
- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    
    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) { // 黄砂
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
    
    if (_publicModel.isOwen) {
        if (!_publicModel.addressImgModels.count) { // 如果卸货地址没有图片
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
        cell.textLabel.textColor = [UIColor lightGrayColor];
        cell.detailTextLabel.textColor = [UIColor blackColor];
    SynacInstance *synac = [SynacInstance sharedInstance];
    
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
                cell.textLabel.text = @"货物信息";
                GoodsModel *model = [synac goodsModelForPcode:_publicModel.pcode];
                cell.detailTextLabel.text = model.goodsName;
            }else if (indexPath.row == 1) {
                if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
                    cell.textLabel.text = @"分类";
                    GoodsModel *goods = [synac goodsModelForPtype:_publicModel.ptype];
                    cell.detailTextLabel.text = goods.goodsName;
                }else {
                    cell.textLabel.text = @"规格";
                    GoodChildModel *model = [synac goodsChildStone:_publicModel.pid];
                    cell.detailTextLabel.text = model.goodChildPname;
                }
            }else {
                cell.textLabel.text = @"规格";
                GoodChildModel *model = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%@(%@-%@)mm",model.sizeModel.name,model.sizeModel.minv,model.sizeModel.maxv];
            }
        }
            break;
        case 2:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"货物颜色";
                cell.detailTextLabel.text = _publicModel.pcolor;
            }else {
                cell.textLabel.text = @"货物产地";
                cell.detailTextLabel.text = _publicModel.paddress;
            }
        }
            break;
        case 3:
        {
            NSArray *proArray;
            if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
                GoodChildModel *model = [SynacObject goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                proArray = model.propretyArray;
            }else {
                GoodChildModel *model = [SynacObject goodsChildStone:_publicModel.pid];
                proArray = model.propretyArray;
            }
            ProModel *promodel;
            if (_publicModel.proList.count > indexPath.row) {
                promodel = _publicModel.proList[indexPath.row];
            }else if (proArray.count > indexPath.row) {
                promodel = proArray[indexPath.row];
            }
            cell.textLabel.text = [promodel.pname stringByAppendingString:@"(%)"];
            if (_publicModel.isSetDetailStand) { // 如果填写了详细规格就展示
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[promodel.proContent floatValue]];
            }else { // 如果没填写详细规格就显示未填写
                cell.detailTextLabel.text = @"未填写";
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
            cell.textLabel.text = [_publicModel.unit[@"val"] isEqualToString:MathUnitTon] ? @"购买量(单位:吨)" : @"购买量(单位:立方)";
            cell.detailTextLabel.text = [_publicModel.totalnum stringValue];
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
                cell.textLabel.text = @"到港单价:(单位:元)";
                cell.detailTextLabel.text = [_publicModel.price stringValue];
            }else {
                cell.textLabel.text = @"卸货地点指定方式";
                NSInteger type = [[_publicModel.addresstype objectForKey:@"val"] integerValue];
                NSString *s;
                if (type == 1) {
                    s = @"买方指定";
                }else {
                    s = @"卖方指定";
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
                cell.textLabel.text = FommatString(_publicModel.areaFullName, _publicModel.address);
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
                    cell.textLabel.text = @"卸货码头水深度(单位:米)";
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }
              
            }else if (indexPath.row == 3) {
                if (_publicModel.addressImgModels) {
                    cell.textLabel.text = @"卸货码头水深度(单位:米)";
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }else {
                    cell.textLabel.text = @"可停泊载重船吨位(单位:米)";
                    cell.detailTextLabel.text = [_publicModel.shippington stringValue];
                }
                
            }else if (indexPath.row == 4) {
                cell.textLabel.text = @"可停泊载重船吨位(单位:米)";
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

#pragma mark - Private 

- (CompanyAuthViewController *)publicInfoVC {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[CompanyAuthViewController class]]) {
            return (CompanyAuthViewController *)vc;
        }
    }
    return nil;
}

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
    [params addString:_publicModel.areaCode forKey:@"areacode"];
    
    // 总量
    [params addNumber:_publicModel.totalnum forKey:@"totalnum"];
    
    // 交易范围
    [params addString:_publicModel.starttime forKey:@"starttime"];
    [params addString:_publicModel.endtime forKey:@"endtime"];
    
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
    if (_publicModel.isSetDetailStand) {
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
        [self showHUD:@"正在发布..." isDim:YES Yoffset:0];
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
    CompanyAuthViewController *vc = [self publicInfoVC];
    NSString *serPath = vc.publicInfoType == public_Modify ? bModifyMyProduct : bPublishOrderInfo;
    if (vc.publicInfoType == public_Modify) {
        [param addString:_publicModel.id forKey:@"id"];
    }
    [self requestWithURL:serPath params:param HTTPMethod:kHttpPostMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [self showTip:@"发布成功！"];
    } failedBlock:^(ASIHTTPRequest *req){
        HUD(kNetError);
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
