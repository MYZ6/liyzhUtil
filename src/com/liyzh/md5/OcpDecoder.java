package com.liyzh.md5;

import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.bouncycastle.util.encoders.Base64;

public class OcpDecoder {
	public String decryptPassword(String encPass) {
		try {
			Key deskey = getDesKey();
			Cipher cipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, deskey);
			byte[] clearByte = cipher.doFinal(Base64.decode(encPass
					.getBytes("UTF-8")));
			return new String(clearByte, "UTF-8");
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
			Security.addProvider(new sun.security.provider.Sun());
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed("seed".getBytes("UTF-8"));
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			KeyGenerator kGen = KeyGenerator.getInstance("DESEDE", "SunJCE");
			kGen.init(168, sr);
			return kGen.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		OcpDecoder decoder = new OcpDecoder();
		String result = decoder.decryptPassword("E0r/eBP8WjM=");
		System.out.println(result);
	}
}
