package com.chen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TestString {
	public TestString(){}
	public static boolean matchString(String str) throws PatternSyntaxException{
		// 只允许字母       
		String   regEx  =   "^[a-zA-Z]+$";                      
		Pattern   p   =   Pattern.compile(regEx);     
		Matcher   m   =   p.matcher(str);    
		return   m.matches();     
	}
	public static boolean matchNum(String s)throws PatternSyntaxException{
		// 只允许数字       
		 String   regEx  =  "^[0-9_]+$";                      
		Pattern   p   =   Pattern.compile(regEx);     
		Matcher   m   =   p.matcher(s);    
		return   m.matches();  
	}
	public static boolean match(String s)throws PatternSyntaxException{
		// 只允许汉字       
		String   regEx  ="^[\\u4e00-\\u9fa5]+$";                       
		Pattern   p   =   Pattern.compile(regEx);     
		Matcher   m   =   p.matcher(s);    
		return   m.matches();  
	}
}
