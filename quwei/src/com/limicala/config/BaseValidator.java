package com.limicala.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
/**
 * 校验器基类
 * @author wrj
 *
 */
public abstract class BaseValidator extends Validator {
	protected static final String mobilephonePattern = "^[1](3|5|7|8)[0-9]{9}$";
	protected static final String telephonePattern = "^(\\d{3,4}-?)?\\d{8}$";
	
	protected void validateMobilephone(String field, String errorKey, String errorMessage) {
		validateRegex(field, mobilephonePattern, false, errorKey, errorMessage);
	}
	
	protected void validateTelephone(String field, String errorKey, String errorMessage){
		validateRegex(field, telephonePattern, false, errorKey, errorMessage);
	}
	
	protected void validateRegexString(String value, String regExpression, boolean isCaseSensitive, String errorKey, String errorMessage) {
		if (value == null) {
        	addError(errorKey, errorMessage);
        	return ;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
        	addError(errorKey, errorMessage);
	}
	
	protected void validateStrNotRequired(Controller c,String field, int minLen, int maxLen, String errorKey, String errorMessage) {
		String value = c.getPara(field);
		if(value != null){
			if(value.length() < minLen || value.length() > maxLen){
				addError(errorKey, errorMessage);
			}
		}
	}
	
	protected void validateRequiredString(Controller c,String field, Integer length, String errorKey, String errorMessage){
		String value = c.getPara(field);
		if (value == null || "".equals(value)){
			addError(errorKey, errorMessage);
		}else{
			if(length != null && value.trim().length() != length){
				addError(errorKey, errorMessage);
			}
		}
	}
	
	@Override
	protected void handleError(Controller c) {
		c.setAttr("success", false);
		c.renderJson();
	}
}
