package com.shailendher.tracer.domain;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidInputException(final String message) {
		super(message);
	}

}
