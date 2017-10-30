package com.web.nike.add;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.web.nike.bean.ProductPageInfo;
import com.web.nike.bean.UserLoginInfo;
import com.web.nike.util.PreData;

public class Addproduct {

	private String url;

	public Addproduct() {

	}

	public Addproduct(String url) {
		this.url = url;
	}

	public void exec() {

		ExecutorService pool = Executors.newFixedThreadPool(8);
		List<UserLoginInfo> userList = PreData.getLoginUserList(true);
		ProductPageInfo productInfo = PreData.getProductPageInfo();

		for (UserLoginInfo userInfo : userList) {
			AddProductThread target = new AddProductThread(userInfo, productInfo, url);
			Thread t = new Thread(target);
			pool.execute(t);
		}

		pool.shutdown();

		while (true) {
			if (pool.isTerminated()) {
				System.out.println("Add product over!!");
				break;
			}
		}
	}
}
