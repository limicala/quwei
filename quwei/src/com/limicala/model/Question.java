package com.limicala.model;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;
import com.limicala.constant.AppTableConstant;
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
}
