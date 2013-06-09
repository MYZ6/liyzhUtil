package com.liyzh.transdb.clob.singleline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 将大字段数据中的一条记录从一个数据库复制到另外一个数据库，db2move可以将一个表的数据进行复制转移，但是不知道能否处理单条数据，暂且使用JDBC来得简单
 * @author liyzh
 *
 */
public class Version2XML {
	public static void main(String[] args) {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		String url153 = "jdbc:db2://10.157.33.153:50000/FMS";
		String url48 = "jdbc:db2://10.157.33.48:50000/FMS";
		String url = "jdbc:db2://localhost:50000/FMS";
		String user = "FMS";
		String password = "Fms123";
		String password48 = "FMS123";
		try {
			Connection conn = DriverManager.getConnection(url153, user,
					password);
			// String sqlStr = "select * from t_lpb_protargets";
			String sqlStr = "select DEFPROCESSID,version,name,data from WFMS_DEF_PROCESS "
					+ "where version = '0.3' and name = '生产前资料审批'";
			// String sqlStr = "select DEFPROCESSID,version,name,data "
			// +
			// "from WFMS_DEF_PROCESS where version = '0.9' and name = '生产阶段审批'";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("操作数据表成功!");
			System.out.println("----------------!");
			Blob fileData = null;
			while (rs.next()) {
				System.out.print(rs.getString("DEFPROCESSID") + "     ");
				System.out.print(rs.getString("name") + "     ");
				fileData = rs.getBlob("data");
				InputStream ins = fileData.getBinaryStream();
				// 输出到文件
				File file = new File("d://output.xml");
				OutputStream fout;
				try {
					fout = new FileOutputStream(file);
					// 下面将BLOB数据写入文件
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = ins.read(b)) != -1) {
						fout.write(b, 0, len);
					}
					// 依次关闭
					fout.close();
					ins.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.print(rs.getBlob("data") + "     ");
				// System.out.print(rs.getString("Ssex") + "     ");
				System.out.println(rs.getString("version"));
			}
			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// public InputStream getInputData() {
	// if (inputData != null)
	// return inputData;
	// if (fileData != null) {
	// try {
	// inputData = fileData.getBinaryStream();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// return inputData;
	// }
}
