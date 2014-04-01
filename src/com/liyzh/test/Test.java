package com.liyzh.test;

public class Test {
	public static void main(String[] args) {
		String duties = "被叫-59秒,被叫-51秒,被叫-56秒,主叫-1分4秒,主叫-47秒,主叫-1分20秒,主叫-18秒,被叫-21秒,被叫-1分3秒,主叫-26秒,被叫-53秒,主叫-17秒,被叫-26秒,主叫-34秒,主叫-21秒,主叫-1分23秒,-,被叫-12分45秒,主叫-10分39秒,主叫-1分42秒,主叫-1分22秒,主叫-41秒,被叫-22秒,被叫-1分13秒,主叫-1分31秒,主叫-2分12秒,主叫-1分46秒,被叫-17秒,被叫-1分13秒,被叫-5秒,主叫-1秒,主叫-1分20秒,主叫-2分29秒,-,主叫-6分0秒,主叫-5分51秒,主叫-20秒,主叫-1分12秒,主叫-9秒,被叫-2分22秒,被叫-16秒,被叫-25秒,被叫-1分30秒,被叫-34秒,主叫-24秒,主叫-17秒,主叫-1分41秒,被叫-3分38秒,主叫-35秒,-,主叫-4分6秒,-,被叫-6分28秒,被叫-6分20秒,-,被叫-23秒,主叫-25秒,-,被叫-21分11秒,-,主叫-7分39秒";
		String[] arr = duties.split(",");
		int total = 0;
		for (String ele : arr) {
			if (!"-".equals(ele) && ele.indexOf("主叫") >= 0) {
				int iMinute = ele.indexOf("分");
				int minute = 1;
				if (iMinute > 0) {
					String sM = ele.substring(3, iMinute);
					minute = Integer.parseInt(sM);
					if (ele.indexOf("秒") > 0) {
						minute += 1;
					}
				}
				System.out.println("minute is : " + minute);
				System.out.println("string  is : " + ele);
				total += minute;
			}
		}
		System.out.println("total is : " + total);
	}
}
