package com.study.taskmanagement.service.logging.aspect;

import com.study.taskmanagement.service.logging.annotation.LogMethodNameAndParameters;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MethodNameWithParametersAspect {

    @Around("@annotation(LogMethodNameWithParameters)")
    public Object logMethodName(ProceedingJoinPoint joinPoint)
            throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method invokedMethod = methodSignature.getMethod();
        LogMethodNameAndParameters methodAnnotation = invokedMethod.getAnnotation(LogMethodNameAndParameters.class);
        final Logger logger = LoggerFactory.getLogger(methodAnnotation.loggerName());
        logger.info("{} method invoked with parameters : {}",
                invokedMethod.getName(), joinPoint.getArgs());
        return joinPoint.proceed();
    }

}