package com.limicala.config;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.limicala.controller.UserController;
import com.limicala.model.Admin;

public class BaseConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		/**
		 * JFinal框架的开发配置步骤
		 * 1。配置视图类型：jsp模式
		 * 2.
		 */
		//读取数据库配置文件
		PropKit.use("c3p0.properties");
		
		
		me.setViewType(ViewType.JSP);
		me.setDevMode(PropKit.getBoolean("devMode", false));
		
		/**
		 * 配置c3p0数据源
		 */
		
		
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("user", UserController.class);
	}
	
	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		// 配置C3p0数据库连接池插件
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl", ""),PropKit.get("user", ""), PropKit.get("password", "").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		//显示sql语句
		arp.setShowSql(true);
		
		//把表和一个对象对应起来
		
		arp.addMapping("Admin", Admin.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		JFinal.start("WebContent", 6000, "/", 5);
	}
}
