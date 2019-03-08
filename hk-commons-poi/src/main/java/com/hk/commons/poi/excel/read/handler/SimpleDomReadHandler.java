package com.hk.commons.poi.excel.read.handler;

import com.hk.commons.poi.excel.exception.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.SheetData;
import com.hk.commons.util.BeanWrapperUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用注解Dom解析
 *
 * @author kevin
 */
public class SimpleDomReadHandler<T> extends AbstractDomReadHandler<T> {

    public SimpleDomReadHandler(ReadParam<T> param) {
        super(param);
    }

    @Override
    public SheetData<T> processSheet(Sheet sheet, int sheetIndex) {
        final int lastRowNum = sheet.getLastRowNum();
        final String sheetName = WorkbookUtil.createSafeSheetName(sheet.getSheetName());
        List<ErrorLog<T>> errorLogs = new ArrayList<>();
        SheetData<T> dataSheet = new SheetData<>(sheetIndex, sheetName);
        BeanWrapper wrapper;
        for (int rowIndex = readParam.getDataStartRow(); rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (null != row) {
                try {
                    wrapper = BeanWrapperUtils.createBeanWrapper(readParam.getBeanClazz());
                    parseRow(row, wrapper);
                    dataSheet.add(readParam.getBeanClazz().cast(wrapper.getWrappedInstance()));
                } catch (InvalidCellReadableExcelException e) {
                    // 有错误的单元格数据，不能设置为属性值，记录日志
                    errorLogs.add(new ErrorLog<>(sheetName, row.getRowNum(), e.getTarget(), e.getInvalidCells()));
                }
            }
        }
        dataSheet.setErrorLogs(errorLogs);
        return dataSheet;
    }

    /**
     * 解析行
     *
     * @param row     row
     * @param wrapper wrapper
     */
    protected void parseRow(Row row, BeanWrapper wrapper) throws InvalidCellReadableExcelException {
        final int rowNum = row.getRowNum();
        final int maxColumnIndex = getMaxColumnIndex();
        List<InvalidCell> invalidCellList = new ArrayList<>();
        for (int columnIndex = 0; columnIndex <= maxColumnIndex; columnIndex++) {
            Cell cell = row.getCell(columnIndex, MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (null != cell) {
                final String value = getCellValueString(cell);
                try {
                    setWrapperBeanValue(wrapper, columnIndex, value);
                } catch (BeansException e) {
                    LOGGER.error(e.getMessage(), e);
                    invalidCellList.add(new InvalidCell(rowNum, columnIndex, value, getTitle(columnIndex), value + " 设置失败,原因:" + e.getMessage()));
                }
            }
        }
        if (!invalidCellList.isEmpty()) {
            throw new InvalidCellReadableExcelException("设置属性失败", wrapper.getWrappedInstance(), invalidCellList);
        }
    }

}
