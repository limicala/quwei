package com.limicala.util;

import com.limicala.constant.AppTableConstant;

public class AnswerUtil {
	
	public static String answerRange = "abcd";
	
	/**
	 * 判断答案里有包含除了ABCD的其他字母,q_type为题目类型
	 * @param q_type
	 * @param answer
	 * @return
	 */
	public static boolean checkAnswer(Integer q_type, String answer){
		answer = answer.toLowerCase();
		if(q_type == AppTableConstant.QUESTION_SINGLE){
			if(answer == null || answer.length() > 1) return false;
			if(isExsit(answer, answerRange)) return true;
		}else if(q_type == AppTableConstant.QUESTION_MUTIL){
			if(answer == null || answer.length() > 4) return false;
			if(isExsit(answer, answerRange)) return true;
		}
		return false;
	}
	
	/**
	 * 判断某个字符在字符串里
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isExsit(String x, String y){
		int index = y.indexOf(x);
		if(index > -1){
			return true;
		}
		return false;
	}
}
