//
//  BrowseViewController.m
//  Glshop
//
//  Created by River on 14-12-25.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  找买找卖或我的供求详细页

#import "BrowseViewController.h"
#import "CompanyAuthViewController.h"
#import "MySupplyViewController.h"
#import "UIImageView+WebCache.h"
#import "WPHotspotLabel.h"
#import "WPAttributedStyleAction.h"
#import "NSString+WPAttributedMarkup.h"
#import "TPFloatRatingView.h"
#import "TipSuccessViewController.h"
#import "CommentViewController.h"
#import "LoadImageView.h"
#import "ContractDetailViewController.h"
#import "ContractProcessDetailViewController.h"

static NSInteger bottomViewHeight = 50;
static NSInteger cancleAlertViewTag = 2014;
static NSInteger deleteAlertViewTag = 2015;

@interface BrowseViewController () <UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) NSArray *sections;
@property (nonatomic, strong) PublicInfoModel *publicModel;

/**
 *@brief 发布信息的相关数据，用此数据可新建PublicInfoModel
 *@discussion 不把_publicModel给其他控制器引用，是防止数据修改后，BrowseViewController页面显示数据错乱
 */
@property (nonatomic, strong) NSDictionary *publicInfo;

@property (nonatomic, strong) WPHotspotLabel *tipActionLabel;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UILabel *statusLabel;
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
}

- (void)loadSubViews {
    
    [self loadHeaderView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+bottomViewHeight;
    _tableView.tableHeaderView = _headerView;
    _tableView.hidden = YES;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    self.view.backgroundColor = _tableView.backgroundColor;

}

- (void)requestNet {
    [super requestNet];
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_orderId forKey:@"fid"];
    [self.view showWithTip:nil Yoffset:kTopBarHeight];
    __block typeof(self) this = self;
    [self requestWithURL:bOrderInfo params:params HTTPMethod:kHttpGetMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
//        kASIResultLog;
        [this handleNetData:responseData];
        
    } failedBlock:^(ASIHTTPRequest *req){

    }];
}

- (void)handleNetData:(id)responseData {
    _tableView.hidden = NO;
    _publicInfo = [NSDictionary dictionaryWithDictionary:responseData[ServiceDataKey]];
    self.publicModel = [[PublicInfoModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    DLog(@"%@",_publicModel.id);
    [_tableView reloadData];
}

#pragma mark - UI
- (void)loadHeaderView {
    
    float height = _fromMySupply ? 36 : 110;
    _headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, height)];
    
    UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
    
    UIImageView *timeView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 30)];
    timeView.image = image;
    
    UIImageView *loImg = [[UIImageView alloc] initWithFrame:CGRectMake(5, 5, 25, 20)];
    loImg.image = [UIImage imageNamed:@"attestation_icon_suona"];
    [timeView addSubview:loImg];
    
    _statusLabel = [UILabel labelWithTitle:@"发布时间:"];
    _statusLabel.textColor = ColorWithHex(@"#5e5e5e");
    _statusLabel.font = [UIFont systemFontOfSize:FONT_14];
    [timeView addSubview:_statusLabel];
    [_statusLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.mas_equalTo(0);
        make.leading.mas_equalTo(loImg.vright+3);
        make.width.mas_greaterThanOrEqualTo(70);
        make.height.mas_equalTo(30);
    }];
    
    _timeLabel = [UILabel labelWithTitle:nil];
    _timeLabel.adjustsFontSizeToFitWidth = YES;
    _timeLabel.textColor = ColorWithHex(@"#ff2a00");
    _timeLabel.font = [UIFont systemFontOfSize:FONT_14];
    [timeView addSubview:_timeLabel];
    [_timeLabel makeConstraints:^(MASConstraintMaker *make) {
        make.top.mas_equalTo(0);
        make.leading.mas_equalTo(_statusLabel.right);
        make.width.mas_equalTo(220);
        make.height.mas_equalTo(30);
    }];
    [_headerView addSubview:timeView];
    
    UIImageView *imgTipView = [[UIImageView alloc] initWithFrame:CGRectMake(0, timeView.vbottom+5, self.view.vwidth, 70)];
    imgTipView.image = [UIImage imageNamed:@"Buy_sell_baozhang"];
    if (!_fromMySupply) {
        [_headerView addSubview:imgTipView];
    }
}

/**
 *@brief 显示重新发布按钮
 */
- (void)showRePublicBtn {
//    CGRect rect = CGRectMake(10, self.view.vbottom-kNavagtionBarHeight, SCREEN_WIDTH-20, 40);
    UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:@"重新发布" bframe:CGRectZero];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [btn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.height.mas_equalTo(40);
        make.right.mas_equalTo(self.view).offset(-10);
    }];
}


