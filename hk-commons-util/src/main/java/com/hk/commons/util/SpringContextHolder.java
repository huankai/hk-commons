package com.hk.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author: kevin
 * @date: 2018-04-16 09:41
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 根据名称获取Bean
     *
     * @param name beanName
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据Bean类型获取bean
     *
     * @param clazz beanClass
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取国际化消息
     *
     * @param code           code
     * @param defaultMessage defaultMessage
     * @param args           args
     * @return
     */
    public static String getMessage(String code, String defaultMessage, Object... args) {
        return applicationContext.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    @Override
    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("applicationContext destroy......");
        }
        applicationContext = null;
    }


}
