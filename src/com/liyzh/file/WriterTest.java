/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Jun 12, 2015 by liyzh
 * Last edited on Jun 12, 2015 by liyzh
 * 
 * 说明： TODO
 */
package com.liyzh.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriterTest {
	public static void main(String[] args) throws IOException {
		File file = new File("d:/dat_sup_item_cf.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		File file2 = new File("d:/dat_sup_item_cf2.csv");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
		String tempString = null;
		while ((tempString = reader.readLine()) != null) {
			String[] arr = tempString.split(",");
			if (arr[0].equals(arr[1])) {
				continue;
			}
			writer.write(tempString);
			writer.write("\n");
		}
		reader.close();
		writer.close();
	}
}
