package com.appabc.tools.utils;

import com.appabc.bean.pvo.TPk;
import com.appabc.common.utils.LogUtil;
import com.appabc.tools.service.pk.IPKService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : primary key generator
 * @Copyright : GL. All Rights Reserved
 * @Company : 江苏国立网络技术有限公司
 * @author : 黄建华
 * @version : 1.0 Create Date : 2014年8月26日 下午8:09:03
 */
@Repository(value="PKGenerator")
public class PrimaryKeyGenerator {
	
	private String yearFlag = "#year";
	private String monthFlag = "#month";
	private String dayFlag = "#day";
	private String hourFlag = "#hour";
	private String minuteFlag = "#minute";
	private String secondFlag = "#second";
	
	@Autowired
	private IPKService service;
	
	protected LogUtil log = LogUtil.getLogUtil(this.getClass());
	
	private synchronized String getCurrentDateValue(int index){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if(index == Calendar.MONTH){
			int i = cal.get(Calendar.MONTH)+1;
			return i<10 ? "0"+i : String.valueOf(i);
		}else if(index == Calendar.DATE){
			int d = cal.get(Calendar.DATE);
			return d<10 ? "0"+d : String.valueOf(d);
		}else{
			int d = cal.get(index);
			return d<10 ? "0"+d : String.valueOf(d);
		}
	}
	
	/**  
	 * matchAndReplace (利用java的正则表达式匹配和替换相关信息)  
	 * @param String ; String ; String
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	private synchronized String matchAndReplace(String preFlag,String sourceStr,String destStr){
		Pattern p = Pattern.compile(preFlag);
		Matcher m = p.matcher(sourceStr);
		while(m.find()){
			sourceStr = sourceStr.replaceAll(preFlag,destStr);
		}
		return sourceStr;
	}
	
	/**  
	 * doPreProcess (处理前缀信息)  
	 * @param TPk ; StringBuffer
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	private synchronized void doPreProcess(TPk tpk,StringBuffer sb){
		String prefix = tpk.getBprefix();
		if(StringUtils.isNotEmpty(prefix)){
			prefix = matchAndReplace(yearFlag,prefix,getCurrentDateValue(Calendar.YEAR));
			prefix = matchAndReplace(monthFlag,prefix,getCurrentDateValue(Calendar.MONTH));
			prefix = matchAndReplace(dayFlag,prefix,getCurrentDateValue(Calendar.DAY_OF_MONTH));
			prefix = matchAndReplace(hourFlag,prefix,getCurrentDateValue(Calendar.HOUR_OF_DAY));
			prefix = matchAndReplace(minuteFlag,prefix,getCurrentDateValue(Calendar.MINUTE));
			prefix = matchAndReplace(secondFlag,prefix,getCurrentDateValue(Calendar.SECOND));
			sb.append(prefix);
		}else{
			return ;
		}
	}
	
	/**  
	 * doMiddleProcess (处理中间数字自动增长)  
	 * @param TPk ; StringBuffer
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	private synchronized void doMiddleProcess(TPk tpk,StringBuffer sb){
		int len = tpk.getLength();
		int curVal = tpk.getCurval();
		int maxVal = tpk.getMaxval();
		int minVal = tpk.getMinval();
		if(curVal>=maxVal){
			curVal = minVal;
		}else{
			curVal++;
		}
		String curValStr = String.valueOf(curVal);
		int stepLen = len - curValStr.length();
		if(stepLen>0){
			for(int i = 0; i < stepLen; i++){
				sb.append(0);
			}
			sb.append(curVal);
		}else{			
			sb.append(curVal);
		}
		tpk.setCurval(curVal);
	}
	
	/**  
	 * doSufProcess (处理后缀信息)  
	 * @param TPk ; StringBuffer
	 * @author Bill huang 
	 * @return void  
	 * @exception   
	 * @since  1.0.0  
	 */
	private synchronized void doSufProcess(TPk tpk,StringBuffer sb){
		String suffix = tpk.getBsuffix();
		if(StringUtils.isNotEmpty(suffix)){
			suffix = matchAndReplace(yearFlag,suffix,getCurrentDateValue(Calendar.YEAR));
			suffix = matchAndReplace(monthFlag,suffix,getCurrentDateValue(Calendar.MONTH));
			suffix = matchAndReplace(dayFlag,suffix,getCurrentDateValue(Calendar.DAY_OF_MONTH));
			suffix = matchAndReplace(hourFlag,suffix,getCurrentDateValue(Calendar.HOUR_OF_DAY));
			suffix = matchAndReplace(minuteFlag,suffix,getCurrentDateValue(Calendar.MINUTE));
			suffix = matchAndReplace(secondFlag,suffix,getCurrentDateValue(Calendar.SECOND));
			sb.append(suffix);
		}else{
			return ;
		}
	}
	
