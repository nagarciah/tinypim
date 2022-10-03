package com.nagarciah.tinypim.service;

public class InvalidDataException extends TinypimException {

	private static final long serialVersionUID = 9043841261398576071L;

	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
	}

}
