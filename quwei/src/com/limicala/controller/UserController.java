package com.limicala.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.limicala.config.BaseController;
import com.limicala.constant.AppTableConstant;
import com.limicala.model.ConfigOS;
import com.limicala.model.History;
import com.limicala.model.Question;
import com.limicala.model.ResponseModel;
import com.limicala.model.Student;
import com.limicala.util.SessionUtil;
import com.limicala.util.ShuffleUtil;

public class UserController extends BaseController{
	/**
	 * 答题者首页
	 */
	public void index(){
		render("/index.jsp");
	}
	
	/**
	 * 注销
	 */
	public void loginout(){
		this.getSession().invalidate();
		redirect("/");
	}
	
	/**
	 * 检查账号是否存在
	 */
	public void checkExsitStudent(){
		String sid = getPara("sid");//账号
		Student student = Student.me.findById(sid);
		ResponseModel rm = new ResponseModel();
		ConfigOS configOS = ConfigOS.me.findById(1);
		Integer dayinterval = configOS.getInt("cdayinterval");
		if(student != null){
			History history = History.me.findModelByStuNum(student.getStr("sid"));
			boolean flag = true;
			if(history != null){//
				Integer hrate = history.getInt("hrate");
				if(hrate == null) hrate = 0;
				if(hrate >= dayinterval){
					rm.msgFailed("一天只能回答"+dayinterval+"次");
				}else{
					flag = history.set("hrate", hrate + 1).update();
					if(flag == true){
						SessionUtil.setFrontedLoginUserId(getSession(), sid);
						rm.setSuccess(true);
					}else{
						rm.msgFailed("服务器出错");
					}
				}
			}else{
				history = new History();
				flag = history.set("hstuNum", student.getStr("sid"))
				.set("hname", student.getStr("sname"))
				.set("hcollege", student.getStr("scollege"))
				.set("hrate", 1)
				.save();
				if(flag == true){
					SessionUtil.setFrontedLoginUserId(getSession(), sid);
					rm.setSuccess(true);
				}else{
					rm.msgFailed("服务器出错");
				}
			}
		}else{
			rm.msgFailed("您输入的学号有误，或者您未参与本次活动");
		}
		renderJson(rm);
	}
	
	/**
	 * 测试
	 */
	public void contest(){
		//学生账号
		String sid = SessionUtil.getFrontedLoginedUserId(getSession());
		Student student = Student.me.findById(sid);
		
		ConfigOS configOS = ConfigOS.me.findById(1);
		
		Integer judge_total_num = configOS.getInt("cjudge_num");
		Integer single_total_num =  configOS.getInt("csingle_num");
		Integer multi_total_num = configOS.getInt("cmulti_num");
		if(configOS != null){
			setAttr("answer_time", configOS.getInt("canswertime"));
			setAttr("startword", configOS.getStr("cstartword"));
		}
		
		setAttr("student", student);
		setAttr("judge_total_num", judge_total_num);
		setAttr("single_total_num", single_total_num);
		setAttr("multi_total_num", multi_total_num);
		setAttr("judge_list",createRandomQuestion(AppTableConstant.QUESTION_JUDGE, judge_total_num));
		setAttr("single_list",createRandomQuestion(AppTableConstant.QUESTION_SINGLE,single_total_num));
		setAttr("multi_list",createRandomQuestion(AppTableConstant.QUESTION_MUTIL, multi_total_num));
		
		render("testing.jsp");
	}
	
	/**
	 * 答题结果
	 */
	public void result(){
		String sid = SessionUtil.getFrontedLoginedUserId(getSession());
		Student student = Student.me.findById(sid);
		ConfigOS configOS = ConfigOS.me.findById(1);
		//将三种题型的答案都分别放在一个Map对象里面
		Integer judge_total_num = getParaToInt("judge_total_num");
		Integer single_total_num = getParaToInt("single_total_num");
		Integer multi_total_num = getParaToInt("multi_total_num");
		
		//判断题处理
		List<Question> judgeList = createQuestionList(AppTableConstant.QUESTION_JUDGE, judge_total_num);
		//单选题处理
		List<Question> singleList = createQuestionList(AppTableConstant.QUESTION_SINGLE, single_total_num);
		//多选题处理
		List<Question> multiList = createQuestionList(AppTableConstant.QUESTION_MUTIL, multi_total_num);
		
		//求分数
		Integer total_score = getTotalScore(judgeList, singleList, multiList);
		
		History history = History.me.findModelByStuNum(sid);
		if(history != null){
			//取到分数
			Integer maxScore = history.getInt("hscore");
			if(maxScore == null || maxScore < total_score){
				history.set("hscore", total_score).set("htime", new Date()).update();
			}
		}
		
		if(configOS != null){
			setAttr("endword", configOS.getStr("cendword"));
		}
		setAttr("student", student);
		setAttr("total_score", total_score);
		setAttr("judgeList", judgeList);
		setAttr("singleList", singleList);
		setAttr("multiList", multiList);
		render("result.jsp");
	}
	
