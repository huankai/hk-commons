package com.hk.commons.poi.excel.read.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import com.hk.commons.poi.ReadException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;

import com.hk.commons.poi.excel.exception.ExcelReadException;
import com.hk.commons.poi.excel.model.ErrorLog;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.ReadResult;
import com.hk.commons.poi.excel.model.SheetData;
import com.hk.commons.poi.excel.model.Title;
import com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor;
import com.hk.commons.poi.excel.read.validation.Validationable;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.ObjectUtils;
import com.hk.commons.util.StringUtils;

/**
 * @author kevin
 */
public abstract class AbstractReadHandler<T> {

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 解析的一些参数
     */

    protected final ReadParam<T> readParam;

    /**
     * 标题信息，解析标题的时候才会有值
     */
    protected List<Title> titles;

    protected AbstractReadHandler(ReadParam<T> readParam) {
        this.readParam = readParam;
    }

    /**
     * 获取指定列的标题信息
     *
     * @param columnIndex columnIndex
     * @return column mapping title
     */
    protected final Title getTitle(int columnIndex) {
        return titles.stream().filter(item -> item.getColumn() == columnIndex).findFirst()
                .orElseThrow(() -> new ExcelReadException("第[" + columnIndex + "]列 Title 不存在"));
    }

    /**
     * 获取标题值
     *
     * @param columnIndex columnIndex
     * @return column mapping title value
     */
    protected final String getTitleValue(int columnIndex) {
        return getTitle(columnIndex).getValue();
    }

    /**
     * 获取列对应的属性名
     *
     * @param columnIndex columnIndex
     * @return column mapping title propertyName
     */
    protected final String getPropertyName(int columnIndex) {
        return getTitle(columnIndex).getPropertyName();
    }

    /**
     * 设置解析的标题
     *
     * @param titles setTitles
     */
    protected void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    /**
     * 是否为标题行
     *
     * @param rowNum
     * @return if (rowNum == titleRow),return to true;otherwise, return to false
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
        return null == titles ? -1 : titles.size();
    }

    /**
     * 设置属性
     *
     * @param wrapper     wrapper对象
     * @param columnIndex 属性所在列
     * @param value       要设置的属性值
     */
    protected void setWrapperBeanValue(BeanWrapper wrapper, int columnIndex, Object value)
            throws BeansException {
        if (Objects.nonNull(value)) {
            String propertyName = getPropertyName(columnIndex);
            if (StringUtils.isNotEmpty(propertyName)) {
                value = trimToValue(value);
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
     * @param value value
     * @return trim value
     */
    private Object trimToValue(Object value) {
        String result = ObjectUtils.toString(value);
        if (readParam.isTrim()) {
            result = StringUtils.trimToNull(result);
        }
        if (StringUtils.isNotEmpty(result) && readParam.isIgnoreLineBreak()) {
            result = StringUtils.replace(result, StringUtils.LF, StringUtils.EMPTY);
        }
        return result;
    }

    /**
     * 数据有效性验证
     *
     * @param result result
     */
    protected void validate(ReadResult<T> result) {
        List<Validationable<T>> validations = readParam.getValidationList();
        if (CollectionUtils.isNotEmpty(validations)) {
            final ValidationInterceptor<T> interceptor = readParam.getInterceptor();
            interceptor.preValidate(result);
            List<SheetData<T>> sheetDataList = result.getSheetDataList();
            long size = CollectionUtils.size(sheetDataList);
            if (size > 1) {
                CountDownLatch countDownLatch = new CountDownLatch((int) size);
                for (SheetData<T> sheetData : sheetDataList) {
                    new SheetValidateThread(countDownLatch, sheetData, result).start();
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new ReadException("ThreadName: " + Thread.currentThread().getName() + ",,Message:" + e.getMessage(), e);
                }
            } else if (size == 1) {
                doValidate(sheetDataList.get(0), result);
            }
            interceptor.afterComplete(result);
        }
    }

    private void doValidate(SheetData<T> dataSheet, ReadResult<T> result) {
        ValidationInterceptor<T> interceptor = readParam.getInterceptor();
        int rowIndex = readParam.getDataStartRow();
        ListIterator<T> listIterator = dataSheet.getData().listIterator();
        while (listIterator.hasNext()) {
            T t = listIterator.next();
            List<InvalidCell> invalidCells = new ArrayList<>();
            if (interceptor.beforeValidate(t)) {
                for (Validationable<T> validation : readParam.getValidationList()) {
                    List<InvalidCell> errors = validation.validate(t, rowIndex, titles);
                    if (CollectionUtils.isNotEmpty(errors)) {
                        invalidCells.addAll(errors);
                        if (!validation.errorNext()) {
                            break;
                        }
                    }
                }
                interceptor.afterValidate(t);
            }
            if (!invalidCells.isEmpty()) {
                result.addErrorLog(new ErrorLog<>(dataSheet.getSheetName(), rowIndex, t, invalidCells));
                listIterator.remove();
            }
            rowIndex++;
        }
    }

    @AllArgsConstructor
    private class SheetValidateThread extends Thread {

        private final CountDownLatch countDownLatch;

        private SheetData<T> sheetData;

        private ReadResult<T> result;

        @Override
        public void run() {
            try {
                doValidate(sheetData, result);
            } finally {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        }
    }

}
