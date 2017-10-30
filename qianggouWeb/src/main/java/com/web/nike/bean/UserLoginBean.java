package com.web.nike.bean;

import java.util.HashMap;
import java.util.Map;

public class UserLoginBean {

	// login_url_param  getUser_param
	private String appVersion;
	private String experienceVersion;
	private String uxid;
	private String locale = "zh_CN";
	private String backendEnvironment;
	private String browser = "Google%20Inc.";
	private String os = "undefined";
	private String mobile = "false";
	private String native_ = "false";

	private String username;
	private String password;
	private String autoSize;
	private String proxyIp;
	private String proxyPort;
	private String proxyUser;
	private String proxyPwd;

	// login_post_param
	private Boolean keepMeLoggedIn = true;
	private String client_id;
	private String ux_id;
	private String grant_type = "password";

	// getUser_param
	private String uuid;
	private String token;
	private String AnalysisUserId;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAutoSize() {
		return autoSize;
	}

	public void setAutoSize(String autoSize) {
		this.autoSize = autoSize;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getProxyPwd() {
		return proxyPwd;
	}

	public void setProxyPwd(String proxyPwd) {
		this.proxyPwd = proxyPwd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getKeepMeLoggedIn() {
		return keepMeLoggedIn;
	}

	public void setKeepMeLoggedIn(Boolean keepMeLoggedIn) {
		this.keepMeLoggedIn = keepMeLoggedIn;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getUx_id() {
		return ux_id;
	}

	public void setUx_id(String ux_id) {
		this.ux_id = ux_id;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
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

	public String getAnalysisUserId() {
		return AnalysisUserId;
	}

	public void setAnalysisUserId(String analysisUserId) {
		AnalysisUserId = analysisUserId;
	}

	public UserLoginBean() {

	}

	public UserLoginBean(UserLoginInfo userInfo, MainPageInfo mainInfo) {
		this.username = userInfo.getUserName();
		this.password = userInfo.getUserPwd();
		this.proxyIp = userInfo.getProxyIp();
		this.proxyPort = userInfo.getProxyPort();
		this.proxyUser = userInfo.getProxyUser();
		this.proxyPwd = userInfo.getProxyPwd();
		this.appVersion = mainInfo.getAppVersion();
		this.experienceVersion = mainInfo.getExperienceVersion();
		this.uxid = mainInfo.getUxid();
		this.backendEnvironment = mainInfo.getBackendEnvironment();
		this.client_id = mainInfo.getClient_id();
		this.ux_id = mainInfo.getUx_id();
	}

	public String getUserString() {
		StringBuilder sb = new StringBuilder();
		sb.append("username=" + this.getUsername())
		.append("password=" + this.getPassword())
		.append("autoSize=" + this.getAutoSize())
		.append("proxyIp=" + this.getProxyIp())
		.append("proxyPort=" + this.getProxyPort())
		.append("proxyUser=" + this.getProxyUser())
		.append("proxyPwd=" + this.getProxyPwd());
		return sb.toString();
	}

	public String getLoginUrl() {
		StringBuilder sb = new StringBuilder("?");
		sb.append("&appVersion=" + this.getAppVersion())
		.append("&experienceVersion=" + this.getExperienceVersion())
		.append("&uxid=" + this.getUxid())
		.append("&locale=" + this.getLocale())
		.append("&backendEnvironment=" + this.getBackendEnvironment())
		.append("&browser=" + this.getBrowser())
		.append("&os=" + this.getOs())
		.append("&mobile=" + this.getMobile())
		.append("&native=" + this.getNative_());

		return sb.toString();
	}

	public Map<String, Object> getPostParam() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", this.getUsername());
		params.put("password", this.getPassword());
		params.put("keepMeLoggedIn", this.getKeepMeLoggedIn());
		params.put("client_id", this.getClient_id());
		params.put("ux_id", this.getUx_id());
		params.put("grant_type", this.getGrant_type());
		return params;
	}

	public String getUserUrl() {
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
