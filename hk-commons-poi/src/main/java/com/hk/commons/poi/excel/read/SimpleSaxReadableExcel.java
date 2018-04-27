/**
 * 
 */
package com.hk.commons.poi.excel.read;

import com.google.common.io.Files;
import com.hk.commons.poi.excel.exceptions.ReadableExcelException;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.read.handler.*;
import com.hk.commons.util.StringUtils;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangkai
 *
 */
public class SimpleSaxReadableExcel<T> extends AbstractReadableExcel<T> {

	/**
	 * Sax xls 格式解析
	 */
	private final SaxXlsReadHandler<T> xlsReadHandler;

	/**
	 * Sax xlsx 格式解析
	 */
	private final SaxXlsxReadHandler<T> xlsxReadHandler;

	/**
	 * @param param
	 */
	public SimpleSaxReadableExcel(ReadParam<T> param) {
		this(param, new SimpleSaxXlsReadHandler<>(param), new SimpleSaxXlsxReadHandler<>(param));
	}

	/**
	 * @param param
	 * @param xlsReadHandler
	 * @param xlsxReadHandler
	 */
	public SimpleSaxReadableExcel(ReadParam<T> param, SaxXlsReadHandler<T> xlsReadHandler,
			SaxXlsxReadHandler<T> xlsxReadHandler) {
		super(param);
		this.xlsReadHandler = xlsReadHandler;
		this.xlsxReadHandler = xlsxReadHandler;
	}

	@Override
	protected ReadableHandler<T> createReadableHandler(InputStream in) throws IOException {
		try {
			if (FileMagic.valueOf(in) == FileMagic.OLE2) {
				return xlsReadHandler;
			} else if (FileMagic.valueOf(in) == FileMagic.OOXML) {
				return xlsxReadHandler;
			}
			throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
		} catch (Exception e) {
			throw new ReadableExcelException(e.getMessage(), e);
		}
	}

	@Override
	protected ReadableHandler<T> createReadableHandler(File file) throws IOException {
		String extension = Files.getFileExtension(file.getName());
		if (StringUtils.equalsAnyIgnoreCase("xls", extension)) {
			return xlsReadHandler;
		} else if (StringUtils.equalsAnyIgnoreCase("xlsx", extension)) {
			return xlsxReadHandler;
		}
		throw new IllegalArgumentException("Your File was neither a xls, nor a xlsx");
	}

	@Override
	protected ColumnProperty getColumnProperty() {
		return new AnnotationColumnProperty();
	}

}
