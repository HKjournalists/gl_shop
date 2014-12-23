/**
 * Copyright (c) 2013-2014 http://www.appabc.com/
 *
 */
package com.appabc.datas.user;


import com.appabc.bean.enums.UserInfo.UserStatus;
import com.appabc.bean.pvo.TUser;
import com.appabc.common.base.QueryContext;
import com.appabc.common.base.QueryResult;
import com.appabc.common.utils.SystemConstant.OrderEnum;
import com.appabc.common.utils.security.BaseCoder;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.user.IUserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

/**
 * @author yyh
 * @date 2014年8月14日
 */
//
public class UserTest extends AbstractDatasTest{

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private IUserService userService;
	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		encryptMd5AllUser();

	}

	public void testQ(){
		/*testUserSave();
		testUserQuerySingle(8);
		testUserQuerySingle(9);
		testUserUpdate(10);
		testUserQuery();*/
		//testUserSave();
		//testUserDelete(9);
		//testUserUpdate(35);
//		testQueryTuserByEntity();
	}

	public void encryptMd5AllUser(){
		List<TUser> list = userService.queryForList(new TUser());

		for(TUser user : list){
//			user.setPhone(user.getUsername());
			try {
				user.setPassword(BaseCoder.encryptMD5(user.getUsername()+user.getPassword()));
			} catch (Exception e) {
				e.printStackTrace();
			}

//			this.userService.modify(user);
		}
	}

	public void testQueryTuserByEntity(){
		TUser user = new TUser();
		user.setUsername("15811822330");

		user = this.userService.query(user);
		System.out.println(user);
	}

	public void testUserUpdate(int id){
		TUser user = new TUser();
		user.setId(String.valueOf(id));
		user.setPassword("654321");
		user.setUsername("aa");
		user.setNick("没有");
		user.setPhone("13856239874");
		user.setStatus(UserStatus.enumOf("1"));
		user.setLogo("logo");
		Date now = new Date();
		user.setCreatedate(now);
		user.setUpdatedate(now);
		userService.modify(user);
	}

	public void testUserSave(){
		TUser user = new TUser();
		user.setPassword("852963");
		user.setUsername("uuuu");
		user.setNick("木有");
		user.setPhone("12345678965");
		user.setStatus(UserStatus.enumOf("1"));
		user.setLogo("logo");
		user.setCreatedate(new Date());
		userService.add(user);
	}

	public void testUserDelete(int i){
		TUser user = new TUser();
		user.setId(String.valueOf(i));
		userService.delete(user);
	}

	public void testUserQuerySingle(int id){
		TUser user = new TUser();
		user.setId(String.valueOf(id));
		user = userService.query(user);
		System.out.println(user);
	}

	//@Rollback(value=false)
	public void testUserQuery(){
		QueryContext<TUser> qContext = new QueryContext<TUser>();
		qContext.setOrderColumn("ID");
		qContext.setOrder(OrderEnum.ORDERDESC.getText());
		qContext.getPage().setPageIndex(-1);
		qContext.getPage().setPageSize(5);
		qContext = userService.queryListForPagination(qContext);
		QueryResult<TUser> qr = qContext.getQueryResult();
		for(TUser u : qr.getResult()){
			System.out.println(u);
		}
		System.out.println(qContext.getQueryResult().getResult().size());
		System.out.println(qContext.getQueryResult().getTotalSize());
	}


}
