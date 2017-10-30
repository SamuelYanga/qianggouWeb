package com.web.nike.login;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.web.nike.util.HttpClientManager;
import com.web.nike.util.JsonUtil;
import com.web.nike.util.PreData;
import com.web.nike.bean.CallBackBean;
import com.web.nike.bean.LoginResultBean;
import com.web.nike.bean.UserLoginBean;
import com.web.nike.bean.UserLoginInfo;

public class NikeLoginThread implements Runnable {

	private UserLoginBean loginBean;
	private HttpClientManager httpClientManager;
	private String productUrl = "http://www.nike.com/cn/zh_cn/";
//	private String loginUrl = "https://unite.nike.com/loginWithSetCookie?appVersion=221&experienceVersion=187&uxid=com.nike.commerce.nikedotcom.web&locale=zh_CN&backendEnvironment=default&browser=Google%20Inc.&os=undefined&mobile=false&native=false";
	private String loginUrl = "https://unite.nike.com/loginWithSetCookie";
	private String getUserUrl = "https://unite.nike.com/getUser";
	private String callbackUrl = "https://secure-store.nike.com/ap/services/jcartService";

	private Map<String, String> loginResultCookies;
	private LoginResultBean loginResultBean;
	private Map<String, String> getUserCookies;
	private Map<String, String> callbackCookies;

	public HttpClientManager getHttpClientManager() {
		return httpClientManager;
	}

	public NikeLoginThread(UserLoginBean loginBean) {
		this.loginBean = loginBean;
		httpClientManager = new HttpClientManager(20000, 50000);
	}

	public boolean login(int retryTimes) {

		if (retryTimes > 0) {
			if (!login_()) {
				login(--retryTimes);
			}
		} else {
			System.out.println("login error, userInfo:{" + loginBean.getUserString() + "}");
			return false;
		}

		return true;
	}
	
	public boolean login_() {
		if (PreData.isLogin(loginBean.getUsername())) {
			return true;
		}

		boolean loginFlag = loginWithSetCookie_();
		boolean getUserFlag = false;
		boolean callBackFlag = false;

		if (loginFlag) {
			getUserFlag = getUser_();
		}

		if (getUserFlag) {
			callBackFlag = callBack_();
		}
		return callBackFlag;
	}

	
	@SuppressWarnings("unchecked")
	public boolean loginWithSetCookie_() {
		try {
			URI uri = new URI(loginUrl + loginBean.getLoginUrl());

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			headers.put("Origin", "http://store.nike.com");
			headers.put("Content-Type", "text/plain");
			headers.put("Accept", "*/*");
			headers.put("Referer", productUrl);
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.8");

			Map<String, Object> params = loginBean.getPostParam();
			
			StringBuilder cookie = new StringBuilder("testY=a");
			String result = "";
			
			Map<String, Object> resultMap = httpClientManager.execHttpPost(uri, params, headers, cookie.toString(),
					loginBean.getProxyIp(), loginBean.getProxyPort(), loginBean.getProxyUser(),
					loginBean.getProxyPwd());
			loginResultCookies = (Map<String, String>) resultMap.get("cookie");
			result = (String) resultMap.get("json");
			loginResultBean = JsonUtil.getObjectFromJson(result, LoginResultBean.class);
			loginBean.setUuid(loginResultBean.getUser_id());
			loginBean.setToken(loginResultBean.getAccess_token());
			
			return true;

		} catch (URISyntaxException e) {
			System.out.println("loginWithSetCookie_ error, userInfo:{" + loginBean.getUserString() + "}");
			e.printStackTrace();
			return false;
		}
	}

	
	@SuppressWarnings("unchecked")
	public boolean getUser_() {
		try {
			URI uri = new URI(getUserUrl + loginBean.getUserUrl());
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			headers.put("Origin", "http://store.nike.com");
			headers.put("Accept", "*/*");
			headers.put("Referer", productUrl);
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.8");

			Map<String, Object> resultMap = httpClientManager.execHttpGet(uri, headers, null, loginBean.getProxyIp(),
					loginBean.getProxyPort(), loginBean.getProxyUser(), loginBean.getProxyPwd());

			getUserCookies = (Map<String, String>) resultMap.get("cookie");
			loginBean.setAnalysisUserId(getUserCookies.get("AnalysisUserId"));

			return true;

		} catch (URISyntaxException e) {
			System.out.println("getUser_ error, userInfo:{" + loginBean.getUserString() + "}");
			e.printStackTrace();
			return false;
		}
	}

	
	@SuppressWarnings("unchecked")
	public boolean callBack_() {
		try {
			CallBackBean callbackBean = new CallBackBean();
			callbackBean.setAction("getCartSummary");
			callbackBean.setRt("json");
			callbackBean.setCountry("CN");
			callbackBean.setLang_locale("zh_CN");
			callbackBean.setTime_(String.valueOf(System.currentTimeMillis()));

			URI uri = new URI(callbackUrl + callbackBean.getCallbackUrl());

			Map<String, String> headers = new HashMap<String, String>();
			headers.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			headers.put("Accept", "*/*");
			headers.put("Referer", productUrl);
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.8");

			StringBuilder cookies = new StringBuilder("AnalysisUserId=" + loginBean.getAnalysisUserId());
			cookies.append(";guidS=")
			.append(";guidU=")
			.append(";sls=" + loginResultCookies.get("sls"))
			.append(";lls=" + loginResultCookies.get("lls"))
			.append(";slCheck=" + loginResultCookies.get("slCheck"))
			.append(";llCheck=" + loginResultCookies.get("llCheck"));
			Map<String, Object> resultMap = httpClientManager.execHttpGet(uri, headers, cookies.toString(), loginBean.getProxyIp(),
					loginBean.getProxyPort(), loginBean.getProxyUser(), loginBean.getProxyPwd());
			callbackCookies = (Map<String, String>) resultMap.get("cookie");

			return true;

		} catch (URISyntaxException e) {
			System.out.println("callBack_ error, userInfo:{" + loginBean.getUserString() + "}");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		try {
			if (login(2)) {
				updateLoginInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateLoginInfo() {
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		userLoginInfo.setUserName(loginBean.getUsername());
		userLoginInfo.setSlsw(callbackCookies.get("slsw"));
		userLoginInfo.setAPID(callbackCookies.get("APID"));
		userLoginInfo.setAnalysisUserId(getUserCookies.get("AnalysisUserId"));
		userLoginInfo.setSlCheck(loginResultCookies.get("slCheck"));
		userLoginInfo.setLlCheck(loginResultCookies.get("llCheck"));
		userLoginInfo.setSls(loginResultCookies.get("sls"));
		userLoginInfo.setLls(loginResultCookies.get("lls"));
		userLoginInfo.setAk_bmsc(callbackCookies.get("ak_bmsc"));
		userLoginInfo.setLoginFlag(true);
		PreData.updateUserInfo(userLoginInfo);
		callbackCookies.clear();
		getUserCookies.clear();
		loginResultCookies.clear();
		System.out.println(loginBean.getUsername() + " login success.");
	}

}
