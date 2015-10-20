//
//  ContractStatusView.m
//  Glshop
//
//  Created by River on 15-2-10.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同状态图

#import "ContractStatusView.h"
#import "StatusItemView.h"
#import "ContractModel.h"

static NSInteger itemGap = 8;
static NSInteger itemHeight = 25;
static NSInteger itemWidth = 130;

@interface ContractStatusView ()

@property (nonatomic, strong) UIImageView *commenView;

@end

@implementation ContractStatusView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor whiteColor];
        
        _headerView = [[UIImageView alloc] initWithFrame:CGRectZero];
        UIImage *image = [UIImage imageNamed:@"agreement_beijing"];
        _headerView.image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
        [self addSubview:_headerView];
        
        _leftTitleLabel = [UILabel labelWithTitle:@"买家"];
        _leftTitleLabel.textColor = [UIColor blackColor];
        _leftTitleLabel.textAlignment = NSTextAlignmentCenter;
        [_headerView addSubview:_leftTitleLabel];
        
        _rightTitleLabel = [UILabel labelWithTitle:@"卖家"];
        _rightTitleLabel.textColor = [UIColor blackColor];
        _rightTitleLabel.textAlignment = NSTextAlignmentCenter;
        [_headerView addSubview:_rightTitleLabel];
    }
    return self;
}

#pragma mark - Setter
- (void)setContractModel:(ContractModel *)contractModel {
    if (_contractModel != contractModel) {
        _contractModel = contractModel;
        
        BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
        if (isBuyer) {
            _leftTitleLabel.text = @"买家(我)";
            _leftTitleLabel.textColor = [UIColor orangeColor];
        }else {
            _rightTitleLabel.text = @"卖家(我)";
            _rightTitleLabel.textColor = [UIColor orangeColor];
        }
    }
}

- (void)setLeftItemAttributes:(NSArray *)leftItemAttributes {
    BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    
    if (_leftItemAttributes != leftItemAttributes) {
        _leftItemAttributes = leftItemAttributes;
        
        _pointerViewArray = [NSMutableArray arrayWithCapacity:leftItemAttributes.count];
        
        _leftItems = [NSMutableArray arrayWithCapacity:leftItemAttributes.count];
        
        for (NSDictionary *attDic in leftItemAttributes) {
            NSNumber *num = attDic.allKeys.firstObject;
            NSArray *texts = attDic.allValues.firstObject;
            
            StatusItemView *itemView = [[StatusItemView alloc] initWithArrowDirction:arrowRight];
            itemView.textArray = texts;
            itemView.isMe = isBuyer;
            itemView.itemStyle = [num integerValue];
            [self addSubview:itemView];
            
            [_leftItems addObject:itemView];
            
            UIImageView *pointer = [[UIImageView alloc] init];
            [_pointerViewArray addObject:pointer];
        }
    }
}

