package com.hk.commons.poi.excel.style;

import org.apache.poi.ss.usermodel.Font;

/**
 * 样式
 *
 * @author kevin
 */
public interface StyleSheet {

    /**
     * 字体
     */
    enum Fonts {

        /**
         * 宋体
         */
        SONTTI("宋体"),

        /**
         * 新宋体
         */
        NEW_SONTTI("新宋体"),

        /**
         * Consolas
         */
        CONSOLAS("Consolas"),

        /**
         * Courier New
         */
        COURIER_NEW("Courier New"),

        /**
         * 黑体
         */
        HEITI("黑体"),

        /**
         * 微软雅黑
         */
        YAHEI("微软雅黑"),

        /**
         * 隶书
         */
        LISHU("隶书"),

        /**
         * 华文新魏
         */
        HUAWENXINWEI("华文新魏"),

        /**
         * 华文行楷
         */
        HUAWENXINGKAI("华文行楷");

        private String fontName;

        Fonts(String fontName) {
            this.fontName = fontName;
        }

        public String getFontName() {
            return fontName;
        }

    }

    /**
     * 无样式
     */
    short NONE_STYLE = 0xffffffff;

    /**
     * 下划线
     */

    enum UnderLineStyle {

        /**
         * 下划线 ：无
         */
        U_NONE(Font.U_NONE),

        /**
         * 下划线 ：单下划线与字体同宽
         */
        U_SINGLE(Font.U_SINGLE),

        /**
         * 下划线 :双下划线与字体同宽
         */
        U_DOUBLE(Font.U_DOUBLE),

        /**
         * 下划线 ：单下划线与单元格同宽
         */
        U_SINGLE_ACCOUNTING(Font.U_SINGLE_ACCOUNTING),

        /**
         * 下划线 :双下划线与单元格同宽
         */
        U_DOUBLE_ACCOUNTING(Font.U_DOUBLE_ACCOUNTING);

        private byte value;

        UnderLineStyle(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }

}
