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
    public void send(String mobile, String code) {
        Asyn asyn = () -> LOGGER.info("Mobile : {},Code :{}", mobile, code);
        asyn.start();
    }
}
