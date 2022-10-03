package com.nagarciah.tinypim.service;

public class InvalidParentException extends TinypimException {

	private static final long serialVersionUID = 7560552345953116366L;

	public InvalidParentException() {
		super();
	}

	public InvalidParentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidParentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParentException(String message) {
		super(message);
	}

	public InvalidParentException(Throwable cause) {
		super(cause);
	}

}
