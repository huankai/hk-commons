package com.hk.commons.poi.excel.read.handler;

import com.hk.commons.poi.excel.exception.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.poi.excel.model.SheetData;
import com.hk.commons.util.CollectionUtils;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Sax parse 97~2003
 *
 * @author kevin
 */
public class SimpleSaxXlsReadHandler<T> extends AbstractSaxReadHandler<T> implements SaxXlsReadHandler<T>, HSSFListener {

    /**
     * 解析公式
     */
    private SheetRecordCollectingListener workbookBuildingListener;

    /**
     * 工作簿
     */
    private HSSFWorkbook stubWorkbook;

    /**
     * 保存所有字符串内容
     */
    private SSTRecord sstRecord;

    /**
     *
     */
    private FormatTrackingHSSFListener formatListener;

    /**
     * 所有工作表信息
     */
    private List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

    /**
     * 排序后的工作表
     */
    private BoundSheetRecord[] orderedBSRs;

    private int nextColumn;

    private boolean outputNextStringRecord;

    /**
     * 当前工作表索引
     */
    private int currentSheetIndex = -1;

    /**
     * 是否为工作表
     */
    private boolean isSheet;

    /**
     * 返回数据
     */
    private ReadResult<T> result = new ReadResult<>();

    public SimpleSaxXlsReadHandler(ReadParam<T> param) {
        super(param);
    }

    @Override
    public ReadResult<T> process(InputStream in)
            throws IOException, EncryptedDocumentException {
        return parseFileSystem(new POIFSFileSystem(in));
    }

    @Override
    public ReadResult<T> process(File file) throws IOException, EncryptedDocumentException {
        return parseFileSystem(new POIFSFileSystem(file, true));
    }

    protected ReadResult<T> parseFileSystem(POIFSFileSystem fs) throws IOException {
        doParseFileSystem(fs);
        validate(result);
        return result;
    }

    private void doParseFileSystem(POIFSFileSystem fs) throws IOException {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();// 处理xls文件流,按流循环处理每个Record
        HSSFRequest request = new HSSFRequest();
        if (readParam.isOutputFormulaValues()) {
            request.addListenerForAllRecords(formatListener);// 为所有Record 注册监听器，会影响一些性能
            // request.addListener(formatListener, BOFRecord.sid);
            // request.addListener(formatListener, BoundSheetRecord.sid);
            //// request.addListener(formatListener, RowRecord.sid);
            // request.addListener(formatListener, SSTRecord.sid);
            // request.addListener(formatListener, NumberRecord.sid);
            // request.addListener(formatListener, LabelSSTRecord.sid);
            // request.addListener(formatListener, BlankRecord.sid);
            // request.addListener(formatListener, BoolErrRecord.sid);
            // request.addListener(formatListener, FormulaRecord.sid);
            // request.addListener(formatListener, StringRecord.sid);
            // request.addListener(formatListener, LabelRecord.sid);
            // request.addListener(formatListener, EOFRecord.sid);
            // request.addListener(formatListener, ExtendedFormatRecord.sid);
            // request.addListener(formatListener, NoteRecord.sid);
            // request.addListener(formatListener, RKRecord.sid);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
            // request.addListener(workbookBuildingListener, BOFRecord.sid);
            // request.addListener(workbookBuildingListener, BoundSheetRecord.sid);
            //// request.addListener(workbookBuildingListener, RowRecord.sid);
            // request.addListener(workbookBuildingListener, SSTRecord.sid);
            // request.addListener(workbookBuildingListener, NumberRecord.sid);
            // request.addListener(workbookBuildingListener, LabelSSTRecord.sid);
            // request.addListener(workbookBuildingListener, BlankRecord.sid);
            // request.addListener(workbookBuildingListener, BoolErrRecord.sid);
            // request.addListener(workbookBuildingListener, FormulaRecord.sid);
            // request.addListener(workbookBuildingListener, StringRecord.sid);
            // request.addListener(workbookBuildingListener, LabelRecord.sid);
            // request.addListener(workbookBuildingListener, EOFRecord.sid);
            // request.addListener(workbookBuildingListener, ExtendedFormatRecord.sid);
        }
        factory.processWorkbookEvents(request, fs);
    }

