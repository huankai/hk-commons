package com.hk.commons.poi.excel.style;

import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.hk.commons.poi.excel.model.DataFormat;
import com.hk.commons.poi.excel.util.CellStyleBuilder;

/**
 * @author kevin
 */
@Data
public class CustomCellStyle implements StyleSheet {

    /**
     * 背景颜色
     */
    protected short backgroundColor;

    /**
     * 前景颜色
     */
    protected short fillForegroundColor;

    /**
     * 背景颜色填充类型
     */
    private FillPatternType fillPatternType;

    /**
     * 垂直方向对齐方式
     */
    protected VerticalAlignment verticalAlignment;

    /**
     * 水平方向对齐方式
     */
    protected HorizontalAlignment horizontalAlignment;

    /**
     * 上边框
     */

    protected BorderStyle borderTop;
    /**
     * 上边框颜色
     */
    protected short borderTopColor;

    /**
     * 左边框
     */
    protected BorderStyle borderLeft;

    /**
     * 左边框颜色
     */
    protected short borderLeftColor;

    /**
     * 右边框
     */
    protected BorderStyle borderRight;

    /**
     * 右边框颜色
     */
    protected short borderRightColor;

    /**
     * 下边框
     */
    protected BorderStyle borderBottom;

    /**
     * 下边框颜色
     */
    protected short borderBottomColor;

    /**
     * 自动换行
     */
    protected boolean autoWrap;

    /**
     * 字体名称
     */
    protected Fonts fontName;

    /**
     * 字体大小
     */
    protected short fontSize;

    /**
     * 字体颜色
     */
    protected short fontColor;

    /**
     * 是否斜体
     */
    protected boolean italic;

    /**
     * 是否粗体
     */
    protected boolean bold;

    /**
     * 下划线
     */
    protected UnderLineStyle underline;

    /**
     * 是否有删除线
     */
    protected boolean strikeout;

    /**
     * 设置cell所引用的样式是否锁住
     */
    protected boolean locked;

    /**
     * <table BORDER CELLPADDING=3 CELLSPACING=1> <caption>默认工作表样式</caption>
     * <tr>
     * <td ALIGN=CENTER><b>样式名称</b></td>
     * <td ALIGN=CENTER><b>样式值</b></td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>自动换行</td>
     * <td ALIGN=CENTER>false</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>字体大小</td>
     * <td ALIGN=CENTER>12</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>是否粗体</td>
     * <td ALIGN=CENTER>false</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>是否斜体</td>
     * <td ALIGN=CENTER>false</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>下划线</td>
     * <td ALIGN=CENTER>无</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>字体颜色</td>
     * <td ALIGN=CENTER>灰色50%</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>垂直方向对齐方式</td>
     * <td ALIGN=CENTER>居中对齐</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>水平方向对齐方式</td>
     * <td ALIGN=CENTER>居中对齐</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>背景颜色</td>
     * <td ALIGN=CENTER>无</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>边框颜色</td>
     * <td ALIGN=CENTER>上、下、左、右无样式</td>
     * </tr>
     * <tr>
     * <td ALIGN=CENTER>边框</td>
     * <td ALIGN=CENTER>上、下、左、右无样式</td>
     * </tr>
     * </table>
     */
    public CustomCellStyle() {
        autoWrap = false;
        fontSize = 12;
        fontName = Fonts.SONTTI;
        bold = false;
        underline = UnderLineStyle.U_NONE;
        fontColor = NONE_STYLE;
        fillForegroundColor = NONE_STYLE;
        verticalAlignment = VerticalAlignment.CENTER;
        horizontalAlignment = HorizontalAlignment.CENTER;
        backgroundColor = NONE_STYLE;
        fillPatternType = FillPatternType.NO_FILL;
        setBorderColor(NONE_STYLE);
        setBorder(BorderStyle.NONE);
    }

    /**
     * 设置上、下、左、右边框
     *
     * @param border border
     */
    public void setBorder(BorderStyle border) {
        this.borderTop = border;
        this.borderLeft = border;
        this.borderRight = border;
        this.borderBottom = border;
    }

    /**
     * 设置上、下、左、右边框颜色
     *
     * @param borderColor borderColor
     */
    public void setBorderColor(short borderColor) {
        this.borderTopColor = borderColor;
        this.borderLeftColor = borderColor;
        this.borderRightColor = borderColor;
        this.borderBottomColor = borderColor;
    }

    /**
     * 构建Excel单元格样式
     *
     * @param workbook workbook
     * @param dataFormat dataFormat
     * @return {@link CellStyle}
     */
    public CellStyle toCellStyle(Workbook workbook, DataFormat dataFormat) {
        return CellStyleBuilder.buildCellStyle(workbook, this, dataFormat);
    }

}
