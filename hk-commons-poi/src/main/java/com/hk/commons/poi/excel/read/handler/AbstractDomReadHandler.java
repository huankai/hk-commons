/**
 * 
 */
package com.hk.commons.poi.excel.read.handler;

import com.google.common.collect.Lists;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.poi.excel.model.SheetData;
import com.hk.commons.poi.excel.model.Title;
import com.hk.commons.util.CollectionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author huangkai
 *
 */
public abstract class AbstractDomReadHandler<T> extends AbstractReadHandler<T> implements DomReadHandler<T> {

	/**
	 * 工作薄对象
	 */
	protected Workbook workbook;

	protected AbstractDomReadHandler(ReadParam<T> param) {
		super(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.handler.ReadableHandler#process(org.apache.poi.
	 * poifs.filesystem.NPOIFSFileSystem)
	 */
	// @Override
	// public final ReadResult<T> process(NPOIFSFileSystem fs) throws IOException {
	// workbook = WorkbookFactory.create(fs);
	// sheetParseInit();
	// return processWorkbook();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hk.commons.poi.excel.read.handler.ReadableHandler#process(java.io.
	 * InputStream)
	 */
	@Override
	public final ReadResult<T> process(InputStream in)
			throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
		workbook = WorkbookFactory.create(in);
		sheetParseInit();
		return processWorkbook();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.handler.ReadableHandler#process(java.io.File)
	 */
	@Override
	public final ReadResult<T> process(File file)
			throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
		workbook = WorkbookFactory.create(file);
		sheetParseInit();
		return processWorkbook();
	}

	/**
	 * 解析工作薄并验证
	 * 
	 * @return
	 */
	protected ReadResult<T> processWorkbook() {
		ReadResult<T> result = doProcessWorkbook();
		validate(result);
		return result;
	}

	/**
	 * 解析工作薄
	 * 
	 * @return
	 */
	protected final ReadResult<T> doProcessWorkbook() {
		ReadResult<T> result = new ReadResult<T>();
		for (int index = readParam.getSheetStartIndex(); index <= readParam.getSheetMaxIndex(); index++) {
			final Sheet sheet = workbook.getSheetAt(index);
			if (null != sheet) {
				if (CollectionUtils.isEmpty(result.getTitleList())) {
					List<Title> titles = parseTitleRow(sheet.getRow(readParam.getTitleRow()));
					result.setTitleList(titles);
				}
				SheetData<T> sheetData = processSheet(sheet, index);
				result.addSheetData(sheetData);
				if (CollectionUtils.isNotEmpty(sheetData.getErrorLogs())) {
					result.addErrorLogList(sheetData.getErrorLogs());
				}
			}
		}
		return result;
	}

	/**
	 * 返回工作表
	 * 
	 * @return
	 */
	public final Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * 工作表解析初始化设置
	 * 
	 * @param workbook
	 */
	private void sheetParseInit() {
		int maxSheetsIndex = workbook.getNumberOfSheets() - 1;
		if (readParam.isParseAll()) {
			readParam.setSheetStartIndex(0);
			readParam.setSheetMaxIndex(maxSheetsIndex);
		} else {
			readParam.setSheetMaxIndex(Math.min(readParam.getSheetMaxIndex(), maxSheetsIndex));
		}
	}

	/**
	 * 解析标题行
	 * 
	 * @param titleRow
	 * @return
	 */
	protected final List<Title> parseTitleRow(Row titleRow) {
		List<Title> titleList = Lists.newArrayListWithExpectedSize(titleRow.getLastCellNum());
		titleRow.forEach(
				cell -> titleList.add(new Title(cell, readParam.getColumnProperties().get(cell.getColumnIndex()))));
		setTitles(titleList);
		return titleList;
	}

	/**
	 * 是否为合并单元格
	 * 
	 * @param cell
	 * @return
	 */
	protected final boolean isMerageCell(Cell cell) {
		List<CellRangeAddress> mergedRegions = cell.getSheet().getMergedRegions();
		for (CellRangeAddress cellRangeAddress : mergedRegions) {
			if ((cellRangeAddress.getFirstColumn() <= cell.getColumnIndex()
					&& cellRangeAddress.getLastColumn() >= cell.getColumnIndex())
					|| (cellRangeAddress.getFirstRow() <= cell.getRowIndex()
							&& cellRangeAddress.getLastRow() >= cell.getRowIndex())) {
				return true;
			}
		}
		return false;
	}

}
