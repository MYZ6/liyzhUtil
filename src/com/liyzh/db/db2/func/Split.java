package com.liyzh.db.db2.func;

public class Split {
	public static void split() {
		String test = "abc,def,xyz";
		String[] result = test.split(",");
		for (String a : result) {
			// set(1, a);
		}
	}

	public static void main(String[] args) {
		// String[] result = Split.split();
		// for (String a : result) {
		// System.out.println(a);
		// }
	}
}
