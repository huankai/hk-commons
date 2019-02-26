package com.hk.commons.poi.excel.write;


import com.hk.commons.poi.excel.write.handler.SimpleWriteableHandler;
import com.hk.commons.poi.excel.write.handler.WriteableHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author kevin
 */
public final class HSSFWriteableExcel<T> extends AbstractWriteableExcel<T> {

    public HSSFWriteableExcel() {
        super(new SimpleWriteableHandler<>());
    }

    public HSSFWriteableExcel(WriteableHandler<T> writeHandler) {
        super(writeHandler);
    }

    @Override
    protected Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

}
