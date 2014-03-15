package com.liyzh.transdb.trigger_simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * 生产计划的同步规则如下：
 * 如果一个月的一个牌号有下面的数据，第一个版本从1至27号(更确切地说，是上月的最新版本），第二个版本从7号到27号，第三个版本从12号至27号，
 * 则从第一个版本取1-7,从第二个版本取8至12，从第三个版本取13-27，即除第一个版本，后续版本从第二条记录开始取
 * 
 * @author liyzh
 * 
 */
public class ProductPlan {
	public void execute(Connection conn) {
		try {
			String sqlStr = "SELECT PRO_MON, PRO_COD "
					+ "FROM hydw.SC_MOMS_MV_MAINDAYPLAN_HA p "
					+ " where year(PRO_MON) > 2011 "
					// + "and  PRO_MON=DATE('2014-01-01') AND PRO_COD='02251' "
					+ " GROUP BY pro_mon, pro_cod order by pro_mon desc";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("查询中间表成功!");
			System.out.println("----------------!");
			int count = 0;
			while (rs.next()) {
				count++;
				Timestamp proMonth = rs.getTimestamp("PRO_MON");
				String proCode = rs.getString("PRO_COD");
				System.out.println("第" + count + "条数据，" + proMonth + ","
						+ proCode);
				System.out.println();

				analyse(proMonth, proCode, conn);
			}
			rs.close();
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void analyse(Timestamp proMonth, String proCode, Connection conn)
			throws SQLException {
		String sqlStr = "SELECT VER_NUM, P.COM_DAT FROM HYDW.SC_MOMS_MV_MAINDAYPLAN_HA P WHERE PRO_MON=? AND PRO_COD=? GROUP BY VER_NUM, P.COM_DAT "
				+ "ORDER BY P.VER_NUM DESC";// 按版本号倒序排列
		PreparedStatement st = conn.prepareStatement(sqlStr,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		st.setTimestamp(1, proMonth);
		st.setString(2, proCode);

		ResultSet rs = st.executeQuery();
		int count = 0;

		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(proMonth.getTime());
		System.out.println("resultset type is :" + rs.getType());
		// if (rs.next()) {
		// rs.first();
		rs.last();
		int length = rs.getRow();
		// System.out.println(" version numbers is : " + length);
		rs.beforeFirst();
		// }
		int lastPos = 31;// 从一个月的最后一天开始入前遍历
		while (rs.next()) {
			count++;
			Timestamp comDate = rs.getTimestamp("COM_DAT");
			double verNum = rs.getDouble("VER_NUM");
			System.out.println("	sub第" + count + "条数据，" + verNum + ","
					+ comDate);
			System.out.println();
			lastPos = getVersionData(lastPos, verNum, proMonth, comDate,
					proCode, conn);
			if (comDate.compareTo(proMonth) < 0) {// 如果是在上个月制定的版本，则不再继续往上推
				break;
			}
		}
		rs.close();
		if (st != null) {
			st.close();
		}
	}

	private int getVersionData(int lastPos, double verNum, Timestamp proMonth,
			Timestamp comDate, String proCode, Connection conn)
			throws SQLException {
		String sqlStr = "SELECT * FROM HYDW.SC_MOMS_MV_MAINDAYPLAN_HA P"
				+ " WHERE PRO_MON=? AND PRO_COD=? AND P.VER_NUM = ? "
				+ "ORDER BY P.PRO_DAT desc";// 按生产日期倒序排列，从最大的开始取起
		PreparedStatement st = conn.prepareStatement(sqlStr,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		st.setTimestamp(1, proMonth);
		st.setString(2, proCode);
		st.setDouble(3, verNum);

		ResultSet rs = st.executeQuery();
		System.out.println("----------------!");
		int count = 0;

		Calendar cal = Calendar.getInstance();

		// 如果是上月的版本，全部取完；如果是当月修改的版本，从大到小，取到修改日期为止
		int comDay = 1;// 编制日期
		if (comDate.compareTo(proMonth) >= 0) {
			cal.setTimeInMillis(comDate.getTime());
			comDay = cal.get(Calendar.DAY_OF_MONTH);
		}
		while (rs.next()) {
			count++;
			String proName = rs.getString("PRO_NAM");
			double proDate = rs.getDouble("PRO_DAT");// 计划生产日期
			if (proDate >= comDay) {
				if (proDate > lastPos) {// 把下一个版本的第一条记录作为最大记录
					continue;
				}
				System.out.println("		sub2第" + count + "条数据，" + proCode + ","
						+ proDate + "," + proName + "," + verNum + ","
						+ comDate);
				System.out.println();
			} else {
				lastPos = (int) proDate;
				break;
			}
			if (rs.isLast()) {
				lastPos = comDay - 1;// 前一个版本只取比当前编制日期小的数据
			}
		}
		rs.close();
		if (st != null) {
			st.close();
		}
		return lastPos;
	}

	public void insert(Connection conn, ResultSet rs) throws SQLException {
		String insertSql = "INSERT INTO T_BAS_MATERIAL ( MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_NB_CODE, ERP_MATERIAL_OLDID, UPSYSID, MATERIAL_TYPE_ID, MATERIAL_SPEC, MATERIAL_MODEL, SHORT_NAME, SOURCETYPE, MEASUREUNIT_ID, MEASUREUNIT2_ID, MEASUREUNIT3_ID, IS_OUTSIDE, IS_SALE, TAX_TYPE_ID, USE_FLAG, REMARK, MATERIAL_GRAPHID, GWTH, JKGC, JXDQ ) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		PreparedStatement stat = conn.prepareStatement(insertSql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("MATERIAL_ID"));
		stat.setString(2, rs.getString("MATERIAL_CODE"));
		stat.setString(3, rs.getString("MATERIAL_NAME"));
		stat.setString(4, rs.getString("MATERIAL_NB_CODE"));
		stat.setString(5, rs.getString("ERP_MATERIAL_OLDID"));
		stat.setString(6, rs.getString("UPSYSID"));
		stat.setString(7, rs.getString("MATERIAL_TYPE_ID"));
		stat.setString(8, rs.getString("MATERIAL_SPEC"));
		stat.setString(9, rs.getString("MATERIAL_MODEL"));
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
		stat.setString(20, rs.getString("MATERIAL_GRAPHID"));
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
		String sql = "UPDATE HYDW.T_BAS_MATERIAL "
				+ "SET MATERIAL_ID = ?, MATERIAL_CODE = ?, MATERIAL_NAME = ?, MATERIAL_NB_CODE = ?, ERP_MATERIAL_OLDID = ?, UPSYSID = ?, MATERIAL_TYPE_ID = ?,"
				+ " MATERIAL_SPEC = ?, MATERIAL_MODEL = ?, SHORT_NAME = ?, SOURCETYPE = ?, MEASUREUNIT_ID = ?, MEASUREUNIT2_ID = ?, MEASUREUNIT3_ID = ?, IS_OUTSIDE = ?, IS_SALE = ?, TAX_TYPE_ID = ?, USE_FLAG = ?, REMARK = ?,  MATERIAL_GRAPHID = ?, GWTH = ?, JKGC = ?, JXDQ = ?"
				+ "WHERE MATERIAL_ID = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("MATERIAL_ID"));
		stat.setString(2, rs.getString("MATERIAL_CODE"));
		stat.setString(3, rs.getString("MATERIAL_NAME"));
		stat.setString(4, rs.getString("MATERIAL_NB_CODE"));
		stat.setString(5, rs.getString("ERP_MATERIAL_OLDID"));
		stat.setString(6, rs.getString("UPSYSID"));
		stat.setString(7, rs.getString("MATERIAL_TYPE_ID"));
		stat.setString(8, rs.getString("MATERIAL_SPEC"));
		stat.setString(9, rs.getString("MATERIAL_MODEL"));
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
		stat.setString(20, rs.getString("MATERIAL_GRAPHID"));
		stat.setString(21, rs.getString("GWTH"));
		stat.setString(22, rs.getString("JKGC"));
		stat.setString(23, rs.getString("JXDQ"));
		stat.setString(24, rs.getString("MATERIAL_ID"));

		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("更新基础表数据成功！");
		}
		stat.close();
	}

	public void delete(Connection conn, ResultSet rs) throws SQLException {
		String sql = "delete from T_BAS_MATERIAL where MATERIAL_ID = ?";
		PreparedStatement stat = conn.prepareStatement(sql);
		// parameterIndex the first parameter is 1, the second is 2,
		// ...
		stat.setString(1, rs.getString("MATERIAL_ID"));
		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("删除基础表数据成功！");
		}
		stat.close();
	}

	public void backup(Connection conn, ResultSet rs) throws SQLException {
		String sql = "INSERT INTO T_DC2_MATERIAL_BAK SELECT * FROM T_DC2_MATERIAL WHERE MATERIAL_ID=?";
		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setString(1, rs.getString("MATERIAL_ID"));
		int result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("备份中间表数据成功！");
		}

		sql = "delete from T_DC2_MATERIAL where MATERIAL_ID = ?";
		stat = conn.prepareStatement(sql);
		stat.setString(1, rs.getString("MATERIAL_ID"));
		result = stat.executeUpdate();
		if (result == 1) {
			System.out.println("删除中间表数据成功！");
		}
		stat.close();
	}
}
