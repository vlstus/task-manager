package com.study.taskmanagement.service.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethodNameAndParameters {

    Class<?> loggerName() default LogMethodNameAndParameters.class;

}