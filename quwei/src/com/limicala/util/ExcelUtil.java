package com.limicala.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.limicala.model.Question;

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
	public static List<Question> readExcel(InputStream excelInputStream, String excelName){
		ArrayList<Question> questionList = new ArrayList<Question>();
		if (excelName.equalsIgnoreCase(".xls")){
			questionList = (ArrayList<Question>) readQuestionFromExcelXlsx(excelInputStream);
		}else if(excelName.equalsIgnoreCase(".xlsx")){
			questionList = (ArrayList<Question>) readQuestionFromExcelXls(excelInputStream);
		}
		return questionList;
	}
	
	/**
	 * 读取后缀为“xls”的Excel文件(2003版本)
	 * @param excelInputStream
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<Question> readQuestionFromExcelXls(InputStream excelInputStream){
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(excelInputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row = sheet.getRow(0);
			Question nq;
			if (row.getPhysicalNumberOfCells() == 3){//判断题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", row.getCell(0).getStringCellValue());//获取题目内容
					nq.set("qexplain", row.getCell(2).getStringCellValue());//获取题目注释
					String answer = row.getCell(1).getStringCellValue();//获取答案
					if (answer.equals("对")){
						nq.set("qanswer", 1);//获取题目注释
					}else if(answer.equals("错")){
						nq.set("qexplain", 0);//获取题目注释
					}
					questionList.add(i-1, nq);
				}
			}else if (row.getFirstCellNum() == 7){//单选题和多选题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", row.getCell(0).getStringCellValue());//获取题目内容
					nq.set("qa", row.getCell(0).getStringCellValue());//A内容
					nq.set("qb", row.getCell(0).getStringCellValue());//B内容
					nq.set("qc", row.getCell(0).getStringCellValue());//C内容
					nq.set("qd", row.getCell(0).getStringCellValue());//D内容
					nq.set("qanswer", row.getCell(0).getStringCellValue());//答案内容
					nq.set("qexplain", row.getCell(2).getStringCellValue());//获取题目注释
					questionList.add(i-1, nq);
				}
			}
		} catch (IOException e) {
			System.out.println("readQuestionFromExcelXls抛异常。");
			e.printStackTrace();
		}
		
		return questionList;
	}
	
	/**
	 * 读取后缀为“xlsx”的Excel文件(2007版本)
	 * @param excelInputStream
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<Question> readQuestionFromExcelXlsx(InputStream excelInputStream){
		ArrayList<Question> questionList = new ArrayList<Question>();
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(excelInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			Question nq;
			if (row.getPhysicalNumberOfCells() == 3){//判断题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", row.getCell(0).getStringCellValue());//获取题目内容
					nq.set("qexplain", row.getCell(2).getStringCellValue());//获取题目注释
					String answer = row.getCell(1).getStringCellValue();//获取答案
					if (answer.equals("对")){
						nq.set("qanswer", 1);//获取题目注释
					}else if(answer.equals("错")){
						nq.set("qexplain", 0);//获取题目注释
					}
					questionList.add(i-1, nq);
				}
			}else if (row.getFirstCellNum() == 7){//单选题和多选题
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
					row = sheet.getRow(i);
					nq = new Question();
					nq.set("qcontent", row.getCell(0).getStringCellValue());//获取题目内容
					nq.set("qa", row.getCell(0).getStringCellValue());//A内容
					nq.set("qb", row.getCell(0).getStringCellValue());//B内容
					nq.set("qc", row.getCell(0).getStringCellValue());//C内容
					nq.set("qd", row.getCell(0).getStringCellValue());//D内容
					nq.set("qanswer", row.getCell(0).getStringCellValue());//答案内容
					nq.set("qexplain", row.getCell(2).getStringCellValue());//获取题目注释
					questionList.add(i-1, nq);
				}
			}
		} catch (IOException e) {
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
	public static int excelAllRowNum(InputStream excelInputStream){
		int num = 0;
		Workbook workbook;
		try {
			workbook = new HSSFWorkbook(excelInputStream);
			HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
			num =  sheet.getPhysicalNumberOfRows();
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(excelInputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				num =  sheet.getPhysicalNumberOfRows();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			System.out.println("excelAllRowNum抛异常。");
		}
		return num;
	}
	
	/**
	 * 判断题型适合和上传的表格内容表头相同
	 * @param excelInputStream
	 * @param qtype
	 * @return
	 */
	@SuppressWarnings("resource")
	public static boolean checkTemplateStandard(InputStream excelInputStream, String qtype){
		boolean flag = true;
		int num = 0;
		
		try {
			
			HSSFRow row = new HSSFWorkbook(excelInputStream).getSheetAt(0).getRow(0);
			num =  row.getPhysicalNumberOfCells();
			
			if (num != 3 || num != 7){//上传模板不符合
				flag = false;
			}else if (num == 3 && qtype.trim().equals("1")){
				if(!row.getCell(0).toString().matches("内容") || !row.getCell(1).toString().matches("答案") )
					flag = false;
			}else if (num == 7 && (qtype.trim().equals("2") || qtype.trim().equals("3"))){
				if(!row.getCell(0).toString().matches("内容")){
					flag = false;
				}else if ( !row.getCell(1).toString().matches("A")){
					flag = false;
				}else if ( !row.getCell(2).toString().matches("B")){
					flag = false;
				}else if ( !row.getCell(3).toString().matches("C")){
					flag = false;
				}else if ( !row.getCell(4).toString().matches("D")){
					flag = false;
				}else if ( !row.getCell(5).toString().matches("答案")){
					flag = false;
				}
			}
		} catch (Exception e) {
			try {
				
				XSSFRow row = new XSSFWorkbook(excelInputStream).getSheetAt(0).getRow(0);
				num =  row.getPhysicalNumberOfCells();
				
				if (num != 3 || num != 7){//上传模板不符合
					flag = false;
				}else if (num == 3 && qtype.trim().equals("1")){
					if(!row.getCell(0).toString().matches("内容") || !row.getCell(1).toString().matches("答案") )
						flag = false;
				}else if (num == 7 && (qtype.trim().equals("2") || qtype.trim().equals("3"))){
					if(!row.getCell(0).toString().matches("内容")){
						flag = false;
					}else if ( !row.getCell(1).toString().matches("A")){
						flag = false;
					}else if ( !row.getCell(2).toString().matches("B")){
						flag = false;
					}else if ( !row.getCell(3).toString().matches("C")){
						flag = false;
					}else if ( !row.getCell(4).toString().matches("D")){
						flag = false;
					}else if ( !row.getCell(5).toString().matches("答案")){
						flag = false;
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return flag;
	}
}
