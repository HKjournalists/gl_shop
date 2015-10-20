/**  
 * com.appabc.tools.mail.SendMessageTestAll.java  
 *   
 * 2015年9月14日 下午3:54:08  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.datas.email;

import java.io.File;

import org.junit.Test;

import com.appabc.datas.AbstractDatasTest;
import com.appabc.tools.mail.javamail.MailInfo.SendMailType;
import com.appabc.tools.mail.javamail.MailMessageSender;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月14日 下午3:54:08
 */
public class SendMessageTestAll extends AbstractDatasTest{
	
	public void sendDefaultEmail(){
		MailMessageSender mms = new MailMessageSender();
		mms.setTo("276261562@qq.com");
	    mms.setSubject("使用spring email 测试发送文本消息").setText("正文消息了。。。。").send();/**/  
	}
	
	public void sendHtmlEmail(){
		MailMessageSender mms = new MailMessageSender(SendMailType.HTML);
		mms.setTo("276261562@qq.com");
        mms.setSubject("使用spring email 测试发送Html中文邮件")  
        .setText("<html><head><meta http-equiv='content-type' content='text/html; charset=GBK'>" +  
                "</head><body><h1>这里是咫尺天涯发送的邮件</h1></body></html>").send();
	}
	
	public void sendIncludeImageEmail(){
		MailMessageSender mms = new MailMessageSender(SendMailType.HTML);
		mms.setTo("276261562@qq.com");
        String text = "<html><body>欢迎访问咫尺天涯博客<br>"  
               + "<a href='http://liuzidong.iteye.com/' target='_blank'>"  
               + "<img src='cid:belle'></a><br><img src='cid:belle2'></body></html>";    
        //附件要嵌入到HTML中显示，则在IMG标签中用"cid:XXX"标记。                 
       mms.setSubject("使用spring email 测试邮件中嵌套图片");   
       mms.setText(text);  
       File file = new File("D:\\PROJECT\\UIUE\\高保真\\20140826\\我的供求jpg\\我的供求无发布信息模式.jpg");            
       mms.setImgFile("belle", file);        
       File file2 = new File("D:\\PROJECT\\UIUE\\高保真\\20140826\\我的供求jpg\\b我发布的供求（发布的求）状态_【无效】用户取消.jpg");  
       mms.setImgFile("belle2", file2);      
       mms.send();
	}
	
	public void sendIncludeAttachmentFileEmail(){
		MailMessageSender mms = new MailMessageSender(SendMailType.HTML);
		mms.setTo("276261562@qq.com");
        mms.setSubject("使用spring email 测试发送附件");  
        File file = new File("C:\\Users\\Administrator\\Desktop\\MySQL性能调优与架构设计.pdf");  
        mms.setAttachmentFile("MySQL性能调优与架构设计.pdf", file).send();
	}
	
	public void sendIncludeAllEmail(){
		MailMessageSender mms = new MailMessageSender(SendMailType.HTML);
		mms.setTo("276261562@qq.com");
        mms.setSubject("使用spring email 测试发送邮件<包含html,img,file>等格式");        
        mms.setText("这是我的文本格式");  
        mms.setText("<html><head><h1>这里是咫尺天涯发送的邮件</h1></head><body>" +  
                "<h1><a href='http://liuzidong.iteye.com/' target='_blank'></h1><br>" +  
                "<img src='cid:belle'></a><br><img src='cid:belle2'>"+  
                "</body></html>");  
         //附件要嵌入到HTML中显示，则在IMG标签中用"cid:XXX"标记。   
        File file = new File("D:\\PROJECT\\UIUE\\高保真\\20140826\\我的供求jpg\\我的供求无发布信息模式.jpg");  
        mms.setImgFile("belle", file);        
        File file2 = new File("D:\\PROJECT\\UIUE\\高保真\\20140826\\我的供求jpg\\b我发布的供求（发布的求）状态_【无效】用户取消.jpg");  
        mms.setImgFile("belle2", file2);          
        File file3 = new File("C:\\Users\\Administrator\\Desktop\\cliqrcode1428463124.zip");  
        mms.setAttachmentFile("cliqrcode1428463124.zip", file3);          
        File file4 = new File("C:\\Users\\Administrator\\Desktop\\MySQL性能调优与架构设计.pdf");  
        mms.setAttachmentFile("MySQL性能调优与架构设计.pdf", file4);       
        mms.send();
	}
	
	/* (non-Javadoc)  
	 * @see com.appabc.datas.AbstractDatasTest#mainTest()  
	 */
	@Test
	@Override
	public void mainTest() {
		sendIncludeAllEmail();
	}
	
}
