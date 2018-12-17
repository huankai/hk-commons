package com.hk.commons.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author kevin
 * @date 2018-06-06 17:58
 */
@NoArgsConstructor
@AllArgsConstructor
public class ListResult<T> implements Iterable<T> {

    /**
     * 查詢总记录数
     */
    @Getter
    private long totalRowCount;

    /**
     * 结果集
     */
    private List<T> result;

    public List<T> getResult() {
        return result == null ? Collections.emptyList() : result;
    }

    @Override
    public Iterator<T> iterator() {
        return getResult().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : getResult()) {
            action.accept(t);
        }
    }
}
