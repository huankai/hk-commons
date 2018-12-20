package com.hk.commons.poi;

/**
 * 读取异常
 *
 * @author kevin
 */
@SuppressWarnings("serial")
public class ReadException extends POIException {

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, Throwable t) {
        super(message, t);
    }

}
