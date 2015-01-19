//
//  BrowseViewController.m
//  Glshop
//
//  Created by River on 14-12-25.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  找买找卖或我的供求详细页

#import "BrowseViewController.h"
#import "CompanyAuthViewController.h"
#import "UIImageView+WebCache.h"
#import "WPHotspotLabel.h"
#import "WPAttributedStyleAction.h"
#import "NSString+WPAttributedMarkup.h"

@interface BrowseViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) NSArray *sections;
@property (nonatomic, strong) PublicInfoModel *publicModel;
@property (nonatomic, strong) WPHotspotLabel *tipActionLabel;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UILabel *timeLabel;
@property (nonatomic, strong) UIView *headerView;
@property (nonatomic, strong) UIView *bottomView;

@end

@implementation BrowseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestNet];
}

#pragma mark - Overide
- (void)initDatas {
    _sections = @[@[@1],@[@0,@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,@11,@12,@13,@14],@[@2],@[@3],];
    self.shouldShowFailView = YES;
}

- (void)loadSubViews {
    
    [self loadHeaderView];
    
//    [self loadTipView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vtop += 30;
    _tableView.vheight -= kTopBarHeight+30+50;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    
    [self loadBottomView];
    
    [self hideViewsWhenNoData];
}

- (void)hideViewsWhenNoData {
    for (UIView *view in self.view.subviews) {
        view.hidden = YES;
    }
}

- (void)showViewsWhenDataComing {
    for (UIView *view in self.view.subviews) {
        view.hidden = NO;
    }
}

- (void)requestNet {
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_orderId forKey:@"fid"];
    [self.view showWithTip:nil Yoffset:kTopBarHeight];
    __block typeof(self) this = self;
    [self requestWithURL:bOrderInfo params:params HTTPMethod:kHttpGetMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
        
    } failedBlock:^(ASIHTTPRequest *req){
        DLog(@"faile");
        [self.view hideLoading];
    }];
}

- (void)handleNetData:(id)responseData {
    self.publicModel = [[PublicInfoModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    _timeLabel.text = _publicModel.creatime;
    [_tableView reloadData];
}

#pragma mark - UI
- (void)loadHeaderView {
    _headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 30)];
    
    UIView *timeView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 30)];
    timeView.backgroundColor = [UIColor whiteColor];
    UILabel *label = [UILabel labelWithTitle:@"发布时间:"];
    label.textColor = ColorWithHex(@"#5e5e5e");
    label.frame = CGRectMake(5, 0, 90, 30);
    [timeView addSubview:label];
    
    _timeLabel = [UILabel labelWithTitle:nil];
    _timeLabel.frame = CGRectMake(label.vright, 0, 200, label.vheight);
    _timeLabel.textColor = ColorWithHex(@"#ff2a00");
    _timeLabel.font = [UIFont systemFontOfSize:14.f];
    [timeView addSubview:_timeLabel];
    [_headerView addSubview:timeView];
    [self.view addSubview:_headerView];
}

/**
 *@brief 显示重新发布按钮
 */
- (void)showRePublicBtn {
    CGRect rect = CGRectMake(10, self.view.vbottom-45-kTopBarHeight, SCREEN_WIDTH-20, 40);
    UIButton *btn = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"重新发布" bframe:rect];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
}

/**
 *@brief 显示查看合同按钮
 */
- (void)showContractBtn {
    CGRect rect = CGRectMake(10, self.view.vbottom-45-kTopBarHeight, SCREEN_WIDTH-20, 40);
    UIButton *btn = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"查看合同" bframe:rect];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
}

/**
 *@brief 如果状态是有效的
 */
- (void)showOrderStatus_YES_BottomView {
    CGRect rect = CGRectMake(10, self.view.vbottom-45-kTopBarHeight, SCREEN_WIDTH/2-20, 40);
    UIButton *btn = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"修改信息" bframe:rect];
    [btn addTarget:self action:@selector(modifyPublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    
    CGRect rect1 = CGRectMake(btn.vright+20, btn.vtop, btn.vwidth, btn.vheight);
    UIButton *btn1 = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"取消发布" bframe:rect1];
    [btn1 addTarget:self action:@selector(canclePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn1];
}

/**
 *@brief 无效状态，交易已完成
 */
- (void)showOrderStatus_ZERO_BottomView {
    CGRect rect = CGRectMake(10, self.view.vbottom-45-kTopBarHeight, SCREEN_WIDTH/2-20, 40);
    UIButton *btn = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"重新发布" bframe:rect];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    
    CGRect rect1 = CGRectMake(btn.vright+20, btn.vtop, btn.vwidth, btn.vheight);
    UIButton *btn1 = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"查看合同" bframe:rect1];
    [btn1 addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn1];
}

