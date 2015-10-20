//
//  FilerAreasView.m
//  FilerView
//
//  Created by River on 15-3-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerAreasView.h"
#import "AreaItemTableViewCell.h"
#import "AreaInstance.h"

NSString *const provinectFiler = @"省份";
NSString *const cityFiler = @"城市";
NSString *const areaFiler = @"区域";

static NSString *const reuse1 = @"table1FilerCell";
static NSString *const reuse2 = @"table2FilerCell";
static NSString *const reuse3 = @"table3FilerCell";

#define AreaInstanceObj [AreaInstance sharedInstance]

@implementation FilerAreasView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
        [self initlaizesDatas];
        
        _filerTitleView = [[FilerTitleView alloc] initWithFrame:CGRectMake(0, 0, self.vwidth, 61)];
        _filerTitleView.labe1.text = provinectFiler;
        _filerTitleView.label2.text = cityFiler;
        _filerTitleView.label3.text = areaFiler;
        _filerTitleView.delegate = self;
        BOOL isAll = YES;
        for (AreaModel *model in [AreaInstanceObj provinceAreas]) {
            if (model.selected) {
                isAll = NO;
                break;
            }
        }
        _filerTitleView.checkBox.selected = isAll;
        [self addSubview:_filerTitleView];
        
        _tableView1 = [[UITableView alloc] initWithFrame:CGRectMake(0, _filerTitleView.vbottom+5, 320.0/3, self.vheight-_filerTitleView.vbottom-10) style:UITableViewStylePlain];
        _tableView1.dataSource = self;
        _tableView1.delegate = self;
        _tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView1 registerNib:[UINib nibWithNibName:@"AreaItemTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [self addSubview:_tableView1];
        [_tableView1 selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition:UITableViewScrollPositionNone];
        
        _tableView2 = [[UITableView alloc] initWithFrame:CGRectMake(_tableView1.vright+1, _filerTitleView.vbottom+5, 320.0/3, _tableView1.vheight) style:UITableViewStylePlain];
        _tableView2.dataSource = self;
        _tableView2.delegate = self;
        _tableView2.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView2 registerNib:[UINib nibWithNibName:@"AreaItemTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [self addSubview:_tableView2];
        [_tableView2 selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition:UITableViewScrollPositionNone];
        
        _tableView3 = [[UITableView alloc] initWithFrame:CGRectMake(_tableView2.vright+1, _filerTitleView.vbottom+5, 320.0/3, _tableView1.vheight) style:UITableViewStylePlain];
        _tableView3.dataSource = self;
        _tableView3.delegate = self;
        _tableView3.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView3 registerNib:[UINib nibWithNibName:@"AreaItemTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [self addSubview:_tableView3];
        
    }
    return self;
}

- (void)initlaizesDatas {
    _table1SelectRow = 0;
    _table2SelectRow = 0;
    
    if (!_provinces) {
        _provinces = [NSMutableArray arrayWithArray:[AreaInstanceObj provinceAreas]];
    }
    
}

#pragma mark - UITableView Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    AreaInstance *areaInstance = [AreaInstance sharedInstance];
    if (tableView == _tableView1) {
        return _provinces.count;
    }else if (tableView == _tableView2) {
        NSArray *models = [areaInstance citysForProvinceId:[AreaInstanceObj provinceAreas][_table1SelectRow]];
        return models.count;
    }else {
        NSArray *models = [areaInstance citysForProvinceId:[AreaInstanceObj provinceAreas][_table1SelectRow]];
        AreaModel *model = [models safeObjAtIndex:_table2SelectRow];
        NSArray *areas = [areaInstance regionAreasForProvince:model];
        return areas.count;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 30;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    AreaItemTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuse1];
    AreaInstance *areaInstance = [AreaInstance sharedInstance];
    AreaModel *model;
    if (tableView == _tableView1) {
        model = _provinces[indexPath.row];
    }else if (tableView == _tableView2) {
        NSArray *models = [areaInstance citysForProvinceId:_provinces[_table1SelectRow]];
        model = models[indexPath.row];
    }else {
        NSArray *models = [areaInstance citysForProvinceId:_provinces[_table1SelectRow]];
        AreaModel *city = models[_table2SelectRow];
        NSArray *areas = [areaInstance regionAreasForProvince:city];
        model = areas[indexPath.row];
    }
    cell.nameLabel.text = model.areaName;
    cell.box.selected = model.isSelected;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [self tableView:tableView didSelectBoxRowAtIndexPath:indexPath];
}

#pragma mark - Public
- (void)resetFilerAddressData {
    for (AreaModel *model in [AreaInstanceObj provinceAreas]) {
        if (model.selected) {
            model.selected = NO;
            for (AreaModel *city in [AreaInstanceObj citysForProvinceId:model]) {
                if (city.selected) {
                    city.selected = NO;
                    for (AreaModel *region in [AreaInstanceObj regionAreasForProvince:city]) {
                        if (region.selected) {
                            region.selected = NO;
                        }
                    }
                }
            }
        }
    }
    [_tableView1 reloadData];
    [_tableView2 reloadData];
    [_tableView3 reloadData];
    
    _filerTitleView.checkBox.selected = YES;
}

