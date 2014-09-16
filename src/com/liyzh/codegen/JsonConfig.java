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
 * 说明： TODO
 */
package com.liyzh.codegen;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.google.common.base.CaseFormat;
import com.liyzh.file.ReadFromFile;

public class JsonConfig {
	public static Map<String, Object> fillModel(String filename,
			Map<String, Object> model) {
		StringBuilder sb = ReadFromFile.readFile(filename);
		JSONObject config = JSONObject.fromObject(sb.toString());
		model.put("date", new Date().toString());
		// model.put("author", config.get("author"));
		// model.put("comment", config.get("comment"));
		// model.put("package", config.get("package"));
		// model.put("className", config.get("className"));
		String abbr = config.getString("abbr");
		String uabbr = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, abbr);
		model.put("uabbr", uabbr);
		Set<Map.Entry<String, Object>> entries = config.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			model.put(entry.getKey(), entry.getValue());
		}
		return model;
	}
}
