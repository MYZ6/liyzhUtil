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
import java.util.Date;
import java.util.List;

public class VCFileCopy {
	public static void main(String[] args) {
		// 取得系统剪贴板里可传输的数据构造的Java对象
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);
		try {
			if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				// 因为原系的剪贴板里有多种信息, 如文字, 图片, 文件等
				// 先判断开始取得的可传输的数据是不是文字, 如果是, 取得这些文字

				List<File> lstFile = (List) t
						.getTransferData(DataFlavor.javaFileListFlavor);
				// 同样, 因为Transferable中的DataFlavor是多种类型的,
				// 所以传入DataFlavor这个参数, 指定要取得哪种类型的Data.
				System.out.println(lstFile);
				long bytesum = 0;
				if (lstFile != null && !lstFile.isEmpty()) {

					SimpleDateFormat sdf = new SimpleDateFormat("MMdd_hh");
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
		if (fileName.endsWith(".java")) {
			fileName = fileName.replace(".java", ".class");
			oldParent = oldParent
					.replaceAll("src", "web\\\\WEB-INF\\\\classes");
		}
		String relativePath = oldParent.substring(start);
		relativePath = relativePath.replaceAll("config",
				"web\\\\WEB-INF\\\\classes");
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
}