package com.liyzh.java.xml;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Parser {
	public static void main(String[] args) {

		// 从配置文件中获取应该给多长时间未访问系统的被告监控人员发送短信提醒
		SAXReader saxReader = new SAXReader();
		String configFilePath = "test.xml";
		Document doc = null;
		try {
			File file = new File(configFilePath);
			if (file.exists()) {
				doc = saxReader.read(file);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if (doc != null) {
			String exp = "//daysdiff";
			List nodes = doc.selectNodes(exp);
			if (nodes != null && !nodes.isEmpty()) {
				Element ele = (Element) nodes.get(0);
			}
		}
	}
}
