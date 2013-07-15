package com.liyzh.linux;

import java.util.Scanner;

public class PathUtil {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input your windows style path: ");
		String path = sc.nextLine();
		sc.close();
		path = path.replace(":", "");
		path = path.replace("\\", "/");
		System.out.println("The corresponding unix style path is : ");
		System.out.println("/cygdrive/" + path);
	}
}
