package com.web.nike.bean;

public class AddProductResultBean {

	private String status;
	private String ewt;
	private String pil;
	private String psh;
	private String productId;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("status=" + status).append(", ewt=" + ewt).append(", pil=" + pil).append(", productId=" + productId);
		return sb.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEwt() {
		return ewt;
	}

	public void setEwt(String ewt) {
		this.ewt = ewt;
	}

	public String getPil() {
		return pil;
	}

	public void setPil(String pil) {
		this.pil = pil;
	}

	public String getPsh() {
		return psh;
	}

	public void setPsh(String psh) {
		this.psh = psh;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
