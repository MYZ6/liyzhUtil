package com.liyzh.db2.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrimaryKey {
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
		String url = "jdbc:db2://localhost:50000/FMS";
		String user = "FMS";
		String password = "Fms123";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			DatabaseMetaData _metaData = null;
			try {
				_metaData = conn.getMetaData();
				ResultSet primaryKeyRs = _metaData.getPrimaryKeys(null, null,
						"T_LPB_LEAFBASEUNIT");
				while (primaryKeyRs.next()) {
					String columnName = primaryKeyRs.getString("COLUMN_NAME");
					System.out.println(columnName);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
