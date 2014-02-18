package com.liyzh.eway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Group {
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
		// String url182 = "jdbc:db2://10.157.220.182:50000/HYDW";
		String url = "jdbc:db2://10.157.220.180:50000/HYDWTEST";
		String user = "hydw";
		String password = "hydw";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			String sqlStr = "select * FROM HYDW.ORGMODEL_GROUP g";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			String usql = "update ORGMODEL_GROUP set path = ? where id = ?";
			PreparedStatement ps = conn.prepareStatement(usql);

			ResultSet rs = st.executeQuery(sqlStr);
			while (rs.next()) {
				String id = rs.getString("id");
				String path = rs.getString("path");
				System.out.print(id + "     ");
				System.out.print(path + "     ");

				String[] groupIds = path.split("/");
				String parent = groupIds[groupIds.length - 1];
				if (parent.equals(id)) {
					path = path.substring(0, path.length() - parent.length()
							- 1);
					System.out.print("\t" + path + "  " + id);
					ps.setString(1, path);
					ps.setString(2, id);
					int result = ps.executeUpdate();
					if (result == 1) {
						System.out.println("路径处理成功！");
					}
				} else {
					System.out.print("\t" + "ok------------------");
				}
				System.out.println();
			}
			rs.close();
			st.close();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
