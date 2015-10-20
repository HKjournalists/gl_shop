package com.appabc.cms.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.appabc.bean.bo.SystemMessageEx;
import com.appabc.bean.enums.MsgInfo.MsgBusinessType;
import com.appabc.bean.enums.MsgInfo.MsgStatus;
import com.appabc.bean.enums.MsgInfo.MsgType;
import com.appabc.bean.enums.SystemInfo.SystemCategory;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TPhonePackage;
import com.appabc.bean.pvo.TSystemMessage;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.CommonResult;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.RandomUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.system.IPhonePackageService;
import com.appabc.tools.service.system.ISystemMessageService;
import com.appabc.tools.utils.MessageSendManager;
import com.appabc.tools.utils.file.IFileParser;
import com.appabc.tools.utils.file.IFileParserFactory;

/**
 * @Description : 
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Administrator
 * @version     : 1.0
 * @Create      : 2015年7月7日 下午2:56:31
 */
@Controller
@RequestMapping("/tool/manage")
public class SystemToolController extends AbstractListBaseController {

	@Autowired
	private MessageSendManager MessageSendManager;
	
	@Autowired
	private ICompanyInfoService iCompanyInfoService;
	
	@Autowired
	private IPhonePackageService iPhonePackageService;
	
	@Autowired
	private ISystemMessageService iSystemMessageService;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/downloadPhonePackage/",method={RequestMethod.POST,RequestMethod.GET})
	public void downloadPhonePackage(HttpServletRequest request,HttpServletResponse response,ModelMap model,ModelAndView mav){
		/*String contextPath = request.getContextPath();
		String filePath = contextPath + "/WEB-INF/templates/tools/phone_number_template.zip";*/
		String filePath = request.getRealPath("/WEB-INF/templates/tools/phone_number_template.zip");
		/*logger.debug(filePath);
		//SpringTemplateEngine templateEngine = BeanLocator.getApplicationContext().getBean("templateEngine", SpringTemplateEngine.class);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		BeanNameUrlHandlerMapping s = wac.getBean(BeanNameUrlHandlerMapping.class);
		RequestMappingHandlerMapping d = wac.getBean(RequestMappingHandlerMapping.class);*/
		///WEB-INF/templates/tools/phone_number_template.zip
		//s.getTemplateEngine().getTemplateRepository().getTemplate("templates/tools/phone_number_template.zip");
		
		try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            if(!file.exists()){
            	return;
            }
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            logger.debug(ext);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
	}
	
