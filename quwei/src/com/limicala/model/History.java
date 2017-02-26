package com.limicala.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;

import jdk.nashorn.internal.runtime.arrays.NumericElements;

public class History extends BaseModel<History>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5105236804487974925L;
	
	public static History me = new History();

	public Page<Record> findByParams(Integer hpn, Integer historyPageSize, String condi, String condiValue) {
		// TODO Auto-generated method stub
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from history");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		//学号stuNum、姓名name、学院college
		if (StrKit.notBlank(condi) && StrKit.notBlank(condiValue) && !condi.equals("none")) {
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
	 * 返回3个状态：“0”删除失败，一个都没删除成功；“1”删除成功；“2”只删除部分
	 * @param ids
	 * @return
	 */
	public int deleteHistories(String hid) {
		System.out.println("?????????????????????????here");
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
			System.out.println(id[i]);
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
}
