package com.hk.commons.poi;

/**
 * POI Exception
 *
 * @author kevin@sjq-it.com
 * @date 2017年7月17日上午9:03:21
 */
@SuppressWarnings("serial")
public class POIException extends RuntimeException {

    public POIException(String message) {
        super(message);
    }

    public POIException(String message, Throwable t) {
        super(message, t);
    }

}
