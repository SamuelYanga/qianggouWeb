package com.web.nike.bean;

import java.util.ArrayList;
import java.util.List;

public class HtmlSkuContainer {

	private String id;
	private String displaySizeType;
	private String displaySizeTypeLabel;
	private List<HtmlSkuInfo> productSkus = new ArrayList<HtmlSkuInfo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplaySizeType() {
		return displaySizeType;
	}

	public void setDisplaySizeType(String displaySizeType) {
		this.displaySizeType = displaySizeType;
	}

	public String getDisplaySizeTypeLabel() {
		return displaySizeTypeLabel;
	}

	public void setDisplaySizeTypeLabel(String displaySizeTypeLabel) {
		this.displaySizeTypeLabel = displaySizeTypeLabel;
	}

	public List<HtmlSkuInfo> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<HtmlSkuInfo> productSkus) {
		this.productSkus = productSkus;
	}
}
