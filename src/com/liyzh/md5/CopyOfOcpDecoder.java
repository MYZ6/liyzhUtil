package com.liyzh.md5;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.util.encoders.Base64;

import sun.security.provider.Sun;

import com.sun.crypto.provider.SunJCE;

public class CopyOfOcpDecoder {
	public String decryptPassword(String paramString) {
		try {
			Key localKey = o00000();
			Cipher localCipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
			localCipher.init(2, localKey);
			byte[] arrayOfByte = localCipher.doFinal(Base64.decode(paramString
					.getBytes("UTF-8")));
			return new String(arrayOfByte, "UTF-8");
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new RuntimeException(localNoSuchAlgorithmException);
		} catch (NoSuchPaddingException localNoSuchPaddingException) {
			throw new RuntimeException(localNoSuchPaddingException);
		} catch (Exception localException) {
			throw new RuntimeException(localException);
		}
	}

	private static Key o00000() {
		try {
			Security.addProvider(new Sun());
			SecureRandom localSecureRandom = SecureRandom.getInstance(
					"SHA1PRNG", "SUN");
			localSecureRandom.setSeed("seed".getBytes("UTF-8"));
			Security.addProvider(new SunJCE());
			KeyGenerator localKeyGenerator = KeyGenerator.getInstance("DESEDE",
					"SunJCE");
			localKeyGenerator.init(168, localSecureRandom);
			return localKeyGenerator.generateKey();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		CopyOfOcpDecoder decoder = new CopyOfOcpDecoder();
		String result = decoder.decryptPassword("xX6v2YyrGbNOGIXZiXvQuQ==");
		System.out.println(result);
	}
}
