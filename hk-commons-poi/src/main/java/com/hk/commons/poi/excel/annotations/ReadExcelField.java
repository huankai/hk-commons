package com.hk.commons.poi.excel.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 读取Excel 注解
 *
 * @author kevin
 * @date 2018年1月10日下午3:38:32
 */
@Retention(RUNTIME)
@Target({FIELD})
public @interface ReadExcelField {

    /**
     * 开始列
     *
     * @return int
     */
    int start();

    /**
     * 结束列
     *
     * @return int
     */
    int end() default -1;

}
