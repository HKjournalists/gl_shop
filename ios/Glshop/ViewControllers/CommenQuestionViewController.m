//
//  CommenQuestionViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CommenQuestionViewController.h"
#import "AnswerViewController.h"

@interface CommenQuestionViewController () <UITableViewDataSource,UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation CommenQuestionViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"常见问题";
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 7;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;

    NSArray *texts = @[@"如何下载长江电商客户端",@"如何注册长江电商账号",@"如何发布供求信息",@"如何进行平台认证",@"如何缴纳保证金",@"如何浏览市场买卖信息",@"如何进行合同交易",];
    cell.textLabel.text = texts[indexPath.row];
    cell.textLabel.font = UFONT_16;
    cell.textLabel.textColor = C_BLACK;
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    AnswerViewController *vc = [[AnswerViewController alloc] init];

    NSArray *qusitions = @[@"如何下载长江电商手机客户端？",
                           @"如何注册长江电商账号？",
                           @"如何发布供求信息？",
                           @"如何认证进行平台？",
                           @"如何缴纳保证金？",
                           @"如何浏览市场买卖信息？",
                           @"如何进行合同交易？",];
    
//    NSString *str = kAppStoreVersion ? @"登录AppStore，搜索“长江电商”进行下载" : @"手机访问www.916816.com,进入长江电商下载页面进行下载。";
    NSString *str = @"1、电脑下载：请访问www.916816.com进入长江电商下载页面，根据各自手机平台点击“下载”即可；\n2、手机下载（推荐）：请使用手机浏览器登陆m.916816.com根据手机平台下载安装使用；如果是Ios、Android平台手机用户，可通过AppStore/手机超市搜“长江电商”进行下载安装使用。";
    NSArray *anwers = @[str,
                        @"打开长江电商，点击“免费注册”；\n第2步：进入注册页面后，填写基本注册信息；\n第3步：点击提交确认即可注册成功。",
                        @"您可以自己发布供求信息或者选择联系我司客服免费帮您发布信息：\n第1步：登录“我的信息”选择“发布新信息”；\n第2步：填写完整商品信息进行发布即可；",
                        @"第1步：登陆长江电商平台选择“我的资料”；\n第2步：点击“认证”按钮；\n第3步：按照页面提示进行填写认证相关资料即可。",
                        @"登陆长江电商平台点击“我的钱包”进入“保证金账户”；\n第2步：点击“充值”按钮，然后选择对应的充值方式即可；",
                        @"第1步：请登陆长江电商平台点击“找买找卖”；\n第2步：进行市场信息浏览，即可查看感兴趣的商品买卖信息；\n第3步：对感兴趣的商品信息，可以在信息的最下方点击“交易询盘按钮”按钮，点击成功后将有平台专员与您取得联系，为您撮合商品交易。",
                        @"合同由平台免费帮你撮合找到交易对象且生成电子交易合同；\n平台生成合同后，买卖双方需在15分钟内确认合同，合同方可正式生效；\n买方需在指定时间内，完成合同付款。卖方需按合同要求提供与约定相符合的产品并及时发货。货物交收时，买方需在平台提交货物与货款的实际确认，卖方需根据实际情况选择是否同意，若卖方同意则合同交易成功，平台将进行合同的交易结算。",];
    
    vc.qustionString = qusitions[indexPath.row];
    vc.answerString = anwers[indexPath.row];
    [self.navigationController pushViewController:vc animated:YES];
}

@end
