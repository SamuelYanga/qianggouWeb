package com.web.nike.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	public static void doSecondSleep(int secondNum) {
		try {
			TimeUnit.SECONDS.sleep(secondNum);
		} catch (InterruptedException ignore) {
			ignore.printStackTrace();
		}
	}
}
