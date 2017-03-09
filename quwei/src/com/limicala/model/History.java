package com.limicala.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;

public class History extends BaseModel<History>{

	private static final long serialVersionUID = -5105236804487974925L;
	
	public static History me = new History();

	/**
	 * 获取分页
	 * @param hpn 				当前页码
	 * @param historyPageSize   分页大小
	 * @param condi				查询条件类型
	 * @param condiValue		查询条件值
	 * @return 分页Page<Record>
	 */
	public Page<Record> findByParams(Integer hpn, Integer historyPageSize, String condi, String condiValue) {
		// TODO Auto-generated method stub
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from history");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		//学号stuNum、姓名name、学院college
		if (StrKit.notBlank(condi) && StrKit.notBlank(condiValue)) {
			if (condi.trim().equals("stuNum"))
				whereSql.append(" and hstuNum like ").append("'%").append(condiValue).append("%'");
			else if (condi.trim().equals("name"))
				whereSql.append(" and hname like").append("'%").append(condiValue).append("%'");
			else if (condi.trim().equals("college"))
				whereSql.append(" and hcollege like").append("'%").append(condiValue).append("%'");
		}
		whereSql.append("order by htime desc");
		
		return Db.paginate(hpn, historyPageSize, selectSql.toString(), fromSql
				.append(whereSql).toString());
	}
	
	
	/**
	 * 删除答题记录
	 * 返回3个状态：“0”删除失败，一个都没删除成功；“1”删除成功；“2”只删除部分
	 * @param ids 以“|”分割的记录主键值组成的字符串
	 * @return
	 */
	public int deleteHistories(String hid) {
		/*
		 * split分隔符总结
			1.字符"|","*","+"都得加上转义字符，前面加上"\\"。
			2.而如果是"\"，那么就得写成"\\\\"。
			3.如果一个字符串中有多个分隔符，可以用"|"作为连字符。
		 */
		String[] id = hid.split("\\|");
		Integer idAllNum = id.length;
		int idDelNum = 0;
		for (int i = 0; i < idAllNum; ++i){
			//System.out.println(id[i]);
			if(History.me.deleteById(id[i]))
				idDelNum++;
		}
		if (idAllNum.equals(idDelNum)){
			return 1;
		}else if(idAllNum.equals("0")){
			return 0;
		}else
			return 2;
	}

	/**
	 * 通过条件排序的记录
	 * @param condi 排序条件 "default"默认答题时间降序    "score"答题分数降序   "college"学院分组
	 * @return
	 */
	public ArrayList<History> findByCondi(String condi) {
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from history");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		if (StrKit.notBlank(condi) && !condi.equals("defalut")) {
			if (condi.trim().equals("score"))
				whereSql.append("order by hscore desc");
			else if (condi.trim().equals("college"))
				whereSql.append("order by hcollege desc");
			else
				whereSql.append("order by htime desc");
		}
		return (ArrayList<History>) History.me.find(selectSql.toString()+fromSql.toString()+whereSql.toString());
	}
	
	/**
	 * 通过学号获取相应记录
	 * @param stuNum
	 * @return
	 */
	public History findModelByStuNum(String stuNum){
		History history = findFirst("select * from history where hstuNum = ?",stuNum);
		return history;
	}
	
	/**
	 * 检查用户是否有资格答题
	 * 
	 */
	public boolean canContest(String sid, Integer dayinterval){
		boolean flag = true;
		History history = findModelByStuNum(sid);
		if(history != null){//答过题
			Date lastDate = history.getDate("htime");
			Date nowDate = new Date();
			Integer hrate = history.getInt("hrate");
			if(hrate == null) hrate = 0;
			if(isSameDate(lastDate, nowDate)){//同一天
				if(hrate >= dayinterval){
					flag = false;//不能答题
				}
			}
		}
		return flag;
	}
	
	public boolean updateHistory(String sid, Integer total_score) {
		// TODO Auto-generated method stub
		History history = findModelByStuNum(sid);
		Student student = Student.me.findById(sid);
		Date nowDate = new Date();
		boolean flag = true;
		if(history != null){//答过题
			Date lastDate = history.getDate("htime");
			Integer maxScore = history.getInt("hscore");
			Integer hrate = history.getInt("hrate");
			if(hrate == null) hrate = 0;
			if(isSameDate(lastDate, nowDate)){//同一天
				history.set("hrate", hrate + 1)
						.set("htime", nowDate);
				
			}else{//不是同一天
				history.set("hrate", 1)
						.set("htime", nowDate );
			}
			if(maxScore < total_score){
				history.set("hscore", total_score);
			}
			flag = history.update();
		}else{
			history = new History();
			flag = history.set("hstuNum", sid)
			.set("hname", student.getStr("sname"))
			.set("hcollege", student.getStr("scollege"))
			.set("hscore", total_score)
			.set("hrate", 1)
			.set("htime", nowDate)
			.save();
		}
		return flag;
	}
	
	/**
	 * 检测最后一次答题是否是当天
	 * @param date1
	 * @param date2
	 * @return
	 */
	private static boolean isSameDate(Date date1, Date date2) {
	       Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);

	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);

	       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = isSameYear
	               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	       boolean isSameDate = isSameMonth
	               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);

	       return isSameDate;
	 }


	
}
