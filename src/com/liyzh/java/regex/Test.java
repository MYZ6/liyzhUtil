package com.liyzh.java.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {

		Pattern regex = Pattern
				.compile("WrdMaterialConsumeAction\\$.*\\.class");// 定义正则表达式匹配单词
		ArrayList<String> innerCls = new ArrayList<String>();
		Matcher matcher = regex.matcher("WrdMaterialConsumeAction$1.class");// 定义string1的匹配器
		if (matcher.find()) {
			System.out.println("true or false:" + matcher.group());
		}
		System.out.println("false");
	}
}
