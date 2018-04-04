package com.hk.commons.poi;

/**
 * 
 * @author kally
 *
 */
@SuppressWarnings("serial")
public class ReabableException extends POIException {

	public ReabableException(String message) {
		super(message);
	}

	public ReabableException(String message, Throwable t) {
		super(message, t);
	}

}
