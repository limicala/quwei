package com.limicala.model;


import java.util.List;
import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;

import com.limicala.constant.AppTableConstant;
import com.limicala.util.ExcelUtil;
/**
 * 问题表的Model类
 * @author red
 *
 */
public class Question extends BaseModel<Question>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6174983832181476567L;

	public static Question me = new Question();
	
	public String getTableName(){
		return " "+AppTableConstant.QUESTION+" ";
	}
	
	//问题库列表(初始化和查询结合)
	public Page<Record> findByParams(Integer pageNumber, Integer pageSize, String qtype, String condit){
		// TODO Auto-generated method stub
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from question");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		if (StrKit.notBlank(qtype)) {
			whereSql.append(" and qtype = ").append(qtype);
		}
		if (StrKit.notBlank(condit)) {
			whereSql.append(" and qcontent like ").append("'%").append(condit).append("%'");
		}
		
		return Db.paginate(pageNumber, pageSize, selectSql.toString(), fromSql
				.append(whereSql).toString());
	}
	
	public boolean updateQuestion(String qid, String content, String a, String b, String c, String d, String answer, String explain){
		return Question.me.findById(qid).set("qcontent", content).set("qa", a).set("qb", b).set("qc", c).set("qd", d)
				.set("qanswer", answer).set("qexplain", explain).update();
	}
	
	/**
	 * 返回3个状态：“0”删除失败，一个都没删除成功；“1”删除成功；“2”只删除部分
	 * @param ids
	 * @return
	 */
	public int deleteQuestions(String ids){
		/*
		 * split分隔符总结
			1.字符"|","*","+"都得加上转义字符，前面加上"\\"。
			2.而如果是"\"，那么就得写成"\\\\"。
			3.如果一个字符串中有多个分隔符，可以用"|"作为连字符。
		 */
		String[] id = ids.split("\\|");
		Integer idAllNum = id.length;
		int idDelNum = 0;
	
		for (int i = 0; i < idAllNum; ++i){
			if(Question.me.deleteById(id[i]))
				idDelNum++;
		}
		if (idAllNum.equals(idDelNum)){
			return 1;
		}else if(idAllNum.equals("0")){
			return 0;
		}else
			return 2;
	}
	
	public List<Question> findQuestionByParams(Integer qtype, Integer qlimit){
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from "+getTableName());
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		if (qtype > 0) {
			whereSql.append(" and qtype= "+qtype);
		}
		if (qlimit >= 0){
			whereSql.append(" and qlimit="+qlimit);
		}
		return find(selectSql.append(fromSql).append(whereSql).toString());
	}
	public Integer findCountByParams(Integer qtype, Integer qlimit){
		List<Question> temp = findQuestionByParams(qtype, qlimit);
		return temp.size();
	}
	/**
	 * 接受请求，读取Excel表格然后存储
	 * 
	 * @param req
	 * @return    “1”存储成功   “0”存储失败  “2”上传模板出错  “3”数据填充出错，数据丢失  "4"没数据
	 * @throws FileUploadException 
	 * @throws IOException 
	 */
	public int readWriteFileExcel(HttpServletRequest req) throws FileUploadException, IOException{
		int flag = 0;
		//使用Apache文件上传组件处理文件上传步骤：
		//1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8"); 
		
		//3、判断提交上来的数据是否是上传表单的数据
		if(!ServletFileUpload.isMultipartContent(req)){
			//按照传统方式获取数据
			return 0;
		}
		
		//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		List<FileItem> list = upload.parseRequest(req);
		
		InputStream fileExcelInput;//定义输入流接受Excel文件
		String qtype = "";
		String fileName = "";
		FileItem tempItem;
		
		//先遍历获取到题型
		for(FileItem item : list){
			//System.out.println("接受参数");
			//如果fileitem中封装的是普通输入项的数据
			if(item.isFormField()){
				String name = item.getFieldName();
				//解决普通输入项的数据的中文乱码问题
				//String value = item.getString("UTF-8");
				if (name.trim().equals("uploadType")){
					qtype = item.getString("UTF-8");
				}
			}
		}
		
		if (qtype.trim().equals("")){
			return 0;
		}else if (qtype.trim().equals("judge")){
			qtype = "1";
		}else if (qtype.trim().equals("single")){
			qtype = "2";
		}else if (qtype.trim().equals("multi")){
			qtype = "3";
		}
		

		for(FileItem item : list){
			//System.out.println("接受参数");
			//如果fileitem中封装的是普通输入项的数据
			if(!item.isFormField()){
				//System.out.println("抓取到图片");
				//如果fileitem中封装的是上传文件
				//得到上传的文件名称，
				fileName = item.getName();
				//System.out.println(fileName);
				//获取item中的上传文件的输入流
				fileExcelInput = item.getInputStream();
				
				//System.out.println(fileExcelInput.available());
				
				
				if (fileName.trim().equals("")){//判断文件名是否为空，为空即当做失败
					flag = 0;
				}else if (ExcelUtil.excelAllRowNum(fileExcelInput) == 1){//判断上传Excel文件是否有数据
					flag = 4;
				}else if (!ExcelUtil.checkTemplateStandard(fileExcelInput, qtype)){//判断上传Excel内容格式和题型是否符合
					flag = 2;
				}else{
					int allNum = ExcelUtil.excelAllRowNum(fileExcelInput) - 1;//获取表格有效总记录数目
					
					ArrayList<Question> questionList = (ArrayList<Question>)ExcelUtil.readExcel(fileExcelInput, fileName);
				
					int insertNum = 0;
					
					if (qtype.trim().equals("1")){//判断题
						for (Question q : questionList){
							if ((!q.get("qcontent").toString().trim().equals("")) && (!q.get("qanswer").toString().trim().equals(""))){
								if (q.set("qtype", 1).save())
									insertNum++;
							}
						}
					}else if(qtype.trim().equals("2") || qtype.trim().equals("3")){//选择题
						for (Question q : questionList){
							if (q.get("qcontent").toString().trim().equals("") || 
									q.get("qa").toString().trim().equals("") ||
									q.get("qb").toString().trim().equals("") ||
									q.get("qc").toString().trim().equals("") ||
									q.get("qd").toString().trim().equals("") ||
									q.get("qanswer").toString().trim().equals("")){
								continue;
							}else{
								if (q.set("qtype", qtype).save())
									insertNum++;
							}
						}
					}
					//插入数据后，判断是否全部插入成功
					if (allNum == insertNum){
						flag = 1;
					}else if (allNum > insertNum && insertNum > 0){
						flag = 3;
					}else{
						flag = 0;
					}
				}
				
				//关闭输入流
				fileExcelInput.close();
				//删除处理文件上传时生成的临时文件
				item.delete();
				//tempItem = item;
			}
		}
		
		return flag;
	}
}
