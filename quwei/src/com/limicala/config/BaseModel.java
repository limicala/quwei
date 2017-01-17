package com.limicala.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
/**
 * Model基类
 * @author wrj
 *
 * @param <M>
 */
public abstract class  BaseModel<M extends Model<?>> extends Model<M> {
	private static final long serialVersionUID = -4447796116787868438L;
	
	/**
	 * 构建数组参数
	 * @param vArr
	 * @return
	 */
	protected StringBuilder initArrayParams(Integer[] vArr){
		StringBuilder sql = new StringBuilder("");
		if(vArr != null && vArr.length > 0){
			int i = 1;
			for(Integer v : vArr){
				if(i == vArr.length){
					sql.append(v);
				}else{
					sql.append(v).append(",");
				}
				i++;
			}
		}
		return sql;
	}
	
	/**
	 * 构建数组参数
	 * @param vArr
	 * @return
	 */
	protected StringBuilder initArrayParams(String[] vArr){
		StringBuilder sql = new StringBuilder("");
		if(vArr != null && vArr.length > 0){
			int i = 1;
			for(String v : vArr){
				if(i == vArr.length){
					sql.append(v);
				}else{
					sql.append(v).append(",");
				}
				i++;
			}
		}
		return sql;
	}
	
	/**
	 * 判断是否存在
	 * @param id
	 * @return
	 */
	public boolean isExists(Integer id){
		if(id !=null){
			M m = this.findById(id);
			if(m != null){
				return true;
			}
		}
		return false;
	}
	
	public boolean notExists(Integer id){
		return  !isExists(id);
	}
	
	/**
	 * @author:zbn
	 * @todo:查询记录数
	 * @param: tableName,params
	 * @return:Integer
	 */
	public Number getCount(String tableName,Map<String,Object> params){
		
		int size = params.size();
		Object[] paramArray = new Object[size];
		
		StringBuilder sql = new StringBuilder("select count(1) from "+tableName+" where 1=1 ");
		
		if(0<size){
			Set<Entry<String,Object>> entrySet = params.entrySet();
			Iterator<Entry<String, Object>> iterator = entrySet.iterator();
			
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				if(key.contains("type") || key.contains("status")){
					sql.append(" and "+key+" in");
					Integer[] values = (Integer[])entry.getValue();
					sql.append("(");
					for(int x=0;x<values.length;x++){
						if(x==0){
							sql.append(values[x]);
						}else{
							sql.append(","+values[x]);
						}
					}
					sql.append(")");
				}else{
					sql.append(" and "+key+"=").append(entry.getValue());
				}
				
			}
		}
		
		return Db.queryNumber(sql.toString());
	}
	public String getTableName(){
		return null;
	}
}
