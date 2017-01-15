package com.limicala.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.limicala.model.Admin;

public class UserController extends Controller{
	
	
	public void login(){
		String name = getPara("username");
		if(name.equals("aaa")){
			render("index.jsp");
		}
	}
	
	@Before(Tx.class)
	public void add(){
		String id = getPara("id");
		String password = getPara("password");
		Admin admin = new Admin();
		Admin temp = admin.findFirst("select aid from Admin where aid=?",id);
		
		if(temp!= null && temp.get("aid").equals(id)){
			renderText("已存在");
		}else{
			admin.set("aid", id)
			.set("apassword", password)
			.save();
			renderText("保存成功");
		}
		
	}
}
