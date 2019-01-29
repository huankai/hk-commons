package com.hk.commons.util.date;

import com.hk.commons.util.ArrayUtils;
import com.hk.commons.util.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 日期工具类
 *
 * @author kevin
 */
public abstract class DateTimeUtils {

	public static final DatePattern[] PATTERNS = DatePattern.values();

	public static final String[] DATE_PATTERN = datePatterns();

	/**
	 * 返回 calendar所在天的开始时间
	 *
	 * @param calendar calendar
	 * @return calendar
	 */
	public static Calendar getDateWithOutTime(Calendar calendar) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(calendar.getTime());
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		return newDate;
	}

	/**
	 * 获取指定时间这一天开始时间
	 *
	 * @param time time
	 * @return time 这天的开始时间
	 */
	public static LocalDateTime getLocalDateTimeStart(LocalDateTime time) {
		return time.toLocalDate().atTime(LocalTime.MIN);
	}

	/**
	 * 获取指定时间这一天结束时间
	 *
	 * @param time time
	 * @return time 这天的结束时间
	 */
	public static LocalDateTime getLocalDateTimeEnd(LocalDateTime time) {
		return time.toLocalDate().atTime(LocalTime.MAX);
	}

	/**
	 * 日期转字符串, pattern is 'yyyy-MM-dd'
	 *
	 * @param date date
	 * @return sting
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DatePattern.YYYY_MM_DD);
	}

	/**
	 * 日期转字符串 ,自定义pattern
	 *
	 * @param date    date
	 * @param pattern pattern
	 * @return string pattern
	 */
	public static String dateToString(Date date, DatePattern pattern) {
		return DateFormatUtils.format(date, pattern.getPattern());
	}

	/**
	 * 字符串转日期
	 *
	 * @param date date
	 * @return date
	 */
	public static Date stringToDate(String date) {
		return stringToDate(date, DatePattern.YYYY_MM_DD);
	}

	public static List<String> datePatternList() {
		return Collections.unmodifiableList(Arrays.asList(DATE_PATTERN));
	}

	public static String[] datePatterns() {
		return datePatterns(PATTERNS);
	}

	private static String[] datePatterns(DatePattern... patterns) {
		String[] values = new String[patterns.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = patterns[i].getPattern();
		}
		return values;
	}

	/**
	 * 字符串转日期
	 *
	 * @param date date
	 * @return date
	 */
	public static Date stringToDate(String date, DatePattern... patterns) {
		return stringToDate(date, null, patterns);
	}

	/**
	 * 字符串转日期
	 *
	 * @param date     date
	 * @param locale   local
	 * @param patterns patterns
	 * @return date
	 */
	public static Date stringToDate(String date, Locale locale, DatePattern... patterns) {
		if (ArrayUtils.isEmpty(patterns)) {
			patterns = new DatePattern[] { DatePattern.YYYY_MM_DD };
		}
		try {
			return DateUtils.parseDate(date, locale, datePatterns(patterns));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * string To LocalDateTime
	 *
	 * @param date    date
	 * @param pattern pattern
	 * @return localDateTime
	 */
	public static LocalDateTime stringToLocalDateTime(String date, DatePattern pattern) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern.getPattern()));
	}

	/**
	 * LocalDateTime To string
	 *
	 * @param localDateTime localDateTime
	 * @param pattern       pattern
	 * @return string
	 */
	public static String localDateTimeToString(LocalDateTime localDateTime, DatePattern pattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern()));
	}

	/**
	 * Date To Calendar
	 *
	 * @param date date
	 * @return Calendar
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param year year
	 * @return Date
	 */
	public static Date getYearMinDayToDate(int year) {
		return getYearMinDayToDate(LocalDate.ofYearDay(year, 1));
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param localDateTime localDateTime
	 * @return localDateTime
	 */
	public static LocalDateTime getYearMinDay(LocalDateTime localDateTime) {
		return getYearMinDay(localDateTime.toLocalDate());
	}

	/**
	 * Object to String
	 *
	 * @param obj     obj
	 * @param pattern {@link DatePattern#getPattern()}
	 * @return pattern string 格式
	 */
	public static String objectToString(Object obj, String pattern) {
		if (obj instanceof Date) {
			return DateFormatUtils.format((Date) obj, pattern);
		}
		if (obj instanceof Calendar) {
			return DateFormatUtils.format((Calendar) obj, pattern);
		}
		if (obj instanceof Number) {
			return DateFormatUtils.format(((Number) obj).longValue(), pattern);
		}
		if (obj instanceof ChronoLocalDateTime) {
			return ((ChronoLocalDateTime<?>) obj).format(DateTimeFormatter.ofPattern(pattern));
		}
		if (obj instanceof ChronoLocalDate) {
			return ((ChronoLocalDate) obj).format(DateTimeFormatter.ofPattern(pattern));
		}
		if (obj instanceof LocalTime) {
			return ((LocalTime) obj).format(DateTimeFormatter.ofPattern(pattern));
		}
		return ObjectUtils.toString(obj);
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param localDate localDate
	 * @return Date
	 */
	public static Date getYearMinDayToDate(LocalDate localDate) {
		return Date.from(getYearMinDay(localDate).atZone(ZoneOffset.systemDefault()).toInstant());
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param localDate localDate
	 * @return LocalDateTime
	 */
	public static LocalDateTime getYearMinDay(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.firstDayOfYear()).atTime(LocalTime.MIN);
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param year year
	 * @return LocalDateTime
	 */
	public static LocalDateTime getYearMinDay(int year) {
		return getYearMinDay(LocalDate.ofYearDay(year, 1));
	}

	/**
	 * 获取指定年第一天
	 *
	 * @param localDateTime localDateTime
	 * @return Date
	 */
	public static Date getYearMinDayToDate(LocalDateTime localDateTime) {
		return getYearMinDayToDate(localDateTime.toLocalDate());
	}

	/**
	 * 获取指定年最后第一天
	 *
	 * @param year year
	 * @return Date
	 */
	public static Date getYearMaxDayToDate(int year) {
		return getYearMaxDayToDate(LocalDate.ofYearDay(year, 1));
	}

	/**
	 * 获取指定年最后一天
	 *
	 * @param localDate localDate
	 * @return Date
	 */
	public static Date getYearMaxDayToDate(LocalDate localDate) {
		return localDateTimeToDate(getYearMaxDay(localDate));
	}

	/**
	 * 获取指定年最后一天
	 *
	 * @param year year
	 * @return LocalDateTime
	 */
	public static LocalDateTime getYearMaxDay(int year) {
		return getYearMaxDay(LocalDate.ofYearDay(year, 1));
	}

	/**
	 * 获取指定年最后一天
	 *
	 * @param localDate localDate
	 * @return LocalDateTime
	 */
	public static LocalDateTime getYearMaxDay(LocalDate localDate) {
		return localDate.with(TemporalAdjusters.lastDayOfYear()).atTime(LocalTime.MAX);
	}

	/**
	 * 获取指定年最后一天
	 *
	 * @param localDate localDate
	 * @return Date
	 */
	public static Date getYearMaxDayToDate(LocalDateTime localDate) {
		return getYearMaxDayToDate(localDate.toLocalDate());
	}

	/**
	 * 返回日期所在年的最后一天
	 *
	 * @param date date
	 * @return date
	 */
	public static Date getYearMaxDayToDate(Date date) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
				.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
		return localDateTimeToDate(dateTime);
	}

	/**
	 * LocalDateTime To Date
	 *
	 * @param localDateTime localDateTime
	 * @return Date
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
	}

	/**
	 * 返回日期所在年的第一天
	 *
	 * @param date date
	 * @return date
	 */
	public static Date getYearMinDay(Date date) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
				.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
		return localDateTimeToDate(dateTime);
	}

	/**
	 * 返回日期所在月的最后天
	 *
	 * @param date date
	 * @return date
	 */
	public static Date getMonthMaxDate(Date date) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
				.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
		return localDateTimeToDate(dateTime);
	}

	/**
	 * 返回日期所在月的第一天
	 *
	 * @param date date
	 * @return date
	 */
	public static Date getMonthMinDate(Date date) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
				.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		return localDateTimeToDate(dateTime);
	}

	/**
	 * 返回当前日期之前或之后的几天
	 *
	 * @param day (day > 0) 为之后， (day < 0) 为之前， (day == 0) 为当天日期
	 * @return Date
	 */
	public static Date getDate(int day) {
		return DateUtils.addDays(Calendar.getInstance().getTime(), day);
	}

	/**
	 * 返回当前日期之前或之后的几月
	 *
	 * @param month (day > 0) 为之后， (day < 0) 为之前， (day == 0) 为当天日期
	 * @return Date
	 */
	public static Date getMonthDate(int month) {
		return DateUtils.addMonths(Calendar.getInstance().getTime(), month);
	}

	/**
	 * 返回当前日期之前或之后的几年
	 *
	 * @param year (day > 0) 为之后， (day < 0) 为之前， (day == 0) 为当天日期
	 * @return Date
	 */
	public static Date getYearDate(int year) {
		return DateUtils.addYears(Calendar.getInstance().getTime(), year);
	}
}
