package com.hk.commons.poi.excel.read.handler;

import com.hk.commons.poi.excel.exception.ExcelReadException;
import com.hk.commons.poi.excel.exception.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.StringUtils;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
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

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Sax (2007) 解析
 *
 * @author kevin
 */
public class SimpleSaxXlsxReadHandler<T> extends AbstractSaxReadHandler<T> implements SaxXlsxReadHandler<T>, SheetContentsHandler {

    /**
     * 是否为空行
     */
    private boolean emptyRow = true;

    public SimpleSaxXlsxReadHandler(ReadParam<T> param) {
        super(param);
    }

    @Override
    public ReadResult<T> process(File file)
            throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
        return processOPCPackage(OPCPackage.open(file, PackageAccess.READ));
    }

    @Override
    public ReadResult<T> process(InputStream in)
            throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException {
        return processOPCPackage(OPCPackage.open(in));
    }

    /**
     * 解析工作薄
     *
     * @param opcPackage opcPackage
     * @return {@link ReadResult}
     * @throws IOException        IOException
     * @throws SAXException       SAXException
     * @throws OpenXML4JException OpenXML4JException
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
     * @param opcPackage opcPackage
     * @return {@link ReadResult}
     * @throws IOException        IOException
     * @throws SAXException       SAXException
     * @throws OpenXML4JException OpenXML4JException
     */
    private ReadResult<T> doProcessOPCPackage(OPCPackage opcPackage)
            throws IOException, SAXException, OpenXML4JException {
        ReadResult<T> result = new ReadResult<>();
        ReadOnlySharedStringsTable stringsTable = new ReadOnlySharedStringsTable(opcPackage);
        XSSFReader reader = new XSSFReader(opcPackage);
        SheetIterator sheetsData = (SheetIterator) reader.getSheetsData();
        try {
            for (int sheetIndex = 0; sheetsData.hasNext(); sheetIndex++) {
                if (readParam.isParseAll() || (sheetIndex >= readParam.getSheetStartIndex() && sheetIndex <= readParam.getSheetMaxIndex())) {
                    cleanSheetData();
                    getSheetData().setSheetIndex(sheetIndex);
                    processSheet(reader.getStylesTable(), stringsTable, this, sheetsData.next());
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
            }
        } catch (ParserConfigurationException e) {
            throw new ExcelReadException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 解析每一个工作表
     *
     * @param stylesTable  stylesTable
     * @param stringsTable stringsTable
     * @param saxHandler   saxHandler
     * @param inputStream  inputStream
     * @throws SAXException                 SAXException
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws IOException                  IOException
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
                ErrorLog<T> errorLog = new ErrorLog<>(null, rowNum, e.getTarget(), e.getInvalidCells());
                getSheetData().addErrorLog(errorLog);
            }
        }
    }

    /**
     * 单元格
     */
    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (StringUtils.isNotEmpty(formattedValue)) {
            emptyRow = false;
            CellReference reference = new CellReference(cellReference);
            int thisRow = reference.getRow();
            int thisCol = reference.getCol();
            if (thisRow < readParam.getTitleRow() || (thisRow != readParam.getTitleRow() && thisCol > getMaxColumnIndex())) {
                return; // 小于标题行的内容、大于标题行的最大列不解析
            }
            getRowColumnValues().add(new DefaultKeyValue<>(thisCol, formattedValue));
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        // Not Used
    }

}
