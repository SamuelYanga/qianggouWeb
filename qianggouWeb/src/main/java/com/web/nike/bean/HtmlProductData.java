package com.web.nike.bean;

public class HtmlProductData {

	private String productId;
	private String productGroupId;
	private String productTitle;
	private String productSubTitle;
	private String catalogId;
	private String startDate;
	private String rawPrice;
	private HtmlSkuContainer skuContainer = new HtmlSkuContainer();

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductSubTitle() {
		return productSubTitle;
	}

	public void setProductSubTitle(String productSubTitle) {
		this.productSubTitle = productSubTitle;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getRawPrice() {
		return rawPrice;
	}

	public void setRawPrice(String rawPrice) {
		this.rawPrice = rawPrice;
	}

	public HtmlSkuContainer getSkuContainer() {
		return skuContainer;
	}

	public void setSkuContainer(HtmlSkuContainer skuContainer) {
		this.skuContainer = skuContainer;
	}
}
