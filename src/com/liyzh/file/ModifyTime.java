package com.liyzh.file;

import java.io.File;
import java.util.Calendar;

public class ModifyTime {
	public static void main(String[] args) {
		ModifyTime.modifyDay(args);
		ModifyTime.modifyWeek(args);
	}

	public static void modifyDay(String[] args) {
		String dir = "d:\\test";
		if (args.length > 0) {
			dir += "\\" + args[0];
		}
		File fdir = new File(dir);
		if (fdir.exists() && fdir.isDirectory()) {
			Calendar cal = Calendar.getInstance();
			File[] files = fdir.listFiles();
			for (File f : files) {
				String fName = f.getName();
				if (fName.indexOf("new") >= 0) {
					f.setLastModified(cal.getTimeInMillis());
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
			}
		}
	}

	public static void modifyWeek(String[] args) {
		String dir = "d:\\DB_BAK\\week\\new - 副本 (2).txt";
		if (args.length > 0) {
			dir += "\\" + args[0];
		}
		File fdir = new File(dir);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -48);
		fdir.setLastModified(cal.getTimeInMillis());
	}

}
