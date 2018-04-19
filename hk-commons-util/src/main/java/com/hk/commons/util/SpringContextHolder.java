package com.hk.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author: huangkai
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
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 根据Bean类型获取bean
     *
     * @param clazz
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void destroy() throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("applicationContext destroy......");
        }
        applicationContext = null;
    }


}
