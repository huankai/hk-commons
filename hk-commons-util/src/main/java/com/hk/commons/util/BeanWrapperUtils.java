package com.hk.commons.util;

import com.hk.commons.propertyeditors.CustomLocalDateEditor;
import com.hk.commons.propertyeditors.CustomLocalDateTimeEditor;
import com.hk.commons.propertyeditors.CustomLocalTimeEditor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author: huangkai
 * @date 2018-05-04 13:55
 */
public abstract class BeanWrapperUtils {

    public static BeanWrapper createBeanWrapper(Object obj) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        registryDefaultEditor(beanWrapper);
        return beanWrapper;
    }

    public static BeanWrapper createBeanWrapper(Class<?> clazz) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(clazz);
        registryDefaultEditor(beanWrapper);
        return beanWrapper;
    }

    private static void registryDefaultEditor(BeanWrapper beanWrapper) {
        beanWrapper.setAutoGrowNestedPaths(true);
        beanWrapper.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        beanWrapper.registerCustomEditor(LocalTime.class, new CustomLocalTimeEditor());
        beanWrapper.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
    }
}
