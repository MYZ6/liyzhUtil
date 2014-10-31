/**
 * Copyright (c) 2014
 * Tanry Electronic Technology Co., Ltd.
 * ChangSha, China
 * 
 * All Rights Reserved.
 * 
 * First created on Sep 15, 2014 by liyzh
 * Last edited on Sep 15, 2014 by liyzh
 * 
 * 说明： 这个文件成了代码生成的原始代码，更加完善的版本已经迁移到了单独的工程中sqljgen，使用金山快盘进行SVN的数据同步
 */
package com.liyzh.codegen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.CaseFormat;
import com.liyzh.excel.ExcelOperate;
import com.liyzh.java.regex.Snake2Camel;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class XlsUtil {

	static String parent = "F:/Personal/ws_util/liyzhUtil/web/codegen/";
	static String tpath = parent + "template";
	static String mpath = parent + "model";
	static String currentModelPath;
	static Configuration cfg;
	static Map<String, Object> model;

	public static void main(String[] args) throws Exception {

		/* 一般在应用的整个生命周期中你仅需要执行一下代码一次 */
		/* 创建一个合适的configuration */
		cfg = new Configuration();
		// cfg.setDefaultEncoding("GBK");

		/* 创建一个数据模型Create a data model */
		model = new HashMap<String, Object>();

		File mdir = new File(mpath);
		if (mdir.isDirectory()) {
			String[] dirs = mdir.list();
			for (String dir : dirs) {
				if (dir.indexOf("emplateMeta") >= 0) { // auditH,TransferH
					processModel(mpath + "/" + dir);
				}
			}
		}

	}

	static void setTemplatePath(File dir) throws IOException {
		// 设置模板加载的方式
		// cfg.setDirectoryForTemplateLoading(new File(tpath));

		FileTemplateLoader[] templateLoaders = new FileTemplateLoader[2];
		File tdir = new File(tpath);
		templateLoaders[0] = new FileTemplateLoader(tdir);// 加入父级窗口
		templateLoaders[1] = new FileTemplateLoader(dir);
		MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(templateLoaders);

		cfg.setTemplateLoader(multiTemplateLoader);
	}

	static List<File> getSubdirs(File file) {
		List<File> subdirs = Arrays.asList(file.listFiles(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory();
			}
		}));
		subdirs = new ArrayList<File>(subdirs);

		List<File> deepSubdirs = new ArrayList<File>();
		for (File subdir : subdirs) {
			deepSubdirs.addAll(getSubdirs(subdir));
		}
		subdirs.addAll(deepSubdirs);
		return subdirs;
	}

	public static void processModel(String path) throws FileNotFoundException, IOException, TemplateException {
		currentModelPath = path;
		File file = new File(path, "col.xls");
		List<Map<String, String>> colLst = processCol(file);
		model.put("colLst", colLst);

		model = JsonConfig.fillModel(path + "/" + "config.json", model);

		String templateType = (String) model.get("templateType");
		setTemplatePath(new File(tpath, templateType));// 分类别时模板路径也要分开设置，getTemplate只能根据名字来查找，如果存在多个同名的就会搞不定

		/* 而以下代码你通常会在一个应用生命周期中执行多次 */
		/* 获取或创建一个模版 */
		File tdir = new File(tpath);
		if (tdir.isDirectory()) {
			String[] files = tdir.list();
			File[] fileHandlers = tdir.listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];
				if (fileHandlers[i].isDirectory()) {
					if (templateType.equals(fileName)) { // 根据模板类型选择模板文件
						File typedir = fileHandlers[i];
						String[] subFiles = typedir.list();
						for (String subFileName : subFiles) {
							processTemplate(subFileName);
						}
					}
				} else {
					processTemplate(fileName);
				}
			}
		}
	}

	/**
	 * @param file
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private static void processTemplate(String fileName) throws IOException, TemplateException {
		String outfileName = processForOutputFilepath(model, fileName, cfg);
		Template temp = cfg.getTemplate(fileName);

		/* 合并数据模型和模版 */
		String gdir = currentModelPath + "/generate";
		new File(gdir).mkdir();
		FileOutputStream fos = new FileOutputStream(new File(gdir, outfileName));

		Writer out = new OutputStreamWriter(fos, "GBK");
		temp.process(model, out);
		out.flush();
	}

	/** 处理文件路径的变量变成输出路径 */
	public static String processForOutputFilepath(Map<String, Object> filePathModel, String templateFile,
			Configuration conf) throws IOException {
		return FreemarkerHelper.processTemplateString(templateFile, filePathModel, conf);
	}

	public static List<Map<String, String>> processCol(File file) throws FileNotFoundException, IOException {
		String[][] result = ExcelOperate.getData(file, 0);

		int rowLength = result.length;
		List<Map<String, String>> colLst = new ArrayList<Map<String, String>>();
		for (int i = 1; i < rowLength; i++) {
			String snake = result[i][0].trim();
			String lcamel = Snake2Camel.snakeToLowerCamel(snake);
			String ucamel = Snake2Camel.snakeToUpperCamel(snake);
			Map<String, String> col = new HashMap<String, String>();
			col.put("snake", snake);
			col.put("lsnake", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, snake));
			col.put("lcamel", lcamel);
			col.put("ucamel", ucamel);
			col.put("type", result[i][1]);
			col.put("name", result[i][2]);
			colLst.add(col);
		}
		return colLst;
	}
}
