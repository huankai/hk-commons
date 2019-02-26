package com.hk.commons.poi.excel.write.handler;

import com.hk.commons.poi.excel.model.WriteParam;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * @author kevin
 */
public interface WriteableHandler<T> {

    /**
     * @param workbook workbook
     * @param param    param
     * @param out      out
     */
    void write(Workbook workbook, WriteParam<T> param, OutputStream out);

    /**
     * 返回工作表
     *
     * @return {@link Workbook}
     */
    Workbook getWorkBook();

}
