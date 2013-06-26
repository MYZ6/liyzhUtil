package com.liyzh.javassist;

import java.io.File;
import java.io.FileOutputStream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Test {

	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();

		// 设置目标类的路径，确保能够找到需要修改的类，这里我指向firestorm.jar
		// 解包后的路径
		pool.insertClassPath("c:/jrebel");

		// 获得要修改的类
		CtClass cc = pool.get("com.zeroturnaround.javarebel.Ae");
		// 设置方法需要的参数
		CtClass[] param = new CtClass[2];
		param[0] = pool.get("java.security.cert.X509Certificate[]");
		param[1] = pool.get("java.lang.String");

		// 得到方法
		CtMethod m = cc.getDeclaredMethod("a", param);
		// 插入新的代码
		m.insertBefore("{return true ;}");
		System.out.println(cc.toBytecode().length);
		File f = new File("c:/test.class");
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(cc.toBytecode());
		// 保存到文件里
		cc.debugWriteFile("new");
	}
}