package com.hk.commons.util;

import lombok.Getter;

/**
 * 时间戳Id生成器
 *
 * @author kevin
 * @date 2017年9月12日上午10:13:20
 */
public class TimestampIDGenerator implements IDGenerator<String> {

    /**
     * 前缀
     */
    @Getter
    private final String prefix;

    /**
     * 后缀
     */
    @Getter
    private final String suffix;

    public TimestampIDGenerator() {
        this(null, null);
    }

    public TimestampIDGenerator(String prefix, String suffix) {
        this.prefix = StringUtils.defaultIfBlank(prefix, StringUtils.EMPTY);
        this.suffix = StringUtils.defaultIfBlank(suffix, StringUtils.EMPTY);
    }

    @Override
    public String generate() {
        return String.format("%s%d%s", prefix, System.currentTimeMillis(), suffix);
    }

}
