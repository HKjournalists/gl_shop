package com.appabc.cms.web.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appabc.bean.bo.DataUserSingleCount;
import com.appabc.bean.pvo.TDataAllUser;
import com.appabc.cms.web.AbstractListBaseController;
import com.appabc.common.base.QueryContext;
import com.appabc.common.utils.DateUtil;
import com.appabc.common.utils.pagination.PageModel;
import com.appabc.datas.service.data.IDataAllUserService;

/**
 * @Description : 用户信息统计
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : Administrator
 * @version     : 1.0
 * Create Date  : 2015年7月17日 下午5:18:26
 */
@Controller
@RequestMapping("/data/user")
public class UserCountController extends AbstractListBaseController {
	
	@Autowired
	private IDataAllUserService dataAllUserService;
	
	
	/**
	 * 所有用户信息统计
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/all/count/{page}/")
    public String queryAllUserCount(@PathVariable Integer page, ModelMap model, String starttime, String endtime) {

    	if (page == null || page < 1) {
    		page = 1;
        }
    	
    	Map<String, Object> params = new HashMap<>();
    	StringBuilder psb = new StringBuilder();
    	if(StringUtils.isNotEmpty(starttime) && StringUtils.isNotEmpty(endtime)){
    		
    		try {
				Date startTime = DateUtil.strToDate(starttime, DateUtil.FORMAT_YYYY_MM_DD);
				Date endTime = DateUtil.strToDate(endtime, DateUtil.FORMAT_YYYY_MM_DD);
				
				params.put("startTime", startTime);
				params.put("endTime", endTime);
				psb.append("&starttime=").append(starttime);
				psb.append("&endtime=").append(endtime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        QueryContext<TDataAllUser> qContext = new QueryContext<TDataAllUser>();
        qContext.setParameters(params);

        PageModel paging = new PageModel();
        paging.setPageSize(DEFAULT_PAGE_SIZE);
        paging.setPageIndex(page);
        qContext.setPage(paging);
        qContext.setBeanParameter(new TDataAllUser());
        qContext = this.dataAllUserService.queryListForPagination(qContext);
        calculatePagingData(model, "/data/user/all/count/{}/?1=1"+psb, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
        model.addAttribute("resList", qContext.getQueryResult().getResult());
        model.addAttribute("starttime",starttime);
        model.addAttribute("endtime",endtime);

    	return "data/user/all_users_count_list";
    }
	
	/**
	 * 单个用户信息统计
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/single/count/{page}/")
	public String querySingleUserCount(@PathVariable Integer page, ModelMap model, String ctype, String authstatus, String username) {
		
		if (page == null || page < 1) {
			page = 1;
		}
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder psb = new StringBuilder();
		if(StringUtils.isNotEmpty(ctype)){
			params.put("ctype", ctype);
			psb.append("&ctype=").append(ctype);
		}
		if(StringUtils.isNotEmpty(authstatus)){
			params.put("authstatus", authstatus);
			psb.append("&authstatus=").append(authstatus);
		}
		if(StringUtils.isNotEmpty(username)){
			params.put("username", username);
			psb.append("&username=").append(username);
		}
		
		QueryContext<DataUserSingleCount> qContext = new QueryContext<DataUserSingleCount>();
		
		PageModel paging = new PageModel();
		paging.setPageSize(DEFAULT_PAGE_SIZE);
		paging.setPageIndex(page);
		qContext.setPage(paging);
		qContext.setBeanParameter(new DataUserSingleCount());
		qContext.setParameters(params);
		qContext = this.dataAllUserService.queryListForPaginationOfDataUserSingleCount(qContext);
		calculatePagingData(model, "/data/user/single/count/{}/?1=1"+psb, page, qContext.getPage().getTotalPage() * DEFAULT_PAGE_SIZE);
		model.addAttribute("resList", qContext.getQueryResult().getResult());
        model.addAttribute("ctype",ctype);
        model.addAttribute("authstatus",authstatus);
        model.addAttribute("username",username);
		
		return "data/user/single_users_count_list";
	}
	
	/**
	 * 用户整体数据下载
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/all/download/csv", method = RequestMethod.GET)
	public void csvAllUsersDownLoad(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		fileDownLoadBegin(request, response, "UserAll_");
		
		String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
		File file = new File(path);
		FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"UTF-8");
		BufferedWriter bw = new BufferedWriter(fwwe);
		
		List<TDataAllUser> list = this.dataAllUserService.queryForList(new TDataAllUser());
		
		if(CollectionUtils.isNotEmpty(list)){
			bw.write("日期,已注册用户,已认证用户,已缴纳保证金用户,出售类询单数,购买类询单数,进行中的合同数,已结束合同数,当天登录用户数");
			for(TDataAllUser bean : list){
				StringBuffer str = new StringBuffer();
				bw.write("\n");
				str.append(DateUtil.DateToStr(bean.getCreateDate(), DateUtil.FORMAT_YYYY_MM_DD)).append(",")
					.append(bean.getAllUserNum()).append(",")
					.append(bean.getAuthUserNum()).append(",")
					.append(bean.getBailUserNum()).append(",")
					.append(bean.getSellGoodsNum()).append(",")
					.append(bean.getBuyGoodsNum()).append(",")
					.append(bean.getContractIngNum()).append(",")
					.append(bean.getContractEndNum()).append(",")
					.append(bean.getLoginUserNum()).append(",")
					.append("");
				bw.write(str.toString());
				
			}
		}else{
			bw.write("暂无数据");
		}
		bw.close();
		fwwe.close();
		
		fileDownLoadEnd(response, file);
		
	}
	
	/**
	 * 单个用户数据下载
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/single/download/csv", method = RequestMethod.GET)
	public void csvSingleUsersDownLoad(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		fileDownLoadBegin(request, response, "UserSingle_");
		
		String path = System.getProperty("java.io.tmpdir") + "\\tmp.csv";
		File file = new File(path);
		FileWriterWithEncoding fwwe =new FileWriterWithEncoding(file,"UTF-8");
		BufferedWriter bw = new BufferedWriter(fwwe);
		
		List<DataUserSingleCount> list = this.dataAllUserService.queryAllUserSingleCountForList();
		
		if(CollectionUtils.isNotEmpty(list)){
			bw.write("用户,注册日期,名称,类型,联系人,手机,座机,保证金余额,货款余额,认证状态,购买询单数量,出售询单数量,进行中的合同数,已结束的合同数");
			for(DataUserSingleCount bean : list){
				StringBuffer str = new StringBuffer();
				bw.write("\n");
				str.append(bean.getUsername()).append(",")
					.append(DateUtil.DateToStr(bean.getRegdate(), DateUtil.FORMAT_YYYY_MM_DD)).append(",")
					.append(bean.getCname()).append(",");
				if(bean.getCtype() != null){
					str.append(bean.getCtype().getText()).append(",");
				}else{
					str.append("未知").append(",");
				}
				str.append(bean.getContactname()==null?"":bean.getContactname()).append(",")
					.append(bean.getPhone()==null?"":bean.getPhone()).append(",")
					.append(bean.getTel()==null?"":bean.getTel()).append(",")
					.append(bean.getGuaranty()).append(",")
					.append(bean.getDeposit()).append(",")
					.append(bean.getAuthstatus()).append(",")
					.append(bean.getBuynum()).append(",")
					.append(bean.getSellnum()).append(",")
					.append(bean.getContractnuming()).append(",")
					.append(bean.getContractnumend()).append(",")
					.append("");
				bw.write(str.toString());
				
			}
		}else{
			bw.write("暂无数据");
		}
		bw.close();
		fwwe.close();
		
		fileDownLoadEnd(response, file);
		
	}
	
	public void fileDownLoadBegin(HttpServletRequest request, HttpServletResponse response,String fileName){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
		fileName += dateFormat.format(new Date());
		fileName += ".csv";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");  
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
	}
	
	public void fileDownLoadEnd(HttpServletResponse response, File file)throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream out = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			while (true) {
			  int bytesRead;
			  if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
				  break;
			  }
			  out.write(buff, 0, bytesRead);
			}
			file.deleteOnExit();
		}
		catch (IOException e) {
			throw e;
		}
		finally{
			try {
				if(bis != null){
					bis.close();
				}
				if(out != null){
					out.flush();
					out.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}

	}
	
}