- (void)setRightItemAttributes:(NSArray *)rightItemAttributes {
    BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    
    if (_rightItemAttributes != rightItemAttributes) {
        _rightItemAttributes = rightItemAttributes;
        
        _rightItems = [NSMutableArray arrayWithCapacity:rightItemAttributes.count];
        
        for (NSDictionary *attDic in rightItemAttributes) {
            NSNumber *num = attDic.allKeys.firstObject;
            NSArray *texts = attDic.allValues.firstObject;
            
            StatusItemView *itemView = [[StatusItemView alloc] initWithArrowDirction:arrowLeft];
            itemView.textArray = texts;
            itemView.isMe = !isBuyer;
            itemView.itemStyle = [num integerValue];
            [self addSubview:itemView];
            
            [_rightItems addObject:itemView];
        }
    }
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    _headerView.frame = CGRectMake(0, 0, self.vwidth, 30);
    _leftTitleLabel.frame = CGRectMake(0, 0, self.vwidth/2, _headerView.vheight);
    _rightTitleLabel.frame = CGRectMake(_leftTitleLabel.vright, _leftTitleLabel.vtop, _leftTitleLabel.vwidth, _leftTitleLabel.vheight);
    
    [_leftItems enumerateObjectsUsingBlock:^(StatusItemView *view, NSUInteger idx, BOOL *stop) {
        
        if (idx == 0) {
            view.frame = CGRectMake(17, _headerView.vbottom+10, itemWidth, itemHeight);
        }else {
            UIView *previousView = _leftItems[idx-1];
            StatusItemView *rightItem = _rightItems[idx];
            float height = view.textArray.count == 2 ? itemHeight+15 : itemHeight;
            if (rightItem.textArray.count > 1) {
                height = itemHeight+15;
            }
            view.frame = CGRectMake(previousView.vleft, previousView.vbottom+itemGap, previousView.vwidth, height);
        }
        
        UIImageView *pointer = _pointerViewArray[idx];
        pointer.frame = CGRectMake(self.vwidth/2-6, CGRectGetMidY(view.frame)-6, 12, 12);
        pointer.image = [UIImage imageNamed:@"agreement_yuan_huise"];
        [self addSubview:pointer];
        
        if (idx == 0) {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(self.vwidth/2-3/2.0, _headerView.vbottom, 3, pointer.vtop-_headerView.vbottom)];
            line.backgroundColor = RGB(200, 200, 200, 1);
            [self addSubview:line];
        }else {
            UIImageView *prePointer = _pointerViewArray[idx-1];
            
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(self.vwidth/2-3/2.0, prePointer.vbottom, 3, pointer.vtop-prePointer.vbottom)];
            line.backgroundColor = RGB(200, 200, 200, 1);
            [self addSubview:line];
        }
        
        if (idx == _leftItems.count-1) {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(self.vwidth/2-3/2.0, pointer.vbottom, 3, 20)];
            line.backgroundColor = RGB(200, 200, 200, 1);
            [self addSubview:line];
            
            NSString *imgName = [_contractModel contractValide] ? @"agreement_xuyuan1":@"agreement_huise_yuan";
            UIImageView *end = [[UIImageView alloc] initWithImage:[UIImage imageNamed:imgName]];
            end.frame = CGRectMake(self.vwidth/2-43/2.0, line.vbottom, 43, 43);
            UILabel *tipLabel = [UILabel labelWithTitle:@"交易\n结束"];
            tipLabel.numberOfLines = 2;
            tipLabel.textAlignment = NSTextAlignmentCenter;
            tipLabel.frame = end.bounds;
            tipLabel.font = [UIFont systemFontOfSize:14.f];
            [end addSubview:tipLabel];
            [self addSubview:end];
            
            ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
            if (lifeCycle == NORMAL_FINISHED || ([_contractModel  contractValide] && lifeCycle != ARBITRATING)) {
                UIView *line = [[UIView alloc] initWithFrame:CGRectMake(self.vwidth/2-3/2.0, end.vbottom, 3, 10)];
                line.backgroundColor = RGB(200, 200, 200, 1);
                [self addSubview:line];
                
                [self addSubview:self.commenView];
                self.commenView.vleft = end.vleft;
                self.commenView.vtop = line.vbottom;
            }
        }
        

    }];
    
    [_rightItems enumerateObjectsUsingBlock:^(StatusItemView *view, NSUInteger idx, BOOL *stop) {
        if (idx == 0) {
            view.frame = CGRectMake(CGRectGetMidX(_headerView.frame)+15, _headerView.vbottom+10, itemWidth, itemHeight);
        }else {
            UIView *previousView = _rightItems[idx-1];
            StatusItemView *leftItem = _leftItems[idx];
            float height = view.textArray.count == 2 ? itemHeight+15 : itemHeight;
            if (leftItem.textArray.count > 1) {
                height = itemHeight+15;
            }
            view.frame = CGRectMake(previousView.vleft, previousView.vbottom+itemGap, previousView.vwidth, height);
        }
    }];
    
}

- (UIImageView *)commenView {
    if (!_commenView) {
        _commenView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 43, 43)];
        UILabel *tipLabel = [UILabel labelWithTitle:@"合同\n评价"];
        tipLabel.numberOfLines = 2;
        tipLabel.textAlignment = NSTextAlignmentCenter;
        tipLabel.frame = _commenView.bounds;
        tipLabel.font = [UIFont systemFontOfSize:14.f];
        [_commenView addSubview:tipLabel];
        
        NSString *imgName;
        if ([_contractModel contractValide]) {
            imgName = @"agreement_xuyuan1";
        }else {
            BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
            if (isBuyer) {
                NSInteger eva = [_contractModel.buyerEvaluation[DataValueKey] integerValue];
                imgName = !eva ? @"agreement_xuyuan1":@"agreement_huise_yuan";
            }else {
                NSInteger eva = [_contractModel.sellerEvaluation[DataValueKey] integerValue];
                imgName = !eva ? @"agreement_xuyuan1":@"agreement_huise_yuan";
            }
        }
        _commenView.image = [UIImage imageNamed:imgName];
    }
    return _commenView;
}

@end
