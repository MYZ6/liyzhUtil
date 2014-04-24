package com.liyzh.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogUtil {
	public static void main(String[] args) {
		String dir = "d:\\test";
		String msg = "test";
		if (args.length > 0) {
			dir = args[0];
			msg = args[1];
		}
		try {
			FileWriter logger = new FileWriter(
					dir + File.separator + "log.txt", true);// 追加内容
			logger.write(new Date() + ": " + msg + "\r\n\r\n");
			logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
