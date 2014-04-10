package com.liyzh.java.unicode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) {
		String py = JString.getPinyin("张三");
		System.out.println(py);

		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		// String url = "jdbc:db2://10.157.220.182:50000/HYDW";
		String url = "jdbc:db2://10.157.220.180:50000/HYDWTEST";
		String user = "hydw";
		String password = "hydw";
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, password);
			Test test = new Test();
			test.execute(conn);
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void execute(Connection conn) {
		try {
			String sqlStr = "SELECT * FROM hydw.user_test p ";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("查询中间表成功!");
			System.out.println("----------------!");
			int count = 0;
			while (rs.next()) {
				count++;
				String name = rs.getString("NAME");
				String proCode = rs.getString("DEPT");
				String tid = rs.getString("TID");
				System.out.println("第" + count + "条数据，" + name + "," + proCode);
				String py = JString.getPinyin(name);
				System.out.println(py);
				System.out.println();

				update(conn, py, tid);
				// analyse(proMonth, proCode, conn);
			}
			rs.close();
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Connection conn, String py, String tid)
			throws SQLException {
		String sql = "UPDATE HYDW.user_test " + "SET py = ? WHERE tid = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, py);
		stat.setString(2, tid);

		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("更新基础表数据成功！");
		}
		stat.close();
	}

}
