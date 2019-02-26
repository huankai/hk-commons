package com.hk.commons.poi.excel.write;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hk.commons.poi.excel.write.handler.SimpleWriteableHandler;
import com.hk.commons.poi.excel.write.handler.WriteableHandler;

/**
 * @author kevin
 *
 */
public final class XSSFWriteableExcel<T> extends AbstractWriteableExcel<T> {

	public XSSFWriteableExcel() {
		super(new SimpleWriteableHandler<>());
	}

	public XSSFWriteableExcel(WriteableHandler<T> writeHandler) {
		super(writeHandler);
	}

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

}