/**
 *@brief 根据不同的订单状态，加载不同的视图
 */
- (void)loadBottomView {
    switch (_orderStatus) {
        case OrderStatus_YES:
        {
            [self showOrderStatus_YES_BottomView];
        }
            break;
        case OrderStatus_NO:
        {
            [self showRePublicBtn];
        }
            break;
        case OrderStatus_CLOSE:
        {
            [self showContractBtn];
        }
            break;
        case OrderStatus_ZERO:
        {
            
        }
            break;
        case OrderStatus_FAILURE:
        {
            [self showRePublicBtn];
        }
            break;
        case OrderStatus_CANCEL:
        {
            [self showRePublicBtn];
        }
            break;
            
        default:
            break;
    }
}

- (void)loadTipView {
    UIImageView *imageview = [[UIImageView alloc] initWithFrame:CGRectMake(0, 30, self.view.vwidth, 55)];
    imageview.userInteractionEnabled = YES;
    UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
    imageview.image = image;
    [_headerView addSubview:imageview];
    
    UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(5, 9, 15, 15)];
    logo.image = [UIImage imageNamed:@"supply-and-demand_icon_laba"];
    [imageview addSubview:logo];
    
    _tipActionLabel = [[WPHotspotLabel alloc] initWithFrame:CGRectMake(logo.vright+2, 0, 290, 50)];
    _tipActionLabel.numberOfLines = 3;
    NSDictionary* style3 = @{@"body":@[[UIFont fontWithName:@"HelveticaNeue" size:15.0],ColorWithHex(@"#ba9057")],
                             @"help":[WPAttributedStyleAction styledActionWithAction:^{

                             }],
                             @"link": @[[UIFont boldSystemFontOfSize:16.f],ColorWithHex(@"#FF0000"),],
                             };
    
    self.tipActionLabel.attributedText = [@"已有<help>200</help>吨货物成为订单，目前还有<help>300</help>吨货物信息继续发布。" attributedStringWithStyleBook:style3];
    [imageview addSubview:_tipActionLabel];
}

#pragma mark - Setter
- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    
    // 如果有实物图片，就展示实物图片
    _publicModel.photoUploadView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    if (_publicModel.productImgList.count) {
        NSMutableArray *temp = [NSMutableArray array];
        for (AddressImgModel *model in _publicModel.productImgList) {
            [temp addObject:model.thumbnailSmall];
        }
        _publicModel.photoUploadView.imageUrlArray = [NSArray arrayWithArray:temp];
    }
    
    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
        _sections = @[@[@1],@[@0,@1,@2,@3,@4,@5,@6,@7,@8,@10,@11,@12,@13,@14,@15],@[@1,@1],@[@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,],];
    }else {
        _sections = @[@[@1],@[@0,@2,@3,@4,@5,@6,@7,@8,@9,@10,@11,@12,@13,@14,@15],@[@1,@1],@[@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,],];
    }
    
    if (([_publicModel.type integerValue] == BussinessTypeSell && !_publicModel.addressImgModels.count) || [_publicModel.type integerValue] == BussinessTypeBuy) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[1]];
        [temp removeObject:@12];
        [temp removeObject:@13];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:1 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }
    
    NSInteger type = [[_publicModel.addresstype objectForKey:@"val"] integerValue];
    if (type == 2) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[3]];
        temp = [NSMutableArray arrayWithArray:@[@1,@2,@3,@9,@10,]];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:3 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }
    
    // 如果没有货物备注，屏蔽货物备注
    if (!_publicModel.premark) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[1]];
        [temp removeObject:@14];
        [temp removeObject:@15];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:1 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }
    
    // 如果没有交易备注，屏蔽交易备注
    if (!_publicModel.remark) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[3]];
        [temp removeLastObject];
        [temp removeLastObject];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:3 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }
}

