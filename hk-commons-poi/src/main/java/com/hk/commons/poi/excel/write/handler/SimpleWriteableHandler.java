/**
 * 
 */
package com.hk.commons.poi.excel.write.handler;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;

import com.hk.commons.util.StringUtils;

/**
 * 写入单个工作表数据
 * 
 * @author: kevin
 *
 */
public class SimpleWriteableHandler<T> extends AbstractWriteableHandler<T> {

	@Override
	protected void writeWrokbook() {
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(params.getSheetName()));
		if (StringUtils.isNotBlank(params.getPassword())) {
			sheet.protectSheet(params.getPassword());
		}
		sheet.setDisplayGridlines(params.isDisplayGridLines());
		writeSheet(sheet);

	}

	protected void writeSheet(Sheet sheet) {
		createTitleRow(sheet);
		createDataRows(sheet, params.getData());
	}

}
