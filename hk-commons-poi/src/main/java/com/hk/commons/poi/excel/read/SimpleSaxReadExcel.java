package com.hk.commons.poi.excel.read;

import com.google.common.io.Files;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.read.handler.*;
import com.hk.commons.util.StringUtils;
import org.apache.poi.poifs.filesystem.FileMagic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Sax 解析Excel
 *
 * @author huangkai
 */
public class SimpleSaxReadExcel<T> extends AbstractReadExcel<T> {

    /**
     * Sax xls 格式解析
     */
    private final SaxXlsReadHandler<T> xlsReadHandler;

    /**
     * Sax xlsx 格式解析
     */
    private final SaxXlsxReadHandler<T> xlsxReadHandler;

    /**
     * @param param
     */
    public SimpleSaxReadExcel(ReadParam<T> param) {
        this(param, new SimpleSaxXlsReadHandler<>(param), new SimpleSaxXlsxReadHandler<>(param));
    }

    /**
     * @param param
     * @param xlsReadHandler
     * @param xlsxReadHandler
     */
    public SimpleSaxReadExcel(ReadParam<T> param, SaxXlsReadHandler<T> xlsReadHandler,
                              SaxXlsxReadHandler<T> xlsxReadHandler) {
        super(param);
        this.xlsReadHandler = xlsReadHandler;
        this.xlsxReadHandler = xlsxReadHandler;
    }

    @Override
    protected ReadHandler<T> createReadableHandler(InputStream in) throws IOException {
        if (FileMagic.valueOf(in) == FileMagic.OLE2) { // xls 格式解析
            return xlsReadHandler;
        } else if (FileMagic.valueOf(in) == FileMagic.OOXML) { // xlsx 格式解析
            return xlsxReadHandler;
        }
        throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }

    @Override
    protected ReadHandler<T> createReadableHandler(File file) throws IOException {
        String extension = Files.getFileExtension(file.getName()).toLowerCase(Locale.ENGLISH);
        if (StringUtils.equals(XLS_EXTENSION, extension)) {
            return xlsReadHandler;
        } else if (StringUtils.equals(XLSX_EXTENSION, extension)) {
            return xlsxReadHandler;
        }
        throw new IllegalArgumentException("Your File was neither a xls, nor a xlsx");
    }

    @Override
    protected ColumnProperty getColumnProperty() {
        return new AnnotationColumnProperty();
    }

}