/**
 *@brief 显示查看合同按钮
 */
- (void)showContractBtn {
//    CGRect rect = CGRectMake(10, self.view.vbottom-kNavagtionBarHeight, SCREEN_WIDTH-20, 40);
    UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:@"查看合同" bframe:CGRectZero];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [btn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.height.mas_equalTo(40);
        make.right.mas_equalTo(self.view).offset(-10);
    }];
}

/**
 *@brief 如果状态是有效的
 */
- (void)showOrderStatus_YES_BottomView {
//    CGRect rect = CGRectMake(10, self.view.vbottom-kNavagtionBarHeight, SCREEN_WIDTH/2-20, 40);
    UIButton *btn = [UIFactory createBtn:YelloCommnBtnImgName bTitle:@"修改信息" bframe:CGRectZero];
    [btn addTarget:self action:@selector(modifyPublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [btn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.size.mas_equalTo(CGSizeMake(140, 40));
    }];
    
//    CGRect rect1 = CGRectMake(btn.vright+20, btn.vtop, btn.vwidth, btn.vheight);
    UIButton *btn1 = [UIFactory createBtn:BlueButtonImageName bTitle:@"取消发布" bframe:CGRectZero];
    [btn1 addTarget:self action:@selector(canclePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn1];
    [btn1 makeConstraints:^(MASConstraintMaker *make) {
        make.right.mas_equalTo(self.view).offset(-10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.size.mas_equalTo(CGSizeMake(140, 40));
    }];
}

/**
 *@brief 无效状态，交易已完成
 */
- (void)showOrderStatus_ZERO_BottomView {
//    CGRect rect = CGRectMake(10, self.view.vbottom-kNavagtionBarHeight, SCREEN_WIDTH/2-20, 40);
    UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:@"重新发布" bframe:CGRectZero];
    [btn addTarget:self action:@selector(rePublic) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [btn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.size.mas_equalTo(CGSizeMake(140, 40));
    }];
    
//    CGRect rect1 = CGRectMake(btn.vright+20, btn.vtop, btn.vwidth, btn.vheight);
    UIButton *btn1 = [UIFactory createBtn:BlueButtonImageName bTitle:@"查看合同" bframe:CGRectZero];
    [btn1 addTarget:self action:@selector(checkContract) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn1];
    [btn1 makeConstraints:^(MASConstraintMaker *make) {
        make.right.mas_equalTo(self.view).offset(-10);
        make.bottom.mas_equalTo(self.view).offset(-5);
        make.size.mas_equalTo(CGSizeMake(140, 40));
    }];
}

/**
 *@brief 根据不同的订单状态，加载不同的视图
 */
- (void)loadBottomView {
    _timeLabel.text = nil;
    switch (_orderStatus) {
        case OrderStatus_YES:
        {
            _timeLabel.text = _publicModel.updatetime;
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
            _statusLabel.text = [NSString stringWithFormat:@"该信息已于%@交易完成",_publicModel.updatetime];
            [self showOrderStatus_ZERO_BottomView];
            [self showDeleteNavItem];
        }
            break;
        case OrderStatus_ZERO:
        {
            
        }
            break;
        case OrderStatus_FAILURE:
        {
            _statusLabel.text = [NSString stringWithFormat:@"该信息已于%@过期",_publicModel.endtime];
            [self showRePublicBtn];
            [self showDeleteNavItem];
            
        }
            break;
        case OrderStatus_CANCEL:
        {
            _statusLabel.text = [NSString stringWithFormat:@"该信息已于%@取消发布",_publicModel.updatetime];
            [self showRePublicBtn];
            [self showDeleteNavItem];
        }
            break;
            
        default:
            break;
    }
    
}

- (void)showDeleteNavItem {
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemTrash target:self action:@selector(deleteOrder)];
    self.navigationItem.rightBarButtonItem = item;
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
    
    if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
        _sections = @[@[@1],@[@0,@1,@2,@3,@4,@5,@6,@7,@8,@10,@11,@12,@13,@14,@15],@[@1,@1],@[@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,],@[@1,@1,@1,@1,@1,@1,@1,@1,],];
    }else {
        _sections = @[@[@1],@[@0,@2,@3,@4,@5,@6,@7,@8,@9,@10,@11,@12,@13,@14,@15],@[@1,@1],@[@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,],@[@1,@1,@1,@1,@1,@1,@1,@1,],];
    }
    
    if (([_publicModel.type integerValue] == BussinessTypeSell && !_publicModel.productImgList.count) || [_publicModel.type integerValue] == BussinessTypeBuy) {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[1]];
        [temp removeObject:@12];
        [temp removeObject:@13];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:1 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }
    
    NSInteger type = [[_publicModel.addresstype objectForKey:@"val"] integerValue];
    if (type == 2) { // 未指定地址
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[3]];
        temp = [NSMutableArray arrayWithArray:@[@1,@2,@9,@10,]];
        
        NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
        [te replaceObjectAtIndex:3 withObject:temp];
        _sections = [NSArray arrayWithArray:te];
    }else {
        if (!_publicModel.addressImgModels.count) { // 卸货地址没有图片
            NSMutableArray *temp = [NSMutableArray arrayWithArray:_sections[3]];
            if ([temp containsObject:@6]) {
                [temp removeObject:@6];
            }
            
            NSMutableArray *te = [NSMutableArray arrayWithArray:_sections];
            [te replaceObjectAtIndex:3 withObject:temp];
            _sections = [NSArray arrayWithArray:te];
        }
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
    
    _timeLabel.text = _publicModel.updatetime;
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    if ([_publicModel.cid isEqualToString:userInstance.user.cid] || [_publicModel.isApply integerValue] == 1) { // 是自己发布的或者已经申请过的不显示交易询盘按钮
        if (_fromMySupply == 1) {
            [self loadBottomView];
        }else {
            if (![_publicModel.cid isEqualToString:userInstance.user.cid]) {
                UIButton *button = [UIFactory createBtn:YelloCommnBtnImgName bTitle:@"我已交易询盘" bframe:CGRectZero];
                button.enabled = NO;
                [self.view addSubview:button];
                [button makeConstraints:^(MASConstraintMaker *make) {
                    make.leading.mas_equalTo(self.view).offset(10);
                    make.right.mas_equalTo(self.view).offset(-10);
                    make.bottom.mas_equalTo(self.view).offset(-5);
                }];
            }else {
                _tableView.vheight += 50;
            }
            
        }
    }else {
        UIButton *button = [UIFactory createBtn:BlueButtonImageName bTitle:@"交易询盘" bframe:CGRectZero];
        [button addTarget:self action:@selector(gobuy) forControlEvents:UIControlEventTouchUpInside];
        [self.view addSubview:button];
        [button makeConstraints:^(MASConstraintMaker *make) {
            make.leading.mas_equalTo(self.view).offset(10);
            make.right.mas_equalTo(self.view).offset(-10);
            make.bottom.mas_equalTo(self.view).offset(-5);
        }];
    }
}

