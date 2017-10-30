package com.web.nike.run;

import java.util.HashMap;
import java.util.Map;

public class Test implements Runnable {
	static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {
		map.put("Thread-0", "线程0在运�?.");
		map.put("Thread-1", "线程1在运�?.");
		map.put("Thread-2", "线程2在运�?.");
		map.put("Thread-3", "线程3在运�?.");
		map.put("Thread-4", "线程4在运�?.");
		map.put("Thread-5", "线程5在运�?.");
		Thread t1 = new Thread(new Test());
		Thread t2 = new Thread(new Test());
		Thread t3 = new Thread(new Test());
		t1.start();
		t2.start();
		t3.start();
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ", " + map.get(Thread.currentThread().getName()));
	}

}
