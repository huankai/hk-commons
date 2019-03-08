package com.hk.commons.poi.excel.read.handler;

import com.hk.commons.poi.excel.exception.InvalidCellReadableExcelException;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.ReadParam;
import com.hk.commons.poi.excel.model.SheetData;
import com.hk.commons.poi.excel.model.Title;
import com.hk.commons.util.BeanWrapperUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.KeyValue;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Use Sax parse Excel
 *
 * @author kevin
 */
public abstract class AbstractSaxReadHandler<T> extends AbstractReadHandler<T> {

    /**
     * <pre>
     * 行的单元格所在列与内容
     * 在解析新一行数据时，需要手动清空此对象中的数据
     * </pre>
     */
    private List<KeyValue<Integer, String>> cacheRowColumnValues;

    /**
     * <pre>
     * 每一个工作表的数据
     * 在解析一个新的工作表时，需要手动清空此对象中的数据
     * </pre>
     */
    private SheetData<T> sheetData;

    protected AbstractSaxReadHandler(ReadParam<T> param) {
        super(param);
    }

    /**
     * <pre>
     * 每一个工作表都是一个sheetData
     * 在解析一个新的工作表时，创建新的sheetData
     * </pre>
     */
    protected final void cleanSheetData() {
        sheetData = new SheetData<>();
    }

    /**
     * 解析标题行
     */
    protected final void parseTitleRow() {
        List<KeyValue<Integer, String>> columnValues = getRowColumnValues();
        List<Title> titles = new ArrayList<>(columnValues.size());
        Map<Integer, String> columnProperties = readParam.getColumnProperties();
        columnValues.forEach((item) -> titles.add(new Title(readParam.getTitleRow(), item.getKey(), item.getValue(), columnProperties.get(item.getKey()))));
        setTitles(titles);
    }

    /**
     * 将当行行的数据解析为一个对象
     *
     * @param rowNum 当前数据所在的行
     * @return
     */
    protected T parseToData(int rowNum) throws InvalidCellReadableExcelException {
        List<KeyValue<Integer, String>> columnValues = getRowColumnValues();
        if (CollectionUtils.isEmpty(columnValues)) {
            return null;
        }
        BeanWrapper wrapper = BeanWrapperUtils.createBeanWrapper(readParam.getBeanClazz());
        List<InvalidCell> invalidCells = new ArrayList<>();
        for (KeyValue<Integer, String> keyValue : columnValues) {
            try {
                setWrapperBeanValue(wrapper, keyValue.getKey(), keyValue.getValue());
            } catch (BeansException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage(), e);
                }
                invalidCells.add(new InvalidCell(rowNum, keyValue.getKey(), keyValue.getValue(),
                        getTitle(keyValue.getKey()), keyValue.getValue() + " 设置失败"));
            }
        }
        T target = readParam.getBeanClazz().cast(wrapper.getWrappedInstance());
        if (!invalidCells.isEmpty()) {
            throw new InvalidCellReadableExcelException("设置属性失败", target, invalidCells);
        }
        return target;
    }

    /**
     * 清除每一行的数据
     *
     * <pre>
     * 每一行解析出来的数据，都会放在 cacheRowColumnValues 中
     * 在每一行解析结束、新一行解析开始时，需要清除之前解析的数据
     * </pre>
     */
    protected final void clearRowValues() {
        getRowColumnValues().clear();
    }

    /**
     * 获取当前解析的列数据
     *
     * @return
     */
    protected List<KeyValue<Integer, String>> getRowColumnValues() {
        if (null == cacheRowColumnValues) {
            cacheRowColumnValues = new ArrayList<>();
        }
        return cacheRowColumnValues;
    }

    protected SheetData<T> getSheetData() {
        if (null == sheetData) {
            cleanSheetData();
        }
        return sheetData;
    }
}
