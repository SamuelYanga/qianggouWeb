package com.web.nike.util;

public class NikeShoppingRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4777011887086274817L;

	private String reason;

	public NikeShoppingRuntimeException(String msg) {
		this(msg, "");
	}

	public NikeShoppingRuntimeException(String msg, Throwable cause) {
		this(msg, "", cause);
	}

	public NikeShoppingRuntimeException(String msg, String reason) {
		super(msg);
		this.reason = reason;
	}

	public NikeShoppingRuntimeException(String msg, String reason, Throwable cause) {
		super(msg, cause);
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}
