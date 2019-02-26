package com.hk.commons.poi.excel.write;

import com.hk.commons.poi.excel.model.WriteParam;
import com.hk.commons.poi.excel.write.handler.WriteableHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * @author kevin
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractWriteableExcel<T> implements WriteableExcel<T> {

    /**
     * 写入到Excel处理器
     */
    private final WriteableHandler<T> writeHandler;

    @Override
    public void write(WriteParam<T> param, OutputStream out) {
        writeHandler.write(createWorkbook(), param, out);
    }

    protected abstract Workbook createWorkbook();

}
