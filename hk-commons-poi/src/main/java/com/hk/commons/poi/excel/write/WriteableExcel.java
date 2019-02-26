package com.hk.commons.poi.excel.write;

import com.hk.commons.poi.excel.model.WriteParam;

import java.io.OutputStream;

/**
 * @author kevin
 */
public interface WriteableExcel<T> {

    /**
     * 
     * @param param 写出的参数
     * @param out out
     */
    void write(WriteParam<T> param, OutputStream out);

}
