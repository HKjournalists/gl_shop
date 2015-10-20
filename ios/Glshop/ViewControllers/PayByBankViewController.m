//
//  PayByBankViewController.m
//  Glshop
//
//  Created by Appabc on 15/3/12.
//  Copyright (c) 2015年 appabc. All rights reserved.
//


#import "PayByBankViewController.h"
#import "HLCheckbox.h"
#import "ChargeViewController.h"
#import "WebViewController.h"
#import "IQKeyboardManager.h"

@interface PayByBankViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIActionSheetDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, assign) NSInteger dataSource;

@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UITextField *fieldTun;
@property (nonatomic, strong) UITextField *tfmoney;

@property (nonatomic, strong) UIView *checkView;

@property (nonatomic, assign) UserType type;

/**
 *@brief 充值金额
 */
@property (nonatomic, assign) NSInteger chargeAmount;

@end

@implementation PayByBankViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    UserInstance *userInstance = [UserInstance sharedInstance];
    BOOL isAuth;
    if ([userInstance.user.authstatus[DataValueKey] integerValue] == 1) {
        isAuth = YES;
    }else {
        isAuth = NO;
    }
    _dataSource = 4;
    _type = unKnowType;
    self.title = @"充值";
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.vtop += 20;
    _tableView.vheight = 44*3+170;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.scrollEnabled = NO;
    [self.view addSubview:_tableView];
    
    
    
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 140)];
    footerView.backgroundColor = [UIColor clearColor];
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 120) tipTitle:nil];
    UILabel *label1 = [[UILabel alloc] initWithFrame:CGRectMake(10, 40, 280, 20)];
    label1.font = [UIFont systemFontOfSize:FONT_16];
    label1.numberOfLines = 0;
    
    NSString * htmlString = @"<font>请您向以上平台账号转账<font color='red'>%.2f</font>元充值您的保证金/货款账户，在转账备注信息中填写您的手机号码/公司名称/汇款人姓名其中一项即可），转账后1—2个工作日平台在确认后自动更新您的保证金/货款账户，敬请留意。</font>";
    htmlString = [NSString stringWithFormat:htmlString,_chargeMoney];
    NSAttributedString * attrStr = [[NSAttributedString alloc] initWithData:[htmlString dataUsingEncoding:NSUnicodeStringEncoding] options:@{ NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType } documentAttributes:nil error:nil];
    
    label1.attributedText = attrStr;
    
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [footerView addSubview:tipView];
    _tableView.tableFooterView = footerView;
}

#pragma mark - UITableView DataSource/Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _dataSource;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont boldSystemFontOfSize:FONT_14];
    cell.detailTextLabel.font = [UIFont boldSystemFontOfSize:FONT_14];
    if (indexPath.row) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    if (indexPath.row == 0) {
        cell.textLabel.text = pay_offline_bankaddres_k;
        cell.detailTextLabel.textColor = [UIColor grayColor];
        cell.detailTextLabel.text = pay_offline_bankaddres_v;
        
    }else if (indexPath.row == 1) {
        cell.textLabel.text = pay_offline_bankname_k;
        cell.detailTextLabel.textColor = [UIColor grayColor];
        cell.detailTextLabel.text = pay_offline_bankname_v;
        
    }else if (indexPath.row == 2) {
        cell.textLabel.text = pay_offline_bank_k;
        cell.detailTextLabel.textColor = [UIColor grayColor];
        cell.detailTextLabel.text = pay_offline_bank_v;
    }else if (indexPath.row == 3) {
        cell.textLabel.text = pay_offline_tel_k;
        cell.detailTextLabel.textColor = [UIColor grayColor];
        cell.detailTextLabel.text = pay_offline_tel_v;
    }
    
    return cell;
}
@end

