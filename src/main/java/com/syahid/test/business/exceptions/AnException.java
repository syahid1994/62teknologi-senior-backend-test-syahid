package com.syahid.test.business.exceptions;

public class AnException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnException(Throwable t) {
		super(t);
	}
	
	public AnException(String message) {
		super(message);
	}
}
