package com.limicala.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.limicala.config.BaseModel;
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
}
