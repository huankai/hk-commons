package com.hk.commons.poi;

/**
 * 写入异常
 *
 * @author kally
 */
@SuppressWarnings("serial")
public class WriteException extends POIException {

    public WriteException(String message) {
        super(message);
    }

    public WriteException(String message, Throwable t) {
        super(message, t);
    }
}
