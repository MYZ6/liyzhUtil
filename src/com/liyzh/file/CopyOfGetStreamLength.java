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

public class CopyOfGetStreamLength {

	public static void main(String[] args) {
		try {
			URL url = new URL(
					"http://10.156.1.153/hbzyrpt/reportFiles/yydyxt/quality/showReport.jsp?raq=yydyxt/variety/variety2.raq");
			InputStream is = url.openStream();
			System.out.println(new Date() + "the size of report file is : "
					+ is.available());
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"gb2312"));

			File newFile = new File("c:\\report" + is.available() + ".html");
			FileWriter write = new FileWriter(newFile, false);
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			String line = null;
			while ((line = br.readLine()) != null) {
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			write.close();
			bufferedWriter.close();

			br.close();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}