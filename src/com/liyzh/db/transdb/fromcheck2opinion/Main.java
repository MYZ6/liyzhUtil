package com.liyzh.db.transdb.fromcheck2opinion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * 将以前的checklist意见转移到business_opinion中，因为两个表字段不同，所以要转换一下
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
			Connection conn = DriverManager.getConnection(url48, user,
					password48);
			String sqlStr = "select ID,PROTARGETS_ID,SESSION_ID,REMARK,CHECKLIST_STATE,CHECKLIST_TIME,CHECKLIST_USER,CHECKLIST_USER_ID from T_LPB_CHECKLIST";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			// Connection conn153 = DriverManager.getConnection(url153, user,
			// password);

			// Connection conn48 = DriverManager.getConnection(url48, user,
			// password48);
			// PreparedStatement stat48 = conn48.prepareStatement(insertSql);
			// // parameterIndex the first parameter is 1, the second is 2, ...
			// stat48.setBlob(1, fileData);
			// stat48.setString(2, "生产前资料审批");
			// int result48 = stat48.executeUpdate();
			// if (result48 == 1) {
			// System.out.println("向48数据库复制大字段数据成功！");
			// }
			// stat48.close();
			// conn48.close();

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("操作数据表成功!");
			System.out.println("----------------!");
			while (rs.next()) {
				System.out.print(rs.getString("ID") + "     ");
				System.out.print(rs.getString("PROTARGETS_ID") + "     ");
				System.out.print(rs.getString("SESSION_ID") + "     ");
				System.out.print(rs.getString("REMARK") + "     ");
				System.out.print(rs.getString("CHECKLIST_STATE") + "     ");
				System.out.print(rs.getString("CHECKLIST_TIME") + "     ");
				System.out.print(rs.getString("CHECKLIST_USER") + "     ");
				System.out.print(rs.getString("CHECKLIST_USER_ID") + "     \n");
				String session_id = rs.getString("SESSION_ID");
				String session = "育苗";
				if ("10".equals(session_id)) {
					session = "育苗";
				}
				if ("20".equals(session_id)) {
					session = "施肥";
				}
				if ("30".equals(session_id)) {
					session = "移栽";
				}
				if ("40".equals(session_id)) {
					session = "大田管理";
				}
				if ("50".equals(session_id)) {
					session = "成熟采收";
				}
				if ("60".equals(session_id)) {
					session = "烘烤";
				}
				String insertSql = "INSERT INTO BUSINESS_OPINION(id,infoid,replier,replytime,contenttext,"
						+ "workitemname,flag,repliername,fieldname) values(?,?,?,?,?,?,?,?,'checklist')";
				PreparedStatement stat = conn.prepareStatement(insertSql);
				// parameterIndex the first parameter is 1, the second is 2, ...

				stat.setString(1, rs.getString("ID"));
				stat.setString(2, rs.getString("PROTARGETS_ID"));
				stat.setString(3, rs.getString("CHECKLIST_USER_ID"));
				stat.setTimestamp(
						4,
						Timestamp.valueOf(rs.getString("CHECKLIST_TIME")
								+ " 00:00:00"));
				stat.setString(5, rs.getString("REMARK"));
				stat.setString(6, session);
				stat.setString(7, rs.getString("CHECKLIST_STATE"));
				stat.setString(8, rs.getString("CHECKLIST_USER"));
				int result = stat.executeUpdate();
				if (result == 1) {
					System.out.println("向48数据库复制意见数据，id: " + rs.getString("ID")
							+ "成功！");
				}
				stat.close();
				// break;
			}
			rs.close();
			st.close();
			conn.close();

			// conn153.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
