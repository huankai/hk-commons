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
 * <pre>
 *
 * 身份证验证
 * 必须要严格的身份验证 {@link com.hk.commons.util.ValidateUtils#isIDCard(CharSequence)}
 * </pre>
 *
 * @author kevin
 * @date 2018-08-31 10:51
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IdCardValidator.class)
public @interface IdCard {

    /**
     * 是否不能为空
     */
    boolean notEmpty() default false;

    /**
     * 错误提示消息
     */
    String message() default "{com.hk.commons.validator.constraints.IdCard.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
