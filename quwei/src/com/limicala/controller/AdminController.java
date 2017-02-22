package com.limicala.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.limicala.config.BaseController;
import com.limicala.constant.AppTableConstant;
import com.limicala.model.Admin;

import com.limicala.model.ConfigOS;

import com.limicala.model.Question;

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
		setAttr("url", "userManageView");
		setAttr("account", account);
		setAttr("page", page);
		render("userManage.jsp");
	}
	
	public void questionManageView(){
		Integer jpn = this.getParaToInt("jpn", 1);//判断题
		Integer spn = this.getParaToInt("spn", 1);//单项选择题
		Integer mpn = this.getParaToInt("mpn", 1);//多项选择题
		Integer type = this.getParaToInt("ct", 1);//当前的tab页面
		Integer pageSize = 5;
		Page<Record> page = Question.me.findByParams(spn, pageSize, AppTableConstant.QUESTION_SINGLE);
		Page<Record> page1 = Question.me.findByParams(mpn, pageSize, AppTableConstant.QUESTION_MUTIL);
		Page<Record> page2 = Question.me.findByParams(jpn, pageSize, AppTableConstant.QUESTION_JUDGE);
		
		setAttr("url", "questionManageView");
		setAttr("ct", type);
		setAttr("page", page);
		setAttr("page1", page1);
		setAttr("page2", page2);
		
		render("questionManage.jsp");
	}
	
	public void configView(){
		setAttr("configOS", ConfigOS.me.findById(new Integer(1)));
		
		setAttr("single_ness", 3);
		setAttr("judge_ness", 4);
		setAttr("multi_ness", 2);
		render("config.jsp");
	}
	
	public void checkAid(){
		String aid = getPara("account");//账号
		if(Admin.me.checkIdExist(aid)){
			renderJson(true);
		}else{
			renderJson(false);
		}
	}
	/**
	 * 修改答题间隔时间
	 */
	public void update_interval(){
		ConfigOS configOS = getModel(ConfigOS.class);
		if(configOS.getInt("cid")!= null){
			renderJson(configOS.update());
		}else{
			renderJson(configOS.save());
		}
	}
	
	public void update_score(){
		ConfigOS configOS = getModel(ConfigOS.class);
		if(configOS.getInt("cid")!= null){
			renderJson(configOS.update());
		}else{
			renderJson(configOS.save());
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
		//检查用户是否存在
		if(Admin.me.checkIdExist(aid)){
			boolean isPass = Admin.me.checkLogin(aid, apassword);
			if(isPass){
				SessionUtil.setAdminUserInfo(getSession(), aid);
				rm.msgSuccess("登录成功!");
			}else{
				rm.msgFailed("密码错误!");
			}
		}else{
			rm.msgFailed("用户不存在!");
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
		
		//旧账号为空時为添加用户
		if(StrKit.isBlank(old_aid)){
			Admin admin = new Admin();
			admin.set("aid", new_aid)
			.set("apassword", password);
			if(admin.save()){
				rm.msgSuccess("添加管理员成功");
			}else{
				rm.msgFailed("添加管理员失败");
			}
		}else{
			if (Admin.me.updateInfo(old_aid, new_aid, password)) {
				rm.msgSuccess("修改管理员成功");
			} else {
				rm.msgFailed("修改管理员失败");
			}
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
