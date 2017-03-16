package com.limicala.controller;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.jfinal.aop.Before;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.limicala.config.BaseController;
import com.limicala.constant.AppConstant;
import com.limicala.constant.AppTableConstant;
import com.limicala.model.Admin;

import com.limicala.model.ConfigOS;
import com.limicala.model.History;
import com.limicala.model.Question;

import com.limicala.model.ResponseModel;
import com.limicala.model.Student;
import com.limicala.util.ExcelUtil;
import com.limicala.util.QrcodeUtil;
import com.limicala.util.SessionUtil;

public class AdminController extends BaseController{
	
	//管理员登录
	public void index(){
		render("login.jsp");
	}
	
	//******************************************************************************************
	//******************************************************************************************
	//**************************************** 管 理 员 首 页  *************************************
	//******************************************************************************************
	//******************************************************************************************

	/**
	 * 管理员首页
	 */
	public void mainView(){
		render("index.jsp");
	}
	
	/**
	 * 检查用户账号是否存在
	 */
	public void checkAid(){
		String aid = getPara("account");//账号
		if(Admin.me.checkIdExist(aid)){
			renderJson(true);
		}else{
			renderJson(false);
		}
	}
	
	/**
	 * 生成响应二维码
	 */
	public void getQrcode(){
		String url = getRequest().getScheme()
				+"://"+getRequest().getServerName()
				+":"+getRequest().getServerPort()
				+getRequest().getContextPath();//获取系统答题首页URL
		try {
			MatrixToImageWriter.writeToStream(QrcodeUtil.getQrcode(url), "png", getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//因为前面应用使用Respones对象传递数据了，所有之后就不用再请求，就renderNull();
		renderNull();
	}
	
	/**
	 * 下载二维码
	 */
	public void downloadQrcode(){
		String url = getRequest().getScheme()
				+"://"+getRequest().getServerName()
				+":"+getRequest().getServerPort()
				+getRequest().getContextPath();//获取系统答题首页URL
		String qrname = "趣味问答二维码";
		try {//设置响应头
			getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String( qrname.getBytes("utf-8"), "ISO8859-1" )+".png");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//生成二维码并直接写入响应流
		try {
			MatrixToImageWriter.writeToStream(QrcodeUtil.getQrcode(url), "png", getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{//关闭响应流
			try {
				getResponse().getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				getResponse().getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 注销账号，退出系统
	 */
	public void loginout(){
		this.getSession().invalidate();
		redirect("/admin");
	}
	
	/**
	 * 【ajax】判断登录是否合法
	 */
	public void doLogin(){
		ResponseModel rm = new ResponseModel();
		String aid = getPara("id");//获取账号
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
	
	
	//******************************************************************************************
	//******************************************************************************************
	//**************************************** 管 理 员 管 理 **************************************
	//******************************************************************************************
	//******************************************************************************************
	
	/**
	 * 管理员管理首页初始化
	 */
	public void userManageView(){
		Integer pageNumber = this.getParaToInt("pageNumber", 1);
		Integer pageSize = 6;
		String account = getPara("account");
		Page<Record> page = Admin.me.findByParams(pageNumber, AppConstant.PAGE_SIZE, account);
		setAttr("url", "userManageView");
		setAttr("account", account);
		setAttr("page", page);
		render("userManage.jsp");
	}

	/**
	 * 添加单个管理员
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
	 * 修改管理员信息
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
		boolean result = Admin.me.deleteById(id);
		if (result == true) {
			rm.msgSuccess("删除管理员成功");
		} else{
			rm.msgFailed("删除管理员失败");
		}
		this.renderJson(rm);
	}
	
	
	
	//******************************************************************************************
	//******************************************************************************************
	//****************************************** 题 库 管 理 **************************************
	//******************************************************************************************
	//******************************************************************************************
	
	/**
	 * 题库管理首页初始化
	 */
	public void questionManageView(){
		Integer jpn = this.getParaToInt("jpn", 1);//判断题
		Integer spn = this.getParaToInt("spn", 1);//单项选择题
		Integer mpn = this.getParaToInt("mpn", 1);//多项选择题
		Integer type = this.getParaToInt("ct", 1);//当前的tab页面
		
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
	 * 两种情况：1、获取到的id为空即添加新管理员
	 * 			2、获取到的id不为空即修改相应管理员信息
	 */
	@Before(Tx.class)
	public void updateQuestion(){
		ResponseModel rm = new ResponseModel();
		
		String qid = getPara("id");//获取id
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
	
	/**
	 * 下载题库上传模板
	 */
	public void downloadTemplate(){
		String templateType = getPara("templateType");
		String path = "";
		if ( templateType.equals("single") ){
			path = "/resources/templates/单项选择题上传模板.xlsx"; 
		}else if ( templateType.equals("multi") ){
			path = "/resources/templates/多项选择题上传模板.xlsx"; 
		}else if ( templateType.equals("judge") ){
			path = "/resources/templates/判断题上传模板.xlsx";
		}else{
			return;
		}
		File file = new File(PathKit.getWebRootPath()+path);
		if(file.exists()) {
			renderFile(file);
		}
	}
	
	/**
	 * 批量上传问题到题库
	 * 前端利用隐藏iframe实现伪不刷新上传
	 */
	@Before(Tx.class)
	public void uploadQuestions(){
		int result = 0;
		try {
			result = Question.me.readWriteFileExcel(getRequest());
		} catch (FileUploadException | IOException e) {
			e.printStackTrace();
		}
		//“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
		renderHtml("<script>window.parent.afterUpload(" + result + ");</script>");
	}
	
	
	//******************************************************************************************
	//******************************************************************************************
	//************************************* 答 题 记 录  管 理 ************************************
	//******************************************************************************************
	//******************************************************************************************
	
	/**
	 * 答题记录首页初始化
	 */
	public void historyManageView(){
		Integer hpn = this.getParaToInt("hpn", 1);//当前页码
		String condi = this.getPara("condi", "");//条件类型
		String condiValue = this.getPara("condiValue", "");//条件值

		Page<Record> page = History.me.findByParams(hpn, AppConstant.HISTORY_PAGE_SIZE, condi, condiValue);
	
		setAttr("url", "historyManageView");
		setAttr("condi", condi);
		setAttr("condiValue", condiValue);
		setAttr("page1", page);

		render("historyManage.jsp");
	}
	
	
	/**
	 * 删除题库信息
	 */
	@Before(Tx.class)
	public void deleteHistory(){
		ResponseModel rm = new ResponseModel();
		String hid = getPara("hid");
		//返回3个状态：“0”删除失败，一个都没删除成功；“1”删除成功；“2”只删除部分
		int re = History.me.deleteHistories(hid);
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
		renderJson(rm);
	}
	
	/**
	 * 下载答题记录
	 */
	public void downloadHistories(){
		String condi = getPara("dlCondi");
		XSSFWorkbook workbook = null;
		String excelName = "答题记录_"+(new SimpleDateFormat("yyyy.MM.dd")).format(new Date());
		ArrayList<History> hlist = History.me.findByCondi(condi);
		if (hlist.size() != 0){
			workbook = ExcelUtil.getHistoryExcel(hlist);
	        try {//设置响应头Content-Disposition
				getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String( excelName.getBytes("utf-8"), "ISO8859-1" )+".xlsx");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}  
			try {
				workbook.write(getResponse().getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					getResponse().getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					getResponse().getOutputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		renderNull();
	}
	
	/**
	 * 清空所有记录
	 */
	public void deleteAllHistories(){
		ResponseModel rm = new ResponseModel();
		String password = getPara("password");
		String aid = SessionUtil.getAdminUserId(getSession());
		Admin admin = Admin.me.findById(aid);
		if(admin != null){
			if(admin.getStr("apassword").equals(password)){
				Integer flag = Db.update("TRUNCATE TABLE "+AppTableConstant.HISTORY);
				if(flag >= 0){
					rm.msgSuccess("删除成功");
				}else{
					rm.msgFailed("删除失败");
				}
			}else{
				rm.msgFailed("输入密码错误");
			}
		}else{
			rm.msgFailed("用户不存在");
		}
		renderJson(rm);
	}

	//******************************************************************************************
	//******************************************************************************************
	//****************************************** 系 统 配 置 **************************************
	//******************************************************************************************
	//******************************************************************************************
	
	/**
	 * 系统配置首页初始化
	 */
	public void configView(){
		setAttr("configOS", ConfigOS.me.findById(new Integer(1)));
		setAttr("single_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_SINGLE, AppTableConstant.QUESTION_LIMIT));
		setAttr("judge_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_JUDGE, AppTableConstant.QUESTION_LIMIT));
		setAttr("multi_ness", Question.me.findCountByParams(AppTableConstant.QUESTION_MUTIL, AppTableConstant.QUESTION_LIMIT));
		render("config.jsp");
	}
	
	/**
	 * 修改配置信息
	 */
	public void update_config(){
		ConfigOS configOS = getModel(ConfigOS.class);
		if(configOS.getInt("cid")!= null){
			renderJson(configOS.update());
		}else{
			configOS.set("cid", 1);
			renderJson(configOS.save());
		}
	}

	//******************************************************************************************
	//******************************************************************************************
	//****************************************** 学 生 管 理 **************************************
	//******************************************************************************************
	//******************************************************************************************

	/**
	 * 学生管理页初始化
	 */
	public void stuManageView(){
		Integer pageNumber = this.getParaToInt("pageNumber", 1);
		Integer search_type = getParaToInt("search_type", 1);
		Integer pageSize = 10;
		String condit = this.getPara("condit", "");
		Page<Record> page = Student.me.findByParams(pageNumber, AppConstant.PAGE_SIZE, search_type, condit);
		setAttr("search_type", search_type);
		setAttr("condit", condit);
		setAttr("page", page);
		render("studentManage.jsp");
	}
	
	/**
	 * 添加或编辑学生
	 * 两种情况：1、id为空即添加
	 * 			2、id不为空即编辑修改
	 */
	@Before(Tx.class)
	public void updateStudent(){
		ResponseModel rm = new ResponseModel();
		String old_sid = getPara("old_sid");
		Student student = getModel(Student.class);
		//old_sid为空时添加学生
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
			if (Student.me.updateStudent(student, old_sid)) {
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
	 * 检测学生学号是否存在
	 */
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
	 * 清空所有学生信息
	 * 要通过密码验证
	 */
	public void deleteAllStudent(){
		ResponseModel rm = new ResponseModel();
		String password = getPara("password");
		String aid = SessionUtil.getAdminUserId(getSession());
		Admin admin = Admin.me.findById(aid);
		if(admin != null){
			if(admin.getStr("apassword").equals(password)){
				Integer flag = Db.update("TRUNCATE TABLE "+AppTableConstant.STUDENT);
				//System.out.println("-------------"+flag);
				if(flag >= 0){
					rm.msgSuccess("删除成功");
				}else{
					rm.msgFailed("删除失败");
				}
			}else{
				rm.msgFailed("输入密码错误");
			}
		}else{
			rm.msgFailed("用户不存在");
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
			renderFile(file);
		}
	}
	
	/**
	 * 批量上传学生信息
	 */
	@Before(Tx.class)
	public void uploadStudents(){
		int result = 0;
		try {
			result = Student.me.readWriteFileExcel(getRequest());
		} catch (FileUploadException | IOException e) {
			e.printStackTrace();
		}
		//“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
		renderHtml("<script>window.parent.afterUpload(" + result + ");</script>");
	}
}
