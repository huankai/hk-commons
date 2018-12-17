package com.hk.commons.asyn;

/**
 * @author kevin
 * @date 2018-07-27 16:38
 */
@FunctionalInterface
public interface Asyn {

    void asyn();

    default void start() {
        new Thread(this::asyn).start();
    }

}
