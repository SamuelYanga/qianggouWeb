package com.web.nike.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CallBackBean {

	private String callback = "nike_Cart_handleJCartResponse";
	private String action = "addItem";
	private String lang_locale = "zh_CN";
	private String country = "CN";
	private String catalogId;
	private String productId;
	private String price;
	private String siteId = "";
	private String line1;
	private String line2;
	private String passcode = "";
	private String sizeType = "";
	private String skuAndSize;
	private String qty = "1";
	private String rt = "json";
	private String view = "3";
	private String skuId;
	private String displaySize;
	private String time_;

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLang_locale() {
		return lang_locale;
	}

	public void setLang_locale(String lang_locale) {
		this.lang_locale = lang_locale;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public String getSizeType() {
		return sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}

	public String getSkuAndSize() {
		return skuAndSize;
	}

	public void setSkuAndSize(String skuAndSize) {
		this.skuAndSize = skuAndSize;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public String getTime_() {
		return time_;
	}

	public void setTime_(String time_) {
		this.time_ = time_;
	}

	public String getCallbackUrl() {
		StringBuilder url = new StringBuilder("?callback=");
		url.append("&action=" + action)
		.append("&rt=" + rt)
		.append("&country=" + country)
		.append("&lang_locale=" + lang_locale)
		.append("&_=" + time_);

		return url.toString();
	}

	public String getAddProductUrl() throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder("?callback=nike_Cart_handleJCartResponse");
		url.append("&action="+ action)
		.append("&lang_locale=" + lang_locale)
		.append("&country=" + country)
		.append("&catalogId=" + catalogId)
		.append("&productId=" + productId)
		.append("&price=" + price)
		.append("&siteId=" + siteId)
		.append("&line1=" + URLEncoder.encode(line1, "utf-8"))
		.append("&line2=" + URLEncoder.encode(line2, "utf-8"))
		.append("&passcode=" + passcode)
		.append("&sizeType=" + sizeType)
		.append("&skuAndSize=" + skuAndSize)
		.append("&qty=" + qty)
		.append("&rt=" + rt)
		.append("&view=" + view)
		.append("&skuId=" + skuId)
		.append("&displaySize=" + displaySize)
		.append("&_=" + time_);

		return url.toString();
	}
}
