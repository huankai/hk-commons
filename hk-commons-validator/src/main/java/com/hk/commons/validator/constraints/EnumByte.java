package com.hk.commons.validator.constraints;

import com.hk.commons.validator.EnumByteValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值
 *
 * @author kevin
 * @date 2018-08-31 10:52
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumByteValidator.class)
public @interface EnumByte {

    boolean notNull() default false;

    byte[] values();

    String message() default "{com.hk.commons.validator.constraints.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
