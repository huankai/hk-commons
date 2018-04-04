package com.hk.commons.poi;

/**
 * 
 * @author kally
 *
 */
@SuppressWarnings("serial")
public class WriteableException extends POIException {

	public WriteableException(String message) {
		super(message);
	}

	public WriteableException(String message, Throwable t) {
		super(message, t);
	}
}
