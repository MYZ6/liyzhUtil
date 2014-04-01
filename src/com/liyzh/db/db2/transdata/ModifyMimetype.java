package com.liyzh.db.db2.transdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 由于以前的mimetype文件数据不完整，
 * 导致附件相应的mimetype有一部分不正确，像xls、JPG变成了application/octet-stream，
 * 这一部分数据需要手动改过来
 * 
 * @author liyzh
 * 
 */
public class ModifyMimetype {
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
			Connection conn = DriverManager.getConnection(url48, user,
					password48);
			// Connection conn = DriverManager.getConnection(url153, user,
			// password);
			modifyMap(conn);
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void modifyFile(Connection conn) throws SQLException {
		String sqlStr = "SELECT f.id, f.FILE_NAME, f.mime_type "
				+ "FROM FMS.T_SYS_FILE f where f.mime_type = 'application/octet-stream'";
		Statement st = conn.createStatement();
		System.out.println("创建Statement成功!");

		ResultSet rs = st.executeQuery(sqlStr);
		System.out.println("操作数据表成功!");
		System.out.println("----------------!");
		while (rs.next()) {
			System.out.print(rs.getString("id") + "     ");
			System.out.print(rs.getString("FILE_NAME") + "     ");
			System.out.println(rs.getString("mime_type"));
			String id = rs.getString("id");
			String fileName = rs.getString("FILE_NAME");
			int i = fileName.lastIndexOf('.');
			if (i != -1) {
				String ext = fileName.substring(i + 1).toLowerCase();
				System.out.println(ext);
				String mimeType = "";
				if ("xls".equals(ext)) {
					mimeType = "application/xls";
				}
				if ("docx".equals(ext)) {
					mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
				}
				String updateSql = "update t_sys_file f set f.mime_type = ?  where f.id = ?";

				// parameterIndex the first parameter is 1, the second is
				// 2,...
				PreparedStatement stat = conn.prepareStatement(updateSql);
				stat.setString(1, mimeType);
				stat.setString(2, id);
				int result = stat.executeUpdate();
				if (result == 1) {
					System.out.println("修改mimetype成功！");
				}
			}
		}
		rs.close();
		st.close();
	}

	public static void modifyMap(Connection conn) throws SQLException {
		String sqlStr = "SELECT f.id, f.FILE_NAME, f.mime_type "
				+ "FROM FMS.T_SYS_MAP f where f.mime_type = 'application/octet-stream'";
		Statement st = conn.createStatement();
		System.out.println("创建Statement成功!");

		ResultSet rs = st.executeQuery(sqlStr);
		System.out.println("操作数据表成功!");
		System.out.println("----------------!");
		while (rs.next()) {
			System.out.print(rs.getString("id") + "     ");
			System.out.print(rs.getString("FILE_NAME") + "     ");
			System.out.println(rs.getString("mime_type"));
			String id = rs.getString("id");
			String fileName = rs.getString("FILE_NAME");
			int i = fileName.lastIndexOf('.');
			if (i != -1) {
				String ext = fileName.substring(i + 1);// .toLowerCase();
				System.out.println(ext);
				String mimeType = "";
				if ("JPG".equals(ext)) {
					mimeType = "image/jpeg";
				}
				String updateSql = "update T_SYS_MAP f set f.mime_type = ?  where f.id = ?";

				// parameterIndex the first parameter is 1, the second is
				// 2,...
				PreparedStatement stat = conn.prepareStatement(updateSql);
				stat.setString(1, mimeType);
				stat.setString(2, id);
				int result = stat.executeUpdate();
				if (result == 1) {
					System.out.println("修改mimetype成功！");
				}
			}
		}
		rs.close();
		st.close();
	}
}
