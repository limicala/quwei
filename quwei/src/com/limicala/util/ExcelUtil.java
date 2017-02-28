package com.limicala.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.limicala.constant.AppConstant;
import com.limicala.model.History;
import com.limicala.model.Question;
import com.limicala.model.Student;

public final class ExcelUtil {
	
	/**
	 * 接受文件输入流和文件名，判断版本并且调用相应的读取方式
	 * 
	 * 注意：此处只是简单的将数据全部读入容器，不会检测数据是否符合规范
	 * 
	 * @param excelInputStream
	 * @param excelName
	 * @return
	 */
	public static  List<Question> readExcel(Workbook workbook, String excelName){
		ArrayList<Question> questionList = null;
		if (excelName.endsWith(".xls")){
			questionList = (ArrayList<Question>) readQuestionFromExcelXls(workbook);
		}else if(excelName.endsWith(".xlsx")){
			questionList = (ArrayList<Question>) readQuestionFromExcelXlsx(workbook);
		}
		return questionList;
	}
	
	/**
	 * 接受文件输入流和文件名，判断版本并且调用相应的读取方式
	 * 
	 * 注意：此处只是简单的将数据全部读入容器，不会检测数据是否符合规范
	 * 
	 * @param excelInputStream
	 * @param excelName
	 * @return
	 */
	public static  List<Student> readStudentExcel(Workbook workbook, String excelName){
		List<Student> studentList = null;
		if (excelName.endsWith(".xls")){
			studentList = (ArrayList<Student>) readStudentFromExcelXls(workbook);
		}else if(excelName.endsWith(".xlsx")){
			studentList = (ArrayList<Student>) readStudentFromExcelXlsx(workbook);
		}
		return studentList;
	}
	
