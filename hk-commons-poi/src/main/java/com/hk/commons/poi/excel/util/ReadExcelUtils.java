package com.hk.commons.poi.excel.util;

import com.hk.commons.poi.excel.annotations.NestedProperty;
import com.hk.commons.poi.excel.annotations.ReadExcelField;
import com.hk.commons.poi.excel.exception.ExcelReadException;
import com.hk.commons.util.FieldUtils;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.TypeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 读取Excel 工具类
 *
 * @author kevin
 * @date 2018年1月11日下午12:49:37
 */
public abstract class ReadExcelUtils {

    /**
     * 解析标示有 @ReadExcel 注解的属性列与属性名的map,
     * 包含嵌套的属性，嵌套的属性必须有 NestedProperty.class 修饰
     *
     * @param beanClass 指定的 beanClass
     * @return 标示有 ReadExcel.class 注解的属性列与属性名的map
     */
    public static Map<Integer, String> getReadExcelAnnotationMapping(Class<?> beanClass) {
        Map<Integer, String> map = new HashMap<>();
        putReadExcel(beanClass, null, null, map);
        return map;
    }


    private static void putReadExcel(Class<?> beanClass, Class<?> wrapClass, String nestedPrefix, Map<Integer, String> map) {
        if (null != wrapClass) {
            if (ClassUtils.isAssignable(Iterable.class, wrapClass)) {
                nestedPrefix = StringUtils.isEmpty(nestedPrefix) ? StringUtils.EMPTY
                        : nestedPrefix + WriteExcelUtils.NESTED_PROPERTY;
            }
            /*
             * else if(wrapClass.isArray()) { nestedPath = StringUtils.isBlank(nestedPath) ?
             * StringUtils.EMPTY : nestedPath + "."; }
             */
            else {
                nestedPrefix = StringUtils.isEmpty(nestedPrefix) ?
                        StringUtils.EMPTY : nestedPrefix + PropertyAccessor.NESTED_PROPERTY_SEPARATOR;
            }
        } else {
            nestedPrefix = StringUtils.isEmpty(nestedPrefix) ? StringUtils.EMPTY : nestedPrefix;
        }

        List<Field> fieldList = getFieldWithExcelCellAnnotations(beanClass);
        for (Field field : fieldList) {
            int[] arr = getExcelCellAnnotationColumns(field.getAnnotation(ReadExcelField.class));
            for (int column : arr) {
                map.put(column, nestedPrefix + field.getName());
            }
        }

        List<Field> nestedFieldList = FieldUtils.getFieldsListWithAnnotation(beanClass, NestedProperty.class);
        nestedFieldList.forEach(item -> {
            Class<?> parameterizedTypeClass = TypeUtils.getCollectionParameterizedTypeClass(beanClass, item.getName());
            if (null != parameterizedTypeClass && !BeanUtils.isSimpleProperty(parameterizedTypeClass)) {
                putReadExcel(parameterizedTypeClass, item.getType(), item.getName(), map);
            }
        });
    }

    /**
     * 获取标示有ReadExcel 注解字段指定列的字段名
     *
     * @param beanClazz   beanClass
     * @param columnIndex 指定列
     * @return 属性名
     */
    public static String getPropertyName(Class<?> beanClazz, final int columnIndex) {
        return getAnnotationField(beanClazz, columnIndex).getName();
    }

    /**
     * 返回指定类(包括父类)有 com.hk.commons.poi.excel.annotations.ReadExcel 注解标识的属性
     *
     * @param cls 指定 class
     * @return 包含有 ReadExcel注解的属性
     */
    public static List<Field> getFieldWithExcelCellAnnotations(Class<?> cls) {
        return FieldUtils.getFieldsListWithAnnotation(cls, ReadExcelField.class);
    }

    /**
     * @param beanClazz beanClazz
     * @param columnIndex columnIndex
     * @return {@link ReadExcelField}
     */
    public static ReadExcelField getAnnotation(Class<?> beanClazz, final int columnIndex) {
        return getAnnotationField(beanClazz, columnIndex).getAnnotation(ReadExcelField.class);
    }

    /**
     * @param beanClass beanClass
     * @param columnIndex columnIndex
     * @return {@link Field}
     */
    private static Field getAnnotationField(Class<?> beanClass, final int columnIndex) {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(beanClass, ReadExcelField.class);
        return fields.stream().filter(f -> {
            ReadExcelField readExcel = f.getAnnotation(ReadExcelField.class);
            return readExcel.end() == -1 ? readExcel.start() == columnIndex
                    : columnIndex >= readExcel.start() && columnIndex <= readExcel.end();
        }).findFirst().orElseThrow(() -> new ExcelReadException(beanClass.getName() + "不包含列[" + columnIndex + "]的属性名"));
    }

    /**
     * 返回标记有 {@link ReadExcelField} 注解的属性对应的列
     *
     * @param beanClass    bean class
     * @param propertyName 属性名
     * @return 属性对应的列
     */
    public static int[] getPropertyAnnotationColumns(Class<?> beanClass, String propertyName) {
        List<Field> fields = FieldUtils.getFieldsListWithAnnotation(beanClass, ReadExcelField.class);
        ReadExcelField excelCell = fields.stream().filter(field -> StringUtils.equals(field.getName(), propertyName))
                .findFirst()
                .orElseThrow(() -> new ExcelReadException(beanClass.getName() + "属性名[" + propertyName + "]的没有标示 " + ReadExcelField.class.getName()))
                .getAnnotation(ReadExcelField.class);
        return getExcelCellAnnotationColumns(excelCell);
    }

    /**
     * @param readExcel
     * @return
     */
    public static int[] getExcelCellAnnotationColumns(ReadExcelField readExcel) {
        if (readExcel.start() < readExcel.end()) {
            return IntStream.range(readExcel.start(), readExcel.end() + 1).toArray();
        }
        return new int[]{readExcel.start()};
    }

    /**
     * @param beanClass    beanClass
     * @param propertyName propertyName
     * @return 获取标记有 {@link ReadExcelField} 字段的指定第一个索引的值
     */
    public static int getFirstPropertyAnnotationColumn(Class<?> beanClass, String propertyName) {
        return gePropertyAnnotationColumn(beanClass, propertyName, 0);
    }

    /**
     * @param beanClass    beanClass
     * @param propertyName propertyName
     * @param index        index
     * @return 获取标记有 {@link ReadExcelField} 字段的指定 index 的值
     * @throws ArrayIndexOutOfBoundsException 超出指定索引抛出异常
     */
    public static int gePropertyAnnotationColumn(Class<?> beanClass, String propertyName, int index) {
        return getPropertyAnnotationColumns(beanClass, propertyName)[index];
    }

}
