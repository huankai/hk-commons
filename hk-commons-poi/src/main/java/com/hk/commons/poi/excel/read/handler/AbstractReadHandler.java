/**
 *
 */
package com.hk.commons.poi.excel.read.handler;

import com.google.common.collect.Lists;
import com.hk.commons.poi.excel.model.*;
import com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor;
import com.hk.commons.poi.excel.read.validation.Validationable;
import com.hk.commons.poi.excel.util.WriteExcelUtils;
import com.hk.commons.propertyeditors.CustomLocalDateEditor;
import com.hk.commons.propertyeditors.CustomLocalDateTimeEditor;
import com.hk.commons.propertyeditors.CustomLocalTimeEditor;
import com.hk.commons.util.BooleanUtils;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.ObjectUtils;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;
import com.hk.commons.util.date.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.util.ClassUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

/**
 * @author huangkai
 */
public abstract class AbstractReadHandler<T> implements ReadableHandler<T> {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 解析的一些参数
     */

    protected final ReadableParam<T> readParam;

    /**
     * 标题信息，解析标题的时候才会有值
     */
    protected List<Title> titles;

    protected AbstractReadHandler(ReadableParam<T> readParam) {
        this.readParam = readParam;
    }

    /**
     * 获取指定列的标题信息
     *
     * @param columnIndex
     * @return
     */
    protected final Title getTitle(int columnIndex) {
        return titles.stream().filter(item -> item.getColumn() == columnIndex).findFirst().get();
    }

    /**
     * 获取标题值
     *
     * @param columnIndex
     * @return
     */
    protected final String getTitleValue(int columnIndex) {
        return getTitle(columnIndex).getValue();
    }

    /**
     * 获取列对应的属性名
     *
     * @param columnIndex
     * @return
     */
    protected final String getPropertyName(int columnIndex) {
        return getTitle(columnIndex).getPropertyName();
    }

    /**
     * 设置解析的标题
     *
     * @param titles
     */
    protected void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    /**
     * 是否为标题行
     *
     * @param rowNum
     * @return
     */
    protected final boolean isTitleRow(int rowNum) {
        return rowNum == readParam.getTitleRow();
    }

    /**
     * 在没有解析标题行时，为-1
     *
     * @return 返回最大解析的列
     */
    protected final int getMaxColumnIndex() {
        return titles == null ? -1 : titles.size();
    }

    /**
     * @param
     * @return
     */
    protected final BeanWrapper createParamBeanWrapper() {
        BeanWrapper beanWrapper = new BeanWrapperImpl(readParam.getBeanClazz());
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        beanWrapper.registerCustomEditor(LocalTime.class, new CustomLocalTimeEditor());
        beanWrapper.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
        return beanWrapper;
    }

    /**
     * 设置属性
     *
     * @param wrapper     wrapper对象
     * @param nestedIndex 如果是Nested属性，并且为集合，此值才有效， 如城市集合： citys[0].name
     * @param columnIndex 属性所在列
     * @param value       要设置的属性值
     */
    protected void setWrapperBeanValue(BeanWrapper wrapper, int columnIndex, int nestedIndex, Object value)
            throws BeansException {
        value = trimToValue(value);
        if (ObjectUtils.isNotEmpty(value)) {
            String propertyName = getPropertyName(columnIndex);
            if(StringUtils.isNotEmpty(propertyName)){
                if (StringUtils.indexOf(propertyName, WriteExcelUtils.NESTED_PROPERTY) != -1) {
                    propertyName = String.format(propertyName, nestedIndex);
                }
                Class<?> propertyType = wrapper.getPropertyType(propertyName);
                if (ClassUtils.isAssignable(Boolean.class, propertyType)) {
                    value = BooleanUtils.toBoolean(value.toString());
                } else if (ClassUtils.isAssignable(Date.class, propertyType)) {
                    value = DateTimeUtils.stringToDate(value.toString(), DatePattern.values());
                }
                wrapper.setPropertyValue(propertyName, value);
            }
        }
    }

    /**
     * <pre>
     * 忽略首、末有空字符号串或者有 \n 字符串
     * 如果参数中有设置 trim == true 时
     * "a" ---------> "a"
     * "  a" ---------> "a"
     * "a  " ---------> "a"
     * 如果参数中有设置 ingoreLineBreak == true 时
     * "a\n" ---------> "a"
     * "a\nb" ---------> "ab"
     * </pre>
     *
     * @param value
     * @return
     */
    private Object trimToValue(Object value) {
        String result = ObjectUtils.toString(value);
        if (readParam.isTrim()) {
            result = StringUtils.trimToNull(result);
        }
        if (readParam.isIngoreLineBreak()) {
            result = StringUtils.replaceAll(result, StringUtils.LF, StringUtils.EMPTY);
        }
        return result;
    }

    /**
     * 数据有效性验证
     *
     * @param result
     */
    protected void validate(ReadResult<T> result) {
        List<Validationable<T>> validationables = readParam.getValidationList();
        if (CollectionUtils.isNotEmpty(validationables)) {
            final ValidationInterceptor<T> interceptor = readParam.getInterceptor();
            interceptor.preValidate(result);
            for (SheetData<T> dataSheet : result.getSheetDataList()) {
                int rowIndex = readParam.getDataStartRow();
                ListIterator<T> listIterator = dataSheet.getData().listIterator();
                while (listIterator.hasNext()) {
                    T t = listIterator.next();
                    List<InvalidCell> invalidCells = Lists.newArrayList();
                    if (interceptor.beforeValidate(t)) {
                        for (Validationable<T> validationable : validationables) {
                            List<InvalidCell> errors = validationable.validate(t, rowIndex, titles);
                            if (CollectionUtils.isNotEmpty(errors)) {
                                invalidCells.addAll(errors);
                                if (!validationable.errorNext()) {
                                    break;
                                }
                            }
                        }
                        interceptor.afterValidate(t);
                    }
                    if (!invalidCells.isEmpty()) {
                        result.addErrorLog(new ErrorLog<T>(dataSheet.getSheetName(), rowIndex, t, invalidCells));
                        listIterator.remove();
                    }
                    rowIndex++;
                }
            }
            interceptor.afterComplate(result);
        }
    }

}
