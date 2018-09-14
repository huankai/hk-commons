package com.hk.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 * 
 * @author: kevin
 * @date: 2018年3月5日上午8:56:03
 */
public abstract class ValidateUtils {

	private static final Pattern IDCARD_PATTERN = Pattern
			.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0-2]\\d)|(3[0-1]))\\d{3}[\\dX]$");

	private static final int[] IDCARD_WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private static final char[] IDCARD_VALIDATE_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-]+@[\\w-]+\\.[\\w]+$");

	public static final Pattern MOBILEPHONE_PARTTERN = Pattern.compile("^(1[3-8][0-9])\\d{8}$");

	/**
	 * 严格的身份证校验
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean isIDCard(CharSequence idCard) {
		Matcher matcher = IDCARD_PATTERN.matcher(idCard);
		if (matcher.find()) {
			int sum = 0;
			for (int i = 0; i < idCard.length() - 1; i++) {
				sum += Integer.parseInt(String.valueOf(idCard.charAt(i))) * IDCARD_WEIGHT[i];
			}
			int mod = sum % 11;
			return idCard.charAt(idCard.length() - 1) == IDCARD_VALIDATE_CODE[mod];
		}
		return false;
	}

	/**
	 * 是否为邮箱号
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isEmail(String args) {
		return StringUtils.isNotBlank(args) && EMAIL_PATTERN.matcher(args).find();
	}

	/**
	 * 是否为手机号
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isMobilephone(String args) {
		return StringUtils.isNotBlank(args) && MOBILEPHONE_PARTTERN.matcher(args).find();
	}
}
