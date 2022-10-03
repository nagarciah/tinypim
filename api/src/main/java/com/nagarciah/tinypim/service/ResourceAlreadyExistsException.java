package com.nagarciah.tinypim.service;

public class ResourceAlreadyExistsException extends TinypimException {

	private static final long serialVersionUID = 8522388393719740385L;

	public ResourceAlreadyExistsException() {
	}

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

	public ResourceAlreadyExistsException(Throwable cause) {
		super(cause);
	}

	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
