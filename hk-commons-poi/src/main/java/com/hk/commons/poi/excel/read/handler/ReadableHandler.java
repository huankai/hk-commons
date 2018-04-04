/**
 * 
 */
package com.hk.commons.poi.excel.read.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import com.hk.commons.poi.excel.model.ReadResult;

/**
 * @author kally
 * @date 2018年1月10日下午5:41:31
 */
public interface ReadableHandler<T> {

	/**
	 * 解析
	 * 
	 * @param in
	 *            Excle文件
	 * @return
	 */
	ReadResult<T> process(InputStream in)
			throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException;

	/**
	 * 解析
	 * 
	 * @param file
	 *            Excle文件
	 * @return
	 */
	ReadResult<T> process(File file) throws IOException, EncryptedDocumentException, SAXException, OpenXML4JException;

}