#pragma mark - Private
- (void)cellDetialText:(UITableViewCell *)cell index:(NSInteger)index{
//    SynacInstance *synac = [SynacInstance sharedInstance];
//    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
//        NSArray *moelels = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propretyArray;
//        ProModel *model = moelels[index];
//        cell.detailTextLabel.text = model.proContent;
//    }else {
//        NSArray *moelels = [synac goodsChildStone:_publicModel.pid].propretyArray;
//        ProModel *model = moelels[index+1];
//        cell.detailTextLabel.text = model.proContent;
//    }
    
    NSInteger flag = [_publicModel.pcode isEqualToString:TopProductSendPcode] ? index : index+1;
    if (_publicModel.proList.count > flag) {
        ProModel *model = _publicModel.proList[flag];
        cell.textLabel.text = [NSString stringWithFormat:@"%@",model.pname];
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.1f",[model.proContent floatValue]];
    }
}

/**
 *@brief 计算文本高度
 */
- (float)heightOfLabelSize:(NSString *)text {
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, SCREEN_WIDTH-30, 0)];
    label.numberOfLines = 0;
    label.text = text;
    [label sizeToFit];
    DLog(@"%f",label.vheight);
    return label.vheight+35;
}

#pragma mark - UIActions 
/**
 *@brief 重新发布出售/求购信息
 */
