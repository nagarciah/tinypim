package com.nagarciah.tinypim.service;

public class TinypimException extends RuntimeException {

	private static final long serialVersionUID = 5973638069777414166L;

	public TinypimException() {
	}

	public TinypimException(String message) {
		super(message);
	}

	public TinypimException(Throwable cause) {
		super(cause);
	}

	public TinypimException(String message, Throwable cause) {
		super(message, cause);
	}

	public TinypimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