#pragma mark - Private
- (void)cellDetialText:(UITableViewCell *)cell index:(NSInteger)index{
    NSInteger flag = [_publicModel.pcode isEqualToString:glProduct_top_send_code] ? index : index+1;
    NSArray *proList;
    if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
        GoodChildModel *model = [SynacObject goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
        proList = model.propretyArray;
    }else {
        GoodChildModel *model = [SynacObject goodsChildStone:_publicModel.pid];
        proList = model.propretyArray;
    }
    ProModel *amodel = [proList safeObjAtIndex:flag];
    cell.textLabel.text = amodel.combinePnameWithUnit;
    cell.detailTextLabel.text = @"无";
    
    if (_publicModel.proList.count) { // 如果有详细规格
        // 填充数据
        for (ProModel *aModel in _publicModel.proList) {
            for (ProModel *norModel in proList) {
                if ([norModel.proCode isEqualToString:aModel.proCode]) {
                    NSInteger indexc = [proList indexOfObject:norModel];
                    if (indexc == flag) {
                        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[aModel.proContent floatValue]];
                    }
                }
            }
        }
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
    return label.vheight+35;
}

#pragma mark - UIActions
/**
 *@brief 删除询单
 */
- (void)deleteOrder {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"删除发布信息" message:@"请确认是否删除该信息，删除后将不可恢复。" delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    alert.tag = deleteAlertViewTag;
    [alert show];

}

/**
 *@brief 重新发布出售/求购信息
 */
