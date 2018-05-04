package com.hk.commons.poi.excel.exception;

import com.hk.commons.poi.WriteableException;

/**
 * 写入Excel异常
 * @author huangkai
 */
@SuppressWarnings("serial")
public class WriteableExcelException extends WriteableException {

    public WriteableExcelException(String message) {
        super(message);
    }

    public WriteableExcelException(String message, Throwable t) {
        super(message, t);
    }

}
