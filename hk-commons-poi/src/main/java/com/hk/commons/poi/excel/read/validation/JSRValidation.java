package com.hk.commons.poi.excel.read.validation;

import com.hk.commons.poi.excel.exception.ExcelReadException;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.Title;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 使用JSR 注解验证
 *
 * @author kevin
 * @date 2018年1月10日下午5:24:31
 */
public final class JSRValidation<T> implements Validationable<T> {

    /**
     * Validator
     */
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * groups
     */
    private final Class<?>[] groups;

    /**
     * @param groups groups
     */
    public JSRValidation(Class<?>... groups) {
        this.groups = (null == groups) ? new Class<?>[]{} : groups;
    }

    /**
     * 验证对象并将不通过的单元格转换为对象
     *
     * @param t         验证对象
     * @param rowNumber 对象所在Excel 行
     * @param titleList 标题行
     * @return 不通过的单元格转换为对象
     */
    @Override
    public List<InvalidCell> validate(T t, int rowNumber, List<Title> titleList) {
        return getValidateInfo(VALIDATOR.validate(t, groups), rowNumber, titleList);
    }

    /**
     * 将验证失效的属性转换为失效的单元格
     *
     * @param violationSet 对象的错误信息
     * @param rowNumber    对象所在Excel 行
     * @param titleList    对应的标题集合
     * @return 验证失效的单元格
     */
    private List<InvalidCell> getValidateInfo(Set<ConstraintViolation<Object>> violationSet, int rowNumber, List<Title> titleList) {
        if (CollectionUtils.isNotEmpty(violationSet)) {
            List<InvalidCell> result = new ArrayList<>(violationSet.size());
            violationSet.iterator().forEachRemaining(item -> {
                String propertyName = item.getPropertyPath().toString();
                Title title = titleList.stream()
                        .filter(titleItem -> StringUtils.equals(propertyName, titleItem.getPropertyName()))
                        .findFirst()
                        .orElseThrow(() -> new ExcelReadException("根据属性名[" + propertyName + "]找不到对应的标题"));
                result.add(new InvalidCell(rowNumber, title.getColumn(), item.getInvalidValue(), title, item.getMessageTemplate()));
            });
            return result;
        }
        return null;
    }


}