	protected synchronized TPk getTPKByEntity(TPk entity){
		return service.query(entity);
	}
	
	protected synchronized void saveOrUpdateTPk(TPk tpk){
		service.modify(tpk);
	}
	
	/**  
	 * generatorBusinessKey (根据一条主键数据，生成业务主键)  
	 * @param TPk  
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	protected synchronized String generatorBusinessKey(TPk wt) {
		//查询一条主键数据
		TPk tpk = this.getTPKByEntity(wt);
		if(tpk==null){
			return null;
		}
		StringBuffer key = new StringBuffer();
		TPk t = null;
		//将主键数据拷贝出来一个副本，直接对副本的数据进行操作
		try {
			t = (TPk)tpk.clone();
		} catch (CloneNotSupportedException e) {
			log.debug(e.getMessage(), e);
			t = new TPk();
			BeanUtils.copyProperties(tpk, t);
		}
		//对副本的数据进行前缀处理
		this.doPreProcess(t, key);
		//对副本的数据进行中间数据增长处理
		this.doMiddleProcess(t, key);
		//对副本的数据进行后缀处理
		this.doSufProcess(t, key);
		tpk.setCurval(t.getCurval());
		//生成完成后的主键数据更新到数据库
		this.saveOrUpdateTPk(tpk);
		return key.toString();
	}
	
	/**  
	 * generatorBusinessKeyByBid (根据主键表BID[业务key]查询一条主键数据，并生成业务主键)  
	 * @param bid  
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String generatorBusinessKeyByBid(String bid) {
		if(StringUtils.isEmpty(bid)){
			return StringUtils.EMPTY;
		}
		synchronized (bid) {
			TPk wt = new TPk();
			wt.setBid(bid);
			return this.generatorBusinessKey(wt);
		}
	}
	
	/**  
	 * generatorBusinessKeyById (根据主键表ID查询一条主键数据，并生成业务主键)  
	 * @param id  
	 * @author Bill huang 
	 * @return String  
	 * @exception   
	 * @since  1.0.0  
	 */
	public String generatorBusinessKeyById(String id) {
		if(StringUtils.isEmpty(id)){
			return StringUtils.EMPTY;
		}
		synchronized (id) {
			TPk wt = new TPk();
			wt.setId(id);
			return this.generatorBusinessKey(wt);
		}
	}
	
	public String getPKey(String bid){
		return this.generatorBusinessKeyByBid(bid);
	}
	
	public String replaceCode(String code,String str,String target){
		if(StringUtils.isEmpty(code) || StringUtils.isEmpty(str) || StringUtils.isEmpty(target)){
			return StringUtils.EMPTY;
		}
		return this.matchAndReplace(code, str, target);
	};
	
	public static void main(String[] args) {
		PrimaryKeyGenerator prg = new PrimaryKeyGenerator();
		String m = prg.getCurrentDateValue(Calendar.MONTH);
		System.out.println(m);
		String d = prg.getCurrentDateValue(Calendar.DATE);
		System.out.println(d);
		String h = prg.getCurrentDateValue(Calendar.HOUR);
		String hod = prg.getCurrentDateValue(Calendar.HOUR_OF_DAY);
		String minute = prg.getCurrentDateValue(Calendar.MINUTE);
		String second = prg.getCurrentDateValue(Calendar.SECOND);
		System.out.println(h);
		System.out.println(hod);
		System.out.println(minute);
		System.out.println(second);
		/*TPk tpk = new TPk();
		tpk.setId("001");
		tpk.setBid("bid");
		tpk.setMaxval(1000000000);
		tpk.setMinval(1);
		TPk t = new TPk();
		BeanUtils.copyProperties(tpk, t);
		System.out.println(tpk);
		System.out.println(t);*/
		// create a calendar
	   /*   Calendar cal = Calendar.getInstance();

	      // get the value of all the calendar date fields.
	      System.out.println("Calendar's Year: " + cal.get(Calendar.YEAR));
	      System.out.println("Calendar's Month: " + cal.get(Calendar.MONTH));
	      System.out.println("Calendar's Day: " + cal.get(Calendar.DATE));*/
	}
	
}
