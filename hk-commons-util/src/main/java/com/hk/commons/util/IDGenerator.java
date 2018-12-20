package com.hk.commons.util;

import java.util.UUID;

/**
 * ID生成器
 *
 * @param <T>
 * @author kevin
 * @date 2017年8月22日下午1:32:03
 */
@FunctionalInterface
public interface IDGenerator<T> {

    /**
     * 生成Id
     *
     * @return ID
     */
    T generate();

    /**
     * UUID生成
     */
    IDGenerator<String> STRING_UUID = () -> UUID.randomUUID().toString()
            .replaceAll(StringUtils.RUNG, StringUtils.EMPTY);

    /**
     * MostSignificantBit
     */
    IDGenerator<Long> MOSTSIGN_UUID = () -> UUID.randomUUID().getMostSignificantBits();

}
