//
//  SecondGatherViewController.m
//  Glshop
//
//  Created by River on 15-1-13.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "SecondGatherViewController.h"
#import "BankListViewController.h"
#import "RolloutViewController.h"
#import "MangerGathersViewController.h"

@interface SecondGatherViewController () <UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UITextField *subbranchtf;
@property (nonatomic, strong) UITextField *cardNumtf;
@property (nonatomic, strong) UITextField *codeAuthtf;

@property (nonatomic, strong) UIButton *btnVerifCode;

@end

@implementation SecondGatherViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"新增收款人";
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.scrollEnabled = NO;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.delegate = self;
    [self.view addSubview:_tableView];
    
    [_tableView makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view);
        make.width.mas_equalTo(self.view);
        make.top.mas_equalTo(self.view).offset(-15);
        make.height.greaterThanOrEqualTo(@370);
    }];
    
    UIButton *contractBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"提交" bframe:CGRectZero];
    [self.view addSubview:contractBtn];
    [contractBtn addTarget:self action:@selector(post) forControlEvents:UIControlEventTouchUpInside];
    [contractBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.top.mas_equalTo(_tableView.bottom).offset(5);
    }];
}

#pragma mark - UITableView

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 4 : 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    
    if (indexPath.section == 0) {
        NSArray *titles = @[@"添加银行卡号",@"选择开户行",@"支行名称",@"银行卡号",];
        cell.textLabel.text = titles[indexPath.row];
        if (indexPath.row == 0) {
            cell.textLabel.textColor = [UIColor grayColor];
        }else if (indexPath.row == 1) {
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            cell.detailTextLabel.text = _selectBank ? _selectBank : @"选择";
        }else if (indexPath.row == 2) {
            if (_subbranchtf) {
                [cell addSubview:_subbranchtf];
            }else {
                _subbranchtf = [UITextField textFieldWithPlaceHodler:@"选填" withDelegate:self];
                _subbranchtf.textColor = [UIColor grayColor];
                _subbranchtf.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:_subbranchtf];
                [_subbranchtf makeConstraints:^(MASConstraintMaker *make) {
                    make.right.mas_equalTo(cell.contentView).offset(-10);
                    make.leading.mas_equalTo(cell.textLabel.right);
                    make.height.mas_equalTo(44);
                    make.top.mas_equalTo(cell.contentView.top);
                }];
            }
    
        }else if (indexPath.row == 3) {
            _cardNumtf = [UITextField textFieldWithPlaceHodler:@"请输入银行卡号" withDelegate:self];
            _cardNumtf.textAlignment = NSTextAlignmentRight;
            _cardNumtf.keyboardType = UIKeyboardTypeNumberPad;
            _cardNumtf.textColor = [UIColor grayColor];
            [cell.contentView addSubview:_cardNumtf];
            [_cardNumtf makeConstraints:^(MASConstraintMaker *make) {
                make.right.mas_equalTo(cell.contentView).offset(-10);
                make.leading.mas_equalTo(cell.textLabel.right);
                make.height.mas_equalTo(44);
                make.top.mas_equalTo(cell.contentView.top);
            }];
        }

        
    }else {
        if (indexPath.row == 0) {
            cell.textLabel.textColor = [UIColor grayColor];
            cell.textLabel.text = @"短信验证";
        }else if (indexPath.row == 1) {
            cell.textLabel.text = @"验证码发送至:";
            UserInstance *uins = [UserInstance sharedInstance];
            UILabel *phoneLab = [UILabel labelWithTitle:uins.user.username];
            phoneLab.textColor = RGB(40, 113, 213, 1);
            [cell addSubview:phoneLab];
            [phoneLab makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell.textLabel.right);
                make.width.mas_equalTo(200);
                make.top.mas_equalTo(cell.contentView);
                make.height.mas_equalTo(44);
            }];
            
        }else if (indexPath.row == 2) {
            _codeAuthtf = [UITextField textFieldWithPlaceHodler:@"请输入验证码" withDelegate:self];
            _codeAuthtf.textColor = [UIColor grayColor];
            _codeAuthtf.keyboardType = UIKeyboardTypeNumberPad;
            [cell.contentView addSubview:_codeAuthtf];
            [_codeAuthtf makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell).offset(15);
                make.width.greaterThanOrEqualTo(@200);
                make.height.mas_equalTo(kCellDefaultHeight);
                make.top.mas_equalTo(cell.contentView.top);
            }];
            
            _btnVerifCode = [UIFactory createBtn:@"Buy_sell_publish" bTitle:@"获取验证码" bframe:CGRectZero];
            [_btnVerifCode addTarget:self action:@selector(getCode:) forControlEvents:UIControlEventTouchUpInside];
            [_btnVerifCode setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
            _btnVerifCode.titleLabel.font = [UIFont boldSystemFontOfSize:15.f];
            [cell.contentView addSubview:_btnVerifCode];
            [_btnVerifCode makeConstraints:^(MASConstraintMaker *make) {
                make.width.mas_equalTo(100);
                make.right.mas_equalTo(cell.right).offset(-8);
                make.height.mas_equalTo(30);
                make.top.mas_equalTo(cell).offset(7);
                
            }];
        }
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        if (indexPath.row == 1) {
            BankListViewController *vc = [[BankListViewController alloc] init];
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
}



