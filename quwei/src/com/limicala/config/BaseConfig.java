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
import com.limicala.constant.AppTableConstant;
import com.limicala.controller.AdminController;
import com.limicala.controller.UserController;
import com.limicala.model.Admin;
import com.limicala.model.ConfigOS;
import com.limicala.model.History;
import com.limicala.model.Question;
import com.limicala.model.Student;

/**
 * JFinal框架总配置类（继承JFinalConfig类）
 * 方法作用：
 * 		1、configConstant(Constants me)
 * 		       配置视图文件格式、开发模式、上传下载文件的路径等；
 *  	2、configRoute(Routes me) 代替web.xml的功能，同时实现一个servlet接受处理多个请求
 * 		       配置请求的映射关系和转发重定向的路径等；
 * 		3、configPlugin(Plugins me) 
 * 		       配置添加插件，Plugins类作为插件管理类
 * 		4、configInterceptor(Interceptors me) 
 * 		       配置过滤器
 * 		5、configHandler(Handlers me) 
 * 		       
 * 
 * @author ZDD
 *
 */
public class BaseConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		/**
		 * JFinal框架的开发配置步骤
		 * 1、配置视图类型：jsp模式
		 * 
		 */

		/**
		 * 配置c3p0数据源
		 */
		//读取数据库配置文件
		PropKit.use("jdbc.properties");
		
		me.setViewType(ViewType.JSP);
		me.setDevMode(PropKit.getBoolean("devMode", true));
		
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/admin", AdminController.class,"/pages/admin");
		me.add("/", UserController.class,"/pages/user");
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
		
		arp.addMapping(AppTableConstant.ADMIN, AppTableConstant.ADMIN_KEY, Admin.class);
		arp.addMapping(AppTableConstant.CONFIG_OS, AppTableConstant.CONFIG_OS_KEY, ConfigOS.class);
		arp.addMapping(AppTableConstant.QUESTION, AppTableConstant.QUESTION_KEY, Question.class);
		arp.addMapping(AppTableConstant.HISTORY, AppTableConstant.HISTORY_KEY ,History.class);
		arp.addMapping(AppTableConstant.STUDENT, AppTableConstant.STUDENT_KEY, Student.class);
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
		JFinal.start("WebContent", 9080, "/", 5);
	}
}