- (void)tableView:(UITableView *)tableView didSelectBoxRowAtIndexPath:(NSIndexPath *)indexPath boxSelect:(BOOL)isSelect {
    [self tableView:tableView didSelectBoxRowAtIndexPath:indexPath];
    if (isSelect) {
        _filerTitleView.checkBox.selected = NO;
    }
    
    if (isSelect) {
        if (tableView == _tableView1) {
            [self tableView1SelectAction:indexPath.row select:YES];
        }else if (tableView == _tableView2) {
            [self tableView2SelectAction:indexPath.row select:YES];
        }else {
            [self tableView3SelectAction:indexPath.row select:YES];
        }
    }else {
        if (tableView == _tableView1) {
            [self tableView1SelectAction:indexPath.row select:NO];
        }else if (tableView == _tableView2) {
            [self tableView2SelectAction:indexPath.row select:NO];
        }else {
            [self tableView3SelectAction:indexPath.row select:NO];
        }
    }

    [_tableView1 reloadData];
    [_tableView2 reloadData];
    [_tableView3 reloadData];
}

#pragma mark - Private
- (void)tableView:(UITableView *)tableView didSelectBoxRowAtIndexPath:(NSIndexPath *)indexPath {
    if (tableView == _tableView1) {
        _table1SelectRow = indexPath.row;
        _table2SelectRow = 0;
        [_tableView2 reloadData];
        [_tableView2 selectRowAtIndexPath:[NSIndexPath indexPathForRow:_table2SelectRow inSection:0] animated:YES scrollPosition:UITableViewScrollPositionNone];
        [_tableView3 reloadData];
    }else if (tableView == _tableView2) {
        _table2SelectRow = indexPath.row;
        [_tableView3 reloadData];
    }else {
        [tableView deselectRowAtIndexPath:indexPath animated:NO];
    }
}

- (void)tableView1SelectAction:(NSInteger)selectRow select:(BOOL)isSelect{
    AreaModel *model = _provinces[selectRow];
    model.selected = isSelect;
    NSArray *models = [AreaInstanceObj citysForProvinceId:_provinces[_table1SelectRow]];
    for (AreaModel *area in models) {
        area.selected = isSelect;
        
        NSArray *regions = [AreaInstanceObj regionAreasForProvince:area];
        for (AreaModel *region in regions) {
            region.selected = isSelect;
        }
    }
}

- (void)tableView2SelectAction:(NSInteger)selectRow select:(BOOL)isSelect {
    NSArray *models = [AreaInstanceObj citysForProvinceId:_provinces[_table1SelectRow]];
    AreaModel *city = models[selectRow];
    city.selected = isSelect;
    NSArray *regions = [AreaInstanceObj regionAreasForProvince:city];
    for (AreaModel *region in regions) {
        region.selected = isSelect;
    }
    
    // 如果选择了一个市，那么对应的省肯定要选上
    if (isSelect) {
        AreaModel *provin = _provinces[_table1SelectRow];
        provin.selected = YES;
    }
    
    // 如果所有的市都未选中，那么对应的省肯定要取消选中
    BOOL isAllCityUnselect = YES;
    for (AreaModel *city in models) {
        if (city.isSelected) {
            isAllCityUnselect = NO;
            break;
        }
    }
    if (isAllCityUnselect) {
        AreaModel *provin = _provinces[_table1SelectRow];
        provin.selected = NO;
    }
}

- (void)tableView3SelectAction:(NSInteger)selectRow select:(BOOL)isSelect {
    NSArray *models = [AreaInstanceObj citysForProvinceId:[AreaInstanceObj provinceAreas][_table1SelectRow]];
    AreaModel *model = models[_table2SelectRow];
    NSArray *areas = [AreaInstanceObj regionAreasForProvince:model];
    
    AreaModel *region = areas[selectRow];
    region.selected = isSelect;
    
    // 如果选择了一个区，那么对应的省、市肯定要选上
    if (isSelect) {
        AreaModel *city = models[_table2SelectRow];
        city.selected = YES;
        
        AreaModel *provin = _provinces[_table1SelectRow];
        provin.selected = YES;
    }
    
    // 如果所有的区都未选中，那么对应的市、县肯定要取消选中
    BOOL isAllRegionUnselect = YES;
    for (AreaModel *region in areas) {
        if (region.isSelected) {
            isAllRegionUnselect = NO;
            break;
        }
    }
    if (isAllRegionUnselect) {
        AreaModel *city = models[_table2SelectRow];
        city.selected = NO;
    }
    
    // 如果所有的市都未选中，那么对应的省肯定要取消选中
    BOOL isAllCityUnselect = YES;
    for (AreaModel *city in models) {
        if (city.isSelected) {
            isAllCityUnselect = NO;
            break;
        }
    }
    if (isAllCityUnselect) {
        AreaModel *provin = _provinces[_table1SelectRow];
        provin.selected = NO;
    }
}

#pragma mark - FilerSelectAllDelegate
- (void)filerSelectAll:(BOOL)isSelectAll {
    if (isSelectAll) {
        [self resetFilerAddressData];
    }
}

@end
