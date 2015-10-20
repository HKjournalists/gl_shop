//
//  GatherDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "GatherDetailViewController.h"
#import "GatherModel.h"
#import "HLCheckbox.h"
#import "HLEHudView.h"

@interface GatherDetailViewController () <UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) UITableView *detailTableview;
@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) HLEHudView *hudProgress;

@end

@implementation GatherDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.title = @"收款人详情";
}

- (void)loadSubViews {
    _detailTableview = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _detailTableview.dataSource = self;
    _detailTableview.delegate = self;
    _detailTableview.scrollEnabled = NO;
    [self.view addSubview:_detailTableview];
    
    UserInstance *uIns = [UserInstance sharedInstance];
    NSInteger i = _gather.isAutush ? 6 : 5;
    i = uIns.userType == user_personal ? i-1 : i;
    [_detailTableview makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view);
        make.width.mas_equalTo(self.view);
        make.top.mas_equalTo(self.view).offset(15);
        make.height.mas_equalTo(43.9*i);
    }];
    
    if ([_gather.authstatus[DataValueKey] integerValue] == 2) {
        return;
    }
    UIButton *contractBtn = [UIFactory createBtn:YelloCommnBtnImgName bTitle:@"删除该收款人" bframe:CGRectZero];
    [contractBtn addTarget:self action:@selector(deleteGather) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:contractBtn];
    
    [contractBtn makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo(150);
        make.centerX.mas_equalTo(self.view);
        make.height.mas_equalTo(30);
        make.top.mas_equalTo(_detailTableview.bottom).offset(20);
    }];
    
    if ([_gather.authstatus[DataValueKey] integerValue] == 0) {
        return;
    }
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(doneAction)];
    self.navigationItem.rightBarButtonItem = rightItem;
    self.navigationItem.rightBarButtonItem.enabled = NO;

}

#pragma mark - Setter
- (void)setGather:(GatherModel *)gather {
    if (_gather != gather) {
        _gather = gather;
        
    }
}

- (NSString *)bankName {
    NSArray *banks = [SynacObject banksData];
    for (BankModel *model in banks) {
        if ([model.val isEqualToString:_gather.banktype]) {
            return model.name;
        }
    }
    return nil;
}


#pragma mark - UITableView
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    UserInstance *uIns = [UserInstance sharedInstance];
    NSInteger i = _gather.isAutush ? 6 : 5;
    i = uIns.userType == user_personal ? i-1 : i;
    
    return i;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = UFONT_16_B;
    cell.textLabel.textColor = C_GRAY;
    cell.detailTextLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = UFONT_16_B;
    NSArray *texts = @[@"提款人姓名",@"开户银行",@"支行名称",@"银行卡号",@"证明材料",@"no",];
    cell.textLabel.text = texts[indexPath.row];
    
    UserInstance *uIns = [UserInstance sharedInstance];
    
    switch (indexPath.row) {
        case 0:
        {
            cell.detailTextLabel.text = _gather.carduser;
        }
            break;
        case 1:
        {
            cell.detailTextLabel.text = [self bankName];
        }
            break;
        case 2:
        {
            cell.detailTextLabel.text = _gather.bankname;
        }
            break;
        case 3:
        {
            cell.detailTextLabel.text = _gather.bankcard;
        }
            break;
        case 4:
        {
            if (uIns.userType == user_personal && _gather.isAutush) {
                    cell.textLabel.text = nil;
                    _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
                    _box.frame = CGRectMake(15, cell.vheight/2-10, 20, 20);
                    __block typeof(self) this = self;
                    _box.tapBlock = ^(BOOL selected) {
                        this.navigationItem.rightBarButtonItem.enabled = this.box.selected ? YES : NO;
                    };
                    [cell.contentView addSubview:_box];
                    
                    self.agreeLabel = [UILabel labelWithTitle:@"设置为默认收款人"];
                    _agreeLabel.font = [UIFont systemFontOfSize:14.5f];
                    _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 200, _box.vheight);
                    [cell.contentView addSubview:_agreeLabel];
            }else {
                UIButton *btn = [UIButton buttonWithTip:@"查看" target:self selector:@selector(checkImg:)];
                btn.frame = CGRectMake(cell.vright-60, 22-35/2.0, 50, 35);
                [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
                [cell addSubview:btn];
            }
        }
            break;
        case 5:
        {
            cell.textLabel.text = nil;
            _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
            _box.frame = CGRectMake(15, cell.vheight/2-10, 20, 20);
            __block typeof(self) this = self;
            _box.tapBlock = ^(BOOL selected) {
                this.navigationItem.rightBarButtonItem.enabled = this.box.selected ? YES : NO;
            };
            [cell.contentView addSubview:_box];
            
            self.agreeLabel = [UILabel labelWithTitle:@"设置为默认收款人"];
            _agreeLabel.font = [UIFont systemFontOfSize:14.5f];
            _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 200, _box.vheight);
            [cell.contentView addSubview:_agreeLabel];
        }
            break;
            
        default:
            break;
    }
    
    return cell;
}

#pragma mark - UIActions 
- (void)checkImg:(UIButton *)btn {
    if (!_hudProgress) {
        _hudProgress = [[HLEHudView alloc] initWithTagView:btn];
        _hudProgress.imgUrl = self.gather.imgModel.url;
    }
    [_hudProgress showProgressing];
}

- (void)doneAction {
     NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_gather.gatherId,@"id", nil];
    [self showHUD:@"正在保存..." isDim:NO Yoffset:0];
    __block typeof(self) this = self;
    [self requestWithURL:bcopnacceptsetDefault params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushGatherListNotification object:nil];        
        [this.navigationController popViewControllerAnimated:YES];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
    
}

- (void)deleteGather {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"删除收款人" message:@"您确定要删除此收款人吗？" delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    [alert show];
}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex) {
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_gather.gatherId,@"id", nil];
        __block typeof(self) this = self;
        [self showHUD:@"正在删除..." isDim:NO Yoffset:0];
        [self requestWithURL:bcopnacceptdel params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            HUD(@"删除成功");
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushGatherListNotification object:nil];
            [this.navigationController popViewControllerAnimated:YES];
        } failedBlock:^(ASIHTTPRequest *request) {
            
        }];
    }
}

@end
