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

@interface PreViewPublicViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *sections;
@property (nonatomic, strong) ASINetworkQueue *netQueue;
@property (nonatomic, strong) NSArray *imgIdArray;

@end

@implementation PreViewPublicViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    [_netQueue cancelAllOperations];
}

- (void)initDatas {
    if (_publicModel.addressType.count == 0) {
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
    
    UIButton *nexBtn = [UIButton buttonWithTip:@"立即发布" target:self selector:@selector(publicInfo:)];
    nexBtn.backgroundColor = CJBtnColor;
    nexBtn.layer.cornerRadius = 3.f;
    nexBtn.frame = CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40);
    [self.view addSubview:nexBtn];
}

#pragma mark - Setter
- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    
    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
        if ([_publicModel.type integerValue] == BussinessTypeSell) {
            _sections = @[@1,@3,@2,@6,@2,@1,@2,@2,@5,@2,@2];
        }else {
            _sections = @[@1,@3,@2,@6,@2,@1,@2,@2,@5,@2,];
        }
    }else {
        if ([_publicModel.type integerValue] == BussinessTypeSell) {
            _sections = @[@1,@2,@2,@6,@2,@1,@2,@2,@5,@2,@2];
        }else {
            _sections = @[@1,@2,@2,@6,@2,@1,@2,@2,@5,@2,];
        }
    }
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _sections.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_sections[section] integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.textColor = [UIColor lightGrayColor];
    cell.detailTextLabel.textColor = [UIColor blackColor];
    SynacInstance *synac = [SynacInstance sharedInstance];
    switch (indexPath.section) {
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
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%@(%@-%@)",model.sizeModel.name,model.sizeModel.minv,model.sizeModel.maxv];
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
            SynacInstance *synac = [SynacInstance sharedInstance];
            NSArray *titles = @[@"含泥量(%)",@"泥块含量(%)",@"含水率(%)",@"表观密度(kg/m)",@"堆积密度(kg/m)",@"坚固性指标(%)",];
            cell.textLabel.text = titles[indexPath.row];
            if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
                NSArray *moelels = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propretyArray;
                ProModel *model = moelels[indexPath.row];
                cell.detailTextLabel.text = model.content;
            }else {
                NSArray *moelels = [synac goodsChildStone:_publicModel.pid].propretyArray;
                ProModel *model = moelels[indexPath.row];
                cell.detailTextLabel.text = model.content;
            }
        }
            break;
        case 4:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"货物备注";
                cell.detailTextLabel.text = _publicModel.premark;
            }
        }
            break;
        case 5:
        {
            cell.textLabel.text = @"购买量(单位:吨)";
            cell.detailTextLabel.text = [_publicModel.totalnum stringValue];
        }
            break;
        case 6:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"交易日期范围";
                UIFont *font = [UIFont systemFontOfSize:13.f];
                UILabel *label = [UILabel labelWithTitle:_publicModel.starttime];
                label.frame = CGRectMake(150, 0, 80, 44);
                label.font = font;
                [cell addSubview:label];
                
                UILabel *label1 = [UILabel labelWithTitle:@"至"];
                label1.frame = CGRectMake(label.vright, 0, 20, 44);
                label1.font = [UIFont systemFontOfSize:14.f];
                label1.textColor = [UIColor lightGrayColor];
                [cell addSubview:label1];
                
                UILabel *label2 = [UILabel labelWithTitle:_publicModel.limitime];
                label2.frame = CGRectMake(label1.vright, 0, label.vwidth, 44);
                label2.font = font;
                [cell addSubview:label2];
            }else {
                cell.textLabel.text = @"交易地域";
                cell.detailTextLabel.text = _publicModel.publicOtherInfoModel.bussinessPlace;
            }
        }
            break;
        case 7:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"到港单价:(单位:元)";
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",_publicModel.publicOtherInfoModel.unitPrice];
            }else {
                cell.textLabel.text = @"卸货地点指定方式";
                NSInteger type = [[_publicModel.addressType objectForKey:@"val"] integerValue];
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
                cell.textLabel.text = _publicModel.addressModel.address;
            }else if (indexPath.row == 2) {
                int j = 0;
                for (AddressImgModel *model in _publicModel.addressModel.addressImgModels) {
                    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80)];
                    [imgView sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] placeholderImage:nil];
                    j++;
                    [cell addSubview:imgView];
                }
            }else if (indexPath.row == 3) {
                cell.textLabel.text = @"卸货码头水深度(单位:米)";
                cell.detailTextLabel.text = [_publicModel.addressModel.realdeep stringValue];
            }else if (indexPath.row == 4) {
                cell.textLabel.text = @"可停泊载重船吨位(单位:米)";
                cell.detailTextLabel.text = [_publicModel.addressModel.deep stringValue];
            }
        }
            break;
        case 9:
        {
            if ([_publicModel.type integerValue] == BussinessTypeSell) {
                if (indexPath.row == 0) {
                    cell.textLabel.text = @"实物照片";
                }else {
                    for (int i = 0; i < 3; i++) {
                        cell.selectionStyle = UITableViewCellSelectionStyleNone;
                        UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:nil];
                        imageBtn.frame = CGRectMake(15+i*(260/3+15), 10, 260/3, 80);
                        imageBtn.tag = indexPath.section*100+i;
                        if (i == 0 && fmod(imageBtn.tag, indexPath.section*100) == 0) {
                            UIImage *image = _publicModel.image1 ? _publicModel.image1 : [UIImage imageNamed:@"address_photo"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }else if (i == 1 && fmod(imageBtn.tag, indexPath.section*100) == 1) {
                            UIImage *image = _publicModel.image2 ? _publicModel.image2 : [UIImage imageNamed:@"address_photo_add"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }else if (i == 2 && fmod(imageBtn.tag, indexPath.section*100) == 2){
                            UIImage *image = _publicModel.image3 ? _publicModel.image3 : [UIImage imageNamed:@"address_photo_add"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }
                        
                        [cell addSubview:imageBtn];
                    }

                }
            }else {
                if (indexPath.row == 0) {
                    cell.textLabel.text = [_publicModel.type integerValue] == BussinessTypeSell ? @"销售备注" : @"购买备注";
                }
            }
        }
            break;
        case 10:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = [_publicModel.type integerValue] == BussinessTypeSell ? @"销售备注" : @"购买备注";
            }
        }
            break;
        default:
            break;
    }
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 8) {
        if (indexPath.row == 2) {
            return 100;
        }
    }
    
    if (indexPath.section == 9 && [_publicModel.type integerValue] == BussinessTypeSell) {
        if (indexPath.row == 1) {
            return 100;
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
- (NSMutableDictionary *)createPostDictionary {
    NSMutableDictionary *params = [NSMutableDictionary dictionary];

    // 买家/卖家
    [params setObject:_publicModel.type forKey:@"typeValue"];
    
    // 企业id
    UserInstance *user = [UserInstance sharedInstance];
    NSString *cid = user.user.cid;
    [params addString:cid forKey:@"cid"];
    
    // 卸货地址指定方式
    NSNumber *number = [_publicModel.addressType objectForKey:@"val"];
    [params addNumber:number forKey:@"addresstypeValue"];

    // 单价
    [params addNumber:_publicModel.price forKey:@"price"];
    
    // 总量
    [params addNumber:_publicModel.totalnum forKey:@"totalnum"];
    
    // 交易范围
    [params addString:_publicModel.starttime forKey:@"starttime"];
    [params addString:_publicModel.limitime forKey:@"limitime"];
    
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
    [params addString:_publicModel.unit forKey:@"unit"];
    
    // 属性jsonString
    [params addString:_publicModel.productPropertys forKey:@"productPropertys"];
    
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
    
    if (_publicModel.entryImages.count > 0 && [_publicModel.type integerValue] == BussinessTypeSell) { // 如果有实物图片，需要先上传实物图片
        [self showHUD:@"正在发布..." isDim:NO Yoffset:0];
        _netQueue = [ASINetworkQueue queue];
        NSMutableArray *tempIdArray = [NSMutableArray array];
        int i = 0;
        for (UIImage *image in _publicModel.entryImages) {
            NSData *data = UIImageJPEGRepresentation(image, 1);
            NSString *name = [NSString stringWithFormat:@"entityImg%d.jpeg",i];
            NSString *filePath = [[NSString documentsPath] stringByAppendingPathComponent:name];
            [data writeToFile:filePath atomically:YES];
            ASIFormDataRequest *request = [self uploadImgWithURL:bfileupload
                                                      HTTPMethod:kHttpPostMethod
                                                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                               
                NSArray *array = [responseData objectForKey:ServiceDataKey];
                if (array.count > 0) {
                    NSDictionary *dic = array.firstObject;
                    [tempIdArray addObject:dic[@"id"]];
                }else{
                    return;
                }
                if (i == _publicModel.entryImages.count-1) { // 最有最后一次图片上传成功，才进行发布信息
                    [self hideHUD];
                    NSString *parmStr = [tempIdArray componentsJoinedByString:@","];
                    NSMutableDictionary *params = [self createPostDictionary];
                    [params addString:parmStr forKey:@"productImgIds"];
                    [self public:params];
                }
                
            } failedBlock:^{
                [self hideHUD];
                if (i == _publicModel.entryImages.count-1) { // 只有最后一次的图片上传失败，才进行发布消息
                    [self public:nil];
                }
            } ];
            [request addData:data withFileName:filePath andContentType:@"image/jpeg" forKey:@"file"];
            [_netQueue addOperation:request];
            i++;
        }
        [_netQueue setMaxConcurrentOperationCount:1];
        [_netQueue go];

    }else {
        [self public:nil];
    }
    
}

/**
 *@brief 发布信息
 */
- (void)public:(NSMutableDictionary *)params {
    [self showHUD:@"正在发布..." isDim:NO Yoffset:0];
    NSMutableDictionary *param = params ? params : [self createPostDictionary];
    [self requestWithURL:bPublishOrderInfo params:param HTTPMethod:kHttpPostMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [self showTip:@"发布成功！"];
    } failedBlock:^{

    }];
}

@end
