/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Aug 13, 2014 by liyzh
 * Last edited on Aug 13, 2014 by liyzh
 * 
 * 说明： 查找指定时间内星期几和几号的个位数重合的情况，一直觉得这种情况很有趣，
 * 我想把它们枚举出来，就可以知道下一次出现这种情况是什么时候，就像别人计算下一次月食是什么时候一样
 */
package com.liyzh.java.calendar;

import java.util.Calendar;

/**
 * @author liyzh
 *
 */
public class DayWeek {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		for (int i = 1; i <= 740; i++) {
			// MONDAY = 2, SUNDAY = 1
			int dayofWeek = cal.get(Calendar.DAY_OF_WEEK);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (dayofWeek - 1 == day % 10 && dayofWeek != 1) {
				System.out.println(cal.getTime());
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
