package com.hk.commons.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.google.common.collect.Lists;
import com.hk.commons.fastjson.support.joda.JodaDateTimeDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalDateDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalDateTimeDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalTimeDeserializer;
import com.hk.commons.util.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;

/**
 * FastJson 工具类
 *
 * @author huangkai
 */
public final class JsonUtils {

    /**
     * 默认 使用的 SerializerFeature
     */
    public static final SerializerFeature[] FEATURES = {SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullStringAsEmpty};

    public static final SerializeConfig CONFIG;

    static {
        CONFIG = new SerializeConfig();
        CONFIG.put(DateTime.class, JodaDateTimeDeserializer.INSTANCE);
        CONFIG.put(LocalDate.class, JodaLocalDateDeserializer.INSTANCE);
        CONFIG.put(LocalDateTime.class, JodaLocalDateTimeDeserializer.INSTANCE);
        CONFIG.put(LocalTime.class, JodaLocalTimeDeserializer.INSTANCE);
    }

    /**
     * <pre>
     *  转换成Json字符串,使用默认的序列化特征
     * </pre>
     *
     * @param object
     * @return
     * @see #FEATURES
     */
    public static String toJSONString(Object object) throws JSONException {
        return toJSONString(object, true);
    }

    /**
     * @param object
     * @return
     */
    public static String toFormatJSONString(Object object) throws JSONException {
        return toJSONString(object, true, SerializerFeature.PrettyFormat);
    }

    /**
     * 是否使用默认的字符串特征 转换Json字符串
     *
     * @param object
     * @param useDefaultFeature
     * @return
     */
    public static String toJSONString(Object object, boolean useDefaultFeature) throws JSONException {
        return toJSONString(object, useDefaultFeature, (SerializerFeature[]) null);
    }

    /**
     * 指定属性转换为Json字符串
     *
     * @param object
     * @param properties
     * @return
     */
    public static String toJSONString(Object object, String... properties) throws JSONException {
        return JSON.toJSONString(object, new SimplePropertyPreFilter(properties), FEATURES);
    }

    /**
     * 过滤指定的属性
     *
     * @param object
     * @param excludesProperties
     * @return
     */
    public static String toJSONStringExcludes(Object object, String... excludesProperties) {
        return JSON.toJSONString(object, new PropertyPreFilters().addFilter().addExcludes(excludesProperties), FEATURES);
    }

    /**
     * @param object
     * @param serializerFeatures
     * @return
     */
    public static String toJSONString(Object object, boolean useDefaultFeature, SerializerFeature... serializerFeatures)
            throws JSONException {
        List<SerializerFeature> features = Lists.newArrayList();
        if (useDefaultFeature) {
            CollectionUtils.addAll(features, FEATURES);
        }
        CollectionUtils.addAll(features, serializerFeatures);
        return JSON.toJSONString(object, CONFIG, features.toArray(new SerializerFeature[]{}));
    }

    /**
     * json字符串转对象
     *
     * @param input
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String input, Class<T> clazz) throws JSONException {
        return JSON.parseObject(input, clazz);
    }

    /**
     * json字符串转Map
     *
     * @param input
     * @return
     */
    public static Map<String, Object> parseObjectToMap(String input) throws JSONException {
        return JSON.parseObject(input);
    }

    /**
     * json字符串转集合
     *
     * @param input
     * @param clazz
     * @return
     */
    public static <T> List<T> parseObjectToList(String input, Class<T> clazz) throws JSONException {
        return JSON.parseArray(input, clazz);
    }

}
