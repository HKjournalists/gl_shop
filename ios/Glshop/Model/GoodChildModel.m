//
//  GoodChildModel.m
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "GoodChildModel.h"

//- (NSDictionary *)attributeMapDictionary
//{
//    NSDictionary *mapAtt = @{
//                             @"":@"",
//                             @"":@"",
//                             @"":@"",
//                             @"":@"",
//                             @"":@"",
//                             };
//    return mapAtt;
//}

//"id": "201411200000001",
//"pname": "碎石1-2",
//"pcode": "G001",
//"pcolor": "红色",
//"paddress": "凤凰山",
//"unit": "U001",
//"remark": "石子——碎石1-2",

@implementation GoodChildModel

- (NSDictionary *)attributeMapDictionary
{
    NSDictionary *mapAtt = @{
                             @"goodChildId":@"id",
                             @"goodChildPname":@"pname",
                             @"goodChildPcode":@"pcode",
                             @"goodChildPcolor":@"pcolor",
                             @"goodChildPaddress":@"paddress",
                             @"goodChildUnit":@"unit",
                             @"goodChildRemark":@"remark",
                             @"goodChildPtype":@"ptype",
                             @"goodOrderno":@"orderno",
                             };
    return mapAtt;
}

- (void)setAttributes:(NSDictionary *)dataDic
{
    [super setAttributes:dataDic];
    
    NSDictionary *retweetDic = [dataDic objectForKey:@"psize"];
    if (retweetDic != nil) {
        PsizeModel *relWeibo = [[PsizeModel alloc] initWithDataDic:retweetDic];
        self.sizeModel  = relWeibo;
    }
    
    NSArray *userDics = [dataDic objectForKey:@"propertyList"];
    
    NSMutableArray *tempDicArr = [NSMutableArray array];
    for (NSDictionary *dic in userDics) {
        NSMutableDictionary *tempDic = [NSMutableDictionary dictionaryWithObjectsAndKeys:dic[@"content"],@"content",dic[@"id"],@"id", nil];
        [tempDicArr addObject:tempDic];
    }
    self.propreDicArray = [NSArray arrayWithArray:tempDicArr];
    
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in userDics) {
        ProModel *model = [[ProModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    self.propretyArray = [NSArray arrayWithArray:temp];
}

#pragma mark - Public 
- (NSString *)combineNameWithUnit {
    float max = [self.sizeModel.maxv floatValue];
    float min = [self.sizeModel.minv floatValue];
    NSString *str = [NSString stringWithFormat:@"%@(%.1f-%.1f)mm",self.goodChildPname,min,max];
    
    // 只有最小值
    if (max==0 && min > 0) {
        str = [NSString stringWithFormat:@"%@(>=%.1f)mm",self.goodChildPname,min];
    }
    
    // 最大最小值都是0
    if (max == 0 && min == 0) {
        str = self.goodChildPname;
    }
    
    return str;
}

- (NSString *)nameWithUnit {
    return [NSString stringWithFormat:@"%@%@",self.goodChildPname,self.productUnit];
}

- (NSString *)productUnit {
    float max = [self.sizeModel.maxv floatValue];
    float min = [self.sizeModel.minv floatValue];
    
    NSString *str = [NSString stringWithFormat:@"(%.1f-%.1f)",min,max];
    
    // 只有最小值
    if (max==0 && min > 0) {
        str = [NSString stringWithFormat:@"(>=%.1f)",min];
    }
    
    // 最大最小值都是0
    if (max == 0 && min == 0) {
        str = nil;
    }
    
    return str;
}

@end
