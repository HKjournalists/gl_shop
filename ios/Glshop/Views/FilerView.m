//
//  FilerView.m
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerView.h"
#import "FilerProductView.h"
#import "FilerAreasView.h"
#import "AreaModel.h"
#import "AreaInstance.h"

NSString *const comma = @",";

@interface FilerView ()

@property (nonatomic, strong) FilerProductView *filerProductView;
@property (nonatomic, strong) FilerAreasView *filerAreasView;
@property (nonatomic, strong) FilerTimeView *timeView;

@end

@implementation FilerView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
//        self.backgroundColor = [UIColor lightGrayColor];
        
        _segmentControl = [[UISegmentedControl alloc] initWithItems:@[@"种类规格",@"交易地域",@"交易时间范围",]];
        _segmentControl.frame = CGRectMake(8, 5, self.vwidth-16, 30);
        _segmentControl.selectedSegmentIndex = 0;
        [_segmentControl addTarget:self action:@selector(segmentAction:) forControlEvents:UIControlEventValueChanged];
        [self addSubview:_segmentControl];

    
        [self addSubview:self.filerProductView];
        [self addSubview:self.filerAreasView];
        [self addSubview:self.timeView];
        self.filerAreasView.hidden = YES;
        self.timeView.hidden = YES;
    }
    return self;
}

#pragma mark - Getter
- (FilerProductView *)filerProductView {
    if (!_filerProductView) {
        _filerProductView = [[FilerProductView alloc] initWithFrame:CGRectMake(0, _segmentControl.vbottom+9, self.vwidth, self.vheight-64-40)];
        _filerProductView.backgroundColor = RGBA(222, 222, 222, 1);
    }
    return _filerProductView;
}

- (FilerAreasView *)filerAreasView {
    if (!_filerAreasView) {
        _filerAreasView = [[FilerAreasView alloc] initWithFrame:CGRectMake(0, _segmentControl.vbottom+9, self.vwidth, self.vheight-64-40)];
        _filerAreasView.backgroundColor = RGBA(222, 222, 222, 1);
    }
    return _filerAreasView;
}

- (FilerTimeView *)timeView {
    if (!_timeView) {
        _timeView = [[FilerTimeView alloc] initWithFrame:CGRectMake(0, _segmentControl.vbottom+9, self.vwidth, 179)];
    }
    return _timeView;
}

#pragma mark - UIActions
- (void)segmentAction:(UISegmentedControl *)control {
    if (control.selectedSegmentIndex == 0) {
        self.filerAreasView.hidden = YES;
        self.filerProductView.hidden = NO;
        self.timeView.hidden = YES;
    }else if (control.selectedSegmentIndex == 1) {
        self.filerAreasView.hidden = NO;
        self.filerProductView.hidden = YES;
        self.timeView.hidden = YES;
    }else {
        self.filerAreasView.hidden = YES;
        self.filerProductView.hidden = YES;
        self.timeView.hidden = NO;
    }
}

#pragma mark - Public
- (void)resetFilerData {
    [_filerProductView resetFilerProductData];
    [_filerAreasView resetFilerAddressData];
    [_timeView resetFilerTimeData];
}

- (NSString *)gatherFilerProductData {
    // 黄砂产品
    NSMutableArray *temp = [NSMutableArray array];
    for (GoodsModel *send in [SynacObject sendSubType]) {
        NSArray *childSends = [SynacObject sendsGroundSonProductType:send.goodsVal];
        for (GoodChildModel *child in childSends) {
            if (child.isSelected) {
                [temp addObject:child.goodChildId];
            }
        }
    }
    
    // 石子产品
    for (GoodChildModel *child in [SynacObject stoneSubType]) {
        if (child.isSelected) {
            [temp addObject:child.goodChildId];
        }
    }
    
    NSString *filerProductStr = [temp componentsJoinedByString:comma];
    DLog(@"选中的产品%@",filerProductStr);
    return filerProductStr;
}

- (NSString *)gatherFilerProvinceData {
    AreaInstance *areaObj = [AreaInstance sharedInstance];
    NSMutableArray *temp = [NSMutableArray array];
    for (AreaModel *province in areaObj.provinceAreas) {
        if (province.selected) {
            [temp addObject:province.areaVal];
        }
    }
    NSString *filerProvinceStr = [temp componentsJoinedByString:comma];
    DLog(@"选中的省%@",filerProvinceStr);
    return filerProvinceStr;
}

- (NSString *)gatherFilerRegionData {
    AreaInstance *areaObj = [AreaInstance sharedInstance];
    NSMutableArray *temp = [NSMutableArray array];
    for (AreaModel *province in areaObj.provinceAreas) {
        if (province.isSelected) {
            NSArray *citys = [areaObj citysForProvinceId:province];
            for (AreaModel *city in citys) {
                NSArray *regions = [areaObj regionAreasForProvince:city];
                for (AreaModel *region in regions) {
                    if (!region.isSelected) {
                        [temp addObject:region.areaVal];
                    }
                }
            }
        }
    }
    NSString *filerUnSelectRegionStr = [temp componentsJoinedByString:comma];
    DLog(@"未选中的区%@",filerUnSelectRegionStr);
    return filerUnSelectRegionStr;
}

@end
