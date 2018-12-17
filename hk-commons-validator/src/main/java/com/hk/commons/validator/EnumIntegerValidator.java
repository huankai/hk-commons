package com.hk.commons.validator;

import com.hk.commons.util.ArrayUtils;
import com.hk.commons.validator.constraints.EnumInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author kevin
 * @date 2018-08-31 10:57
 */
public class EnumIntegerValidator implements ConstraintValidator<EnumInteger, Integer> {

    private int[] enumValue;

    private boolean notNull;

    @Override
    public void initialize(EnumInteger constraintAnnotation) {
        this.notNull = constraintAnnotation.notNull();
        this.enumValue = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return null == value ? !notNull : ArrayUtils.contains(enumValue, value);
    }
}
