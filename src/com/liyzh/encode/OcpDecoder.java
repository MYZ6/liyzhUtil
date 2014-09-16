package com.liyzh.encode;

import java.security.Key;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class OcpDecoder {
	public static final String URL153 = "jdbc:db2://10.157.33.153:50000/FMS";
	public static final String USER153 = "FMS";
	public static final String PW153 = "Fms123";

	public static final String URL48 = "jdbc:db2://10.157.33.48:50000/FMS";
	public static final String PW48 = "FMS123";

	public static final String URL_LOCAL = "jdbc:db2://localhost:50000/FMS";

	public static final String URL_182 = "jdbc:db2://10.157.220.182:50000/HYDW";
	public static final String USER182 = "hydw";
	public static final String PW182 = "hydw";

	private String url;
	private String user;
	private String pw;

	public static void main(String[] args) {
		// OcpDecoder decoder = new OcpDecoder(URL153, USER153, PW153);
		OcpDecoder decoder = new OcpDecoder(URL_182, USER182, PW182);
		String result = decoder
				.decryptPassword(decoder.getPassword("12005141"));
		// String result = decoder.decryptPassword("mxCHaIqkAMZ2midJ/bojkQ==");
		System.out.println(result);
	}

	public OcpDecoder(String url, String user, String pw) {
		this.url = url;
		this.user = user;
		this.pw = pw;
	}

	private String getPassword(String loginname) {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {

			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		String pwd = "";
		try {
			Connection conn = DriverManager.getConnection(url, user, pw);
			String sqlStr = "select * from ORGMODEL_USER u where u.LOGINNAME = '"
					+ loginname + "'";
			Statement st = conn.createStatement();
			System.out.println("创建Statement成功!");

			ResultSet rs = st.executeQuery(sqlStr);
			System.out.println("操作数据表成功!");
			System.out.println("----------------!");
			while (rs.next()) {
				pwd = rs.getString("PASSWORD");
				System.out.print(rs.getString("LOGINNAME") + "     ");
				System.out.print(rs.getString("PASSWORD") + "     ");
				System.out.println(rs.getString("NAME"));
				break;
			}
			rs.close();
			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pwd;
	}

	public String decryptPassword(String encPass) {
		try {
			Key deskey = getDesKey();
			Cipher cipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			// byte[] clearByte = cipher.doFinal(Base64.decode(encPass
			// .getBytes("UTF-8")));
			// return new String(clearByte, "UTF-8");
			return null;
		} catch (java.security.NoSuchAlgorithmException e1) {
			throw new RuntimeException(e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			throw new RuntimeException(e2);
		} catch (java.lang.Exception e3) {
			throw new RuntimeException(e3);
		}
	}

	private static Key getDesKey() {
		try {
			// Security.addProvider(new sun.security.provider.Sun());
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed("seed".getBytes("UTF-8"));
			// Security.addProvider(new com.sun.crypto.provider.SunJCE());
			KeyGenerator kGen = KeyGenerator.getInstance("DESEDE", "SunJCE");
			kGen.init(168, sr);
			return kGen.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
