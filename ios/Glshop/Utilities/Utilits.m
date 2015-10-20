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

+ (void)alertWithString:(NSString *)text alertTitle:(NSString *)title {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:title message:text delegate:nil cancelButtonTitle:globe_sure_str otherButtonTitles:nil, nil];
    [alertView show];
}

+ (NSAttributedString *)attString:(NSString *)normalString attTargetStr:(NSString *)targetString attrubites:(NSDictionary *)attDic {
    NSRange range = [normalString rangeOfString:targetString];
    if (range.location == NSNotFound) {
        DLog(@"字符串越界，请检查！");
        return nil;
    }
    
    NSMutableAttributedString *attStr = [[NSMutableAttributedString alloc] initWithString:normalString];
    [attStr setAttributes:attDic range:range];
    
    return attStr;
}

+(BOOL)isMobilePhone:(NSString *)mobileNumber {
    
    if(!mobileNumber)
        return 0;
    NSString *regex = @"^1[3|4|5|8|7]\\d{9}$";
    return [self predicateMatchWithString:mobileNumber regex:regex];
}

+ (CGSize)labelSizeCalculte:(UIFont *)font labelText:(NSString *)text {
    UILabel *label = [[UILabel alloc] init];
    label.font = font;
    label.text = text;
    [label sizeToFit];
    
    return label.frame.size;
}

+ (NSString *)ToGetdeviceToken {
    NSData *udid = [[NSUserDefaults standardUserDefaults] objectForKey:kDeviceTokenKey];
    if (!udid) {
        DLog(@"获取设备token失败");
    }
    NSString *pushToken = [[[udid description] stringByReplacingOccurrencesOfString:@"<" withString:@""]stringByReplacingOccurrencesOfString:@">" withString:@""];
    NSString *deviceToken = [pushToken stringByReplacingOccurrencesOfString:@" " withString:@""];
    return deviceToken;
}

