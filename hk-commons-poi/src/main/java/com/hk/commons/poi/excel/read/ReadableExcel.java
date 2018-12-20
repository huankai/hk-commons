package com.hk.commons.poi.excel.read;

import com.hk.commons.poi.excel.model.ReadResult;

import java.io.File;
import java.io.InputStream;

/**
 * 解析Excel
 *
 * @author kevin
 * @date 2018年1月10日下午3:41:13
 */
public interface ReadableExcel<T> {

    /**
     * xls 扩展名
     */
    String XLS_EXTENSION = "xls";

    /**
     * xlsx 扩展名
     */
    String XLSX_EXTENSION = "xlsx";

    /**
     * 从文件中读取
     *
     * @param file Excel文件
     * @return {@link ReadResult}
     */
    ReadResult<T> read(File file);

    /**
     * 从文件流中读取
     *
     * @param in Excel文件流
     * @return {@link ReadResult}
     */
    ReadResult<T> read(InputStream in);

}
