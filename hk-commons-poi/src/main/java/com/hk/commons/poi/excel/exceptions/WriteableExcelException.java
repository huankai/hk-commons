package com.hk.commons.poi.excel.exceptions;

import com.hk.commons.poi.WriteableException;

@SuppressWarnings("serial")
public class WriteableExcelException extends WriteableException {

	public WriteableExcelException(String message) {
		super(message);
	}

	public WriteableExcelException(String message, Throwable t) {
		super(message, t);
	}

}
