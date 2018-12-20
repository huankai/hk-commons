package com.hk.commons.poi.excel.annotations;

import com.hk.commons.poi.excel.style.StyleSheet;
import com.hk.commons.poi.excel.style.StyleSheet.Fonts;
import com.hk.commons.poi.excel.style.StyleSheet.UnderLineStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 样式
 *
 * @author kevin
 * @date 2017年9月15日下午5:43:37
 */
@Target({FIELD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface CellStyle {

    /* ******************************************背        景*********************************************************  */

    /**
     * 背景颜色
     *
     * @return short
     */
    short backgroundColor() default StyleSheet.NONE_STYLE;

    /**
     * 前景颜色
     *
     * @return short
     */
    short fillForegroundColor() default StyleSheet.NONE_STYLE;

    /**
     * 颜色填充类型
     *
     * @return {@link FillPatternType}
     */
    FillPatternType fillPatternType() default FillPatternType.NO_FILL;

    /* ******************************************对 齐 方 式 *********************************************************  */

    /**
     * 垂直方向对齐方式
     *
     * @return {@link VerticalAlignment}
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    /**
     * 水平方向对齐方式
     *
     * @return {@link HorizontalAlignment}
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.CENTER;

    /* ******************************************边    框*********************************************************  */

    /**
     * 上下左右边框，优先级低
     *
     * @return {@link BorderStyle}
     */
    BorderStyle border() default BorderStyle.NONE;

    /**
     * 上下左右边框颜色，优先级低
     *
     * @return short
     */
    short borderColor() default StyleSheet.NONE_STYLE;

    /**
     * 上边框
     *
     * @return {@link BorderStyle}
     */
    BorderStyle borderTop() default BorderStyle.NONE;

    /**
     * 上边框颜色
     *
     * @return short
     */
    short borderTopColor() default StyleSheet.NONE_STYLE;

    /**
     * 左边框
     *
     * @return {@link BorderStyle}
     */
    BorderStyle borderLeft() default BorderStyle.NONE;

    /**
     * 左边框颜色
     *
     * @return short
     */
    short borderLeftColor() default StyleSheet.NONE_STYLE;

    /**
     * 右边框
     *
     * @return {@link BorderStyle}
     */
    BorderStyle borderRight() default BorderStyle.NONE;

    /**
     * 右边框颜色
     *
     * @return short
     */
    short borderRightColor() default StyleSheet.NONE_STYLE;

    /**
     * 下边框
     *
     * @return {@link BorderStyle}
     */
    BorderStyle borderBottom() default BorderStyle.NONE;

    /**
     * 下边框颜色
     *
     * @return short
     */
    short borderBottomColor() default StyleSheet.NONE_STYLE;

    /* ******************************************字    体*********************************************************  */

    /**
     * 自动换行
     *
     * @return boolean
     */
    boolean autoWrap() default false;

    /**
     * 设置cell所引用的样式是否锁住
     *
     * @return boolean
     */
    boolean locked() default false;

    /**
     * 字体名称
     *
     * @return {@link Fonts}
     */
    Fonts fontName() default Fonts.SONTTI;

    /**
     * 字体大小
     *
     * @return short
     */
    short fontSize() default 12;

    /**
     * 字体颜色
     *
     * @return short
     */
    short fontColor() default StyleSheet.NONE_STYLE;

    /**
     * 是否斜体
     *
     * @return boolean
     */
    boolean italic() default false;

    /**
     * 是否粗体
     *
     * @return boolean
     */
    boolean bold() default false;

    /**
     * 是否加删除线
     *
     * @return boolean
     */
    boolean strikeout() default false;

    /**
     * 下划线，默认无下划线
     *
     * @return {@link UnderLineStyle}
     */
    UnderLineStyle underline() default UnderLineStyle.U_NONE;

}
