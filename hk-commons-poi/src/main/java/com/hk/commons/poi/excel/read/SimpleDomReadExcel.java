package com.hk.commons.poi.excel.read;

import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.read.handler.DomReadHandler;
import com.hk.commons.poi.excel.read.handler.ReadHandler;
import com.hk.commons.poi.excel.read.handler.SimpleDomReadHandler;

import java.io.File;
import java.io.InputStream;

/**
 * @author: kevin
 */
public class SimpleDomReadExcel<T> extends AbstractReadExcel<T> {

    /**
     * 使用Dom解析的处理器
     */
    private final DomReadHandler<T> handler;

    /**
     * @param readParam
     */
    public SimpleDomReadExcel(ReadParam<T> readParam) {
        this(readParam, new SimpleDomReadHandler<>(readParam));
    }

    /**
     * @param readParam
     * @param handler
     */
    public SimpleDomReadExcel(ReadParam<T> readParam, DomReadHandler<T> handler) {
        super(readParam);
        this.handler = handler;
    }


    @Override
    protected ReadHandler<T> createReadableHandler(InputStream in) {
        return handler;
    }


    @Override
    protected ReadHandler<T> createReadableHandler(File file) {
        return handler;
    }

    @Override
    protected ColumnProperty getColumnProperty() {
        return new AnnotationColumnProperty();
    }

}
