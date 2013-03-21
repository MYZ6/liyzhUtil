package com.liyzh.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liyzh.file.ReadFromFile;

/**
 * 烟叶项目中用于 查询后将查询条件暂存起来，
 * 由于这个功能是后来补上的，所以要对以前的代码进行批量转换，以减轻负担和减少错误
 * @author liyzh
 *
 */
public class ConditionArrTransfer {
	public static void main(String[] args) {
		String fileName = "c:/regex.txt";
		String str = ReadFromFile.readFile(fileName).toString();
		String newStr = str;
		String g3 = "([a-zA-Z]*)";// id
		String g5 = "(combobox\\('getValue')";// 类型为combo
		String g6 = "(combobox\\('getValues')";// 类型为combom
		String g7 = "(val\\(\\))";// 类型为text
		String g4 = "(" + g5 + "|" + g6 + "|" + g7 + ")";
		String g2 = "jQuery\\(\"#" + g3 + ".*?" + g4;// value
		String group0 = "(.*?):(.*?" + g2 + ".*)";
		Pattern pattern = Pattern.compile(group0);
		Matcher matcher = pattern.matcher(newStr);
		int count = 0;
		String condition_arr = "var condition_arr = [";
		String data = "var data = {";
		String vars = "";
		while (matcher.find()) {
			String item = matcher.group(0).trim();
			String key = matcher.group(1).trim();
			String value = matcher.group(2).trim().replace(",", "");
			String id = matcher.group(3).trim();
			String combo = matcher.group(5);
			String combom = matcher.group(6);
			String text = matcher.group(7);

			// System.out.println("\n\tMatch count is : " + count++);
			// System.out.println("group0--item is : ");
			System.out.println(item);
			System.out.println(matcher.group(1));// key
			System.out.println(matcher.group(2).replace(",", ""));// value
			System.out.println(matcher.group(3));// id
			System.out.println(matcher.group(4));//
			// System.out.println(matcher.group(5));
			// System.out.println("group1--key is : ");
			// System.out.println("\t" + key);
			// System.out.println("group2--value is : ");
			// System.out.println("\t" + value);
			String type = "combo";
			if (combom != null) {
				type = "combom";
			} else if (text != null) {
				type = "text";
			}

			String var = "var _" + key + " = " + value + ";";
			vars += var + "\n";

			String obj = key + " : _" + key;
			data += obj + ",\n";

			String condition = "{id : '" + id + "' , value : _" + key
					+ ", type : '" + type + "'}";
			condition_arr += condition + ",\n";

			System.out.println();
		}
		data += "};";
		condition_arr += "];";

		System.out.println("\n " + vars);
		System.out.println("\n " + data);
		System.out.println("\n " + condition_arr);
	}
}