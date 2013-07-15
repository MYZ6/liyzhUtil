package com.liyzh.db2.parsesql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyzh
 * 
 *         把java代码中的sql（字符串拼接而成）尽可能还原成可直接运行的sql
 */
public class Test {
	public static void main(String[] args) {
		File file = new File("c:\\data.sql");
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "gbk");
			reader = new BufferedReader(isr);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				Pattern pattern = Pattern.compile("values\\s*\\((.*\\))");
				Matcher matcher = pattern.matcher(tempString);
				int count = 0;
				while (matcher.find()) {
					String img = matcher.group(0);
					String g2 = matcher.group(1);
					// System.out.println(g2);
					String[] arr = g2.split(",");
					// System.out.println(arr[7]);
					if (!"null".equals(arr[7].trim())) {
						arr[7] = "'" + arr[7].trim() + "'";
					}
					StringBuilder sb2 = new StringBuilder(arr[0]);
					for (int i = 1; i < arr.length; i++) {
						sb2.append("," + arr[i]);
					}
					// System.out.println(sb2);
					tempString = tempString.replace(g2, sb2);
					System.out.println(tempString);
				}
				// System.out.println("\n");
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				line++;
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
	}
}
