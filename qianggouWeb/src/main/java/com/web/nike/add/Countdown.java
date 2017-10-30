package com.web.nike.add;

import java.util.Map;

import com.web.nike.bean.UserLoginInfo;
import com.web.nike.login.UsersLogin;
import com.web.nike.util.PreData;

public class Countdown {

	// static String url = "http://store.nike.com/cn/zh_cn/pd/air-force-1-ultraforce-lthr-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B/pid-11254739/pgid-11493581";

	// static String url = "http://store.nike.com/cn/zh_cn/pd/air-jordan-13-retro-%E5%A4%8D%E5%88%BB%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B/pid-11252851/pgid-11446368?cp=cnns_soc_021817_m_aj13_si02";
	static String url = "http://store.nike.com/cn/zh_cn/pd/air-jordan-1-retro-high-og-as-%E7%94%B7%E5%AD%90%E8%BF%90%E5%8A%A8%E9%9E%8B/pid-11464172/pgid-11592084";

	public static void main(String[] args) {

		OpenProduct openProduct = new OpenProduct(url);
		openProduct.init();

		UsersLogin userLogin = new UsersLogin();
		userLogin.exec();
		System.out.println(PreData.mainPageInfo);
		System.out.println(PreData.productPageInfo);
		System.out.println(PreData.userMap);

		for (Map.Entry<String, UserLoginInfo> e : PreData.userMap.entrySet()) {
			System.out.print(e.getKey() + " ");
			System.out.println(((UserLoginInfo)e.getValue()).getLoginFlag());
			System.out.println();
		}

		Long cutDownTime = PreData.productPageInfo.getCutDownTime();

		// 1485392400000L
		while (true) {
			if (System.currentTimeMillis() > (cutDownTime+1000)) {
				break;
			}
		}

		Addproduct addproduct = new Addproduct(url);
		addproduct.exec();

	}

}
