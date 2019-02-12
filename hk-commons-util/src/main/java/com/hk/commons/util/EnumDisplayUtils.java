package com.hk.commons.util;

import com.hk.commons.annotations.EnumDisplay;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EnumDisplay Util
 *
 * @author kevin
 * @date 2017年10月20日上午9:33:12
 * @see EnumDisplay
 */
public abstract class EnumDisplayUtils {

    /**
     * 获取 EnumDisplay注解修饰的value
     *
     * @param enumValue enumValue
     * @return enumText
     */
    public static String getDisplayText(Object enumValue) {
        EnumDisplay enumDisplay = getEnumDisplay(enumValue);
        return SpringContextHolder.getMessageWithDefault(enumDisplay.value(), enumDisplay.value());
    }

    /**
     * @param enumClass enumClass
     * @param order     order
     * @return enumText
     */
    public static String getDisplayText(Class<? extends Enum<?>> enumClass, int order) {
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            EnumDisplay enumDisplay = getEnumDisplay(enumConstant);
            if (enumDisplay.order() == order) {
                return SpringContextHolder.getMessageWithDefault(enumDisplay.value(), enumDisplay.value());
            }
        }
        return null;
    }

    /**
     * 获取EnumDisplay注解
     *
     * @param enumValue enumValue
     * @return EnumDisplay
     */
    private static EnumDisplay getEnumDisplay(Object enumValue) {
        if (null == enumValue) {
            return null;
        }
        Class<?> type = enumValue.getClass();
        Enum<?> en = (Enum<?>) enumValue;
        try {
            Field field = type.getField(en.name());
            return field.getAnnotation(EnumDisplay.class);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 EnumDisplay注解修饰的order
     *
     * @param enumValue enumValue
     * @return order value
     */
    public static int getDisplayOrder(Object enumValue) {
        EnumDisplay enumDisplay = getEnumDisplay(enumValue);
        return null == enumDisplay ? 0 : enumDisplay.order();
    }

    /**
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getDisplayText(String enumValue, Class<? extends Enum> enumClass) {
        Object value = Enum.valueOf(enumClass, enumValue);
        return getDisplayText(value);
    }

    /**
     * 读取枚举项列表。
     *
     * @param enumClass 枚举类
     * @return 枚举项的列表 1.value为枚举值value
     * 2.如果枚举项有@EnumDisplay标注，text则取标注的value属性，order取标注的order属性
     * 3.如果没有@EnumDisplay标注，text值为枚举值value，order为0
     */
    public static <TEnum extends Enum<?>> List<EnumItem> getEnumItemList(Class<TEnum> enumClass) {
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
                        item.setText(SpringContextHolder.getMessageWithDefault(ed.value(), ed.value()));
                        item.setOrder(ed.order());
                    }
                    items.add(item);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return items.stream()
        		.sorted(Comparator.comparingInt(EnumItem::getOrder))
        		.collect(Collectors.toList());
    }

	@Data
    @EqualsAndHashCode(callSuper = true)
	@SuppressWarnings("serial")
    public static class EnumItem extends TextValueItem {

        private int order;

    }

}
