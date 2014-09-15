/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Aug 28, 2014 by liyzh
 * Last edited on Aug 28, 2014 by liyzh
 * 
 * 说明： TODO
 */
package com.liyzh.java.regex;

import com.google.common.base.CaseFormat;

public class Snake2Camel {
	public static String snakeToLowerCamel(String snakeStr) {
		String result = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
				snakeStr);
		return result;
	}

	public static String snakeToUpperCamel(String snakeStr) {
		String result = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
				snakeStr);
		return result;
	}

	public static void main(String[] args) {
		String[] arr = new String[] { "FORM_ID", "ITEM_ID", "ITEM_NAME",
				"ITEM_DIMENSION", "ITEM_SPECIFICATION", "ITEM_CATEGORY",
				"PACKAGING_FACTOR", "PACKAGING_UNIT", "REQUEST_COUNT",
				"SHIPPING_COUNT", "DELIVERY_COUNT", "RECEIVE_COUNT",
				"DIFFERENT_COUNT", "RETURN_COUNT", "ITEM_UNIT_PRICE",
				"PAY_AMT", "EXPIRED_TIME" };
		for (String col : arr) {
			String result = snakeToLowerCamel(col);
			System.out.println(result);
		}
		System.out.println();
		for (String col : arr) {
			String result = snakeToUpperCamel(col);
			System.out.println(result);
		}
	}
}