+ (id)findDesignatedViewController:(NSString *)vcClassName
                             currentViewController:(UIViewController *)currentVC {
    
    for (UIViewController *vc in currentVC.navigationController.viewControllers) {
        if ([vc isKindOfClass:NSClassFromString(vcClassName)]) {
            return vc;
        }
    }
    return nil;
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

/**
 *@brief 计算距离现在的时间差
 *@param limitimeStr 目标时间
 *@return x小时y分 eg.2小时36分
 */
+ (NSString *)timeGap:(NSString *)limitimeStr {
    NSDate *limitime = [Utilits dateFromFomate:limitimeStr formate:@"yyyy-MM-dd HH:mm:ss"];
    NSTimeInterval timegap = [limitime timeIntervalSinceDate: [NSDate date]];
    
    if (timegap <= 0) {
        return @"0小时0分钟";
    }
    
//    NSInteger hour = timegap/3600;
    NSInteger mintine = fmod(timegap, 3600)/60;
    
    return [NSString stringWithFormat:@"0小时%ld分",(long)mintine];
}

+ (NSString *)timeStrFomate:(NSString *)timeString {
    if (!timeString.length) {
        DLog(@"传入的时间字符串为空")
        return nil;
    }
    NSDate *date = [Utilits dateFromFomate:timeString formate:kTimeDetail_Format];
    return [Utilits stringFromFomate:date formate:@"yyyy年MM月dd日"];
}

/**
 *@brief 计算距离现在的时间差
 *@param limitimeStr 目标时间
 *@return z天x小时y分 eg.2天2小时36分
 */
+ (NSString *)timeDHMGap:(NSString *)limitimeStr {
    NSDate *limitime = [Utilits dateFromFomate:limitimeStr formate:@"yyyy-MM-dd HH:mm:ss"];
    NSTimeInterval timegap = [limitime timeIntervalSinceDate:[NSDate date]];
    
    NSInteger day = timegap/3600/24;
    NSInteger hour = fmod(timegap, 3600*24)/3600;
    NSInteger mintine = fmod(timegap, 3600)/60;
    if(timegap <= 0){
        day = 0;
        hour = 0;
        mintine =0;
    }
    
    return [NSString stringWithFormat:@"%ld天%ld小时%ld分",(long)day,(long)hour,(long)mintine];
}

+ (NSMutableDictionary *)packSevrverRequestParams:(NSMutableDictionary *)paramDictionary {
    if (paramDictionary.count <= 0) {
        DebugLog(@"请求参数为空");
        paramDictionary = [NSMutableDictionary dictionary];
    }
    
    NSDate *now = [NSDate date];
    NSString *timeStr = [Utilits stringFromFomate:now formate:@"yyyyMMddHHmmss"];
    // 这里可能出现汉子，要删掉
    NSMutableString *astr = [NSMutableString stringWithString:timeStr];
    NSString *tStr = [Utilits deleteChinese:astr];
    
    [paramDictionary setObject:astr forKey:@"timestamp"];
    
    NSArray *keys = paramDictionary.allKeys;
    NSArray *sortArray = [Utilits sortStrArray:keys];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    for (NSString *key in sortArray) {
        [params setObject:[paramDictionary objectForKey:key] forKey:key];
    }
    
    NSMutableString *signString = [NSMutableString stringWithString:k_APPID];
    for (NSString *key in sortArray) {
        id noSpaceStr = params[key];
        NSString *trimmedString;
        if ([noSpaceStr isKindOfClass:[NSString class]]) {
           trimmedString  = [noSpaceStr stringByTrimmingCharactersInSet:
                                       [NSCharacterSet whitespaceAndNewlineCharacterSet]];
        }
        id obj = trimmedString.length ? trimmedString : noSpaceStr;
        NSString *tempStr = [NSString stringWithFormat:@"%@%@",key,obj];
        [signString appendString:tempStr];
    }
    
    NSString *md5Str = [signString md5];
    NSMutableDictionary *finalParams = [NSMutableDictionary dictionaryWithDictionary:params];
    [finalParams setObject:md5Str forKey:@"sign"];
    [finalParams setObject:tStr forKey:@"timestamp"];
    
    return finalParams;
}

+ (NSString *)deleteChinese:(NSString *)str {
    NSMutableString *mStr = [NSMutableString stringWithString:str];
    NSRegularExpression *regx = [NSRegularExpression regularExpressionWithPattern:@"[\u4e00-\u9fa5]" options:NSRegularExpressionCaseInsensitive error:nil];
    NSArray *array =    nil;
    
    array = [regx matchesInString:str options:0 range:NSMakeRange(0, [str length])];
    for (NSTextCheckingResult *resulet in array) {
        [mStr replaceCharactersInRange:resulet.range withString:@" "];
    }
    NSLog(@"%@",mStr);
    [mStr replaceOccurrencesOfString:@" " withString:@"" options:0 range:NSMakeRange(0, mStr.length)];
    NSLog(@"%@",mStr);
    
    return mStr;
}

+ (NSString *)handleErrorCode:(NSInteger)errorCode {
    NSString *tipMessage;
    switch (errorCode) {
        case 30000:
            tipMessage = @"操作限制";
            break;
        case 300001:
            tipMessage = @"用户未登录";
            break;
        case 300002:
            tipMessage = @"用户登录失效";
            break;
        case 40000:
            tipMessage = @"重复提交,重复操作";
            break;
        case 50000:
            tipMessage = @"当前时间大于结束时间";
            break;
        case 50001:
            tipMessage = @"开始时间大于结束时间";
            break;
        case 10002:
            tipMessage = @"数据不完整";
            break;
        case 10003:
            tipMessage = @"验证码错误";
            break;
        case 10004:
            tipMessage = @"短信发送发失败";
            break;
        case 10005:
            tipMessage = @"用户名或密码错误";
            break;
        case 10006:
            tipMessage = @"原始密码错误";
            break;
        case 10007:
            tipMessage = @"新旧密码不能相同";
            break;
        case 10008:
            tipMessage = @"用户不存在";
            break;
        case 10009:
            tipMessage = @"用户已存在";
            break;
        case 100005001:
            tipMessage = @"合同模板为空";
            break;
            
        case 100005002:
            tipMessage = @"支付合同错误";
            break;
        case 100005003:
            tipMessage = @"取消合同错误";
            break;
        case 100005004:
            tipMessage = @"确认合同错误";
            break;
        case 100005005:
            tipMessage = @"确认卖家卸货错误";
            break;
        case 100005006:
            tipMessage = @"确认买家收货错误";
            break;
        case 100005007:
            tipMessage = @"合同确认议价操作错误";
            break;
        case 100005008:
            tipMessage = @"合同咨询客户错误";
            break;
        case 100005009:
            tipMessage = @"确认合同错误 重复确认";
            break;
        case 100005010:
            tipMessage = @"确认合同错误 保证金冻结失败";
            break;
        case 100005011:
            tipMessage = @"合同支付错误 合同支付时状态错误";
            break;
        case 100005012:
            tipMessage = @"合同支付错误 您不是买家不能进行操作";
            break;
        case 100005013:
            tipMessage = @"合同操作错误 重复提交 ";
            break;
        case 100005014:
            tipMessage = @"合同操作错误 支付合同货款失败";
            break;
            
        case 100005015:
            tipMessage = @"确认合同错误 合同确认时候状态不对，不允许操作";
            break;
        case 100005016:
            tipMessage = @"合同不能为空";
            break;
        case 100005017:
            tipMessage = @"合同验货环节，您不是买家不能进行收货操作";
            break;
        case 100005018:
            tipMessage = @"合同卸货环节，您不是卖家不能进行卸货操作";
            break;
        case 100005019:
            tipMessage = @"合同的生命周期不允许您操作";
            break;
        case 100005020:
            tipMessage = @"当前合同状态，只能允许您进行同意议价操作";
            break;
        case 100005021:
            tipMessage = @"当前合同状态，只能允许您进行议价操作";
            break;
        case 100005022:
            tipMessage = @"当前合同状态，不允许取消操作";
            break;
        case 100005023:
            tipMessage = @"您不是合同买卖双方，不能操作合同";
            break;
        case 100005024:
            tipMessage = @"合同重复评价 您已经评价过,不能重复评价";
            break;
        case 100005025:
            tipMessage = @"超出合同评价时间范围,系统已经自动评价完成";
            break;
        case 100005026:
            tipMessage = @"合同的买方和卖方不能是同一个人";
            break;
        case 100005027:
            tipMessage = @"你不是合同的买家不能结算申请";
            break;
        case 100005028:
            tipMessage = @"你不是合同的卖家不能同意结算申请";
            break;
        case 100005029:
            tipMessage = @"合同的不支持重复操作";
            break;
        case 100005030:
            tipMessage = @"合同不支持的操作类型";
            break;
        case 100005031:
            tipMessage = @"合同更新到我的结束合同列表错误";
            break;
        case 100005034:
            tipMessage = @"合同确认操作超时处理异常";
            break;
        case 100005035:
            tipMessage = @"合同付款操作超时处理异常";
            break;
            
        case 100004001:
            tipMessage = @"认证重复提交错误";
            break;
        case 100004002:
            tipMessage = @"企业认证不通过";
            break;
            
        case 100003001:
            tipMessage = @"保证金余额不足";
            break;
        case 100003002:
            tipMessage = @"空询单";
            break;
        case 100003003:
            tipMessage = @"感兴趣询单重复申请";
            break;
        case 100003004:
            tipMessage = @"询单不支持多地域发布,并生成合同";
            break;
        case 100003005:
            tipMessage = @"超过最大单价";
            break;
        case 100003006:
            tipMessage = @"超过最大总量";
            break;
        case 800001001:
            tipMessage = @"你已预定成功，请勿重复预定！";
            break;
        case 800001002:
            tipMessage = @"请填写合法电话号码";
            break;
        default:
            tipMessage = @"操作异常，请稍后再试";
            break;
    }
    return tipMessage;
}


+ (NSArray *)sortStrArray:(NSArray *)strArray {
    
    NSStringCompareOptions comparisonOptions = NSNumericSearch|
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
+(NSString *)formatMoney:(double)money
{
    return [Utilits formatMoney:money isUnit:true];
}

+(NSString *)formatMoney:(double)money isUnit:(Boolean)isUnit
{
    NSString *moenyStr = [NSString stringWithFormat:@"%.2f",money];
    
    int size = [moenyStr length];
    
    NSString *result = isUnit ? @"￥" : @"";
    if(size > 6){
        int index = size-6;
        int length = 6;
        NSMutableArray *allArray = [NSMutableArray array];
    
        while(index >= 0){
            NSString *partStr = [moenyStr substringWithRange:NSMakeRange(index, length)];
            [allArray addObject:partStr];
        
            if(index == 0){
                break;
            }
            index = index -3;
            length = 3;
            if(index < 0){
                length = 3+index;
                index = 0;
            }
            
        }
    
        int allsize = allArray.count;
        for(int i =allsize-1;i >=0;i--){
            NSString *tmp = allArray[i];
            result = [result stringByAppendingFormat:tmp];
            if(i!=0){
               result = [result stringByAppendingFormat:@","];
            }
        }
    }else{
        result = [result stringByAppendingFormat:moenyStr];
    }
    
    return result;
}

@end
