/**
 * 
 */
package com.hk.commons.poi.excel.read.validation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.common.collect.Lists;
import com.hk.commons.poi.excel.model.InvalidCell;
import com.hk.commons.poi.excel.model.Title;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.StringUtils;

/**
 * 使用JSR 验证
 * 
 * @author kally
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
	private List<Class<?>> groupList = Lists.newArrayList();

	/**
	 * 
	 * @param groups
	 */
	public JSRValidation(Class<?>... groups) {
		CollectionUtils.addAll(groupList, groups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.validation.Validationable#validate(java.lang.
	 * Object, int, java.lang.String)
	 */
	@Override
	public final List<InvalidCell> validate(T obj, int rowNumber, List<Title> titleList) {
		List<InvalidCell> result = null;
		Set<ConstraintViolation<Object>> sets = VALIDATOR.validate(obj, groupList.toArray(new Class<?>[] {}));
		if (CollectionUtils.isNotEmpty(sets)) {
			result = Lists.newArrayListWithCapacity(sets.size());
			Iterator<ConstraintViolation<Object>> iterator = sets.iterator();
			while (iterator.hasNext()) {
				ConstraintViolation<Object> violation = iterator.next();
				String propertyName = violation.getPropertyPath().toString();
				Title title = titleList.stream().filter(item -> StringUtils.equals(propertyName, item.getPropertyName())).findFirst().orElse(null);
				result.add(new InvalidCell(rowNumber, null == title ? -1 : title.getColumn(),
						violation.getInvalidValue(), title, violation.getMessageTemplate()));
			}
		}
		return result;
	}

}
