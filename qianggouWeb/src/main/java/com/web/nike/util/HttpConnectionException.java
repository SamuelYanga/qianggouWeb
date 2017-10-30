package com.web.nike.util;

public class HttpConnectionException extends NikeShoppingRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2735801868164761784L;

	public HttpConnectionException(String msg) {
		super(msg);
	}
	
	public HttpConnectionException(String msg, Throwable cause) {
		super(msg,  cause);
	}
	
	public HttpConnectionException(String msg, String reason) {
		super(msg,reason);
	}
	
	public HttpConnectionException(String msg, String reason, Throwable cause) {
		super(msg,reason, cause);
	}

}
