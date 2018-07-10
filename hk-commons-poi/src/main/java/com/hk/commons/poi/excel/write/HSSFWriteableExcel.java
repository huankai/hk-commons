/**
 * 
 */
package com.hk.commons.poi.excel.write;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.hk.commons.poi.excel.write.handler.SimpleWriteableHandler;
import com.hk.commons.poi.excel.write.handler.WriteableHandler;

/**
 * @author: kevin
 *
 */
public final class HSSFWriteableExcel<T> extends AbstractWriteableExcel<T> {
	
	public HSSFWriteableExcel() {
		super(new SimpleWriteableHandler<>());
	}

	public HSSFWriteableExcel(WriteableHandler<T> writeableHandler) {
		super(writeableHandler);
	}

	@Override
	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}

}
