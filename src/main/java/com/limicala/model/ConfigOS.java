package com.limicala.model;

import com.limicala.config.BaseModel;
import com.limicala.constant.AppTableConstant;

/**
 * 配置表的Model类
 */
public class ConfigOS extends BaseModel<ConfigOS>{

	private static final long serialVersionUID = -23763987677493413L;
	
	public static ConfigOS me = new ConfigOS();

	private static String tableName = null;

	static {
		tableName = " " + AppTableConstant.CONFIG_OS + " ";
	}
	public boolean hasConfiged(){
		boolean flag = false;
		ConfigOS configOS = ConfigOS.me.findById(1);
		if (configOS != null) {
			flag = configOS.getInt("cdayinterval")!= null && configOS.getInt("canswertime") != null;
			flag = flag && configOS.getInt("csingle_num") != null && configOS.getInt("csingle_score") != null;
			flag = flag && configOS.getInt("cmulti_num") != null && configOS.getInt("cmulti_score")!= null;
			flag = flag && configOS.getInt("cjudge_num") != null && configOS.getInt("cjudge_score") != null;
		}
		//System.out.println("ssjsj"+flag);
		return flag;
	}
}
