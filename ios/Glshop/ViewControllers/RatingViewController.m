//
//  RatingViewController.m
//  Glshop
//
//  Created by River on 15-2-28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "RatingViewController.h"
#import "RatingOpertViewController.h"
#import "ContractProcessDetailViewController.h"
#import "CommentModel.h"
#import "TPFloatRatingView.h"

static NSString *cellEvaForme = @"对方给我的评价";
static NSString *cellEvaToOppsite = @"我给对方的评价";
static NSString *noEvaStr = @"暂无评价";
static NSString *oppsiteNoEva = @"对方尚未评价您";

@interface RatingViewController () <UITableViewDataSource,UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tableView;
/**
 *@brief 对方评价我
 */
@property (nonatomic, strong) CommentModel *commentmeModel;
/**
 *@brief 我评价对方
 */
@property (nonatomic, strong) CommentModel *commentoppsiteModel;
///**
// *@brief 我是否已评价
// */
//@property (assign) BOOL isMeEva;
///**
// *@brief 对方是否已评价
// */
//@property (assign) BOOL isOppsiteEva;
/**
 *@brief 我如果没有评价，显示去评价按钮
 */
@property (strong, nonatomic) IBOutlet UIButton *ratingBtn;

@end

@implementation RatingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.title = @"交易评价";
    self.view.backgroundColor = self.tableView.backgroundColor;
    
    UIImage *image = [UIImage imageNamed:BlueButtonImageName];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    [_ratingBtn setBackgroundImage:image forState:UIControlStateNormal];
    _tableView.contentInset = UIEdgeInsetsMake(-15, 0, 0, 0);
    
    [self requestNet];
}

- (void)initDatas {
    _tableView.hidden = YES;
    [_tableView reloadData];
}

- (void)updateViewConstraints {
    if (_commentoppsiteModel) {
        _ratingBtn.hidden = YES;
        [_ratingBtn removeFromSuperview];
        [_tableView updateConstraints:^(MASConstraintMaker *make) {
            make.height.mas_equalTo(self.view.height);
            make.edges.mas_equalTo(self.view).insets(UIEdgeInsetsMake(0, 0, 0, 0));
        }];
    }
    [super updateViewConstraints];
}

- (void)requestNet {
    [super requestNet];
    
    ContractProcessDetailViewController *vc = [self findDesignatedViewController:[ContractProcessDetailViewController class]];
    NSString *oid = vc.contractModel.contractId;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:oid,@"ID",@1,@"type", nil];
    
    __block typeof(self) this = self;
    [self requestWithURL:bnoAuthUrlgetEvaluationContractList params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    
    NSArray *dataDics = responseData[ServiceDataKey];
    UserInstance *userInstance = [UserInstance sharedInstance];
    if (dataDics.count > 0) {
        for (NSDictionary *dic in dataDics) {
            CommentModel *model = [[CommentModel alloc] initWithDataDic:dic];
            if ([model.creater isEqualToString:userInstance.user.cid]) {
                self.commentoppsiteModel = model;
            }else {
                self.commentmeModel = model;
            }
        }
    }
    _tableView.hidden = NO;
    [_tableView reloadData];
    
    _ratingBtn.hidden = self.commentoppsiteModel ? YES : NO;
    
    [self updateViewConstraints];
    
}

