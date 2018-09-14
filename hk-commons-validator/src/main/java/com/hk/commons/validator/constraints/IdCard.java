package com.hk.commons.validator.constraints;

import com.hk.commons.validator.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: kevin
 * @date: 2018-08-31 10:51
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {

    boolean notEmpty() default false;

    String message() default "{com.hk.commons.validator.constraints.IdCard.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
