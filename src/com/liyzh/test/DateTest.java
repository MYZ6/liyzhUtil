package com.liyzh.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		Date endDate = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endStr = sdf.format(endDate);
		// 向前推60天
		cal.add(Calendar.DATE, -60);
		Date beginDate = cal.getTime();
		String beginStr = sdf.format(beginDate);
	}
}
