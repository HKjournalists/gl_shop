/**
 *
 */
package com.appabc.datas.codes;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.appabc.bean.pvo.TPublicCodes;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.codes.IPublicCodesService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年8月29日 下午2:55:39
 */
public class PublicCodesTest extends AbstractDatasTest{
	
	@Autowired
	private IPublicCodesService publicCodesService;
	
//	@Test
//	@Rollback(value=false)
	public void testAdd(){
		
		TPublicCodes t = new TPublicCodes();
		t.setCode("RIVER_STAGE");
		t.setName("靖江段");
		t.setVal("RS001");
		
		this.publicCodesService.add(t);
	}
	
//	@Test
//	@Rollback(value=false)
	public void update(){
		TPublicCodes t = this.publicCodesService.query(3);
		t.setCode("RIVER_SECTION");
		this.publicCodesService.modify(t);
	}

	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Override
	@Test
	public void mainTest() {
		// TODO Auto-generated method stub
		
	}

}
