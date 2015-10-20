//
//  StatusItemView.h
//  Glshop
//
//  Created by River on 15-2-10.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同状态图卡

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, StatusItmeViewStyle) {
    /**
     *@brief 合同未执行的状态
     */
    statusWaitProcessStyle,
    /**
     *@brief 合同正在进行的状态
     */
    statusProcessingStyle,
    /**
     *@brief 合同已经执行完的状态
     */
    statusProcessOverStyle,
};

typedef NS_ENUM(NSInteger, ItemArrowDirction) {
    arrowRight,
    arrowLeft,
};

@interface StatusItemView : UIView
{
@private
    UIImageView *_backgroundView;
    UILabel     *_textLabel;
    UILabel     *_assistLabel;
    UIFont      *_textFont;
    ItemArrowDirction    _dirction;
}

- (instancetype)initWithArrowDirction:(ItemArrowDirction)driction;

/**
 *@brief 显示风格
 */
@property (nonatomic, assign) StatusItmeViewStyle itemStyle;

/**
 *@brief 要显示的文本，最多2行文本
 */
@property (nonatomic, strong) NSArray *textArray;

@property (nonatomic, assign) BOOL isMe;

@end
