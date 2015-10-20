//
//  PayListDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-12.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListDetailViewController.h"
#import "ContractProcessDetailViewController.h"
#import "PayListModel.h"

@interface PayListDetailViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *detailTableview;
@property (nonatomic, strong) PayListModel *payModel;
@property (nonatomic, strong) UIButton *contractBtn;

@end

@implementation PayListDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.shouldShowFailView = YES;
    [self requestNet];
}

- (void)loadSubViews {
    _detailTableview = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _detailTableview.dataSource = self;
    _detailTableview.delegate = self;
    [self.view addSubview:_detailTableview];
    
    [_detailTableview makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view);
        make.width.mas_equalTo(self.view);
        make.top.mas_equalTo(self.view).offset(15);
        make.height.mas_equalTo(44*6);
    }];
    _detailTableview.hidden = YES;
    
    
    _contractBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"查看相关合同" bframe:CGRectZero];
    [_contractBtn addTarget:self action:@selector(goContacts:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_contractBtn];
    
    [_contractBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.top.mas_equalTo(_detailTableview.bottom).offset(20);
    }];
    
    [_contractBtn setHidden:YES];
}

- (void)requestNet {
    [super requestNet];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params addString:_payId forKey:@"PID"];
    __block typeof(self) this = self;
    [self requestWithURL:bgetPayRecordDetail params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSDictionary *dic = responseData[ServiceDataKey];
    _payModel = [[PayListModel alloc] initWithDataDic:dic];
    if(_payModel.oid != NULL){
        [_contractBtn setHidden:NO];
    }
    _detailTableview.hidden = NO;
    [_detailTableview reloadData];
}

#pragma mark - UITableView
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 6;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = UFONT_16;
    cell.textLabel.textColor = C_GRAY;
    cell.detailTextLabel.font = UFONT_16;
    cell.detailTextLabel.textColor = C_BLACK;
    NSArray *texts = @[@"流水账号",@"类型",@"收入金额(单位:元)",@"支付方式",@"时间",@"余额(单位:元)",];
    cell.textLabel.text = texts[indexPath.row];
    
    switch (indexPath.row) {
        case 0:
        {
            cell.detailTextLabel.text = _payModel.passid;
        }
            break;
        case 1:
        {
            cell.detailTextLabel.text = _payModel.otype[DataTextKey];
        }
            break;
        case 2:
        {
            cell.detailTextLabel.text = [_payModel.amount stringValue];
            float aomus =[_payModel.amount floatValue];
            NSString *money = [Utilits formatMoney:aomus isUnit:false];
            
            if ([_payModel.direction[DataValueKey] integerValue] == 1) { // 流出
                cell.detailTextLabel.text = [NSString stringWithFormat:@"-%@", money];
                cell.detailTextLabel.textColor = RGB(255, 102, 0, 1);
            }else {
                cell.detailTextLabel.text = [NSString stringWithFormat:@"+%@",money];
                cell.detailTextLabel.textColor = RGB(68, 153, 0, 1);
            }
        }
            break;
        case 3:
        {
            cell.detailTextLabel.text = _payModel.paytype[DataTextKey];
        }
            break;
        case 4:
        {
            cell.detailTextLabel.text = _payModel.paytime;
        }
            break;
        case 5:
        {
            double aomus =[_payModel.balance doubleValue];
            NSString *money = [Utilits formatMoney:aomus isUnit:false];
            cell.detailTextLabel.text = money;
            cell.detailTextLabel.textColor = RGB(241, 0, 0, 1);
        }
            break;
            
        default:
            break;
    }
    
    return cell;
}
- (void)goContacts:(UIButton *)btn {
    ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
    vc.contractId = _payModel.oid;
    [self.navigationController pushViewController:vc animated:YES];
}
@end
