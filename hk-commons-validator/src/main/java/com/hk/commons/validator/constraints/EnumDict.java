package com.hk.commons.validator.constraints;

import com.hk.commons.validator.EnumDictValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值：使用字典
 *
 * @author kevin
 * @date 2018-08-31 10:52
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumDictValidator.class)
public @interface EnumDict {

    /**
     * 是否不能为空
     */
    boolean notNull() default true;

    /**
     * 字典id
     */
    String codeId();

    /**
     * 错误提示信息
     */
    String message() default "{com.hk.commons.validator.constraints.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
