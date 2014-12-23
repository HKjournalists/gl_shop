//
//  DetailStandardViewController.m
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "DetailStandardViewController.h"
#import "CompanyAuthViewController.h"
#import "JSONKit.h"

static NSString *listDetailResueIdentify = @"detailstandardCell";

@interface DetailStandardViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *datas;
@property (nonatomic, strong) NSMutableArray *textFields;

@end

@implementation DetailStandardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:@"取消" style:UIBarButtonItemStylePlain target:self action:@selector(cancelPresent)];
    self.navigationItem.leftBarButtonItem = item;
    
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(doneAction)];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.vheight -= kTopBarHeight;
    [self.view addSubview:_tableView];
    
    UIView *view = UIView.new;
    view.backgroundColor = [UIColor clearColor];
    _tableView.tableFooterView = view;
}

- (void)initDatas {
    self.title = @"详细规格";
    _textFields = [NSMutableArray array];
}

- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    SynacInstance *synac = [SynacInstance sharedInstance];
    if ([_publicModel.ptype isEqualToString:UnKnow] ) {  // 石子的详细规格
        self.datas = [synac goodsChildStone:_publicModel.pid].propretyArray;
        _publicModel.productPropertys = [[synac goodsChildStone:_publicModel.pid].propreDicArray JSONString];
    }else { // 黄砂的详细规格
        self.datas = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propretyArray;
        _publicModel.productPropertys = [[synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propreDicArray JSONString];
    }
}

#pragma mark - UIActions
- (void)cancelPresent {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)doneAction {
    for (UITextField *field in _textFields) {
        if (field.text.length == 0) {
            [self showTip:@"请将信息填写完整！"];
            return;
        }
    }
    
    NSMutableArray *temp = [NSMutableArray array];
    for (UITextField *field in _textFields) {
        [temp addObject:field.text];
    }
    _publicModel.detailstandards = [NSArray arrayWithArray:temp];
    
    SynacInstance *synac = [SynacInstance sharedInstance];
    int i = 0;
    NSMutableArray *temp1 = [NSMutableArray array];
    NSMutableArray *temp2 = [NSMutableArray array];
    if ([_publicModel.ptype isEqualToString:UnKnow] ) {
        for (NSMutableDictionary *mdic in [synac goodsChildStone:_publicModel.pid].propreDicArray) {
            UITextField *field = _textFields[i];
            [mdic addString:field.text forKey:@"content"];
            [temp1 addObject:mdic];
            i++;
        }
    }else {
        for (NSMutableDictionary *mdic in [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propreDicArray) {
            UITextField *field = _textFields[i];
            [mdic addString:field.text forKey:@"content"];
            [temp2 addObject:mdic];
            i++;
        }
    }
    if ([_publicModel.pid isEqualToString:UnKnow]) {
        _publicModel.productPropertys = [temp1 JSONString];
    }else {
        _publicModel.productPropertys = [temp2 JSONString];
    }
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.datas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:listDetailResueIdentify];

    if (!cell) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:listDetailResueIdentify];
        
        UITextField *field = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
        field.frame = CGRectMake(cell.vwidth-155, 44/2.0-35/2.0, 150, 35);
        field.textAlignment = NSTextAlignmentRight;
        field.tag = 100 + indexPath.row;
        field.clearButtonMode = UITextFieldViewModeNever;
        field.keyboardType = UIKeyboardTypeNumberPad;
        [cell.contentView addSubview:field];
        
        if (![_textFields containsObject:field]) {
            [_textFields addObject:field];
        }
    }
    
    UITextField *field = (UITextField *)[cell viewWithTag:indexPath.row+100];
    ProModel *model  = _datas[indexPath.row];
    cell.textLabel.text = [model.name stringByAppendingString:@"(%)"];
    field.text = model.content;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {

}


@end
