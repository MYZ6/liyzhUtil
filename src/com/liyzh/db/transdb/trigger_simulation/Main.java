package com.liyzh.db.transdb.trigger_simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 将大字段数据中的一条记录从一个数据库复制到另外一个数据库，db2move可以将一个表的数据进行复制转移，但是不知道能否处理单条数据，
 * 暂且使用JDBC来得简单
 * 
 * @author liyzh
 * 
 */
public class Main {
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
		// String url = "jdbc:db2://10.157.220.182:50000/HYDW";
		String url = "jdbc:db2://10.157.220.180:50000/HYDWTEST";
		String user = "hydw";
		String password = "hydw";
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, user, password);
			ProductPlan plan = new ProductPlan();
			plan.execute(conn);
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