#pragma mark - UIAcions
- (void)getCode:(UIButton *)btn {
    [self _requesSmsCode];
    btn.enabled = NO;
}

/**
 *@brief 提交
 */
- (void)post {
    
    if (!_cardNumtf.text.length) {
        HUD(@"请输入银行卡号");
        return;
    }
    
    if (!_codeAuthtf.text.length) {
        HUD(@"请输入验证码");
        return;
    }
    UserInstance *usIns = [UserInstance sharedInstance];
    NSString *cid = usIns.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:cid,@"cid",_cardNumtf.text,@"bankcard",self.name,@"carduser",@"BANK001",@"banktype",_codeAuthtf.text,@"code",self.imgId,@"imgid", nil];
    if (self.subbranchtf.text) {
        [params addString:_subbranchtf.text forKey:@"bankname"];
    }
    
    __block typeof(self) this = self;
    [self requestWithURL:bAccetpAuthApply params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushGatherListNotification object:nil];
    UIViewController *vc = [self targetvc];
    [self.navigationController popToViewController:vc animated:YES];
}

#pragma mark - Private

- (void)_requesSmsCode {
    UserInstance *uins = [UserInstance sharedInstance];
    NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:uins.user.username,@"phone", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bMessageSendPath
                  params:param
              HTTPMethod:kHttpGetMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [this _setTime];
    } failedBlock:^(ASIHTTPRequest *request) {
        HUD(kNetError);
        this.btnVerifCode.enabled = YES;
    }];
}

/**
 *@brief 倒计时
 */
-(void)_setTime
{
    _btnVerifCode.enabled=NO;
    __block int timeout=60; //倒计时时间
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1.0*NSEC_PER_SEC, 0); //每秒执行
    dispatch_source_set_event_handler(_timer, ^{
        if(timeout<=0){ //倒计时结束，关闭
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                _btnVerifCode.userInteractionEnabled=YES;
                [_btnVerifCode setTitle:@"重发验证码" forState:UIControlStateNormal];
                _btnVerifCode.enabled=YES;
            });
        }else{
            NSString *strTime = [NSString stringWithFormat:@"%ds后重发",timeout];
            dispatch_async(dispatch_get_main_queue(), ^{
                _btnVerifCode.enabled=YES;
                [_btnVerifCode setTitle:strTime forState:UIControlStateNormal];
                _btnVerifCode.enabled=NO;
            });
            timeout--;
        }
    });
    dispatch_resume(_timer);
}

- (UIViewController *)targetvc {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[MangerGathersViewController class]]) {
            return (MangerGathersViewController *)vc;
        }else if ([vc isKindOfClass:[RolloutViewController class]]) {
            return (RolloutViewController *)vc;
        }
    }
    return nil;
}

#pragma mark - UITextField Delegate
-(BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    
    if (textField == _cardNumtf) {
        NSString *text = [textField text];
        
        NSCharacterSet *characterSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789\b"];
        string = [string stringByReplacingOccurrencesOfString:@" " withString:@""];
        if ([string rangeOfCharacterFromSet:[characterSet invertedSet]].location != NSNotFound) {
            return NO;
        }
        
        text = [text stringByReplacingCharactersInRange:range withString:string];
        text = [text stringByReplacingOccurrencesOfString:@" " withString:@""];
        
        NSString *newString = @"";
        while (text.length > 0) {
            NSString *subString = [text substringToIndex:MIN(text.length, 4)];
            newString = [newString stringByAppendingString:subString];
            if (subString.length == 4) {
                newString = [newString stringByAppendingString:@" "];
            }
            text = [text substringFromIndex:MIN(text.length, 4)];
        }
        
        newString = [newString stringByTrimmingCharactersInSet:[characterSet invertedSet]];
        
        if (newString.length >= 20) {
            return NO;
        }
        
        [textField setText:newString];
        
        return NO;
    }else {
        
        return YES;
    }
    
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    DLog(@"%@",textField.text);
}

@end
