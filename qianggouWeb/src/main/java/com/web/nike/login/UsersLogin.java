package com.web.nike.login;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.web.nike.util.PreData;
import com.web.nike.bean.MainPageInfo;
import com.web.nike.bean.UserLoginBean;
import com.web.nike.bean.UserLoginInfo;

public class UsersLogin {

	public void exec() {

		ExecutorService pool = Executors.newFixedThreadPool(5);

		List<UserLoginInfo> userList = PreData.getAllUserList();
		MainPageInfo mainPageInfo = PreData.getMainPageInfo();

		for (UserLoginInfo userInfo : userList) {
			if (userInfo.getLoginFlag()) {
				continue;
			}
			UserLoginBean login = new UserLoginBean(userInfo, mainPageInfo);
			NikeLoginThread target = new NikeLoginThread(login);
			Thread t = new Thread(target);
			pool.execute(t);
		}

		pool.shutdown();

		while (true) {
			if (pool.isTerminated()) {
				System.out.println("login over!!");
				break;
			}
		}
	}

}
