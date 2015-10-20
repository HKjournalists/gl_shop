//
//  PlatformViewController.m
//  Glshop
//
//  Created by River on 15-2-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PlatformViewController.h"

static NSString *const mission = @"使长江沿岸的供方企业与需方企业之间无难做的生意。";
static NSString *const vision = @"为长江沿岸的供方企业与需方企业搭建最好的商务保障综合电子服务平台。";
static NSString *const platformVCTitle = @"平台介绍";
static NSString *const idea = @"(1)针对沿江商务的特性电子商务交易平台\n(2)针对沿江商务唯一的交易保障平台\n(3)以手机智能终端为主要载体的移动交易平台\n(4)以交易数据为基础的企业商务融资平台";

@interface PlatformViewController () <UITableViewDataSource,UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic, strong) NSArray *imgNames;
@property (nonatomic, strong) NSArray *titles;
@property (nonatomic, strong) NSArray *ideas;
@property (nonatomic, strong) NSArray *values;

@end

@implementation PlatformViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.title = platformVCTitle;
    _imgNames = @[@"help_icon_shiming",@"help_icon_yuanjing",@"help_icon_linian",@"help_icon_jiazhi",];
    _titles = @[@"我们的使命",@"我们的愿景",@"长江电商平台理念",@"长江电商平台价值",];
    _values = @[@"保障价值",@"通过用户真实身份认证，电子交易合同签订，电子合同保证金机制，货款冻结机制，货物抽检，货物全检后实际支付的机制，保障正常，安全的交易。",@"信息价值",@"通过沿江货物的及时报价信息采集，以及根据沿江生产和货运来预测市场价格变化，为沿江商户提供最准确的市场信息。供需之间通过信息发布与收集，建立交易桥梁，寻找最适宜的交易对象。",@"效率价值",@"全面而安全的信息，稳定而可靠的交易对象，提前约定的详细货物信息，提前掌握稳定的交货时间，快捷的交易手续，安全的支付体系，提升交易效率。",@"企业融资价值",@"购货专款专用，随着成功的交易信息的累积，诚信交易历史的累积，为企业扩大经营所期待的无担保融资打下基础。",];
    
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 4;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 3) {
        return 9;
    }
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 2) {
        if (indexPath.row == 1) {
            return 80;
        }
    }else if (indexPath.section == 3) {
        if (indexPath.row && indexPath.row%2==0) {
            return indexPath.row == 4 ? 80 : 65;
        }
    }
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = UFONT_16_B;
    cell.textLabel.textColor = C_BLACK;
    
    if (indexPath.row == 0) {
        cell.textLabel.text = _titles[indexPath.section];
        cell.imageView.image = [UIImage imageNamed:_imgNames[indexPath.section]];
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(kCellLeftEdgeInsets, 43.5, SCREEN_WIDTH-kCellLeftEdgeInsets, 0.5)];
        line.backgroundColor = [UIColor lightGrayColor];
        [cell.contentView addSubview:line];
    }else {
        cell.textLabel.font = [UIFont systemFontOfSize:FONT_14];
        cell.textLabel.textColor = GLTextCommenColor;
    }
    
    if (indexPath.section == 0) {
        if (indexPath.row == 1) {
            cell.textLabel.text = mission;
            cell.textLabel.numberOfLines = 2;
        }
    }else if (indexPath.section == 1) {
        if (indexPath.row == 1) {
            cell.textLabel.text = vision;
            cell.textLabel.numberOfLines = 2;
        }
    }else if (indexPath.section == 2) {
        if (indexPath.row == 1) {
            cell.textLabel.numberOfLines = 4;
            cell.textLabel.text = idea;
        }
    }else if (indexPath.section == 3) {
        if (indexPath.row%2 == 1) {
            cell.textLabel.font = [UIFont systemFontOfSize:15.f];
            cell.textLabel.textColor = RGB(50, 50, 50, 1);
        }else if (indexPath.row && indexPath.row%2==0){
            cell.textLabel.numberOfLines = 5;
        }
        if (indexPath.row) {
            cell.textLabel.text = _values[indexPath.row-1];
        }
    }
    
    return cell;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    if (section == 0) {
        UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 80)];
        UIImage *image = [UIImage imageNamed:@"help_icon"];
        UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-58/2, 10, 58, 44)];
        imgView.image = image;
        [view addSubview:imgView];
        
        UILabel *label = [UILabel labelWithTitle:@"长江电商"];
        label.frame = CGRectMake(SCREEN_WIDTH/2-100, imgView.vbottom+10, 200, 20);
        label.textColor = [UIColor orangeColor];
        label.textAlignment = NSTextAlignmentCenter;
        label.font = [UIFont boldSystemFontOfSize:17.f];
        [view addSubview:label];
        
        return view;
    }
    
    return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return section == 0 ? 100 : 5;
}

@end
