/**  
 * com.appabc.tools.mail.MailMessageFactory.java  
 *   
 * 2015年9月11日 下午6:12:09  
 * Copyright APPABC Information System Co.Ltd. All rights reserved.
 */
package com.appabc.tools.mail.javamail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.appabc.common.spring.BeanLocator;
import com.appabc.tools.mail.javamail.MailInfo.MailType;
import com.appabc.tools.mail.javamail.MailInfo.SendMailType;

/**
 * @Description : http://liuzidong.iteye.com/blog/1114783
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 黄建华
 * @version     : 1.0
 * @Create_Date  : 2015年9月11日 下午6:12:09
 */

public class MailMessageSender {

	private static final Logger log = Logger.getLogger(MailMessageSender.class);
	
	private JavaEmailConfig javaEmailConfig = BeanLocator.getBean(JavaEmailConfig.class);
	private JavaMailSender javaMailSender = (JavaMailSender)BeanLocator.getBean("mailSender");
	private SimpleMailMessage simpleMailMessage;  
    private MimeMessageHelper mimeMessageHelper;
    private MimeMessage mimeMessage;  
    private SendMailType sendMailType;    
    private SendMessage sendMessage;      
	
	public MailMessageSender(){
		this(SendMailType.HTML);
	}
	
	public MailMessageSender(SendMailType sendMailType){
		this.sendMailType = sendMailType;  
        this.mimeMessage = javaMailSender.createMimeMessage();                
        sendMessage = new SendMessage();      
        try {  
            switch (this.sendMailType) {  
            case TEXT:  
                simpleMailMessage = new SimpleMailMessage();  
                break;  
            case HTML:    
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, javaEmailConfig.getEmail_encoding());  
            }  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);
        }
	}
	
	/** 
     * @param to 接收者人地址 
     * @return 
     */  
    public MailMessageSender setTo(String ... to){       
        sendMessage.setTos(to);       
        return this;  
    }
    
    /**发送标题*/  
    public MailMessageSender setSubject(String subject){  
        sendMessage.setSubject(subject);  
        return this;  
    }  
      
    /**发送内容*/  
    public MailMessageSender setText(String text){  
        sendMessage.addText(text);  
        return this;  
    }
    
    /** 
     * 加入图片文件 
     * @param imageName  图片名称 
     * @param file   图片源 
     * @return 
     */  
    public MailMessageSender setImgFile(String imageName,File file){     
        try {  
            sendMessage.addFile(MailType.IMG,MimeUtility.encodeWord(imageName), file);                    
        } catch (UnsupportedEncodingException e) {            
            log.error(e.getMessage(), e);
        }         
        return this;  
    }  
      
      
    /** 
     * 加入图片文件 
     * @param imageName  图片名称 
     * @param filePath   图片路径 
     * @return 
     */  
    public MailMessageSender setImgFile(String imageName,String filePath){           
        setImgFile(imageName, new File(filePath));        
        return this;  
    }  
      
    /** 
     * 加入附件 
     * @param fileName  附件名称 
     * @param file      附件路径 
     * @return 
     */  
    public MailMessageSender setAttachmentFile(String fileName,String filePath){     
        setAttachmentFile(fileName, new File(filePath));          
        return this;  
    }  
      
  
    /** 
     * 加入附件 
     * @param fileName  附件名称 
     * @param file      附件源  
     * @return 
     */  
    public MailMessageSender setAttachmentFile(String fileName,File file){           
        try {  
            sendMessage.addFile(MailType.FILE,MimeUtility.encodeWord(fileName),file);                     
        } catch (UnsupportedEncodingException e) {            
            log.error(e.getMessage(), e);
        }     
        return this;  
    }
    
    /**发送消息,你调用此方法进行Email消息的发送*/  
    public void send(){
    	if(sendMessage == null){
        	log.warn(sendMailType + "失败!, sendMessage 为空!");
        	return ;
        }
        //消息发送前,检查发送人地址,接收人地址是否为空,为空就设置为配置文件中的地址          
        sendMessage.setForm(javaEmailConfig.getEmail_from());  
        //检查接收人地址是否为空  
        String [] tos = sendMessage.getTos();  
        if(null == tos || tos.length == 0){
        	String targets = javaEmailConfig.getEmail_to();
        	if(StringUtils.isNotEmpty(targets)){
		    	if(targets.indexOf(",") != -1){
		    		tos = targets.split(",");
		    	}else{
		    		tos = new String[]{targets};
		    	}
		        sendMessage.setTos(tos); 
        	}
        }                 
        
        if(sendMessage.getTos() == null || sendMessage.getTos().length == 0){
        	log.warn(sendMailType + "失败!, sendMessage 	接收人为空!");
        	return ;
        }
        
        long startTime = System.currentTimeMillis();  
        //发送普通文本  
        if(sendMailType == SendMailType.TEXT){  
            sendTextMessage();  
        }else{            
            sendHtmlImgFileMessage();  
        }         
        long endTime = System.currentTimeMillis();
        log.info(sendMailType + "到 "+  Arrays.toString(sendMessage.getTos()) +" 成功!,耗费时间: " + (endTime - startTime)+"毫秒!");
    }
    
    /** 
     * 发送html,图片,附件的消息 
     */  
    private void sendHtmlImgFileMessage() {  
          
        try {             
            mimeMessageHelper.setTo(sendMessage.getTos());  
            mimeMessageHelper.setFrom(sendMessage.getForm());  
            String subject = sendMessage.getSubject();  
            if(StringUtils.isNotEmpty(subject)){  
                mimeMessageHelper.setSubject(subject);  
            }  
            String text = sendMessage.getSendTexts();  
            if(StringUtils.isNotEmpty(text)){  
                mimeMessageHelper.setText(text,true);  
            }             
            //检查图片或者文件集合是否为空  
            int imgSize = sendMessage.getImages().size();     
            if(imgSize > 0){  
                    List<SendMessage> lists = sendMessage.getImages();  
                    FileSystemResource fsr = null;  
                    String imgName = null;  
                    for(SendMessage entity : lists){  
                        fsr = new FileSystemResource(entity.getFile());  
                        imgName = entity.getImgName();  
                        //如果为图片类型就调用addInline,否则就调用:addAttachment方法^_^  
                        if(entity.getMailType() == MailType.IMG) {
                        	mimeMessageHelper.addInline(imgName, fsr);  
                        }else{
                        	mimeMessageHelper.addAttachment(imgName, fsr);                                
                        }   
                    }  
                    fsr = null;               
            }     
        } catch (MessagingException e) {                  
            log.error(e.getMessage(), e);
        }  
        javaMailSender.send(mimeMessage);  
    }  
  
    /** 
     * 发送文本消息 
     */  
    private void sendTextMessage() {  
        simpleMailMessage.setTo(sendMessage.getTos());    
        simpleMailMessage.setFrom(sendMessage.getForm());  
        String subject = sendMessage.getSubject();  
        if(StringUtils.isNotEmpty(subject)){  
            simpleMailMessage.setSubject(subject);  
        }         
        String text = sendMessage.getSendTexts();  
        if(StringUtils.isNotEmpty(text)){  
            simpleMailMessage.setText(text);  
        }     
        javaMailSender.send(simpleMailMessage);  
    }
    
    /**  
     * @param subject       发送标题  
     * @param text          发送内容         
     * @param to            接收人地址 
     */  
    public void send(String subject,String text,String ... to){       
        sendMessage(subject, text, to);               
    }  
      
      
  
    private void sendMessage(String subject, String text, String... to) {  
        setTo(to);  
        setSubject(subject);  
        setText(text);  
    }  
      
      
  
    public SendMessage getSendMessage() {  
        return sendMessage;  
    }  
  
    /*** 
     * 接收一个消息对象 
     * */  
    public MailMessageSender setSendMessage(SendMessage sendMessage) {  
        this.sendMessage = sendMessage;       
        return this;  
    }

}
