package com.liyzh.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DelOldFile {
	public static void main(String[] args) {
		String dir = "d:\\test";
		if (args.length > 0) {
			dir = args[0];
		}
		DelOldFile util = new DelOldFile(dir);
		util.delAndCopy();
		util.delWeekFile();
	}

	public DelOldFile(String dir) {
		this.dir = dir;
		logFile = new File(dir + File.separator + "log.txt");
	}

	public void delAndCopy() {
		File fdir = new File(dir);
		if (fdir.exists() && fdir.isDirectory()) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7); // 保留七个天备份，其余删除，每周转储一个周备份
			Timestamp dayTime = new Timestamp(cal.getTimeInMillis());

			File[] files = fdir.listFiles();
			for (File f : files) {
				String fName = f.getName();
				Timestamp editTime = new Timestamp(f.lastModified());
				cal.setTimeInMillis(f.lastModified());
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
					File cFile = new File(dir + File.separator + "week"
							+ File.separator + fName);
					if (!cFile.exists()) {
						try {
							copyFile(f, cFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (editTime.compareTo(dayTime) < 0) {
					String msg = "delete day copy : " + fName
							+ ",lastModifiedTime is : " + editTime;
					log(msg);
					f.delete();
				}
			}
		}
	}

	public void delWeekFile() {
		File fdir = new File(dir + File.separator + "week");
		if (fdir.exists() && fdir.isDirectory()) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -35);// 保留五个周备份，其余删除
			Timestamp weekTime = new Timestamp(cal.getTimeInMillis());

			File[] files = fdir.listFiles();
			for (File f : files) {
				String fName = f.getName();
				Timestamp editTime = new Timestamp(f.lastModified());
				cal.setTimeInMillis(f.lastModified());
				if (editTime.before(weekTime)) {
					String msg = "delete week copy : " + fName
							+ ",lastModifiedTime is : " + editTime;
					log(msg);
					f.delete();
				}
			}
		}
	}

	public int copyFile(File iFile, File oFile) throws IOException {
		InputStream inStream = new FileInputStream(iFile); // 读入原文件
		FileOutputStream fos = new FileOutputStream(oFile);
		byte[] buffer = new byte[1444];
		int byteread = 0;
		int bytesum = 0;
		while ((byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; // 字节数 文件大小
			fos.write(buffer, 0, byteread);
		}
		inStream.close();
		fos.close();
		return bytesum;
	}

	public void log(String msg) {
		try {
			FileWriter logger = new FileWriter(logFile, true);// 追加内容
			logger.write(new Date() + ": " + msg + "\r\n\r\n");
			logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File logFile;
	private String dir;
}
