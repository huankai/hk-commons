package com.hk.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Spring Bean 工具类
 *
 * @author kevin
 * @date 2018-04-16 09:41
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
     * @return T
     * @throws BeansException {@link BeansException}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据Bean类型获取bean
     *
     * @param clazz beanClass
     * @return T
     * @throws BeansException {@link BeansException}
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取国际化消息
     *
     * @param code code
     * @param args args
     * @return i18n Message
     */
    public static String getMessage(String code, Object... args) {
        return getMessageWithDefault(code, null, args);
    }

    /**
     * 获取国际化消息
     *
     * @param code           code
     * @param defaultMessage defaultMessage
     * @param args           args
     * @return i18n Message
     */
    public static String getMessageWithDefault(String code, String defaultMessage, Object... args) {
        return null == applicationContext ? code : applicationContext.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * applicationContext destroy
     */
    @Override
    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("applicationContext destroy......");
        }
        applicationContext = null;
    }


}
