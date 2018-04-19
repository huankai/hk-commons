package com.hk.commons.util;

import com.hk.commons.annotations.EnumDisplay;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * EnumDisplay Util
 *
 * @author huangkai
 * @date 2017年10月20日上午9:33:12
 * @see EnumDisplay
 */
public abstract class EnumDisplayUtils {

	/**
	 * 获取 EnumDisplay注解修饰的value
	 *
	 * @param enumValue
	 * @return
	 */
	public static String getDisplayText(Object enumValue) {
		EnumDisplay enumDisplay = getEnumDisplay(enumValue);
		return SpringContextHolder.getMessage(enumDisplay.value(), enumDisplay.value());
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
		} catch (NoSuchFieldException |SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取 EnumDisplay注解修饰的order
	 *
	 * @param enumValue
	 * @return
	 */
	public static Integer getDisplayOrder(Object enumValue) {
		EnumDisplay enumDisplay = getEnumDisplay(enumValue);
		return null == enumDisplay ? null : enumDisplay.order();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getDisplayText(String enumValue, Class<? extends Enum> enumClass) {
		Object value = Enum.valueOf(enumClass, enumValue);
		return getDisplayText(value);
	}

	/**
	 * 读取枚举项列表。
	 *
	 * @param type
	 * @return 枚举项的列表 1.value为枚举值value
	 *         2.如果枚举项有@EnumDisplay标注，text则取标注的value属性，order取标注的order属性
	 *         3.如果没有@EnumDisplay标注，text值为枚举值value，order为0
	 */
	public static <TEnum extends Enum<?>> List<EnumItem> getEnumItems(Class<TEnum> type) {
		List<EnumItem> items = new ArrayList<>();
		Field[] fields = type.getFields();
		for (Field field : fields) {
			if (!field.isEnumConstant()) {
				continue;
			}
			EnumItem item = new EnumItem();
			try {
				Object value = field.get(null);
				item.setValue(value);
				item.setText(value.toString());
				item.setOrder(0);
				EnumDisplay ed = field.getAnnotation(EnumDisplay.class);
				if (null != ed) {
					item.setText(SpringContextHolder.getMessage(ed.value(),ed.value()));
					item.setOrder(ed.order());
				}
				items.add(item);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return items;
	}

	public static class EnumItem extends TextValueItem {

		private int order;

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

	}

}
