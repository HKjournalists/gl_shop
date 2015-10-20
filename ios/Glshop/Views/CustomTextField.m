//
//  CustomTextField.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/30.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CustomTextField.h"

#define PLACEHODERCOLOR [UIColor colorWithRed:136/255.0 green:136/255.0 blue:136/255.0 alpha:1.0]

@implementation CustomTextField


- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    
    if (self) {
        
        self.font = [UIFont systemFontOfSize:11];
        
    }
    return self;
}
- (void)setTextFieldStyleWith:(NSString *)placeholdStr
{
    self.borderStyle = UITextBorderStyleRoundedRect;
    UIFont *placeholderFont = [UIFont systemFontOfSize:11];
    UIColor *placeholderColor = PLACEHODERCOLOR;
    self.attributedPlaceholder = [[NSAttributedString alloc] initWithString:placeholdStr attributes:[NSDictionary dictionaryWithObjectsAndKeys:placeholderFont,NSFontAttributeName,placeholderColor,NSForegroundColorAttributeName, nil]];
    
    self.backgroundColor = GROUPBUYWHITECOLOR;

}

- (CGRect)placeholderRectForBounds:(CGRect)bounds
{
    CGRect inset = CGRectMake(bounds.origin.x+7, bounds.origin.y, bounds.size.width, bounds.size.height);
    
    return inset;
    
}


@end
