package com.liyzh.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.springframework.util.FileCopyUtils;

public class GetStreamLength {

	public static void main(String[] args) {
		InputStream is = null;
		try {
			URL url = new URL(
					"http://10.156.1.153/hbzyrpt/reportFiles/yydyxt/quality/showReport.jsp?raq=yydyxt/variety/variety2.raq");
			is = url.openStream();
			System.out
					.println(new Date()
							+ " an estimate of the number of bytes of report stream is : "
							+ is.available());

			// 写文件
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"gb2312"));

			File tempFile = new File("temp.html");

			FileWriter write = new FileWriter(tempFile, false);

			BufferedWriter bufferedWriter = new BufferedWriter(write);

			String line = null;
			while ((line = br.readLine()) != null) {
				bufferedWriter.write(line);
				bufferedWriter.newLine();

			}
			bufferedWriter.flush();
			write.close();
			bufferedWriter.close();
			File newFile = new File("c:\\report" + tempFile.length() + ".html");
			FileCopyUtils.copy(tempFile, newFile);
			System.out.println(new Date() + " the length of report file is : "
					+ tempFile.length());

			// 读成流
			// byte[] bytes = FileCopyUtils.copyToByteArray(is);
			// System.out.println(bytes.length);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}
}