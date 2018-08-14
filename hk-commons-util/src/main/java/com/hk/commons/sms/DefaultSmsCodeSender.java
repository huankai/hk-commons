package com.hk.commons.sms;

import com.hk.commons.asyn.Asyn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: kevin
 * @date 2018-07-27 08:53
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSmsCodeSender.class);

    @Override
    public void send(String mobile, String code, int expireSecond) {
        Asyn asyn = () -> LOGGER.info("[XXX] {},登陆验证码: {},请在 {} 分钟内完成验证.", mobile, code, expireSecond / 60);
        asyn.start();
    }
}
