package com.hk.commons.poi.excel.exception;


import com.hk.commons.poi.ReadException;

/**
 * 读取Excel异常
 * @author kevin
 *
 */
@SuppressWarnings("serial")
public class ExcelReadException extends ReadException {

	public ExcelReadException(String message) {
		super(message);
	}

	public ExcelReadException(String message, Throwable t) {
		super(message, t);
	}

}
