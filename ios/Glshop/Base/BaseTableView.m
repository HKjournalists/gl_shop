//
//  BaseTableView.m
//  PromoterClient
//
//  Created by ios on 14-7-21.
//  Copyright (c) 2014年 TCL. All rights reserved.
//

#import "BaseTableView.h"

@implementation BaseTableView

- (id)initWithFrame:(CGRect)frame style:(UITableViewStyle)style
{
    self = [super initWithFrame:frame style:style];
    if (self) {
        _pageIndex = 1;
        _dataArray = [NSArray array];
        _refreshControl = [[ODRefreshControl alloc] initInScrollView:self];
        [self _initView];
    }
    return self;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    cell.textLabel.text = self.dataArray[indexPath.row];
    return cell;
}

- (void)_initView
{
//    _freshHeaderView = [[EGORefreshTableHeaderView alloc] initWithFrame:CGRectMake(0, 0.0f - self.bounds.size.height, self.frame.size.width, self.bounds.size.height)];
//    _freshHeaderView = [[EGORefreshTableHeaderView alloc] initWithFrame:CGRectZero];
    _freshHeaderView.delegate = self;
    _freshHeaderView.backgroundColor = [UIColor clearColor];
    
    self.dataSource = self;
    self.delegate = self;
    
    // 加载尾部视图
    _moreButton = [UIButton buttonWithType:UIButtonTypeCustom];// 取得所有权，否则自动释放池管理
    _moreButton.backgroundColor = [UIColor clearColor];
    _moreButton.frame = CGRectMake(0, 0, SCREEN_WIDTH, 40);// 高度可以bu设定
    _moreButton.titleLabel.font = [UIFont systemFontOfSize:16.0f];
    [_moreButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [_moreButton setTitle:@"上拉加载更多" forState:UIControlStateNormal];
//    [_moreButton addTarget:self action:@selector(loadMoreAction) forControlEvents:UIControlEventTouchUpInside];
    
    
    UIActivityIndicatorView *activityView = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    activityView.frame = CGRectMake(100, 10, 20, 20);
    activityView.tag = 2013;
    [activityView stopAnimating];// 禁止转动
    [_moreButton addSubview:activityView];

    self.tableFooterView = _moreButton;
    
}


// 上拉加载
- (void)_starLoadMore {
    
    [_moreButton setTitle:@"正在加载..." forState:UIControlStateNormal];
    _moreButton.enabled = NO;
    UIActivityIndicatorView *activityView = (UIActivityIndicatorView *)[_moreButton viewWithTag:2013];
    [activityView startAnimating];
}

- (void)_stopLoadMore {
    
#warning 待完善
    if (self.dataArray.count > 0) {
        _moreButton.hidden = NO;
        [_moreButton setTitle:@"上拉加载..." forState:UIControlStateNormal];
        _moreButton.enabled = YES;
        UIActivityIndicatorView *activityView = (UIActivityIndicatorView *)[_moreButton viewWithTag:2013];
        [activityView stopAnimating];
        
        if (!self.isMore) {
            [_moreButton setTitle:@"没有更多了" forState:UIControlStateNormal];
            _moreButton.enabled = NO;
            _moreButton.hidden = YES;
        }
    }else {
        _moreButton.hidden = YES;
    }
}

// 覆写
- (void)reloadData {
    [super reloadData];
    // 停止加载更多
    [self _stopLoadMore];
}

// 在设置refreshHeader时候,确定是添加还是移除_freshHeaderView
- (void)setRefreshHeader:(BOOL)refreshHeader
{
    _refreshHeader = refreshHeader;
    if (_refreshHeader) {
        [self addSubview:_freshHeaderView];
    }else {
        // 正确移除视图
        if ([_freshHeaderView superview]) {
            [_freshHeaderView removeFromSuperview];
        }
    }
}


#pragma mark Data Source Loading / Reloading Methods
// 自动更新数据
- (void)refreshData
{
    [_freshHeaderView initLoading:self];
}

- (void)reloadTableViewDataSource{
    _reloading = YES;
    
}

- (void)doneLoadingTableViewData{
    _reloading = NO;
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationDuration:.3];
    //	[scrollView setContentInset:UIEdgeInsetsMake(0.0f, 0.0f, 0.0f, 0.0f)];
    self.contentInset = UIEdgeInsetsMake(0.f, 0.0f, -40, 0.0f);
    [UIView commitAnimations];
    
//    [self setState:EGOOPullRefreshNormal];
    [_freshHeaderView egoRefreshScrollViewDataSourceDidFinishedLoading:self];
}

#pragma mark UIScrollViewDelegate Methods

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    [_freshHeaderView egoRefreshScrollViewDidScroll:scrollView];
    
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
    
    [_freshHeaderView egoRefreshScrollViewDidEndDragging:scrollView];
    
    if (!self.isMore) {
        return;
    }
    float offset = scrollView.contentOffset.y;
    float contentHeight = scrollView.contentSize.height;
    NSLog(@"%f-------%f",offset,contentHeight);
    // 当偏移量滑到最底部时，差值是scrollerView的高度,用fmaxf来取与0比较后较大值的原因是，当scrollView内容为空时scrollView.contentSize.height可能是0
    if (offset >= fmaxf(.0f, contentHeight - CGRectGetHeight(scrollView.frame)) + 30)  {
        [self _starLoadMore];
        self.contentInset = UIEdgeInsetsMake(0.f, 0.0f, 0, 0.0f);
        if ([self.eventDelegate respondsToSelector:@selector(pullUp:)]) {
            [self.eventDelegate pullUp:self];
        }
    }
    
}

#pragma mark EGORefreshTableHeaderDelegate Methods
//下拉到一定距离，手指放开时调用
- (void)egoRefreshTableHeaderDidTriggerRefresh:(EGORefreshTableHeaderView*)view
{
    [self reloadTableViewDataSource];
    
    //停止加载，弹回下拉
    //    [self performSelector:@selector(doneLoadingTableViewData) withObject:nil afterDelay:3.0];
//    if ([self.eventDelegate respondsToSelector:@selector(pullDown:)]) {
//        [self.eventDelegate pullDown:self];
//    }
}

- (BOOL)egoRefreshTableHeaderDataSourceIsLoading:(EGORefreshTableHeaderView*)view
{
    return _reloading;
}

- (NSDate*)egoRefreshTableHeaderDataSourceLastUpdated:(EGORefreshTableHeaderView*)view
{
    return [NSDate date];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if ([self.eventDelegate respondsToSelector:@selector(tableView:didSelectRowAtIndexPath:)]) {
        [self.eventDelegate tableView:self didSelectRowAtIndexPath:indexPath];
    }
}

@end