    @Override
    public void processRecord(Record record) {
        if (record.getSid() == BOFRecord.sid) { // 一个文件的开始,表示sheet或workbook的开始
            BOFRecord br = (BOFRecord) record;
            isSheet = br.getType() == BOFRecord.TYPE_WORKSHEET;
            if (isSheet) { //表示为工作表
                // Create sub workbook if required
                if (workbookBuildingListener != null && stubWorkbook == null) {
                    stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                }
                // Output the worksheet name
                // Works by ordering the BSRs by the location of
                // their BOFRecords, and then knowing that we
                // process BOFRecords in byte offset order
                // curSheet++;
                currentSheetIndex++;
                if (null == orderedBSRs) {
                    orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                }
            }
            return;
        }
        if (currentSheetIndex == -1 || readParam.isParseAll() || (currentSheetIndex >= readParam.getSheetStartIndex()
                && currentSheetIndex <= readParam.getSheetMaxIndex())) {
            int thisColumn = -1;
            String value = null;
            switch (record.getSid()) {
                case BoundSheetRecord.sid: // 存储了每个sheet的sheet名和每个sheet在文件流中的起始位置
                    BoundSheetRecord boundSheetRecord = (BoundSheetRecord) record;
                    boundSheetRecords.add(boundSheetRecord);
                    break;
                // case RowRecord.sid://存储了sheet中一行的信息
                // RowRecord rowRecord = (RowRecord) record;
                // System.out.println("row :" + rowRecord.getRowNumber());
                // break;
                case NumberRecord.sid: // 单元格为数字类型，也可能是日期
                    NumberRecord numrec = (NumberRecord) record;
                    thisColumn = numrec.getColumn();
                    value = formatListener.formatNumberDateCell(numrec);
                    break;
                case SSTRecord.sid: // 存储了在Excel中文本单元格中的文本值，文本单元格通过索引获取文本值
                    sstRecord = (SSTRecord) record;
                    // for (int k = 0; k < sstRecord.getNumUniqueStrings(); k++) {
                    // System.out.println("String table value " + k + " = " +
                    // sstRecord.getString(k));
                    // }
                    break;
                case LabelSSTRecord.sid: // 引用了SSTRecord中一个String类型的单元格值
                    LabelSSTRecord lsrec = (LabelSSTRecord) record;
                    thisColumn = lsrec.getColumn();
                    if (null != sstRecord) {
                        value = sstRecord.getString(lsrec.getSSTIndex()).getString();
                    }
                    break;
                // case DimensionsRecord.sid : //存储了一个sheet的行列范围
                // break;
                // case MergeCellsRecord.sid: //存储了一个sheet中的合并单元格
                // MergeCellsRecord mergeCellsRecord = (MergeCellsRecord) record;
                // break;
                case BlankRecord.sid: // 空白单元格，没有值，但单元格有样式
                    BlankRecord brec = (BlankRecord) record;
                    thisColumn = brec.getColumn();
                    break;
                case BoolErrRecord.sid:// 单元格为布尔类型或错误类型
                    BoolErrRecord berec = (BoolErrRecord) record;
                    thisColumn = berec.getColumn();
                    value = String.valueOf(!berec.isError() && berec.getBooleanValue());
                    break;
                case FormulaRecord.sid: // 单元格为公式类型
                    FormulaRecord frec = (FormulaRecord) record;
                    thisColumn = frec.getColumn();
                    if (readParam.isOutputFormulaValues()) {
                        if (Double.isNaN(frec.getValue())) {
                            // Formula result is a string
                            // This is stored in the next record
                            outputNextStringRecord = true;
                            nextColumn = frec.getColumn();
                        } else {
                            value = formatListener.formatNumberDateCell(frec);
                        }
                    } else {
                        value = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
                    }
                    break;
                case StringRecord.sid:// 存储文本公式的缓存结果
                    if (outputNextStringRecord) {
                        // String for formula
                        StringRecord srec = (StringRecord) record;
                        value = srec.getString();
                        thisColumn = nextColumn;
                        outputNextStringRecord = false;
                    }
                    break;
                case LabelRecord.sid: // 只读，支持读取直接存储在单元格中的字符串，而不是存储在SSTRecord中，除了读取不要使用LabelRecord，应该使用SSTRecord替代
                    LabelRecord lrec = (LabelRecord) record;
                    thisColumn = lrec.getColumn();
                    value = lrec.getValue();
                    break;
                // case NoteRecord.sid:
                // NoteRecord nrec = (NoteRecord) record;
                // thisColumn = nrec.getColumn();
                // thisStr = "";
                // break;
                // case RKRecord.sid:
                // RKRecord rkrec = (RKRecord) record;
                // thisColumn = rkrec.getColumn();
                // thisStr = "";
                // break;
                // case ExtendedFormatRecord.sid:
                // ExtendedFormatRecord extendedFormatRecord = (ExtendedFormatRecord) record;
                // System.out.println(extendedFormatRecord.getWrapText());
                case EOFRecord.sid:// 表示Workbook或一个sheet的结尾
                    if (isSheet) {
                        BoundSheetRecord sheetRecord = orderedBSRs[currentSheetIndex];
                        SheetData<T> dataSheet = getSheetData();
                        dataSheet.setSheetIndex(currentSheetIndex);
                        dataSheet.setSheetName(sheetRecord.getSheetname());
                        result.addSheetData(dataSheet);
                        if (CollectionUtils.isNotEmpty(dataSheet.getErrorLogs())) {
                            result.addErrorLogList(dataSheet.getErrorLogs());
                        }
                        cleanSheetData();
                    }
                    return;
                default:
                    break;
            }
            int maxColumn = getMaxColumnIndex();
            // 超出标题列的单元格内容过滤
            if (maxColumn > 0 && thisColumn > maxColumn) {
                return;
            }
            // Handle missing column 空值的操作
            if (record instanceof MissingCellDummyRecord) {
                MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
                thisColumn = mc.getColumn();
                value = null;
            }

            if (null != value) {
                getRowColumnValues().add(new DefaultKeyValue<>(thisColumn, value));
            }
            // Handle end of row 行结束时的操作
            if (record instanceof LastCellOfRowDummyRecord) {
                LastCellOfRowDummyRecord cellOfRowDummyRecord = (LastCellOfRowDummyRecord) record;
                int rowNum = cellOfRowDummyRecord.getRow();
                if (rowNum >= readParam.getTitleRow()) { // 只有标题行及以下的行数据才解析
                    if (isTitleRow(rowNum) && CollectionUtils.isEmpty(result.getTitleList())) { // 如果是标题行
                        parseTitleRow();
                        result.setTitleList(titles);
                    } else {
                        try {
                            getSheetData().add(parseToData(rowNum));
                        } catch (InvalidCellReadableExcelException e) {
                            BoundSheetRecord sheetRecord = orderedBSRs[currentSheetIndex];
                            getSheetData().addErrorLog(new ErrorLog<>(sheetRecord.getSheetname(), rowNum, e.getTarget(), e.getInvalidCells()));
                        }
                    }
                }
                clearRowValues();
            }
        }
    }

}
