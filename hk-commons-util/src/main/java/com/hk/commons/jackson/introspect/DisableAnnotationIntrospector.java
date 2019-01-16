package com.hk.commons.jackson.introspect;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

/**
 * 禁用标注的属性序列化
 *
 * @author huangkai
 * @date 2018-12-19 23:34
 */
@SuppressWarnings("serial")
public class DisableAnnotationIntrospector extends JacksonAnnotationIntrospector {

    private static final DisableAnnotationIntrospector INSTANCE = new DisableAnnotationIntrospector();

    private DisableAnnotationIntrospector() {

    }

    public static DisableAnnotationIntrospector getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isAnnotationBundle(Annotation ann) {
        return !JsonIgnoreProperties.class.equals(ann.annotationType());
    }

    /**
     * 禁用 {@link JsonIgnoreProperties} 忽略的属性
     *
     * @param ac ac
     * @return {@link JsonIgnoreProperties.Value}
     */
    @Override
    public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated ac) {
        return JsonIgnoreProperties.Value.empty();
    }
}
