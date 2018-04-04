package com.hk.commons.poi.excel.exceptions;

import java.util.List;

import com.hk.commons.poi.excel.model.InvalidCell;

/**
 * 解析数据时出现的异常，记录每个单元格的错误信息
 * @author huangkai
 * @date 2017年9月11日下午5:42:52
 */
@SuppressWarnings("serial")
public class InvalidCellReadableExcelException extends ReadableExcelException {

	/**
	 * 失效的单元格数据
	 */
	private List<InvalidCell> invalidCells;

	/**
	 * 目标对象
	 */
	private Object target;

	public InvalidCellReadableExcelException(String message,Object target, List<InvalidCell> invalidCells) {
		super(message);
		this.target = target;
		this.invalidCells = invalidCells;
	}

	public List<InvalidCell> getInvalidCells() {
		return invalidCells;
	}

	public void setInvalidCells(List<InvalidCell> invalidCells) {
		this.invalidCells = invalidCells;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
