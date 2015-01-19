//
//  Utilits.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Utilits : NSObject

+(BOOL)isMobilePhone:(NSString *)mobileNumber;
+(BOOL)isValidateEmail:(NSString *)email;

+ (UIColor *) colorWithHexString: (NSString *)color;

/**
 *@brief 在导航控制器的堆栈中，查找到指定的视图控制器
 */
+ (id)findDesignatedViewController:(NSString *)vcClassName
             currentViewController:(UIViewController *)currentVC;

/**
 *@brief 根据文本，计算label的size
 */
+ (CGSize)labelSizeCalculte:(UIFont *)font labelText:(NSString *)text;

/**
 *@brief 将date格式化为字符串
 *@param date 目标时间
 *@param formate 目标格式
 *@return NSString 格式化的时间字符串
 */
+ (NSString*) stringFromFomate:(NSDate*)date formate:(NSString*)formate;

/**
 *@brief 将string格式化为date
 *@param datestring 目标时间字符串
 *@param formate 目标格式
 *@return NSDate 格式化的时间
 */
+ (NSDate *) dateFromFomate:(NSString *)datestring formate:(NSString*)formate;

/**
 *@brief 包装请求参数,针对本项目
 *@param paramDictionary 不固定请求参数（key,value)
 *@return NSDictionary 最终的要post/get的参数
 */
+ (NSMutableDictionary *)packSevrverRequestParams:(NSMutableDictionary *)paramDictionary;


@end
