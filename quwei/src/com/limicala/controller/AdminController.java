package com.limicala.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.fileupload.FileUploadException;

import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.limicala.config.BaseController;
import com.limicala.constant.AppConstant;
import com.limicala.constant.AppTableConstant;
import com.limicala.model.Admin;

import com.limicala.model.ConfigOS;

import com.limicala.model.Question;

import com.limicala.model.ResponseModel;
import com.limicala.model.Student;
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
	
	//***************************************************题库*************************************************************
	
	public void questionManageView(){
		Integer jpn = this.getParaToInt("jpn", 1);//判断题
		Integer spn = this.getParaToInt("spn", 1);//单项选择题
		Integer mpn = this.getParaToInt("mpn", 1);//多项选择题
		Integer type = this.getParaToInt("ct", 1);//当前的tab页面
		Integer pageSize = 5;
		String scondi = this.getPara("scondi", "");//单项选择题目查询关键字
		String mcondi = this.getPara("mcondi", "");//多项选择题目查询关键字
		String jcondi = this.getPara("jcondi", "");//判断题查询关键字
		Page<Record> page = Question.me.findByParams(spn, AppConstant.SINGEL_PAGE_SIZE, AppTableConstant.QUESTION_SINGLE, scondi);
		Page<Record> page1 = Question.me.findByParams(mpn, AppConstant.MUTIL_PAGE_SIZE, AppTableConstant.QUESTION_MUTIL, mcondi);
		Page<Record> page2 = Question.me.findByParams(jpn, AppConstant.JUDGE_PAGE_SIZE, AppTableConstant.QUESTION_JUDGE, jcondi);
		
		setAttr("url", "questionManageView");
		setAttr("ct", type);
		setAttr("jcondi", jcondi);
		setAttr("scondi", scondi);
		setAttr("mcondi", mcondi);
		setAttr("page", page);
		setAttr("page1", page1);
		setAttr("page2", page2);
		
		render("questionManage.jsp");
	}
	
	/**
	 * 编辑或者添加题库信息
	 */
	@Before(Tx.class)
	public void updateQuestion(){
		ResponseModel rm = new ResponseModel();
		
		String qid = getPara("id");
		
		String qcontent = getPara("content");
		String qanswer = getPara("answer");
		String qexplain = getPara("explain");
		
		String qtype = getPara("qtype");

		String a = getPara("a");
		String b = getPara("b");
		String c = getPara("c");
		String d = getPara("d");
		
		//qid为空时添加题目
		if(StrKit.isBlank(qid)){
			Question question = new Question();
			question.set("qtype", qtype)
			.set("qcontent", qcontent)
			.set("qa", a).set("qb", b).set("qc", c).set("qd", d)
			.set("qanswer", qanswer).set("qexplain", qexplain);
			if(question.save()){
				rm.setSuccess(true);
				rm.setType("add");
				rm.msgSuccess("添加成功,刷新即可看到新增信息");
			}else{
				rm.setSuccess(false);
				rm.setType("add");
				rm.msgFailed("添加失败");
			}
		}else{
			if (Question.me.updateQuestion(qid, qcontent, a, b, c, d, qanswer, qexplain)) {
				rm.setSuccess(true);
				rm.setType("edit");
				rm.msgSuccess("修改成功");
			} else {
				rm.setSuccess(false);
				rm.setType("edit");
				rm.msgSuccess("修改失败");
			}
		}
		
		renderJson(rm);
	}
	
	
	/**
	 * 删除题库信息
	 */
	@Before(Tx.class)
	public void deleteQuestion(){
		ResponseModel rm = new ResponseModel();
		String qid = getPara("id");
		String delType = getPara("delType");
		
		if (delType.trim().equals("s")){
			
			if(StrKit.notBlank(qid)){
				if(Question.me.deleteById(qid)){
					rm.setSuccess(true);
					rm.msgSuccess("删除成功");
				}else{
					rm.setSuccess(false);
					rm.msgFailed("删除失败");
				}
			}else{
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}
		}else if (delType.trim().equals("m")){
			int re = Question.me.deleteQuestions(qid);
			if( 1 == re ){
				rm.setSuccess(true);
				rm.msgFailed("删除成功");
			}else if( 0 == re ){
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}else if( 2 == re ){
				rm.setSuccess(false);
				rm.msgFailed("删除失败，只删除选中部分数据，请刷新后重试");
			}else{
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}
		}else{
			rm.setSuccess(false);
			rm.msgFailed("删除失败");
		}
		
		renderJson(rm);
	}
	
	/**
	 * 设置题目的答题限定情况
	 */
	@Before(Tx.class)
	public void setQuestionState(){
		ResponseModel rm = new ResponseModel();
		
		String qid = getPara("id");
		String state = getPara("state");
		if (!state.trim().equals("1") && !state.trim().equals("0")){
			rm.setSuccess(false);
			rm.msgFailed("状态设置失败");
			renderJson(rm);
			return;
		}
		if (StrKit.notBlank(state) && StrKit.notBlank(state) && Question.me.findById(qid).set("qlimit", state).update()){
			rm.setSuccess(true);
			rm.msgSuccess("设置成功");
		}else{
			rm.setSuccess(false);
			rm.msgFailed("状态设置失败");
		}
		renderJson(rm);
	}
	
	public void downloadTemplate(){
		String templateType = getPara("templateType");
		//System.out.println(PathKit.getWebRootPath());
		String path = "";
		if ( templateType.equals("single") ){
			path = "/resources/templates/单项选择题上传模板.xlsx";
			//renderFile(PathKit.getWebRootPath()+"/resources/templates/单项选择题上传模板.xlsx");  
		}else if ( templateType.equals("multi") ){
			path = "/resources/templates/多项选择题上传模板.xlsx";
			//renderFile(PathKit.getWebRootPath()+"/resources/templates/多项选择题上传模板.xlsx");  
		}else if ( templateType.equals("judge") ){
			path = "/resources/templates/判断题上传模板.xlsx";
			//renderFile(PathKit.getWebRootPath()+"/resources/templates/判断题上传模板.xlsx");
		}else{
			//转到错误页
		}
		File file = new File(PathKit.getWebRootPath()+path);
		if(file.exists()) {
			System.out.println("文件存在");
			renderFile(file);
		}
	}
	
	/**
	 * 批量上传问题
	 */
	@Before(Tx.class)
	public void uploadQuestions(){
		ResponseModel re = new ResponseModel();
		int result = 0;
		try {
			result = Question.me.readWriteFileExcel(getRequest());
		} catch (FileUploadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
		
		renderHtml("<script>window.parent.afterUpload(" + result + ");</script>");
	}
	
	public void configView(){
		setAttr("configOS", ConfigOS.me.findById(new Integer(1)));
		
		setAttr("single_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_SINGLE, AppTableConstant.QUESTION_LIMIT));
		setAttr("judge_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_JUDGE, AppTableConstant.QUESTION_LIMIT));
		setAttr("multi_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_MUTIL, AppTableConstant.QUESTION_LIMIT));
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
	 * 修改配置信息
	 */
	public void update_config(){
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
	
	//************************学生管理*****************//
	public void stuManageView(){
		Integer pageNumber = this.getParaToInt("pageNumber", 1);
		Integer search_type = getParaToInt("search_type", 1);
		Integer pageSize = 10;
		String condit = this.getPara("condit", "");//单项选择题目查询关键字
		Page<Record> page = Student.me.findByParams(pageNumber, pageSize, search_type, condit);
		
		setAttr("search_type", search_type);
		setAttr("condit", condit);
		setAttr("page", page);
		render("studentManage.jsp");
	}
	
	//添加或编辑学生
	@Before(Tx.class)
	public void updateStudent(){
		ResponseModel rm = new ResponseModel();
		
		String old_sid = getPara("old_sid");
		
		Student student = getModel(Student.class);
		
		//qid为空时添加题目
		if(StrKit.isBlank(old_sid)){
			if(student.save()){
				rm.setSuccess(true);
				rm.setType("add");
				rm.msgSuccess("添加成功,刷新即可看到新增信息");
			}else{
				rm.setSuccess(false);
				rm.setType("add");
				rm.msgFailed("添加失败");
			}
		}else{
			if (Student.me.deleteById(old_sid) && student.save()) {
				rm.setSuccess(true);
				rm.setType("edit");
				rm.msgSuccess("修改成功");
			} else {
				rm.setSuccess(false);
				rm.setType("edit");
				rm.msgSuccess("修改失败");
			}
		}
		
		renderJson(rm);
	}
	
	public void checkExsitStudent(){
		String sid = getPara("sid");//账号
		Student student = Student.me.findById(sid);
		if(student != null){
			renderJson(true);
		}else{
			renderJson(false);
		}
	}
	
	/**
	 * 删除学生信息
	 */
	@Before(Tx.class)
	public void deleteStudent(){
		ResponseModel rm = new ResponseModel();
		String sid = getPara("id");
		String delType = getPara("delType");
		
		if (delType.trim().equals("s")){
			
			if(StrKit.notBlank(sid)){
				if(Student.me.deleteById(sid)){
					rm.setSuccess(true);
					rm.msgSuccess("删除成功");
				}else{
					rm.setSuccess(false);
					rm.msgFailed("删除失败");
				}
			}else{
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}
		}else if (delType.trim().equals("m")){
			int re = Student.me.deleteStudents(sid);
			if( 1 == re ){
				rm.setSuccess(true);
				rm.msgFailed("删除成功");
			}else if( 0 == re ){
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}else if( 2 == re ){
				rm.setSuccess(false);
				rm.msgFailed("删除失败，只删除选中部分数据，请刷新后重试");
			}else{
				rm.setSuccess(false);
				rm.msgFailed("删除失败");
			}
		}else{
			rm.setSuccess(false);
			rm.msgFailed("删除失败");
		}
		
		renderJson(rm);
	}
	/**
	 * 下载学生上传模板
	 */
	public void downloadStudentTemplate(){
		String path = "/resources/templates/学生信息上传模板.xlsx";
		File file = new File(PathKit.getWebRootPath()+path);
		if(file.exists()) {
			System.out.println("文件存在");
			renderFile(file);
		}else{
			renderText("文件不存在");
		}
	}
	
	/**
	 * 批量上传学生信息
	 */
	@Before(Tx.class)
	public void uploadStudents(){
		ResponseModel re = new ResponseModel();
		int result = 0;
		try {
			result = Student.me.readWriteFileExcel(getRequest());
		} catch (FileUploadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
		
		renderHtml("<script>window.parent.afterUpload(" + result + ");</script>");
	}
}