- (IBAction)rating:(id)sender {
    RatingOpertViewController *vc = [[RatingOpertViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (_commentmeModel && _commentoppsiteModel) {
        return 4;
    }else if (_commentoppsiteModel && !_commentmeModel) {
        return section ? 4 : 2;
    }else if (!_commentoppsiteModel && _commentmeModel) {
        return section ? 2 : 4;
    }
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (!_commentmeModel && !_commentoppsiteModel) {
        if (indexPath.row == 1) {
            return 80;
        }
    }
    
    if (_commentoppsiteModel && !_commentmeModel) {
        if (indexPath.section == 1 && indexPath.row == 3) {
            float height = [self heightOfLabelSize:_commentoppsiteModel.evaluation];
            return height+55;
        }
    }
    
    if (_commentmeModel && !_commentoppsiteModel) {
        if (indexPath.section == 0 && indexPath.row == 3) {
            float height = [self heightOfLabelSize:_commentmeModel.evaluation];
            return height+55;
        }
    }
    
    if (_commentoppsiteModel && _commentmeModel) {
        if (indexPath.section == 0 && indexPath.row == 3) {
            float height =[self heightOfLabelSize:_commentmeModel.evaluation];;
            return height+55;
        }else if (indexPath.section == 1 && indexPath.row == 3) {
            float height = [self heightOfLabelSize:_commentoppsiteModel.evaluation];;
            return height+55;
        }
    }
    
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    if (indexPath.row == 0) {
        UIImage *image = [UIImage imageNamed:@"agreement_beijing"];
        UIImageView *bgView = [[UIImageView alloc] initWithFrame:cell.bounds];
        bgView.image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
        [cell.contentView addSubview:bgView];
    }
    
    if (indexPath.section == 0&&indexPath.row == 0 ) {
        cell.textLabel.text = cellEvaForme;
    }else if (indexPath.section == 1 && indexPath.row == 0) {
        cell.textLabel.text = cellEvaToOppsite;
    }
    
    UIFont *font = [UIFont systemFontOfSize:14.f];
    if (_commentoppsiteModel) {
        if (indexPath.section) {
            if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"appraise_chengxin"];
                cell.textLabel.font = font;
                cell.textLabel.text = evaBusinessSatified;
                
                TPFloatRatingView *ratingView = [self creatRatingView];
                ratingView.rating = [_commentoppsiteModel.satisfaction integerValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"appraise_mangyi"];
                cell.textLabel.font = font;
                cell.textLabel.text = evaBusinessInterd;
                
                TPFloatRatingView *ratingView = [self creatRatingView];
                ratingView.rating = [_commentoppsiteModel.credit integerValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 3) {
                UILabel *tipLabel = [UILabel labelWithTitle:@"详细评价"];
                tipLabel.frame = CGRectMake(15, 10, 120, 20);
                tipLabel.font = [UIFont systemFontOfSize:14.f];
                tipLabel.textColor = RGB(100, 100, 100, 1);
                [cell.contentView addSubview:tipLabel];
                
                UILabel *detailLabel = [UILabel labelWithTitle:@" "];
                detailLabel.font = font;
                detailLabel.numberOfLines = 0;
                detailLabel.text = _commentoppsiteModel.evaluation ? _commentoppsiteModel.evaluation : @"暂无详细评价";
                detailLabel.frame = CGRectMake(tipLabel.vleft, tipLabel.vbottom+5, self.view.vwidth-30, 0);
                [detailLabel sizeToFit];
                [cell.contentView addSubview:detailLabel];
            }
        }
    }else {
        if (indexPath.row == 1 && indexPath.section == 1) {
           cell.textLabel.text = noEvaStr;
            cell.textLabel.textColor = [UIColor grayColor];
        }
    }
    
    if (_commentmeModel) {
        if (indexPath.section == 0) {
            if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"appraise_chengxin"];
                cell.textLabel.font = [UIFont systemFontOfSize:15.f];
                cell.textLabel.text = evaBusinessSatified;
                
                TPFloatRatingView *ratingView = [self creatRatingView];
                ratingView.rating = [_commentmeModel.satisfaction integerValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"appraise_mangyi"];
                cell.textLabel.font = [UIFont systemFontOfSize:15.f];
                cell.textLabel.text = evaBusinessInterd;
                
                TPFloatRatingView *ratingView = [self creatRatingView];
                ratingView.rating = [_commentmeModel.credit integerValue];
                [cell.contentView addSubview:ratingView];
            }else if (indexPath.row == 3) {
                UILabel *tipLabel = [UILabel labelWithTitle:@"详细评价"];
                tipLabel.frame = CGRectMake(15, 10, 120, 20);
                tipLabel.font = [UIFont systemFontOfSize:14.f];
                tipLabel.textColor = RGB(100, 100, 100, 1);
                [cell.contentView addSubview:tipLabel];
                
                UILabel *detailLabel = [UILabel labelWithTitle:@" "];
                detailLabel.font = font;
                detailLabel.numberOfLines = 0;
                detailLabel.text = _commentmeModel.evaluation ? _commentmeModel.evaluation : @"暂无详细评价";
                detailLabel.frame = CGRectMake(tipLabel.vleft, tipLabel.vbottom+5, self.view.vwidth-30, 0);
                [detailLabel sizeToFit];
                [cell.contentView addSubview:detailLabel];
            }

        }
    }else {
        if (indexPath.row == 1 && indexPath.section == 0) {
           cell.textLabel.text = oppsiteNoEva;
            cell.textLabel.textColor = [UIColor grayColor];
        }
    }
    
    return cell;
}

/**
 *@brief 计算文本高度
 */
- (float)heightOfLabelSize:(NSString *)text {
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, SCREEN_WIDTH-30, 0)];
    label.numberOfLines = 0;
    label.font = [UIFont systemFontOfSize:14.f];
    label.text = text;
    [label sizeToFit];
    return label.vheight;
}

- (TPFloatRatingView *)creatRatingView {
    TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.view.vwidth-130, 8.5, 120, 30)];
    ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
    ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
    return ratingView;
}

@end
