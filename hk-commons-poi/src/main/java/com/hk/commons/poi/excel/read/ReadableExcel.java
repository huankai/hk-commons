/**
 *
 */
package com.hk.commons.poi.excel.read;

import com.hk.commons.poi.excel.model.ReadResult;

import java.io.File;
import java.io.InputStream;

/**
 * @author: kevin
 * @date 2018年1月10日下午3:41:13
 */
public interface ReadableExcel<T> {

    /**
     * xls 后缀
     */
    String XLS_EXTENSION = "xls";

    /**
     * xlsx 后缀
     */
    String XLSX_EXTENSION = "xlsx";

    /**
     * 从文件中读取
     *
     * @param file  Excel文件
     * @return
     */
    ReadResult<T> read(File file);

    /**
     * 从文件流中读取
     *
     * @param in    Excel文件流
     * @return
     */
    ReadResult<T> read(InputStream in);

}
