package com.web.nike.util;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str != null && str.length() > 0) {
			return false;
		}
		return true;
	}
}
