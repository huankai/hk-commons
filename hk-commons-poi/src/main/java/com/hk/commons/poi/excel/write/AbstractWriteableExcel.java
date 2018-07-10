/**
 * 
 */
package com.hk.commons.poi.excel.write;

import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;

import com.hk.commons.poi.excel.model.WriteParam;
import com.hk.commons.poi.excel.write.handler.WriteableHandler;

/**
 * @author: kevin
 *
 */
public abstract class AbstractWriteableExcel<T> implements WriteableExcel<T> {

	/**
	 * 写入到Excel处理器
	 */
	private final WriteableHandler<T> writeableHandler;

	protected AbstractWriteableExcel(WriteableHandler<T> writeableHandler) {
		this.writeableHandler = writeableHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.write.WriteableExcel#write(com.hk.commons.poi.excel.
	 * model.WriteParam, java.io.OutputStream)
	 */
	@Override
	public void write(WriteParam<T> param, OutputStream out) {
		writeableHandler.write(createWorkbook(), param, out);
	}

	protected abstract Workbook createWorkbook();

}
