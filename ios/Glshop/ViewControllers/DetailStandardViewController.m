//
//  DetailStandardViewController.m
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "DetailStandardViewController.h"
#import "JSONKit.h"

static NSString *listDetailResueIdentify = @"detailstandardCell";

@interface DetailStandardViewController () <UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
/**
 *@brief 指定商品的详细规格数据 ProModel
 */
@property (nonatomic, strong) NSArray *datas;
/**
 *@brief 指定商品的详细规格数据深拷贝 ProModel
 */
@property (nonatomic, strong) NSMutableArray *datasCopy;
/**
 *@brief 用户填写了相关属性的对象 ProModel
 */
@property (nonatomic, strong) NSMutableArray *datasSetup;

@property (nonatomic, strong) NSMutableArray *textFields;

@end

@implementation DetailStandardViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:globe_cancel_str style:UIBarButtonItemStylePlain target:self action:@selector(cancelPresent)];
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
    if ([_publicModel.ptype isEqualToString:UnKnow] || !_publicModel.ptype) {  // 石子的详细规格
        self.datas = [synac goodsChildStone:_publicModel.pid].propretyArray;
    }else { // 黄砂的详细规格
        self.datas = [synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid].propretyArray;
    }
    
    // 拷贝数据
    NSMutableArray *temp = [NSMutableArray array];
    for (ProModel *model in _datas) {
        ProModel *modelCopy = [model mutableCopy];
        [temp addObject:modelCopy];
    }
    _datasCopy = [NSMutableArray arrayWithArray:temp];
    
    if (_publicModel.proList.count) { // 如果设置了属性，就用设置后的
        _datasSetup = [NSMutableArray arrayWithArray:_publicModel.proList];
    }else {
        _datasSetup = [NSMutableArray array];
    }
    
    if (!_publicModel.productDicArray) {
        _publicModel.productDicArray = [NSMutableArray array];
    }
    
}

/**
 *@brief 用户是否填写了数据
 */
- (BOOL)isUserWrite {
    for (UITextField *filed in _textFields) {
        if (filed.text.length) {
            return YES;
        }
    }
    return NO;
}

#pragma mark - UIActions
- (void)cancelPresent {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)doneAction {
    if (![self isUserWrite]) {
        [self showTip:@"请填写相关信息在保存"];
        return;
    }
    
    // 记录用户填写的数据
    [_datasSetup removeAllObjects];
    for (UITextField *field in _textFields) {
        if (field.text.length) {
            NSInteger index = field.tag-100;
            ProModel *model;
            if (_datasCopy.count > index) {
                model = _datasCopy[index];
                model.proContent = field.text;
                if (![_datasSetup containsObject:model]) {
                    [_datasSetup addObject:model];
                }
            }
        }
    }
    _publicModel.proList = [NSArray arrayWithArray:_datasSetup];
    
    
    NSMutableArray *temp = [NSMutableArray array];
    for (ProModel *pModel in _datasSetup) {
        NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObjectsAndKeys:pModel.proContent,@"content",pModel.proid,@"id", nil];
        [temp addObject:dic];
    }
    
    _publicModel.productPropertys = [temp JSONString];
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.datas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:listDetailResueIdentify];
    
    
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:listDetailResueIdentify];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
        WTReTextField *field = [[WTReTextField alloc] init];
        field.placeholder = @"填写";
        field.delegate = self;
        field.frame = CGRectMake(cell.vwidth-155, 44/2.0-35/2.0, 150, 35);
        field.textAlignment = NSTextAlignmentRight;
        field.tag = 100 + indexPath.row;
        field.clearButtonMode = UITextFieldViewModeNever;
        field.keyboardType = UIKeyboardTypeDecimalPad;
        field.pattern = @"^[0-9]+(.[0-9]{1,2})?$";
        [cell.contentView addSubview:field];
        
        if (![_textFields containsObject:field]) {
            [_textFields addObject:field];
        }
    }
    
    ProModel *model  = _datas[indexPath.row];
    cell.textLabel.text = model.combinePnameWithUnit;
    cell.textLabel.font = UFONT_16;
    
    // 填充数据
    for (ProModel *aModel in _datasSetup) {
        for (ProModel *norModel in _datas) {
            if ([norModel.proCode isEqualToString:aModel.proCode]) {
                NSInteger index = [_datas indexOfObject:norModel];
                if (index != NSNotFound) {
                    UITextField *field = (UITextField *)[cell viewWithTag:index+100];
                    field.text = aModel.proContent;
                }
            }
        }
    }

    return cell;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField.text.length == 0) {
        return;
    }
    
    NSString *regex = @"^[0-9]+([.]{0}|[.]{1}[0-9]+)$";
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", regex];
    if ([predicate evaluateWithObject:textField.text] == NO) {
        [self showTip:@"输入的字符非法"];
        textField.text = nil;
        return;
    }
    
}


@end
