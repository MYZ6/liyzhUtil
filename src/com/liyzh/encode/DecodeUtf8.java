package com.liyzh.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DecodeUtf8 {
	public static void main(String[] args) {
		String str = "HTTP%3a%2f%2feip.ccb.com%2fwps%2fmyportal%2fwcmcontent%3fns%3a%3dns%3aLHQ6LGY6LGM6OGIxYmM3YzQzYjc1MjllNTAxM2NmNTQ4NzJiNDI3MTMscDosYTosbTo%3d%2fthread.vsml";
		try {
			String after = URLDecoder.decode(str, "utf-8");
			System.out.println(after);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	}
}
