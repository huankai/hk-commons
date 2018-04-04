package com.hk.commons.util.encrypt;

/**
 * 密码加密
 *
 * @author huangkai
 * @date 2017-11-24 16:12
 */
public interface Encrypt {
	
	/**
	 * <pre>
	 * sha512 to Base 64
	 * </pre>
	 *
	 * @param source
	 * @param salt
	 * @return
	 */
	default String asSha512HashToBase64(Object source, Object salt) {
		return asSha512HashToBase64(source, salt, 1);
	}

	/**
	 * sha512 to Base 64
	 * 
	 * @param source
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	String asSha512HashToBase64(Object source, Object salt, int hashIterations);

	/**
	 * Md5 to String
	 * 
	 * @param source
	 * @param salt
	 * @return
	 */
	default String asMD5ToString(Object source, Object salt) {
		return asMD5ToString(source, salt, 1);
	}

	/**
	 * Md5 to String
	 * 
	 * @param source
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	String asMD5ToString(Object source, Object salt, int hashIterations);
}
