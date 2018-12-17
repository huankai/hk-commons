package com.hk.commons.sms;

/**
 * 短信验证码发送
 *
 * @author kevin
 * @date 2018-07-26 17:47
 */
public interface SmsCodeSender {

    /**
     * 发送信息
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param expireSecond 过期时间:单位:秒
     */
    void send(String mobile, String code, int expireSecond);
}
