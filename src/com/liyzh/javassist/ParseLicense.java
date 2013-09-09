package com.liyzh.javassist;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.zeroturnaround.licensing.UserLicense;

public class ParseLicense {
	@Test
	public void parseLicenseFile() {
		Object localObject1 = null;
		try {
			System.out.println("start to get lic file.");
			// File localFile2 = new File(
			// "C:\\TDDOWNLOAD\\jrebel-master\\jrebel.lic");
			File localFile2 = new File("E:\\Personal\\.jrebel\\jrebel.lic");
			// File localFile2 = new File(
			// "E:\\backup\\软件\\jrebel\\jrebel5.3.0\\jrebel-master\\jrebel.lic");

			Assert.assertNotNull(localFile2);
			System.out.println("start to get objectInputStream.");
			localObject1 = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(localFile2)));
			Assert.assertNotNull(localObject1);
			System.out.println("start to get userLicense.");
			UserLicense localUserLicense1 = (UserLicense) ((ObjectInputStream) localObject1)
					.readObject();
			Assert.assertNotNull(localUserLicense1);
			System.out.println("start to get ObjectInputStream2.");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					new ByteArrayInputStream(localUserLicense1.getLicense()));
			Assert.assertNotNull(localObjectInputStream);
			System.out.println("start to get localMap.");
			Map localMap = (Map) localObjectInputStream.readObject();
			Assert.assertNotNull(localMap);
			System.out.println("start tot output value.");
			for (Iterator<?> iter = localMap.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				System.out.println("key: " + key + ", value: "
						+ localMap.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("faile to parse license. ");
		} finally {
			if (localObject1 != null) {
				try {
					((ObjectInputStream) localObject1).close();
				} catch (IOException localIOException12) {
				}
			}
		}
	}
}