	@RequestMapping(value="/uploadPhonePackage/",method={RequestMethod.POST})
	public @ResponseBody Object uploadPhonePackage(@RequestParam(value = "Filedata", required = true) CommonsMultipartFile file,HttpServletRequest request,ModelMap model){
		if(file == null){
			return CommonResult.getCommonResult(false, "文件不能为空.");
		}
		String realFileName = file.getOriginalFilename();
		IFileParser iFileParser = IFileParserFactory.getFileParser(realFileName);
		String str = StringUtils.EMPTY;
		try {
			str = iFileParser.readFileAsString(file.getInputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		boolean b = iFileParser.validatePhoneFileContentFormat(str);
		if(!b){
			return CommonResult.getCommonResult(false, "文件格式不对.");
		}
		String[] phones = iFileParser.splitStrToArrayByRegex(str, IFileParser.COMMA);
		String nos = iFileParser.joinStrArrToStrByRegex(phones, IFileParser.COMMA);
		List<TCompanyInfo> res = iCompanyInfoService.queryCmpInfoListByUserPhones(nos);
		if(CollectionUtils.isEmpty(res)){
			return CommonResult.getCommonResult(false, "找不到相关用户信息.");
		}
		String pid = RandomUtil.getUUID();
		Date now = DateUtil.getNowDate();
		String creator = String.valueOf(getCurrentUser().getId());
		for(TCompanyInfo info : res){
			TPhonePackage tpp = new TPhonePackage();
			tpp.setPid(pid);
			tpp.setPname(realFileName);
			tpp.setPhone(info.getCphone().trim());
			tpp.setCname(info.getCname());
			tpp.setCtype(info.getCtype());
			tpp.setCid(info.getId());
			tpp.setCreator(creator);
			tpp.setCreatedate(now);
			iPhonePackageService.add(tpp);
		}
		return CommonResult.getCommonResult(true, "上传文件包成功.",pid);
	}
	
	@RequestMapping(value="/system_message/send/",method={RequestMethod.GET,RequestMethod.POST})
	public String sendSystemMessage(ModelMap model,HttpServletRequest request){
		String acceptor = request.getParameter("acceptor");
		String cid = request.getParameter("cid");
		String packageId = request.getParameter("packageId");
		
		String messageType = request.getParameter("messageType");
		String content = request.getParameter("content");
		
		if(StringUtils.isEmpty(messageType) || StringUtils.isEmpty(content)){
			model.addAttribute("retFlag", "false");
			return "tools/system_message_send";
		}
		User currentUser = getCurrentUser();
		if(StringUtils.isNotEmpty(packageId)){
			List<TPhonePackage> phs = iPhonePackageService.queryPhonePackageListByPid(packageId);
			for(TPhonePackage tpp : phs){
				TSystemMessage smsg = new TSystemMessage();
				smsg.setBusinessid(tpp.getPhone());
				smsg.setBusinesstype(MsgBusinessType.BUSINESS_TYPE_OTHERS);
				smsg.setContent(content);
				smsg.setCreatetime(DateUtil.getNowDate());
				smsg.setQyid(tpp.getCid());
				smsg.setStatus(MsgStatus.STATUS_IS_READ_NO);
				smsg.setType(MsgType.enumOf(NumberUtils.toInt(messageType)));
				smsg.setSystemcategory(SystemCategory.SYSTEM_CATEGORY_WEB);
				smsg.setCreator(currentUser.getId()+"");
				iSystemMessageService.add(smsg); // 新消息存储
			}
			model.addAttribute("retFlag", "true");
			return "tools/system_message_send"; 
		}
		if(StringUtils.isEmpty(cid) || StringUtils.isEmpty(acceptor)){
			model.addAttribute("retFlag", "false");
			return "tools/system_message_send";
		}
		TSystemMessage smsg = new TSystemMessage();
		smsg.setBusinessid(acceptor);
		smsg.setBusinesstype(MsgBusinessType.BUSINESS_TYPE_OTHERS);
		smsg.setContent(content);
		smsg.setCreatetime(DateUtil.getNowDate());
		smsg.setQyid(cid);
		smsg.setStatus(MsgStatus.STATUS_IS_READ_NO);
		smsg.setType(MsgType.enumOf(NumberUtils.toInt(messageType)));
		smsg.setSystemcategory(SystemCategory.SYSTEM_CATEGORY_WEB);
		smsg.setCreator(currentUser.getId()+"");
		iSystemMessageService.add(smsg); // 新消息存储
		model.addAttribute("retFlag", "true");
		
		return "tools/system_message_send";
	}
	
	@RequestMapping(value="/system_message/list/{page}/",method={RequestMethod.GET,RequestMethod.POST})
	public String sendSystemMessageList(@PathVariable int page, ModelMap model,HttpServletRequest request){
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		boolean isDateNotEmpty = StringUtils.isNotEmpty(starttime) && StringUtils.isNotEmpty(endtime);
		QueryContext<SystemMessageEx> ctx = new QueryContext<>();
        PageModel pm = new PageModel();
        pm.setPageIndex(page);
        pm.setPageSize(DEFAULT_PAGE_SIZE);
        ctx.setPage(pm);
        if(isDateNotEmpty){
        	QueryContext.DateQueryEntry dateQuery = new QueryContext.DateQueryEntry("createtime",
    				DateUtil.strToDate(starttime, DateUtil.FORMAT_YYYY_MM_DD),
    				DateUtil.strToDate(endtime, DateUtil.FORMAT_YYYY_MM_DD));
            ctx.addParameter("createtime", dateQuery);
        }
        ctx.addParameter("systemCategory", SystemCategory.SYSTEM_CATEGORY_WEB.getVal());
        ctx = iSystemMessageService.queryMessageExListForPagination(ctx);
        model.addAttribute("systemMessage", ctx.getQueryResult().getResult());
        StringBuilder urlBase = new StringBuilder("/tool/manage/system_message/list/{}/");
        if(isDateNotEmpty){        	
        	model.addAttribute("starttime", starttime);
        	model.addAttribute("endtime", endtime);
        	
        	urlBase.append("?starttime="+starttime+"&endtime="+endtime);
        }
        calculatePagingData(model, urlBase.toString(), page, pm.getTotalPage() * pm.getPageSize());
		
		return "tools/system_message_list";
	}
	
	@RequestMapping(value="/system_message/query/",method={RequestMethod.GET,RequestMethod.POST})
	public String sendSystemMessageQuery(ModelMap model,HttpServletRequest request){
		return sendSystemMessageList(1, model,request);
	}
	
	@RequestMapping(value="/short_message/{page}/",method={RequestMethod.GET,RequestMethod.POST})
	public String sendShortMessage(@PathVariable int page){
		
		return "";
	}
	
	@RequestMapping(value="/validate_code/{page}/",method={RequestMethod.GET,RequestMethod.POST})
	public String manageValidateCode(@PathVariable int page){
		
		return "";
	}
	
}
