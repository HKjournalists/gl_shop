//
//  Utilits.m
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "Utilits.h"

#define k_APPID @"appabc.com"  // 与服务器约定的参数

@implementation Utilits

+(BOOL)isMobilePhone:(NSString *)mobileNumber {
    
    if(!mobileNumber)
        return 0;
    NSString *regex = @"^1[3|4|5|8|7]\\d{9}$";
    return [self predicateMatchWithString:mobileNumber regex:regex];
}

+(BOOL)isValidateEmail:(NSString *)email {
    
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    return [self predicateMatchWithString:email regex:emailRegex];
}

+(BOOL)predicateMatchWithString:(NSString *)str regex:(NSString *)regex{
    
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",regex];
    return [predicate evaluateWithObject:str];
}

+ (NSString*) stringFromFomate:(NSDate*) date formate:(NSString*)formate {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:formate];
    NSString *str = [formatter stringFromDate:date];
    return str;
}

+ (NSDate *) dateFromFomate:(NSString *)datestring formate:(NSString*)formate {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:formate];
    NSDate *date = [formatter dateFromString:datestring];
    return date;
}

+ (NSMutableDictionary *)packSevrverRequestParams:(NSMutableDictionary *)paramDictionary {
    if (paramDictionary.count <= 0) {
        DebugLog(@"参数不能为空");
        return nil;
    }
    
    NSDate *now = [NSDate date];
    NSString *timeStr = [Utilits stringFromFomate:now formate:@"yyyymmddhhMMss"];
    [paramDictionary setObject:timeStr forKey:@"timestamp"];
    
    NSArray *keys = paramDictionary.allKeys;
    NSArray *sortArray = [Utilits sortStrArray:keys];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    for (NSString *key in sortArray) {
        [params setObject:[paramDictionary objectForKey:key] forKey:key];
    }
    
    NSMutableString *signString = [NSMutableString stringWithString:k_APPID];
    for (NSString *key in sortArray) {
        NSString *tempStr = [NSString stringWithFormat:@"%@%@",key,params[key]];
        [signString appendString:tempStr];
        
    }
    
    NSString *md5Str = [signString md5];
    NSMutableDictionary *finalParams = [NSMutableDictionary dictionaryWithDictionary:params];
    [finalParams setObject:md5Str forKey:@"sign"];
    [finalParams setObject:timeStr forKey:@"timestamp"];
    
    return finalParams;
}

+ (NSArray *)sortStrArray:(NSArray *)strArray {
    
    NSStringCompareOptions comparisonOptions = NSCaseInsensitiveSearch|NSNumericSearch|
    NSWidthInsensitiveSearch|NSForcedOrderingSearch;
    NSComparator sort = ^(NSString *obj1,NSString *obj2){
        NSRange range = NSMakeRange(0,obj1.length);
        return [obj1 compare:obj2 options:comparisonOptions range:range];
    };
    NSArray *resultArray2 = [strArray sortedArrayUsingComparator:sort];
    return resultArray2;
}

+ (UIColor *) colorWithHexString: (NSString *)color
{
    NSString *cString = [[color stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    // String should be 6 or 8 characters
    if ([cString length] < 6) {
        return [UIColor clearColor];
    }
    
    // strip 0X if it appears
    if ([cString hasPrefix:@"0X"])
        cString = [cString substringFromIndex:2];
    if ([cString hasPrefix:@"#"])
        cString = [cString substringFromIndex:1];
    if ([cString length] != 6)
        return [UIColor clearColor];
    
    // Separate into r, g, b substrings
    NSRange range;
    range.location = 0;
    range.length = 2;
    
    //r
    NSString *rString = [cString substringWithRange:range];
    
    //g
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    //b
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    return [UIColor colorWithRed:((float) r / 255.0f) green:((float) g / 255.0f) blue:((float) b / 255.0f) alpha:1.0f];
}

@end
