/**
 *
 */
package com.appabc.datas.system;

import com.appabc.bean.bo.ViewImgsBean;
import com.appabc.bean.enums.FileInfo;
import com.appabc.datas.AbstractDatasTest;
import com.appabc.datas.service.system.IUploadImagesService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/**
 * @Description :
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月14日 上午10:26:17
 */
public class UploadImgTest extends AbstractDatasTest {

	@Autowired
	private IUploadImagesService uploadImgService;

	/* (non-Javadoc)
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()
	 */
	@Override
	@Test
	@Rollback(value=false)
	public void mainTest() {
//		updateOtypeAndOidTest();
//		getViewImgsByOidAndOtypeTest();
	}

	public void updateOtypeAndOidTest() {
		uploadImgService.updateOtypeAndOid("29", FileInfo.FileOType.FILE_OTYPE_ADDRESS, "3017");
	}
	public void getViewImgsByOidAndOtypeTest() {
		List<ViewImgsBean> list = uploadImgService.getViewImgsByOidAndOtype("33", "2");
		System.out.println(list);
	}

}
