package com.liyzh.test;

import java.text.DecimalFormat;

public class Test3 {
	public static void main(String[] args) {
		String value = new DecimalFormat("0.000").format(123200.33053);
		System.out.println(Test3.subZeroAndDot("0.000"));

	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * @param s
	 * @return 
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
}
