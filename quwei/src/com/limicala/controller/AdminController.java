package com.limicala.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.limicala.config.BaseController;
import com.limicala.model.Admin;
import com.limicala.model.ResponseModel;
import com.limicala.util.SessionUtil;

public class AdminController extends BaseController{
	/**
	 * 管理员首页
	 */
	public void index(){
		render("login.jsp");
	}
	
	public void mainView(){
		render("index.jsp");
	}
	
	public void userManageView(){
		Integer pageNumber = this.getParaToInt("pageNumber", 1);
		Integer pageSize = 6;
		String account = getPara("account");
		Page<Record> page = Admin.me.findByParams(pageNumber, pageSize, account);
		setAttr("page", page);
		render("userManage.jsp");
	}
	
	public void questionManageView(){
		render("questionManage.jsp");
	}
	
	public void checkAid(){
		String aid = getPara("id");//账号
		if(Admin.me.checkIdExist(aid)){
			renderJson(true);
		}else{
			renderJson(false);
		}
	}
	
	/**
	 * ajax判断登录是否合法
	 */
	public void doLogin(){
		System.out.println("done");
		ResponseModel rm = new ResponseModel();
		String aid = getPara("id");//账号
		String apassword = getPara("password");
		
		boolean isPass = Admin.me.checkLogin(aid, apassword);
		if(isPass){
			Admin admin = Admin.me.findById(aid);
			SessionUtil.setAdminUserInfo(getSession(), admin.get("aid"), admin.get("auser_name"));
			rm.msgSuccess("登录成功!");
		}else{
			rm.msgFailed("密码错误!");
		}
		renderJson(rm);
	}
	

	/**
	 * 添加
	 * @param aid
	 * @param apassword
	 */
	@Before(Tx.class)
	public void save(){
		ResponseModel rm = new ResponseModel();
		Admin admin = getModel(Admin.class);
		if (admin.save()) {
			rm.msgSuccess("添加管理员成功");
		} else {
			rm.msgFailed("添加管理员失败");
		}
		
		renderJson(rm);
	}
	
	/**
	 * 修改
	 * @param aid
	 * @param apassword
	 */
	@Before(Tx.class)
	public void update(){
		ResponseModel rm = new ResponseModel();
		String old_aid = getPara("old_account");
		String new_aid = getPara("account");
		String password = getPara("password");
		
		if (Admin.me.updateInfo(old_aid, new_aid, password)) {
			rm.msgSuccess("修改管理员成功");
		} else {
			rm.msgFailed("修改管理员失败");
		}
		
		renderJson(rm);
	}
	/**
	 * 删除管理员
	 */
	@Before(Tx.class)
	public void delete(){
		ResponseModel rm = new ResponseModel();
		String id = this.getPara("id");
//		String id = "111";
		boolean result = Admin.me.deleteById(id);
		if (result == true) {
			rm.msgSuccess("删除管理员成功");
			
		} else{
			rm.msgFailed("删除管理员失败");
			
		}
		this.renderJson(rm);
	}
}
