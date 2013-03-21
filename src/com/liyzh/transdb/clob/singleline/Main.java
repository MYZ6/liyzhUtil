package com.liyzh.transdb.clob.singleline;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 将大字段数据中的一条记录从一个数据库复制到另外一个数据库，db2move可以将一个表的数据进行复制转移，但是不知道能否处理单条数据，暂且使用JDBC来得简单
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
		String url153 = "jdbc:db2://10.157.33.153:50000/FMS";
		String url48 = "jdbc:db2://10.157.33.48:50000/FMS";
		String url = "jdbc:db2://localhost:50000/FMS";
		String user = "FMS";
		String password = "Fms123";
		String password48 = "FMS123";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			// String sqlStr = "select * from t_lpb_protargets";
			// String sqlStr =
			// "select DEFPROCESSID,version,name,data from WFMS_DEF_PROCESS where version = '0.2' and name = '生产前资料审批'";
			String sqlStr = "select DEFPROCESSID,version,name,data from WFMS_DEF_PROCESS where version = '2.2.3.17' and name = '生产阶段审批'";
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

				System.out.print(rs.getBlob("data") + "     ");
				// System.out.print(rs.getString("Ssex") + "     ");
				System.out.println(rs.getString("version"));
			}
			rs.close();
			st.close();
			conn.close();

			// String insertSql =
			// "insert into WFMS_DEF_PROCESS (data,DEFPROCESSID, TEMPLATEID, PUBSTATUS, VALIDFROM, VALIDTO, LOCKED, AUTHOR, CREATED, DESCRIPTION, NAME, VERSION, DEMOFLAG) values (?,'defp1af90b088163', '402881e63b2c5e8b013b2c6c39c00002', 1, '2012-11-23 00:00:00', null, 1, 'admin', null, '', ?, '0.2', '0')";

			String insertSql = "insert into WFMS_DEF_PROCESS (data,DEFPROCESSID, TEMPLATEID, PUBSTATUS, VALIDFROM, VALIDTO, LOCKED, AUTHOR, CREATED, DESCRIPTION, NAME, VERSION, DEMOFLAG) values (?,'defp577e51248b44', '402881ea37e38d740137e39bfe1f0004', 1, '2012-06-19 00:00:00', null, 1, 'admin', null, '', ?, '2.2.3.17', '0')";

			// Connection conn153 = DriverManager.getConnection(url153, user,
			// password);
			// PreparedStatement stat = conn153.prepareStatement(insertSql);
			// // parameterIndex the first parameter is 1, the second is 2, ...
			// stat.setBlob(1, fileData);
			// stat.setString(2, "生产前资料审批");
			// int result = stat.executeUpdate();
			// if (result == 1) {
			// System.out.println("向153数据库复制大字段数据成功！");
			// }
			// stat.close();
			// conn153.close();

			Connection conn48 = DriverManager.getConnection(url48, user,
					password48);
			PreparedStatement stat48 = conn48.prepareStatement(insertSql);
			// parameterIndex the first parameter is 1, the second is 2, ...
			stat48.setBlob(1, fileData);
			stat48.setString(2, "生产前资料审批");
			int result48 = stat48.executeUpdate();
			if (result48 == 1) {
				System.out.println("向48数据库复制大字段数据成功！");
			}
			stat48.close();
			conn48.close();
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
