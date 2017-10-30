package com.web.nike.bean;

public class GetUserBean {

	private String appVersion = "221";
	private String experienceVersion = "187";
	private String uxid = "com.nike.commerce.nikedotcom.web";
	private String locale = "zh_CN";
	private String backendEnvironment = "default";
	private String browser = "Google%20Inc.";
	private String os = "undefined";
	private String mobile = "false";
	private String uuid;
	private String token = "com.nike.commerce.nikedotcom.web";

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getExperienceVersion() {
		return experienceVersion;
	}

	public void setExperienceVersion(String experienceVersion) {
		this.experienceVersion = experienceVersion;
	}

	public String getUxid() {
		return uxid;
	}

	public void setUxid(String uxid) {
		this.uxid = uxid;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getBackendEnvironment() {
		return backendEnvironment;
	}

	public void setBackendEnvironment(String backendEnvironment) {
		this.backendEnvironment = backendEnvironment;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public GetUserBean() {
		
	}
	
	public GetUserBean(String userid, String token) {
		this.uuid = userid;
		this.token = token;
	}

	public String getUrlParams() {
		StringBuilder params = new StringBuilder("?nativa=false");
		params.append("&appVersion=" + appVersion);
		params.append("&experienceVersion=" + experienceVersion);
		params.append("&uxid=" + uxid);
		params.append("&locale=" + locale);
		params.append("&backendEnvironment=" + backendEnvironment);
		params.append("&browser=" + browser);
		params.append("&os=" + os);
		params.append("&mobile=" + mobile);
		params.append("&uuid=" + uuid);
		params.append("&token=" + token);
		return params.toString();
	}
}
