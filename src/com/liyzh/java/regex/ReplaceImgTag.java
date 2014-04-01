package com.liyzh.java.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liyzh.file.ReadFromFile;

public class ReplaceImgTag {
	public static void main(String[] args) {
		String fileName = "c:/regex.txt";
		Map cidMap = new HashMap<String, String>();
		List fckImages = new ArrayList<String>();
		String str = ReadFromFile.readFile(fileName).toString();
		String newStr = str;
		/*
		 * (?s)//����ƥ�俪�أ���ӦPattern���е��ֶ�DOTALL;
		 * 
		 * .*?//��ʾ��̰��ƥ�䣬�ҵ����ľ�ֹͣ
		 */
		String group5 = "([0-9a-zA-Z_]*\\.[0-9a-zA-Z]*)";// group5����ƥ��group4���ļ���
		String group4 = "(common/FCKeditor.*?/" + group5 + ")";// group4
		String group7 = "([0-9a-zA-Z]*)";// group7����ƥ��group6�и���id
		String group6 = "(cms/simpleDownload\\?fileId=" + group7 + ")";// group6

		String group3 = "(" + group4 + "|" + group6 + ")";// src���Ե�ֵȥ��url�����ģ��� ��ȥ�����ĵķ�����web�����·����group3
		String group2 = "(.*?" + group3 + ")";// src���Ե�ֵ��(����url�����ģ���Ӧ�ø�·����eg:fms),group2
		String group1 = "(src=\"" + group2 + ")";// ���src����,group1
		String group0 = "(?s)<img.*?" + group1;// ���img��ǩ,group0
		Pattern pattern = Pattern.compile(group0);
		Matcher matcher = pattern.matcher(newStr);
		int count = 0;
		while (matcher.find()) {
			String img = matcher.group(0);
			String src = matcher.group(1);
			String url = matcher.group(2);
			String localUrl = matcher.group(3);// ��ȥ�����ĵķ�����web�����·��
			String imgName = matcher.group(5);
			String fileId = matcher.group(7);// ([0-9a-zA-Z]*)
			if (fileId != null) {
				cidMap.put(fileId, "true");
				String newUrl = "cid:" + fileId;
				img = img.replace(url, newUrl);
			} else {
				String cid = UUID.randomUUID().toString();
				String newUrl = "cid:" + cid;
				// img = img.replace(url, newUrl);
				img = "<img src=\"" + newUrl + "\" />";
				fckImages.add(localUrl);
			}
			System.out.println("\t\t\t\tMatch count is : " + count++);
			System.out.println("group0--img tag is : ");
			System.out.println(img);
			System.out.println("group1--src attr is : ");
			System.out.println("\t" + src);
			System.out.println("group2--url is : ");
			System.out.println("\t" + url);
			System.out.println("group346--fckUrl or attUrl is : ");
			System.out.println("\t" + localUrl);
			System.out.println("group5--imgName is : ");
			System.out.println("\t" + imgName);
			System.out.println("group7--fileId is : ");
			System.out.println("\t" + fileId);
			System.out.println();
		}
		cidMap.put("fckImages", fckImages);
		System.out.println(cidMap);
		System.out.println(fckImages);
	}
}