package com.appabc.datas.banner;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.appabc.bean.enums.ClientEnum.ClientType;
import com.appabc.bean.pvo.TClientBanner;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.banner.IClientBannerService;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年8月10日 上午11:37:45
 */

public class CientBannerTest extends AbstractDatasTest {

	@Autowired
	private IClientBannerService clientBannerService;
	
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
		//queryBannerByBtype();
		//deleteBannerbyBid();
		//addBanner();
		//updateBannerByBid();
	}
	
	public void queryBannerByBtype()
	{
		ClientType btype=ClientType.ANDROID;
		TClientBanner entity=new TClientBanner();
		entity.setBtype(btype);
		
		List <TClientBanner> clientBannerList = this.clientBannerService.queryForList(entity);
		log.info(clientBannerList);
	}
	
	public void deleteBannerbyBid()
	{
		/*for(int i = 1; i < 35; i++){
			String Bid=""+i;
			this.clientBannerService.delete(Bid);
		}*/
		String Bid="37";
		this.clientBannerService.delete(Bid);
	}
	
	
	public void addBanner()
	{
		TClientBanner cb=new TClientBanner();
		cb.setBtype(ClientType.ANDROID);
		cb.setBname("junistest1");
		cb.setSortimgid("123");
		cb.setThumedid("123");
		cb.setOrderno(1);
		cb.setCreater("hmj");
		cb.setUpdater("hmj");
		cb.setCreatetime(Calendar.getInstance().getTime());
		cb.setUpdatetime(Calendar.getInstance().getTime());
		cb.setTargeturl("http://www.baidu.com");
		cb.setRemark("沒有");
		try
		{
		this.clientBannerService.add(cb);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	public void updateBannerByBid()
	{
		TClientBanner cb=new TClientBanner();
		cb.setBid("37");
		cb.setBtype(ClientType.ANDROID);
		cb.setBname("junistest2");
		cb.setSortimgid("333");
		cb.setThumedid("333");
		cb.setOrderno(2);
		cb.setCreater("justme");
		cb.setUpdater("justme");
		cb.setCreatetime(Calendar.getInstance().getTime());
		cb.setUpdatetime(Calendar.getInstance().getTime());
		cb.setTargeturl("http://www.qq.com");
		cb.setRemark("沒有啊");
		this.clientBannerService.modify(cb);
	}

}
