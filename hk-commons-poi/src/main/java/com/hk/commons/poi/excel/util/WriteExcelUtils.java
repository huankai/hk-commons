package com.hk.commons.poi.excel.util;

import com.hk.commons.poi.excel.annotations.NestedProperty;
import com.hk.commons.poi.excel.annotations.WriteExcelField;
import com.hk.commons.poi.excel.exception.ExcelWriteException;
import com.hk.commons.poi.excel.model.ExcelColumnInfo;
import com.hk.commons.poi.excel.model.StyleTitle;
import com.hk.commons.poi.excel.style.CustomCellStyle;
import com.hk.commons.util.BeanUtils;
import com.hk.commons.util.FieldUtils;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.TypeUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * 导出Excel工具类
 *
 * @author kevin
 * @date 2017年9月15日下午1:28:06
 */
public abstract class WriteExcelUtils {

    /**
     * get method prefix
     */
    private static final String GET_METHOD_PREFIX = "get";

    /**
     * is method prefix
     */
    private static final String IS_METHOD_PREFIX = "is";

    /**
     * 嵌套集合属性
     */
    public static final String NESTED_PROPERTY = "[%d].";

    /**
     * 解析
     *
     * @param clazz    beanClass
     * @param titleRow 标题行
     * @return 解析后的Excel 列信息,每一列都是一个 com.hk.commons.poi.excel.model.ExcelColumnInfo
     */
    public static List<ExcelColumnInfo> parse(Class<?> clazz, final int titleRow) {
        List<ExcelColumnInfo> result = new ArrayList<>();
        addWriteExcel(titleRow, clazz, null, null, result);
        result.sort(Comparator.comparingInt((column) -> column.getTitle().getColumn()));
        return result;
    }

    /**
     * 使用递归解析
     *
     * @param titleRow               标题所在行
     * @param parameterizedTypeClass 参数类型class
     * @param wrapClass              包装类型
     * @param nestedPrefix           nested属性前缀
     * @param result                 结果list
     */
    private static void addWriteExcel(final int titleRow, Class<?> parameterizedTypeClass, Class<?> wrapClass, String nestedPrefix, List<ExcelColumnInfo> result) {
        if (null != wrapClass) {
            if (ClassUtils.isAssignable(Iterable.class, wrapClass)) {
                nestedPrefix = StringUtils.isEmpty(nestedPrefix) ? StringUtils.EMPTY : nestedPrefix + NESTED_PROPERTY;
            } else {
                nestedPrefix = StringUtils.isEmpty(nestedPrefix) ? StringUtils.EMPTY : nestedPrefix + PropertyAccessor.NESTED_PROPERTY_SEPARATOR;
            }
        } else {
            nestedPrefix = StringUtils.isEmpty(nestedPrefix) ? StringUtils.EMPTY : nestedPrefix;
        }
        Map<String, WriteExcelField> maps = getWriteExcelAnnotationList(parameterizedTypeClass);
        ExcelColumnInfo info;
        StyleTitle styleTitle;
        for (Entry<String, WriteExcelField> entry : maps.entrySet()) {
            WriteExcelField writeExcel = entry.getValue();
            info = new ExcelColumnInfo();
            info.setStatistics(writeExcel.isStatistics());
            info.setCommentAuthor(writeExcel.author());
            info.setCommentVisible(writeExcel.visible());

            CustomCellStyle titleStyle = StyleCellUtils.toCustomCellStyle(writeExcel.titleStyle());
            styleTitle = new StyleTitle(titleRow, writeExcel.index(), writeExcel.value(),
                    nestedPrefix + entry.getKey());
            styleTitle.setColumnWidth(writeExcel.width());
            styleTitle.setStyle(titleStyle);
            info.setTitle(styleTitle);

            CustomCellStyle dataStyle = StyleCellUtils.toCustomCellStyle(writeExcel.dataStyle());
            info.setDataStyle(dataStyle);
            result.add(info);
        }
        List<Field> nestedFieldList = FieldUtils.getFieldsListWithAnnotation(parameterizedTypeClass,
                NestedProperty.class);
        if (nestedFieldList.size() > 1) {
            // 如果有多个 NestedProperty的注解,对于合并单元格是个麻烦事,如果你有好的想法,可以提出来
            throw new ExcelWriteException("暂不支持多个有NestedProperty注解标记的属性");
        }
        nestedFieldList.forEach(item -> {
            Class<?> ptclass = TypeUtils.getCollectionParameterizedTypeClass(parameterizedTypeClass, item.getName());
            if (null != ptclass && !BeanUtils.isSimpleProperty(ptclass)) {
                addWriteExcel(titleRow, ptclass, item.getType(), item.getName(), result);
            }
        });
    }

    /**
     * 获取有ExportExcel注解修饰的属性，包括父类
     *
     * @param cls cls
     * @return {@link List}
     */
    private static List<Field> getFieldWithWriteExcelAnnotationList(Class<?> cls) {
        return FieldUtils.getFieldsListWithAnnotation(cls, WriteExcelField.class);
    }

    /**
     * 获取有ExportExcel注解修饰的方法，包括父类
     *
     * @param cls cls
     * @return {@link List}
     */
    private static List<Method> getMethodWithWriteExcelAnnotationList(Class<?> cls) {
        return MethodUtils.getMethodsListWithAnnotation(cls, WriteExcelField.class);
    }

    /**
     * 获取属性和Get方法有ExportExcel注解修饰Map集合，包括父类
     *
     * @param cls cls
     * @return {@link Map}
     */
    private static Map<String, WriteExcelField> getWriteExcelAnnotationList(Class<?> cls) {
        List<Field> fields = getFieldWithWriteExcelAnnotationList(cls);
        List<Method> methods = getMethodWithWriteExcelAnnotationList(cls);
        Map<String, WriteExcelField> result = new HashMap<>();
        fields.forEach(item -> result.put(item.getName(), item.getAnnotation(WriteExcelField.class)));
        methods.forEach(item -> {
            String methodName = item.getName();
            if (StringUtils.startsWithAny(methodName, GET_METHOD_PREFIX, IS_METHOD_PREFIX)) {
                String name = StringUtils.uncapitalize(StringUtils.substring(methodName, GET_METHOD_PREFIX.length()));
                result.put(name, item.getAnnotation(WriteExcelField.class));
            }
        });
        return result;
    }

}