	/**
	 * 读取后缀为“xls”的Excel文件(2003版本)
	 * @param excelInputStream
	 * @return
	 */
	public static List<Student> readStudentFromExcelXls(Workbook workbook) {
		List<Student> studentList = new ArrayList<Student>();
		try {
			HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
				Student student = new Student();
				row = sheet.getRow(i);
				student.set("sid", handleHSSFCell(row.getCell(0)));//获取学生学号
				student.set("sname", handleHSSFCell(row.getCell(1)));//获取学生姓名
				student.set("scollege", handleHSSFCell(row.getCell(2)));//获取学生学院
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
	
	/**
	 * 读取后缀为“xlsx”的Excel文件(2007版本)
	 * @param excelInputStream
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Student> readStudentFromExcelXlsx(Workbook workbook) {
		List<Student> studentList = new ArrayList<Student>();
		XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
		XSSFRow row = null;
		Student student = null;
		try {
			 //System.out.println("读取的行数"+sheet.getPhysicalNumberOfRows());
			 Iterator rows = sheet.rowIterator();
			 rows.next();
			 while(rows.hasNext()){
				 row = (XSSFRow) rows.next();
				 student = new Student();
				 student.set("sid", handleXSSFCell(row.getCell(0)));//获取学生学号
				 student.set("sname", handleXSSFCell(row.getCell(1)));//获取学生姓名
			 	 student.set("scollege", handleXSSFCell(row.getCell(2)));//获取学生学院
				 studentList.add( student);
			 }
		} catch (Exception e) {
			System.out.println("readStudentFromExcelXlsx抛异常。");
			e.printStackTrace();
		}
		return studentList;
	}

	/**
	 * 读取后缀为“xls”的Excel文件(2003版本)
	 * @param excelInputStream
	 * @return
	 */
	public static List<Question> readQuestionFromExcelXls(Workbook workbook){
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			Question nq;
			if (row.getPhysicalNumberOfCells() == 3){//判断题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", handleHSSFCell(row.getCell(0)));//获取题目内容
					nq.set("qexplain", handleHSSFCell(row.getCell(2)));//获取题目注释
					String answer = handleHSSFCell(row.getCell(1));//获取答案
					if (answer.equals("对")){
						nq.set("qanswer", 1);
					}else if(answer.equals("错")){
						nq.set("qanswer", 0);
					}
					questionList.add(i-1, nq);
				}
			}else if (row.getPhysicalNumberOfCells() == 7){//单选题和多选题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", handleHSSFCell(row.getCell(0)));//获取题目内容
					nq.set("qa", handleHSSFCell(row.getCell(1)));//A内容
					nq.set("qb", handleHSSFCell(row.getCell(2)));//B内容
					nq.set("qc", handleHSSFCell(row.getCell(3)));//C内容
					nq.set("qd", handleHSSFCell(row.getCell(4)));//D内容
					nq.set("qanswer", handleHSSFCell(row.getCell(5)));//答案内容
					nq.set("qexplain", handleHSSFCell(row.getCell(6)));//获取题目注释
					questionList.add(i-1, nq);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return questionList;
	}
	
	/**
	 * 读取后缀为“xlsx”的Excel文件(2007版本)
	 * @param excelInputStream
	 * @return
	 */
	public static List<Question> readQuestionFromExcelXlsx(Workbook workbook){
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			Question nq;
			if (row.getPhysicalNumberOfCells() == 3){//判断题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", handleXSSFCell(row.getCell(0)));//获取题目内容
					nq.set("qexplain", handleXSSFCell(row.getCell(2)));//获取题目注释
					String answer = handleXSSFCell(row.getCell(1));//获取答案
					if (answer.equals("对")){
						nq.set("qanswer", 1);
					}else if(answer.equals("错")){
						nq.set("qanswer", 0);
					}
					questionList.add(i-1, nq);
				}
			}else if (row.getPhysicalNumberOfCells() == 7){//单选题和多选题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", handleXSSFCell(row.getCell(0)));//获取题目内容
					nq.set("qa", handleXSSFCell(row.getCell(1)));//A内容
					nq.set("qb", handleXSSFCell(row.getCell(2)));//B内容
					nq.set("qc", handleXSSFCell(row.getCell(3)));//C内容
					nq.set("qd", handleXSSFCell(row.getCell(4)));//D内容
					nq.set("qanswer", handleXSSFCell(row.getCell(5)));//答案内容
					nq.set("qexplain", handleXSSFCell(row.getCell(6)));//获取题目注释
					questionList.add(i-1, nq);
				}
			}
		} catch (Exception e) {
			System.out.println("readQuestionFromExcelXlsx抛异常。");
			e.printStackTrace();
		}
		return questionList;
	}
	
	/**
	 * 获取表格有效数据总行数
	 * @param excelInputStream
	 * @return
	 */
	public static int excelAllRowNum(Workbook workbook){
		return workbook.getSheetAt(0).getPhysicalNumberOfRows();
	}
	
	/**
	 * 判断题型适合和上传的表格内容表头相同
	 * @param excelInputStream
	 * @param qtype
	 * @return
	 */
	public static boolean checkTemplateStandard(Workbook workbook, String qtype){
		boolean flag = true;
		if (workbook.getSheetAt(0).getRow(0) == null )
			return false;
		int num = workbook.getSheetAt(0).getRow(0).getPhysicalNumberOfCells();
		try {
			Row row = workbook.getSheetAt(0).getRow(0);
			num =  row.getPhysicalNumberOfCells();
			if (num != 3 && num != 7){//上传模板不符合
				flag = false;
			}else if (num == 3 && qtype.trim().equals("1")){
				if(!row.getCell(0).toString().equals(AppConstant.QCONTENT) 
						|| !row.getCell(1).toString().equals(AppConstant.QANSWER) 
						|| !row.getCell(2).toString().equals(AppConstant.QEXPLAIN))
					flag = false;
			}else if (num == 7 && (qtype.trim().equals("2") || qtype.trim().equals("3"))){
				if(!row.getCell(0).toString().equals(AppConstant.QCONTENT)){
					flag = false;
				}else if ( !row.getCell(1).toString().equals(AppConstant.QA)){
					flag = false;
				}else if ( !row.getCell(2).toString().equals(AppConstant.QB)){
					flag = false;
				}else if ( !row.getCell(3).toString().equals(AppConstant.QC)){
					flag = false;
				}else if ( !row.getCell(4).toString().equals(AppConstant.QD)){
					flag = false;
				}else if ( !row.getCell(5).toString().equals(AppConstant.QANSWER)){
					flag = false;
				}else if ( !row.getCell(6).toString().equals(AppConstant.QEXPLAIN)){
					flag = false;
				}
			}else{
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 判断学生适合和上传的表格内容表头相同
	 * @param excelInputStream
	 * @param qtype
	 * @return
	 */
	public static boolean checkStudentStandard(Workbook workbook){
		boolean flag = true;
		int num = workbook.getSheetAt(0).getRow(0).getPhysicalNumberOfCells();
		try {
			Row row = workbook.getSheetAt(0).getRow(0);
			num =  row.getPhysicalNumberOfCells();
			System.out.println("表格列数为"+num);
			if (num != 3){//上传模板不符合
				flag = false;
			}else{
				
				if(!row.getCell(0).toString().equals(AppConstant.SID) 
						|| !row.getCell(1).toString().equals(AppConstant.SNAME) 
						|| !row.getCell(2).toString().equals(AppConstant.SCOLLEGE))
					flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 处理单元格数据(2003)
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String handleHSSFCell(HSSFCell cell){
		String value = "";
		if(cell != null){
			switch(cell.getCellType()){
				case HSSFCell.CELL_TYPE_STRING ://字符串类型
					value = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC ://数字类型
					value = String.valueOf(cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_FORMULA ://公式类型
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);      
		            value = String.valueOf(cell.getNumericCellValue()); 
					break;
				case HSSFCell.CELL_TYPE_BLANK :
					value = " ";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN :
					break;
				case HSSFCell.CELL_TYPE_ERROR :
					break;
				default:
					break;
			}
		}
		return value;
	}
	
	/**
	 * 处理单元格数据(2007)
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String handleXSSFCell(XSSFCell cell) {
		String value = "";
		if(cell != null){
			switch(cell.getCellType()){
				case XSSFCell.CELL_TYPE_STRING ://字符串类型
					value = cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_NUMERIC ://数字类型
					value = String.valueOf(cell.getNumericCellValue());
					break;
				case XSSFCell.CELL_TYPE_FORMULA ://公式类型
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);      
		            value = String.valueOf(cell.getNumericCellValue()); 
					break;
				case XSSFCell.CELL_TYPE_BLANK :
					value = " ";
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN :
					break;
				case XSSFCell.CELL_TYPE_ERROR :
					break;
				default:
					break;
			}
		}
		return value;
	}
	
	
	/**
	 * 处理单元格数据
	 * @param cell
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private static String handleCell(Cell cell) {
		String value = "";
		if(cell != null){
			switch(cell.getCellType()){
				case Cell.CELL_TYPE_STRING ://字符串类型
					value = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC ://数字类型
					value = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA ://公式类型
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);      
		            value = String.valueOf(cell.getNumericCellValue()); 
					break;
				case Cell.CELL_TYPE_BLANK :
					value = " ";
					break;
				case Cell.CELL_TYPE_BOOLEAN :
					break;
				case Cell.CELL_TYPE_ERROR :
					break;
				default:
					break;
			}
		}
		return value;
	}
	
	/**
	 * 获取答题记录Workbook
	 * @param hlist 
	 * @return
	 */
	public static XSSFWorkbook getHistoryExcel(ArrayList<History> hlist){
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("答题记录_"+(new SimpleDateFormat("yyyy_MM_dd-hh_mm")).format(new Date()));
		sheet.setDefaultColumnWidth(20);
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue(AppConstant.HSTU_NUM);
		row.createCell(1).setCellValue(AppConstant.HNAME);
		row.createCell(2).setCellValue(AppConstant.HCOLLEGE);
		row.createCell(3).setCellValue(AppConstant.HSCORE);
		row.createCell(4).setCellValue(AppConstant.HTIME);
		int i = 1;
		for (History h : hlist){
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(h.get("hstuNum").toString());
			row.createCell(1).setCellValue(h.get("hname").toString());
			row.createCell(2).setCellValue(h.get("hcollege").toString());
			row.createCell(3).setCellValue(h.get("hscore").toString());
			row.createCell(4).setCellValue(h.get("htime").toString().substring(0, 16));
			++i;
		}
		return workbook;
	}
}
