package com.limicala.util;

import org.apache.log4j.chainsaw.Main;

import com.limicala.constant.AppTableConstant;

public class AnswerUtil {
	
	public static String answerRange = "abcd";
	
	//判断答案里有包含除了ABCD的其他字母,q_type为题目类型
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
	
	//判断某个字符在字符串里
	public static boolean isExsit(String x, String y){
		int index = y.indexOf(x);
		if(index > -1){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(AnswerUtil.checkAnswer(2, "A"));
		System.out.println(AnswerUtil.checkAnswer(2, "是"));
		System.out.println(AnswerUtil.checkAnswer(3, "去A我"));
		System.out.println(AnswerUtil.checkAnswer(3, "ABC"));
		System.out.println(AnswerUtil.checkAnswer(3, "啊啊啊1"));
	}
}
