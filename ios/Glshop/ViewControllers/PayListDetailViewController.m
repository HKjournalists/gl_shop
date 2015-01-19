//
//  PayListDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-12.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListDetailViewController.h"
#import "PayListModel.h"

@interface PayListDetailViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *detailTableview;
@property (nonatomic, strong) PayListModel *payModel;

@end

@implementation PayListDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
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
    
    UIButton *contractBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"查看相关合同" bframe:CGRectZero];
    [self.view addSubview:contractBtn];
    
    [contractBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.top.mas_equalTo(_detailTableview.bottom).offset(20);
    }];
}

- (void)requestNet {
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
    [_detailTableview reloadData];
}

#pragma mark - UITableView
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 6;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    cell.textLabel.textColor = [UIColor grayColor];
    cell.detailTextLabel.font = [UIFont systemFontOfSize:16.f];
    cell.detailTextLabel.textColor = [UIColor blackColor];
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
            
            if ([_payModel.direction[DataValueKey] integerValue] == 1) { // 流出
                cell.detailTextLabel.text = [NSString stringWithFormat:@"-%@",_payModel.amount];
                cell.detailTextLabel.textColor = RGB(255, 102, 0, 1);
            }else {
                cell.detailTextLabel.text = [NSString stringWithFormat:@"+%@",_payModel.amount];
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
            cell.detailTextLabel.text = [_payModel.balance stringValue];
            cell.detailTextLabel.textColor = RGB(241, 0, 0, 1);
        }
            break;
            
        default:
            break;
    }
    
    return cell;
}


@end
