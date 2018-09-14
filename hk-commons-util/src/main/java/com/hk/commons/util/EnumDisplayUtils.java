package com.hk.commons.util;

import com.hk.commons.annotations.EnumDisplay;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * EnumDisplay Util
 *
 * @author: kevin
 * @date: 2017年10月20日上午9:33:12
 * @see EnumDisplay
 */
public abstract class EnumDisplayUtils {

    /**
     * @param enumValue enumValue
     * @return
     */
    public static String getDisplayTest(Object enumValue) {
        return getDisplayText(enumValue, true);
    }

    /**
     * 获取 EnumDisplay注解修饰的value
     *
     * @param enumValue
     * @param useI18n
     * @return
     */
    public static String getDisplayText(Object enumValue, boolean useI18n) {
        EnumDisplay enumDisplay = getEnumDisplay(enumValue);
        return useI18n ? SpringContextHolder.getMessage(enumDisplay.value(), enumDisplay.value()) : enumDisplay.value();
    }

    /**
     * @param enumClass enumClass
     * @param order     order
     * @param useI18n   useI18n
     * @return enumText
     */
    public static String getDisplayText(Class<? extends Enum> enumClass, int order, boolean useI18n) {
        Enum[] enumConstants = enumClass.getEnumConstants();
        for (Enum enumConstant : enumConstants) {
            EnumDisplay enumDisplay = getEnumDisplay(enumConstant);
            if (enumDisplay.order() == order) {
                return useI18n ? SpringContextHolder.getMessage(enumDisplay.value(), enumDisplay.value()) : enumDisplay.value();
            }
        }
        return null;
    }

    /**
     * 获取EnumDisplay注解
     *
     * @param enumValue
     * @return
     */
    private static EnumDisplay getEnumDisplay(Object enumValue) {
        if (null == enumValue) {
            return null;
        }
        Class<?> type = enumValue.getClass();
        Enum<?> en = (Enum<?>) enumValue;
        Field field;
        try {
            field = type.getField(en.name());
            return field.getAnnotation(EnumDisplay.class);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 EnumDisplay注解修饰的order
     *
     * @param enumValue
     * @return
     */
    public static int getDisplayOrder(Object enumValue) {
        EnumDisplay enumDisplay = getEnumDisplay(enumValue);
        return null == enumDisplay ? 0 : enumDisplay.order();
    }

    /**
     * @param enumValue
     * @param enumClass
     * @return
     */
    public static String getDisplayText(String enumValue, Class<? extends Enum<?>> enumClass) {
        return getDisplayText(enumValue, enumClass, true);
    }

    /**
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getDisplayText(String enumValue, Class<? extends Enum> enumClass, boolean useI18n) {
        Object value = Enum.valueOf(enumClass, enumValue);
        return getDisplayText(value, useI18n);
    }

    /**
     * @param enumClass 枚举类
     * @param <TEnum>
     * @return
     */
    public static <TEnum extends Enum<?>> List<EnumItem> getEnumItems(Class<TEnum> enumClass) {
        return getEnumItems(enumClass, true);
    }

    /**
     * 读取枚举项列表。
     *
     * @param enumClass 枚举类
     * @param useI18n   是否使用国际化
     * @return 枚举项的列表 1.value为枚举值value
     * 2.如果枚举项有@EnumDisplay标注，text则取标注的value属性，order取标注的order属性
     * 3.如果没有@EnumDisplay标注，text值为枚举值value，order为0
     */
    public static <TEnum extends Enum<?>> List<EnumItem> getEnumItems(Class<TEnum> enumClass, boolean useI18n) {
        List<EnumItem> items = new ArrayList<>();
        Field[] fields = enumClass.getFields();
        EnumItem item;
        try {
            for (Field field : fields) {
                if (field.isEnumConstant()) {
                    item = new EnumItem();
                    Object value = field.get(null);
                    item.setValue(value);
                    item.setText(value.toString());
                    item.setOrder(0);
                    EnumDisplay ed = field.getAnnotation(EnumDisplay.class);
                    if (null != ed) {
                        item.setText(useI18n ? SpringContextHolder.getMessage(ed.value(), ed.value()) : ed.value());
                        item.setOrder(ed.order());
                    }
                    items.add(item);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class EnumItem extends TextValueItem {

        private int order;

    }

}
