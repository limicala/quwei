package com.limicala.constant;

/**
 *数据库：数据表名、主键名、特殊类型值
 */
public final class AppTableConstant {
	
	//管理员
	public static final String ADMIN = "admin";
	public static final String ADMIN_KEY = "aid";
	//配置信息
	public static final String CONFIG_OS = "config_os";
	public static final String CONFIG_OS_KEY = "cid";
	//答题历史
	public static final String HISTORY = "history";
	public static final String HISTORY_KEY = "hid"; 

	//问题
	public static final String QUESTION = "question";
	public static final String QUESTION_KEY = "qid";
	
	//学生
	public static final String STUDENT = "student";
	public static final String STUDENT_KEY = "sid";

	public static final Integer QUESTION_JUDGE = 1;//判断题
	public static final Integer QUESTION_SINGLE = 2;//单选题
	public static final Integer QUESTION_MUTIL = 3;//多选题

	public static final Integer QUESTION_UNLIMIT = 0;//随机
	public static final Integer QUESTION_LIMIT = 1;//优先
}
