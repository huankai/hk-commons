/**
 * 
 */
package com.hk.commons.poi.excel.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @author kevin
 *
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface NestedProperty {

}
