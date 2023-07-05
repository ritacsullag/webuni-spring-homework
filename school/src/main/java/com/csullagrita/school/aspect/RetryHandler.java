package com.csullagrita.school.aspect;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface RetryHandler {

    int retryAttempts() default 5;

    long sleepInterval() default 500L; //millisecond

}