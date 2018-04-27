/**
 * 
 */
package com.hk.commons.poi.excel.read;

import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.read.handler.DomReadHandler;
import com.hk.commons.poi.excel.read.handler.ReadableHandler;
import com.hk.commons.poi.excel.read.handler.SimpleDomReadHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangkai
 *
 */
public class SimpleDomReadableExcel<T> extends AbstractReadableExcel<T> {

	/**
	 * 使用Dom解析的处理器
	 */
	private final DomReadHandler<T> handler;

	/**
	 * 
	 * @param readParam
	 */
	public SimpleDomReadableExcel(ReadParam<T> readParam) {
		this(readParam, new SimpleDomReadHandler<>(readParam));
	}

	/**
	 * 
	 * @param readParam
	 * @param handler
	 */
	public SimpleDomReadableExcel(ReadParam<T> readParam, DomReadHandler<T> handler) {
		super(readParam);
		this.handler = handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.AbstractReadableExcel#createReadableHandler(
	 * java.io.InputStream)
	 */
	@Override
	protected ReadableHandler<T> createReadableHandler(InputStream in) throws IOException {
		return handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.AbstractReadableExcel#createReadableHandler(
	 * java.io.File)
	 */
	@Override
	protected ReadableHandler<T> createReadableHandler(File file) throws IOException {
		return handler;
	}

	@Override
	protected ColumnProperty getColumnProperty() {
		return new AnnotationColumnProperty();
	}

}
