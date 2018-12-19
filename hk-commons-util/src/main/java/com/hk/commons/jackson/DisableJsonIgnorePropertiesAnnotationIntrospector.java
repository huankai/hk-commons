package com.hk.commons.jackson;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

/**
 * @author huangkai
 * @date 2018/12/19 23 34
 */
public class DisableJsonIgnorePropertiesAnnotationIntrospector extends JacksonAnnotationIntrospector {


    @Override
    public boolean isAnnotationBundle(Annotation ann) {
        return !JsonIgnoreProperties.class.equals(ann.annotationType());
    }

    @Override
    public JsonIgnoreProperties.Value findPropertyIgnorals(Annotated ac) {
        return JsonIgnoreProperties.Value.empty();
    }
}
