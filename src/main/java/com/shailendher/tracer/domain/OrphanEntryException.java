package com.shailendher.tracer.domain;

public class OrphanEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrphanEntryException() {
		super();
	}

	public OrphanEntryException(final String message) {
		super(message);
	}

}