- (void)rePublic {
        CompanyAuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CompanyAuthViewControllerId"];
        vc.publicModel = [[PublicInfoModel alloc] initWithDataDic:_publicInfo];;
        vc.publicInfoType = public_Reset;
        [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 查看合同
 */
- (void)checkContract {

    ContractStatus status = [_publicModel.contractStatus[DataValueKey] integerValue];
    if (status == DRAFT) { // 起草中的合同跳到 ContractDetailViewController
        ContractDetailViewController *vc = [[ContractDetailViewController alloc] init];
        vc.contractId = _publicModel.contractid;
        vc.title = @"合同详情";
        [self.navigationController pushViewController:vc animated:YES];
    }else { // 进行中和已结束的合同跳到ContractProcessDetailViewController
        ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
        vc.contractId = _publicModel.contractid;
        [self.navigationController pushViewController:vc animated:YES];
    }

}

/**
 *@brief 修改发布信息
 */
- (void)modifyPublic {
    CompanyAuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CompanyAuthViewControllerId"];
    vc.publicModel = [[PublicInfoModel alloc] initWithDataDic:_publicInfo];
    vc.publicInfoType = public_Modify;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 取消发布
 */
- (void)canclePublic {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"取消发布" message:@"取消该信息发布后将无法获得更多的生意成交机会，请确认是否取消。" delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alert.tag = cancleAlertViewTag;
    [alert show];
}

/**
 *@brief 交易询盘
 */
- (void)gobuy {
    UserInstance *userObj = [UserInstance sharedInstance];
    if (![userObj login]) {
        [self showTip:@"您还没有登录！"];
        return;
    }
    
//    if (!userObj.isBeAuthed) {
//        [Utilits alertWithString:@"请完成长江电商平台认证后再来交易！" alertTitle:nil];
//        return;
//    }
//    
//    if (!userObj.isPaymentMargin) {
//        [Utilits alertWithString:@"请先缴纳保证金后再来交易！" alertTitle:nil];
//        return;
//    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_publicModel.id,@"fid", nil];
    __block typeof(self) this = self;
    this.shouldShowFailView = NO;
    [self showHUD];
    [self requestWithURL:bDealApply
                  params:params
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
       [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushBuySellNotification object:nil];
        TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
        vc.operationType = tip_inquiry_success;
        [this.navigationController pushViewController: vc animated:YES];
    } failedBlock:^(ASIHTTPRequest *request) {

    }];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _fromMySupply ? _sections.count-1 : _sections.count;
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
        
        if (indexPath.row == 5 && _publicModel.addressImgModels.count) {
            return 100;
        }
    }
    
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.textColor = C_GRAY;
    cell.textLabel.font = [UIFont systemFontOfSize:FONT_16];
    cell.detailTextLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_16];
    
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
                    cell.textLabel.text = glProduct_goods_info;
                    cell.detailTextLabel.text = model.goodsName;
                }
                    break;
                case 1:
                {
                    GoodsModel *goods = [synac goodsModelForPtype:_publicModel.ptype];
                    cell.textLabel.text = glProduct_goods_category;
                    cell.detailTextLabel.text = goods.goodsName;
                }
                    break;
                case 2:
                {
                    cell.textLabel.text = glProduce_goods_stand;
                    if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
                        GoodChildModel *model = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                        cell.detailTextLabel.text = model.combineNameWithUnit;
                    }else {
                        GoodChildModel *model = [synac goodsChildStone:_publicModel.pid];
                        cell.detailTextLabel.text = model.combineNameWithUnit;
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
                    cell.textLabel.text = glProduct_goods_color;
                    cell.detailTextLabel.text = _publicModel.pcolor.length ? _publicModel.pcolor : @"无";
                }
                    break;
                case 11:
                {
                    cell.textLabel.text = glProduct_goods_place;
                    cell.detailTextLabel.text = _publicModel.paddress.length ? _publicModel.paddress : @"无";
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
                    for (AddressImgModel *imageModel in _publicModel.productImgList) {
                        LoadImageView *imageView = [[LoadImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80) bigImageUrl:imageModel.url];
                        [imageView sd_setImageWithURL:[NSURL URLWithString:imageModel.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                        [cell addSubview:imageView];
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
                cell.textLabel.text = [_publicModel.unit[@"val"] isEqualToString:MathUnitTon] ? @"购买量(吨)" : @"购买量(立方)";
                if ([_publicModel.type integerValue] == BussinessTypeSell) {
                cell.textLabel.text = [_publicModel.unit[@"val"] isEqualToString:MathUnitTon] ? @"销售量(吨)" : @"销售量(立方)";
                }
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_publicModel.totalnum floatValue]];
            }else {
                cell.textLabel.text = @"到港单价:(元/吨)";
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_publicModel.price floatValue]];
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
                    cell.textLabel.text = @"交货地址指定方式";
                    cell.detailTextLabel.text = _publicModel.addresstype[DataTextKey];
                }
                    break;
                case 2:
                {
                    cell.textLabel.text = @"交易地域";
                    cell.detailTextLabel.text = _publicModel.areaFullName;
                }
                    break;
                case 3:
                {
                    cell.textLabel.text = @"详细交货地址";
                }
                    break;
                case 4:
                {
                    cell.textLabel.textColor = [UIColor blackColor];
                    cell.textLabel.text = [NSString stringWithFormat:@"%@%@",_publicModel.addrAreaFullName,_publicModel.address];
                }
                    break;
                case 5:
                {
                    if (_publicModel.addressImgModels.count) {
                        int j = 0;
                        for (AddressImgModel *model in _publicModel.addressImgModels) {
                            LoadImageView *imageView = [[LoadImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80) bigImageUrl:model.url];
                            [imageView sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] placeholderImage:[UIImage imageNamed:PlaceHodelImageName]];
                            [cell addSubview:imageView];
                            j++;
                        }
                    }else {
                        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(15, 10, 260/3, 80)];
                        imageView.image = [UIImage imageNamed:PlaceHodelImageName];
                        [cell addSubview:imageView];
                    }
                }
                    break;
                case 6:
                {
                    cell.textLabel.text = cell_address_depth;
                    cell.detailTextLabel.text = [_publicModel.deep stringValue];
                }
                    break;
                case 7:
                {
                    cell.textLabel.text = cell_boat_tun;
                    cell.detailTextLabel.text = [_publicModel.shippington stringValue];
                }
                    break;
                case 8:
                {
                    cell.textLabel.text = _publicModel.type.integerValue == BussinessTypeSell ?  @"出售备注" :  @"购买备注";
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
            NSArray *titles = @[@"买家信息",@"用户性质",@"用户真实信息认证",@"用户交易保证金状态",@"交易满意度",@"交易诚信度",@"交易成功率",@"查看对方的交易历史评价",];
            cell.textLabel.text = titles[indexPath.row];
            if (indexPath.row == 0) {
                if ([_publicModel.type integerValue] == 2) {
                    cell.textLabel.text = @"卖家信息";
                }
            }else if (indexPath.row == 1) {
                NSString *txt = _publicModel.ctype[DataTextKey];
                cell.detailTextLabel.text = txt.length ? txt : @"未知";
            }else if (indexPath.row == 2) {
                cell.detailTextLabel.text = _publicModel.authstatus[DataTextKey];
            }else if (indexPath.row == 3) {
                cell.detailTextLabel.text = _publicModel.bailstatus[DataTextKey];
            }else if (indexPath.row == 4) {
                TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(cell.vwidth-130, 8.5, 120, 30)];
                ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
                ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
                ratingView.rating = [_publicModel.evalutModel.averageEvaluation floatValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 5) {
                TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(cell.vwidth-130, 8.5, 120, 30)];
                ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
                ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
                ratingView.rating = [_publicModel.evalutModel.averageCredit floatValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 6) {
                float rate = [_publicModel.evalutModel.transactionSuccessRate floatValue] * 100;
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f%%",rate];
                cell.detailTextLabel.textColor = [UIColor orangeColor];
            }else if (indexPath.row == 7) {
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                cell.textLabel.textColor = [UIColor orangeColor];
                cell.selectionStyle = UITableViewCellSelectionStyleGray;
            }
        }
            break;
            
        default:
            break;
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 4 && indexPath.row == 7) {
        CommentViewController *vc = [[CommentViewController alloc] init];
        vc.cid = _publicModel.cid;
        [self.navigationController pushViewController:vc animated:YES];
    }
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

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == cancleAlertViewTag) {
        if (buttonIndex) { // 确认取消发布
            __block typeof(self) this = self;
            NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObject:_orderId forKey:@"fid"];
            [self requestWithURL:bCancelOrder params:dic HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                [this handleAction:NO];
            } failedBlock:^(ASIHTTPRequest *req){

            }];
        }
    }else if (alertView.tag == deleteAlertViewTag) {
        if (buttonIndex) { // 确认删除发布信息
            __block typeof(self) this = self;
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_publicModel.id forKey:@"fid"];
            [self showHUD];
            [self requestWithURL:bDeleteOrder params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                [this handleAction:YES];
            } failedBlock:^(ASIHTTPRequest *request) {

            }];
        }
    }
}

/**
 *@brief 取消发布成功后，通知刷新并返回“我的供求”页面
 */
- (void)handleAction:(BOOL)isDelete {
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMySupplyNotification object:nil];
    
    TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
    vc.operationType = isDelete ? tip_delete_public : tip_cancel_public_success;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
