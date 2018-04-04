/**
 * 
 */
package com.hk.commons.poi.excel.read.validation;

import java.util.List;

import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.Title;

/**
 * Excel 解析验证数据接口
 * 
 * @author kally
 * @date 2018年1月10日下午5:21:46
 */
public interface Validationable<T> {

	/**
	 * 
	 * @param obj
	 *            验证对象
	 * @param rowNumber
	 *            验证对象所在行
	 * @param titleList
	 *            每一列都应该有一个对应的标题
	 * @return 验证错误的列的与错误消息
	 */
	List<InvalidCell> validate(T obj, int rowNumber,List<Title> titleList);

	/**
	 * 当此验证有错误信息时，下一个验证是否继续
	 * 
	 * @return
	 */
	default boolean errorNext() {
		return false;
	};

}
