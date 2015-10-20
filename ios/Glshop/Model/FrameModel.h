//
//  FrameModel.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/23.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FrameModel : NSObject


@property (nonatomic,assign)CGRect frame;

+(instancetype)createWithDescriptionModel:(id)descriptionModel;

@end
