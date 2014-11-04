package com.appabc.datas.pk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.appabc.bean.pvo.TPk;
import com.appabc.common.utils.RandomUtil;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.service.pk.IPKService;
import com.appabc.tools.utils.PrimaryKeyGenerator;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * Create Date  : 2014年8月26日 下午5:58:42
 */

public class PKTest extends AbstractDatasTest {

	@Autowired
	private IPKService service;
	
	@Autowired
	private PrimaryKeyGenerator prkg;
	
	private Map<Integer,Boolean> runningStatus = new HashMap<Integer,Boolean>();
	
	private boolean isRunning = false;
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Test
	@Rollback(value=true)
	public void mainTest() {
		//testInsertTPK();
		//testThreadGetId();
	}
	
	void testThreadGetId(){
		isRunning = true;
		long start = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++){
			final int index = i;
			runningStatus.put(index, false);
			Thread r = new Thread() {
				public void run() {
					testR();
					runningStatus.put(index, true);
					
					for(Entry<Integer, Boolean> entry : runningStatus.entrySet()){
						if(!entry.getValue()){
							return;
						}
					}
					isRunning = false;
				}
			};
			r.start();
		}
		while(isRunning){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Use the time :  "+(end-start));
	}
	
	void testR(){
		String key = prkg.generatorBusinessKeyByBid("TESTID");
		log.fatal(key);
	}
	
	void testInsertTPK(){
		TPk entity = new TPk();
		entity.setId(RandomUtil.getUUID());
		entity.setBid("PASSID");
		entity.setBprefix("PASSID#year#month#day");
		entity.setBsuffix("#hour#minute#second");
		entity.setMaxval(99999);
		entity.setMinval(0);
		entity.setCurval(0);
		entity.setLength(5);
		entity.setCreatetime(new Date());
		service.add(entity);
	}

}
