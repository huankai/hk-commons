package com.hk.commons.validator.constraints;

import com.hk.commons.validator.EnumStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值：String 类型
 *
 * @author kevin
 * @date 2018-08-31 10:52
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumStringValidator.class)
public @interface EnumString {

    /**
     * 是否不能为空
     */
    boolean notEmpty() default true;

    /**
     * string 类型值列表
     */
    String[] values();

    /**
     * 错误提示消息
     */
    String message() default "{com.hk.commons.validator.constraints.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
