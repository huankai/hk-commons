package com.hk.commons.poi.excel.model;

import com.hk.commons.poi.excel.style.CustomCellStyle;
import lombok.Data;

/**
 * 导出Excel每一列的属性参数
 *
 * @author kevin
 * @date 2017年9月15日下午3:21:51
 */
@Data
public class ExcelColumnInfo {

    /**
     * 标题与样式
     */
    private StyleTitle title;

    /**
     * 是否统计
     */
    private boolean isStatistics;

    /**
     * 如果有注解，注解是否可见，默认隐藏，需要鼠标移动到指定的区域才可见
     */
    private boolean commentVisible;

    /**
     * 如果有注解，注解作者名称
     */
    private String commentAuthor;

    /**
     * 数据样式
     */
    private CustomCellStyle dataStyle;

}
