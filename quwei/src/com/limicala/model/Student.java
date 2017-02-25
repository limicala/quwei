package com.limicala.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;

/**
 * 学生表的model类
 * @author red
 *
 */
public class Student extends BaseModel<Student>{
	
	public static Student me = new Student();
	
	
	private final static class SearchType{
		public static final Integer ID = 1;
		public static final Integer NAME = 2;
		public static final Integer PROFESSION = 3;
		public static final Integer COLLEGE = 4;
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
			}else if(search_type == SearchType.PROFESSION){
				whereSql.append(" and sprofession like ").append("'%").append(condit).append("%'");
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
}
