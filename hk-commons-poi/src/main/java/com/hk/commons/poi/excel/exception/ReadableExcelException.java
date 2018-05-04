package com.hk.commons.poi.excel.exception;

import com.hk.commons.poi.ReabableException;

/**
 * 读取Excel异常
 * @author huangkai
 *
 */
@SuppressWarnings("serial")
public class ReadableExcelException extends ReabableException {

	public ReadableExcelException(String message) {
		super(message);
	}

	public ReadableExcelException(String message, Throwable t) {
		super(message, t);
	}

}
