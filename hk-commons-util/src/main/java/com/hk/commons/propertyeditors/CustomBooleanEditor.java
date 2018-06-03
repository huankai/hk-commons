package com.hk.commons.propertyeditors;

import com.hk.commons.util.BooleanUtils;
import com.hk.commons.util.StringUtils;
import org.springframework.beans.BeanWrapper;

/**
 * BeanWraper Editor
 *
 * @author: huangkai
 * @date 2018-05-08 09:55
 * @see com.hk.commons.util.BeanWrapperUtils#registryDefaultEditor(BeanWrapper)
 */
public class CustomBooleanEditor extends org.springframework.beans.propertyeditors.CustomBooleanEditor {


    public CustomBooleanEditor(boolean allowEmpty) {
        super(allowEmpty);
    }

    public CustomBooleanEditor(String trueString, String falseString, boolean allowEmpty) {
        super(trueString, falseString, allowEmpty);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        text = StringUtils.trim(text);
        if (StringUtils.equals(BooleanUtils.TRUE_CHINEASE, text)) {
            setValue(Boolean.TRUE);
        } else if (StringUtils.equals(BooleanUtils.FALSE_CHINEASE, text)) {
            setValue(Boolean.FALSE);
        } else {
            super.setAsText(text);
        }
    }

    @Override
    public String getAsText() {
        String value = (String) getValue();
        if (StringUtils.equalsAny(value, BooleanUtils.TRUE_CHINEASE, BooleanUtils.FALSE_CHINEASE)) {
            return value;
        }
        return super.getAsText();
    }
}
