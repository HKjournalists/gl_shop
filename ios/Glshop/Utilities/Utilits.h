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

+ (void)alertWithString:(NSString *)text alertTitle:(NSString *)title;

+ (NSAttributedString *)attString:(NSString *)normalString attTargetStr:(NSString *)targetString attrubites:(NSDictionary *)attDic;

/**
 *@brief 字符串清除中文
 *@param str 原始字符串（可能有汉子）
 *@return 无汉子的字符串
 */
+ (NSString *)deleteChinese:(NSString *)str;

/**
 *@brief 得到token字符串
 */
+ (NSString *)ToGetdeviceToken;
/**
 *@brief 在导航控制器的堆栈中，查找到指定的视图控制器
 */
+ (id)findDesignatedViewController:(NSString *)vcClassName
             currentViewController:(UIViewController *)currentVC;

/**
 *@brief 服务器返回的异常代码处理
 */
+ (NSString *)handleErrorCode:(NSInteger)errorCode;

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
 *@return e.g 2080年02月23日
 */
+ (NSString *)timeStrFomate:(NSString *)timeString;

/**
 *@brief 计算距离现在的时间差
 *@param limitimeStr 目标时间
 *@return x小时y分 eg.2小时36分
 */
+ (NSString *)timeGap:(NSString *)limitimeStr;

/**
 *@brief 计算距离现在的时间差
 *@param limitimeStr 目标时间
 *@return z天x小时y分 eg.2天2小时36分
 */
+ (NSString *)timeDHMGap:(NSString *)limitimeStr;

/**
 *@brief 包装请求参数,针对本项目
 *@param paramDictionary 不固定请求参数（key,value)
 *@return NSDictionary 最终的要post/get的参数
 */
+ (NSMutableDictionary *)packSevrverRequestParams:(NSMutableDictionary *)paramDictionary;


+(NSString *)formatMoney:(double)money;

+(NSString *)formatMoney:(double)money isUnit:(Boolean)isUnit;

@end
