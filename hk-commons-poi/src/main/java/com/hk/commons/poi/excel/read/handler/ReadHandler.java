package com.hk.commons.poi.excel.read.handler;

import com.hk.commons.poi.excel.model.ReadResult;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author kevin
 * @date 2018年1月10日下午5:41:31
 */
public interface ReadHandler<T> {

    /**
     * 解析 inputStream
     *
     * @param in Excle文件
     * @return 解析后的结果
     */
    ReadResult<T> process(InputStream in) throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException;

    /**
     * 解析 Excel File
     *
     * @param file Excle 文件
     * @return 解析后的结果
     */
    ReadResult<T> process(File file) throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException;

}
