/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Mar 23, 2015 by liyzh
 * Last edited on Mar 23, 2015 by liyzh
 * 
 * 说明： TODO
 */
package com.liyzh.encode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FindGbkJSP {
	private static List<File> fileLst = new ArrayList<File>();
	private static List<File> fileLst2 = new ArrayList<File>();

	public static void main(String[] args) {
		String path = "F:/Personal/ws_tanry/erp/jsp";
		File root = new File(path);
		int count = 0;
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				count += iterate(file);
			} else {
				count++;
				// System.out.println(file.getAbsolutePath());
			}
		}
		System.out.println(count);
		System.out.println(fileLst.size());
		System.out.println(fileLst2.size());
	}

	private static boolean excludeDir(String dirName) {
		String[] eArr = new String[] { "lib", "", "" };
		for (String edir : eArr) {
			if (edir.equals(dirName)) {
				return true;
			}
		}
		return false;
	}

	private static int iterate(File dir) {
		int count = 0;
		for (File file : dir.listFiles()) {
			if (file.isDirectory() && !excludeDir(file.getName())) {
				count += iterate(file);
			} else {
				String fileName = file.getName();
				int start = fileName.lastIndexOf(".");
				if (start <= 0) {
					// System.out.println(fileName);
				}
				if (start > 0) {
					String ext = fileName.substring(start + 1);
					if ("jsp".equals(ext)) {
						existGBK(file);
						count++;
					}
					// System.out.println(ext);
				}
				// System.out.println(file.getAbsolutePath());
			}
		}
		return count;
	}

	private static boolean existGBK(File file) {
		boolean exist = false;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (tempString.indexOf("charset=gbk") >= 0 || tempString.indexOf("charset=GBK") >= 0
						|| tempString.indexOf("charset=GB2312") >= 0 || tempString.indexOf("charset=gb2312") >= 0) {
					System.out.println(tempString);
					convert(file);
					fileLst2.add(file);
					exist = true;
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return exist;
	}

	private static void convert(File file) throws IOException {
		// convert encoding
		// String content = FileUtils.readFileToString(file, "GBK");
		// System.out.println(content);
		// FileUtils.writeStringToFile(file, content, "UTF-8");

		// replace
		Path path = Paths.get(file.getAbsolutePath());
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("GBK", "UTF-8");
		content = content.replaceAll("gbk", "UTF-8");
		content = content.replaceAll("GB2312", "UTF-8");
		content = content.replaceAll("gb2312", "UTF-8");
		Files.write(path, content.getBytes(charset));
	}
}
