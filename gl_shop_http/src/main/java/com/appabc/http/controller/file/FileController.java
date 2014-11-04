/**
 *
 */
package com.appabc.http.controller.file;

import java.io.File;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.appabc.bean.pvo.TUploadImages;
import com.appabc.common.base.controller.BaseController;
import com.appabc.common.base.exception.BusinessException;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.SystemConstant;
import com.appabc.datas.enums.FileInfo;
import com.appabc.datas.service.system.IUploadImagesService;
import com.appabc.http.utils.EveryUtil;
import com.appabc.http.utils.PicThumbnailUtil;
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
	
	@Autowired
	private IUploadImagesService uploadImagesService;
	
	@Autowired
	private SystemParamsManager systemParamsManager;
	
	/**
	 * 文件上传
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload",method=RequestMethod.POST)
	public Object upload(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request,HttpServletResponse response){
		
		String relativePath = request.getParameter("relativePath");
		if(StringUtils.isEmpty(relativePath)){
			relativePath = File.separator;
		}else{
			relativePath = File.separator + relativePath +File.separator;
		}
		
		String absolutePathFileDir = systemParamsManager.getString(SystemConstant.UPLOADFILE_DIR);
		if(StringUtils.isEmpty(absolutePathFileDir)){
			throw new BusinessException("store the file dir is null.");
		}
		/*****文件保存，后期改为调存储接口************************/
        String fileName = file.getOriginalFilename();  
        Long fileSize = file.getSize();
        log.info(absolutePathFileDir+relativePath);
        String storeFileName = RandomUtil.getUUID()+(fileName.lastIndexOf(".")!=-1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : StringUtils.EMPTY);
        File targetFile = new File(absolutePathFileDir+relativePath, storeFileName);  
        
        if(!targetFile.exists()){  
            targetFile.mkdirs();
            File thumbPath =  new File(absolutePathFileDir+relativePath+"/thumb/"); // 创建略图目录
            thumbPath.mkdirs();
           
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {
        	e.printStackTrace();
        	log.debug(e.getMessage(), e.getCause());
            throw new BusinessException(e);
        }  
        
        String uploadFileDomain = systemParamsManager.getString(SystemConstant.UPLOADFILE_DOMAIN);
        String fullRelativePath = request.getParameter("relativePath");
        String fullPath = "";
        if(StringUtils.isEmpty(fullRelativePath)){
        	fullPath = EveryUtil.URLPATHSEPARATOR+storeFileName;
		}else{
			fullPath = EveryUtil.URLPATHSEPARATOR + fullRelativePath +EveryUtil.URLPATHSEPARATOR+storeFileName;
		}
        /******文件信息存储****************************************/
		TUploadImages ui = new TUploadImages();
		ui.setCommitserver(FileInfo.FileCommitServer.COMMIT_SERVER_GLSHOPHTTP.getVal());
		ui.setCreatedate(Calendar.getInstance().getTime());
		ui.setFname(fileName);
		ui.setFpath(relativePath+storeFileName);
		ui.setFsize(fileSize);
		ui.setFtype(file.getContentType());
		ui.setFullpath(uploadFileDomain+fullPath);
		ui.setFserverid("FILESERVERID0001");
		ui.setFstyle(FileInfo.FileStyle.FILE_STYLE_ORIGINAL.getVal());
		this.uploadImagesService.add(ui);
		
		/******略图生成*************************************************/
		if(file.getContentType().indexOf("image") > -1) { // 图片
			
			String thumbFileName = "thumb100"+storeFileName;
			
			
			PicThumbnailUtil ptu = new PicThumbnailUtil();
			ptu.generateThumbnail(absolutePathFileDir+relativePath, absolutePathFileDir+relativePath+"/thumb/", storeFileName, thumbFileName, 100, 100, false);
			
			File thumbFile = new File(absolutePathFileDir+relativePath+"/thumb/"+thumbFileName);
			
			TUploadImages uiThumb = new TUploadImages();
			uiThumb.setCommitserver(FileInfo.FileCommitServer.COMMIT_SERVER_GLSHOPHTTP.getVal());
			uiThumb.setCreatedate(Calendar.getInstance().getTime());
			uiThumb.setFname(thumbFileName);
			uiThumb.setFpath(relativePath+"/thumb/"+thumbFileName);
			uiThumb.setFsize(thumbFile.length());
			uiThumb.setFtype(file.getContentType());
			uiThumb.setFullpath(uploadFileDomain+"/thumb/"+thumbFileName);
			uiThumb.setFserverid("FILESERVERID0001");
			uiThumb.setFstyle(FileInfo.FileStyle.FILE_STYLE_ORIGINAL.getVal());
			uiThumb.setPid(Integer.parseInt(ui.getId()));
			this.uploadImagesService.add(uiThumb);
			ui.setThumbPic(uiThumb.getFullpath());
		}
		
		return this.buildFilterResultWithBean(ui, "createdate","fname","fpath","fsize","ftype","fserverid","commitserver");
	}

}
