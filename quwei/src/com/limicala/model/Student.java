package com.limicala.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;
import com.limicala.constant.AppTableConstant;
import com.limicala.util.ExcelUtil;

/**
 * 学生表的model类
 */
public class Student extends BaseModel<Student>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Student me = new Student();
	
	public String getTableName(){
		return " " + AppTableConstant.STUDENT + " ";
	}
	
	private final static class SearchType{
		public static final Integer ID = 1;
		public static final Integer NAME = 2;
		public static final Integer COLLEGE = 3;
	}
	
	//问题库列表(初始化和查询结合)
	public Page<Record> findByParams(Integer pageNumber, Integer pageSize, Integer search_type, String condit){
		// TODO Auto-generated method stub
		StringBuilder selectSql = new StringBuilder();
		selectSql.append(" select * ");
		StringBuilder fromSql = new StringBuilder();
		fromSql.append("from student");
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" where 1 = 1 ");
		if(StrKit.notBlank(condit)){
			if(search_type == SearchType.ID){
				whereSql.append(" and sid like ").append("'%").append(condit).append("%'");
			}else if(search_type == SearchType.NAME){
				whereSql.append(" and sname like ").append("'%").append(condit).append("%'");
			}else if(search_type == SearchType.COLLEGE){
				whereSql.append(" and scollege like ").append("'%").append(condit).append("%'");
			}
		}
		
		return Db.paginate(pageNumber, pageSize, selectSql.toString(), fromSql
				.append(whereSql).toString());
	}
	/**
	 * 返回3个状态：“0”删除失败，一个都没删除成功；“1”删除成功；“2”只删除部分
	 * @param ids
	 * @return
	 */
	public int deleteStudents(String ids){
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
			if(Student.me.deleteById(id[i]))
				idDelNum++;
		}
		if (idAllNum.equals(idDelNum)){
			return 1;
		}else if(idAllNum.equals("0")){
			return 0;
		}else
			return 2;
	}
	
	/**
	 * 更新学生信息
	 * @param student
	 * @param old_sid
	 * @return
	 */
	public boolean updateStudent(Student student,String old_sid){
		String sid = student.getStr("sid");
		String sname = student.getStr("sname");
		String scollege = student.getStr("scollege");
		boolean flag = Db.update("update "+getTableName()+"set sid=?,sname=?,scollege=? where sid=?", sid,sname,scollege,old_sid) > 0;
		History history = History.me.findModelByStuNum(old_sid);
		if(history != null){
			flag = flag && (history.set("hstuNum", sid)
			.set("hname", sname)
			.set("hcollege", scollege)
			.update());
		}
		return flag;
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
		
		String qtype = "";//题型
		String fileName = "";//上传文件名字
		
		for(FileItem item : list){
			//System.out.println("接受参数");
			//如果fileitem中封装的是普通输入项的数据
			if(!item.isFormField()){
				//如果fileitem中封装的是上传文件
				//得到上传的文件名称，
				fileName = item.getName();

				Workbook workbook = null;
				try{
					workbook = new HSSFWorkbook(item.getInputStream()); 
				}catch(Exception e){
					workbook = new XSSFWorkbook(item.getInputStream()); 
				}
				//“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
				if (fileName.trim().equals("")){//判断文件名是否为空，为空即当做失败
					flag = 0;
				}else if (!ExcelUtil.checkStudentStandard(workbook)){//判断上传Excel内容格式和题型是否符合
					flag = 2;
				}else if (ExcelUtil.excelAllRowNum(workbook) == 1){//判断上传Excel文件是否有数据
					flag = 4;
				}else{
					int allNum = ExcelUtil.excelAllRowNum(workbook) - 1;//获取表格有效总记录数目
					System.out.println("有效行数"+allNum);
					List<Student> studentList = (ArrayList<Student>)ExcelUtil.readStudentExcel(workbook, fileName);
					
					int insertNum = 0;
					
					for (Student s : studentList){
						if ((!s.getStr("sid").trim().equals("")) && (!s.getStr("sname").trim().equals(""))){
							if (s.save())
								insertNum++;
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
				//删除处理文件上传时生成的临时文件
				item.delete();
			}
		}
		return flag;
	}
}
