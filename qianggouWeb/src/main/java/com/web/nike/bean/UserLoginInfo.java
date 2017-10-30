package com.web.nike.bean;

public class UserLoginInfo {

	private String userName;
	private String userPwd;
	private String autoSize;
	private String proxyIp;
	private String proxyPort;
	private String proxyUser;
	private String proxyPwd;
	private String slsw;
	private String APID;
	private String AnalysisUserId;
	private String slCheck;
	private String llCheck;
	private String sls;
	private String lls;
	private String ak_bmsc;

	private Boolean loginFlag = false;
	
	public UserLoginInfo() {
		
	}
	
	public UserLoginInfo(String userName, String userPwd, String autoSize, String proxyIp, String proxyPort,
			String proxyUser, String proxyPwd) {
		this.userName = userName;
		this.userPwd = userPwd;
		this.autoSize = autoSize;
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
		this.proxyUser = proxyUser;
		this.proxyPwd = proxyPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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

	public String getSlsw() {
		return slsw;
	}

	public void setSlsw(String slsw) {
		this.slsw = slsw;
	}

	public String getAPID() {
		return APID;
	}

	public void setAPID(String aPID) {
		APID = aPID;
	}

	public String getAnalysisUserId() {
		return AnalysisUserId;
	}

	public void setAnalysisUserId(String analysisUserId) {
		AnalysisUserId = analysisUserId;
	}

	public String getSlCheck() {
		return slCheck;
	}

	public void setSlCheck(String slCheck) {
		this.slCheck = slCheck;
	}

	public String getLlCheck() {
		return llCheck;
	}

	public void setLlCheck(String llCheck) {
		this.llCheck = llCheck;
	}

	public String getSls() {
		return sls;
	}

	public void setSls(String sls) {
		this.sls = sls;
	}

	public String getLls() {
		return lls;
	}

	public void setLls(String lls) {
		this.lls = lls;
	}

	public Boolean getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Boolean loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getAk_bmsc() {
		return ak_bmsc;
	}

	public void setAk_bmsc(String ak_bmsc) {
		this.ak_bmsc = ak_bmsc;
	}

	public void update(UserLoginInfo info) {
		this.slsw = info.getSlsw();
		this.APID = info.getAPID();
		this.AnalysisUserId = info.getAnalysisUserId();
		this.slCheck = info.getSlCheck();
		this.llCheck = info.getLlCheck();
		this.sls = info.getSls();
		this.lls = info.getLls();
		this.loginFlag = info.getLoginFlag();
	}
}
