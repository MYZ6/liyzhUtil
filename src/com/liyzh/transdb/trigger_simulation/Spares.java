package com.liyzh.transdb.trigger_simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 将中间表T_DC2_SPARES中的数据遍历，
 * 根据同步类型exchange_type决定是插入、更新还是删除，
 * 然后对基础表T_BAS_SPARES做相应操作
 * 
 * @author liyzh
 * 
 */
public class Spares {
	public void execute(Connection conn) {
		try {
			String sqlStr = "select * " + "from T_DC2_SPARES";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("查询中间表成功!");
			System.out.println("----------------!");
			int count = 0;
			while (rs.next()) {
				count++;
				String exchangeType = rs.getString("EXCHANGE_TYPE");
				System.out.println("第" + count + "条数据，" + exchangeType);
				if ("INSERT".equals(exchangeType)) {
					insert(conn, rs);
				} else if ("UPDATE".equals(exchangeType)) {
					update(conn, rs);
				} else if ("DELETE".equals(exchangeType)) {
					delete(conn, rs);
				}
				backup(conn, rs);
				System.out.println();
			}
			rs.close();
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(Connection conn, ResultSet rs) throws SQLException {
		String insertSql = "INSERT INTO T_BAS_SPARES ( SPARES_ID, SPARES_CODE, SPARES_NAME, SPARES_NB_CODE, ERP_SPARES_OLDID, UPSYSID, SPARES_TYPE_ID, SPARES_SPEC, SPARES_MODEL, SHORT_NAME, SOURCETYPE, MEASUREUNIT_ID, MEASUREUNIT2_ID, MEASUREUNIT3_ID, IS_OUTSIDE, IS_SALE, TAX_TYPE_ID, USE_FLAG, REMARK, SPARES_GRAPHID, GWTH, JKGC, JXDQ ) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		PreparedStatement stat = conn.prepareStatement(insertSql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("SPARES_ID"));
		stat.setString(2, rs.getString("SPARES_CODE"));
		stat.setString(3, rs.getString("SPARES_NAME"));
		stat.setString(4, rs.getString("SPARES_NB_CODE"));
		stat.setString(5, rs.getString("ERP_SPARES_OLDID"));
		stat.setString(6, rs.getString("UPSYSID"));
		stat.setString(7, rs.getString("SPARES_TYPE_ID"));
		stat.setString(8, rs.getString("SPARES_SPEC"));
		stat.setString(9, rs.getString("SPARES_MODEL"));
		stat.setString(10, rs.getString("SHORT_NAME"));
		stat.setString(11, rs.getString("SOURCETYPE"));
		stat.setString(12, rs.getString("MEASUREUNIT_ID"));
		stat.setString(13, rs.getString("MEASUREUNIT2_ID"));
		stat.setString(14, rs.getString("MEASUREUNIT3_ID"));
		stat.setInt(15, rs.getInt("IS_OUTSIDE"));
		stat.setInt(16, rs.getInt("IS_SALE"));
		stat.setString(17, rs.getString("TAX_TYPE_ID"));
		stat.setInt(18, rs.getInt("USE_FLAG"));
		stat.setString(19, rs.getString("REMARK"));
		stat.setString(20, rs.getString("SPARES_GRAPHID"));
		stat.setString(21, rs.getString("GWTH"));
		stat.setString(22, rs.getString("JKGC"));
		stat.setString(23, rs.getString("JXDQ"));

		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("新增基础表数据成功！");
		}
		stat.close();
	}

	public void update(Connection conn, ResultSet rs) throws SQLException {
		String sql = "UPDATE HYDW.T_BAS_SPARES "
				+ "SET SPARES_ID = ?, SPARES_CODE = ?, SPARES_NAME = ?, SPARES_NB_CODE = ?, ERP_SPARES_OLDID = ?, UPSYSID = ?, SPARES_TYPE_ID = ?,"
				+ " SPARES_SPEC = ?, SPARES_MODEL = ?, SHORT_NAME = ?, SOURCETYPE = ?, MEASUREUNIT_ID = ?, MEASUREUNIT2_ID = ?, MEASUREUNIT3_ID = ?, IS_OUTSIDE = ?, IS_SALE = ?, TAX_TYPE_ID = ?, USE_FLAG = ?, REMARK = ?,  SPARES_GRAPHID = ?, GWTH = ?, JKGC = ?, JXDQ = ?"
				+ "WHERE SPARES_ID = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("SPARES_ID"));
		stat.setString(2, rs.getString("SPARES_CODE"));
		stat.setString(3, rs.getString("SPARES_NAME"));
		stat.setString(4, rs.getString("SPARES_NB_CODE"));
		stat.setString(5, rs.getString("ERP_SPARES_OLDID"));
		stat.setString(6, rs.getString("UPSYSID"));
		stat.setString(7, rs.getString("SPARES_TYPE_ID"));
		stat.setString(8, rs.getString("SPARES_SPEC"));
		stat.setString(9, rs.getString("SPARES_MODEL"));
		stat.setString(10, rs.getString("SHORT_NAME"));
		stat.setString(11, rs.getString("SOURCETYPE"));
		stat.setString(12, rs.getString("MEASUREUNIT_ID"));
		stat.setString(13, rs.getString("MEASUREUNIT2_ID"));
		stat.setString(14, rs.getString("MEASUREUNIT3_ID"));
		stat.setInt(15, rs.getInt("IS_OUTSIDE"));
		stat.setInt(16, rs.getInt("IS_SALE"));
		stat.setString(17, rs.getString("TAX_TYPE_ID"));
		stat.setInt(18, rs.getInt("USE_FLAG"));
		stat.setString(19, rs.getString("REMARK"));
		stat.setString(20, rs.getString("SPARES_GRAPHID"));
		stat.setString(21, rs.getString("GWTH"));
		stat.setString(22, rs.getString("JKGC"));
		stat.setString(23, rs.getString("JXDQ"));
		stat.setString(24, rs.getString("SPARES_ID"));

		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("更新基础表数据成功！");
		}
		stat.close();
	}

	public void delete(Connection conn, ResultSet rs) throws SQLException {
		String sql = "delete from T_BAS_SPARES where SPARES_ID = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("SPARES_ID"));
		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("删除基础表数据成功！");
		}
		stat.close();
	}

	public void backup(Connection conn, ResultSet rs) throws SQLException {
		String sql = "INSERT INTO T_DC2_SPARES_BAK SELECT * FROM T_DC2_SPARES WHERE SPARES_ID=?";
		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setString(1, rs.getString("SPARES_ID"));
		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("备份中间表数据成功！");
		}

		sql = "delete from T_DC2_SPARES where SPARES_ID = ?";
		stat = conn.prepareStatement(sql);
		stat.setString(1, rs.getString("SPARES_ID"));
		result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("删除中间表数据成功！");
		}
		stat.close();
	}
}
