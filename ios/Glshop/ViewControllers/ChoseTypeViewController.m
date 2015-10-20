//
//  ChoseTypeViewController.m
//  Glshop
//
//  Created by River on 14-12-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ChoseTypeViewController.h"
#import "CompanyAuthViewController.h"
#import "JSONKit.h"

static NSString *listResueIdentify = @"listCell";

@interface ChoseTypeViewController () <UITableViewDataSource,UITableViewDelegate>

@property (strong, nonatomic) UITableView *listView;
@property (strong, nonatomic) NSArray *datas;
@property (nonatomic, assign) NSInteger lastSeleced;

@end

@implementation ChoseTypeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self sureSelecedMark];
    
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:globe_cancel_str style:UIBarButtonItemStylePlain target:self action:@selector(cancelPresent)];
    self.navigationItem.leftBarButtonItem = item;

    _listView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _listView.dataSource = self;
    _listView.delegate = self;
    _listView.vheight -= kTopBarHeight;
    [_listView registerClass:[UITableViewCell class] forCellReuseIdentifier:listResueIdentify];
    [self.view addSubview:_listView];
}

#pragma mark - Private
/**
 *@brief 确定用户选择的产品索引值，用来标记用户已经选中的产品
 */
- (void)sureSelecedMark {
    SynacInstance *synac = [SynacInstance sharedInstance];
    NSString *name,*controllerTitle;
    if (_productType == sendsSubType) { // 黄砂二级子类
        controllerTitle = @"选择分类";
        name = _publicModel.ptype;
        _lastSeleced = [[synac sendSubType] indexOfObject:[synac goodsModelForPtype:_publicModel.ptype]];
    }else if (_productType == sendsGroundsonType) { // 黄砂三级子类
        controllerTitle = @"选择规格";
        name = _publicModel.pid;
        _lastSeleced = [[synac sendsGroundSonProductType:_publicModel.ptype] indexOfObject:[synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid]];
    }else if (_productType == stoneType) {
        controllerTitle = @"选择规格";
        name = _publicModel.pid;
        _lastSeleced = [[synac stoneSubType] indexOfObject: [synac goodsChildStone:_publicModel.pid]];
    }
    if ([name isEqualToString:UnKnow] || _lastSeleced == NSNotFound) {
        _lastSeleced = 0;
    }
    self.title = controllerTitle;
}

/**
 *@brief 获得发布信息视图控制器
 */
- (CompanyAuthViewController *)publicInfoVC {
    UINavigationController *nav = (UINavigationController *)self.presentingViewController;
    for (UIViewController *vc in nav.viewControllers) {
        if ([vc isKindOfClass:[CompanyAuthViewController class]]) {
            return (CompanyAuthViewController *)vc;
        }
    }
    return nil;
}


#pragma mark - UIActions
- (void)cancelPresent {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)doneAction {

    CompanyAuthViewController *vc = [self publicInfoVC];

    [self dismissViewControllerAnimated:YES completion:^{
        switch (_productType) {
            case sendsSubType:
            {
                GoodsModel *model;
                if (_lastSeleced != NSNotFound) {
                    model = _datas[_lastSeleced];
                }
                _publicModel.ptype = model.goodsVal;
            }
                break;
            case sendsGroundsonType:
            {
                GoodChildModel *model;
                if (_lastSeleced != NSNotFound) {
                    model = _datas[_lastSeleced];
                }
                _publicModel.pid = model.goodChildId;
            
            }
                break;
            case stoneType:
            {
                GoodChildModel *model;
                if (_lastSeleced != NSNotFound) {
                    model = _datas[_lastSeleced];
                }
                _publicModel.pid = model.goodChildId;
            }
                break;
            default:
                break;
        }

        [vc.tableView reloadData];
    }];
}

#pragma mark - Setters
- (void)setProductType:(ProductType)productType {
    _productType = productType;
    
    SynacInstance *synac = [SynacInstance sharedInstance];
    if (_productType == sendsSubType) {
        self.datas = synac.sendSubType;
    }else if (_productType == sendsGroundsonType) {
        self.datas = [synac sendsGroundSonProductType:_ptype];
    }else if (_productType == stoneType) {
        self.datas = synac.stoneSubType;
    }
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.datas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:listResueIdentify];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = UFONT_16;
    if (_productType == stoneType) {
        GoodChildModel *model = self.datas[indexPath.row];
        cell.textLabel.text = [model combineNameWithUnit];
    }else if (_productType == sendsSubType) {
        GoodsModel *model = self.datas[indexPath.row];
        cell.textLabel.text = model.goodsName;
    }else {
        NSArray *source = [[SynacInstance sharedInstance] sendsGroundSonProductTypeName:_ptype];
        cell.textLabel.text = source[indexPath.row];
    }
    
    if (indexPath.row == _lastSeleced) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    cell.accessoryType = UITableViewCellAccessoryCheckmark;

    if (indexPath.row != _lastSeleced) {
        UITableViewCell *prvCell = [tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:_lastSeleced inSection:0]];
        prvCell.accessoryType = UITableViewCellAccessoryNone;
    }
    
    if (_productType == stoneType) {
        GoodChildModel *model = self.datas[indexPath.row];
        _publicModel.pid = model.goodChildId;
    }else if (_productType == sendsSubType) {

    }else {
        NSArray *source = [[SynacInstance sharedInstance] sendsGroundSonProductType:_ptype];
        GoodChildModel *model = source[indexPath.row];
        _publicModel.pid = model.goodChildId;
    }
    
    [self doneAction];
    
    _lastSeleced = indexPath.row;
}

@end
