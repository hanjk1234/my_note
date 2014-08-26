package com.java.annotation.document;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 未解决的
 *
 * @author wei.Li by 14-8-12.
 */
@Documented
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Unsolved {

    public String Description() default "";

    public String Result() default "";
}
