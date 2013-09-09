package com.liyzh.javassist;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.zeroturnaround.licensing.UserLicense;

public class ParseChangeLicense {
	@Test
	public void parseLicenseFile() {
		Object localObject1 = null;
		try {
			System.out.println("start to get lic file.");
			File localFile2 = new File(
					"E:\\backup\\软件\\jrebel\\jrebel5.3.0\\jrebel-master\\jrebel.lic");
			// File localFile2 = new File("d:\\temp\\jrebel.lic");
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
			System.out.println("start to change date.");
			Calendar cal = new GregorianCalendar(2013, 9, 01);
			localMap.put("validUntil", cal.getTime());
			// cal.set(Calendar.MONTH, 2);
			// localMap.put("limitedUntil", cal.getTime());
			// localMap.put("validFrom", cal.getTime());
			localMap.put("Seats", 100);

			System.out.println("start to set a new date to license.");
			ByteArrayOutputStream out1 = new ByteArrayOutputStream();
			ObjectOutputStream out2 = new ObjectOutputStream(out1);
			out2.writeObject(localMap);
			out1.toByteArray();
			localUserLicense1.setLicense(out1.toByteArray());
			out1.close();
			out2.close();
			System.out.println("start to get ObjectInputStream2.");
			localObjectInputStream = new ObjectInputStream(
					new ByteArrayInputStream(localUserLicense1.getLicense()));
			Assert.assertNotNull(localObjectInputStream);
			System.out.println("start to get localMap.");
			localMap = (Map) localObjectInputStream.readObject();
			Assert.assertNotNull(localMap);
			for (Iterator<?> iter = localMap.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				System.out.println("key: " + key + ", value: "
						+ localMap.get(key));
			}
			String newLicenseFile = "d:\\jrebel.lic";
			ObjectOutputStream out3 = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(
							newLicenseFile)));
			out3.writeObject(localUserLicense1);
			out3.close();
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
