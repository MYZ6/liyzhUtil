package com.liyzh.file;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCFileCopy {
	public static void main(String[] args) {
		// 取得系统剪贴板里可传输的数据构造的Java对象
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);
		try {
			if (t != null
					&& t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// 因为原系的剪贴板里有多种信息, 如文字, 图片, 文件等
				// 先判断开始取得的可传输的数据是不是文字, 如果是, 取得这些文字

				List<File> lstFile = (List) t
						.getTransferData(DataFlavor.javaFileListFlavor);
				// 同样, 因为Transferable中的DataFlavor是多种类型的,
				// 所以传入DataFlavor这个参数, 指定要取得哪种类型的Data.
				System.out.println(lstFile);
				long bytesum = 0;
				if (lstFile != null && !lstFile.isEmpty()) {

					SimpleDateFormat sdf = new SimpleDateFormat("MMdd_HH");
					String hour = sdf.format(new Date());
					String newPath = "c:\\mycopy\\" + hour;
					if (args.length > 0) {
						newPath += "\\" + args[0];
					}
					(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹

					for (File oldfile : lstFile) {
						try {
							if (oldfile.exists()) { // 文件存在时
								String oldParent = oldfile.getParent();
								String fileName = oldfile.getName();
								// 获取相对路径
								int start = oldParent.indexOf("hyfmp");
								if (start < 0) {
									start = oldParent.indexOf("eway_simple");
									/* 从eway_simple复制到hyfmp */
									copy2fmp(oldParent, start, newPath,
											fileName);
								}
								// 生成复制到服务器的文件结构
								bytesum += copy2server(oldParent, start,
										newPath, fileName);
								System.out.println(bytesum);
							}
						} catch (Exception e) {
							System.out.println("复制单个文件操作出错");
							e.printStackTrace();
						}
					}
				}
			}
		} catch (UnsupportedFlavorException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static long copy2fmp(String oldParent, int start, String newPath,
			String fileName) throws IOException {
		newPath += "\\fmp";
		long bytesum = 0;
		int byteread = 0;
		String relativePath = oldParent.substring(start);
		// 统一两个工程文件结构
		relativePath = relativePath.replaceAll("WebContent", "web");
		System.out.println(relativePath);

		String newParent = newPath + File.separator + relativePath;
		(new File(newParent)).mkdirs(); // 如果文件夹不存在
										// 则建立新文件夹
		FileInputStream inStream = new FileInputStream(new File(oldParent,
				fileName));
		// 读入原文件
		FileOutputStream fs = new FileOutputStream(
				new File(newParent, fileName));
		byte[] buffer = new byte[5120];
		while ((byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; // 字节数 文件大小
			fs.write(buffer, 0, byteread);
		}
		fs.close();
		inStream.close();
		return bytesum;
	}

	public static long copy2server(String oldParent, int start, String newPath,
			String fileName) throws IOException {
		newPath += "\\server";
		long bytesum = 0;
		int byteread = 0;
		// 只复制class文件到服务器
		// 如果是java文件，则复制其相应的class文件

		ArrayList<String> innerCls = new ArrayList<String>();
		if (fileName.endsWith(".java")) {
			fileName = fileName.replace(".java", ".class");
			String fmpParent = oldParent.replaceAll("src",
					"web\\\\WEB-INF\\\\classes");
			if (new File(fmpParent).exists()) {// 如果工程是hyfmp
				oldParent = fmpParent;
			} else {// 如果工程是eway_simple
				oldParent = oldParent.replaceAll("src", "build\\\\classes");
			}
			// 搜寻内部类和匿名类的编译文件
			File clsDir = new File(oldParent);
			String[] files = clsDir.list();
			Pattern regex = Pattern.compile(fileName.substring(0,
					fileName.length() - 6)
					+ "\\$.*\\.class");// 定义正则表达式
			for (String f : files) {
				Matcher matcher = regex.matcher(f);// 定义string1的匹配器
				if (matcher.find()) {
					innerCls.add(f);
				}
				// System.out.println(f);
			}
			System.out.println(innerCls);
		}
		String relativePath = oldParent.substring(start);
		relativePath = relativePath.replaceAll("config|src|build\\\\classes",
				"web\\\\WEB-INF\\\\classes");// xwork,spring｜mybatis
												// mapper|class

		// 统一两个工程文件结构
		relativePath = relativePath.replaceAll("WebContent", "web");// js,jsp
		System.out.println(relativePath);

		String newParent = newPath + File.separator + relativePath;
		(new File(newParent)).mkdirs(); // 如果文件夹不存在
										// 则建立新文件夹
		FileInputStream inStream = new FileInputStream(new File(oldParent,
				fileName));

		FileOutputStream fs = new FileOutputStream(
				new File(newParent, fileName));
		byte[] buffer = new byte[5120];
		while ((byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; // 字节数 文件大小
			fs.write(buffer, 0, byteread);
		}
		fs.close();
		inStream.close();

		// 复制粘贴内部类和匿名类的编译文件
		for (String cls : innerCls) {
			FileInputStream fis = new FileInputStream(new File(oldParent, cls));
			FileOutputStream fos = new FileOutputStream(
					new File(newParent, cls));
			while ((byteread = fis.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				fos.write(buffer, 0, byteread);
			}
			fos.close();
			fis.close();
		}

		return bytesum;
	}
}