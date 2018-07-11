package com.hk.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.google.common.collect.Lists;
import com.hk.commons.fastjson.support.joda.JodaDateTimeDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalDateDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalDateTimeDeserializer;
import com.hk.commons.fastjson.support.joda.JodaLocalTimeDeserializer;
import com.hk.commons.util.date.DatePattern;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;

/**
 * FastJson 工具类
 *
 * @author kevin
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
     * @param object 序列化的对象
     * @return 序列化后的对象字符串表现形式
     * @see #FEATURES
     */
    public static String toJSONString(Object object) throws JSONException {
        return toJSONString(object, DatePattern.YYYY_MM_DD);
    }

    /**
     * <pre>
     *  转换成Json字符串,使用默认的序列化特征
     * </pre>
     *
     * @param object 序列化的对象
     * @return 序列化后的对象字符串表现形式
     * @see #FEATURES
     */
    public static String toJSONString(Object object, DatePattern datePattern) throws JSONException {
        return toJSONString(object, true, datePattern);
    }

    /**
     * <pre>
     *  转换成Json字符串,使用默认的序列化特征，并使用 漂亮的格式特征
     * </pre>
     *
     * @param object 序列化的对象
     * @return 序列化后的对象字符串表现形式
     */
    public static String toFormatJSONString(Object object) throws JSONException {
        return toFormatJSONString(object, DatePattern.YYYY_MM_DD);
    }

    /**
     * <pre>
     *  转换成Json字符串,使用默认的序列化特征，并使用 漂亮的格式特征
     * </pre>
     *
     * @param object      序列化的对象
     * @param datePattern 指定日期序列化格式
     * @return 序列化后的对象字符串表现形式
     */
    public static String toFormatJSONString(Object object, DatePattern datePattern) throws JSONException {
        return toJSONString(object, true, datePattern, null, SerializerFeature.PrettyFormat);
    }

    /**
     * 是否使用默认的字符串特征 转换Json字符串
     *
     * @param object            序列化的对象
     * @param useDefaultFeature 是否使用默认的序列化特征
     * @param pattern           指定日期序列化格式
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONString(Object object, boolean useDefaultFeature, DatePattern pattern) throws JSONException {
        return toJSONString(object, useDefaultFeature, pattern, null);
    }

    /**
     * 指定属性转换为Json字符串
     *
     * @param object     序列化的对象
     * @param properties 要序列化的对象属性名
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONString(Object object, String... properties) throws JSONException {
        return toJSONString(object, DatePattern.YYYY_MM_DD, properties);
    }

    /**
     * 指定属性转换为Json字符串
     *
     * @param object     序列化的对象
     * @param pattern    pattern
     * @param properties 要序列化的对象属性名
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONString(Object object, DatePattern pattern, String... properties) throws JSONException {
        return toJSONString(object, true, pattern, new SerializeFilter[]{new SimplePropertyPreFilter(properties)});
    }

    /**
     * 过滤指定的属性
     *
     * @param object            序列化的对象
     * @param excludeProperties 要序列化的过滤的属性名
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONStringExcludes(Object object, String... excludeProperties) {
        return toJSONStringExcludes(object, DatePattern.YYYY_MM_DD, excludeProperties);
    }

    /**
     * 过滤指定的属性
     *
     * @param object            序列化的对象
     * @param pattern           pattern
     * @param excludeProperties 要序列化的过滤的属性名
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONStringExcludes(Object object, DatePattern pattern, String... excludeProperties) {
        return toJSONString(object, true, pattern, new SerializeFilter[]{new PropertyPreFilters().addFilter().addExcludes(excludeProperties)});
    }

    /**
     * 过滤指定的属性
     *
     * @param object            序列化的对象
     * @param pattern           pattern
     * @param excludeProperties 要序列化的过滤的属性名
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONStringExcludes(Object object,String[] excludeProperties, SerializerFeature... serializerFeatures) {
        return toJSONString(object, true, DatePattern.YYYY_MM_DD_HH_MM_SS, new SerializeFilter[]{new PropertyPreFilters().addFilter().addExcludes(excludeProperties)}, serializerFeatures);
    }

    /**
     * @param object             序列化的对象
     * @param useDefaultFeature  是否使用默认的Feature
     * @param filters            序列化的Filter
     * @param serializerFeatures 指定序列化特征
     * @return 序列化后的对象字符串表现形式
     */
    public static String toJSONString(Object object, boolean useDefaultFeature, DatePattern datePattern, SerializeFilter[] filters, SerializerFeature... serializerFeatures)
            throws JSONException {
        List<SerializerFeature> features = Lists.newArrayList();
        if (useDefaultFeature) {
            CollectionUtils.addAllNotNull(features, FEATURES);
        }
        if (null == datePattern) {
            datePattern = DatePattern.YYYY_MM_DD_HH_MM_SS;
        }
        CollectionUtils.addAllNotNull(features, serializerFeatures);
        return JSON.toJSONString(object, CONFIG, filters, datePattern.getPattern(), JSON.DEFAULT_GENERATE_FEATURE, features.toArray(new SerializerFeature[]{}));
    }

    /**
     * json字符串转对象
     *
     * @param input json字符串
     * @param clazz 要转化的类
     * @return 转化后的类
     */
    public static <T> T parseObject(String input, Class<T> clazz) throws JSONException {
        return JSON.parseObject(input, clazz);
    }

    /**
     * json字符串转Map
     *
     * @param input json字符串
     * @return 转化后的Map
     */
    public static Map<String, Object> parseObjectToMap(String input) throws JSONException {
        return JSON.parseObject(input);
    }

    /**
     * json字符串转集合
     *
     * @param input json字符串
     * @param clazz 要转化的包装类
     * @return 转化后的类
     */
    public static <T> List<T> parseObjectToList(String input, Class<T> clazz) throws JSONException {
        return JSON.parseArray(input, clazz);
    }

}
