package com.liyzh.test;

import java.util.Calendar;

public class Test {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		// cal.set(1930, 1, 2);
		int endMonth = cal.get(Calendar.MONTH) + 1;
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		System.out.println(cal.getTime());
		System.out.println("month of now is :" + endMonth);
	}
}
