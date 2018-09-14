package com.hk.commons.poi.excel.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 读取Excel 注解
 * 
 * @author: kevin
 * @date: 2018年1月10日下午3:38:32
 */
@Retention(RUNTIME)
@Target({ FIELD })
public @interface ReadExcel {

	/**
	 * 开始列
	 * 
	 * @return
	 */
	int start();

	/**
	 * 结束列
	 * 
	 * @return
	 */
	int end() default -1;

}
