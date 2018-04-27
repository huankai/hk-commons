/**
 * 
 */
package com.hk.commons.poi.excel.read.handler;

import com.google.common.collect.Lists;
import com.hk.commons.poi.excel.exceptions.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.SheetData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;

import java.util.List;

/**
 * 使用注解Dom解析
 * 
 * @author huangkai
 *
 */
public class SimpleDomReadHandler<T> extends AbstractDomReadHandler<T> {

	public SimpleDomReadHandler(ReadParam<T> param) {
		super(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.handler.DomReadHandler#processSheet(org.apache.
	 * poi.ss.usermodel.Sheet, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public SheetData<T> processSheet(Sheet sheet, int sheetIndex) {
		final int lastRowNum = sheet.getLastRowNum();
		final String sheetName = WorkbookUtil.createSafeSheetName(sheet.getSheetName());
		List<ErrorLog<T>> errorLogs = Lists.newArrayList();
		SheetData<T> dataSheet = new SheetData<T>(sheetIndex,sheetName);
		for (int rowIndex = readParam.getDataStartRow(); rowIndex <= lastRowNum; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if(null != row) {
				try {
					T data = parseRow(row);
					dataSheet.add(data);
				}catch (InvalidCellReadableExcelException e) {
					// 有错误的单元格数据，不能设置为属性值，记录日志
					errorLogs.add(new ErrorLog<>(sheetName, row.getRowNum(),
							(T)e.getTarget(), e.getInvalidCells()));
				}
			}
		}
		dataSheet.setErrorLogs(errorLogs);
		return dataSheet;
	}

	/**
	 * 解析行
	 * @param row
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T parseRow(Row row) throws InvalidCellReadableExcelException {
		BeanWrapper wrapper = createParamBeanWrapper();
		final int rowNum = row.getRowNum();
		List<InvalidCell> invalidCells = Lists.newArrayList();
		for (int columnIndex = 0; columnIndex <= getMaxColumnIndex(); columnIndex++) {
			Cell cell = row.getCell(columnIndex, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if(null != cell) {
				final String value = new DataFormatter().formatCellValue(cell);
				try {
					setWrapperBeanValue(wrapper,columnIndex,-1,value);
				} catch (BeansException e) {
					LOGGER.error(e.getMessage(),e);
					invalidCells.add(new InvalidCell(rowNum,columnIndex,
							value,getTitle(columnIndex),value + " 设置失败"));
				}
			}
		}
		T target = (T) wrapper.getWrappedInstance();
		if(!invalidCells.isEmpty()) {
			throw new InvalidCellReadableExcelException("设置属性失败",target,invalidCells);
		}
		return target;
	}

}
