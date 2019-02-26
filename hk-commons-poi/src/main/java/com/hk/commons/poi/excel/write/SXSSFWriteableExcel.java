package com.hk.commons.poi.excel.write;

import com.hk.commons.poi.excel.write.handler.SimpleWriteableHandler;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hk.commons.poi.excel.write.handler.WriteableHandler;

/**
 * 比 XSSFWriteableExcel 效率高
 *
 * @author kevin
 */
public final class SXSSFWriteableExcel<T> extends AbstractWriteableExcel<T> {

    /**
     * <pre>
     * 在内存中保留100行，超过行将刷新到磁盘
     * 如果rowAccessWindowSize <= 0 ,关闭自动刷新并累积内存中的所有行
     * </pre>
     */
    @Getter
    @Setter
    private int rowAccessWindowSize = SXSSFWorkbook.DEFAULT_WINDOW_SIZE;

    /**
     * <pre>
     * 如果是全新的工作表，此参数可以不设置，
     * 如果是在一个已存在的工作表中添加新的工作表，请设置此参数，也可将行追加到现有的工作表
     * </pre>
     */
    @Getter
    @Setter
    private XSSFWorkbook xssfWorkbook;

    /**
     * <pre>
     * 是否压缩临时文件，sxssf写表数据时会写入到临时文件中，这些临时文件大小可能会变成很大
     * 如果设置为true,会使用 gzip压缩文件，
     * 如果设置为true,会有性能上的损失。
     * </pre>
     */
    @Getter
    @Setter
    private boolean compressTmpFiles;

    /**
     *
     */
    @Getter
    @Setter
    private boolean useSharedStringsTable;

    public SXSSFWriteableExcel() {
        this(new SimpleWriteableHandler<>());
    }

    public SXSSFWriteableExcel(WriteableHandler<T> writeHandler) {
        super(writeHandler);
    }

    public SXSSFWriteableExcel(int rowAccessWindowSize) {
        this(rowAccessWindowSize, new SimpleWriteableHandler<>());
    }

    public SXSSFWriteableExcel(int rowAccessWindowSize, WriteableHandler<T> writeableHandler) {
        super(writeableHandler);
        this.rowAccessWindowSize = rowAccessWindowSize;
    }

    @Override
    protected Workbook createWorkbook() {
        return new SXSSFWorkbook(xssfWorkbook, rowAccessWindowSize, compressTmpFiles, useSharedStringsTable);
    }

}
