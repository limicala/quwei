package com.limicala.model;

import com.limicala.config.BaseModel;
/**
 * 配置表的Model类
 * @author wrj
 *
 */
public class ConfigOS extends BaseModel<ConfigOS>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -23763987677493413L;
	
	public static ConfigOS me = new ConfigOS();
	
	/*public boolean setInterval(Integer interval){
		//已存在
		ConfigOS configOS = findById(new Integer(1));
		boolean flag = false;
		if(configOS != null){
			flag = configOS.set("cinterval", interval).update();
		}else{
			ConfigOS temp = new ConfigOS();
			flag = temp.set("cinterval", interval)
					.set("cid", new Integer(1)).save();
		}
		return flag;
	}
	
	
	public Integer getInterval(){
		ConfigOS configOS = findById(new Integer(1));
		if(configOS != null){
			return configOS.getInt("cinterval");
		}
		return null;
	}
	
	public Integer getId(){
		ConfigOS configOS = findById(new Integer(1));
		if(configOS != null){
			return new Integer(1);
		}
		return null;
	}
	
	public Integer[] getScores(){
		Integer[] a = new Integer[6];
		ConfigOS configOS = findById(new Integer(1));
		if(configOS != null){
			a[0] = configOS.getInt("cjudge_num");
			a[1] = configOS.getInt("cjudge_score");
			a[2] = configOS.getInt("")
			return new Integer(1);
		}
		return null;
	}*/
}