- (void)rePublic {
        CompanyAuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CompanyAuthViewControllerId"];
        vc.publicModel = _publicModel;
        vc.publicInfoType = public_Reset;
        [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 修改发布信息
 */
- (void)modifyPublic {
    CompanyAuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CompanyAuthViewControllerId"];
    vc.publicModel = _publicModel;
    vc.publicInfoType = public_Modify;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 取消发布
 */
- (void)canclePublic {
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObject:_orderId forKey:@"fid"];
    [self requestWithURL:bCancelOrder params:dic HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        
    } failedBlock:^(ASIHTTPRequest *req){
        
    }];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _sections.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    NSArray *obj = _sections[section];
    return [obj count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSNumber *number = _sections[indexPath.section][indexPath.row];
    
    if ([number integerValue] == 13 && indexPath.section == 1) {
        return 100;
    }
    
    if ([number integerValue] == 15 && indexPath.section == 1) {
        return [self heightOfLabelSize:_publicModel.premark];
    }
    
    if (indexPath.section == 3) {
        if (indexPath.row == 9) {
            return [self heightOfLabelSize:_publicModel.remark];
        }
        
        if (indexPath.row == 5) {
            return 100;
        }
    }
    
    return 44;
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
            cell.textLabel.text = @"信息类型";
            cell.detailTextLabel.text = [_publicModel.type integerValue] == BussinessTypeSell ? @"出售信息":@"求购信息";
        }
            break;
        case 1:
        {

            NSArray *arr1 = _sections[indexPath.section];
            NSInteger type = [arr1[indexPath.row] integerValue];
            switch (type) {
                case 0:
                {
                    GoodsModel *model = [synac goodsModelForPcode:_publicModel.pcode];
                    cell.textLabel.text = @"货物信息";
                    cell.detailTextLabel.text = model.goodsName;
                }
                    break;
                case 1:
                {
                    GoodsModel *goods = [synac goodsModelForPtype:_publicModel.ptype];
                    cell.textLabel.text = @"分类";
                    cell.detailTextLabel.text = goods.goodsName;
                }
                    break;
                case 2:
                {
                    cell.textLabel.text = @"规格";
                    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
                        GoodChildModel *model = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                        cell.detailTextLabel.text = [NSString stringWithFormat:@"%@(%@-%@)mm",model.sizeModel.name,model.sizeModel.minv,model.sizeModel.maxv];
                    }else {
                        GoodChildModel *model = [synac goodsChildStone:_publicModel.pid];
                        cell.detailTextLabel.text = model.goodChildPname;
                    }
                }
                    break;
                case 3:
                {
//                    cell.textLabel.text = @"含泥量(%)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 4:
                {
//                    cell.textLabel.text = @"泥块含量(%)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 5:
                {
//                    cell.textLabel.text = @"含水率(%)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 6:
                {
//                    cell.textLabel.text = @"表观密度(kg/m)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 7:
                {
//                    cell.textLabel.text = @"堆积密度(kg/m)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 8:
                {
//                    cell.textLabel.text = @"坚固性指标(%)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 9:
                {
//                    cell.textLabel.text = @"坚固性指标(%)";
                    [self cellDetialText:cell index:indexPath.row-3];
                }
                    break;
                case 10:
                {
                    cell.textLabel.text = @"货物颜色";
                    cell.detailTextLabel.text = _publicModel.pcolor;
                }
                    break;
                case 11:
                {
                    cell.textLabel.text = @"货物产地";
                    cell.detailTextLabel.text = _publicModel.paddress;
                }
                    break;
                case 12:
                {
                    cell.textLabel.text = @"实物照片";
                }
                    break;
                case 13:
                {
                    int j = 0;
                    for (UIImage *image in _publicModel.photoUploadView.imageArray) {
                        UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:nil];
                        imageBtn.frame = CGRectMake(15+j*(260/3+15), 10, 260/3, 80);
                        [imageBtn setImage:image forState:UIControlStateNormal];
                        [cell addSubview:imageBtn];
                        j++;
                    }
                }
                    break;
                case 14:
                {
                    cell.textLabel.text = @"货物备注";
                }
                    break;
                case 15:
                {
                    UILabel *label = [UILabel labelWithTitle:_publicModel.premark];
                    label.frame = CGRectMake(15, 10, cell.vwidth-30, 0);
                    label.numberOfLines = 0;
                    [label sizeToFit];
                    [cell addSubview:label];
                }
                    break;
                    
                default:
                    break;
            }
            
            }
            break;
        case 2:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = [_publicModel.unit[@"val"] isEqualToString:MathUnitTon] ? @"购买量(单位:吨)" : @"购买量(单位:立方)";
                cell.detailTextLabel.text = [_publicModel.totalnum stringValue];
            }else {
                cell.textLabel.text = @"到港单价:(单位/元)";
                cell.detailTextLabel.text = [_publicModel.price stringValue];
            }
        }
            break;
        case 3:
        {
            NSArray *arr1 = _sections[indexPath.section];
            NSInteger type = [arr1[indexPath.row] integerValue];
            switch (type-1) {
                case 0:
                {
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
                }
                    break;
                case 1:
                {
                    cell.textLabel.text = @"交易地域";
                }
                    break;
                case 2:
                {
                    cell.textLabel.text = @"交易地址指定方式";
                    NSInteger type = [[_publicModel.addresstype objectForKey:@"val"] integerValue];
                    NSString *s;
                    if (type == 1) {
                        s = @"己方指定";
                    }else {
                        s = @"对方指定";
                    }
                    cell.detailTextLabel.text = s;
                }
                    break;
                case 3:
                {
                    cell.textLabel.text = @"详细交易地址";
                }
                    break;
                case 4:
                {
                    cell.textLabel.textColor = [UIColor blackColor];
                    cell.textLabel.text = _publicModel.address;
                }
                    break;
                case 5:
                {
                    if (_publicModel.addressImgModels) {
                        int j = 0;
                        for (AddressImgModel *model in _publicModel.addressImgModels) {
                            UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80)];
                            [imgView sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] placeholderImage:nil];
                            j++;
                            [cell addSubview:imgView];
                        }
                    }
                }
                    break;
                case 6:
                {
                    cell.textLabel.text = @"卸货码头水深度(单位/米)";
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }
                    break;
                case 7:
                {
                    cell.textLabel.text = @"可停泊载重船吨位(单位:吨)";
                    cell.detailTextLabel.text = [_publicModel.shippington stringValue];
                }
                    break;
                case 8:
                {
                    cell.textLabel.text = @"出售备注";
                }
                    break;
                case 9:
                {
                    UILabel *label = [UILabel labelWithTitle:_publicModel.remark];
                    label.frame = CGRectMake(15, 10, cell.vwidth-30, 0);
                    label.numberOfLines = 0;
                    [label sizeToFit];
                    [cell addSubview:label];
                }
                    break;
                    
                default:
                    break;
            }
        }
            break;
        case 4:
        {
            
        }
            break;
            
        default:
            break;
    }
    
    return cell;
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

@end
