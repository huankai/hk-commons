/**
 * 
 */
package com.hk.commons.poi.excel.read.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.hk.commons.poi.excel.exceptions.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.exceptions.ReadableExcelException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.poi.excel.model.ReadableParam;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.StringUtils;

/**
 * Sax (2007) 解析
 * 
 * @author huangkai
 *
 */
public class SimpleSaxXlsxReadHandler<T> extends AbstractSaxReadHandler<T> implements SaxXlsxReadHandler<T>,SheetContentsHandler {

	/**
	 * 是否为空行
	 */
	private boolean emptyRow = true;

	public SimpleSaxXlsxReadHandler(ReadableParam<T> param) {
		super(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.handler.ReadableHandler#process(java.io.File)
	 */
	@Override
	public ReadResult<T> process(File file)
			throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
		return processOPCPackage(OPCPackage.open(file, PackageAccess.READ));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hk.commons.poi.excel.read.handler.ReadableHandler#process(java.io.
	 * InputStream)
	 */
	@Override
	public ReadResult<T> process(InputStream in)
			throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
		return processOPCPackage(OPCPackage.open(in));
	}

	/**
	 * 解析工作薄
	 * 
	 * @param opcPackage
	 * @return {@link ReadResult}
	 * @throws IOException
	 * @throws SAXException
	 * @throws OpenXML4JException
	 */
	protected ReadResult<T> processOPCPackage(OPCPackage opcPackage)
			throws IOException, SAXException, OpenXML4JException {
		ReadResult<T> result = doProcessOPCPackage(opcPackage);
		validate(result);
		return result;
	}

	/**
	 * 解析工作薄
	 * 
	 * @param opcPackage
	 * @return {@link ReadResult}
	 * @throws IOException
	 * @throws SAXException
	 * @throws OpenXML4JException
	 */
	private ReadResult<T> doProcessOPCPackage(OPCPackage opcPackage)
			throws IOException, SAXException, OpenXML4JException {
		ReadResult<T> result = new ReadResult<T>();
		ReadOnlySharedStringsTable stringsTable = new ReadOnlySharedStringsTable(opcPackage);
		XSSFReader reader = new XSSFReader(opcPackage);
		SheetIterator sheetsData = (SheetIterator) reader.getSheetsData();
		for (int sheetIndex = 0; sheetsData.hasNext(); sheetIndex++) {
			InputStream inputStream = sheetsData.next();
			try {
				if (readParam.isParseAll() || (sheetIndex >= readParam.getSheetStartIndex()
						&& sheetIndex <= readParam.getSheetMaxIndex())) {
					cleanSheetData();
					getSheetData().setSheetIndex(sheetIndex);
					processSheet(reader.getStylesTable(), stringsTable, this, inputStream);
					if (CollectionUtils.isEmpty(result.getTitleList())) {
						result.setTitleList(titles);
					}
					getSheetData().setSheetName(sheetsData.getSheetName());
					List<ErrorLog<T>> errorLogs = getSheetData().getErrorLogs();
					if (CollectionUtils.isNotEmpty(errorLogs)) {
						errorLogs.forEach(item -> item.setSheetName(sheetsData.getSheetName()));
						result.addErrorLogList(errorLogs);
					}
					result.addSheetData(getSheetData());
				}
			} catch (ParserConfigurationException e) {
				throw new ReadableExcelException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		return result;
	}

	/**
	 * 解析每一个工作表
	 * 
	 * @param stylesTable
	 * @param stringsTable
	 * @param saxHandler
	 * @param inputStream
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private void processSheet(StylesTable stylesTable, ReadOnlySharedStringsTable stringsTable,
			SheetContentsHandler saxHandler, InputStream inputStream)
			throws SAXException, ParserConfigurationException, IOException {
		XMLReader xmlReader = SAXHelper.newXMLReader();
		ContentHandler handler = new XSSFSheetXMLHandler(stylesTable, null, stringsTable, saxHandler,
				new DataFormatter(), !readParam.isOutputFormulaValues());
		xmlReader.setContentHandler(handler);
		xmlReader.parse(new InputSource(inputStream));
	}

	/**
	 * 开始行
	 */
	@Override
	public void startRow(int rowNum) {
		emptyRow = true;
		clearRowValues();
	}

	/**
	 * 结束行
	 */
	@Override
	public void endRow(int rowNum) {
		if (isTitleRow(rowNum)) {// 标题行
			if (CollectionUtils.isEmpty(titles)) {
				parseTitleRow();
			}
		} else if (readParam.getDataStartRow() <= rowNum && !emptyRow) {// 数据行
			try {
				getSheetData().add(parseToData(rowNum));
			} catch (InvalidCellReadableExcelException e) {
				@SuppressWarnings("unchecked")
				ErrorLog<T> errorLog = new ErrorLog<>(null, rowNum, (T) e.getTarget(), e.getInvalidCells());
				getSheetData().addErrorLog(errorLog);
			}
		}
	}

	/**
	 * 单元格
	 */
	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (StringUtils.isNotBlank(formattedValue)) {
			emptyRow = false;
			CellReference reference = new CellReference(cellReference);
			int thisRow = reference.getRow();
			int thisCol = reference.getCol();
			if (thisRow < readParam.getTitleRow() || (thisRow != readParam.getTitleRow() && thisCol > getMaxColumnIndex())) {
				return; // 小于标题行的内容、大于标题行的最大列不解析
			}
			getRowColumnValues().add(new DefaultKeyValue<Integer, String>(thisCol, formattedValue));
		}
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {
		// Not Used
	}

}
