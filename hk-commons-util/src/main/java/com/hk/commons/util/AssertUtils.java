package com.hk.commons.util;

import org.springframework.util.Assert;

/**
 * Assert utils
 * 
 * @author: kevin
 * @date 2017年8月31日上午10:09:14
 */
public abstract class AssertUtils extends Assert {

	/**
	 * Default blank error message
	 */
	private static final String DEFAULT_IS_blank_EX_MESSAGE = "The validated object is blank";

	/**
	 * Check the args is not blank
	 * 
	 * @param args
	 *            validate String input
	 */
	public static void notBlank(String args) {
		notBlank(args, DEFAULT_IS_blank_EX_MESSAGE);
	}

	/**
	 * Check the args is not blank
	 * 
	 * @param args
	 *            validate String input
	 * @param message
	 *            Custom validation is not through the error message
	 */
	public static void notBlank(String args, String message) {
		if (StringUtils.isBlank(args)) {
			throw new IllegalArgumentException(message);
		}
	}

}
