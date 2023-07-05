package com.csullagrita.school.aspect;

import com.csullagrita.school.exception.SomethingWentWrongException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class MethodRetryHandlerAspect {
    private static Logger logger = LoggerFactory.getLogger(MethodRetryHandlerAspect.class);

    @Pointcut("@annotation(com.csullagrita.school.aspect.RetryHandler) || @within(com.csullagrita.school.aspect.RetryHandler)")
    public void annotationRetry() {
    }


    @Around("com.csullagrita.school.aspect.MethodRetryHandlerAspect.annotationRetry()")
    public Object doRetryAfterException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object response = null;
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        RetryHandler annotation = method.getAnnotation(RetryHandler.class);
        int retryCount = annotation.retryAttempts();
        long waitSeconds = annotation.sleepInterval();
        boolean successful = false;

        do {
            try {
                retryCount--;
                logger.info("Operation failed, retries remaining: {}", retryCount);
                response = proceedingJoinPoint.proceed();
                successful = true;
            } catch (SomethingWentWrongException exception) {
                if (retryCount < 0) {
                    throw exception;
                }
                if (waitSeconds > 0) {
                    logger.info("Waiting for {} second(s) before next retry", waitSeconds);
                    Thread.sleep(waitSeconds);
                }
            }
        } while (!successful);

        return response;
    }
}
