package com.web.nike.bean;

import java.util.HashMap;
import java.util.Map;

public class ParseProductBean {

	private Boolean productEsitsflag = false;
	private Boolean startflag = false;
	private Boolean skuIdEsitsFlag = false;

	private String action;
	private String lang_locale;
	private String country;
	private String catalogId;
	private String productId;
	private String price;
	private String siteId;
	private String line1;
	private String line2;
	private String passcode;
	private String sizeType;
	private String title;
	private String pid;
	private String skuId;
	private String size;
	private String startDate;

	private Map<String, String> skuMap = new HashMap<String, String>();

	public void copy(ParseProductBean from) {
		this.productEsitsflag = from.getProductEsitsflag();
		this.startflag = from.getStartflag();
		this.skuIdEsitsFlag = from.getSkuIdEsitsFlag();
		this.action = from.getAction();
		this.lang_locale = from.getLang_locale();
		this.country = from.getCountry();
		this.catalogId = from.getCatalogId();
		this.productId = from.getProductId();
		this.price = from.getPrice();
		this.siteId = from.getSiteId();
		this.line1 = from.getLine1();
		this.line2 = from.getLine2();
		this.passcode = from.getPasscode();
		this.sizeType = from.getSizeType();
		this.title = from.getTitle();
		this.pid = from.getPid();
		this.skuId = from.getSkuId();
		this.size = from.getSize();
		this.startDate = from.getStartDate();
		if (from.getSkuMap() != null) {
			for (Map.Entry<String, String> e : from.getSkuMap().entrySet()) {
				skuMap.put(e.getKey(), e.getValue());
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("productEsitsflag=" + productEsitsflag);
		sb.append(", startflag=" + startflag)
		.append(", action=" + action)
		.append(", action=" + action)
		.append(", lang_locale=" + lang_locale)
		.append(", country=" + country)
		.append(", catalogId=" + catalogId)
		.append(", productId=" + productId)
		.append(", price=" + price)
		.append(", siteId=" + siteId)
		.append(", line1=" + line1)
		.append(", line2=" + line2)
		.append(", passcode=" + passcode)
		.append(", sizeType=" + sizeType)
		.append(", title=" + title)
		.append(", pid=" + pid)
		.append(", skuId=" + skuId)
		.append(", size=" + size)
		.append(", startDate=" + startDate)
		.append(", skuMap=" + skuMap.toString());

		return sb.toString();
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Map<String, String> getSkuMap() {
		return skuMap;
	}

	public void setSkuMap(Map<String, String> skuMap) {
		this.skuMap = skuMap;
	}

	public Boolean getProductEsitsflag() {
		return productEsitsflag;
	}

	public void setProductEsitsflag(Boolean productEsitsflag) {
		this.productEsitsflag = productEsitsflag;
	}

	public Boolean getStartflag() {
		return startflag;
	}

	public void setStartflag(Boolean startflag) {
		this.startflag = startflag;
	}

	public Boolean getSkuIdEsitsFlag() {
		return skuIdEsitsFlag;
	}

	public void setSkuIdEsitsFlag(Boolean skuIdEsitsFlag) {
		this.skuIdEsitsFlag = skuIdEsitsFlag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