	/*类内方法*/
	/**
	 * 随机生成一套试卷中该题目类型的题目
	 * @param qtype 问题类型
	 * @param total_num 需要的题数
	 * @return
	 */
	private List<Question> createRandomQuestion(Integer qtype, Integer total_num){
		//先添加必答的题目
		List<Question> list = Question.me.findQuestionByParams(qtype, AppTableConstant.QUESTION_LIMIT);
		//随机选取的题目
		List<Question> list_unlimit = Question.me.findQuestionByParams(qtype, AppTableConstant.QUESTION_UNLIMIT);

		int limit_size = list.size();
		int unlimit_size = list_unlimit.size();

		//如果当前配置中需要的题数小于必答题题数，返回空
		if(total_num < limit_size){
			return null;
		}
		//生成一个不重复的随机序列
		int[] random = ShuffleUtil.GetRandomSequence2(unlimit_size, total_num - limit_size);
		for(int r : random){
			//从不是必选题中挑出来，添加到列表里
			list.add(list_unlimit.get(r));
		}
		//将符合条件的列表再重新打乱以下顺序
		ShuffleUtil.shuffle3(list);
		return list;
	}
	
	/**
	 * 根据题目类型筛选存储在列表中
	 * @param qtype 题目类型
	 * @param total_num 该类型需答的题数
	 * @return
	 */
	private List<Question> createQuestionList(Integer qtype, Integer total_num){
		List<Question> questions = new ArrayList<Question>();
		String prefix = "";//代表在答题页面中题目的标签name值的前缀
		if(qtype == AppTableConstant.QUESTION_JUDGE){
			prefix = "jid";
		}else if(qtype == AppTableConstant.QUESTION_SINGLE){
			prefix = "sid";
		}else if(qtype == AppTableConstant.QUESTION_MUTIL){
			prefix = "mid";
		}
		for(int i = 1; i <= total_num; i++){
			String input_name = prefix.substring(0, 1)+i;
			String user_answer = "";
			if(qtype != AppTableConstant.QUESTION_MUTIL){
				if(getPara(input_name) != null){
					user_answer = getPara(input_name);
				}
			}else{
				String[] temp = getParaValues(input_name);
				
				if(temp != null){
					StringBuilder sb = new StringBuilder();
					for(String t : temp){
						sb.append(t);
					}
					user_answer = sb.toString();
				}
			}
			
			Integer q_id = getParaToInt(prefix+i);
			Question question = Question.me.findById(q_id);
			//用户的答案
			question.put("u_answer",user_answer);
			questions.add(question);
		}
		return questions;
	}
	
	/**
	 * 计算总分
	 * @param judgeList 当前试卷中判断题题题目
	 * @param singleList 当前试卷中单选题题题目
	 * @param multiList 当前试卷中多选题题题目
	 * @return
	 */
	private int getTotalScore(List<Question> judgeList, List<Question> singleList, List<Question> multiList){
		int score = 0;
		ConfigOS configOS = ConfigOS.me.findById(1);
		int j_score = configOS.getInt("cjudge_score");
		int s_score = configOS.getInt("csingle_score");
		int m_score = configOS.getInt("cmulti_score");
		for(Question q : judgeList){
			String u_answer = q.getStr("u_answer");
			String qanswer = q.getStr("qanswer");
			Question.me.testPlus(q);
			if(StrKit.notBlank(u_answer, qanswer) && u_answer.equals(qanswer)){
				score += j_score;
				Question.me.truePlus(q);
			}
		}
		for(Question q : singleList){
			String u_answer = q.getStr("u_answer");
			String qanswer = q.getStr("qanswer");
			Question.me.testPlus(q);
			if(StrKit.notBlank(u_answer, qanswer) && u_answer.equals(qanswer)){
				score += s_score;
				Question.me.truePlus(q);
			}
		}
		for(Question q : multiList){
			String u_answer = q.getStr("u_answer");
			String qanswer = q.getStr("qanswer");
			Question.me.testPlus(q);
			if(StrKit.notBlank(u_answer, qanswer) && u_answer.equals(qanswer)){
				score += m_score;
				Question.me.truePlus(q);
			}
		}
		return score;
	}
	
	/**
	 * 检测最后一次答题是否是当天
	 * @param date1
	 * @param date2
	 * @return
	 */
	private static boolean isSameDate(Date date1, Date date2) {
	       Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);

	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);

	       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = isSameYear
	               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	       boolean isSameDate = isSameMonth
	               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);

	       return isSameDate;
	  }
}
