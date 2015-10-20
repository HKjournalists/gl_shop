/**
 *
 */
package com.appabc.cms.web;

import java.io.File;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.appabc.bean.enums.FileInfo;
import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.base.exception.BusinessException;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.tools.utils.SystemParamsManager;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年9月23日 下午7:33:35
 */
@Controller
@RequestMapping(value="/file")
public class FileController extends BaseController<TUploadImages> {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUploadImagesService uploadImagesService;
	
	@Autowired
	private SystemParamsManager systemParamsManager;
	
	private int fileNO = 0; // 文件数
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value="/upload/",method={RequestMethod.POST})
	public @ResponseBody Object uploadImg(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,ModelMap model){
		
		logger.debug("文件上传开始");
		String relativePath =request.getParameter("relativePath");
		if(StringUtils.isEmpty(relativePath)){
			relativePath = File.separator;
		}else{
			relativePath = File.separator + relativePath +File.separator;
		}
		
		String absolutePathFileDir =systemParamsManager.getString(SystemConstant.UPLOADFILE_DIR);
		if(StringUtils.isEmpty(absolutePathFileDir)){
			throw new BusinessException("Store the File Dir is Null.");
		}
		/*****文件保存，后期改为调存储接口************************/
        String fileName = file.getOriginalFilename();  
        Long fileSize = file.getSize();
        logger.info(absolutePathFileDir+relativePath);
        String storeFileName = RandomUtil.getUUID()+(fileName.lastIndexOf(".")!=-1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : StringUtils.EMPTY);
        File targetFile = new File(absolutePathFileDir+relativePath, storeFileName);  
        
        if(!targetFile.exists()){  
            targetFile.mkdirs();
        }
  
        //保存  
        try {
        	logger.debug("文件保存开始");
            file.transferTo(targetFile); 
            fileNO++;
            logger.debug("文件保存结束");
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.debug(e.getMessage(), e.getCause());
            throw new BusinessException(e);
        }  
        
        String uploadFileDomain =systemParamsManager.getString(SystemConstant.UPLOADFILE_DOMAIN);
        String fullRelativePath =request.getParameter("relativePath");
        String fullPath = "";
        if(StringUtils.isEmpty(fullRelativePath)){
        	fullPath = "/"+storeFileName;
		}else{
			fullPath = "/" + fullRelativePath +"/"+storeFileName;
		}
        /******文件信息存储****************************************/
		TUploadImages ui = new TUploadImages();
		ui.setCommitserver(FileInfo.FileCommitServer.COMMIT_SERVER_GLSHOPHTTP);
		ui.setCreatedate(Calendar.getInstance().getTime());
		ui.setFname(fileName);
		ui.setFpath(relativePath+storeFileName);
		ui.setFsize(fileSize);
		ui.setFtype(file.getContentType());
		ui.setFullpath(uploadFileDomain+fullPath);
		ui.setFserverid("FILESERVERID0001");
		ui.setFstyle(FileInfo.FileStyle.FILE_STYLE_ORIGINAL);
		try {
			this.uploadImagesService.add(ui);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String sortimgid=ui.getId();
		logger.debug("文件上传个数：FileNO="+fileNO);		
		return this.buildFilterResultWithBean(sortimgid);
	}
}
