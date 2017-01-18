package com.limicala.util;

import javax.servlet.http.HttpSession;

import com.jfinal.kit.StrKit;
import com.limicala.constant.AppConstant;

public final class SessionUtil {

	
	/**
	 * 设置登录用户ID
	 * @author	wwd
	 * @date	2015-7-1
	 * @param httpSession
	 * @param userId
	 * @return
	 */
	public static void setAdminUserId(HttpSession httpSession,String userId){
		if(httpSession != null && userId != null){
			httpSession.setAttribute(AppConstant.adminUserId, userId);
		}
	}
	/**
	 * 获取登陆用户ID
	 * @return
	 */
	public static Integer getAdminUserId(HttpSession httpSession){
		Object obj = httpSession.getAttribute(AppConstant.adminUserId);
		if(null != obj){
			return (Integer) obj;
		}
		return null;
	}
	
	public static void setAdminUsername(HttpSession httpSession,String username){
		if(httpSession != null && StrKit.notBlank(username)){
			httpSession.setAttribute(AppConstant.adminUserName, username);
		}
	}
	
	public static String getAdminUsername(HttpSession httpSession){
		Object obj = httpSession.getAttribute(AppConstant.adminUserName);
		if(null != obj){
			return obj.toString();
		}
		return null;
	}
	
	public static void setSessionId(HttpSession httpSession,String sessionId){
		if(httpSession != null && StrKit.notBlank(sessionId)){
			httpSession.setAttribute(AppConstant.sessionId, sessionId);
		}
	}
	
	public static String getSessionId(HttpSession httpSession){
		Object obj = httpSession.getAttribute(AppConstant.sessionId);
		if(null != obj){
			return obj.toString();
		}
		return null;
	}
	
	public static void setAdminUserInfo(HttpSession httpSession,String userId,String username){
		setAdminUserId(httpSession, userId);
		setAdminUsername(httpSession, username);
//		setSessionId(httpSession, sessionId);
	}
	
	
	public static void setFrontedLoginUserId(HttpSession httpSession,Integer userId){
		if(httpSession != null && userId != null){
			httpSession.setAttribute(AppConstant.frontedUserId, userId);
		}
	}
	
	
	/**
	 * 获取前台用户id
	 * @param httpSession
	 * @return
	 */
	public static Integer getFrontedLoginedUserId(HttpSession httpSession){
		if(httpSession != null ){
			Integer userId = (Integer)httpSession.getAttribute(AppConstant.frontedUserId);
			return userId;
		}
		return null;
	}
}
