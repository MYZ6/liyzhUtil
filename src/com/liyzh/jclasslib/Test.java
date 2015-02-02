package com.liyzh.jclasslib;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.MethodInfo;
import org.gjt.jclasslib.structures.attributes.CodeAttribute;

public class Test {
	public static void main(String[] args) throws Exception {

		String filePath = "c:/C.class";
		FileInputStream fis = new FileInputStream(filePath);

		DataInput di = new DataInputStream(fis);
		ClassFile cf = new ClassFile();
		cf.read(di);
		MethodInfo mi = cf.getMethod("Ă", "(Ljava/util/Properties;)V");
		AttributeInfo[] aArr = mi.getAttributes();
		CodeAttribute codeAttr = (CodeAttribute) aArr[0];
		byte[] codeArr = codeAttr.getCode();
		for (int i = 0; i < codeArr.length; i++) {
			System.out.println(i + "\t" + codeArr[i]);
			if (i == 172) {
				codeArr[i] = -103;
			}
		}
		codeAttr.setCode(codeArr);
		// for (AttributeInfo attr : aArr) {
		// System.out.println(attr.getName());
		// }
		// MethodInfo[] miArr = cf.getMethods();
		// for (MethodInfo mInfo : miArr) {
		// System.out.println(mInfo.getName());
		// System.out.println(mInfo.getDescriptor());
		// }
		// CPInfo[] infos = cf.getConstantPool();
		//
		// int count = infos.length;
		// for (int i = 0; i < count; i++) {
		// if (infos[i] != null) {
		// System.out.print(i);
		// System.out.print(" = ");
		// System.out.print(infos[i].getVerbose());
		// System.out.print(" = ");
		// System.out.println(infos[i].getTagVerbose());
		// if (i == 362) {
		// ConstantUtf8Info uInfo = (ConstantUtf8Info) infos[i];
		// uInfo.setBytes("芝麻不开门!".getBytes());
		// infos[i] = uInfo;
		// }
		// }
		// }
		// cf.setConstantPool(infos);
		fis.close();
		String newFilePath = "c:/new/C.class";
		File f = new File(newFilePath);
		ClassFileWriter.writeToFile(f, cf);

		String zipPath = "c:/dbvis.jar";
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
	}
}