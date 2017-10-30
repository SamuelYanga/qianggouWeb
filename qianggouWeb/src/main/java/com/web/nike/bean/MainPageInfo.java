package com.web.nike.bean;

public class MainPageInfo {

	private String appVersion = "223";//
	private String experienceVersion = "188";//
	private String uxid = "com.nike.commerce.nikedotcom.web";//
	private String locale = "zh_CN";
	private String backendEnvironment = "default";//
	private String browser = "Google%20Inc.";
	private String os = "undefined";
	private String mobile = "false";
	private String native_ = "false";

	private String client_id = "HlHa2Cje3ctlaOqnxvgZXNaAs7T9nAuH";//
	private String grant_type = "password";
	private String keepMeLoggedIn = "True";
	private String ux_id = "com.nike.commerce.nikedotcom.web";//

	private Boolean setFlag = false;

	public void copy(MainPageInfo from) {
		this.appVersion = from.appVersion;
		this.experienceVersion = from.experienceVersion;
		this.uxid = from.uxid;
		this.locale = from.locale;
		this.backendEnvironment = from.backendEnvironment;
		this.browser = from.browser;
		this.os = from.os;
		this.mobile = from.mobile;
		this.native_ = from.native_;
		this.client_id = from.client_id;
		this.grant_type = from.grant_type;
		this.keepMeLoggedIn = from.keepMeLoggedIn;
		this.ux_id = from.ux_id;
		this.setFlag = from.setFlag;
	}

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

	public String getNative_() {
		return native_;
	}

	public void setNative_(String native_) {
		this.native_ = native_;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getKeepMeLoggedIn() {
		return keepMeLoggedIn;
	}

	public void setKeepMeLoggedIn(String keepMeLoggedIn) {
		this.keepMeLoggedIn = keepMeLoggedIn;
	}

	public String getUx_id() {
		return ux_id;
	}

	public void setUx_id(String ux_id) {
		this.ux_id = ux_id;
	}

	public Boolean getSetFlag() {
		return setFlag;
	}

	public void setSetFlag(Boolean setFlag) {
		this.setFlag = setFlag;
	}

}
