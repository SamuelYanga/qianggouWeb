package com.web.nike.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.nike.bean.MainPageInfo;
import com.web.nike.bean.ProductPageInfo;
import com.web.nike.bean.UserLoginInfo;

public class PreData {

	public static MainPageInfo mainPageInfo = new MainPageInfo();

	public synchronized static void updateMainPageInfo(MainPageInfo info) {
		mainPageInfo.copy(info);
	}

	public static MainPageInfo getMainPageInfo() {
		MainPageInfo result = new MainPageInfo();
		result.copy(mainPageInfo);
		return result;
	}

	public static ProductPageInfo productPageInfo = new ProductPageInfo();

	public synchronized static void updateProductPageInfo(ProductPageInfo info) {
		productPageInfo.copy(info);
	}

	public static ProductPageInfo getProductPageInfo() {
		ProductPageInfo result = new ProductPageInfo();
		result.copy(productPageInfo);
		return result;
	}

	public static Map<String, UserLoginInfo> userMap = new HashMap<String, UserLoginInfo>();

	public synchronized static void addUserInfo(UserLoginInfo userLoginInfo) {
		if (userMap.get(userLoginInfo.getUserName()) != null
				&& userMap.get(userLoginInfo.getUserName()).getLoginFlag()) {
			return;
		}
		userMap.put(userLoginInfo.getUserName(), userLoginInfo);
	}

	public synchronized static void addUsersInfo(List<UserLoginInfo> userList) {
		for (UserLoginInfo userLoginInfo : userList) {
			if (userMap.get(userLoginInfo.getUserName()) != null
					&& userMap.get(userLoginInfo.getUserName()).getLoginFlag()) {
				if (!userMap.get(userLoginInfo.getUserName()).getAutoSize().equals(userLoginInfo.getAutoSize())) {
					UserLoginInfo temp = userMap.get(userLoginInfo.getUserName());
					temp.setAutoSize(userLoginInfo.getAutoSize());
					userMap.put(userLoginInfo.getUserName(), temp);
				}
				continue;
			}
			userMap.put(userLoginInfo.getUserName(), userLoginInfo);
		}
	}

	public synchronized static void updateUserInfo(UserLoginInfo userLoginInfo) {
		for (Map.Entry<String, UserLoginInfo> e : userMap.entrySet()) {
			String userName = e.getKey();
			UserLoginInfo info = e.getValue();
			if (userName.equals(userLoginInfo.getUserName())) {
				info.update(userLoginInfo);
				userMap.put(userName, info);
			}
		}
	}

	public synchronized static void deleteUserInfo(String userName) {
		userMap.remove(userName);
	}

	public static UserLoginInfo getUserInfo(String userName) {
		return userMap.get(userName);
	}

	public static List<UserLoginInfo> getLoginUserList(boolean flag) {
		List<UserLoginInfo> result = new ArrayList<UserLoginInfo>();
		for (Map.Entry<String, UserLoginInfo> e : userMap.entrySet()) {
			UserLoginInfo info = e.getValue();
			if (flag && info.getLoginFlag()) {
				result.add(e.getValue());
			} else if (!flag && !info.getLoginFlag()) {
				result.add(e.getValue());
			}
		}
		return result;
	}

	public static List<UserLoginInfo> getAllUserList() {
		List<UserLoginInfo> result = new ArrayList<UserLoginInfo>();
		for (Map.Entry<String, UserLoginInfo> e : userMap.entrySet()) {
			result.add(e.getValue());
		}
		return result;
	}

	public static boolean isLogin(String userName) {
		UserLoginInfo info = userMap.get(userName);
		if (info != null && info.getLoginFlag()) {
			return true;
		}
		return false;
	}

}
