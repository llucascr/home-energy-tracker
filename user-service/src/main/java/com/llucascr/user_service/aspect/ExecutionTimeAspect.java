package com.llucascr.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Pointcut("execution(* com.llucascr.user_service.controller.*.*(..))")
    public void controllerMethod() {}

    @Around("controllerMethod()")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long endTime = System.nanoTime();
            long elapsedNs = endTime - startTime;
            long elapsedMs = TimeUnit.NANOSECONDS.toMillis(elapsedNs);
            String signature = pjp.getSignature().toShortString();
            log.info("[USER_CONTROLLER] Controller method {} executed in {} ms", signature, elapsedMs);
        }
    }

}
