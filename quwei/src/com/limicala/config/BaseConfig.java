package com.limicala.config;


import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
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
import com.limicala.util.SessionUtil;

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
 */
public class BaseConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		/**
		 * JFinal框架的开发配置步骤
		 * 1、配置视图类型：jsp模式
		 */

		/**
		 * 配置c3p0数据源
		 */
		//读取数据库配置文件
		PropKit.use("jdbc.properties");
		
		me.setViewType(ViewType.JSP);//配置框架所用视图
		me.setDevMode(PropKit.getBoolean("devMode", true));//开发模式
	}

	@Override
	public void configRoute(Routes me) {
		/**
		 * 配置请求映射关系
		 * add(para1, para2, para3)
		 * para1:请求servlet路径
		 * para2:所映射的类
		 * para3:视图响应路径
		 */
		me.add("/admin", AdminController.class,"/pages/admin");
		me.add("/", UserController.class,"/pages/user");
	}
	
	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl", ""),PropKit.get("user", ""), PropKit.get("password", "").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		
		//设置显示sql语句
		arp.setShowSql(true);
		
		//配置数据表和相应映射类（Model子类）
		arp.addMapping(AppTableConstant.ADMIN, AppTableConstant.ADMIN_KEY, Admin.class);
		arp.addMapping(AppTableConstant.CONFIG_OS, AppTableConstant.CONFIG_OS_KEY, ConfigOS.class);
		arp.addMapping(AppTableConstant.QUESTION, AppTableConstant.QUESTION_KEY, Question.class);
		arp.addMapping(AppTableConstant.HISTORY, AppTableConstant.HISTORY_KEY ,History.class);
		arp.addMapping(AppTableConstant.STUDENT, AppTableConstant.STUDENT_KEY, Student.class);
	}

	/**
	 * 配置拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new Interceptor() {
			@Override
			public void intercept(Invocation inv) {
				String rpath = inv.getController().getRequest().getServletPath().toLowerCase();
				boolean f = true;
				Controller controller = inv.getController();
				HttpSession session = controller.getSession();
				System.out.println("rpath"+rpath);
				//在系统内部了
				if(!(rpath.startsWith("/admin"))){//不是管理员
					if(rpath.equals("/") || rpath.equals("/index.jsp") || rpath.equals("/checkexsitstudent")){//学生登录页面
						f = true;
					}else{//学生其他页面
						f = isStudent(session);
						if(f == false){
							System.out.println("拦截该请求:"+rpath);
							System.out.println("管理员未登录");
							controller.redirect("/");
							return;
						}
					}
				}else {
					if(rpath.equals("/admin/") || rpath.equals("/admin") || rpath.equals("/admin/dologin")){//不用拦截
						f = true;
					}else{
						f = isAdmin(session);
						if(f == false){
							System.out.println("拦截该请求:"+rpath);
							System.out.println("学生未登录");
							controller.redirect("/admin");
							return;
						}
					}
				}
				if(f){
					inv.invoke();//转为请求的控制器
				}
			}
			
			//判断是否是管理员
			private boolean isAdmin(HttpSession session){
				String admin = SessionUtil.getAdminUserId(session);
				if(null!=admin){
					return true;
				}
				return false;
			}
			
			//判断是否是学生
			private boolean isStudent(HttpSession session){
				String user = SessionUtil.getFrontedLoginedUserId(session);
				if(null!=user){
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void configHandler(Handlers me) {	}
	
	public static void main(String[] args) {
		JFinal.start("WebContent", 9080, "/", 5);
	}
}
