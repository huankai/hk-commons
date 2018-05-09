package com.hk.commons.util.date;

import com.google.common.collect.Lists;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * 区间日期工具类
 *
 * @author huangkai
 * @date 2017年12月14日下午1:35:43
 */
public abstract class DateTimeIntervalUtils {

    /**
     * <pre>
     * 获取两个时间段的所有周数
     * 每周开始时间为 周一
     * 每周结束时间为 周日
     * </pre>
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<IntervalDate> getAllWeekList(LocalDateTime start, LocalDateTime end) {
        List<IntervalDate> result = Lists.newArrayList();
        int index = 1;
        LocalDateTime dayOfweekstart = start;
        while (dayOfweekstart.isBefore(end)) {
            LocalDateTime dayOfweekEnd = dayOfweekstart.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                    .with(LocalTime.MAX);
            if (dayOfweekEnd.isAfter(end)) {
                dayOfweekEnd = end;
            }
            result.add(new IntervalDate(index++, dayOfweekstart, dayOfweekEnd));
            dayOfweekstart = dayOfweekstart.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        }
        return result;
    }

    /**
     * 获取一年的所有月数
     *
     * @param year 所在年
     * @return 所在年所有月数
     */
    public static List<IntervalDate> getMonthListByYear(int year) {
        return getAllMonthList(DateTimeUtils.getYearMinDay(year), DateTimeUtils.getYearMaxDay(year));
    }

    /**
     * 获取两个时间段的所有周数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<IntervalDate> getAllWeekList(Date start, Date end) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return getAllWeekList(LocalDateTime.ofInstant(start.toInstant(), defaultZoneId),
                LocalDateTime.ofInstant(end.toInstant(), defaultZoneId));
    }

    /**
     * 获取两个时间段的所有月数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<IntervalDate> getAllMonthList(LocalDateTime start, LocalDateTime end) {
        return getIntervalDateList(start, TemporalAdjusters.lastDayOfMonth(), end, TemporalAdjusters.firstDayOfNextMonth());

    }


    /**
     * 获取两个时间段的所有年
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static List<IntervalDate> getAllYearList(LocalDateTime start, LocalDateTime end) {
        return getIntervalDateList(start, TemporalAdjusters.lastDayOfYear(), end, TemporalAdjusters.firstDayOfNextYear());
    }

    /**
     * @param start      start
     * @param lastDayOf  lastDayOf
     * @param end        end
     * @param firstDayOf firstDayOf
     * @return
     */
    private static List<IntervalDate> getIntervalDateList(LocalDateTime start, TemporalAdjuster lastDayOf, LocalDateTime end, TemporalAdjuster firstDayOf) {
        List<IntervalDate> result = Lists.newArrayList();
        int index = 1;
        LocalDateTime dayOfweekstart = start;
        while (dayOfweekstart.isBefore(end)) {
            LocalDateTime dayOfweekEnd = dayOfweekstart.with(lastDayOf).with(LocalTime.MAX);
            if (dayOfweekEnd.isAfter(end)) {
                dayOfweekEnd = end;
            }
            result.add(new IntervalDate(index++, dayOfweekstart, dayOfweekEnd));
            dayOfweekstart = dayOfweekstart.with(firstDayOf).with(LocalTime.MIN);
        }
        return result;
    }

    /**
     * 区间日期
     *
     * @author huangkai
     * @date 2017年12月14日下午3:00:12
     */
    public static class IntervalDate {

        /**
         * 索引，从1 开始
         */
        private int index;

        /**
         * 开始时间
         */
        private LocalDateTime start;

        /**
         * 结束时间
         */
        private LocalDateTime end;

        IntervalDate(int index, LocalDateTime start, LocalDateTime end) {
            this.index = index;
            this.start = start;
            this.end = end;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

    }

}
