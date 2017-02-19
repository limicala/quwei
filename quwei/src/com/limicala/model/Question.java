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
	
	
	public Page<Record> findByParams(Integer pageNumber, Integer pageSize, String qtype){
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
		
		return Db.paginate(pageNumber, pageSize, selectSql.toString(), fromSql
				.append(whereSql).toString());
	}
}
