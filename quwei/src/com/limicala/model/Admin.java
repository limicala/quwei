package com.limicala.model;

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

	private static final long serialVersionUID = 1L;
	
	public static Admin me = new Admin();
	
	/**
	 * 获取表名
	 */
	public String getTableName(){
		return " "+AppTableConstant.ADMIN+" ";
	}
	
	/**
	 * 判断该账号是否存在
	 * @param aid
	 * @return
	 */
	public boolean checkIdExist(String aid){
		return findById(aid) != null;
	}
	
	/**
	 * 登录验证账号密码
	 * @param aid
	 * @param apassword
	 * @return
	 */
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
	
	/**
	 * 获取管理员分页
	 * @param pageNumber 
	 * @param pageSize
	 * @param aid
	 * @return
	 */
	public Page<Record> findByParams(Integer pageNumber, Integer pageSize, String aid){
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
	
	/**
	 * 编辑用户信息
	 * @param old_aid
	 * @param new_aid
	 * @param password
	 * @return
	 */
	public boolean updateInfo(String old_aid, String new_aid, String password){
		return Db.update("update "+getTableName()+"set aid=?,apassword=? where aid=?", new_aid,password,old_aid)>0;
	}
}
