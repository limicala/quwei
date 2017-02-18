package com.limicala.model;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;
import com.limicala.constant.AppTableConstant;
/**
 * 管理员表的Model类
 * @author wrj
 *
 */
public class Admin extends BaseModel<Admin>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Admin me = new Admin();
	
	public String getTableName(){
		return " "+AppTableConstant.ADMIN+" ";
	}
	/**
	 * 判断该账号是否存在
	 * @param aid
	 * @return
	 */
	public boolean checkIdExist(String aid){
//		Integer count = Db.queryNumber("select count(1) from"+getTableName()+"where aid = ?",aid).intValue();
		Admin admin = findById(aid);
		return admin != null;
	}
	
	public boolean checkLogin(String aid, String apassword){
		if(StrKit.notBlank(aid) && StrKit.notBlank(apassword)){
			Admin admin = findById(aid);
			if(admin != null){
				if(admin.getStr("apassword").equals(apassword)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public Page<Record> findByParams(Integer pageNumber, Integer pageSize, String aid){
		// TODO Auto-generated method stub
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from admin");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		if (StrKit.notBlank(aid)) {
			whereSql.append(" and aid like ").append("'%").append(aid).append("%'");
		}
		
		return Db.paginate(pageNumber, pageSize, selectSql.toString(), fromSql
				.append(whereSql).toString());
	}
	
	public boolean updateInfo(String old_aid, String new_aid, String password){
		
		return Db.update("update "+getTableName()+"set aid=?,apassword=? where aid=?", new_aid,password,old_aid)>0;
	}
}
