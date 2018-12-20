package com.hk.commons.util;

import com.hk.commons.propertyeditors.CustomBooleanEditor;
import com.hk.commons.propertyeditors.CustomLocalDateEditor;
import com.hk.commons.propertyeditors.CustomLocalDateTimeEditor;
import com.hk.commons.propertyeditors.CustomLocalTimeEditor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * BeanWrapper Create util
 *
 * @author kevin
 * @date 2018-05-04 13:55
 */
public abstract class BeanWrapperUtils {

    /**
     * create BeanWrapper by Obj
     *
     * @param obj obj
     * @return BeanWrapper
     */
    public static BeanWrapper createBeanWrapper(Object obj) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        registryDefaultEditor(beanWrapper);
        return beanWrapper;
    }

    /**
     * create BeanWrapper by clazz
     *
     * @param clazz clazz
     * @return BeanWrapper
     */
    public static BeanWrapper createBeanWrapper(Class<?> clazz) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(clazz);
        registryDefaultEditor(beanWrapper);
        return beanWrapper;
    }

    private static void registryDefaultEditor(BeanWrapper beanWrapper) {
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.setConversionService(ConverterUtils.DEFAULT_CONVERSION_SERVICE);
        beanWrapper.registerCustomEditor(Boolean.class, CustomBooleanEditor.ALLOW_EMPTY_INSTANCE);
        beanWrapper.registerCustomEditor(LocalDate.class, CustomLocalDateEditor.INSTANCE);
        beanWrapper.registerCustomEditor(LocalTime.class, CustomLocalTimeEditor.INSTANCE);
        beanWrapper.registerCustomEditor(LocalDateTime.class, CustomLocalDateTimeEditor.INSTANCE);
    }
}
