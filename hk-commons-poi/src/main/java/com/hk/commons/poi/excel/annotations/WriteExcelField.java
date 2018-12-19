package com.hk.commons.poi.excel.annotations;

import org.apache.poi.ss.usermodel.BorderStyle;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author kevin
 */
@Retention(RUNTIME)
@Target({FIELD, METHOD})
@Documented
public @interface WriteExcelField {

    /**
     * 导出列
     *
     * @return int
     */
    int index();

    /**
     * 导出的标题名称
     *
     * @return String
     */
    String value();

    /**
     * 宽度
     *
     * @return int
     */
    int width() default 20;

    /**
     * 是否在导出时统计，只有导出的单元格类型为NUMERIC并且不为日期类型才会统计
     *
     * @return boolean
     */
    boolean isStatistics() default false;

    /**
     * 如果有注解，注解作者名称
     *
     * @return String
     */
    String author() default "";

    /**
     * 如果有注解，注解是否可见，默认隐藏，需要鼠标移动到指定的区域才可见
     *
     * @return boolean
     */
    boolean visible() default false;

    /**
     * 标题行样式
     *
     * @return {@link CellStyle}
     */
    CellStyle titleStyle() default @CellStyle(bold = true, fontSize = 18, border = BorderStyle.THIN);

    /**
     * 数据行样式
     *
     * @return {@link CellStyle}
     */
    CellStyle dataStyle() default @CellStyle(border = BorderStyle.THIN);

}
