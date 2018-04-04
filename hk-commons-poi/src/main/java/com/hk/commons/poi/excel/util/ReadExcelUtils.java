/**
 * 
 */
package com.hk.commons.poi.excel.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Maps;
import com.hk.commons.poi.excel.annotations.NestedProperty;
import com.hk.commons.poi.excel.annotations.ReadExcel;
import com.hk.commons.util.FieldUtils;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.TypeUtils;

/**
 * 读取Excel 工具类
 * 
 * @author kally
 * @date 2018年1月11日下午12:49:37
 */
public abstract class ReadExcelUtils {

	/**
	 * 
	 * @param beanClass
	 * @param wrapClass
	 * @param nestedPath
	 * @param map
	 * @return
	 */
	public static Map<Integer, String> getReadExcelAnnotationMapping(Class<?> beanClass) {
		Map<Integer, String> map = Maps.newHashMap();
		putReadExcel(beanClass,null,null,map);
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
			int[] arr = getExcelCellAnnotationColumns(field.getAnnotation(ReadExcel.class));
			for (int column : arr) {
				map.put(column, nestedPrefix + field.getName());
			}
		}
		
		List<Field> nestedFieldList = FieldUtils.getFieldsListWithAnnotation(beanClass, NestedProperty.class);
		nestedFieldList.forEach(item -> {
			Class<?> parameterizedTypeClass = TypeUtils.getParameterizedTypeClass(beanClass, item.getName());
			if (null != parameterizedTypeClass && !BeanUtils.isSimpleProperty(parameterizedTypeClass)) {
				putReadExcel(parameterizedTypeClass, item.getType(), item.getName(), map);
			}
		});
		
	}



	/**
	 * @param beanClazz
	 * @param columnIndex
	 * @return
	 */
	public static String getPropertyName(Class<?> beanClazz, final int columnIndex) {
		return getAnnotationField(beanClazz, columnIndex).getName();
	}

	/**
	 * 返回指定类(包括父类)有 com.hk.commons.poi.excel.annotations.ImportExcel 注解标识的属性
	 * 
	 * @param cls
	 * @return
	 */
	public static List<Field> getFieldWithExcelCellAnnotations(Class<?> cls) {
		return FieldUtils.getFieldsListWithAnnotation(cls, ReadExcel.class);
	}

	/**
	 * 
	 * @param beanClazz
	 * @param columnIndex
	 * @return
	 */
	public static ReadExcel getAnnotation(Class<?> beanClazz, final int columnIndex) {
		return getAnnotationField(beanClazz, columnIndex).getAnnotation(ReadExcel.class);
	}

	/**
	 * 
	 * @param beanClazz
	 * @param columnIndex
	 * @return
	 */
	private static Field getAnnotationField(Class<?> beanClazz, final int columnIndex) {
		List<Field> fields = FieldUtils.getFieldsListWithAnnotation(beanClazz, ReadExcel.class);
		return fields.stream().filter(f -> {
			ReadExcel readExcel = f.getAnnotation(ReadExcel.class);
			return readExcel.end() == -1 ? readExcel.start() == columnIndex
					: columnIndex >= readExcel.start() && columnIndex <= readExcel.end();
		}).findFirst().get();
	}

	/**
	 * @param beanClass
	 * @param propertyName
	 * @return
	 */
	public static int[] getPropertyAnnotationColumns(Class<?> beanClass, String propertyName) {
		List<Field> fields = FieldUtils.getFieldsListWithAnnotation(beanClass, ReadExcel.class);
		ReadExcel excelCell = fields.stream().filter(field -> StringUtils.equals(field.getName(), propertyName))
				.findFirst().get().getAnnotation(ReadExcel.class);
		return getExcelCellAnnotationColumns(excelCell);
	}

	/**
	 * 
	 * @param importExcel
	 * @return
	 */
	public static int[] getExcelCellAnnotationColumns(ReadExcel readExcel) {
		if (readExcel.start() < readExcel.end()) {
			return IntStream.range(readExcel.start(), readExcel.end() + 1).toArray();
		}
		return new int[] { readExcel.start() };
	}

	/**
	 * @param beanClass
	 * @param propertyName
	 * @return
	 */
	public static int getFirstPropertyAnnotationColumn(Class<?> beanClass, String propertyName) {
		
		return gePropertyAnnotationColumn(beanClass, propertyName, 0);
	}

	/**
	 * @param beanClass
	 * @param propertyName
	 * @param index
	 * @throws ArrayIndexOutOfBoundsException
	 * @return
	 */
	public static int gePropertyAnnotationColumn(Class<?> beanClass, String propertyName, int index) {
		return getPropertyAnnotationColumns(beanClass, propertyName)[index];
	}

}
