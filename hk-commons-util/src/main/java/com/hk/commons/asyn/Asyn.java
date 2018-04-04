package com.hk.commons.asyn;

/**
 *
 */
@FunctionalInterface
public interface Asyn {

	void asyn();

	default void start() {
		new Thread(this::asyn).start();
	}

}
