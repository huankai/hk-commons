package com.hk.commons.util;

import com.hk.commons.util.date.DatePattern;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期Id生成器
 *
 * @author kevin
 * @date 2017年9月12日上午10:13:59
 */
public class DateIdGenerator extends TimestampIDGenerator {

    /**
     * 日期模式
     */
    @Getter
    private DatePattern pattern = DatePattern.YYYY_MM_DD_EN;

    public DateIdGenerator() {
        this(null, null);
    }

    public DateIdGenerator(DatePattern pattern) {
        this(null, null, pattern);
    }

    public DateIdGenerator(String prefix, String suffix) {
        super(prefix, suffix);
    }

    public DateIdGenerator(String prefix, String suffix, DatePattern pattern) {
        super(prefix, suffix);
        this.pattern = pattern;
    }

    @Override
    public String generate() {
        LocalDateTime dateTime = LocalDateTime.now();
        String dateStr = dateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern()));
        return String.format("%s%s%s", getPrefix(), dateStr, getSuffix());
    }

}
